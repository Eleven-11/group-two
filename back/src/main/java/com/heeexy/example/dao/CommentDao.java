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
    List<JSONObject> getComment(JSONObject jsonObject);

    /**
     * 获取评论数
     */
    int countComment(JSONObject jsonObject);

    /**
     * 根据id获取评论
     */
    JSONObject getCommentById(int id);

    /**
     * 新增评论
     */
    int addComment(JSONObject jsonObject);

    /**
     * 变更评论显示状态  0：未读 1：已读 -1：删除
     */
    int updateComment(JSONObject jsonObject);

}
