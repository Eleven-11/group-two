package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxFansDao;
import com.heeexy.example.dao.WxUserDao;
import com.heeexy.example.service.WxFansService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:36 2019/6/17
 * @ Description：用户粉丝关注接口实现层
 * @ Version: 1.0
 */
@Service
public class WxFansServiceImpl implements WxFansService {
    @Autowired
    private WxFansDao wxFansDao;
    @Autowired
    private WxUserDao wxUserDao;
    /*查找关注用户列表*/
    @Override
    public JSONObject getListByUserId(JSONObject jsonObject) {
        List<JSONObject> list = wxFansDao.getListByUserId(jsonObject);
        return CommonUtil.successPage( list);
    }

    @Override
    public JSONObject countByUserId(JSONObject jsonObject) {
        //前台用户计算关注数
        List<JSONObject> jsonObject1 = wxFansDao.countByUserId(jsonObject);
        return CommonUtil.successPage( jsonObject1);
    }

    /*对用户关注状态的修改取消*/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateFansByUserId(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
         wxFansDao.updateFansByUserId(jsonObject);
        int count = wxUserDao.getFansByUserId(jsonObject);
        String userId = jsonObject.getString("userId");
        Map<String,Object> map=new HashMap<>();
        map.put("userFans", count-1);
        map.put("userId", userId);
        //修改用户真实粉丝数量
        wxUserDao.updateFansById(map);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updateFansStateByUserId(JSONObject jsonObject) {
        //修改原有的点赞状态
        wxFansDao.updateFansStateByUserId(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addByFans(JSONObject jsonObject) {
        //     添加粉丝关注用户
        JSONObject userFans = wxFansDao.getUserFans(jsonObject);
        if (userFans.isEmpty()){
            wxFansDao.addByFans(jsonObject);
        }else {
            wxFansDao.updateFansStateByUserId(jsonObject);

        }
        int count = wxUserDao.getFansByUserId(jsonObject);
        String userId = jsonObject.getString("userId");
        Map<String,Object> map=new HashMap<>();
        map.put("userFans", count+1);
        map.put("userId", userId);
        //修改用户真实粉丝数量
        wxUserDao.updateFansById(map);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject getUserFans(JSONObject jsonObject) {
        //查看两用户之间是否有关注信息
        JSONObject user = wxFansDao.getUserFans(jsonObject);
        return user;
    }

    @Override
    public JSONObject getListFans(JSONObject jsonObject) {
        //后台关注用户展示
        CommonUtil.fillPageParam(jsonObject);
        int count = wxFansDao.countUserFans(jsonObject);
        List<JSONObject> list = wxFansDao.getListFans(jsonObject);
        System.out.println(list);
        return CommonUtil.successPage(jsonObject, list, count);

    }


}
