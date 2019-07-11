package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostExhibitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lover_tyn
 * @description: todo
 * @date: 2019/7/2 20:28
 * @version: 1.0
 */
@RestController
@RequestMapping("/api")
public class PostExhibitController {

    @Autowired
    private PostExhibitService postExhibitService;



    @PostMapping("/post/stickPost")
    public JSONObject news(@RequestBody JSONObject requestJson){

        return postExhibitService.getIndexPost(requestJson);
    }



    @PostMapping("/post/postDetail")
    public JSONObject detail(@RequestBody JSONObject requestJson){

        return postExhibitService.queryThePost(requestJson);
    }




    @PostMapping("/search/searchpost")
    public JSONObject searchPost(@RequestBody JSONObject requestJson){

        return postExhibitService.getThePost(requestJson);
    }



    @PostMapping("/post/typePost")
    public JSONObject sortPost(@RequestBody JSONObject requestJson){
        return postExhibitService.getSortPost(requestJson);
    }



    @PostMapping("/post/release")
    public JSONObject addPost(@RequestBody JSONObject requestJson){
        return  postExhibitService.addPost(requestJson);
    }


}
