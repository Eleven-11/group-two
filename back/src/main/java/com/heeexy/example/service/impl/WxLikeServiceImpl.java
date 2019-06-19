package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxLikeDao;
import com.heeexy.example.service.WxLikeService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 9:43 2019/6/19
 * @ Description：
 * @ Version: 1.0
 */

public class WxLikeServiceImpl implements WxLikeService {
    @Autowired
    private WxLikeDao wxLikeDao;

    /*用户添加点赞记录*/
    @Override
    public JSONObject addByLike(JSONObject jsonObject) {
        wxLikeDao.addByLike(jsonObject);
        return CommonUtil.successJson();
    }
    /*获取用户点赞列表*/
    @Override
    public JSONObject getLikeByUserId(JSONObject jsonObject) {
        List<JSONObject> likeByUserId = wxLikeDao.getLikeByUserId(jsonObject);

        return CommonUtil.successPage(likeByUserId);
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
}
