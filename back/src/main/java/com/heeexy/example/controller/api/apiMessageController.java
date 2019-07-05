package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-07-04 14:58
 * @Version : 1.0
 **/
@RestController
@RequestMapping("api/messageApi")
public class apiMessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("getChat")
    public JSONObject getChatByUserId(int uid){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", uid);
        return messageService.getChatByUserId(jsonObject);
    }

    @GetMapping("getMessage")
    public JSONObject getMessageByChatId(int cid, int uid){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uid", uid);
        jsonObject.put("cid", cid);
        return messageService.getMessageByChatId(jsonObject);
    }
}
