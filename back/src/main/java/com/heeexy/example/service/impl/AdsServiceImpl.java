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
     * @param jsonObject （key: status 状态 可选；pageRow 一页条数；pageNum 页数）
     * @return JSONObject （key：code 状态码；msg 消息；info{totalPage 总页数；list[] 数据列表；totalCount 数据总条数} 数据）
     *         list[（id 编号；picture 图片路径；link 广告链接；title 广告标题；status 状态）]
     */
    @Override
    public JSONObject getAds(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = adsDao.countAds();
        List<JSONObject> list = adsDao.getAds(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 根据广告编号获取一条广告信息
     * @param id 编号 必选
     * @return JSONObject （key：code 状态码；msg 消息；info{} 数据）
     *         info（id 编号；picture 图片路径；link 广告链接；title 广告标题；status 状态）
     */
    @Override
    public JSONObject getAdsById(int id) {
        return adsDao.getAdsById(id);
    }

    /**
     * 修改广告
     * @param jsonObject （key：id 编号 必选；picture 图片路径 必选；title 标题 必选；link 链接 必选；status 状态 必选）
     * @return JSONObject （key：code 状态码；msg 消息；info{} 数据）
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject updateAds(JSONObject jsonObject) {
        adsDao.updateAds(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 添加广告
     * @param jsonObject （picture 图片路径 必选；title 标题 必选；link 链接 必选）
     * @return JSONObject （key：code 状态码；msg 消息；info{} 数据）
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
