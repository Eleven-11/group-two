<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" >
        <el-form-item>
          <div class="block">
            <el-date-picker
            v-model="theStart"
            type="datetime"
            placeholder="选择起始时间">
          </el-date-picker>至
            <el-date-picker
              v-model="theEnd"
              type="datetime"
              placeholder="选择截止时间">
            </el-date-picker>
        </div>
        </el-form-item>
      <el-form-item>
        <el-input type="text" v-model="theOwnerName" placeholder="搜索发帖人" clearable="true" style="width: 180px"></el-input>
      </el-form-item>
        <el-form-item>
          <el-input type="text" v-model="theOthers" placeholder="其他搜索条件:分类，标签，内容等" maxlength="20"
                    clearable="true" style="width: 300px"  ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search"  @click="handleFilter">搜索
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column  align="center" label="帖子ID" prop="postId" style="width: 60px;" v-if="false"></el-table-column>
      <el-table-column align="center" label="发帖人" prop="ownerName"  sortable></el-table-column>
      <el-table-column align="center" label="发帖时间" prop="postTime" width="100" sortable></el-table-column>
      <el-table-column align="center" label="所属分类" prop="sortName" width="110" sortable></el-table-column>
      <el-table-column align="center" label="标签" width="270">
        <template slot-scope="scope">
          <div v-for="tag in scope.row.tagList">
            <el-tag v-text="tag" style="float: left;margin-left: 5px"></el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="置顶状态" prop="isTop" width="170"></el-table-column>
      <el-table-column align="center" label="显示状态" prop="deleteState" width="120" sortable></el-table-column>
      <el-table-column align="center" label="点赞数/偏移量" prop="alike" width="150" sortable></el-table-column>
      <el-table-column align="center" label="收藏数/偏移量" prop="acollect" width="150" sortable></el-table-column>
      <el-table-column align="center" label="浏览数/偏移量" prop="aview" width="150" sortable></el-table-column>
      <el-table-column align="center" label="管理" width="300" >
        <template slot-scope="scope">
          <el-button type="primary" icon="edit"
                     @click="showDetail(scope.$index)"><i class="el-icon-info"></i></el-button>
          <el-button type="primary" icon="edit"
                     @click="showUpdate(scope.$index)"><i class="el-icon-edit"></i></el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.deleteState=='隐藏'"
                     @click="recoverPost(scope.$index)"><i class="el-icon-circle-plus"></i>
          </el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.deleteState=='正常显示'"
                     @click="deletePost(scope.$index)"><i class="el-icon-remove"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempPost" label-position="left" label-width="180px">
        <el-form-item v-if="false" v-model="tempPost.postId" required>
          {{tempPost.postId}}
        </el-form-item>
        <el-form-item label="发帖者账号:"  v-if="dialogStatus=='detail'" v-model="tempPost.ownerId" required>
             {{tempPost.ownerId}}
        </el-form-item>
        <el-form-item label="发帖时间:"  v-if="dialogStatus=='detail'" v-model="tempPost.postTime" required>
            {{tempPost.postTime}}
        </el-form-item>
        <el-form-item label="所属分类:"  v-model="tempPost.sortName" required>
          <div v-if="dialogStatus=='detail'" >
            {{tempPost.sortName}}
          </div>
          <div v-else >
              <el-select v-model="tempPost.sortId" placeholder="请选择">
                <el-option
                  v-for="item in sorts"
                  :key="item.sortId"
                  :label="item.sortName"
                  :value="item.sortId">
                </el-option>
              </el-select>
          </div>
        </el-form-item>
        <el-form-item label="帖子标签:" v-if="dialogStatus=='detail'" required>
          <div v-if="dialogStatus=='detail'" v-model="tempPost.tags">
            <div v-for="tag in tempPost.tags">
              <div v-text="'#'+tag" style="float: left;"></div>
           </div>
          </div>
        </el-form-item>
        <el-form-item label="帖子内容:"  required>
          <div v-if="dialogStatus=='detail'" v-model="tempPost.postContent">
            {{tempPost.postContent}}
          </div>
          <div v-else>
            <el-input
              type="textarea"
              :maxlength="200"
              show-word-limit
              autosize
              v-model="tempPost.postContent">
            </el-input>
          </div>
        </el-form-item>
        <el-form-item  v-model="tempPost.imgs" style="overflow: hidden;">
          <div v-if="dialogStatus=='detail'" >
            <div  v-for="oneImg in tempPost.imgs" style="float: left; margin-left: 20px;">
              <img :src="oneImg" style="width: 120px;" >
            </div>
          </div>
          <div v-else>
            <div v-for="oneImg in tempPost.imgs" style="float: left;margin-left: 20px">
              <el-checkbox-button><img :src="oneImg" style="width: 120px;"></el-checkbox-button>
            </div>
          </div>
      </el-form-item>
        <el-form-item label="位置:" required v-if="dialogStatus=='detail'" v-model="tempPost.postLocation">
            {{tempPost.postLocation}}
        </el-form-item>
        <el-form-item label="联系电话:"  v-if="dialogStatus=='detail'" v-model="tempPost.postPhone"required>
            {{tempPost.postPhone}}
        </el-form-item>
        <el-form-item   label="价格:"  v-if="dialogStatus=='detail'" v-model="tempPost.priceLow" required>
            {{tempPost.priceLow}}
          <span v-if="tempPost.priceHigh!=null " v-model="tempPost.priceHigh">~~{{tempPost.priceHigh}}</span>
        </el-form-item>
        <el-form-item label="租赁时间:"  v-if="tempPost.sortName=='菜虚鲲'&&dialogStatus=='detail'" v-model="tempPost.sortName" required>
          <div v-if=" tempPost.timeStart!=null" v-model="tempPost.timeStart" >
            {{tempPost.timeStart}}
            <span v-if="tempPost.timeEnd!=null" v-model="tempPost.timeEnd">~~{{tempPost.timeEnd}}</span>
          </div>
        </el-form-item>
        <el-form-item label="点赞数浮动值:" required v-if="dialogStatus=='update'" v-model="tempPost.likeOff">
          <el-input-number  v-model="tempPost.likeOff" @change="checkNumber" >
          </el-input-number>
        </el-form-item>
        <el-form-item label="收藏数浮动值:" required v-if="dialogStatus=='update'" v-model="tempPost.collectOff">
          <el-input-number v-model="tempPost.collectOff" @change="checkNumber">
          </el-input-number>
        </el-form-item>
        <el-form-item label="浏览数浮动值:" required v-if="dialogStatus=='update'" v-model="tempPost.viewOff">
          <el-input-number v-model="tempPost.viewOff" @change="checkNumber">
          </el-input-number>
        </el-form-item>
      </el-form>
