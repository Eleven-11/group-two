package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lover_tyn
 * @description: todo
 * @date: 2019/6/17 16:54
 * @version: 1.0
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        return postService.getAllPost(CommonUtil.request2Json(request));
    }

    @PostMapping("/updatePostState")
    public JSONObject updatePostState(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "deleteState,postId");
        return postService.updatePostState(requestJson);
    }

    @PostMapping("/getOnePost")
    public JSONObject getOnePost(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson,"postId");
        return postService.queryOnePost(requestJson);
    }

    @GetMapping("/getAllSorts")
    public JSONObject getAllSorts() {
        return postService.getAllSorts();
    }

    @PostMapping("/updatePost")
    public JSONObject updatePost(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "sortId,postContent, likeOff,collectOff,viewOff, postId,deleteTag,newTag");

        return postService.updatePost(requestJson);
    }

    @PostMapping("/updatePostTop")
    public JSONObject updatePostTop(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "isTop,postId");
        return postService.updatePostTop(requestJson);
    }

    @PostMapping("/getSomeTag")
    public JSONObject getSomeTag(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "need");
        return postService.getSomeTag(requestJson);
    }

}
