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
     * @param jsonObject  businessSubwayName(标签名),superiorId(上级id)
     * @return
     */
    JSONObject addBusinessSubway(JSONObject jsonObject);
    /**
     * 修改帖子类别的显示值
     * @param jsonObject  businessSubwayId（标签编号）display(状态值)
     * @return
     */
    JSONObject updateBusinessSubwayDisplay(JSONObject jsonObject);
    /**
     * 修改帖子类别
     * @param jsonObject businessSubwayName(标签名),superiorId(上级id)
     * @return
     */
    JSONObject updateBusinessSubway(JSONObject jsonObject);
    /**
     * 查询帖子类别列表
     * @param jsonObject  input(输入框的内容，根据标签名模糊)
     * @return
     */
    JSONObject listBusinessSubway(JSONObject jsonObject);
    /**
     *查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     * @return
     */
    JSONObject getAllBusinessSubway();

    /**
     * 增量导入
     * @param fileName 表格的文件名
     * @param file 表格的路径
     * @return
     * @throws Exception
     */
    JSONObject  batchImport(String fileName, MultipartFile file) throws Exception;
    /**
     * 覆盖导入
     * @param fileName 表格的文件名
     * @param file 表格的路径
     * @return
     * @throws Exception
     */
    JSONObject  batchImports(String fileName, MultipartFile file) throws Exception;
}
