package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: chenqiangyong
 * @description:帖子收藏的/增删改查
 * @date: 2019/6/20 10:52
 * @vevsion 1.0
 */
public interface PostCollectService {

//  ************************ 后台************************

    /**
     * 新增收藏
     * @param jsonObject postId(帖子id),userId(用户id)
     * @return
     */
    JSONObject addPostCollect(JSONObject jsonObject);
    /**
     * 修改帖子收藏状态值
     * @param jsonObject  postCollectId(收藏id),display(状态值)
     * @return
     */
    JSONObject updatePostCollectDisplay(JSONObject jsonObject);
//    /**
//     * 修改收藏
//     * @param jsonObject  postCollectId(收藏id),display(状态值)
//     * @return
//     */
//    JSONObject updatePostCollect(JSONObject jsonObject);
    /**
     * 查询收藏列表
     * @param jsonObject
     * @return
     */
    JSONObject listPostCollect(JSONObject jsonObject);

    /**
     * 查询所有的收藏
     * 在添加/修改收藏的时候要使用此方法
     * @return
     */
    JSONObject getAllPostCollect();
//************************ 小程序前台************************
    /**
     * 根据用户查询的收藏帖子
     * @param jsonObject  userId(用户id)
     * @return
     */
    JSONObject getAllPostCollectByUserId(JSONObject jsonObject);
    /**
     * 修改帖子收藏状态值
     * @param jsonObject  postId(帖子id),userId(用户id)
     * @return
     */
    JSONObject updatePostCollectDisplays(JSONObject jsonObject);


}
