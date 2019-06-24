package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-06-18 14:36
 * @Version : 1.0
 **/
public interface AdsService {
    /**
     * 获取所有广告数量
     */
    int countAds();

    /**
     * 获取启用广告数量
     */
    int countEnableAds();

    /**
     * 获取所有广告信息
     */
    List<JSONObject> getAllAds(JSONObject jsonObject);

    /**
     * 获取启用广告信息
     */
    List<JSONObject> getEnableAds(JSONObject jsonObject);

    /**
     * 获取单条广告信息
     */
    JSONObject getAdsById(int id);

    /**
     * 修改广告信息
     */
    JSONObject updateAds(JSONObject jsonObject);

    /**
     * 新增广告信息
     */
    JSONObject addAds(JSONObject jsonObject);

    /**
     * 删除广告信息
     */
    JSONObject removeAdsById(int id);
}
