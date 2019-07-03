package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import javax.swing.*;
import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 20:07 2019/6/20
 * @ Description：用户评论记录
 * @Version: 1.0
 */
public interface WxMyCommentDao {


     /**
          * @methodsName: getMyCommentList
          * @description: 后台每个用户自己对帖子的评论记录
          * @param:  JSONObject
          * @return: List<JSONObject>
          */
    List<JSONObject> getMyCommentList(JSONObject jsonObject);
     /**
          * @methodsName: countMyComment
          * @description: 后台计数
          * @param:  JSONObject
          * @return: int
          */
    int countMyComment(JSONObject jsonObject);
     /**
          * @methodsName: getMyCommentListById
          * @description: 前台用户自己对帖子的评论记录
          * @param:  JSONObject
          * @return: List<JSONObject>
          */
    List<JSONObject> getMyCommentListById(JSONObject jsonObject);
    /**
     * @methodsName: myselfComment
     * @description: 我的评论
     * @param:  JSONObject
     * @return:  List<JSONObject>
     */
    List<JSONObject> myselfComment(JSONObject jsonObject);
}
