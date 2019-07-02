package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 20:43 2019/6/19
 * @ Description：我的帖子
 * @Version: 1.0
 */
public interface WxMyPostDao {

     /**
          * @methodsName: getMyPostList
          * @description: 后台每个用户自己的帖子记录
          * @param:  JSONObject
          * @return: List<JSONObject>
          */
    List<JSONObject> getMyPostList(JSONObject jsonObject);
     /**
          * @methodsName: countMyPost
          * @description: 后台计数
          * @param:  JSONObject
          * @return: int
          */
    int countMyPost(JSONObject jsonObject);

     /**
          * @methodsName: updateMyPostById
          * @description: 修改自己帖子状态
          * @param:  JSONObject
          * @return: int
          */
    int updateMyPostById(JSONObject jsonObject);
     /**
          * @methodsName: getMyPostListById
          * @description: 前台用户自己的帖子记录
          * @param:  JSONObject
          * @return:   List<JSONObject>
          */
    List<JSONObject> getMyPostListById(JSONObject jsonObject);
     /**
          * @methodsName: countMyPostByUserId
          * @description: 计算该用户发帖数量
          * @param:  JSONObject
          * @return: List<JSONObject>
          */
     List<JSONObject> countMyPostByUserId(JSONObject jsonObject);
     /**
          * @methodsName: updateByPostIdMany
          * @description: 批量删除帖子
          * @param:  JSONObject
          * @return: int
          */
    int updateByPostIdMany(Map map);
     /**
          * @methodsName: getPostCollectListByUserId
          * @description: 前台某用户收藏列表
          * @param:  JSONObject
          * @return: List<JSONObject>
          */
    List<JSONObject> getPostCollectListByUserId(JSONObject jsonObject);
     /**
          * @methodsName: countPostCollectByUserId
          * @description: 某用户收藏帖子数
          * @param:  JSONObject
          * @return:  List<JSONObject>
          */
    List<JSONObject> countPostCollectByUserId(JSONObject jsonObject);
}
