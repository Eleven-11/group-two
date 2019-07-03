package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description:模块/增删改查
 * @date: 2019/6/18 9:03
 * @vevsion 1.0
 */
public interface ModuleService {
    /**
     * 新增模块
     * @param jsonObject moduleName(模块名字),moduleContent(模块内容)
     * @return
     */
    JSONObject addModule(JSONObject jsonObject);
    /**
     * 新增消息（群）
     * @param jsonObject
     * @return
     */
    JSONObject addMessage(JSONObject jsonObject);

    /**
     * 新增封禁消息（单独）
     * @param jsonObject
     * @return
     */
    JSONObject addMessageF(JSONObject jsonObject);
    /**
     * 新增警告消息（单独）
     * @param jsonObject
     * @return
     */
    JSONObject addMessageJ(JSONObject jsonObject);

    /**
     * 新增欢迎消息（单独）
     * @param jsonObject
     * @return
     */
    JSONObject addMessageH(JSONObject jsonObject);

    /**
     * 删除模块（假）
     * @param jsonObject  moduleId(模块id)
     * @return
     */
    JSONObject removeModuleDisplay(JSONObject jsonObject);
    /**
     * 修改模块内容
     * @param jsonObject  moduleName(模板名字)，moduleContent(模板内容)，moduleId(模板id)
     * @return
     */
    JSONObject updateModule(JSONObject jsonObject);
    /**
     * 查询模块列表
     * @param jsonObject
     * @return
     */
    JSONObject listModule(JSONObject jsonObject);
    /**
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     * @return
     */
    JSONObject getAllModule();



}
