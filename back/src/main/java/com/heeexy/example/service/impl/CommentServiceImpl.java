package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CommentDao;
import com.heeexy.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-06-21 9:43
 * @Version : 1.0
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public List<JSONObject> getAllComment(JSONObject jsonObject) {
        return commentDao.getAllComment(jsonObject);
    }

    @Override
    public List<JSONObject> getComment(JSONObject jsonObject) {
        return commentDao.getComment(jsonObject);
    }

    @Override
    public List<JSONObject> getAllCommentByPostId(JSONObject jsonObject) {
        return getAllCommentByPostId(jsonObject);
    }

    @Override
    public List<JSONObject> getAllCommentByUserId(JSONObject jsonObject) {
        return getAllCommentByUserId(jsonObject);
    }

    @Override
    public List<JSONObject> getCommentByPostId(JSONObject jsonObject) {
        return commentDao.getCommentByPostId(jsonObject);
    }

    @Override
    public List<JSONObject> getCommentByUserId(JSONObject jsonObject) {
        return commentDao.getCommentByUserId(jsonObject);
    }

    @Override
    public JSONObject getCommentById(int id) {
        return commentDao.getCommentById(id);
    }

    @Override
    public JSONObject countAllComment(int id) {
        return commentDao.countAllComment(id);
    }

    @Override
    public JSONObject countUnreadComment(int id) {
        return commentDao.countUnreadComment(id);
    }

    @Override
    public int addComment(JSONObject jsonObject) {
        return commentDao.addComment(jsonObject);
    }

    @Override
    public int updateComment(JSONObject jsonObject) {
        return commentDao.updateComment(jsonObject);
    }
}
