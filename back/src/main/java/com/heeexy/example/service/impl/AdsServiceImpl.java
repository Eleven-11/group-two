package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.AdsDao;
import com.heeexy.example.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int countAds() {
        return adsDao.countAds();
    }

    @Override
    public int countEnableAds() {
        return adsDao.countEnableAds();
    }

    @Override
    public List<JSONObject> getAllAds(JSONObject jsonObject) {
        return adsDao.getAllAds(jsonObject);
    }

    @Override
    public List<JSONObject> getEnableAds(JSONObject jsonObject) {
        return adsDao.getEnableAds(jsonObject);
    }

    @Override
    public JSONObject getAdsById(int id) {
        return adsDao.getAdsById(id);
    }

    @Override
    public JSONObject updateAds(JSONObject jsonObject) {
        return adsDao.updateAds(jsonObject);
    }

    @Override
    public JSONObject addAds(JSONObject jsonObject) {
        return adsDao.addAds(jsonObject);
    }

    @Override
    public JSONObject removeAdsById(int id) {
        return adsDao.removeAdsById(id);
    }
}
