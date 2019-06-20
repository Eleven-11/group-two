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
    //查找所有用户
    List<JSONObject> getListUser(JSONObject jsonObject);
    //计算用户量
    int countUser(JSONObject jsonObject);
    //查找最大id数用户
    JSONObject getMaxNumber(JSONObject jsonObject);
    //增加普通用户
    int addByUser(JSONObject jsonObject);
    //添加游客用户
    int addGuestUser(JSONObject jsonObject);
    //修改虚假粉丝数
    int updateFansfById(JSONObject jsonObject);

    //模糊查询用户
    List<JSONObject> getUserByUsername(JSONObject jsonObject);

    //修改用户封禁状态
    int updateStateByUserId(JSONObject jsonObject);
    //查找封禁状态集合
    List<JSONObject> listUserState();


    }
