<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus"  @click="showCreate">添加
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
      <el-table-column align="center" label="发帖人" prop="ownerName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="发帖时间" prop="postTime" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="所属分类" prop="sortName" width="170"></el-table-column>
      <el-table-column align="center" label="标签">
        <template slot-scope="scope">
          <div v-for="tag in scope.row.tagList">
            <div v-text="tag" style="display: inline-block;vertical-align: middle;"></div>
          </div>
        </template>
      </el-table-column>
      <el-table-column align="center" label="置顶状态" prop="isTop" width="170"></el-table-column>
      <el-table-column align="center" label="显示状态" prop="deleteState" width="120"></el-table-column>
      <el-table-column align="center" label="点赞数+偏移量" prop="alike" width="120"></el-table-column>
      <el-table-column align="center" label="收藏数+偏移量" prop="acollect" width="120"></el-table-column>
      <el-table-column align="center" label="浏览数+偏移量" prop="aview" width="120"></el-table-column>
      <el-table-column align="center" label="管理" width="220" >
        <template slot-scope="scope">
          <el-button type="primary" icon="edit"
                     @click="showDetail(scope.$index)">详情</el-button>
          <el-button type="primary" icon="edit"
                     @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.deleteState=='隐藏'"
                     @click="recoverPost(scope.$index)">恢复
          </el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.deleteState=='正常显示'"
                     @click="deletePost(scope.$index)">隐藏
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
      <el-form class="small-space" :model="tempPost" label-position="left" label-width="180px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="发帖者账号" required v-if="dialogStatus=='detail'">
          <el-input aria-readonly="true" type="text" v-model="tempPost.ownerId">
          </el-input>
        </el-form-item>
        <el-form-item label="发帖人昵称" v-if="dialogStatus=='detail'" required>
          <el-input  type="text" aria-readonly="true" v-model="tempPost.ownerName">
          </el-input>
        </el-form-item>
        <el-form-item label="发帖时间" v-else>
          <el-input type="text" v-model="tempPost.postTime" >
          </el-input>
        </el-form-item>
        <el-form-item label="角色" required>
          <el-select v-model="tempUser.roleId" placeholder="请选择">
            <el-option
              v-for="item in roles"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="昵称" required>
          <el-input type="text" v-model="tempUser.nickname">
          </el-input>
        </el-form-item>
      </el-form>
      <el-form-item label="标签" required>
        <div v-for=" (menu,_index) in allPermission" :key="menu.menuName">
            <span style="width: 100px;display: inline-block;">
              <el-button :type="isMenuNone(_index)?'':(isMenuAll(_index)?'success':'primary')" size="mini"
                         style="width:80px;"
                         @click="checkAll(_index)">{{menu.menuName}}</el-button>
            </span>
          <div style="display: inline-block;margin-left:20px;">
            <el-checkbox-group v-model="tempRole.permissions">
              <el-checkbox v-for="perm in menu.permissions" :label="perm.id" @change="checkRequired(perm,_index)"
                           :key="perm.id">
                <span :class="{requiredPerm:perm.requiredPerm===1}">{{perm.permissionName}}</span>
              </el-checkbox>
            </el-checkbox-group>
          </div>
        </div>
        <p style="color:#848484;">说明:红色权限为对应菜单内的必选权限</p>
      </el-form-item>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">返回</el-button>
        <el-button v-if="dialogStatus=='update'" type="success" @click="createUser">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        peerPost:[],
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
        },
        roles: [],//角色列表
        dialogStatus: 'detail',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          detail: '详情'
        },
        tempPost: {
          postId: '',
          ownerId: '',
          ownerName: '',
          postTime: '',
          sortName: '',
          postContent:'',
          postLocation:'',
          postPhone:'',
          priceLow:'',
          priceHigh:'',
          timeStart:'',
          timeEnd:'',
          isTop:'',
          allLike:'',
          allCollect:'',
          allView:'',
          tags:[],
          imgs:[],
        },

      }
    },
    created() {
      this.getList();
      if (this.hasPerm('user:add') || this.hasPerm('user:update')) {
        this.getAllRoles();
      }
    },
    computed: {
      ...mapGetters([
        'userId'
      ])
    },
    methods: {
      getAllRoles() {
        this.api({
          url: "/user/getAllRoles",
          method: "get"
        }).then(data => {
          this.roles = data.list;
        })
      },
      getList() {
        //查询列表
        this.listLoading = true;
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
              }
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
            this.peerPost.alike=this.peerPost.alike+'+'+this.peerPost.likeOff;
              this.peerPost.acollect=this.peerPost.acollect+'+'+this.peerPost.collectOff;
                this.peerPost.aview=this.peerPost.aview+'+'+this.peerPost.viewOff;
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
            this.tempPost.ownerName = oPost.ownerName;
            this.dialogStatus = "detail"
            this.dialogFormVisible = true
          })
      },
      showCreate() {
        //显示新增对话框
        this.tempUser.username = "";
        this.tempUser.password = "";
        this.tempUser.nickname = "";
        this.tempUser.roleId = "";
        this.tempUser.userId = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        let user = this.list[$index];
        this.tempUser.username = post.username;
        this.tempUser.nickname = post.nickname;
        this.tempUser.roleId = post.roleId;
        this.tempUser.userId = post.userId;
        this.tempUser.deleteStatus = '1';
        this.tempUser.password = '';
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createUser() {
        //添加新用户
        this.api({
          url: "/user/addUser",
          method: "post",
          data: this.tempUser
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateUser() {
        //修改用户信息
        let _vue = this;
        this.api({
          url: "/user/updateUser",
          method: "post",
          data: this.tempUser
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false
          if (this.userId === this.tempUser.userId) {
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
    }
  }
</script>
