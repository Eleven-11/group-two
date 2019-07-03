package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.xmlbeans.impl.jam.JSourcePosition;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: PostDao
 * @description: 后台管理帖子
 * @date: 2019/6/18 9:06
 * @version: 1.0
 */
@Repository
public interface PostDao {
    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:25
     * @Param [jsonObject]
     * @return int
     */
    int queryCountPost(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:25
     * @Param [jsonObject]
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    List<JSONObject> getAllPost(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:26
     * @Param [jsonObject]
     * @return int
     */
    int updatePostState(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:26
     * @Param [jsonObject]
     * @return com.alibaba.fastjson.JSONObject
     */
    JSONObject queryOnePost(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:26
     * @Param []
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    List<JSONObject> getAllSorts();

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:26
     * @Param [jsonObject]
     * @return int
     */
    int updatePost(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:27
     * @Param [jsonObject]
     * @return int
     */
    int updatePostTop(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:27
     * @Param [jsonObject]
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    List<JSONObject> getSomeTag(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:27
     * @Param [jsonObject]
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    List<JSONObject> getNewTagId(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:28
     * @Param [jsonObject]
     * @return int
     */
    int addPostTag(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:28
     * @Param [jsonObject]
     * @return java.util.List<java.lang.Integer>
     */
    List<Integer> getDeleteTagId(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:28
     * @Param [jsonObject]
     * @return int
     */
    int deleteTag(JSONObject jsonObject);

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:28
     * @Param []
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    List<JSONObject> getFirstTag();

    /**
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/7/1 15:28
     * @Param [jsonObject]
     * @return int
     */
    int deleteImg(JSONObject jsonObject);



    
    

    
    
    
    
    
    

}
