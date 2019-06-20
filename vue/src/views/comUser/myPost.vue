<template>
  <div class="app-container">
<el-row>
  <el-col>
    <el-button type="primary" icon="delete" :disabled="this.checkBoxData.length===0"
               @click="deletePost(checkBoxData)">选择删除
    </el-button>
  </el-col>
</el-row>
    <el-table :data="list" v-loading.body="listLoading"  @selection-change="changeFun" element-loading-text="拼命加载中" border fit
              highlight-current-row >
      <el-table-column type="selection"></el-table-column>
      <el-table-column align="center" label="序号"  style="width: 60px;">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="用户ID" prop="onUserId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="帖子ID" prop="postId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="帖子状态" prop="myPostState" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="danger" icon="delete" v-if="scope.row.myPostId!=myPostId "
                     @click="removeUser(scope.$index)">删除
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
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        tableData:[],
        checkBoxData:[],//被选中的记录数据
        ids:[],//批量删除id
        onUserId:[],
        postId:[],
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
        tempMyPost: {
          onUserId: '',
          postId:'',
          myPostState:'',
          createTime: '',
          myPostId: ''
        }
      }
    },
    created() {
      this.getList();
    },
    computed: {
      ...mapGetters([
        'myPostId'
      ])
    },
    methods: {
      deletePost(rows){
        //批量删除
          let _vue = this;
        _vue.$confirm('是否确认此操作?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            rows.forEach(element =>{
              _vue.ids.push(element.myPostId)
              let param = {
                "myPostId":_vue.ids
              }
              _vue.api({

                url: "/comUserPost/updateUserPost",
                method: "post",
                data: param
              }).then(() => {
                _vue.getList()
              }).catch(() => {
                _vue.$message.error("删除失败")
              })

            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消'
            });
          });
      },
      changeFun(val) {
        console.log("changeFun",val)
        this.checkBoxData = val;
      },
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/comUser/listUserPost",
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
      selectUser(){

        this.getList();
      },
      showUpdate($index) {
        let user = this.list[$index];

        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      updateUser() {
        //修改用户信息
        let _vue = this;
        this.api({
          url: "/comUser/updateUserPost",
          method: "post",
          data: this.tempFans
        }).then(() => {
          let msg = "修改成功";
          this.dialogFormVisible = false
          if (this.fansId === this.tempFans.fansId) {
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
      removeUser($index) {
        let _vue = this;
        this.$confirm('确定删除此帖子?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          if (user.myPostState == 0)
          {
            user.myPostState =1;
          }else {
            user.myPostState =0;
          }

          _vue.api({
            url: "/comUserPost/updateUserPost",
            method: "post",
            data: user
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
    }
  }
</script>
