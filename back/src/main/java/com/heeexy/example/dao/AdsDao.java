package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : 林成
 * @Description : 广告Dao层
 * @Date : 2019-06-18 10:25
 * @Version : 1.0
 **/
@Repository
public interface AdsDao {
    /**
     * 获取所有广告数量
     */
    int countAds();

    /**
     * 获取广告列表
     */
    List<JSONObject> getAds(JSONObject jsonObject);

    /**
     * 根据id获取单条广告信息
     */
    JSONObject getAdsById(int id);

    /**
     * 修改广告信息
     */
    int updateAds(JSONObject jsonObject);

    /**
     * 新增广告信息
     */
    int addAds(JSONObject jsonObject);

    /**
     * 删除广告信息
     */
    int removeAdsById(int id);

}
