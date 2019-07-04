package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
          * @param:  map
          * @return: int
          */
    int addByUser(Map map);
     /**
          * @methodsName: addGuestUser
          * @description: 添加游客用户
          * @param:  map
          * @return: int
          */
    int addGuestUser(Map map);
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


     /**
          * @methodsName: updateStateByUserId
          * @description: 修改用户封禁状态
          * @param:  JSONObject
          * @return: int
          */
    int updateStateByUserId(JSONObject jsonObject);
         /**
              * @methodsName: countFansByUserId
              * @description: 计算用户粉丝数
              * @param:  JSONObject
              * @return: JSONObject
              */
         List<JSONObject> countFansByUserId(JSONObject jsonObject);
          /**
               * @methodsName: getDetailUserById
               * @description: 用户详情发帖计数和粉丝计数
               * @param:  JSONObject
               * @return: List<JSONObject>
               */
         List<JSONObject> getDetailUserById(JSONObject jsonObject);
          /**
               * @methodsName: updateFansById
               * @description: 修改真实粉丝数
               * @param:  Map
               * @return: int
               */
         int updateFansById(Map map);
          /**
               * @methodsName: getFansByUserId
               * @description: 获取用户真实粉丝数
               * @param:  JSONObject
               * @return: int
               */
         int getFansByUserId(JSONObject jsonObject);

    /**
     * @methodsName: mySelf
     * @description: 我的界面
     * @param:  JSONObject
     * @return:  List<JSONObject>
     */
    List<JSONObject> mySelf(JSONObject jsonObject);
    /**
     * @methodsName: mySelfFans
     * @description: 我的界面
     * @param:  JSONObject
     * @return:  List<JSONObject>
     */
    List<JSONObject> mySelfFans(JSONObject jsonObject);
     /**
          * @methodsName: queryUserByUuId
          * @description: 根据uuid查找用户
          * @param:  Map
          * @return: JSONObject
          */
    JSONObject queryUserByUuId(Map map);
    }
