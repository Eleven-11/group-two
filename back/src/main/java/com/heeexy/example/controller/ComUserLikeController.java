package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WxBrowserService;
import com.heeexy.example.service.WxLikeService;
import com.heeexy.example.service.WxMyPostService;
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

        CommonUtil.hasAllRequired(requestJson, "onUserId,userId");

        return wxLikeService.addLike(requestJson);
    }
    /**
     * 修改用户帖子点赞状态
     */
    @PostMapping("/updateUserLike")
    public JSONObject updateFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,userId,postId,likeState");
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
