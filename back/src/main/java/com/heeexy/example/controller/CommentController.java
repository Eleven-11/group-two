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

    @GetMapping("getAllComment")
    public List<JSONObject> getAllComment(HttpServletRequest request){
        return commentService.getAllComment(CommonUtil.request2Json(request));
    }

    @GetMapping("getComment")
    public List<JSONObject> getComment(HttpServletRequest request){
        return commentService.getComment(CommonUtil.request2Json(request));
    }

    @GetMapping("getAllCommentByUserId")
    public List<JSONObject> getAllCommentByUserId(HttpServletRequest request){
        return commentService.getAllCommentByUserId(CommonUtil.request2Json(request));
    }

    @GetMapping("getAllCommentByPostId")
    public List<JSONObject> getAllCommentByPostId(HttpServletRequest request){
        return commentService.getAllCommentByPostId(CommonUtil.request2Json(request));
    }

    @GetMapping("getCommentByUserId")
    public  List<JSONObject> getCommentByUserId(HttpServletRequest request){
        return commentService.getCommentByUserId(CommonUtil.request2Json(request));
    }

    @GetMapping("getCommentByPostId")
    public List<JSONObject> getCommentByPostId(HttpServletRequest request){
        return commentService.getCommentByPostId(CommonUtil.request2Json(request));
    }

    @GetMapping("getCommentById/{id}")
    public JSONObject getCommentById(@PathVariable int id){
        return commentService.getCommentById(id);
    }

    @GetMapping("countAllComment/{id}")
    public JSONObject countAllComment(@PathVariable int id){
        return commentService.countAllComment(id);
    }

    @GetMapping("countUnreadComment/{id}")
    public JSONObject countUnreadComment(@PathVariable int id){
        return  commentService.countUnreadComment(id);
    }

    @PostMapping("addComment")
    public int addComment(HttpServletRequest request){
        return commentService.addComment(CommonUtil.request2Json(request));
    }

    @PostMapping("updateComment")
    public int updateComment(HttpServletRequest request){
        return commentService.updateComment(CommonUtil.request2Json(request));
    }
}
