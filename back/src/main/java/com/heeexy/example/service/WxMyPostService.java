package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;


/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:27 2019/6/20
 * @ Description：我的帖子
 * @Version: 1.0
 */
public interface WxMyPostService {

    /**
     * @methodsName: getMyPostList
     * @description: 后台每个用户自己的帖子记录
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject getMyPostList(JSONObject jsonObject);

    /**
     * @methodsName: updateMyPostById
     * @description: 修改自己帖子状态
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject updateMyPostById(JSONObject jsonObject);

    /**
     * @methodsName: getMyPostListById
     * @description: 前台用户自己的帖子记录
     * @param:  JSONObject
     * @return:  JSONObject
     */
    JSONObject getMyPostListById(JSONObject jsonObject);
    /**
     * @methodsName: countMyPostByUserId
     * @description: 计算该用户发帖数量
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject countMyPostByUserId(JSONObject jsonObject);
    /**
     * @methodsName: updateByPostIdMany
     * @description: 批量删除帖子
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject updateByPostIdMany(Map map);
}
