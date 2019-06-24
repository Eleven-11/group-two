package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author: PostService
 * @description: todo
 * @date: 2019/6/17 16:57
 * @version: 1.0
 */

public interface PostService {
    
    
    JSONObject getAllPost(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/6/17 17:02
     * @Param []
     * @return com.alibaba.fastjson.JSONObject
     */

    JSONObject updatePostState(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/6/23 10:39
     * @Param [jsonObject]
     * @return com.alibaba.fastjson.JSONObject
     */

    JSONObject queryOnePost(JSONObject jsonObject);
    /*
     * @Author lover_tyn
     * @Description //TODO
     * @Date  2019/6/23 19:44
     * @Param [jsonObject]
     * @return com.alibaba.fastjson.JSONObject
     */

    JSONObject getAllSorts();








    
}
