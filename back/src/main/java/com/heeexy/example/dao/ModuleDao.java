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
     * @param jsonObject  moduleName(模块名字),moduleContent(模块内容)
     * @return
     */
    int addModule(JSONObject jsonObject);

    /**
     * 发送给所有人消息
     * @param jsonObject  chatid(聊天id),moduleContent(模块内容)
     * @return
     */
    int addMessage(JSONObject jsonObject);

    /**
     * 获取聊天表中不存在的用户id
     * @return
     */
     List<Integer> getAllUserId();

    /**
     * 获取接收人的id
     * @return
     */
    List<Integer> getAllLcChatAid();

    /**
     * 获取chat中userid是1的id
     * @return
     */
    List<Integer> getAllLcChatId();

    /**
     * 插入chat表中信息
     * @param jsonObject  userid(遍历而来)
     * @return
     */
    int addChat(JSONObject jsonObject);

    /**
     * 修改模块的显示值
     * @param jsonObject moduleId(模块id)
     * @return
     */
    int removeModuleDisplay(JSONObject jsonObject);

    /**
     * 修改模块
     * @param jsonObject moduleName(模板名字)，moduleContent(模板内容)，moduleId(模板id)
     * @return
     */
    int updateModuleName(JSONObject jsonObject);

    /**
     * 查询模块数量
     * @param jsonObject
     * @return
     */
    int countModule(JSONObject jsonObject);

    /**
     * 查询模块列表
     * @param jsonObject input(文本框中的值,模糊查询moduleName)
     * @return
     */
    List<JSONObject> listModule(JSONObject jsonObject);

    /**
     * 校验模块名是否已存在
     * @param jsonObject moduleName(模块名)
     * @return
     */
    int queryExistModuleName(JSONObject jsonObject);

    /**
     * 获取模块的隐藏值
     * @param jsonObject moduleName(模块名)
     * @return
     */
    int queryExistModuleDisplay(JSONObject jsonObject);

    /**
     * 查询所有的模块
     *在添加/修改模块的时候要使用此方法
     * @return
     */
    List<JSONObject> getAllModule();



}
