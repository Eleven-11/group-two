package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-06-21 9:42
 * @Version : 1.0
 **/
public interface CommentService {
    /**
     * 获取全部评论
     */
    JSONObject getAllComment(JSONObject jsonObject);

    /**
     * 根据状态获取评论  0：未读 1：已读 -1：删除
     */
    JSONObject getComment(JSONObject jsonObject);

    /**
     * 获取帖子里的所有评论
     */
    JSONObject getAllCommentByPostId(JSONObject jsonObject);

    /**
     * 获取用户发布的所有评论
     */
    JSONObject getAllCommentByUserId(JSONObject jsonObject);

    /**
     * 根据状态获取帖子里的评论  0：未读 1：已读 -1：删除
     */
    JSONObject getCommentByPostId(JSONObject jsonObject);

    /**
     * 根据状态获取用户发布的评论  0：未读 1：已读 -1：删除
     */
    JSONObject getCommentByUserId(JSONObject jsonObject);

    /**
     * 根据id获取评论
     */
    JSONObject getCommentById(int id);


    /**
     * 新增评论
     */
    JSONObject addComment(JSONObject jsonObject);

    /**
     * 变更评论显示状态  0：未读 1：已读 -1：删除
     */
    JSONObject updateComment(JSONObject jsonObject);
}
