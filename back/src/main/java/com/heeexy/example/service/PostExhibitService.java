package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: lover_tyn
 * @description: todo
 * @date: 2019/7/2 20:31
 * @version: 1.0
 */
public interface PostExhibitService {
    JSONObject getIndexPost(JSONObject jsonObject);

    JSONObject queryThePost(JSONObject jsonObject);

    JSONObject getThePost(JSONObject jsonObject);

    JSONObject getSortPost(JSONObject jsonObject);

    JSONObject addPost(JSONObject jsonObject);


}
