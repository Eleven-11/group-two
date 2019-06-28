<template>
  <div class="app-container">
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
        <template slot-scope="scope">
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
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" v-if="scope.row.likeState=='0'" @click="addlikes(scope.$index)">点赞</el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.likeState=='1'" @click="removeUser(scope.$index)">取消</el-button>
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
    },
    computed: {
      ...mapGetters([
        'likeId'
      ])
    },
    methods: {
      getList() {
        //查询列表
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
