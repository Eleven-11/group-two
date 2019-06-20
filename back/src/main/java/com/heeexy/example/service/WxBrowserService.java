package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 13:48 2019/6/18
 * @ Description：帖子浏览记录Service
 * @Version: 1.0
 */
public interface WxBrowserService {
    //添加浏览记录
    JSONObject addBrowsePost(JSONObject jsonObject);

    //查找该用户是否有该帖子的浏览记录
    JSONObject getBrowsePostByPostId(JSONObject jsonObject);
    //修改该用户的浏览帖子记录的时间
    JSONObject updateBrowse(JSONObject jsonObject);
    //后台显示浏览记录表
    JSONObject getBrowseList(JSONObject jsonObject);
    //前台显示当前用户浏览记录
    JSONObject getBrowseByUser(JSONObject jsonObject);
}
