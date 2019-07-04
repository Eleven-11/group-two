package com.heeexy.example.controller;

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
@RequestMapping("/information")
public class PostExhibitController {

    @Autowired
    private PostExhibitService postExhibitService;

//    {
//        "id":1,
//            "uid":2,
//            "pageNum":1
//    }

    @PostMapping("/news")
    public JSONObject news(@RequestBody JSONObject requestJson){

        return postExhibitService.getIndexPost(requestJson);
    }

//    {
//
//        "uid":2,
//            "tid":1
//
//    }

    @PostMapping("/detail")
    public JSONObject detail(@RequestBody JSONObject requestJson){

        return postExhibitService.queryThePost(requestJson);
    }


//    {
//        "uid":2,
//            "keyword":"菜",
//            "pageNum":1
//    }

    @PostMapping("/searchpost")
    public JSONObject searchpost(@RequestBody JSONObject requestJson){

        return postExhibitService.getThePost(requestJson);
    }

    @PostMapping("/addPost")
    public JSONObject addPost(@RequestBody JSONObject requesjJson){
        return  postExhibitService.addPost(requesjJson);
    }


}
