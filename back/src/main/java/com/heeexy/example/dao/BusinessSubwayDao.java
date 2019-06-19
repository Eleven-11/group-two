package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: 陈强勇
 * @description: 帖子标签表Dao层
 * @date: 2019/6/18 17:47
 */
public interface BusinessSubwayDao {
    /**
     * 新增帖子标签
     */
    int addBusinessSubway(JSONObject jsonObject);
    /**
     * 修改帖子标签
     */
    int updateBusinessSubway(JSONObject jsonObject);
    /**
    * 删除帖子标签
    */
    int updateBusinessSubwayDisplay(JSONObject jsonObject);
    /**
     * 查询帖子标签数量
     */
    int countBusinessSubway(JSONObject jsonObject);
    /**
     * 查询帖子标签列表
     */
    List<JSONObject> listBusinessSubway(JSONObject jsonObject);
    /**
     * 校验帖子类标签是否已存在
     */
    int queryExistBusinessSubwayName(JSONObject jsonObject);
    /**
     * 查询帖子标签的隐藏值
     */
    int queryExistBusinessSubwayDisplay(JSONObject jsonObject);
    /**
     * 查询所有的帖子标签
     * 在添加/修改帖子标签的时候要使用此方法
     */
    List<JSONObject> getAllBusinessSubway();
    /**
     * 查询帖子标签的上级id,根据名
     */
    List<JSONObject> queryExistBusinessSubwayByName(JSONObject jsonObject);
    /**
     * 查询帖子标签的名,根据id
     */
    int queryExistBusinessSubwayById(JSONObject jsonObject);


}
