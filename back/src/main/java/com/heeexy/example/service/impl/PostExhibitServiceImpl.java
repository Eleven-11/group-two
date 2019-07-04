package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.PostExhibitDao;
import com.heeexy.example.service.PostExhibitService;
import com.heeexy.example.util.CommonUtil;
import net.sf.json.processors.JsDateJsonBeanProcessor;
import org.apache.commons.codec.language.bm.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
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
 * @Param [jsonObject]:int id，int userId
 * @return com.alibaba.fastjson.JSONObject
 */
    @Override
    public JSONObject getIndexPost(JSONObject jsonObject) {
        int test = (int) jsonObject.get("id");
        JSONObject condition = new JSONObject();
        condition.put("getType", null);
        condition.put("tagType", null);
        condition.put("pageNum",jsonObject.get("pageNum"));
        List<JSONObject> list = null;
        if(test== 1){
            condition.put("isTop","1___");
            list = generateList(condition);

        }else if(test==2) {
            condition.put("isTop", "%1__");
            condition.put("tagType", postExhibitDao.queryTagType("热门商圈"));
            list = generateList(condition);
        }else if(test==3){
            condition.put("isTop", "%1_");
            condition.put("tagType", postExhibitDao.queryTagType("地铁周边"));
            list = generateList(condition);
        }else if(test==4){
            System.out.println("impl"+test);
            condition.put("isTop", "%1");
            condition.put("getType", 4);
            list = generateList(condition);
        }else{
            condition.put("isTop",null);
            condition.put("getType", 5);
            list = postExhibitDao.getNormalPost(condition);
        }
        //生成点赞状态，收藏状态，日期
        for(int i=0;i<list.size();i++){
            int userId = (int) jsonObject.get("uid");
            list = handleList(list,userId);
        }
        return CommonUtil.successPage(list);
    }

    @Override
    public JSONObject queryThePost(JSONObject requestJson) {
        JSONObject condition = new JSONObject();
        condition.put("postId",requestJson.get("tid"));
        condition.put("userId",requestJson.get("uid"));
        JSONObject the = postExhibitDao.queryThePost(requestJson);
        the.put("likeState",postExhibitDao.isLike(condition));
        the.put("collectState",postExhibitDao.isCollect(condition));
        int i =  requestJson.getInteger("uid");
        int j = the.getInteger("uId");
        if(i==j){
            the.put("seePeople",the.get("realView"));
            the.put("likePeople",the.get("realLike"));
        }else {
            the.put("seePeople",the.getInteger("realView")+the.getInteger("viewOff"));
            the.put("likePeople",the.getInteger("realLike")+the.getInteger("likeOff"));
        }
        the.put("time",handleTime(the.getLong("time")));
        the.remove("likeOff");
        the.remove("viewOff");
        the.remove("realLike");
        the.remove("realView");
        return CommonUtil.successJsonOne(the);
    }

    @Override
    public JSONObject getThePost(JSONObject jsonObject) {
        List<JSONObject> list = postExhibitDao.getThePost(jsonObject);
        int userId = (int) jsonObject.get("uid");
        list = handleList(list,userId);
        return CommonUtil.successPage(list);
    }

    @Override
    public JSONObject addPost(JSONObject jsonObject) {
        int soetId = postExhibitDao.querySortId(jsonObject);
        jsonObject.put("sortId",soetId);

        return null;
    }


    private List<JSONObject> generateList(JSONObject jsonObject){
        List<JSONObject> list = postExhibitDao.getTopPost(jsonObject);
        //设置帖子的置顶状态
        for(int i=0;i<list.size();i++){
            list.get(i).put("isTop",1);
        }
        List<JSONObject> normalPost = postExhibitDao.getNormalPost(jsonObject);
        for(int i=0;i<normalPost.size();i++){
            normalPost.get(i).put("isTop",0);
            list.add(normalPost.get(i));
        }
        return list;
    }

    private List<JSONObject> handleList(List<JSONObject> list,int userId){
        for(int i=0;i<list.size();i++){
            int postId = (int)list.get(i).get("tId");
            JSONObject object = new JSONObject();
            object.put("userId",userId);
            object.put("postId",postId);
            list.get(i).put("likeState",postExhibitDao.isLike(object));
            list.get(i).put("collectState",postExhibitDao.isCollect(object));
            String time = handleTime(list.get(i).getLong("timet"));
            list.get(i).put("time",time);
        }
        return list;
    }

    private String handleTime(long that){
        long now = System.currentTimeMillis();
        String result = null;
        int second= (int)(now/1000-that);
        int minute =  (int)second/60;
        int hour = (int)minute/60;
        int day = (int)hour/24;
        int week = (int)day/7;
        int month = (int)day/30;
        if(second<60){
           result = second+" seconds ago";
        }else if(minute<60){
            result = minute+" minutes ago";
        }else if(hour<24) {
            result = hour+" hours ago";
        }else if(day<7){
            result = day +" days ago";
        }else if(week<4){
            result = week +" weeks ago";
        }else if(month<36){
            result = month +" months ago";
        }
        return result;
    }


}