<!--      <el-form-item label="标签" required>-->
<!--        <div v-for=" (menu,_index) in allPermission" :key="menu.menuName">-->
<!--            <span style="width: 100px;display: inline-block;">-->
<!--              <el-button :type="isMenuNone(_index)?'':(isMenuAll(_index)?'success':'primary')" size="mini"-->
<!--                         style="width:80px;"-->
<!--                         @click="checkAll(_index)">{{menu.menuName}}</el-button>-->
<!--            </span>-->
<!--          <div style="display: inline-block;margin-left:20px;">-->
<!--            <el-checkbox-group v-model="tempRole.permissions">-->
<!--              <el-checkbox v-for="perm in menu.permissions" :label="perm.id" @change="checkRequired(perm,_index)"-->
<!--                           :key="perm.id">-->
<!--                <span :class="{requiredPerm:perm.requiredPerm===1}">{{perm.permissionName}}</span>-->
<!--              </el-checkbox>-->
<!--            </el-checkbox-group>-->
<!--          </div>-->
<!--        </div>-->
<!--        <p style="color:#848484;">说明:红色权限为对应菜单内的必选权限</p>-->
<!--      </el-form-item>-->
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">返回</el-button>
        <el-button v-if="dialogStatus=='update'"  @click="updatePost">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  export default {
    data() {
      return {
        theStart:'',
        theEnd:'',
        theOwnerName:'',
        theOthers:'',  //搜索参数
        listTop:[],    //置顶模块
        allListTop:[], //可选置顶模块
        peerPost:[],
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
          theStart:'',
          theEnd:'',
          theOwnerName:'',
          theOthers:'',
        },
        sorts: [],//分类列表
        dialogStatus: 'detail',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          detail: '详情'
        },
        tempPost: {
          postId: '',
          ownerId: '',
          postTime: '',
          sortId:'',
          sortName: '',
          postContent:'',
          postLocation:'',
          postPhone:'',
          priceLow:'',
          priceHigh:'',
          timeStart:'',
          timeEnd:'',
          isTop:'',
          likeOff:'',
          collectOff:'',
          viewOff:'',
          tags:[],
          imgs:[]
        },
      }
    },
    created() {
      this.getList();
      this.getAllSorts();
      // if (this.hasPerm('user:add') || this.hasPerm('user:update')) {
      //   this.getAllRoles();
      // }
    },
    computed: {
      ...mapGetters([
        'postId'
      ])
    },
      methods: {
        getAllSorts() {
          this.api({
            url: "/post/getAllSorts",
            method: "get"
          }).then(data => {
            this.sorts = data.list;
          })
        },
      getList() {
        //查询列表
        this.listLoading = true;
        this.listQuery.theStart = this.theStart;
        this.listQuery.theEnd =this.theEnd;
        this.listQuery.theOwnerName = this.theOwnerName;
        this.listQuery.theOthers = this.theOthers;
        this.api({
          url: "/post/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
          for ( var i=0;i<data.list.length;i++){
            this.peerPost=data.list[i];
            var temp=parseInt(this.peerPost.isTop);
            if(this.peerPost.isTop==2222) {this.peerPost.isTop='未置顶' }
            else{
              if(temp%10<2) {
                this.peerPost.isTop = '人气模块/'
              }else{this.peerPost.isTop=''}
              var temp1=parseInt(temp/10);
              if(temp1%10<2) {
                this.peerPost.isTop = '地铁周边模块/'+this.peerPost.isTop;
              }
              var temp2=parseInt(temp1/10);
              if(temp2%10<2) {
                this.peerPost.isTop = '热门商圈模块/'+this.peerPost.isTop;
              }
              var temp3=parseInt(temp2/10);
              if(temp3%10<2) {
                this.peerPost.isTop = '推荐模块/'+this.peerPost.isTop;
              }
            }
            if(this.peerPost.deleteState==1){
              this.peerPost.deleteState='正常显示';
            }else{this.peerPost.deleteState="隐藏";}
            this.peerPost.alike=this.peerPost.alike+'/'+this.peerPost.likeOff;
              this.peerPost.acollect=this.peerPost.acollect+'/'+this.peerPost.collectOff;
                this.peerPost.aview=this.peerPost.aview+'/'+this.peerPost.viewOff;
          }
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val
        this.getList();
      },
      handleFilter() {
        //查询事件
        this.listQuery.pageNum = 1
        this.getList()
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showDetail($index){
        let _vue = this;
        let onePost = _vue.list[$index];
          this.api({
            url: "/post/getOnePost",
            method: "post",
            data: onePost
          }).then(data => {
            this.listLoading = false;
            let oPost=data.thePost;
            this.tempPost.ownerId = oPost.ownerId;
            this.tempPost.postTime = oPost.postTime;
            this.tempPost.sortName = oPost.sortName;
            this.tempPost.postContent = oPost.postContent;
            this.tempPost.postLocation = oPost.postLocation;
            this.tempPost.postPhone = oPost.postPhone;
            this.tempPost.priceLow = oPost.priceLow;
            this.tempPost.priceHigh = oPost.priceHigh;
            this.tempPost.timeStart = oPost.timeStart;
            this.tempPost.timeEnd = oPost.timeEnd;
            this.tempPost.tags = oPost.tagList;
            this.tempPost.imgs = oPost.imgList;
            this.dialogStatus = "detail";
            this.dialogFormVisible = true;
          })
      },
      showUpdate($index){
        let _vue = this;
        let onePost = _vue.list[$index];
        this.api({
          url: "/post/getOnePost",
          method: "post",
          data: onePost
        }).then(data => {
          this.listLoading = false;
          let oPost=data.thePost;
          this.tempPost.postId = oPost.postId;
          this.tempPost.sortId = oPost.sortId;
          this.tempPost.sortName = oPost.sortName;
          this.tempPost.postContent = oPost.postContent;
          this.tempPost.postLocation = oPost.postLocation;
          this.tempPost.postPhone = oPost.postPhone;
          this.tempPost.tags = oPost.tagList;
          this.tempPost.imgs = oPost.imgList;
          this.tempPost.likeOff = oPost.likeOff;
          this.tempPost.collectOff = oPost.collectOff;
          this.tempPost.viewOff = oPost.viewOff;
          this.tempPost.isTop = oPost.isTop;
          this.dialogStatus = "update";
          this.dialogFormVisible = true;
        })
      },
      updatePost() {
        //修改用户信息
        let _vue = this;
        this.api({
          url: "/post/updatePost",
          method: "post",
          data: this.tempPost
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false
          if (this.postId === this.tempPost.postId) {
            msg = '修改成功,部分信息重新登录后生效'
          }
          this.$message({
            message: msg,
            type: 'success',
            duration: 1 * 1000,
            onClose: () => {
              _vue.getList();
            }
          })
        })
      },
      recoverPost($index) {
        let _vue = this;
        this.$confirm('确定恢复此帖子的显示?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let onePost = _vue.list[$index];
          onePost.deleteState =1;
          _vue.api({
            url: "/post/updatePostState",
            method: "post",
            data: onePost
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("恢复失败")
          })
        })
      },
      deletePost($index) {
        let _vue = this;
        this.$confirm('确定隐藏此帖子?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let onePost = _vue.list[$index];
          onePost.deleteState =2;
          _vue.api({
            url: "/post/updatePostState",
            method: "post",
            data: onePost
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("隐藏失败")
          })
        })
      },
        checkNumber(value){
         if(value<0)
           this.$alert("浮动值不能为负");
        }
    }
  }
</script>
