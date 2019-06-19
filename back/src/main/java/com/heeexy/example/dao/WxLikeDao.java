package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 9:21 2019/6/19
 * @ Description：用户点赞记录dao层
 * @Version: 1.0
 */
public interface WxLikeDao {
    //增加用户点赞记录
    int addByLike(JSONObject jsonObject);
    //查找用户点赞帖子列表
    List<JSONObject> getLikeByUserId(JSONObject jsonObject);
    //查找用户是否已点赞该帖子信息
    JSONObject getLikeUserPost(JSONObject jsonObject);
    //修改帖子用户点赞状态
    int updateLikeByUserId(JSONObject jsonObject);
}
