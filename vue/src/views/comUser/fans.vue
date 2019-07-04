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
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号"  style="width: 60px;">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" v-if="false" label="用户ID" prop="onUserId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="用户名" prop="onUserName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" v-if="false" label="被关注用户ID" prop="userId" style="width: 60px;"></el-table-column>
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
          <el-button type="primary" icon="delete"  @click="addUserfans(scope.row.onUserId)">添加关注</el-button>
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
    <el-dialog
      title="关注粉丝"
      :visible.sync="fansDialogVisible"
      width="80%"
      v-if="fansDialogVisible">
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
        <el-table-column align="center" label="关注Id" v-if="false" prop="userId" width="170">
          <template slot-scope="scope">
            <span>{{scope.row.fansId}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="用户Id" v-if="false" prop="userId" width="170">
          <template slot-scope="scope">
           <span>{{scope.row.userId}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="用户名" prop="userName" width="170">
         <template slot-scope="scope">
          <span>{{scope.row.userName}}</span>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="MoreFans.pageNum"
        :page-size="MoreFans.pageRow"
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
        MoreFans:{
          onUserId:'',//获取值
          pageNum: 1,//页码
          pageRow: 50,//每页条数
        },
        roles: [],//角色列表
        dialogStatus: 'update',
        fansDialogVisible:false,
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          add:'添加',
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
              "fansId":_vue.ids,
            }
            _vue.api({
              url: "/comUserFans/addMoreFans",
              method: "post",
              data: param
            }).then(() => {
              _vue.getList();
              this.fansDialogVisible = false;
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
      addUserfans(val){
        this.MoreFans.onUserId = val;
        this.api({
          url: "/comUserFans/getMoreFansList",
          method: "get",
          params: this.MoreFans,
        }).then(data => {
          this.dialogList = data.list;
          this.totalCount = data.totalCount;

        })
        this.fansDialogVisible = true;
      },
      selectUser(){
        this.getList();
      },
      getList() {
        //查询列表
        this.listQuery.userName = this.userName
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
