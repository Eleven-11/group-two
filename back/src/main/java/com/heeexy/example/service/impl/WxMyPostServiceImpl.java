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
    @Override
    public JSONObject getMyPostList(JSONObject jsonObject) {
        //后台数据显示
        List<JSONObject> list = wxMyPostDao.getMyPostList(jsonObject);
        int count = wxMyPostDao.countMyPost(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject updateMyPostById(JSONObject jsonObject) {
        //用户修改帖子状态
        wxMyPostDao.updateMyPostById(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject getMyPostListById(JSONObject jsonObject) {
        //前台用户展示帖子数据
        List<JSONObject> list = wxMyPostDao.getMyPostListById(jsonObject);

        return CommonUtil.successPage(list);
    }

    @Override
    public JSONObject countMyPostByUserId(JSONObject jsonObject) {
        //计算某用户发帖数量
        List<JSONObject> list = wxMyPostDao.countMyPostByUserId(jsonObject);
        return CommonUtil.successPage(list);
    }

    @Override
    public JSONObject updateByPostIdMany(Map map) {
        //批量删除帖子
        wxMyPostDao.updateByPostIdMany(map);
        return CommonUtil.successJson();
    }


}
