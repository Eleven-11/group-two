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
      <el-table-column align="center" prop="picture" label="图片" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="title" label="标题" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="link" label="链接" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="状态" style="width: 60px;">
        <template slot-scope="scope">
          <span v-if="scope.row.status">启用</span>
          <span v-if="!scope.row.status">禁用</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="管理" width="200" >
        <template slot-scope="scope">
          <el-button type="danger"  icon="edit" v-if="scope.row.status" @click="changeStatus(scope.$index, 0)">禁用</el-button>
          <el-button type="success" icon="edit" v-if="!scope.row.status" @click="changeStatus(scope.$index, 1)">启用</el-button>
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
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
        <el-form-item label="图片">
          <el-upload
            action="api/ads/upload"
            :show-file-list="false"
            :on-success="handleSuccess"
            :on-error="handleError"
            :on-progress="handleProgress"
          >
            <el-button type="primary" size="medium">上传图片</el-button>
          </el-upload>

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
        }
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
          url: "/ads/getAllAds",
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
          this.getList();
          this.dialogFormVisible = false
        })
      },
      changeStatus($index, val) {
        this.tempAds.id = this.list[$index].id;
        this.tempAds.picture = this.list[$index].picture;
        this.tempAds.title = this.list[$index].title;
        this.tempAds.link = this.list[$index].link;
        this.tempAds.status = val;
        //修改广告
        this.api({
          url: "/ads/updateAds",
          method: "post",
          data: this.tempAds
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
    }
  }
</script>
