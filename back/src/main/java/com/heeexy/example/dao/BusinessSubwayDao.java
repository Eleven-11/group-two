package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.bean.BusinessSubway;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description: 帖子标签表Dao层
 * @date: 2019/6/18 17:47
 * @vevsion 1.0
 */
public interface BusinessSubwayDao {
    /**
     * 新增帖子类别
     * @param jsonObject  businessSubwayName(标签名),superiorId(上级id)
     * @return
     */
    int addBusinessSubway(JSONObject jsonObject);
    /**
     * 修改帖子类别
     * @param jsonObject businessSubwayName(标签名),superiorId(上级id)
     * @return
     */
    int updateBusinessSubway(JSONObject jsonObject);
    /**
     * 修改帖子类别的显示值
     * @param jsonObject  businessSubwayId（标签编号）display(状态值)
     * @return
     */
    int updateBusinessSubwayDisplay(JSONObject jsonObject);
    /**
     * 查询帖子标签数量
     * @param jsonObject
     * @return
     */
    int countBusinessSubway(JSONObject jsonObject);
    /**
     * 查询帖子类别列表
     * @param jsonObject  input(输入框的内容，根据标签名模糊)
     * @return
     */
    List<JSONObject> listBusinessSubway(JSONObject jsonObject);
    /**
     * 校验帖子类标签是否已存在
     * @param jsonObject businessSubwayName(标签名)
     * @return
     */
    int queryExistBusinessSubwayName(JSONObject jsonObject);

    /**
     * 查询帖子标签的隐藏值
     * @param jsonObject display(状态值)
     * @return
     */
    int queryExistBusinessSubwayDisplay(JSONObject jsonObject);
    /**
     *查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     * @return
     */
    List<JSONObject> getAllBusinessSubway();
    /**
     * 查询帖子标签的上级id,根据名
     * @param jsonObject businessSubwayName(标签名)
     * @return
     */
    List<JSONObject> queryExistBusinessSubwayByName(JSONObject jsonObject);
    /**
     * 查询帖子标签的名,根据id
     * @param jsonObject  businessSubwayId（标签编号）
     * @return
     */
    int queryExistBusinessSubwayById(JSONObject jsonObject);

    /**
     * 上传导入
     */
    Integer selectSuperior(String businesssubwayname);
    /**
     * 增加字段
     * @param businessSubway
     */
    void addUser(BusinessSubway businessSubway);
    int addbusinessSubwayName(String businessaubwayname);
    int selectByName(Integer businessaubwayid);
    /**
     * 删除记录
     * @return
     */
    int removeBusinessSubway();
    /**
     * 计算增加的记录数
     * @param businessaubwayname businessSubwayName(标签名)
     * @return
     */
    int countbusinessSubwayName(String businessaubwayname);


}
