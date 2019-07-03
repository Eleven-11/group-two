package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 13:29 2019/6/18
 * @ Description：帖子浏览记录
 * @Version: 1.0
 */
public interface WxBrowserDao {


    /**
     * @methodsName: addBrowsePost
     * @description: 添加浏览记录
     * @param:  JSONObject
     * @return: int
     */
    int addBrowsePost(JSONObject jsonObject);
    /**
     * @methodsName: getBrowseList
     * @description: 后台显示浏览记录表
     * @param:  JSONObject
     * @return: List<JSONObject>
     */
    List<JSONObject> getBrowseList(JSONObject jsonObject);
    /**
     * @methodsName: countBrowse
     * @description: 后台计算浏览记录数
     * @param:  JSONObject
     * @return: int
     */
    int countBrowse(JSONObject jsonObject);
    /**
     * @methodsName: getBrowseByUser
     * @description: 前台显示当前用户浏览记录
     * @param:  JSONObject
     * @return:  List<JSONObject>
     */
    List<JSONObject> getBrowseByUser(JSONObject jsonObject);
    /**
     * @methodsName: myBrowse
     * @description: 我的足迹
     * @param:  JSONObject
     * @return:  List<JSONObject>
     */
    List<JSONObject> myBrowse(JSONObject jsonObject);

}
