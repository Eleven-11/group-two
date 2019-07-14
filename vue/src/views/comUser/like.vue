<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form >
        <el-form-item label="用户名"  >
          <el-input type="text" v-model="userName"  onkeyup="value=value.replace(/[^\w\u4E00-\u9FA5]/g, '')" style="width: 200px;margin-right: 30px;">
          </el-input>
          <el-button type="primary"  icon="el-icon-search"  @click="selectUser">查询</el-button>
        </el-form-item>
      </el-form>
       <!--<el-button type="primary" icon="delete"  @click="addUserlikes">添加点赞</el-button>-->
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号"  style="width: 60px;">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" v-if="false" label="用户ID" prop="onUserId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="用户名" prop="onUserName" style="width: 60px;"></el-table-column>
      <el-table-column  align="center" v-if="false" label="帖子ID" prop="postId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="帖子内容" prop="postContent" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="发帖用户名"  prop="userName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="点赞状态:1"  prop="likeState" style="width: 60px;">
        <template slot-scope="scope" >
          <p v-if="scope.row.likeState=='1'">
            已点赞
          </p>
          <p v-else-if="scope.row.likeState=='0'">
            未点赞
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="更新时间" prop="updateTime" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="220" v-if="hasPerm('comuserlike:add')">
        <template slot-scope="scope">
          <el-button type="danger" icon="edit" v-if="scope.row.likeState=='1'" @click="addlikes(scope.$index)">取消</el-button>
          <el-button type="primary" icon="edit" v-if="scope.row.likeState=='0'" @click="addlikes(scope.$index)">点赞</el-button>
          <!--<el-button type="danger" icon="delete" v-if="scope.row.likeState=='1'" @click="removeUser(scope.$index)">取消</el-button>-->
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
    <el-dialog
      title="关注粉丝"
      :visible.sync="likeDialogVisible"
      width="80%"
      v-if="likeDialogVisible">
      <el-row>
        <el-col>
          <el-button type="primary" icon="delete" :disabled="this.checkBoxData.length===0"
                     @click="addmany(checkBoxData)">选择修改
          </el-button>
        </el-col>
      </el-row>
      <el-table :data="dialogList"  v-loading.body="listLoading" @selection-change="changeFun" element-loading-text="拼命加载中" border fit
                highlight-current-row>
        <el-table-column type="selection"></el-table-column>
        <el-table-column align="center" label="序号" width="60">
          <template slot-scope="scope">
            <span v-text="getIndex(scope.$index)"></span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="点赞Id" v-if="false"  width="170">
          <template slot-scope="scope">
            <span>{{scope.row.likeId}}</span>
          </template>
        </el-table-column>
       <el-table-column align="center" label="用户名"   width="170">
          <template slot-scope="scope">
            <span>{{scope.row.onUserName}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="帖子用户Id" v-if="false"  width="170">
          <template slot-scope="scope">
            <span>{{scope.row.userId}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="帖子用户名"  width="170">
          <template slot-scope="scope">
            <span>{{scope.row.userName}}</span>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="MoreLikes.pageNum"
        :page-size="MoreLikes.pageRow"
        :total="totalCount"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-dialog>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        dialogList:[],
        ids:[],
        checkBoxData:[],//被选中的记录数据
        userName:"",//搜索框中信息
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          userName:this.userName,//获取输入框的值
          pageNum: 1,//页码
          pageRow: 50,//每页条数
        },
        MoreLikes:{
          /*onUserId:'',//获取输入框的值*/
          pageNum: 1,//页码
          pageRow: 50,//每页条数
        },
        roles: [],//角色列表
        dialogStatus: 'update',
        likeDialogVisible:false,
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
        },
        tempLike: {
          onUserId:'',
          postContent:'',
          onUserName: '',
          postId:'',
          userName: '',
          likeState: '',
          createTime: '',
          likeId: ''
        }
      }
    },
    created() {
      this.getList();
      if (this.hasPerm('user:add') || this.hasPerm('user:update')||this.hasPerm('comuserpost:update')||this.hasPerm('comuserfans:add')||this.hasPerm('comuserlike:add')||this.hasPerm('comuser:updatestate')||this.hasPerm('comuser:updatefans')) {
        this.getAllRoles();
      }
    },
    computed: {
      ...mapGetters([
        'likeId'
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
      changeFun(val) {
        console.log("changeFun",val)
        this.checkBoxData = val;
      },
      addmany(rows){
        //批量添加
        let _vue = this;
        _vue.$confirm('是否确认此操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          rows.forEach(element =>{
            _vue.ids.push(element);
            let param = {
              "likeId":_vue.ids,
            }
            _vue.api({
              url: "/comUserLike/addMoreLike",
              method: "post",
              data: param
            }).then(() => {
              _vue.getList();
              this.likeDialogVisible = false;
            }).catch(() => {
              _vue.$message.error("删除失败")
            })
          })
          console.log(this.postId);
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });
        });
      },
      addUserlikes(){

        this.api({
          url: "/comUserLike/getMoreLikesList",
          method: "get",
          params: this.MoreLikes,
        }).then(data => {
          this.dialogList = data.list;
          this.totalCount = data.totalCount;

        })
        this.likeDialogVisible = true;
      },
      selectUser(){
        this.getList();
      },
      getList() {
        //查询列表
        this.listQuery.userName = this.userName
        this.listLoading = true;
        this.api({
          url: "/comUser/listUserLike",
          method: "get",
          params: this.listQuery,
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
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
      addlikes($index) {

        let _vue = this;
        this.$confirm('确定点赞该帖子?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          _vue.api({
            url: "/comUserLike/addUserLikes",
            method: "post",
            data: user
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("修改失败")
          })
        })
      },
      removeUser($index) {
        let _vue = this;
        this.$confirm('确定取消点赞?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          _vue.api({
            url: "/comUserLike/updateUserLike",
            method: "post",
            data: user
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("修改失败")
          })
        })
      },
    }
  }
</script>
