package com.heeexy.example.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:35 2019/6/17
 * @ Description：用户粉丝关注接口Service层
 * @ Version: 1.0
 */

public interface WxFansService{
    /**
     * @methodsName: getListByUserId
     * @description: 查找关注用户列表
     * @param:  JSONObject
     * @return: List<JSONObject>
     */
    JSONObject getListByUserId(JSONObject jsonObject);

    /**
     * @methodsName: updateFansByUserId
     * @description: 对用户关注状态的取消
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject updateFansByUserId(JSONObject jsonObject);
    /**
     * @methodsName: updateFansStateByUserId
     * @description: 重新修改关注状态
     * @param:  JSONObject
     * @return: int
     */
    JSONObject updateFansStateByUserId(JSONObject jsonObject);
    /**
     * @methodsName: addByFans
     * @description: 添加粉丝关注用户
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject addByFans(JSONObject jsonObject);
    /**
     * @methodsName: getUserFans
     * @description: 查找是否之前有关注用户信息
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject getUserFans(JSONObject jsonObject);


    /**
     * @methodsName: getListFans
     * @description: 查找所有用户粉丝关注表
     * @param:  JSONObject
     * @return:  JSONObject
     */
    JSONObject getListFans(JSONObject jsonObject);

}
