package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description: 帖子类别表Dao层
 * @date: 2019/6/18 17:47
 * @vevsion 1.0
 */
public interface PostCategorieDao {

//************************ 后台************************
    /**
     * 新增帖子类别
     * @param jsonObject categoriesName(类别名),categoriesImg(类别图片)
     * @return
     */
    int addPostCategorie(JSONObject jsonObject);
    /**
     * 修改帖子类别的状态值
     * @param jsonObject categoriesId（类别id）,display(状态值)
     * @return
     */
    int updatePostCategorieDisplay(JSONObject jsonObject);
    /**
     * 修改帖子类别
     * @param jsonObject
     * @return
     */
    int updatePostCategorie(JSONObject jsonObject);
    /**
     * 查询帖子类别数量
     * @param jsonObject
     * @return
     */
    int countPostCategorie(JSONObject jsonObject);
    /**
     * 查询帖子类别列表
     * @param jsonObject
     * @return
     */
    List<JSONObject> listPostCategorie(JSONObject jsonObject);
    /**
     * 校验帖子类别名是否已存在
     * @param jsonObject
     * @return
     */
    int queryExistPostCategorieName(JSONObject jsonObject);
    /**
     * 校验帖子类别名是否已存在（不包含自己）
     * @param jsonObject
     * @return
     */
    int queryExistPostCategorieNameTwo(JSONObject jsonObject);
    /**
     * 查询帖子类别的隐藏值
     * @param jsonObject
     * @return
     */
    int queryExistCategorieDisplay(JSONObject jsonObject);
    /**
     * 查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     * @return
     */
     List<JSONObject> getAllPostCategorie();
//   ************************ 小程序前台************************
    /**
     * 查询帖子类别列表（不包含隐藏的）
     * @return
     */
    List<JSONObject> RgetAllPostCategorie();



}
