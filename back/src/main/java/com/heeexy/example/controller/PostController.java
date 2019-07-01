package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: lover_tyn
 * @description: 后台管理帖子
 * @date: 2019/6/17 16:54
 * @version: 1.0
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;
    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 11:06
     * @Param [request]
     * @return com.alibaba.fastjson.JSONObject
     */
    @GetMapping("/list")
    public JSONObject list(HttpServletRequest request) {
        return postService.getAllPost(CommonUtil.request2Json(request));
    }

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:11
     * @Param [requestJson]
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/updatePostState")
    public JSONObject updatePostState(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "deleteState,postId");
        return postService.updatePostState(requestJson);
    }

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:11
     * @Param [requestJson]
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/getOnePost")
    public JSONObject getOnePost(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson,"postId");
        return postService.queryOnePost(requestJson);
    }

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:12
     * @Param []
     * @return com.alibaba.fastjson.JSONObject
     */
    @GetMapping("/getAllSorts")
    public JSONObject getAllSorts() {
        return postService.getAllSorts();
    }

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:13
     * @Param [requestJson]
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/updatePost")
    public JSONObject updatePost(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "sortId,postContent, likeOff,collectOff,viewOff, postId,deleteTag,newTag,deleteImg");
        return postService.updatePost(requestJson);
    }


    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:13
     * @Param [requestJson]
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/updatePostTop")
    public JSONObject updatePostTop(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "isTop,postId");
        return postService.updatePostTop(requestJson);
    }


    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:13
     * @Param [requestJson]
     * @return com.alibaba.fastjson.JSONObject
     */
    @PostMapping("/getSomeTag")
    public JSONObject getSomeTag(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "need");
        return postService.getSomeTag(requestJson);
    }


    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:13
     * @Param []
     * @return com.alibaba.fastjson.JSONObject
     */
    @GetMapping("/getFirstTag")
    public JSONObject getFirstTag(){
        System.out.println("controller");
        return postService.getFirstTag();
    }

}
