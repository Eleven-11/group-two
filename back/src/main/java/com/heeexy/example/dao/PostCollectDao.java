package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description: 帖子收藏的Dao层
 * @date: 2019/6/20 9:58
 */
public interface PostCollectDao {
    /**
     * 新增收藏
     */
    int addPostCollect(JSONObject jsonObject);

    /**
     * 修改收藏的显示值(1改0)
     */
    int updatePostCollectDisplay(JSONObject jsonObject);

    /**
     * 修改收藏的显示值(0改1)
     */
    JSONObject updatePostCollectDisplayTwo(JSONObject jsonObject);
    /**
     * 修改收藏
     */
    int updatePostCollect(JSONObject jsonObject);

    /**
     * 查询收藏数量
     */
    int countPostCollect(JSONObject jsonObject);

    /**
     * 查询收藏列表
     */
    List<JSONObject> listPostCollect(JSONObject jsonObject);

    /**
     * 校验帖子id和用户id是否已存在
     */
    int queryExistPostCollectPU(JSONObject jsonObject);
    /**
     * 校验帖子id是否存在（在帖子表中）
     */
    int queryExistPostCollectPostId(JSONObject jsonObject);

    /**
     * 校验用户是否已存在（在用户表中）
     */
    int queryExistPostCollectUserId(JSONObject jsonObject);


    /**
     * 校验收藏的的隐藏值
     */
    int queryExistPostCollectDisplay(JSONObject jsonObject);
    /**
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     */
    List<JSONObject> getAllPostCollect();

    /**
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     */
     List<JSONObject> listPostCollectId();
}
