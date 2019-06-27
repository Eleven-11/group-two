package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.heeexy.example.service.MessageService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-06-26 17:20
 * @Version : 1.0
 **/
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/getMessage")
    public JSONObject getMessage(JSONObject jsonObject){
        return messageService.getMessage(jsonObject);
    }

    @GetMapping("/getChatByUserId")
    public  JSONObject getChatByUserId(JSONObject jsonObject){
        return messageService.getChatByUserId(jsonObject);
    }

    @GetMapping("/getMessageByUserId")
    public JSONObject getMessageByUserId(JSONObject jsonObject){
        return messageService.getMessageByUserId(jsonObject);
    }

    @GetMapping("/getMessageByMessageId")
    public JSONObject getMessageByMessageId(JSONObject jsonObject){
        return messageService.getMessageByMessageId(jsonObject);
    }

    @GetMapping("/getPictureByMessageId/{mid}")
    public JSONObject getPictureByMessageId(@PathVariable int mid){
        return messageService.getPictureByMessageId(mid);
    }

    @PostMapping("/addMessage")
    public JSONObject addMessage(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "fuid,tuid,content");
        return messageService.addMessage(requestJson);
    }

    @PostMapping("/addPicture")
    public JSONObject addPicture(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "mid,path");
        return messageService.addPicture(requestJson);
    }

    @PostMapping("/updateMessage")
    public JSONObject updateMessage(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "mid,status");
        return messageService.updateMessage(requestJson);
    }
}
