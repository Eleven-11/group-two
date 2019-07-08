package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-06-26 11:29
 * @Version : 1.0
 **/
@Repository
public interface MessageDao {

    /**
     * 获取消息列表
     */
    List<JSONObject> getMessage(JSONObject jsonObject);

    /**
     * 获取消息条数
     */
    int countMessage(JSONObject jsonObject);

    /**
     * 根据用户ID获取聊天
     */
    List<JSONObject> getChatByUserId(JSONObject jsonObject);

    /**
     * 根据聊天id获取消息详情
     */
    List<JSONObject> getMessageByChatId(JSONObject jsonObject);

    /**
     * 获取聊天条数
     */
    int countChatByUserId(JSONObject jsonObject);

    /**
     * 根据用户ID获取消息条数
     */
    int countMessageByUserId(JSONObject jsonObject);

    /**
     * 根据消息ID获取消息
     */
    JSONObject getMessageByMessageId(JSONObject jsonObject);

    /**
     * 获取一条消息的图片路径
     */
    List<JSONObject> getPictureByMessageId(int id);


    /**
     * 获取聊天记录编号
     */
    int getChat(JSONObject jsonObject);

    /**
     * 获取聊天记录条数（用于判断两用户是否存在聊天记录）
     */
    int countChat(JSONObject jsonObject);

    /**
     * 添加聊天记录
     */
    int addChat(JSONObject jsonObject);

    /**
     * 添加消息
     */
    int addMessage(JSONObject jsonObject);

    /**
     * 添加图片
     */
    int addPicture(JSONObject jsonObject);

    /**
     * 改变消息状态
     */
    int updateMessage(JSONObject jsonObject);
}
