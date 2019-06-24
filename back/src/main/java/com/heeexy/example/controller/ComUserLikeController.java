package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;

import com.heeexy.example.service.WxLikeService;

import com.heeexy.example.service.WxUserService;
import com.heeexy.example.util.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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
    public JSONObject getListUserLike(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId");
        return wxLikeService.getLikeByUserId(requestJson);
    }
    /**
     * 添加用户帖子点赞
     */
    @PostMapping("/addLike")
    public JSONObject addFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,userId,userUuId");
        JSONObject userByUsername = wxUserService.getUserByUsername(requestJson);
        String userState = userByUsername.getString("userState");
        if (userState =="1"){
            return null;
        }else {
            return wxLikeService.addLike(requestJson);
        }
    }
    /**
     * 修改用户帖子点赞状态
     */
    @PostMapping("/updateUserLike")
    public JSONObject updateFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "likeId,likeState");
        return wxLikeService.updateLikeByUserId(requestJson);
    }
    /**
     * 查找是否点赞该帖子信息
     */
    @PostMapping("/selectLike")
    public JSONObject selectFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,userId,postId");
        return wxLikeService.getLikeUserPost(requestJson);
    }
}
