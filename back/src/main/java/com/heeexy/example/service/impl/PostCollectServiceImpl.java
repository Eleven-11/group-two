package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PostCollectDao;
import com.heeexy.example.service.PostCollectService;
import com.heeexy.example.util.Calculating;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: chenqiangyong
 * @description:帖子收藏的/增删改查的实现
 * @date: 2019/6/20 10:52
 * @vevsion 1.0
 */

@Service
public class PostCollectServiceImpl implements PostCollectService {

//   ************************ 后台************************

    @Autowired
    private PostCollectDao postCollectDao;


    /**
     * 添加帖子收藏
     * @param jsonObject postId(帖子id),userId(用户id)
     * @return
     */
    @Override
    public JSONObject addPostCollect(JSONObject jsonObject) {
        int exist = postCollectDao.queryExistPostCollectPU( jsonObject );
        if( exist > 0 ){
            int dexist = postCollectDao.queryExistPostCollectDisplay( jsonObject );
            if( dexist == 1 ){
                postCollectDao.updatePostCollectDisplay( jsonObject );
                return  CommonUtil.successJson();
            }else if( dexist == 0 ){
                postCollectDao.updatePostCollectDisplayTwo( jsonObject );
                return  CommonUtil.successJson();
            }
        }
        postCollectDao.addPostCollect( jsonObject );
        return CommonUtil.successJson();
    }

    /**
     * 修改帖子收藏状态值
     * @param jsonObject  postCollectId(收藏id),display(状态值)
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject updatePostCollectDisplay(JSONObject jsonObject) {
        postCollectDao.updatePostCollectDisplay( jsonObject );
        return  CommonUtil.successJson();
    }

//    /**
//     * 修改帖子收藏
//     */
//    @Override
//    public JSONObject updatePostCollect(JSONObject jsonObject) {
//        postCollectDao.updatePostCollect( jsonObject );
//        return CommonUtil.successJson();
//    }
    /**
     * 查询所有的帖子收藏
     * 在添加/修改帖子收藏的时候要使用此方法
     * @return
     */
    @Override
    public JSONObject getAllPostCollect() {
        List<JSONObject> roles = postCollectDao.getAllPostCollect();
        return CommonUtil.successJson(roles);
    }
    /**
     * 查询帖子收藏列表
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject listPostCollect(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = postCollectDao.countPostCollect( jsonObject );
        List<JSONObject> list = postCollectDao.listPostCollect( jsonObject );
        return CommonUtil.successPage( jsonObject,list,count );
    }


    //   ************************ 小程序前台************************

    /**
     * 根据用户查询的收藏帖子
     * @param jsonObject  userId(用户id)
     * @return
     */
    @Override
    public JSONObject getAllPostCollectByUserId(JSONObject jsonObject) {
        Object userId = jsonObject.get( "userId" );
        List<JSONObject> roles = postCollectDao.getAllPostCollectByUserId(jsonObject);
        for (JSONObject role : roles) {
            role.put( "userIds", userId);
            Object postId = role.get("postId");
            List<JSONObject> allCommentByPostId = postCollectDao.getAllCommentByPostId(postId);
            List<JSONObject> allCommentByToCommentId = postCollectDao.getAllCommentByToCommentId(postId);
            for (JSONObject object : allCommentByPostId) {
                for (JSONObject jsonObject1 : allCommentByToCommentId) {
                    if(object.get("userName").equals( jsonObject1.get( "userName" ) )){
                        Object toCommentId = jsonObject1.get("toCommentId");
                        JSONObject jsonObject2 = postCollectDao.gettoCommentId( toCommentId );
                        Object userName1 = jsonObject2.get( "userName" );
                        String userName= userName1.toString();
                        userName=jsonObject1.get("userName").toString()+"回复了@"+userName;
                        object.put("userName",userName);
                    }
                }
                object.put("Commentsid",object.remove("commentId"));
                object.put("commentsuserid",object.remove("fromUserId"));
                object.put("commentsimg",object.remove("userPhoto"));
                object.put("commentsname",object.remove("userName"));
                object.put("commentstext",object.remove("commentContent"));
            }
            List<JSONObject> allPostImgByPostId = postCollectDao.getAllPostImgByPostId(postId);
            List<JSONObject> allLikeByPostId = postCollectDao.getAllLikeByPostId(postId);
            role.put( "comments",allCommentByPostId);
            role.put( "imgUrl",allPostImgByPostId);
            role.put( "good",allLikeByPostId);

            String likestate = postCollectDao.getlikeByUP(role);
            int likestates;
            if(likestate==null){
                likestates=0;
            }else {
                likestates=Integer.valueOf(likestate);
            }
            if(likestates==1){
                role.put( "likestate",true );
            }else {
                role.put( "likestate",false );
            }
            String collectionstate = postCollectDao.getPsotCollectByUP(role);
            int collectionstates;
            if(collectionstate==null){
                collectionstates=0;
            }else {
                collectionstates=Integer.valueOf(collectionstate);
            }
            if(collectionstates==1){
                role.put("collectionstate",true);
            }else{
                role.put("collectionstate",false);
            }
            role.remove( "userIds" );
            String seepeople = postCollectDao.getBrowseByP(postId);
            role.put( "seepeople",seepeople );

            role.put("uid",role.remove("userId"));
            role.put("tid",role.remove("postId"));
            role.put("avatarUrl",role.remove("userPhoto"));
            role.put("username",role.remove("userName"));
            role.put("types",role.remove("categoriesName"));
            role.put("desc",role.remove("postContent"));
            role.put("address",role.remove("postLocation"));
            role.put("time",role.remove("postTime"));

            Date postTime = role.getDate( "time" );
            Calculating calculating = new Calculating();
            String time = calculating.differentDaysByMillisecond( postTime );
            role.put( "time" ,time);
        }
        return CommonUtil.successJson(roles);
    }
    /**
     * 修改帖子收藏状态值
     * @param jsonObject  postId(帖子id),userId(用户id)
     * @return
     */
    @Override
    public JSONObject updatePostCollectDisplays(JSONObject jsonObject) {
        int presence = postCollectDao.queryExistPostCollectPostId( jsonObject );
        int presences = postCollectDao.queryExistPostCollectUserId( jsonObject );
        if(presence==1){
            if(presences==1){
                int exist = postCollectDao.queryExistPostCollectPU( jsonObject );
                if (exist!=0){
                    int exists = postCollectDao.queryExistPostCollectDisplay( jsonObject );
                    if (exists==0){
                        postCollectDao.updatePostCollectDisplayTwo( jsonObject );
                    }else if(exists==1){
                        postCollectDao.updatePostCollectDisplay( jsonObject );
                    }
                }else {
                    postCollectDao.addPostCollect( jsonObject );
                }
            }
        }
        return CommonUtil.successJson();
    }


}
