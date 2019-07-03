package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WxFansService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

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
  /*  @GetMapping("/listUserFans")
    public JSONObject listUserFans(HttpServletRequest request) {

        return wxFansService.getListByUserId(CommonUtil.request2Json(request));
    }*/
    /**
     * 后台添加用户关注
     */
    @RequiresPermissions("comuserfans:add")
    @PostMapping("/addFans")
    public JSONObject addFans(@RequestBody JSONObject requestJson) {

        CommonUtil.hasAllRequired(requestJson, "onUserId,userId");
        return wxFansService.addByFans(requestJson);
    }
    /**
     * 后台修改用户关注状态
     */
    @RequiresPermissions("comuserfans:delete")
    @PostMapping("/updateUserFans")
    public JSONObject updateFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "state,fansId");
        return wxFansService.updateFansByUserId(requestJson);
    }
    /**
     * 前台添加用户关注
     */
 /*   @PostMapping("/addFansByUser")
    public JSONObject addFans1(@RequestBody JSONObject requestJson) {

        CommonUtil.hasAllRequired(requestJson, "onUserId,userId");
        return wxFansService.addByFans(requestJson);
    }*/
    /**
     * 前台修改用户关注状态
     */
   /* @PostMapping("/updateFansByUser")
    public JSONObject updateFans1(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "state,fansId");
        return wxFansService.updateFansByUserId(requestJson);
    }*/
    /**
     * 查找是否关注该用户信息
     */
  /*  @PostMapping("/selectFans")
    public JSONObject selectFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId,userId");
        return wxFansService.getUserFans(requestJson);
    }*/
  @GetMapping("/getMoreFansList")
  public JSONObject getMoreFansList(HttpServletRequest request ) {

      return wxFansService.getListMoreFansById(CommonUtil.request2Json(request));
  }
  @PostMapping("/addMoreFans")
  public JSONObject addMoreFans(@RequestBody JSONObject requestJson) {
      ArrayList<Map<String,String>> fans = (ArrayList<Map<String,String>>)requestJson.get("fansId");
      for (Map<String, String> fansId : fans) {

          wxFansService.addMoreFans(fansId);
      }
      return CommonUtil.successJson();
  }
}
