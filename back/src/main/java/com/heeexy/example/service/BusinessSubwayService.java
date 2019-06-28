package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.bean.BusinessSubway;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: chengqiangyong
 * @description: 帖子标签表/增删改查
 * @date: 2019/6/19 12:31
 * @vevsion 1.0
 */
public interface BusinessSubwayService {
    /**
     * 新增帖子类别
     */
    JSONObject addBusinessSubway(JSONObject jsonObject);

    /**
     * 修改帖子类别的显示值
     */
    JSONObject updateBusinessSubwayDisplay(JSONObject jsonObject);

    /**
     * 修改帖子类别
     */
    JSONObject updateBusinessSubway(JSONObject jsonObject);

    /**
     * 查询帖子类别列表
     */
    JSONObject listBusinessSubway(JSONObject jsonObject);

    /**
     * 查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     */
    JSONObject getAllBusinessSubway();

    /**
     * 导入导出
     */
    List<BusinessSubway> selectUsers();

    boolean batchImport(String fileName, MultipartFile file) throws Exception;

    boolean batchImports(String fileName, MultipartFile file) throws Exception;
}
