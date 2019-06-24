<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-input style="width: 300px" placeholder="请输入内容" v-model="tempUser.input" clearable ></el-input>
          <el-button type="primary" icon="el-icon-search" @click="getList" >搜索</el-button>
          <el-button type="primary" icon="plus" v-if="hasPerm('user:add')" @click="showCreate">添加</el-button>
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
      <el-table-column align="center" label="帖子编号" prop="postId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="用户编号" prop="userId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="用户名" prop="userName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" width="170" sortable></el-table-column>
      <el-table-column align="center" label="最近修改时间" prop="updateTime" width="170" sortable></el-table-column>
      <el-table-column align="center" label="是否收藏收藏"style="width: 60px;">
        <template slot-scope="scope">
          <el-tag  v-if="scope.row.display==1" type="success">收藏</el-tag>
          <el-tag  v-if="scope.row.display==0" type="primary">不收藏</el-tag>
        </template>

      </el-table-column>
      <el-table-column align="center" label="管理" width="220" v-if="hasPerm('user:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="delete" v-if="scope.row.userId!=userId "
                     @click="removeUser(scope.$index)">更改收藏状态
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
      <el-form class="small-space" :model="tempUser" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="帖子编号">
          <el-input type="text" v-model="tempUser.postId">
          </el-input>
        </el-form-item>
        <el-form-item label="用户编号">
          <el-input type="text" v-model="tempUser.userId">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createUser">创 建</el-button>
        <el-button type="primary" v-else @click="updateUser">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          input: '',
          pageNum: 1,//页码
          pageRow: 50,//每页条数
        },
        roles: [],//角色列表
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建'
        },
        tempUser: {
          postCollectId:'',
          postId: '',
          userId:'',
          display:'',
          username:'',
          input: ''
        }
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
          url: "/postcollect/getAllPostCollect",
          method: "get"
        }).then(data => {
          this.roles = data.list;
        })
      },
      getList() {
        //查询列表
        this.listQuery.input = this.tempUser.input;
        this.listLoading = true;
        this.api({
          url: "/postcollect/list",
          method: "get",
          params: this.listQuery
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
      showCreate() {
        //显示新增对话框
        this.tempUser.postCollectId= "";
        this.tempUser.postId = "";
        this.tempUser.userId= "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        let postcategorie = this.list[$index];
        this.tempUser.postId = postcategorie.postId;
        this.tempUser.userId = postcategorie.userId;
        this.tempUser.display = postcategorie.display;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },showSearch($index){
        this.tempUser.businessSubwayName = postcategorie.businessSubwayName;
        this.dialogStatus = "search"
        this.dialogFormVisible = true
      },
      createUser() {
        //添加新用户
        this.api({
          url: "/postcollect/addPostCollect",
          method: "post",
          data: this.tempUser
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },

      removeUser($index) {
        let _vue = this;
        this.$confirm('确定修改收藏值?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          if(user.display == 1){
            user.display = 0;
          }else{
            user.display = 1;
          }
          _vue.api({
            url: "/postcollect/updatePostCollectDisplay",
            method: "post",
            data: user
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("修改成功")
          })
        })
      },
    }
  }
</script>


