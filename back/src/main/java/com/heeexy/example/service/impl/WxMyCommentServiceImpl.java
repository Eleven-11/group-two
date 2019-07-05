package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxMyCommentDao;
import com.heeexy.example.service.WxMyCommentService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.emjoy;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 20:17 2019/6/20
 * @ Description：
 * @Version:
 */
@Service
public class WxMyCommentServiceImpl implements WxMyCommentService {
    @Autowired
    private WxMyCommentDao wxMyCommentDao;

    /**
     * 后台表格显示评论列表
     * @param jsonObject (offSet,pageRow)
     * @return JSONObject
     */
    @Override
    public JSONObject getMyCommentList(JSONObject jsonObject) {
        //后台网页显示
        CommonUtil.fillPageParam(jsonObject);
        List<JSONObject> list = wxMyCommentDao.getMyCommentList(jsonObject);
        for (JSONObject ments : list) {
            String time = ments.getString("time");
            String times = emjoy.getTimes(time);
            ments.put("time", times);
        }
        int count = wxMyCommentDao.countMyComment(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
    /**
     * 前台某用户评论列表
     * @param jsonObject (onUserId)
     * @return JSONObject
     */
    @Override
    public JSONObject getMyCommentListById(JSONObject jsonObject) {
        //前台网页显示
        List<JSONObject> myCommentListById = wxMyCommentDao.getMyCommentListById(jsonObject);
        return CommonUtil.successPage(myCommentListById);
    }

    /**
     * 我的评论
     * @param jsonObject (keyword)
     * @return JSONObject
     */
    @Override
    public JSONObject myselfComment(JSONObject jsonObject) {
        List<JSONObject> list = wxMyCommentDao.myselfComment(jsonObject);
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
//              String commentstext = (String)lcment1.get("commentstext");
              String commentsname = (String)lcment1.get("commentsname");
              lcment1.remove("toComentName");
              if (toComentName !=null){
                   lcment1.put("commentsname", commentsname+"@"+toComentName+":");
            }else{
                   lcment1.put("commentsname", commentsname+":");
            }
          }
        }


        return  CommonUtil.successPage(list);
    }

    /**
     * 根据被评论idtoComnetId查找用户名
     * @param toComentId
     * @return String
     */
    @Override
    public String selectUserName(String toComentId) {
        String name = wxMyCommentDao.selectUserName(toComentId);
        return name;
    }
}
