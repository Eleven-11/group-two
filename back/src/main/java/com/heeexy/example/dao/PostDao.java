package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: PostDao
 * @description: todo
 * @date: 2019/6/18 9:06
 * @version: 1.0
 */
@Repository
public interface PostDao {
    int queryCountPost(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/6/18 9:07
     * @Param [jsonObject]
     * @return int
     */

    List<JSONObject> getAllPost(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/6/18 10:55
     * @Param [jsonObject]
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */

    int updatePostState(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/6/23 10:41
     * @Param [jsonObject]
     * @return int
     */

    JSONObject queryOnePost(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/6/23 19:45
     * @Param [jsonObject]
     * @return com.alibaba.fastjson.JSONObject
     */

    List<JSONObject> getAllSorts();

    int updatePost(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/6/25 20:13
     * @Param [jsonObject]
     * @return int
     */

    int updatePostTop(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO 
     * @Date  2019/6/27 11:00
     * @Param [jsonObject]
     * @return int
     */

    List<JSONObject> getSomeTag(JSONObject jsonObject)
    /*
     * @Author lover_tyn
     * @Description //TODO 
     * @Date  2019/6/27 11:01
     * @Param [need]
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     */
    ;
    
    
    






    

}
