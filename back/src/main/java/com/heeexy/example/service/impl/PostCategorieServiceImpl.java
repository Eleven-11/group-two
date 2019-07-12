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
 * @description:帖子类别/增删改查的实现
 * @date: 2019/6/18
 * @vevsion 1.0
 */
@Service
public class PostCategorieServiceImpl implements PostCategorieService {
    @Autowired
    private PostCategorieDao postCategorieDao;


//   ************************ 后台************************

    /**
     * 新增帖子类别
     * @param jsonObject categoriesName(类别名),categoriesImg(类别图片)
     * @return
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
     * @param jsonObject categoriesId（类别id）,display(状态值)
     * @return
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
     * @param jsonObject categoriesId（类别id）,categoriesName(类别名),categoriesImg(类别图片)
     * @return
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
     * @param jsonObject
     * @return
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
     * @return
     */
    @Override
    public JSONObject getAllPostCategorie() {
        List<JSONObject> roles = postCategorieDao.getAllPostCategorie();
        return CommonUtil.successPage(roles);
    }




//   ************************ 小程序前台************************

    /**
     * 查询帖子类别列表（不包含隐藏的）
     * @return
     */
    @Override
    public JSONObject RgetAllPostCategorie() {
        List<JSONObject> roles = postCategorieDao.RgetAllPostCategorie();
        for (JSONObject role : roles) {
            role.put("title",role.remove("categoriesName"));
            role.put("id",role.remove("categoriesId"));
            role.put("imgUrl",role.remove("categoriesImg"));
        }
        return CommonUtil.successPage(roles);
    }
}
