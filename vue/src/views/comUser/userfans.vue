<template>
  <div class="app-container">

    <p>===========================关注人==================</p>
    <div v-for="item3 in onUserfans">
      <p>关注ID：{{ item3.fansId }}</p>
      <p>关注人：{{ item3.userName }}</p>
  </div>
    <p>-----------------------点赞-------------------------</p>
    <div v-for="item4 in onUserLike">
      <p>点赞ID：{{ item4.likeId }}</p>
      <p>帖子ID：{{ item4.postId }}</p>
      <p>点赞内容：{{ item4.postContent }}</p>
      <p>发帖人：{{ item4.userName }}</p>
    </div>
    <p>-------------------------浏览-----------------------</p>
    <div v-for="item5 in onUserBrowse">
      <p>浏览ID：{{ item5.browseId }}</p>
      <p>浏览帖子ID：{{ item5.postId }}</p>
      <p>浏览帖子ID：{{ item5.postContent }}</p>
      <p>浏览时间：{{ item5.createTime }}</p>
    </div>
    <p>-----------------------帖子-------------------------</p>
    <div v-for="item6 in onUserPost">
      <p>帖子ID：{{ item6.postId }}</p>
      <p>帖子内容：{{ item6.postContent }}</p>
      <p>帖子创建时间：{{ item6.createTime }}</p>
    </div>
    <p>------------------------评论------------------------</p>
    <div v-for="item7 in onUsercomment">
      <p>评论ID：{{ item7.commentId }}</p>
      <p>评论的帖子ID：{{ item7.postId }}</p>
      <p>帖子内容：{{ item7.postContent }}</p>
      <p>评论者：{{ item7.onUserName }}</p>
      <div v-if="item7.toCommentId !=0">
        <p>被评论者：{{ item7.toCommentUserName }}</p>
      </div>
      <p>评论内容：{{ item7.commentContent }}</p>
      <p>评论时间：{{ item7.createTime }}</p>
    </div>
    <p>==============粉丝============</p>
    <div v-for="item1 in userFansNumber">
     <p>关注数：{{ item1.fansNumber}}</p>
   </div>
    <div v-for="item1 in userLikeNumber">
    <p>点赞数：{{ item1.likeNumber}}</p>
  </div>
    <div v-for="item1 in FansNumber">
      <p>粉丝数：{{ item1.fansToal}}</p>
    </div>
    <div v-for="item1 in userpostNumber">
      <p>帖子数：{{ item1.postNumber}}</p>
    </div>
    <div v-for="item1 in MyCollectPostNumber">
      <p>收藏数：{{ item1.collectNumber}}</p>
    </div>
    <p>============收藏帖子=============</p>
    <div v-for="item1 in UserCollectPost">
      <p>收藏帖子内容：{{ item1.postContent}}</p>
      <p>收藏帖子时间：{{ item1.createTime}}</p>
    </div>
  <p>==============点赞数============</p>
    <!--<div v-for="item2 in userLikeNumber">
      <p>点赞数量：{{ item2.likeNumber}}</p>
    </div>-->
    </div>
</template>

<script>
    export default {
      data(){
        return {
          onUserfans:[],
          onUsercomment:[],
          onUserLike:[],
          onUserBrowse:[],
          onUserPost:[],
          FansNumber:[],
          userFansNumber:[],
          userLikeNumber:[],
          userpostNumber:[],
          UserCollectPost:[],
          MyCollectPostNumber:[],
        }
      },
      created() {
        this.getUserFans();
        this.getUserLike();
        this.getUserPost();
        this.getUserbrowse();
        this.getUsercomment();
        this.getUserFansNumber();
        this.getMyLikeNumber();
        this.getFansNumber();
        this.getpostNumber();
      this.UserCollectPostNumber();
      this. getUserCollectPost();
      },
      methods: {
        /*粉丝列表*/
        getUserFans() {
            let param1 = {
              "onUserId":this.$route.query.onUserId,
            }
            console.log(param1);
            this.api({
              url: "/api/comUserFansWeb/listUserFans",
              method: "get",
              params: param1
            }).then(data => {
              this.onUserfans = data.list;
            })
        },
        /*点赞记录*/
        getUserLike() {
          let param2= {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param2);
          this.api({
            url: "/api/comUserLikeWeb/listUserLike",
            method: "get",
            params: param2
          }).then(data => {
            this.onUserLike = data.list;
          })
        },
        /*帖子记录*/
        getUserPost() {
          let param3 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param3);
          this.api({
            url: "/api/comUserPostWeb/listUserPostById",
            method: "get",
            params: param3
          }).then(data => {
            this.onUserPost = data.list;
          })
        },
        /*收藏帖子记录*/
        getUserCollectPost() {
          let param = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param);
          this.api({
            url: "/api/comUserPostWeb/ListUserCollectPost",
            method: "get",
            params: param
          }).then(data => {
            this.UserCollectPost = data.list;
          })
        },
        /*收藏帖子记录计数*/
        UserCollectPostNumber() {
          let param = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param);
          this.api({
            url: "/api/comUserPostWeb/countMyCollectPostByUserId",
            method: "get",
            params: param
          }).then(data => {
            this.MyCollectPostNumber = data.list;
          })
        },
        /*浏览记录*/
        getUserbrowse() {
          let param4 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param4);
          this.api({
            url: "/api/comUserBrowseWeb/listBrowseByUser",
            method: "get",
            params: param4
          }).then(data => {
            this.onUserBrowse = data.list;
          })
        },
        /*评论记录*/
        getUsercomment() {
          let param5 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param5);
          this.api({
            url: "/api/comUserCommentWeb/listUserCommentByUserId",
            method: "get",
            params: param5
          }).then(data => {
            this.onUsercomment = data.list;
          })
        },
        /*点赞数*/
        getMyLikeNumber() {
          let param6 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param6);
          this.api({
            url: "/api/comUserLikeWeb/userLikeNumber",
            method: "get",
            params: param6
          }).then(data => {


            this.userLikeNumber = data.list;
          })
        },
        /*帖子数*/
        getpostNumber() {
          let param9= {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param9);
          this.api({
            url: "/api/comUserPostWeb/countMyPostNumber",
            method: "get",
            params: param9
          }).then(data => {
            this.userpostNumber = data.list;
          })
        },
        /*关注数*/
        getUserFansNumber() {
          let param7 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param7);
          this.api({
            url: "/api/comUserFansWeb/userFansNumber",
            method: "get",
            params: param7
          }).then(data => {
            this.userFansNumber = data.list;
          })
        },
        /*粉丝数*/
        getFansNumber() {
          let param8 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param8);
          this.api({
            url: "/api/comUserWeb/getUserFans",
            method: "get",
            params: param8
          }).then(data => {
            this.FansNumber = data.list;
          })
        },
      }
    }
</script>

<style scoped>

</style>
