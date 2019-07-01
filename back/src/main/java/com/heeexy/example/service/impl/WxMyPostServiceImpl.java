package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxMyPostDao;
import com.heeexy.example.service.WxMyPostService;
import com.heeexy.example.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:28 2019/6/20
 * @ Description：
 * @ Version:
 */
@Service
public class WxMyPostServiceImpl implements WxMyPostService {

    @Autowired
    private WxMyPostDao wxMyPostDao;
    /**
     * 后台帖子显示集合
     * @param jsonObject (userName,offSet,pageRow)
     * @return JSONObject
     */
    @Override
    public JSONObject getMyPostList(JSONObject jsonObject) {
        //后台数据显示
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = wxMyPostDao.getMyPostList(jsonObject);
        int count = wxMyPostDao.countMyPost(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    /**
     * 用户修改单个帖子状态
     * @param jsonObject (postId,myPostState)
     * @return JSONObject
     */
    @Override
    public JSONObject updateMyPostById(JSONObject jsonObject) {
        //用户修改帖子状态
        wxMyPostDao.updateMyPostById(jsonObject);
        return CommonUtil.successJson();
    }
    /**
     * 前台用户展示帖子数据
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject getMyPostListById(JSONObject jsonObject) {
        //前台用户展示帖子数据
        List<JSONObject> list = wxMyPostDao.getMyPostListById(jsonObject);

        return CommonUtil.successPage(list);
    }
    /**
     * 计算某用户发帖数量
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject countMyPostByUserId(JSONObject jsonObject) {
        //计算某用户发帖数量
        List<JSONObject> list = wxMyPostDao.countMyPostByUserId(jsonObject);
        return CommonUtil.successPage(list);
    }

    /**
     * 批量删除帖子
     * @param map (myPostState,postId)
     * @return JSONObject
     */
    @Override
    public JSONObject updateByPostIdMany(Map map) {
        //批量删除帖子
        wxMyPostDao.updateByPostIdMany(map);
        return CommonUtil.successJson();
    }
}
