<template>
  <div class="app-container">
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="ID" width="60">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)">{{scope.row.id}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="pid" label="所在帖子ID" style="width: 60px;">
        <template slot-scope="scope">
          <el-button type="text" @click="showPostTable(scope.row.pid)">{{scope.row.pid}}</el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="fuid" label="发布用户" style="width: 60px;">
        <template slot-scope="scope">
          <el-button type="text" @click="showUserTable(scope.row.fuid)">{{scope.row.uname}}</el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="tcid" label="被回复评论ID" style="width: 60px;">
        <template slot-scope="scope">
          <el-button type="text" @click="showCommentTable(scope.row.tcid)" v-if="scope.row.tcid>0">{{scope.row.tcid}}</el-button>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="content" label="评论内容" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="发布时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.ctime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="当前状态" width="200">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status>0">已读</el-tag>
          <el-tag type="info" v-if="scope.row.status==0">未读</el-tag>
          <el-tag type="danger" v-if="scope.row.status<0">删除</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="danger" icon="edit" v-if="scope.row.status>-1" @click="warn(scope.$index)">删除并警告</el-button>
          <el-button type="danger" icon="edit" v-if="scope.row.status<0" disabled>删除并警告</el-button>
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
      title="该帖子内的评论"
      :visible.sync="postDialogVisible"
      width="80%"
      v-if="postDialogVisible"
    >
      <el-table :data="dialogList" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
                highlight-current-row>
        <el-table-column align="center" label="ID" width="60">
          <template slot-scope="scope">
            <span v-text="getIndex(scope.$index)">{{scope.row.id}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="uname" label="发布用户" style="width: 60px;">
          <template slot-scope="scope">
            <span>{{scope.row.uname}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="tcid" label="被回复评论ID" style="width: 60px;">
          <template slot-scope="scope">
            <el-button type="text" @click="showCommentTable(scope.row.tcid)" v-if="scope.row.tcid>0">{{scope.row.tcid}}</el-button>
            <span v-else>无</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="content" label="评论内容" style="width: 60px;"></el-table-column>
        <el-table-column align="center" label="发布时间" width="170">
          <template slot-scope="scope">
            <span>{{scope.row.ctime}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="当前状态" width="200">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status>0">已读</el-tag>
            <el-tag type="info" v-if="scope.row.status==0">未读</el-tag>
            <el-tag type="danger" v-if="scope.row.status<0">删除</el-tag>
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
      <span slot="footer" class="dialog-footer">
        <el-button @click="postDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="该用户的评论"
      :visible.sync="userDialogVisible"
      width="80%"
      v-if="userDialogVisible"
      >
      <el-table :data="dialogList" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
                highlight-current-row>
        <el-table-column align="center" label="ID" width="60">
          <template slot-scope="scope">
            <span v-text="getIndex(scope.$index)">{{scope.row.id}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="pid" label="所在帖子" style="width: 60px;"></el-table-column>
        <el-table-column align="center" prop="tcid" label="被回复评论ID" style="width: 60px;">
          <template slot-scope="scope">
            <el-button type="text" @click="showCommentTable(scope.row.tcid)" v-if="scope.row.tcid>0">{{scope.row.tcid}}</el-button>
            <span v-else>无</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="content" label="评论内容" style="width: 60px;"></el-table-column>
        <el-table-column align="center" label="发布时间" width="170">
          <template slot-scope="scope">
            <span>{{scope.row.ctime}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="当前状态" width="200">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.status>0">已读</el-tag>
            <el-tag type="info" v-if="scope.row.status==0">未读</el-tag>
            <el-tag type="danger" v-if="scope.row.status<0">删除</el-tag>
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
      <span slot="footer" class="dialog-footer">
        当前用户状态：
        <span v-if="!dialogList[0].ustate">正常</span>
        <span v-else>封禁</span>
        <el-button type="danger"  icon="edit" v-if="!dialogList[0].ustate" @click="changeUserState(dialogList[0].fuid, 0)">封禁</el-button>
        <el-button type="success" icon="edit" v-else @click="changeUserState(dialogList[0].fuid, 1)">解封</el-button>
        <el-button @click="userDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>


    <el-dialog title="被回复评论" :visible.sync="commentDialogVisible">
      <el-form class="small-space" :model="tempComment2" label-position="left" label-width="60px"
               style='width: 300px; margin-left:50px;'>
        <el-form-item label="评论id" label-width="100px">
          <span>{{tempComment2.id}}</span>
        </el-form-item>
        <el-form-item label="所在帖子id" label-width="100px">
          <span>{{tempComment2.pid}}</span>
        </el-form-item>
        <el-form-item label="发布用户" label-width="100px">
          <span>{{tempComment2.uname}}</span>
        </el-form-item>
        <el-form-item label="评论内容" label-width="100px">
          <span>{{tempComment2.content}}</span>
        </el-form-item>
        <el-form-item label="发布时间" label-width="100px">
          <span>{{tempComment2.ctime}}</span>
        </el-form-item>
        <el-form-item label="当前状态" label-width="100px">
          <el-tag type="success" v-if="tempComment2.status>0">已读</el-tag>
          <el-tag type="info" v-if="tempComment2.status==0">未读</el-tag>
          <el-tag type="danger" v-if="tempComment2.status<0">删除</el-tag>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="commentDialogVisible = false">关闭</el-button>
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
        userDialogVisible: false,
        postDialogVisible: false,
        commentDialogVisible: false,
        dialogTotalCount: 0,
        dialogList: [],
        dialogListQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
          name: '',
          pid: '',
          uid: ''
        },
        tempComment: {
          cid: "",
          status: "",
          uid: "",
          content: ""
        },
        tempComment2: {
          id: "",
          pid: "",
          fuid: "",
          tcid: "",
          content: "",
          ctime: "",
          status: "",
          uname: ""
        },
        tempUser: {
          userId : "",
          userState: ""
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
          url: "/comment/getAllComment",
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
      warn ($index) {
        this.tempComment.cid = this.list[$index].id;
        this.tempComment.status = -1;
        this.tempComment.uid = this.list[$index].fuid;
        this.tempComment.content = this.list[$index].content;

        //修改评论状态
        this.api({
          url: "/comment/updateComment",
          method: "post",
          data: this.tempComment
        }).then(() => {
          this.$message.success("操作成功");
          this.getList();
          this.dialogFormVisible = false;
          this.userDialogVisible = false;
          this.postDialogVisible = false;
          this.commentDialogVisible = false;
        })
      },
      changeUserState(id, val) {
        this.tempUser.userId = id;
        this.tempUser.userState = val;
        //修改用户状态
        this.api({
          url: "/comUser/updateUserState",
          method: "post",
          data: this.tempUser
        }).then(() => {
          this.$message.success("修改成功");
          this.getList();
          this.dialogFormVisible = false;
          this.userDialogVisible = false;
          this.postDialogVisible = false;
          this.commentDialogVisible = false;
        })
      },
      showUserTable(val){
        this.dialogListQuery.uid = val;
        this.api({
          url: "/comment/getCommentByUserId",
          method: "get",
          params: this.dialogListQuery
        }).then(data => {
          this.dialogList = data.list;
          this.dialogTotalCount = data.totalCount;
        })
        this.userDialogVisible = true;
      },
      showPostTable(val){
        this.dialogListQuery.pid = val;
        this.api({
          url: "/comment/getCommentByPostId",
          method: "get",
          params: this.dialogListQuery
        }).then(data => {
          this.dialogList = data.list;
          this.dialogTotalCount = data.totalCount;
        })
        this.postDialogVisible = true;
      },
      showCommentTable(val){
        this.api({
          url: "/comment/getCommentById/"+val,
          method: "get",
        }).then(data => {
          this.tempComment2 = data;
        })
        this.commentDialogVisible = true;
      }
    }
  }
</script>
