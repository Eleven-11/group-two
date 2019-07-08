package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-06-26 16:44
 * @Version : 1.0
 **/
public interface MessageService {
    /**
     * 获取消息列表
     */
    JSONObject getMessage(JSONObject jsonObject);

    /**
     * 根据用户ID获取聊天列表
     */
    JSONObject getChatByUserId(JSONObject jsonObject);

    /**
     * 根据用户ID获取消息列表
     */
    JSONObject getMessageByChatId(JSONObject jsonObject);

    /**
     * 根据消息ID获取消息
     */
    JSONObject getMessageByMessageId(JSONObject jsonObject);

    /**
     * 获取一条消息的图片路径
     */
    JSONObject getPictureByMessageId(int id);

    /**
     * 添加消息
     */
    JSONObject addMessage(JSONObject jsonObject);

    /**
     * 添加图片
     */
    JSONObject addPicture(JSONObject jsonObject);

    /**
     * 改变消息状态
     */
    JSONObject updateMessage(JSONObject jsonObject);
}
