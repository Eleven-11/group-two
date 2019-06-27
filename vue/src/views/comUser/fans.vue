<template>
  <div class="app-container">
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号"  style="width: 60px;">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户ID" prop="onUserId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="用户名" prop="onUserName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="被关注用户ID" prop="userId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="被关注者用户名" prop="userName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="关注状态:1" prop="state" style="width: 60px;">
        <template slot-scope="scope">
        <p v-if="scope.row.state=='1'">
          已关注
        </p>
        <p v-else-if="scope.row.state=='0'">
          未关注
        </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="更新时间" prop="updateTime" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="primary" icon="delete" v-if="scope.row.state=='0'" @click="addfans(scope.$index)">关注</el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.state=='1'" @click="removeUser(scope.$index)">取消</el-button>
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
          pageNum: 1,//页码
          pageRow: 50,//每页条数
        },
        roles: [],//角色列表
        dialogStatus: 'update',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
        },
        tempFans: {
          onUserId:'',
          userId:'',
          onUserName: '',
          userName: '',
          state: '',
          fansId: ''
        }
      }
    },
    created() {
      this.getList();
    },
    computed: {
      ...mapGetters([
        'fansId'
      ])
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/comUser/listUserFans",
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
      addfans($index) {
        let _vue = this;
        this.$confirm('确定修改此用户关注?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          _vue.api({
            url: "/comUserFans/addFans",
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
        this.$confirm('确定修改此用户关注?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          _vue.api({
            url: "/comUserFans/updateUserFans",
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
