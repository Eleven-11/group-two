package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxMyPostDao;
import com.heeexy.example.service.WxMyPostService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        int count = wxMyPostDao.countMyPostByUserId(jsonObject);
        return CommonUtil.successPage(jsonObject,list,count);
    }

    @Override
    public JSONObject updateByPostId(JSONObject jsonObject) {
        //批量删除帖子
        wxMyPostDao.updateByPostId(jsonObject);
        return CommonUtil.successJson();
    }


}
