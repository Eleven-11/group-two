package com.heeexy.example.controller.api;

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
@RequestMapping("/api/comUserLikeWeb")
public class ComUserLikeWebController {
    @Autowired
    private WxLikeService wxLikeService;
    @Autowired
    private WxUserService wxUserService;
    /**
     * 前台展示用户点赞列表
     */
    @GetMapping("/listUserLike")
    public JSONObject getListUserLike(HttpServletRequest request) {

        return wxLikeService.getLikeByUserId(CommonUtil.request2Json(request));
    }
    /**
     * 前台展示用户点赞列表计数
     */
    @GetMapping("/userLikeNumber")
    public JSONObject getuserLikeNumber(HttpServletRequest request) {

        return wxLikeService.countPostLikeByUserId(CommonUtil.request2Json(request));
    }
    /**
     * 前台添加用户帖子点赞
     */
    @PostMapping("/postlike")
    public JSONObject addLikeByUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,likeId,postId,userId");
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
    /**
     * 我的点赞列表
     */
    @PostMapping("/mylike")
    public JSONObject searchlike(HttpServletRequest request) {

        return wxLikeService.myLike(CommonUtil.request2Json(request));
    }
}
