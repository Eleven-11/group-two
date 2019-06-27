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
     * 获取广告列表
     */
    JSONObject getAds(JSONObject jsonObject);

    /**
     * 根据id获取单条广告信息
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
