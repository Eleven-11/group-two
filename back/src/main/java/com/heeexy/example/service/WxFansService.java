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
    /*查找关注用户列表*/
    JSONObject getListByUserId(JSONObject jsonObject);

    /*对用户关注状态的取消*/
    JSONObject updateFansByUserId(JSONObject jsonObject);
    //     添加粉丝关注用户
    JSONObject addByFans(JSONObject jsonObject);
    //查找是否之前有关注
    JSONObject getUserFans(JSONObject jsonObject);

    /*查找所有用户粉丝关注表*/
    JSONObject getListFans(JSONObject jsonObject);

}
