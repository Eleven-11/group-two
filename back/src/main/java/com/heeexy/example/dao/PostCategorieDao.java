package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: 陈强勇
 * @description: 帖子类别表Dao层
 * @date: 2019/6/18 17:47
 */
public interface PostCategorieDao {
    /**
     * 新增帖子类别
     */
    int addPostCategorie(JSONObject jsonObject);

    /**
     * 修改帖子类别的显示值
     */
    int updatePostCategorieDisplay(JSONObject jsonObject);

    /**
     * 修改帖子类别
     */
    int updatePostCategorie(JSONObject jsonObject);

    /**
     * 查询帖子类别数量
     */
    int countPostCategorie(JSONObject jsonObject);

    /**
     * 查询帖子类别列表
     */
    List<JSONObject> listPostCategorie(JSONObject jsonObject);

    /**
     * 校验帖子类别名是否已存在
     */
    int queryExistPostCategorieName(JSONObject jsonObject);
    /**
     * 校验帖子类别名是否已存在（不包含自己）
     */
    int queryExistPostCategorieNameTwo(JSONObject jsonObject);
    /**
     * 查询帖子类别的隐藏值
     */
    int queryExistCategorieDisplay(JSONObject jsonObject);
    /**
     * 查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     */
    List<JSONObject> getAllPostCategorie();
}
