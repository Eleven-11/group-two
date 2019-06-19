package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 13:29 2019/6/18
 * @ Description：帖子浏览记录
 * @Version: 1.0
 */
public interface WxBrowserDao {

    //添加浏览记录
    int addBrowsePost(JSONObject jsonObject);
    //查找某个用户的浏览记录
    List<JSONObject> getBrowsePost(JSONObject jsonObject);
    //查找该用户是否有该帖子的浏览记录
     JSONObject getBrowsePostByPostId(JSONObject jsonObject);
    //修改该用户的浏览帖子记录的时间
    int updateBrowse(JSONObject jsonObject);


}
