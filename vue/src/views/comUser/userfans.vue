<template>
  <div class="app-container">
    <div v-for="item in onUserfans">
      <p>关注人：{{ item.userName }}</p>
  </div>
    <div v-for="item in onUserLike">
      <p>
        点赞内容：{{ item.postContent }}
      </p>

      <p>发帖人：{{ item.userName }}</p>
    </div>
    <div v-for="item in onUserBrowse">
      <p>浏览内容：{{ item.postContent }}</p>
    </div>
    <div v-for="item in onUserPost">
      <p>帖子内容：{{ item.postContent }}</p>
    </div>
    <div v-for="item in onUsercomment">
      <p>评论内容：{{ item.commentContent }}</p>
    </div>
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
        }

      },
      created() {
        this.getUserFans();
        this.getUserLike();
        this.getUserPost();
        this.getUserbrowse();
        this.getUsercomment();
      },
      methods: {
        getUserFans() {
            let param1 = {
              "onUserId":this.$route.query.onUserId,
            }
            console.log(param1);
            this.api({
              url: "/comUserFans/listUserFans",
              method: "get",
              params: param1
            }).then(data => {
              this.onUserfans = data.list;
            })
        },
        getUserLike() {
          let param2= {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param2);
          this.api({
            url: "/comUserLike/listUserLike",
            method: "get",
            params: param2
          }).then(data => {
            this.onUserLike = data.list;
          })
        },
        getUserPost() {
          let param3 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param3);
          this.api({
            url: "/comUserPost/listUserPostById",
            method: "get",
            params: param3
          }).then(data => {
            this.onUserPost = data.list;
          })
        },
        getUserbrowse() {
          let param4 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param4);
          this.api({
            url: "/comUserBrowse/listBrowseByUser",
            method: "get",
            params: param4
          }).then(data => {
            this.onUserBrowse = data.list;
          })
        },
        getUsercomment() {
          // this.listQuery.onUserId=this.$route.query.onUserId,
          // alert(this.listQuery.onUserId);
          // console.log(this.listQuery)
          let param5 = {
            "onUserId":this.$route.query.onUserId,
          }
          console.log(param5);
          this.api({
            url: "/comUserComment/listUserCommentByUserId",
            method: "get",
            params: param5
          }).then(data => {
            this.onUsercomment = data.list;
          })
        },
      }

    }
</script>

<style scoped>

</style>
