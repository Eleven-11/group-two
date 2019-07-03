package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 9:42 2019/6/19
 * @ Description：用户点赞帖子Service
 * @Version: 1.0
 */
public interface WxLikeService {

    /**
     * @methodsName: addLike
     * @description: 增加用户点赞记录
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject addLike(JSONObject jsonObject);
    //网页查找用户点赞帖子列表
    /**
     * @methodsName: getLikeByUserId
     * @description: 网页查找用户点赞帖子列表
     * @param:  JSONObject
     * @return:  JSONObject
     */
    JSONObject getLikeByUserId(JSONObject jsonObject);

    /**
     * @methodsName: getLikeList
     * @description: 后台每个用户点赞帖子记录
     * @param:  JSONObject
     * @return:   JSONObject
     */
    JSONObject getLikeList(JSONObject jsonObject);
    /**
     * @methodsName: countPostLikeByUserId
     * @description: 前台计算某个用户的点赞数量
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject countPostLikeByUserId(JSONObject jsonObject);

    /**
     * @methodsName: getLikeUserPost
     * @description: 查找用户是否有已点赞该帖子信息
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject getLikeUserPost(JSONObject jsonObject);
    /**
     * @methodsName: updateLikeByUserId
     * @description: 修改帖子用户点赞状态
     * @param:  JSONObject
     * @return: JSONObject
     */
   JSONObject updateLikeByUserId(JSONObject jsonObject);
    /**
     * @methodsName: addLikeByUserId
     * @description: 重新添加点赞状态
     * @param:  JSONObject
     * @return: JSONObject
     */
    JSONObject addLikeByUserId(JSONObject jsonObject);
    /**
     * @methodsName: getListMoreFansById
     * @description: 查找用户未关注的用户集合
     * @param:  JSONObject
     * @return:  List<JSONObject>
     */
    JSONObject getListMoreLikesById(JSONObject jsonObject);
    /**
     * @methodsName: addMoreFans
     * @description: 批量添加关注
     * @param:  Map
     * @return: int
     */
    JSONObject addMoreLikes(Map map);
    /**
     * 我的点赞
     * @param jsonObject （keyword）
     * @return JSONObject
     */
    JSONObject myLike(JSONObject jsonObject);
}
