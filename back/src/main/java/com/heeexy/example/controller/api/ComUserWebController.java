package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.*;
import com.heeexy.example.util.CommonUtil;
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
@RequestMapping("/api/comUserWeb")
public class ComUserWebController {
    @Autowired
    private WxUserService wxUserService;
    /**
     * 查找用户
     */
  /*  @GetMapping("/getUserByName")
    public JSONObject getUserByName(HttpServletRequest request){
        return wxUserService.getUserByUsername(CommonUtil.request2Json(request));
    }*/
    /**
     * 添加普通用户
     */
    @PostMapping("/addUser")
    public JSONObject addUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userUuid,userName,userPhoto,userSex");
        String userUuid = requestJson.getString("userUuid");
        if (userUuid ==null ){
            return wxUserService.addGuestUser(requestJson);
        }else {
            return wxUserService.addByUser(requestJson);
        }
    }
    /**
     * 添加游客用户
     */
    @PostMapping("/addGuestUser")
    public JSONObject addGuestUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userUuid,userName,userPhoto,userSex");
        return wxUserService.addGuestUser(requestJson);
    }
    /**
     * 计算用户粉丝数
      */
    @GetMapping("/getUserFans")
    public JSONObject getUserFans(HttpServletRequest request){
        return wxUserService.countFansByUserId(CommonUtil.request2Json(request));

    }


}
