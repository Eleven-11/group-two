package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 10:31 2019/6/17
 * @ Description：微信用户接口Dao层
 * @ Version: 1.0
 */
public interface WxUserDao {
     /**
          * @methodsName: getListUser
          * @description: 查找所有用户
          * @param:  JSONObject
          * @return:  List<JSONObject>
          */
    List<JSONObject> getListUser(JSONObject jsonObject);

     /**
          * @methodsName: countUser
          * @description: 计算用户量
          * @param:  JSONObject
          * @return: int
          */
    int countUser(JSONObject jsonObject);
     /**
          * @methodsName: getMaxNumber
          * @description: 查找最大id数用户
          * @param:  JSONObject
          * @return: JSONObject
          */
    JSONObject getMaxNumber(JSONObject jsonObject);
     /**
          * @methodsName: addByUser
          * @description: 增加普通用户
          * @param:  JSONObject
          * @return: int
          */
    int addByUser(JSONObject jsonObject);
     /**
          * @methodsName: addGuestUser
          * @description: 添加游客用户
          * @param:  JSONObject
          * @return: int
          */
    int addGuestUser(JSONObject jsonObject);
     /**
          * @methodsName: updateFansfById
          * @description: 修改虚假粉丝数
          * @param:  JSONObject
          * @return: int
          */
    int updateFansfById(JSONObject jsonObject);
     /**
          * @methodsName: getUserByUsername
          * @description: 查询某个用户
          * @param:  JSONObject
          * @return: JSONObject
          */
    JSONObject getUserByUsername(JSONObject jsonObject);

    //修改用户封禁状态
     /**
          * @methodsName: updateStateByUserId
          * @description: 修改用户封禁状态
          * @param:  JSONObject
          * @return: int
          */
    int updateStateByUserId(JSONObject jsonObject);

     /**
          * @methodsName: listUserState
          * @description: 查找封禁状态集合
          * @param:
          * @return: List<JSONObject>
          */
    List<JSONObject> listUserState();
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
         JSONObject getDetailUserById(JSONObject jsonObject);
    }
