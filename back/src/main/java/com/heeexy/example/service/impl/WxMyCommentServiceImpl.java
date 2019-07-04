package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxMyCommentDao;
import com.heeexy.example.service.WxMyCommentService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.emjoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 20:17 2019/6/20
 * @ Description：
 * @Version:
 */
@Service
public class WxMyCommentServiceImpl implements WxMyCommentService {
    @Autowired
    private WxMyCommentDao wxMyCommentDao;

    /**
     * 后台表格显示评论列表
     * @param jsonObject (offSet,pageRow)
     * @return JSONObject
     */
    @Override
    public JSONObject getMyCommentList(JSONObject jsonObject) {
        //后台网页显示
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = wxMyCommentDao.getMyCommentList(jsonObject);
        for (JSONObject ments : list) {
            String time = ments.getString("time");
            String times = emjoy.getTimes(time);
            ments.put("time", times);
        }
        int count = wxMyCommentDao.countMyComment(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    /**
     * 前台某用户评论列表
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject getMyCommentListById(JSONObject jsonObject) {
        //前台网页显示
        List<JSONObject> myCommentListById = wxMyCommentDao.getMyCommentListById(jsonObject);
        return CommonUtil.successPage(myCommentListById);
    }

    /**
     * 我的评论
     * @param jsonObject (keyword)
     * @return JSONObject
     */
    @Override
    public JSONObject myselfComment(JSONObject jsonObject) {
        List<JSONObject> list = wxMyCommentDao.myselfComment(jsonObject);
        return  CommonUtil.successPage(list);
    }
}
