package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PostDao;
import com.heeexy.example.service.PostService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lover_tyn
 * @description: 后台管理帖子
 * @date: 2019/6/18 10:50
 * @version: 1.0
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDao postDao;

    /**
     * @Author lover_tyn
     * @Description //获取所有帖子
     * @Date  2019/7/1 14:56
     * @Param [jsonObject]:theStart,theEnd,theOwnerName,theOthers
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject getAllPost(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = postDao.queryCountPost(jsonObject);
        List<JSONObject> list = postDao.getAllPost(jsonObject);
        System.out.println(count);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * @Author lover_tyn
     * @Description //删除帖子
     * @Date  2019/7/1 14:56
     * @Param [jsonObject]:postId,deleteState
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject updatePostState(JSONObject jsonObject) {
       postDao.updatePostState(jsonObject);
       return CommonUtil.successJson();
    }

    /**
     * @Author lover_tyn
     * @Description //查询帖子详情
     * @Date  2019/7/1 14:56
     * @Param [jsonObject]:postId
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject queryOnePost(JSONObject jsonObject) {
        JSONObject thePost = postDao.queryOnePost(jsonObject);
        return CommonUtil.successJsonOne(thePost);
    }

    /**
     * @Author lover_tyn
     * @Description //获取帖子分类列表
     * @Date  2019/7/1 14:55
     * @Param []
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject getAllSorts() {
        List<JSONObject> posts = postDao.getAllSorts();
        return CommonUtil.successPage(posts);
    }

    /**
     * @Author lover_tyn
     * @Description //更新帖子信息
     * @Date  2019/7/1 14:55
     * @Param [jsonObject]:sortId,postContent, likeOff,collectOff,viewOff, postId,deleteTag,newTag,deleteImg
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject updatePost(JSONObject jsonObject) {
        postDao.updatePost(jsonObject);
        List<String> temp = (List<String>) jsonObject.get("newTag");
        //判断是否有新增标签
        if(temp!=null&&temp.size()!=0) {
            List<JSONObject> newTags =  postDao.getNewTagId(jsonObject);
            for (JSONObject newTag : newTags) {
                JSONObject tag = new JSONObject();
                tag.put("postId",jsonObject.get("postId"));
                tag.put("tId",newTag.get("tId"));
                tag.put("fId",newTag.get("fId"));
                postDao.addPostTag(tag);
            }
        }
        List<String> temp1 = (List<String>) jsonObject.get("deleteTag");
        //判断是否有标签删除
        if(temp1!=null&&temp1.size()!=0) {
            List<Integer> deleteTags =  postDao.getDeleteTagId(jsonObject);
            for (Integer i : deleteTags) {
                JSONObject tag = new JSONObject();
                tag.put("postId",jsonObject.get("postId"));
                tag.put("tId",i);
                postDao.deleteTag(tag);
            }
        }
        List<String> temp2 = (List<String>) jsonObject.get("deleteImg");
        //判断是否有图片删除
        if(temp2!=null&&temp2.size()!=0){
            for (String s:temp2
                 ) {
                JSONObject tempImg = new JSONObject();
                tempImg.put("postId",jsonObject.get("postId"));
                tempImg.put("img",s);
                postDao.deleteImg(tempImg);

            }
        }
        return CommonUtil.successJson();
    }

    /**
     * @Author lover_tyn
     * @Description //置顶帖子
     * @Date  2019/7/1 14:55
     * @Param [jsonObject]：postId,is_top
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject updatePostTop(JSONObject jsonObject) {
        postDao.updatePostTop(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * @Author lover_tyn
     * @Description //获取标签
     * @Date  2019/7/1 14:55
     * @Param [jsonObject]:need
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject getSomeTag(JSONObject jsonObject) {
       List<JSONObject> someTag =  postDao.getSomeTag(jsonObject);
        return CommonUtil.successPage(someTag);
    }



    /**
     * @Author lover_tyn
     * @Description //获取第一级别的标签
     * @Date  2019/7/1 14:54
     * @Param []
     * @return com.alibaba.fastjson.JSONObject
     */
    @Override
    public JSONObject getFirstTag() {
        List<JSONObject> firstTag = postDao.getFirstTag();
        return CommonUtil.successPage(firstTag);
    }


}
