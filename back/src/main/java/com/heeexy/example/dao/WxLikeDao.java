package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 9:21 2019/6/19
 * @ Description：用户点赞记录dao层
 * @Version: 1.0
 */
public interface WxLikeDao {

    /**
     * @methodsName: addLike
     * @description: 增加用户点赞记录
     * @param: JSONObject
     * @return: int
     */
    int addLike(JSONObject jsonObject);

    /**
     * @methodsName: getLikeByUserId
     * @description: 网页查找用户点赞帖子列表
     * @param: JSONObject
     * @return: List<JSONObject>
     */
    List<JSONObject> getLikeByUserId(JSONObject jsonObject);

    /**
     * @methodsName: getLikeList
     * @description: 后台每个用户点赞帖子记录
     * @param: JSONObject
     * @return: List<JSONObject>
     */
    List<JSONObject> getLikeList(JSONObject jsonObject);

    /**
     * @methodsName: countPostLike
     * @description: 后台每个用户点赞帖子列表计算
     * @param: JSONObject
     * @return: int
     */
    int countPostLike(JSONObject jsonObject);

    /**
     * @methodsName: countPostLikeByUserId
     * @description: 计算某个用户的点赞数量
     * @param: JSONObject
     * @return: List<JSONObject>
     */
    List<JSONObject> countPostLikeByUserId(JSONObject jsonObject);

    /**
     * @methodsName: getLikeUserPost
     * @description: 查找用户是否有已点赞该帖子信息
     * @param: JSONObject
     * @return: JSONObject
     */
    JSONObject getLikeUserPost(JSONObject jsonObject);

    /**
     * @methodsName: updateLikeByUserId
     * @description: 修改帖子用户点赞状态
     * @param: JSONObject
     * @return: int
     */
    int updateLikeByUserId(JSONObject jsonObject);

    /**
     * @methodsName: addLikeByUserId
     * @description: 重新添加点赞状态
     * @param: JSONObject
     * @return: int
     */
    int addLikeByUserId(JSONObject jsonObject);

    /**
     * @methodsName: getListMoreLikesById
     * @description: 查找用户未点赞的用户集合
     * @param: JSONObject
     * @return: List<JSONObject>
     */
    List<JSONObject> getListMoreLikesById(JSONObject jsonObject);

    /**
     * @methodsName: addMoreLikes
     * @description: 批量添加点赞
     * @param: Map
     * @return: int
     */
    int addMoreLikes(Map map);

    /**
     * @methodsName: myLike
     * @description: 我的点赞
     * @param:  JSONObject
     * @return:  List<JSONObject>
     */
    List<JSONObject> myLike(JSONObject jsonObject);
}
