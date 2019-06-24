package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PostCategorieDao;
import com.heeexy.example.service.PostCategorieService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description:模块/增删改查的实现
 * @date: 2019/6/18
 * @vevsion 1.0
 */
@Service
public class PostCategorieServiceImpl implements PostCategorieService {
    @Autowired
    private PostCategorieDao postCategorieDao;

    /**
     * 新增帖子类别
     */
    @Override
    public JSONObject addPostCategorie(JSONObject jsonObject) {
        int exist = postCategorieDao.queryExistPostCategorieName(jsonObject);
        if( exist > 0 ){
                return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        postCategorieDao.addPostCategorie(jsonObject);
        return CommonUtil.successJson();
    }
    /**
     * 修改帖子类别状态
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject updatePostCategorieDisplay(JSONObject jsonObject) {
        postCategorieDao.updatePostCategorieDisplay(jsonObject);
        return CommonUtil.successJson();
    }


    /**
     * 修改帖子类别
     */
    @Override
    public JSONObject updatePostCategorie(JSONObject jsonObject) {
        int exist = postCategorieDao.queryExistPostCategorieNameTwo( jsonObject );
        if( exist== 1 ){
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        postCategorieDao.updatePostCategorie(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 查询帖子类别列表
     */
    @Override
    public JSONObject listPostCategorie(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = postCategorieDao.countPostCategorie(jsonObject);
        List<JSONObject> list = postCategorieDao.listPostCategorie( jsonObject );
        return CommonUtil.successPage( jsonObject,list,count );
    }
    /**
     * 查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     */
    @Override
    public JSONObject getAllPostCategorie() {
        List<JSONObject> roles = postCategorieDao.getAllPostCategorie();
        return CommonUtil.successPage(roles);
    }
}
