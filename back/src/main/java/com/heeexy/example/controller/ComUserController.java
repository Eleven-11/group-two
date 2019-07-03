package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.WxMyCommentDao;
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
    @Autowired
    private WxMyCommentService wxMyCommentService;
    //cqy
    @Autowired
    private ModuleService moduleService;
    /**
     * 展示用户列表
     */
    /**
     * 展示用户列表
     * @param request
     * @return
     */
    @RequiresPermissions("comuser:list")
    @GetMapping("/listUser")
    public JSONObject getListUser( HttpServletRequest request) {
        return wxUserService.getListUser(CommonUtil.request2Json(request));
    }
    /**
     * 展示用户关注列表
      */
    @RequiresPermissions("comuserfans:list")
    @GetMapping("/listUserFans")
    public JSONObject getListUserFans(HttpServletRequest request) {

        return wxFansService.getListFans(CommonUtil.request2Json(request));
    }
    /**
     * 展示用户点赞列表
     * */
    @RequiresPermissions("comuserlike:list")
    @GetMapping("/listUserLike")
    public JSONObject getListUserLike(HttpServletRequest request) {

        return wxLikeService.getLikeList(CommonUtil.request2Json(request));
    }
    /**
     * 后台展示用户浏览记录列表
     */
    @RequiresPermissions("comuserbrowse:list")
    @GetMapping("/listUserBrowse")
    public JSONObject getListUserBrowse(HttpServletRequest request) {

        return wxBrowserService.getBrowseList(CommonUtil.request2Json(request));
    }
    /**
     * 展示用户帖子列表
     */
    @RequiresPermissions("comuserpost:list")
    @GetMapping("/listUserPost")
    public JSONObject getListUserPost(HttpServletRequest request) {

        return wxMyPostService.getMyPostList(CommonUtil.request2Json(request));
    }
    /**
     * 展示用户评论记录列表
     */
    @RequiresPermissions("comusercomment:list")
    @GetMapping("/listUserComment")
    public JSONObject getlistUserComment(HttpServletRequest request) {

        return wxMyCommentService.getMyCommentList(CommonUtil.request2Json(request));
    }
    /**
     * 查找用户
     */
    @GetMapping("/getUserByName")
    public JSONObject getUserByName(HttpServletRequest request){
        return wxUserService.getUserByUsername(CommonUtil.request2Json(request));
    }
    /**
     * 添加普通用户
     */
 /*   @PostMapping("/addUser")
    public JSONObject addUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userUuid,userName,userPhoto,userSex");
        String userUuid = requestJson.getString("userUuid");
        if (userUuid ==null ){
            return wxUserService.addGuestUser(requestJson);
        }else {
            return wxUserService.addByUser(requestJson);
        }
    }*/
    /**
     * 添加游客用户
     */
 /*   @PostMapping("/addGuestUser")
    public JSONObject addGuestUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userUuid,userName,userPhoto,userSex");
        return wxUserService.addGuestUser(requestJson);
    }*/
        /**
        *修改用户粉丝数
        * */
    @RequiresPermissions("comuser:updatefans")
    @PostMapping("/updateUserFans")
    public JSONObject updateUserFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, " userId,userFansf");
        return wxUserService.updateFansfById(requestJson);
    }
    /**
     *修改用户封禁状态
     * */
    @RequiresPermissions("comuser:updatestate")
    @PostMapping("/updateUserState")
    public JSONObject updateUserState(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, " userId,userState");

        JSONObject jsonObject = wxUserService.updateStateByUserId( requestJson );
        //cqy操作
        Object userState = requestJson.get("userState");
        Integer userStates = Integer.valueOf(userState.toString());
        if(userStates==0){
            moduleService.addMessageF(requestJson);
        }
        return jsonObject;
    }


    /**
     * 计算用户粉丝数
      */
 /*   @GetMapping("/getUserFans")
    public JSONObject getUserFans(HttpServletRequest request){
        return wxUserService.countFansByUserId(CommonUtil.request2Json(request));

    }*/
    /**
     * 用户详情页数量部分数据
     */
   /* @GetMapping("/getDetailUserById")
    public JSONObject getDetailUserById(HttpServletRequest request){
        return wxUserService.getDetailUserById(CommonUtil.request2Json(request));

    }*/

}
