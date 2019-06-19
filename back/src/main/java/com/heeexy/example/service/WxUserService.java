package com.heeexy.example.service;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:36 2019/6/17
 * @ Description：微信用户接口Service层
 * @ Version: 1.0
 */

public interface WxUserService {
    //查找所有用户
    JSONObject getListUser(JSONObject jsonObject);

    //查找最大id数用户
    JSONObject getMaxNumber(JSONObject jsonObject);
    //增加用户
    JSONObject addByUser(JSONObject jsonObject);
    //修改虚假粉丝数和封禁状态
    JSONObject updateFansfById(JSONObject jsonObject);

    //查找状态集合
    JSONObject listUserState();
}
