package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.*;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 17:39 2019/6/17
 * @ Description：普通用户操作
 * @ Version: 1.0
 */
@RestController
@RequestMapping("/comUser")
public class ComUserController {
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private WxFansService wxFansService;
    @Autowired
    private WxLikeService wxLikeService;
    @Autowired
    private WxBrowserService wxBrowserService;
    @Autowired
    private WxMyPostService wxMyPostService;
    /*展示用户列表*/
    @GetMapping("/listUser")
    public JSONObject getListUser( HttpServletRequest request) {

        return wxUserService.getListUser(CommonUtil.request2Json(request));
    }
    /*展示用户关注列表*/
    @GetMapping("/listUserFans")
    public JSONObject getListUserFans(HttpServletRequest request) {

        return wxFansService.getListFans(CommonUtil.request2Json(request));
    }
    /*展示用户点赞列表*/
    @GetMapping("/listUserLike")
    public JSONObject getListUserLike(HttpServletRequest request) {

        return wxLikeService.getLikeList(CommonUtil.request2Json(request));
    }
    /*展示用户浏览记录列表*/
    @GetMapping("/listUserBrowse")
    public JSONObject getListUserBrowse(HttpServletRequest request) {

        return wxBrowserService.getBrowseList(CommonUtil.request2Json(request));
    }
    /*展示用户帖子列表*/
    @GetMapping("/listUserPost")
    public JSONObject getListUserPost(HttpServletRequest request) {

        return wxMyPostService.getMyPostList(CommonUtil.request2Json(request));
    }
    //添加普通用户
    @PostMapping("/addUser")
    public JSONObject addUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userName,userPhoto,userSex");
        return wxUserService.addByUser(requestJson);
    }
    //添加普通用户
    @PostMapping("/addGuestUser")
    public JSONObject addGuestUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userName,userPhoto,userSex");
        return wxUserService.addByUser(requestJson);
    }
    //修改用户粉丝数或封禁状态
    @PostMapping("/updateUser")
    public JSONObject updateUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, " userId,userFansf,userState");
        return wxUserService.updateFansfById(requestJson);
    }

    /**
     * 用户状态列表
     */

    @GetMapping("/getAllState")
    public JSONObject listRole() {
        return wxUserService.listUserState();
    }

}
