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

    /*用户添加点赞记录*/
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


    /*网页获取用户点赞列表*/
    @Override
    public JSONObject getLikeByUserId(JSONObject jsonObject) {
        List<JSONObject> likeByUserId = wxLikeDao.getLikeByUserId(jsonObject);

        return CommonUtil.successPage(likeByUserId);
    }

    @Override
    public JSONObject getLikeList(JSONObject jsonObject) {
        //后台数据展示
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = wxLikeDao.getLikeList(jsonObject);
        int count = wxLikeDao.countPostLike(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject countPostLikeByUserId(JSONObject jsonObject) {
        //前台获取某用户点赞数量根据onUserId
        List<JSONObject> likeNubmer = wxLikeDao.countPostLikeByUserId(jsonObject);
        return CommonUtil.successPage(likeNubmer);
    }

    /*查找用户是否对该帖子有点赞记录*/
    @Override
    public JSONObject getLikeUserPost(JSONObject jsonObject) {
        JSONObject likeUserPost = wxLikeDao.getLikeUserPost(jsonObject);
        return likeUserPost;
    }
    /*修改帖子的点赞状态*/
    @Override
    public JSONObject updateLikeByUserId(JSONObject jsonObject) {

        wxLikeDao.updateLikeByUserId(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject addLikeByUserId(JSONObject jsonObject) {
        wxLikeDao.addLikeByUserId(jsonObject);
        return CommonUtil.successJson();
    }
}
