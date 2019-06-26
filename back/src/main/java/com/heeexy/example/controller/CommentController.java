package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.CommentService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author : 林成
 * @Description : 评论相关Controller
 * @Date : 2019-06-21 10:01
 * @Version : 1.0
 **/
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 获取所有评论
     */
    @GetMapping("getAllComment")
    public JSONObject getAllComment(HttpServletRequest request){
        return commentService.getAllComment(CommonUtil.request2Json(request));
    }

    /**
     * 获取可见评论
     */
    @GetMapping("getComment")
    public JSONObject getComment(HttpServletRequest request){
        return commentService.getComment(CommonUtil.request2Json(request));
    }

    /**
     * 获取某个用户的所有评论
     */
    @GetMapping("getAllCommentByUserId")
    public JSONObject getAllCommentByUserId(HttpServletRequest request){
        return commentService.getAllCommentByUserId(CommonUtil.request2Json(request));
    }

    /**
     * 获取某个帖子的所有评论
     */
    @GetMapping("getAllCommentByPostId")
    public JSONObject getAllCommentByPostId(HttpServletRequest request){
        return commentService.getAllCommentByPostId(CommonUtil.request2Json(request));
    }

    /**
     * 获取某个用户的可见评论
     */
    @GetMapping("getCommentByUserId")
    public  JSONObject getCommentByUserId(HttpServletRequest request){
        return commentService.getCommentByUserId(CommonUtil.request2Json(request));
    }

    /**
     * 获取某个帖子的可见评论
     */
    @GetMapping("getCommentByPostId")
    public JSONObject getCommentByPostId(HttpServletRequest request){
        return commentService.getCommentByPostId(CommonUtil.request2Json(request));
    }

    /**
     * 根据id获取一条评论
     */
    @GetMapping("getCommentById/{id}")
    public JSONObject getCommentById(@PathVariable int id){
        return commentService.getCommentById(id);
    }

    /**
     * 添加一条评论
     */
    @PostMapping("addComment")
    public JSONObject addComment(HttpServletRequest request){
        return commentService.addComment(CommonUtil.request2Json(request));
    }

    /**
     * 修改评论
     */
    @PostMapping("updateComment")
    public JSONObject updateComment(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "id,status");
        return commentService.updateComment(requestJson);
    }
}
