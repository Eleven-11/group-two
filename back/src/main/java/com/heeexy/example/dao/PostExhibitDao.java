package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author: lover_tyn
 * @description: todo
 * @date: 2019/7/2 20:34
 * @version: 1.0
 */
@Repository
public interface PostExhibitDao {
    List<JSONObject> getTopPost(JSONObject jsonObject);

    List<JSONObject> getNormalPost(JSONObject jsonObject);

    Integer queryTagType(String tagName);

    Integer isLike(JSONObject jsonObject);

    Integer isCollect(JSONObject jsonObject);

    JSONObject queryThePost(JSONObject jsonObject);

    List<JSONObject> getThePost(JSONObject jsonObject);

    Integer querySortId(JSONObject jsonObject);

    int addPost(JSONObject jsonObject);

    String queryCommentUserName(int con);



}
