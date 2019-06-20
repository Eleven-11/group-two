package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 20:43 2019/6/19
 * @ Description：我的帖子
 * @Version: 1.0
 */
public interface WxMyPostDao {
    //后台每个用户自己的帖子记录
    List<JSONObject> getMyPostList(JSONObject jsonObject);
    //后台计数
    int countMyPost(JSONObject jsonObject);
    //修改自己帖子状态
    int updateMyPostById(JSONObject jsonObject);
    //前台用户自己的帖子记录
    List<JSONObject> getMyPostListById(JSONObject jsonObject);
    //计算该用户发帖数量
    int countMyPostByUserId(JSONObject jsonObject);
        //批量删除帖子
    int updateByPostId(JSONObject jsonObject);
}
