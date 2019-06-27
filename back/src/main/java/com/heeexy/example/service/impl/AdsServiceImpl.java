package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.AdsDao;
import com.heeexy.example.service.AdsService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-06-18 14:38
 * @Version : 1.0
 **/
@Service
public class AdsServiceImpl implements AdsService {
    @Autowired
    private AdsDao adsDao;

    /**
     * 获取广告列表
     */
    @Override
    public JSONObject getAds(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = adsDao.countAds();
        List<JSONObject> list = adsDao.getAds(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 根据id获取单条广告信息
     */
    @Override
    public JSONObject getAdsById(int id) {
        return adsDao.getAdsById(id);
    }

    /**
     * 修改广告信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateAds(JSONObject jsonObject) {
        adsDao.updateAds(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 新增广告信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject addAds(JSONObject jsonObject) {
        adsDao.addAds(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 删除广告信息
     */
    @Override
    public JSONObject removeAdsById(int id) {
        adsDao.removeAdsById(id);
        return CommonUtil.successJson();
    }
}
