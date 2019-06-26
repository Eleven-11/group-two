package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CommentDao;
import com.heeexy.example.service.CommentService;
import com.heeexy.example.util.CommonUtil;
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
    public JSONObject getAllComment(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = commentDao.countAllComment();
        List<JSONObject> list = commentDao.getAllComment(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getComment(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = commentDao.countComment();
        List<JSONObject> list = commentDao.getComment(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getAllCommentByPostId(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = commentDao.countAllCommentByPostId(jsonObject);
        List<JSONObject> list = commentDao.getAllCommentByPostId(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getAllCommentByUserId(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = commentDao.countAllCommentByUserId(jsonObject);
        List<JSONObject> list = commentDao.getAllCommentByUserId(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getCommentByPostId(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = commentDao.countUnreadCommentByPostId(jsonObject);
        List<JSONObject> list = commentDao.getCommentByPostId(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getCommentByUserId(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = commentDao.countUnreadCommentByUserId(jsonObject);
        List<JSONObject> list = commentDao.getCommentByUserId(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getCommentById(int id) {
        return CommonUtil.successJson(commentDao.getCommentById(id));
    }

    @Override
    public JSONObject addComment(JSONObject jsonObject) {
        commentDao.addComment(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updateComment(JSONObject jsonObject) {
        commentDao.updateComment(jsonObject);
        return CommonUtil.successJson();
    }
}
