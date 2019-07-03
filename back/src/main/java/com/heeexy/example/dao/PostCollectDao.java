package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description: 帖子收藏的Dao层
 * @date: 2019/6/20 9:58
 * @vevsion 1.0
 */
public interface PostCollectDao {

//************************ 后台************************

    /**
     * 新增收藏
     * @param jsonObject postId(帖子id),userId(用户id)
     * @return
     */
    int addPostCollect(JSONObject jsonObject);


    /**
     * 修改收藏状态
     * @param jsonObject postCollectId(收藏id),display(状态值)
     * @return
     */
    int updatePostCollect(JSONObject jsonObject);

    /**
     * 查询收藏数量
     * @param jsonObject
     * @return
     */
    int countPostCollect(JSONObject jsonObject);

    /**
     * 查询收藏列表
     * @param jsonObject input(文本框的值,模糊查询username)
     * @return
     */
    List<JSONObject> listPostCollect(JSONObject jsonObject);

    /**
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     * @return
     */
    List<JSONObject> getAllPostCollect();

    //   ************************ 共用************************

    /**
     * 校验帖子id和用户id是否已存在
     * @param jsonObject  postId(帖子id),userId(用户id)
     * @return
     */
    int queryExistPostCollectPU(JSONObject jsonObject);
    /**
     * 查询收藏的的隐藏值
     * @param jsonObject  postId(帖子id),userId(用户id)
     * @return
     */
    int queryExistPostCollectDisplay(JSONObject jsonObject);
    /**
     * 修改收藏的显示值(1改0)
     * @param jsonObject postCollectId(收藏id)
     * @return
     */
    int updatePostCollectDisplay(JSONObject jsonObject);
    /**
     * 修改收藏的显示值(0改1)
     * @param jsonObject postCollectId(收藏id)
     * @return
     */
    int updatePostCollectDisplayTwo(JSONObject jsonObject);

    //   ************************ 小程序前台************************

    /**
     * 校验帖子id是否存在（在帖子表中）
     * @param jsonObject postId(帖子id)
     * @return
     */
    int queryExistPostCollectPostId(JSONObject jsonObject);

    /**
     * 校验用户是否已存在（在用户表中）
     * @param jsonObject userId(用户id)
     * @return
     */
    int queryExistPostCollectUserId(JSONObject jsonObject);

    /**
     * 返回用户收藏的所有帖子
     * @param jsonObject userId(用户id)
     * @return
     */
     List<JSONObject> getAllPostCollectByUserId(JSONObject jsonObject);

    /**
     * 帖子的图片
     * @param postId
     * @return
     */
    List<JSONObject> getAllPostImgByPostId(Object postId);

    /**
     * 帖子的点赞
     * @param postId
     * @return
     */
    List<JSONObject> getAllLikeByPostId(Object postId);

    /**
     * 返回用户收藏的所有帖子的回复消息
     * @param  postId
     * @return
     */
    List<JSONObject> getAllCommentByPostId(Object postId);

    /**
     * 回复消息的回复消息
     * @param  commentId
     * @return
     */
    List<JSONObject> getAllCommentByToCommentId(Object commentId);




}
