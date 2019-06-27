package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.MessageDao;
import com.heeexy.example.service.MessageService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-06-26 16:47
 * @Version : 1.0
 **/
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public JSONObject getMessage(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = messageDao.countMessage(jsonObject);
        List<JSONObject> list = messageDao.getMessage(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getChatByUserId(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = messageDao.countChatByUserId(jsonObject);
        List<JSONObject> list = messageDao.getChatByUserId(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getMessageByUserId(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = messageDao.countMessageByUserId(jsonObject);
        List<JSONObject> list = messageDao.getMessageByUserId(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getMessageByMessageId(JSONObject jsonObject) {
        JSONObject message = messageDao.getMessageByMessageId(jsonObject);
        return CommonUtil.successJson(message);
    }

    @Override
    public JSONObject getPictureByMessageId(int id) {
        List<JSONObject> list = messageDao.getPictureByMessageId(id);
        return CommonUtil.successJson(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addMessage(JSONObject jsonObject) {
        if(messageDao.countChat(jsonObject)==0){
            messageDao.addChat(jsonObject);
        }
        int cid = messageDao.getChat(jsonObject);
        jsonObject.put("cid", cid);
        messageDao.addMessage(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addPicture(JSONObject jsonObject) {
        messageDao.addPicture(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateMessage(JSONObject jsonObject) {
        messageDao.updateMessage(jsonObject);
        return CommonUtil.successJson();
    }
}
