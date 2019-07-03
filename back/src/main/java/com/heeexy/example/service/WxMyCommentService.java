package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 20:08 2019/6/20
 * @ Description：用户评论记录Service
 * @Version: 1.0
 */
public interface WxMyCommentService {

    /**
     * @methodsName: getMyCommentList
     * @description: 后台每个用户自己对帖子的评论记录
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject getMyCommentList(JSONObject jsonObject);

    /**
     * @methodsName: getMyCommentListById
     * @description: 前台用户自己对帖子的评论记录
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject getMyCommentListById(JSONObject jsonObject);
     /**
          * @methodsName: myselfComment
          * @description: 我的评论
          * @param:  JSONObject
          * @return:  SONObject
          */
    JSONObject myselfComment(JSONObject jsonObject);
}
