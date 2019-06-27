package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxMyCommentDao;
import com.heeexy.example.service.WxMyCommentService;
import com.heeexy.example.util.CommonUtil;
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
    @Override
    public JSONObject getMyCommentList(JSONObject jsonObject) {
        //后台网页显示
        List<JSONObject> list = wxMyCommentDao.getMyCommentList(jsonObject);
        int count = wxMyCommentDao.countMyComment(jsonObject);
        System.out.println(list);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getMyCommentListById(JSONObject jsonObject) {
        //前台网页显示
        List<JSONObject> myCommentListById = wxMyCommentDao.getMyCommentListById(jsonObject);
        System.out.println(myCommentListById);
        return CommonUtil.successPage(myCommentListById);
    }
}
