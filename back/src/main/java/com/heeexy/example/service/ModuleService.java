package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: 陈强勇
 * @description:模块/增删改查
 * @date: 2019/6/18 9:03
 */
public interface ModuleService {

    /**
     * 新增模块
     */
    JSONObject addModule(JSONObject jsonObject);

    /**
     * 删除模块
     */
//    JSONObject removeModule(JSONObject jsonObject);

    /**
     * 修改模块
     */
    JSONObject updateModuleName(JSONObject jsonObject);



    /**
     * 查询模块列表
     */
    JSONObject listModule(JSONObject jsonObject);


    /**
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     */
    JSONObject getAllModule();
}
