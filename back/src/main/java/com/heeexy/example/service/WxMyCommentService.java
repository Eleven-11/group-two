package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

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
    /**
     * @methodsName: selectUserName
     * @description: 根据被评论idtoComnetId查找用户名
     * @param:  Integer
     * @return: String
     */
    String selectUserName(@Param("toComentId") String toComentId);
}
