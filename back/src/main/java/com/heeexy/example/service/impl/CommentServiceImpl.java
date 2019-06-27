package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.CommentDao;
import com.heeexy.example.service.CommentService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 获取评论列表
     */
    @Override
    public JSONObject getComment(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = commentDao.countComment(jsonObject);
        List<JSONObject> list = commentDao.getComment(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 根据id获取评论
     */
    @Override
    public JSONObject getCommentById(int id) {
        return CommonUtil.successJson(commentDao.getCommentById(id));
    }

    /**
     * 新增评论
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addComment(JSONObject jsonObject) {
        commentDao.addComment(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 变更评论显示状态  0：未读 1：已读 -1：删除
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateComment(JSONObject jsonObject) {
        commentDao.updateComment(jsonObject);
        return CommonUtil.successJson();
    }
}
