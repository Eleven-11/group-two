package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.BusinessSubwayDao;
import com.heeexy.example.service.BusinessSubwayService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description:帖子标签表/增删改查的实现
 * @date: 2019/6/19
 * @vevsion 2019/6/19 12:34
 */
@Service
public class BusinessSubwayServiceImpl implements BusinessSubwayService {
    @Autowired
    private BusinessSubwayDao businessSubwayDao;
    /**
     * 新增帖子标签
     */

    @Override
    public JSONObject addBusinessSubway(JSONObject jsonObject) {
        int exist = businessSubwayDao.queryExistBusinessSubwayName( jsonObject );
        if( exist > 0 ){
            int i = businessSubwayDao.queryExistBusinessSubwayDisplay(jsonObject);
            if(i==1){
                return CommonUtil.errorJson( ErrorEnum.E_10009);
            }
        }
        businessSubwayDao.addBusinessSubway(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 修改帖子标签状态
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject updateBusinessSubwayDisplay(JSONObject jsonObject) {
        businessSubwayDao.updateBusinessSubwayDisplay( jsonObject );
        return CommonUtil.successJson();
    }
    /**
     * 修改帖子标签
     */
    @Override
    public JSONObject updateBusinessSubway(JSONObject jsonObject) {
        businessSubwayDao.updateBusinessSubway( jsonObject );
        return CommonUtil.successJson();
    }
    /**
     * 查询帖子标签列表
     */
    @Override
    public JSONObject listBusinessSubway(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = businessSubwayDao.countBusinessSubway(jsonObject);
        List<JSONObject> list = businessSubwayDao.listBusinessSubway(jsonObject);

        return CommonUtil.successPage( jsonObject,list,count );
    }
    /**
     * 查询所有的帖子标签
     * 在添加/修改帖子标签的时候要使用此方法
     */
    @Override
    public JSONObject getAllBusinessSubway() {
        List<JSONObject> roles = businessSubwayDao.getAllBusinessSubway();
        return CommonUtil.successJson(roles);
    }
}
