package com.heeexy.example.controller;

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
@RequestMapping("/comUserFans")
public class comUserFansController {
    @Autowired
    private WxFansService wxFansService;
    /**
    *前台展示用户关注列表
    */
    @GetMapping("/listUserFans")
    public JSONObject listUserFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId");
        return wxFansService.getListByUserId(requestJson);
    }
    /**
     * 添加用户关注
     */
    @PostMapping("/addFans")
    public JSONObject addFans(@RequestBody JSONObject requestJson) {

        CommonUtil.hasAllRequired(requestJson, "onUserId,userId");
        return wxFansService.addByFans(requestJson);
    }
    /**
     * 修改用户关注状态
     */
    @PostMapping("/updateUserFans")
    public JSONObject updateFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "state,onUserId,userId");
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
}
