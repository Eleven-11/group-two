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
    </div>
<el-row>
  <el-col>
    <el-button type="primary" icon="delete" :disabled="this.checkBoxData.length===0"
               @click="deletePost(checkBoxData)">选择修改
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

      <el-table-column align="center" label="用户名" prop="onUserName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" v-if="false" label="帖子ID" prop="postId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="帖子内容" prop="postContent" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="帖子状态" prop="myPostState" style="width: 60px;">
        <template slot-scope="scope">
          <p v-if="scope.row.myPostState=='0'">
            未删除
          </p>
          <p v-else-if="scope.row.myPostState=='1'">
            已删除
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="danger"  v-if="scope.row.myPostState=='0'" icon="delete"@click="removeUser(scope.$index)">删除</el-button>
          <el-button type="primary"  v-if="scope.row.myPostState=='1'" icon="delete"@click="removeUser(scope.$index)">取消</el-button>
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
        userName:"",//搜索框中信息
        tableData:[],
        checkBoxData:[],//被选中的记录数据
        ids:[],
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          userName:this.userName,//获取输入框的值
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
          postContent:'',
          onUserName: '',
          postId:'',
          myPostState:'',
          createTime: '',
        }
      }
    },
    created() {
      this.getList();
    },
    computed: {
      ...mapGetters([
        'postId'
      ])
    },
    methods: {
      getList() {
        //查询列表
        this.listQuery.userName = this.userName;
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
      deletePost(rows){
        //批量删除
        let _vue = this;
        _vue.$confirm('是否确认此操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          rows.forEach(element =>{
            _vue.ids.push(element);
            let param = {
              "posts":_vue.ids,
            }
            _vue.api({
              url: "/comUserPost/updateUserPostMany",
              method: "post",
              data: param
            }).then(() => {
              _vue.getList()
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
      changeFun(val) {
        console.log("changeFun",val)
        this.checkBoxData = val;
      },
      removeUser($index) {
        let _vue = this;
        this.$confirm('确定删除此帖子?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
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
