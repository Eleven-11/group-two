package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:27 2019/6/20
 * @ Description：我的帖子
 * @Version: 1.0
 */
public interface WxMyPostService {
    //后台每个用户自己的帖子记录
    JSONObject getMyPostList(JSONObject jsonObject);

    //删除自己帖子记录
    JSONObject updateMyPostById(JSONObject jsonObject);
    //前台用户自己的帖子记录
    JSONObject getMyPostListById(JSONObject jsonObject);
    //批量删除帖子
    JSONObject updateByPostId(JSONObject jsonObject);
}
