package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: chenqiangyong
 * @description:帖子收藏的/增删改查
 * @date: 2019/6/20 10:52
 * @vevsion 1.0
 */
public interface PostCollectService {
    /**
     * 新增收藏
     */
    JSONObject addPostCollect(JSONObject jsonObject);

    /**
     * 修改收藏的显示值
     */
    JSONObject updatePostCollectDisplay(JSONObject jsonObject);


    /**
     * 修改收藏
     */
    JSONObject updatePostCollect(JSONObject jsonObject);

    /**
     * 查询收藏列表
     */
    JSONObject listPostCollect(JSONObject jsonObject);
    /**
     * 查询所有的收藏
     * 在添加/修改收藏的时候要使用此方法
     */
    JSONObject getAllPostCollect();
}
