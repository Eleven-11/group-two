package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxBrowserDao;
import com.heeexy.example.service.WxBrowserService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.emjoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 13:49 2019/6/18
 * @ Description：
 * @ Version:1.0
 */
@Service
public class WxBrowserServiceImpl implements WxBrowserService {
    @Autowired
    private WxBrowserDao wxBrowserDao;
    /**
     * 添加浏览记录
     * @param jsonObject (postId,onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject addBrowsePost(JSONObject jsonObject) {
         wxBrowserDao.addBrowsePost(jsonObject);
        return CommonUtil.successJson();
    }
    /**
     * 后台浏览记录数据表格显示
     * @param jsonObject (userName,offSet,pageRow)
     * @return JSONObject
     */
    @Override
    public JSONObject getBrowseList(JSONObject jsonObject) {
        //后台浏览记录数据
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = wxBrowserDao.getBrowseList(jsonObject);
        int count = wxBrowserDao.countBrowse(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    /**
     * 前台用户浏览记录信息
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject getBrowseByUser(JSONObject jsonObject) {
        //前台用户浏览记录
        List<JSONObject> browseByUser = wxBrowserDao.getBrowseByUser(jsonObject);

       return CommonUtil.successPage(browseByUser);
    }

    /**
     * 我的足迹
     * @param jsonObject (keyword)
     * @return JSONObject
     */
    @Override
    public JSONObject myBrowse(JSONObject jsonObject) {
        List<JSONObject> list = wxBrowserDao.myBrowse(jsonObject);
        for (JSONObject object : list) {
            String time = object.getString("time");
            String times = emjoy.getTimes(time);
            object.put("time", times);
            Object likestate = object.get("likestate");
            Object collectionstate = object.get("collectionstate");
            if (String.valueOf(likestate)==null){
                object.put("likestate", 0);
            }
            if (String.valueOf(collectionstate)==null){
                object.put("collectionstate", 0);
            }
            JSONArray lcments = object.getJSONArray("lcments");
            for (Object lcment : lcments) {
                JSONObject lcment1=(JSONObject) lcment;
                System.out.println(lcment1.get("toComentName"));
                String toComentName = (String)lcment1.get("toComentName");
//                String commentstext = (String)lcment1.get("commentstext");
                String commentsname = (String)lcment1.get("commentsname");
                lcment1.remove("toComentName");
                if (toComentName !=null){
                    lcment1.put("commentsname", commentsname+"@"+toComentName+":");
                }else{
                    lcment1.put("commentsname", commentsname+":");
                }
            }
        }        return CommonUtil.successPage(list);
    }
}
