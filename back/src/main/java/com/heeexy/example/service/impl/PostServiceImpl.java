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
 * @description: todo
 * @date: 2019/6/18 10:50
 * @version: 1.0
 */
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDao postDao;
    @Override
    public JSONObject getAllPost(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = postDao.queryCountPost(jsonObject);
        List<JSONObject> list = postDao.getAllPost(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject updatePostState(JSONObject jsonObject) {
       postDao.updatePostState(jsonObject);
       return CommonUtil.successJson();
    }

    @Override
    public JSONObject queryOnePost(JSONObject jsonObject) {
        JSONObject thePost = postDao.queryOnePost(jsonObject);
        return CommonUtil.successJsonOne(thePost);
    }

    @Override
    public JSONObject getAllSorts() {
        List<JSONObject> posts = postDao.getAllSorts();
        return CommonUtil.successPage(posts);
    }

    @Override
    public JSONObject updatePost(JSONObject jsonObject) {
        postDao.updatePost(jsonObject);
        List<String> temp = (List<String>) jsonObject.get("newTag");
        if(temp!=null&&temp.size()!=0) {
            System.out.println("添加--------------------------------------------");
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

    @Override
    public JSONObject updatePostTop(JSONObject jsonObject) {
        postDao.updatePostTop(jsonObject);

        return CommonUtil.successJson();
    }

    @Override
    public JSONObject getSomeTag(JSONObject jsonObject) {
       List<JSONObject> someTag =  postDao.getSomeTag(jsonObject);
        return CommonUtil.successPage(someTag);
    }

    @Override
    public JSONObject getFirstTag() {
        List<JSONObject> firstTag = postDao.getFirstTag();

        return CommonUtil.successPage(firstTag);
    }
}
