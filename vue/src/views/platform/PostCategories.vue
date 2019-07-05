<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" v-if="hasPerm('user:add')" @click="showCreate">添加
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
      <el-table-column align="center" label="帖子类别" prop="categoriesName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="类别图标">
        <template slot-scope="scope">
          <img :src="scope.row.categoriesImg" width="70" height="70"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="类别的状态" prop="display" style="width: 60px;">
        <template slot-scope="scope">
          <el-tag  v-if="scope.row.display==1" type="success">显示</el-tag>
          <el-tag  v-if="scope.row.display==0" type="primary">不显示</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="创建时间" prop="createTime" width="170" sortable></el-table-column>
      <el-table-column align="center" label="最近修改时间" prop="updateTime" width="170" sortable></el-table-column>
      <el-table-column align="center" label="管理" width="220" v-if="hasPerm('user:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="warning" icon="delete" v-if="scope.row.userId!=userId "
                     @click="removeUser(scope.$index)">修改状态
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
        <el-form-item label="帖子类别" >
          <el-input type="text" v-model="tempUser.categoriesName">
          </el-input>
        </el-form-item>
        <el-form-item label="类别图标" >
          <el-upload
            ref="upload"
            class="avatar-uploader"
            action="/api/postcategorie/photoupload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <span>图标为80X80</span>
          <!--<el-upload-->
            <!--action="/api/postcategorie/upload"-->
            <!--list-type="picture-card"-->
            <!--:on-preview="handlePictureCardPreview"-->
            <!--:on-remove="handleRemove"-->
            <!--:on-success="handleAvatarSuccess"-->
            <!--:limit="imgLimit" >-->
            <!--<i class="el-icon-plus"></i>-->
          <!--</el-upload>-->
          <!--<el-dialog :visible.sync="dialogVisible">-->
            <!--<img width="100%" :src="dialogImageUrl" alt="">-->
          <!--</el-dialog>-->
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

        imgData:{
          desFilePath:""
        },
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          // imageUrl: '',
          pageNum: 1,//页码
          pageRow: 50,//每页条数
        },
        roles: [],//角色列表
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '新建类别'
        },
        tempUser: {
          categoriesId:'',
          categoriesName: '',
          categoriesImg:'',
          display:''
        },
        imageUrl: '',
        dialogImageUrl: '',
        dialogVisible: false,
        imgLimit: 1
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
          url: "/postcategorie/getAllPostCategorie",
          method: "get"
        }).then(data => {
          this.roles = data.list;
        })
      },
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
        // this.categoriesImg = this.imageUrl;
        this.tempUser.categoriesImg = this.imageUrl;
        // alert(this.tempUser.categoriesImg);


      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      // handleRemove(file, fileList) {
      //   this.api({
      //     url:"/postcategorie/delete",
      //     method:"post",
      //     data:this.imgData
      //
      //   })
      //   console.log(file, fileList);
      // },
/*      handleAvatarSuccess(response, file, fileList) {
        //response这个
        this.imgData.desFilePath = response.url;
        // console.log("传回的地址："+response.url)
      },*/

      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
      },

      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/postcategorie/list",
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
        this.tempUser.categoriesId= "";
        this.tempUser.categoriesName = "";
        this.tempUser.categoriesImg= "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
        this.$refs.upload.clearFiles()
        this.imageUrl='';
      },
      showUpdate($index) {
        let postcategorie = this.list[$index];
        this.tempUser.categoriesId = postcategorie.categoriesId;
        this.tempUser.categoriesName = postcategorie.categoriesName;
        this.tempUser.categoriesImg = postcategorie.categoriesImg;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
        this.$refs.upload.clearFiles();
        this.imageUrl=postcategorie.categoriesImg;
      },
      createUser() {
        //添加新用户
        this.api({
          url: "/postcategorie/addPostCategorie",
          method: "post",
          data: this.tempUser
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
          this.$refs.upload.clearFiles()
        })
      },
      updateUser() {
        //修改用户信息
        let _vue = this;
        this.api({
          url: "/postcategorie/updatePostCategorie",
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
      removeUser($index) {
        let _vue = this;
        this.$confirm('确定修改状态?', '提示', {
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
            url: "/postcategorie/updatePostCategorieDisplay",
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
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
