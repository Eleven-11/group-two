package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: chenqiangyong
 * @description:模块/增删改查
 * @date: 2019/6/18 20:54
 * @vevsion 1.0
 */
public interface PostCategorieService {
//   ************************ 后台************************
    /**
     * 新增帖子类别
     */
    JSONObject addPostCategorie(JSONObject jsonObject);

    /**
     * 修改帖子类别的显示值
     */
    JSONObject updatePostCategorieDisplay(JSONObject jsonObject);

    /**
     * 修改帖子类别
     */
    JSONObject updatePostCategorie(JSONObject jsonObject);

    /**
     * 查询帖子类别列表
     */
    JSONObject listPostCategorie(JSONObject jsonObject);

    /**
     * 查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     */
    JSONObject getAllPostCategorie();


//   ************************ 小程序前台************************

    /**
     * 查询帖子类别列表（不包含隐藏的）
     */
    JSONObject RgetAllPostCategorie();
}
