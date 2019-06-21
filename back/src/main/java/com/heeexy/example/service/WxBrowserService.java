package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 13:48 2019/6/18
 * @ Description：帖子浏览记录Service
 * @Version: 1.0
 */
public interface WxBrowserService {
    //添加浏览记录
    /**
     * @methodsName: addBrowsePost
     * @description: 添加浏览记录
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject addBrowsePost(JSONObject jsonObject);
    /**
     * @methodsName: getBrowsePostByPostId
     * @description: 查找该用户是否有该帖子的浏览记录
     * @param:  JSONObject
     * @return: JSONObject
     */

    JSONObject getBrowsePostByPostId(JSONObject jsonObject);
    //修改该用户的浏览帖子记录的时间
    JSONObject updateBrowse(JSONObject jsonObject);
    /**
     * @methodsName: getBrowseList
     * @description: 后台显示浏览记录表
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject getBrowseList(JSONObject jsonObject);
    /**
     * @methodsName: getBrowseByUser
     * @description: 前台显示当前用户浏览记录
     * @param:  JSONObject
     * @return:  JSONObject
     */
    JSONObject getBrowseByUser(JSONObject jsonObject);
}
