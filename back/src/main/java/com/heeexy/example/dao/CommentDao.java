package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : 林成
 * @Description : 评论Dao层
 * @Date : 2019-06-18 17:30
 * @Version : 1.0
 **/
@Repository
public interface CommentDao {
    /**
     * 获取全部评论
     */
    List<JSONObject> getAllComment(JSONObject jsonObject);

    /**
     * 根据状态获取评论  0：未读 1：已读 -1：删除
     */
    List<JSONObject> getComment(JSONObject jsonObject);

    /**
     * 获取帖子里的所有评论
     */
    List<JSONObject> getAllCommentByPostId(JSONObject jsonObject);

    /**
     * 获取用户发布的所有评论
     */
    List<JSONObject> getAllCommentByUserId(JSONObject jsonObject);

    /**
     * 根据状态获取帖子里的评论  0：未读 1：已读 -1：删除
     */
    List<JSONObject> getCommentByPostId(JSONObject jsonObject);

    /**
     * 根据状态获取用户发布的评论  0：未读 1：已读 -1：删除
     */
    List<JSONObject> getCommentByUserId(JSONObject jsonObject);

    /**
     * 获取所有评论数
     */
    int countAllComment();

    /**
     * 获取可见评论数
     */
    int countComment();

    /**
     * 根据id获取评论
     */
    JSONObject getCommentById(int id);

    /**
     * 根据帖子id获取所有评论数
     */
    int countAllCommentByPostId(JSONObject jsonObject);

    /**
     * 根据帖子id获取未读评论数
     */
    int countUnreadCommentByPostId(JSONObject jsonObject);

    /**
     * 根据用户id获取所有评论数
     */
    int countAllCommentByUserId(JSONObject jsonObject);

    /**
     * 根据用户id获取未读评论数
     */
    int countUnreadCommentByUserId(JSONObject jsonObject);

    /**
     * 新增评论
     */
    int addComment(JSONObject jsonObject);

    /**
     * 变更评论显示状态  0：未读 1：已读 -1：删除
     */
    int updateComment(JSONObject jsonObject);

}
