<template>
  <div class="app-container">
   <!-- <el-row>
      <el-col>
        <el-button type="primary" icon="delete" :disabled="this.checkBoxData.length===0"
                   @click="deletePost(checkBoxData)">选择删除
        </el-button>
      </el-col>
    </el-row>-->
    <el-table :data="list" v-loading.body="listLoading"  @selection-change="changeFun" element-loading-text="拼命加载中" border fit
              highlight-current-row >
      <!--<el-table-column type="selection"></el-table-column>-->
      <el-table-column align="center" label="序号"  style="width: 60px;">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center"  label="用户名" prop="onUserName" style="width: 60px;"></el-table-column>
      <el-table-column align="center" v-if="false" label="帖子ID" prop="postId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="发帖内容" prop="postContent" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="被评论人" prop="toCommentId" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="评论内容" prop="commentContent" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="评论状态" prop="commentState" style="width: 60px;">
        <template slot-scope="scope">
          <p v-if="scope.row.commentState=='0'">
            未读
          </p>
          <p v-else-if="scope.row.commentState=='1'">
            已读
          </p>
          <p v-else-if="scope.row.commentState=='-1'">
            已删除
          </p>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" prop="createTime" style="width: 60px;"></el-table-column>
     <!-- <el-table-column align="center" label="管理" width="220">
        <template slot-scope="scope">
          <el-button type="danger" icon="delete" v-if="scope.row.commentId!=commentId "
                     @click="removeUser(scope.$index)">删除
          </el-button>
        </template>
      </el-table-column>-->



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
        tempMyComment: {
          onUserName: '',
          postId:'',
          postContent:'',
          commentContent:'',
          toCommentId:'',
          commentState:'',
          createTime: '',
          commentId: ''
        }
      }
    },
    created() {
      this.getList();
    },
    computed: {
      ...mapGetters([
        'commentId'
      ])
    },
    methods: {
      getList() {
        //查询列表
        this.listLoading = true;
        this.api({
          url: "/comUser/listUserComment",
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
    }
  }
</script>
