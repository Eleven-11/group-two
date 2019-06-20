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
    int addLike(JSONObject jsonObject);
    //网页查找用户点赞帖子列表
    List<JSONObject> getLikeByUserId(JSONObject jsonObject);
    //后台每个用户点赞帖子记录
    List<JSONObject> getLikeList(JSONObject jsonObject);
    //后台每个用户点赞帖子列表计算
    int countPostLike(JSONObject jsonObject);
    //查找用户是否有已点赞该帖子信息
    JSONObject getLikeUserPost(JSONObject jsonObject);
    //修改帖子用户点赞状态
    int updateLikeByUserId(JSONObject jsonObject);
}
