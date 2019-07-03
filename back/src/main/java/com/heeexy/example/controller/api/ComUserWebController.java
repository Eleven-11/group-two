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
    //cqy
    @Autowired
    private ModuleService moduleService;
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
        CommonUtil.hasAllRequired(requestJson, "uuid,userName,userPhoto,userSex");
        String userUuid = requestJson.getString("uuid");
        //判断uuid，是否为空，空为游客，未授权，有值为授权
        if (userUuid ==null){
            return wxUserService.addGuestUser(requestJson);
        }else {
            //先查看是否之前已授权过该小程序，空为首次登入
            JSONObject jsonObject = wxUserService.queryUserByUuId(requestJson);
            if (jsonObject.isEmpty()){
             //cqy改(未测试)
                JSONObject jsonObject1 = wxUserService.addByUser(requestJson);
                moduleService.addMessageH(jsonObject);
                return jsonObject1 ;
            }else {
                return CommonUtil.successJson();
            }

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
    /**
     * 我的界面信息
     */
    @GetMapping("/myself")
    public JSONObject myself(HttpServletRequest request){
        return wxUserService.mySelf(CommonUtil.request2Json(request));
    }
    /**
     * 我的粉丝信息
     */
    @GetMapping("/searchmySelfFans")
    public JSONObject mySelfFans(HttpServletRequest request){
        return wxUserService.mySelfFans(CommonUtil.request2Json(request));
    }
}
