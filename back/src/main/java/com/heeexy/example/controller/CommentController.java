package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.CommentService;
import com.heeexy.example.service.ModuleService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    //cqy
    @Autowired
    private ModuleService moduleService;

    /**
     * 获取评论列表
     */
    @GetMapping("getAllComment")
    public JSONObject getComment(HttpServletRequest request){
        return commentService.getComment(CommonUtil.request2Json(request));
    }

    /**
     * 获取某个用户的评论
     */
    @GetMapping("getCommentByUserId")
    public JSONObject getCommentByUserId(HttpServletRequest request){
        return commentService.getComment(CommonUtil.request2Json(request));
    }

    /**
     * 获取某个帖子的评论
     * @param request
     * @return
     */
    @GetMapping("getCommentByPostId")
    public JSONObject getCommentByPostId(HttpServletRequest request){
        return commentService.getComment(CommonUtil.request2Json(request));
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
        JSONObject jsonObject = commentService.updateComment( requestJson );
        //cqy写
        Object id = requestJson.get( "uid" );
        Object content = requestJson.get( "content" );
        requestJson.put( "userId",id );
        requestJson.put( "commentContent",content);
        moduleService.addMessageJ(requestJson);
        return jsonObject;
    }
}
