package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description: 模块Dao层
 * @date: 2019/6/17 19:54
 * @vevsion 1.0
 */
public interface ModuleDao {

    /**
     * 新增模块
     */
    int addModule(JSONObject jsonObject);

    /**
     * 发送给所有人消息
     */
    int addMessage(JSONObject jsonObject);
    /**
     * 获取所有人id
     */
     List<Integer> getAllUserId();

    /**
     * 获取接收人的id
     */
    List<Integer> getAllLcChatAid();
    /**
     * 获取chat中userid是1的id
     */
    List<Integer> getAllLcChatId();
    /**
     * 插入chat表中信息
     */
    int addChat(JSONObject jsonObject);







    /**
     * 删除模块
     */
    int removeModuleDisplay(JSONObject jsonObject);

    /**
     * 修改模块
     */
    int updateModuleName(JSONObject jsonObject);

    /**
     * 查询模块数量
     */
    int countModule(JSONObject jsonObject);

    /**
     * 查询模块列表
     */
    List<JSONObject> listModule(JSONObject jsonObject);

    /**
     * 校验模块名是否已存在
     */
    int queryExistModuleName(JSONObject jsonObject);

    /**
     * 校验模块的隐藏值
     */
    int queryExistModuleDisplay(JSONObject jsonObject);
    /**
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     */
    List<JSONObject> getAllModule();



}
