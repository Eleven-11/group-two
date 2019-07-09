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
@RequestMapping("/api/information")
public class PostExhibitController {

    @Autowired
    private PostExhibitService postExhibitService;

//    {
//        "id":1,
//        "uid":2,
//        "pageNum":1
//    }

    @PostMapping("/news")
    public JSONObject news(@RequestBody JSONObject requestJson){

        return postExhibitService.getIndexPost(requestJson);
    }

//    {
//        "uid":2,
//        "tid":1
//    }

    @PostMapping("/detail")
    public JSONObject detail(@RequestBody JSONObject requestJson){

        return postExhibitService.queryThePost(requestJson);
    }


//    {
//          "uid":2,
//         "keyword":"菜",
//         "pageNum":1
//    }

    @PostMapping("/searchPost")
    public JSONObject searchPost(@RequestBody JSONObject requestJson){

        return postExhibitService.getThePost(requestJson);
    }

//    {
//            "type":2,
//            "uid":1,
//            "pageNum":1
//    }

    @PostMapping("/sortPost")
    public JSONObject sortPost(@RequestBody JSONObject requestJson){
        return postExhibitService.getSortPost(requestJson);
    }

//    {
//           "type":"转租",
//            "userid":1,
//            "content":"ssssssssssssssssssssss",
//            "address":"990909",
//            "telephone":1,
//            "pricelow":100,
//            "pricehigh":null,
//            "startdate":null,
//            "enddate":null,
//            "imglist":["88888888888888",
//            "777777777777777"],
//        "taglist":[{"smalltag":666,"bigtag":999},
//        {"smalltag":999,"bigtag":6666}]
//    }

    @PostMapping("/addPost")
    public JSONObject addPost(@RequestBody JSONObject requestJson){
        return  postExhibitService.addPost(requestJson);
    }


}
