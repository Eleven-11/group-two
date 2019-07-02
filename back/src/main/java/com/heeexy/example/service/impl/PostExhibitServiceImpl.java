package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PostExhibitDao;
import com.heeexy.example.service.PostExhibitService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lover_tyn
 * @description: todo
 * @date: 2019/7/2 20:33
 * @version: 1.0
 */
@Service
public class PostExhibitServiceImpl implements PostExhibitService {
    @Autowired
    private PostExhibitDao postExhibitDao;
    /**
 * @Author lover_tyn
 * @Description //TODO
 * @Date  2019/7/2 21:54
 * @Param [jsonObject]:String type,
 * @return com.alibaba.fastjson.JSONObject
 */
    @Override
    public JSONObject getIndexPost(JSONObject jsonObject) {
        String test = (String) jsonObject.get("type");
        JSONObject condition = new JSONObject();
        List<JSONObject> list = null;
        if(test=="推荐"){
            condition.put("isTop","1___");
            condition.put("tagType",null);
            list = postExhibitDao.getTopPost(condition);
            //设置帖子的置顶状态
            for(int i=0;i<list.size();i++){
                list.get(i).put("isTop",1);
            }
            condition.put("isTop",null);
            List<JSONObject> normalPost = postExhibitDao.getNormalPost(condition);
            for(int i=0;i<normalPost.size();i++){
                list.add(normalPost.get(i));
            }
        }else if(test=="热门商圈"){

        }
        return CommonUtil.successPage(list);
    }


}
