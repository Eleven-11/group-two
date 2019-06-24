<template>
<div class="app-container">
  <div class="filter-container">
    <el-form >
      <el-form-item label="用户名"  >
        <el-input type="text" v-model="userName" style="width: 200px;margin-right: 30px;">
        </el-input>
        <el-button type="primary"  icon="plus"  @click="selectUser">查询</el-button>
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
    <el-table-column align="center" label="用户UUID" prop="userUuid" style="width: 60px;"></el-table-column>
    <el-table-column label="头像" width="100">
      <template slot-scope="scope">
        <img :src="scope.row.userPhoto" width="80" height="80" class="head_pic"/>
      </template>
    </el-table-column>
   <!-- <el-table-column align="center" label="用户头像" prop="userPhoto" style="width: 60px;"></el-table-column>-->
    <el-table-column align="center" label="用户名" prop="userName" style="width: 60px;"></el-table-column>
    <el-table-column align="center" label="性别" prop="userSex" style="width: 60px;"></el-table-column>
    <el-table-column align="center" label="粉丝数" prop="userFans" style="width: 60px;"></el-table-column>
    <el-table-column align="center" label="虚拟粉丝" prop="userFansf" style="width: 60px;"></el-table-column>
    <el-table-column align="center" label="游客角色:1" prop="guest" style="width: 60px;"></el-table-column>
    <el-table-column align="center" label="封禁状态" prop="userState" style="width: 60px;"></el-table-column>
    <el-table-column align="center" label="授权时间" prop="firstTime" style="width: 60px;"></el-table-column>
    <el-table-column align="center" label="更新时间" prop="updateTime" style="width: 60px;"></el-table-column>
    <el-table-column align="center" label="管理" width="220">
      <template slot-scope="scope">
        <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
       <!-- <el-button type="danger" icon="delete"  v-if="scope.row.userId!=userId "@click="updateState(scope.$index)">封禁？
        </el-button>-->
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
    <!--  <el-form-item label="用户头像"  >
        <el-input type="text" v-model="tempUser.userPhoto" >
        </el-input>
      </el-form-item>
      <el-form-item label="用户名"  >
        <el-input type="text" v-model="tempUser.userName">
        </el-input>
      </el-form-item>
      <el-form-item label="性别"  >
        <el-input type="text" v-model="tempUser.userSex">
        </el-input>
      </el-form-item>-->
      <el-form-item label="粉丝数" required >
        <el-input type="text" v-model="tempUser.userFansf">
        </el-input>
      </el-form-item>
      <el-form-item label="封禁状态" required>
        <el-select v-model="tempUser.userState" placeholder="请选择">
          <el-option
            v-for="item in states"
            :key="item.Id"
            :label="item.closeName"
            :value="item.closeId">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" v-if="dialogStatus=='update'"  @click="updateUser">修 改</el-button>
    </div>
  </el-dialog>
</div>
</template>
<script>
  import {mapGetters} from 'vuex'

  export default {
    data() {
      return {
        userName:"",
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
        tempUser: {
          userUuid:'',
          userName: '',
          userPhoto: '',
          userSex: '',
          userFans: '',
          userFansf: '',
          userState: '',
          userId: ''
        }
      }
    },
    created() {
      this.getList();
      this.getAllState();
    },
    computed: {
      ...mapGetters([
        'userId'
      ])
    },
    methods: {
      getAllState() {
        this.api({
          url: "/comUser/getAllState",
          method: "get"
        }).then(data => {
          this.states = data.list;
        })
      },
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/comUser/listUser",
          method: "get",
          params: this.listQuery,
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
          this.listQuery.userName = this.userName
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
        this.tempUser.userName = user.userName;
        this.tempUser.userPhoto = user.userPhoto;
        this.tempUser.userSex = user.userSex;
        this.tempUser.userFans = user.userFans;
        this.tempUser.userFansf= user.userFansf;
        this.tempUser.userId= user.userId;
        this.tempUser.guest = user.guest;
        this.tempUser.userState = user.userState;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      updateUser() {
        //修改用户信息
        let _vue = this;
        this.api({
          url: "/comUser/updateUserFans",
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
      updateState($index) {
        let _vue = this;
        this.$confirm('确定做此操作?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          if (user.userState =="游客") {
            user.userState = 0;
          } else  {
            user.userState=1;
          }
          _vue.api({
            url: "/comUser/updateUserState",
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
