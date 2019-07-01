<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate" >添加
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
      <el-table-column align="center" prop="picture" label="图片" style="width: 414px;">
        <template slot-scope="scope">
          <img :src="scope.row.picture"  width="207" height="41" alt="加载中..."/>
          <!--<el-image-->
            <!--style="width: 300px; height: 240px"-->
            <!--:src="scope.row.picture">-->
            <!--<div slot="placeholder" class="image-slot">-->
              <!--加载中<span class="dot">...</span>-->
            <!--</div>-->
          <!--</el-image>-->
        </template>
      </el-table-column>
      <el-table-column align="center" prop="title" label="标题" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="link" label="链接" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="状态" width="200">
        <template slot-scope="scope">
          <el-button type="danger"  icon="edit" v-if="!scope.row.status" @click="changeStatus(scope.$index, 1)">禁用</el-button>
          <el-button type="success" icon="edit" v-if="scope.row.status" @click="changeStatus(scope.$index, 0)">启用</el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" style="width: 200px;" >
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" @click="deleteAds(scope.$index)">删除</el-button>
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
      <el-form class="small-space" :model="tempAds" label-position="left" label-width="60px"
               style='width: 300px; margin-left:50px;'>

        <el-form-item label="图片" v-if="!imgUrl">

          <el-upload
            action="api/ads/upload"
            :show-file-list="false"
            :on-success="handleSuccess"
            :on-error="handleError"
            :before-upload="handleBeforeUpload"
            :on-progress="handleProgress"
          >
            <el-button type="primary" size="medium">上传图片</el-button>
          </el-upload>
          <span>支持大小：414*82</span>
        </el-form-item>
        <el-form-item label="图片" v-if="imgUrl">
            <el-upload
              action="api/ads/upload"
              :show-file-list="false"
              :on-success="handleSuccess"
              :on-error="handleError"
              :before-upload="handleBeforeUpload"
              :on-progress="handleProgress"
            >
              <el-button type="primary" size="medium">修改图片</el-button>
            </el-upload>
            <span>支持大小：414*82</span>

        </el-form-item>
        <el-form-item label="标题">
          <el-input type="text" v-model="tempAds.title"></el-input>
        </el-form-item>
        <el-form-item label="链接">
          <el-input type="text" v-model="tempAds.link"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createAds">创 建</el-button>
        <el-button type="primary" v-else @click="updateAds">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
          name: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '创建广告'
        },
        tempAds: {
          id: "",
          picture: "",
          title: "",
          link: "",
          status: ""
        },
        imgUrl: "",
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/ads/getAds",
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
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempAds.picture = "";
        this.tempAds.title = "";
        this.tempAds.link = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempAds.id = this.list[$index].id;
        this.tempAds.picture = this.list[$index].picture;
        this.tempAds.title = this.list[$index].title;
        this.tempAds.link = this.list[$index].link;
        this.tempAds.status = this.list[$index].status;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createAds() {
        //保存新广告
        this.api({
          url: "/ads/addAds",
          method: "post",
          data: this.tempAds
        }).then(() => {
          this.imgUrl = false;
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateAds() {
        //修改广告
        this.api({
          url: "/ads/updateAds",
          method: "post",
          data: this.tempAds
        }).then(() => {
          this.imgUrl = false;
          this.getList();
          this.dialogFormVisible = false
        })
      },
      //变更启用状态
      changeStatus($index, val) {
        this.tempAds.id = this.list[$index].id;
        this.tempAds.picture = this.list[$index].picture;
        this.tempAds.title = this.list[$index].title;
        this.tempAds.link = this.list[$index].link;
        this.tempAds.status = val;
        this.api({
          url: "/ads/updateAds",
          method: "post",
          data: this.tempAds
        }).then(() => {
          this.$message.success("修改成功");
          this.getList();
          this.dialogFormVisible = false
        })
      },
      //图片上传成功
      handleSuccess(response, file, fileList) {
        this.$message.success(response.msg);
        this.tempAds.picture = response.url;
        this.imgUrl = true;
      },
      handleError() {
        this.$message.error("上传失败,请重新上传图片!");
      },
      handleBeforeUpload(file) {
        const isImage = file.type.includes("image");
        if (!isImage) {
          this.$message.error("上传文件类型必须是图片!");
        }
        const isLt5M = file.size / 1024 / 1024 < 5;
        if (!isLt5M) {
          this.$message.error("上传图片大小不能超过 5MB!");
        }
        return isImage && isLt5M;
      },
      handleProgress(event, file, fileList) {
        this.loading = true;  //  上传时执行loading事件
      },
      deleteAds($index){
        this.$confirm('此操作将永久删除该广告, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.tempAds.id = this.list[$index].id;
          this.api({
            url: "/ads/removeAdsById/"+this.tempAds.id,
            method: "get"
          }).then(() => {
            this.$message.success("删除成功");
            this.getList();
            this.dialogFormVisible = false
          })
        })
      }
    }

  }
</script>
