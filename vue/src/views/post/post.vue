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
        <el-input type="text" v-model="theOwnerName" placeholder="搜索发帖人" :clearable="true" style="width: 180px"></el-input>
      </el-form-item>
        <el-form-item>
          <el-input type="text" v-model="theOthers" placeholder="其他搜索条件:分类，标签，内容等" maxlength="20"
                    :clearable="true" style="width: 300px"  ></el-input>
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
      <el-table-column align="center" label="发帖人"  prop="ownerName"  sortable></el-table-column>
      <el-table-column align="center" label="发帖时间" prop="postTime" width="100" sortable></el-table-column>
      <el-table-column align="center" label="所属分类"  width="110" sortable>
        <template slot-scope="scope">
            <el-tag type="success" v-text="scope.row.sortName" style="float: left;margin-left: 5px"></el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="标签" width="270">
        <template slot-scope="scope">
          <div v-for="tag in scope.row.tagList">
            <el-tag v-text="tag" style="float: left;margin-left: 5px"></el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="置顶模块" width="200">
        <template slot-scope="scope">
          <div v-for="top in scope.row.tops">
            <el-tag v-text="top" style="float: left;margin-left: 5px"></el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="显示状态" width="120" sortable>
      <template slot-scope="scope">
        <div v-if="scope.row.deleteState==1" >
          <el-tag type="success">正常</el-tag>
        </div>
        <div v-else>
          <el-tag type="warning">隐藏</el-tag>
        </div>
      </template>
      </el-table-column>
      <el-table-column align="center" label="点赞数/偏移量" prop="alike" width="150" sortable></el-table-column>
      <el-table-column align="center" label="收藏数/偏移量" prop="acollect" width="150" sortable></el-table-column>
      <el-table-column align="center" label="浏览数/偏移量" prop="aview" width="150" sortable></el-table-column>
      <el-table-column align="center" label="管理" width="300" >
        <template slot-scope="scope">
          <el-button type="primary"
                     @click="showDetail(scope.$index)"><i class="el-icon-info"></i></el-button>
          <el-button type="primary"
                     @click="showUpdate(scope.$index)"><i class="el-icon-edit"></i></el-button>
          <el-button type="primary"
                     @click="showTop(scope.$index)"><i class="el-icon-caret-top"></i>
          </el-button>
          <el-button type="danger"  v-if="scope.row.deleteState==0"
                     @click="recoverPost(scope.$index)"><i class="el-icon-circle-plus"></i>
          </el-button>
          <el-button type="danger"  v-if="scope.row.deleteState==1"
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
        <el-form-item label="请选择置顶模块" v-if="dialogStatus=='makeTop' " v-model="tempPost.tops" required>
          <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll"
                       @change="checkAllTop">全选</el-checkbox>
          <el-checkbox-group v-model="tempPost.tops" @change="checkTop" style="display: inline-block;margin-left: 10px" >
            <el-checkbox v-for="top in allTops" :label="top" :key="top">
              {{top}}
            </el-checkbox>
          </el-checkbox-group>

        </el-form-item>
        <el-form-item label="发帖者账号:"  v-if="dialogStatus=='detail'" v-model="tempPost.ownerId" required>
             {{tempPost.ownerId}}
        </el-form-item>
        <el-form-item label="发帖时间:"  v-if="dialogStatus=='detail'" v-model="tempPost.postTime" required>
            {{tempPost.postTime}}
        </el-form-item>
        <el-form-item label="所属分类:"  v-if="dialogStatus!='makeTop' " v-model="tempPost.sortName" required>
          <div v-if="dialogStatus=='detail'" >
            <el-tag v-text="tempPost.sortName" effect="dark" style="float: left;margin-left: 5px"></el-tag>
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
        <el-form-item label="帖子标签:"   v-if="dialogStatus!='makeTop' " required>
          <div v-if="dialogStatus=='detail'" v-model="tempPost.tags">
            <div v-for="tag in tempPost.tags">
              <el-tag v-text="tag" style="float: left;margin-left: 5px"></el-tag>
           </div>
          </div>
          <div v-else>
            <el-collapse  v-model="activeNames">
              <el-collapse-item  title="已选" name="1" >
                <el-checkbox-group v-model="tempPost.tags" size="mini">
                  <el-checkbox-button  v-for="taged in tempPost.tags" :label="taged" :key="taged" style="margin-left: 10px">
                    {{taged}}
                  </el-checkbox-button>
                </el-checkbox-group>
              </el-collapse-item>
            </el-collapse>
            <el-collapse @change="getFirstTag">
              <el-collapse-item title="全部"  >
                <el-collapse v-model="activeNames" accordion  @change="getSecondTag">
                  <el-collapse-item v-for="firtag in firstTag" :title="firtag.tagName" :key="firtag.tagName" :name="firtag.tagName" style="margin-left: 20px"  >
                    <el-collapse   accordion  @change="getSomeTag" >
                      <el-collapse-item v-for="sectag in secondTag" :title="sectag.tagName"  :key="sectag.tagId"  :name="sectag.tagName" style="margin-left: 20px;">
                        <el-checkbox-group  v-model="tempPost.tags" size="mini">
                          <el-checkbox-button  v-for="tag in someTag" :label="tag.tagName" :key="tag.tagName" style="margin-left: 10px">
                            {{tag.tagName}}
                          </el-checkbox-button>
                        </el-checkbox-group>
                      </el-collapse-item>
                    </el-collapse>
                  </el-collapse-item>
                </el-collapse>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-form-item>
        <el-form-item label="帖子内容:"  v-if="dialogStatus!='makeTop' " required>
          <div v-if="dialogStatus=='detail'" v-model="tempPost.postContent">
            {{tempPost.postContent}}
          </div>
          <div v-else>
            <el-input
              type="textarea"
              :maxlength="200"
              :show-word-limit="true"
              autosize
              v-model="tempPost.postContent">
            </el-input>
          </div>
        </el-form-item>
        <el-form-item   v-if="dialogStatus!='makeTop' " v-model="tempPost.imgs" style="overflow: hidden;">
          <div v-if="dialogStatus=='detail'" >
            <div   v-for="oneImg in tempPost.imgs"  style="float: left; margin-left: 20px;">
              <img :src="oneImg" style="width: 120px;" >
            </div>
          </div>
          <div v-else>

            <el-checkbox-group v-model="tempImg"  >
                <el-checkbox-button v-for="oneImg in tempPost.imgs"  :label="oneImg" :key="oneImg" style="float: left;margin-left: 20px">
                  <img :src="oneImg" style="width: 120px;" >
                </el-checkbox-button>
            </el-checkbox-group>
            <el-button  size="mini" v-if="tempPost.imgs!=null&&tempPost.imgs.length!=0"  @click="tempDelete">删除</el-button>
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">返回</el-button>
        <el-button v-if="dialogStatus=='update'"  @click="updatePost">修 改</el-button>
        <el-button v-if="dialogStatus=='makeTop'"  @click="updatePostTop">置 顶</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  export default {
    data() {
      return {
        theStart: '',
        theEnd: '',
        theOwnerName: '',
        theOthers: '',  //搜索参数
        allTops: ['推荐', '地铁周边', '热门商圈', '人气'], //可选置顶模块
        checkAll:false,
        isIndeterminate: false,
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
          theStart: '',
          theEnd: '',
          theOwnerName: '',
          theOthers: ''
        },
        sorts: [],//分类列表
        activeNames:[],
        tempImg:[],
        firstTag:[],
        secondTag:[],
        someTag:[],
        dialogStatus: 'detail',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          detail: '详情',
          makeTop: '置顶'
        },
        tempPost: {
          postId: '',
          ownerId: '',
          postTime: '',
          sortId: '',
          sortName: '',
          postContent: '',
          postLocation: '',
          postPhone: '',
          priceLow: '',
          priceHigh: '',
          timeStart: '',
          timeEnd: '',
          isTop: '',
          likeOff: '',
          collectOff: '',
          viewOff: '',
          tags: [],
          imgs: [],
          tops: [],
          selectTag:[],
          newTag:[],
          deleteTag:[],
          deleteImg:[]
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
      getFirstTag(){
        this.api({
          url:"/post/getFirstTag",
          method:"get"
        }).then(data=>{
          this.firstTag = data.list;
          console.log(this.firstTag);
        })
      },
      getSecondTag(value) {
        if (value != null && value != '') {
          this.api({
            url: "/post/getSomeTag",
            method: "post",
            data: {need: value}
          }).then(data => {
            this.secondTag = data.list;
          })
        }
      },
      getSomeTag(value) {
        if (value != null && value != '') {
          this.api({
            url: "/post/getSomeTag",
            method: "post",
            data: {need: value}
          }).then(data => {
            this.someTag = data.list;
          })
        }
      },
      getList() {
        //查询列表
        this.listLoading = true;
        this.listQuery.theStart = this.theStart;
        this.listQuery.theEnd = this.theEnd;
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
          for (let i = 0; i < data.list.length; i++) {
            let listTop = new Array();
            let j = 3;
            let temp = parseInt(data.list[i].isTop);
            while (temp > 0) {
              if (temp % 10 == 1)
                listTop.unshift(this.allTops[j]);
              j--;
              temp = parseInt(temp / 10);
            }
            this.list[i].tops = listTop;
            this.list[i].alike += '/' + this.list[i].likeOff;
            this.list[i].acollect += '/' + this.list[i].collectOff;
            this.list[i].aview += '/' + this.list[i].viewOff;
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
      showDetail($index) {
        let _vue = this;
        let onePost = _vue.list[$index];
        this.api({
          url: "/post/getOnePost",
          method: "post",
          data: onePost
        }).then(data => {
          this.listLoading = false;
          let oPost = data.thePost;
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
      showUpdate($index) {
        let _vue = this;
        let onePost = _vue.list[$index];
        this.api({
          url: "/post/getOnePost",
          method: "post",
          data: onePost
        }).then(data => {
          this.listLoading = false;
          let oPost = data.thePost;
          this.activeNames=['1'];
          this.tempPost.postId = oPost.postId;
          this.tempPost.sortId = oPost.sortId;
          this.tempPost.sortName = oPost.sortName;
          this.tempPost.postContent = oPost.postContent;
          this.tempPost.postLocation = oPost.postLocation;
          this.tempPost.postPhone = oPost.postPhone;
          this.tempPost.tags = oPost.tagList;
          this.tempPost.selectTag = oPost.tagList;
          this.tempPost.deleteTag =[];
          this.tempPost.newTag = [];
          this.tempPost.imgs = oPost.imgList;
          this.tempImg = [];
          this.tempPost.deleteImg = [];
          this.tempPost.likeOff = oPost.likeOff;
          this.tempPost.collectOff = oPost.collectOff;
          this.tempPost.viewOff = oPost.viewOff;
          this.tempPost.isTop = oPost.isTop;
          this.dialogStatus = "update";
          this.dialogFormVisible = true;
        })
      },
      updatePost() {
        console.log(this.tempPost.deleteImg);
        for(let i=0;i<this.tempPost.tags.length;i++){
          let st = 1;
          for(let j=0;j<this.tempPost.selectTag.length;j++){
            if(this.tempPost.tags[i]==this.tempPost.selectTag[j]) {
               st=0;   break;
            }
          }
          if(st==1) {this.tempPost.newTag.push(this.tempPost.tags[i]);}
        }
        for(let i=0;i<this.tempPost.selectTag.length;i++){
          let sb = 1;
          for(let j=0;j<this.tempPost.tags.length;j++){
            if(this.tempPost.selectTag[i]==this.tempPost.tags[j]) {
              sb=0;   break;
            }
          }
          if(sb==1) this.tempPost.deleteTag.push(this.tempPost.selectTag[i])
        }

        let _vue = this;
        this.api({
          url: "/post/updatePost",
          method: "post",
          data: this.tempPost
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false;
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
      showTop($index) {
        let post = this.list[$index];
        this.tempPost.postId = post.postId;
        this.tempPost.tops = post.tops;
        this.tempPost.isTop = post.isTop;
        this.dialogStatus = "makeTop"
        this.dialogFormVisible = true
      },
      updatePostTop() {
        let _vue = this;
        this.api({
          url: "/post/updatePostTop",
          method: "post",
          data: this.tempPost
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false;
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
          onePost.deleteState = 1;
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
          onePost.deleteState = 0;
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
      checkAllTop(val) {
        this.tempPost.tops = val ? this.allTops : [];
        this.isIndeterminate = false;
        if (val == true)
          this.tempPost.isTop = 1111;
        else this.tempPost.isTop = 0;
      },
      checkTop(value) {
        let checkedCount = value.length;
        this.checkAll = checkedCount === this.allTops.length;
        this.isIndeterminate = checkedCount > 0 && checkedCount < this.allTops.length;
        this.tempPost.isTop = 0;
        for (let i = 0; i < value.length; i++) {
          for (let j = 0; j < 4; j++) {
            if (value[i] == this.allTops[j]) {
              this.tempPost.isTop += Math.pow(10, (3 - j));
              break;
            }
          }
        }
      },
      tempDelete(){
      for(let i=0;i<this.tempImg.length;i++){
        for(let j=0;j<this.tempPost.imgs.length;j++)
          if(this.tempImg[i]==this.tempPost.imgs[j])
            this.tempPost.imgs.splice(j,1);
      }
      this.tempPost.deleteImg=this.tempImg;
      },
      checkNumber(value){
        if(value<0)
          alert("浮动值不能为负数！");
        if(value>parseInt(value))
          alert("浮动值不能为小数！");
      }
    }
  }
</script>
