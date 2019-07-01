package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxUserDao;
import com.heeexy.example.service.WxUserService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    /**
     * 查找所有用户集合
     * @param jsonObject (userName,offSet,pageRow)
     * @return JSONObject
     */
    @Override
    public JSONObject getListUser(JSONObject jsonObject) {
        //查找所有用户
        CommonUtil.fillPageParam(jsonObject);
        int count = wxUserDao.countUser(jsonObject);
        List<JSONObject> list = wxUserDao.getListUser(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    /**
     * 增加普通用户
     * @param jsonObject (userUuid,userName,userPhoto,userSex)
     * @return JSONObject
     */
    @Override
    public JSONObject addByUser(JSONObject jsonObject) {
        //增加普通用户
         wxUserDao.addByUser(jsonObject);
        return CommonUtil.successJson();
    }
    /**
     * 增加游客用户
     * @param jsonObject jsonObject
     * @return JSONObject
     */
    @Override
    public JSONObject addGuestUser(JSONObject jsonObject) {
        //增加游客用户
        wxUserDao.addGuestUser(jsonObject);
        return CommonUtil.successJson();
    }
    /**
     * 修改虚假粉丝数
     * @param jsonObject (userFansf,userId)
     * @return SONObject
     */
    @Override
    public JSONObject updateFansfById(JSONObject jsonObject) {
        //修改虚假粉丝数
        wxUserDao.updateFansfById(jsonObject);
        return CommonUtil.successJson();
    }
    /**
     * 修改用户封禁状态
     * @param jsonObject (userId,uerState)
     * @return JSONObject
     */
    @Override
    public JSONObject updateStateByUserId(JSONObject jsonObject) {
        //修改用户封禁状态
        wxUserDao.updateStateByUserId(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 查找某个用户
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject getUserByUsername(JSONObject jsonObject) {
        //查找用户
        JSONObject userByUsername = wxUserDao.getUserByUsername(jsonObject);
        return userByUsername;
    }

    /**
     * 计算用户粉丝数
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject countFansByUserId(JSONObject jsonObject) {
        //计算用户粉丝数
        List<JSONObject> fans = wxUserDao.countFansByUserId(jsonObject);
        return CommonUtil.successPage(fans);
    }
    /**
     * 修改真实粉丝数
     * @param map (userId,userFans)
     * @return JSONObject
     */
    @Override
    public JSONObject updateFansById(Map map) {
        //修改真实粉丝数
         wxUserDao.updateFansById(map);
        return CommonUtil.successJson();
    }
}
