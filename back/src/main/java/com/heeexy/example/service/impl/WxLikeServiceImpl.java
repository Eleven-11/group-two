package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxLikeDao;
import com.heeexy.example.service.WxLikeService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 9:43 2019/6/19
 * @ Description：
 * @ Version: 1.0
 */
@Service
public class WxLikeServiceImpl implements WxLikeService {
    @Autowired
    private WxLikeDao wxLikeDao;
    /**
     * 用户添加首次点赞记录
     * @param jsonObject (onUserId,userId,postId)
     * @return JSONObject
     */
    @Override
    public JSONObject addLike(JSONObject jsonObject) {
        JSONObject likeUserPost = wxLikeDao.getLikeUserPost(jsonObject);
        if (likeUserPost.isEmpty()){
            wxLikeDao.addLike(jsonObject);
        }else {
            wxLikeDao.addLikeByUserId(jsonObject);
        }
        return CommonUtil.successJson();
    }
    /**
     * 网页获取用户点赞列表信息
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject getLikeByUserId(JSONObject jsonObject) {
        List<JSONObject> likeByUserId = wxLikeDao.getLikeByUserId(jsonObject);

        return CommonUtil.successPage(likeByUserId);
    }
    /**
     * 后台点赞数据表格展示
     * @param jsonObject (userName,offSet,pageRow)
     * @return JSONObject
     */
    @Override
    public JSONObject getLikeList(JSONObject jsonObject) {
        //后台数据展示
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = wxLikeDao.getLikeList(jsonObject);
        int count = wxLikeDao.countPostLike(jsonObject);

        return CommonUtil.successPage(jsonObject, list, count);
    }
    /**
     * 前台获取某用户点赞数量
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject countPostLikeByUserId(JSONObject jsonObject) {
        //前台获取某用户点赞数量
        List<JSONObject> likeNubmer = wxLikeDao.countPostLikeByUserId(jsonObject);
        return CommonUtil.successPage(likeNubmer);
    }
    /**
     * 查找用户是否对该帖子有点赞记录
     * @param jsonObject (postId,onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject getLikeUserPost(JSONObject jsonObject) {
        JSONObject likeUserPost = wxLikeDao.getLikeUserPost(jsonObject);
        return likeUserPost;
    }
    /**
     * 取消帖子的点赞状态
     * @param jsonObject (likeId)
     * @return JSONObject
     */
    @Override
    public JSONObject updateLikeByUserId(JSONObject jsonObject) {

        wxLikeDao.updateLikeByUserId(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 用户重新添加帖子点赞记录
     * @param jsonObject (postId,onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject addLikeByUserId(JSONObject jsonObject) {
        wxLikeDao.addLikeByUserId(jsonObject);
        return CommonUtil.successJson();
    }
}
