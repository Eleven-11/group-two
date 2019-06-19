package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxUserDao;
import com.heeexy.example.service.WxUserService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:37 2019/6/17
 * @ Description：微信用户接口实现层
 * @ Version: 1.0
 */
@Service
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    private WxUserDao wxUserDao;
    @Override
    public JSONObject getListUser(JSONObject jsonObject) {
        //查找所有用户
        CommonUtil.fillPageParam(jsonObject);
        int count = wxUserDao.countUser(jsonObject);
        System.out.println(count);
        List<JSONObject> list = wxUserDao.getListUser(jsonObject);
//        System.out.println(list);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject getMaxNumber(JSONObject jsonObject) {
        //查找最大id数用户
        JSONObject maxNumber = wxUserDao.getMaxNumber(jsonObject);
        return maxNumber;
    }

    @Override
    public JSONObject addByUser(JSONObject jsonObject) {
        //增加用户
         wxUserDao.addByUser(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updateFansfById(JSONObject jsonObject) {

        //修改虚假粉丝数
        wxUserDao.updateFansfById(jsonObject);
        return CommonUtil.successJson();
    }
    //查找封禁状态集合
    @Override
    public JSONObject listUserState() {
        List<JSONObject> jsonObjects = wxUserDao.listUserState();
        return CommonUtil.successPage(jsonObjects);
    }

}
