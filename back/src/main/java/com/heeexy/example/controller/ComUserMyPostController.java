package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WxBrowserService;
import com.heeexy.example.service.WxLikeService;
import com.heeexy.example.service.WxMyPostService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 12:14 2019/6/20
 * @ Description：用户帖子
 * @Version: 1.0
 */
@RestController
@RequestMapping("/comUserPost")
public class ComUserMyPostController {
    @Autowired
    private WxMyPostService wxMyPostService;
    /**
     * 网页展示用户帖子列表
     */
  /*  @GetMapping("/listUserPostById")
    public JSONObject getListUserPost(HttpServletRequest request) {

        return wxMyPostService.getMyPostListById(CommonUtil.request2Json(request));

    }*/
    /**
     * 删除用户帖子(修改帖子隐藏显示状态)
     */
    @RequiresPermissions("comuserpost:update")
    @PostMapping("/updateUserPost")
    public JSONObject updatePost(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "postId,myPostState");
        return wxMyPostService.updateMyPostById(requestJson);
    }
    /**
     * 前台删除用户帖子(修改帖子隐藏显示状态)
     */
  /*  @PostMapping("/updatePostByUser")
    public JSONObject updatePostByUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "postId,myPostState");
        return wxMyPostService.updateMyPostById(requestJson);
    }*/
    /**
     * 删除用户帖子(批量修改帖子隐藏显示状态)
     */
    @RequiresPermissions("comuserpost:update")
    @PostMapping("/updateUserPostMany")
    public JSONObject updatePostMany(@RequestBody JSONObject requestJson) {
        ArrayList<Map<String,String>> posts = (ArrayList<Map<String,String>>)requestJson.get("posts");
        for (Map<String, String> post : posts) {
            System.out.println(post);
            wxMyPostService.updateByPostIdMany(post);
        }
        return CommonUtil.successJson("success");
    }
    /**
     * 前台删除用户帖子(批量修改帖子隐藏显示状态)
     */
/*    @PostMapping("/updatePostManyByUser")
    public JSONObject updatePostManyByUser(@RequestBody JSONObject requestJson) {
        ArrayList<Map<String,String>> posts = (ArrayList<Map<String,String>>)requestJson.get("posts");
        for (Map<String, String> post : posts) {
            System.out.println(post);
            wxMyPostService.updateByPostIdMany(post);
        }
        return CommonUtil.successJson();
    }*/
    /**
     * 网页就算某用户发帖数量
     */
  /*  @GetMapping("/countMyPostNumber")
    public JSONObject countMyPostByUserId(HttpServletRequest request){

        return wxMyPostService.countMyPostByUserId(CommonUtil.request2Json(request));
    }*/
}
