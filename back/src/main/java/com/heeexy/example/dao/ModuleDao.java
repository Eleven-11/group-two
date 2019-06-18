package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: 陈强勇
 * @description: 模块Dao层
 * @date: 2019/6/17 19:54
 */
public interface ModuleDao {

    /**
     * 新增模块
     */
    int addModule(JSONObject jsonObject);

    /**
     * 删除模块
     */
//    int removeModuleName(JSONObject jsonObject);

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
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     */
    List<JSONObject> getAllModule();

}
