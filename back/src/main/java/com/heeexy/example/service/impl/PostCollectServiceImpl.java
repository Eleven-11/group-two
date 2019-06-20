package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PostCollectDao;
import com.heeexy.example.service.PostCategorieService;
import com.heeexy.example.service.PostCollectService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description:帖子收藏的/增删改查的实现
 * @date: 2019/6/20 10:52
 * @vevsion
 */

@Service
public class PostCollectServiceImpl implements PostCollectService {


    @Autowired
    private PostCollectDao postCollectDao;


    /**
     * 添加帖子收藏
     */
    @Override
    public JSONObject addPostCollect(JSONObject jsonObject) {
        int exist = postCollectDao.queryExistPostCollectPU( jsonObject );
        if( exist > 0 ){
            int dexist = postCollectDao.queryExistPostCollectDisplay( jsonObject );
            if( dexist == 1 ){
                postCollectDao.updatePostCollectDisplay( jsonObject );
                return  CommonUtil.successJson();
            }else if( dexist == 0 ){
                postCollectDao.updatePostCollectDisplayTwo( jsonObject );
                return  CommonUtil.successJson();
            }
        }
        postCollectDao.addPostCollect( jsonObject );
        return CommonUtil.successJson();
    }

    /**
     * 删除帖子收藏
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject updatePostCollectDisplay(JSONObject jsonObject) {
        postCollectDao.updatePostCollectDisplay( jsonObject );
        return  CommonUtil.successJson();
    }

    /**
     * 修改帖子收藏
     */
    @Override
    public JSONObject updatePostCollect(JSONObject jsonObject) {
        postCollectDao.updatePostCollect( jsonObject );
        return CommonUtil.successJson();
    }

    /**
     * 查询帖子收藏列表
     */
    @Override
    public JSONObject listPostCollect(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = postCollectDao.countPostCollect( jsonObject );
        List<JSONObject> list = postCollectDao.listPostCollect( jsonObject );
        return CommonUtil.successPage( jsonObject,list,count );
    }

    /**
     * 查询所有的帖子收藏
     * 在添加/修改帖子收藏的时候要使用此方法
     */
    @Override
    public JSONObject getAllPostCollect() {
        List<JSONObject> roles = postCollectDao.getAllPostCollect();
        return CommonUtil.successJson(roles);
    }
}
