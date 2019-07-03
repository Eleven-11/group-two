package com.heeexy.example.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:36 2019/6/17
 * @ Description：微信用户接口Service层
 * @ Version: 1.0
 */

public interface WxUserService {

    /**
     * @methodsName: getListUser
     * @description: 查找所有用户
     * @param:  JSONObject
     * @return:  JSONObject
     */
    JSONObject getListUser(JSONObject jsonObject);

    /**
     * @methodsName: getMaxNumber
     * @description: 查找最大id数用户
     * @param:  JSONObject
     * @return: JSONObject
     */
   /* JSONObject getMaxNumber(JSONObject jsonObject);*/

    /**
     * @methodsName: addByUser
     * @description: 增加普通用户
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject addByUser(JSONObject jsonObject);

    /**
     * @methodsName: addGuestUser
     * @description: 添加游客用户
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject addGuestUser(JSONObject jsonObject);
    /**
     * @methodsName: updateFansfById
     * @description: 修改虚假粉丝数和封禁状态
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject updateFansfById(JSONObject jsonObject);
    //修改用户封禁状态
    /**
     * @methodsName: updateStateByUserId
     * @description: 修改用户封禁状态
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject updateStateByUserId(JSONObject jsonObject);
    //查询某个用户
    /**
     * @methodsName: getUserByUsername
     * @description: 查询某个用户
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject getUserByUsername(JSONObject jsonObject);
    /**
     * @methodsName: countFansByUserId
     * @description: 计算用户粉丝数
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject countFansByUserId(JSONObject jsonObject);
    /**
     * @methodsName: getDetailUserById
     * @description: 用户详情发帖计数和粉丝计数
     * @param:  JSONObject
     * @return: JSONObject
     */
   /* JSONObject getDetailUserById(JSONObject jsonObject);*/
    /**
     * @methodsName: updateFansById
     * @description: 修改真实粉丝数
     * @param:  Map
     * @return: JSONObject
     */
    JSONObject updateFansById(Map map);
    /**
     * 我的界面信息
     * @param jsonObject （uid）
     * @return  List<JSONObject>
     */
    JSONObject mySelf(JSONObject jsonObject);
    /**
     * @methodsName: mySelfFans
     * @description: 我的界面
     * @param:  JSONObject
     * @return:  JSONObject
     */
    JSONObject mySelfFans(JSONObject jsonObject);
    /**
     * @methodsName: queryUserByUuId
     * @description: 根据uuid查找用户
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject queryUserByUuId(JSONObject jsonObject);
}
