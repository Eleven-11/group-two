package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WxFansService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:12 2019/6/19
 * @ Description：粉丝用户关注controller层
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/comUserFansWeb")
public class ComUserFansWebController {
    @Autowired
    private WxFansService wxFansService;
    /**
    *前台展示用户关注列表
    */
    @GetMapping("/listUserFans")
    public JSONObject listUserFans(HttpServletRequest request) {

        return wxFansService.getListByUserId(CommonUtil.request2Json(request));
    }
    /**
     *前台展示用户关注计算数量
     */
    @GetMapping("/userFansNumber")
    public JSONObject UserFansNumber(HttpServletRequest request) {

        return wxFansService.countByUserId(CommonUtil.request2Json(request));
    }
    /**
     * 前台添加用户关注
     */
    @PostMapping("/addFansByUser")
    public JSONObject addFans1(@RequestBody JSONObject requestJson) {

        CommonUtil.hasAllRequired(requestJson, "onUserId,userId");
        return wxFansService.addByFans(requestJson);
    }
    /**
     * 前台修改用户关注状态(取消)
     */
    @PostMapping("/updateFansByUser")
    public JSONObject updateFans1(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,userId");
        return wxFansService.updateFansByUserId(requestJson);
    }
    /**
     * 查找是否关注该用户信息
     */
    @PostMapping("/selectFans")
    public JSONObject selectFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,userId");
        return wxFansService.getUserFans(requestJson);
    }
    /**
     *我的关注
     */
    @PostMapping("/myattention")
    public JSONObject myfans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "uid");
        return wxFansService.myFans(requestJson);
    }
}
