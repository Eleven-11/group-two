package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;

import com.heeexy.example.service.WxLikeService;

import com.heeexy.example.service.WxUserService;
import com.heeexy.example.util.CommonUtil;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @ Author     ：良优
 * @ Date       ：Created in 12:14 2019/6/20
 * @ Description：点赞记录
 * @ Version: 1.0
 */
@RestController
@RequestMapping("/comUserLike")
public class ComUserLikeController {
    @Autowired
    private WxLikeService wxLikeService;
    @Autowired
    private WxUserService wxUserService;
    /**
     * 展示用户点赞列表
     */
    @GetMapping("/listUserLike")
    public JSONObject getListUserLike(HttpServletRequest request) {

        return wxLikeService.getLikeByUserId(CommonUtil.request2Json(request));

    }
    /**
     * 后台添加用户帖子点赞
     */
    @RequiresPermissions("comuserlike:add")
    @PostMapping("/addUserLikes")
    public JSONObject addLike(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,likeId,postId");
//       JSONObject userByUsername = wxUserService.getUserByUsername(requestJson);
//        String userState = userByUsername.getString("userState");
//        if (userState =="1"){
//            return null;
//        }else {
//            return wxLikeService.addLike(requestJson);
//        }
        return wxLikeService.addLike(requestJson);
    }
    /**
     * 后台修改用户帖子点赞状态
     */
    @RequiresPermissions("comuserlike:delete")
    @PostMapping("/updateUserLike")
    public JSONObject updateLike(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "likeId");
        return wxLikeService.updateLikeByUserId(requestJson);
    }
    /**
     * 前台添加用户帖子点赞
     */
    @PostMapping("/addUserLikes")
    public JSONObject addLikeByUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,likeId,postId");
       JSONObject userByUsername = wxUserService.getUserByUsername(requestJson);
        String userState = userByUsername.getString("userState");
        if (userState =="1"){
            return null;
        }else {
            return wxLikeService.addLike(requestJson);
        }
    }
    /**
     * 前台修改用户帖子点赞状态（取消）
     */
    @PostMapping("/updateLikeByUser")
    public JSONObject updateLikeByUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "likeId,onUserId");
        return wxLikeService.updateLikeByUserId(requestJson);
    }
    /**
     * 查找是否点赞该帖子信息
     */
    @PostMapping("/selectLike")
    public JSONObject selectLike(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,userId,postId");
        return wxLikeService.getLikeUserPost(requestJson);
    }
}
