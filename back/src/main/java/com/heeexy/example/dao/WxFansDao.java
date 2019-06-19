package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:33 2019/6/17
 * @ Description：用户粉丝关注接口Dao层
 * @ Version: 1.0
 */
public interface WxFansDao {
     /*查找关注用户列表*/
     List<JSONObject> getListByUserId(JSONObject jsonObject);
     /*计算粉丝关注用户数*/
     int countByUserId(JSONObject jsonObject);
     /*对用户关注状态的取消*/
     int updateFansByUserId(JSONObject jsonObject);
//     添加粉丝关注用户
     int addByFans(JSONObject jsonObject);
     //查找是否之前有关注用户信息
     JSONObject getUserFans(JSONObject jsonObject);
     //查找所有用户粉丝关注表
     List<JSONObject> getListFans(JSONObject jsonObject);
     //计算所有用户关注信息数
     int countUserFans(JSONObject jsonObject);
}
