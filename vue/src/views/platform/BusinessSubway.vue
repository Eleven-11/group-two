<template>

  <div class="app-container">
    <div>
      <span style="font-size: 30px;">树形图</span>
      <el-tree
        ref="tree"
        :data="treeData"
        :props="defaultProps"
        accordion
        show-checkbox
        node-key="businessSubwayId"
        check-strictly="true"
        @node-click="handleNodeClick" >
      </el-tree>
    </div>


    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-input style="width: 300px" placeholder="请输入内容" v-model="tempUser.input" clearable ></el-input>
          <el-button type="primary" icon="el-icon-search" @click="getList" >搜索</el-button>
          <el-button type="primary" icon="plus" v-if="hasPerm('user:add')" @click="showCreate">添加</el-button>
        </el-form-item>
      </el-form>
      <!--<a href="/api/businesssubway/export"><button type="button" class="btn btn-primary">导出</button></a>-->
      <form class="form-horizontal" id="form_table" action="/api/businesssubway/import" enctype="multipart/form-data" method="post">
        <button type="submit" class="btn btn-primary" style="width: 80px;height: 40px;background-color: steelblue;color: #d3dce6">增量导入</button>
        <input  class="form-input" type="file" name="filename" ></input>
      </form>
      <form class="form-horizontal" id="form_tables" action="/api/businesssubway/imports" enctype="multipart/form-data" method="post">
        <button type="submit" class="btn btn-primary" style="width: 80px;height: 40px;background-color: steelblue;color: #d3dce6">覆盖导入</button>
        <input class="form-input" type="file" name="filename" ></input>
      </form>
    </div>

    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="帖子标签" prop="businessSubwayName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="上级的编号" prop="superiorId" style="width: 60px;">
        <template slot-scope="scope">
          <el-tag  v-if="scope.row.superiorId==0" style="margin-right: 3px;" type="danger">无上级</el-tag>
          <div v-if="scope.row.superiorId!=1" v-for="tags in list" style="text-align: center">
            <el-tag  v-if="scope.row.superiorId == tags.businessSubwayId" style="margin-right: 3px;" type="success" v-text="tags.businessSubwayName"></el-tag>
          </div>
        </template>

      </el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" width="170" sortable></el-table-column>
      <el-table-column align="center" label="最近修改时间" prop="updateTime" width="170" sortable></el-table-column>
      <el-table-column align="center" label="管理" width="220" v-if="hasPerm('user:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.userId!=userId "
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
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempUser" label-position="left" label-width="80px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="帖子标签" >
          <el-input type="text" v-model="tempUser.businessSubwayName">
          </el-input>
        </el-form-item>
        <el-form-item label="选择上级" >
          <!--<el-input type="text" v-model="tempUser.superiorId">-->
            <el-select v-model="tempUser.superiorId" placeholder="请选择">
              <el-option
                v-for="item in roles"
                :key="item.businessSubwayId"
                :label="item.businessSubwayName"
                :value="item.businessSubwayId">
              </el-option>
            </el-select>
          <!--</el-input>-->
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
        data : [],
        defaultProps: {
          children: 'children',
          label: 'businessSubwayName'
        },
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
          businessSubwayId:'',
          businessSubwayName: '',
          superiorId:'',
          display:'',
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
 /*     ...mapGetters([
        'userId'
      ]),*/
      treeData(){
        let cloneData = JSON.parse(JSON.stringify(this.data))    // 对源数据深度克隆
        return cloneData.filter(father=>{
          let branchArr = cloneData.filter(child=>father.businessSubwayId == child.superiorId)    //返回每一项的子级数组
          branchArr.length>0 ? father.children = branchArr : ''   //如果存在子级，则给父级添加一个children属性，并赋值
          return father.superiorId==0;      //返回第一层
        });
      }
    },
    methods: {
      getAllRoles() {
        this.api({
          url: "/businesssubway/getAllBusinessSubway",
          method: "get"
        }).then(data => {
          this.roles = data;
          console.log(data);
          // alert(this.roles);
        })
      },
      getList() {
        //查询列表
        this.listQuery.input = this.tempUser.input;
        this.listLoading = true;
        this.api({
          url: "/businesssubway/list",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
          this.data=data.list
          console.log(this.data);
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
      handleNodeClick(data){
        // console.log(data)
        console.log(this.treeData)
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
        this.tempUser.businessSubwayId= "";
        this.tempUser.businessSubwayName = "";
        this.tempUser.superiorId= "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true

      },

      showUpdate($index) {
        let postcategorie = this.list[$index];
        this.tempUser.businessSubwayId = postcategorie.businessSubwayId;
        this.tempUser.businessSubwayName = postcategorie.businessSubwayName;
        this.tempUser.superiorId = postcategorie.superiorId;
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
          url: "/businesssubway/import",
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
          url: "/businesssubway/updateBusinessSubway",
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



      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },


      removeUser($index) {
        let _vue = this;
        this.$confirm('确定删除此模块?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          user.display = 0;
          _vue.api({
            url: "/businesssubway/updateBusinessSubwayDisplay",
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


<style>
  .el-tree-node__label {
    font-size: 20px;
  }
</style>
