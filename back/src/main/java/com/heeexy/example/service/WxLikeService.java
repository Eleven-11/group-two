package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 9:42 2019/6/19
 * @ Description：用户点赞帖子Service
 * @Version: 1.0
 */
public interface WxLikeService {

    //增加用户点赞记录
    JSONObject addByLike(JSONObject jsonObject);
    //查找用户点赞帖子列表
    JSONObject getLikeByUserId(JSONObject jsonObject);
    //查找用户是否已点赞该帖子信息
    JSONObject getLikeUserPost(JSONObject jsonObject);
    //修改帖子用户点赞状态
    JSONObject updateLikeByUserId(JSONObject jsonObject);
}
