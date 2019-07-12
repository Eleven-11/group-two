package com.heeexy.example.controller.api;

import com.heeexy.example.service.*;
import com.heeexy.example.util.AesCbcUtil;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.HttpRequest;
import com.alibaba.fastjson.JSONObject;
/*import net.sf.json.JSONObject;*/
import com.heeexy.example.util.emjoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 17:39 2019/6/17
 * @ Description：普通用户操作
 * @ Version: 1.0
 */
@RestController
@RequestMapping("/api/my")
public class ComUserWebController {
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private WxLikeService wxLikeService;
    //cqy
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private WxMyPostService wxMyPostService;
    @Autowired
    private WxBrowserService wxBrowserService;
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
    public JSONObject addUser(@RequestBody JSONObject requestJson,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map map =new HashMap();
        String encryptedData = (String)session.getAttribute("encryptedData");
        String iv = (String)session.getAttribute("iv");
        String code = (String)session.getAttribute("code");
        /*解密微信用户信息*/
        Map map1 = emjoy.decodeUser(encryptedData, iv, code);
        Map userInfo = (Map) map1.get("userInfo");
        String nickName = userInfo.get("nickName").toString();
        String gender = userInfo.get("gender").toString();
        String avatarUrl = userInfo.get("avatarUrl").toString();
        String userUuid = userInfo.get("unionId").toString();
        /*根据当前时间随机生成用户Id*/
        Date now = new Date();
        long time = now.getTime()/(1000*60*60);
        String s = Long.toString(time);
        UUID random = UUID.randomUUID();
        String userId=random+s;
        map.put("userId", userId);
        map.put("userUuid", userUuid);
        map.put("userName", nickName);
        map.put("userPhoto", avatarUrl);
        map.put("userSex", gender);
        //判断uuid，是否为空，空为游客，未授权，有值为授权
        if (userUuid ==null){
            wxUserService.addGuestUser(map);
//            JSONObject maxNumber = wxUserService.getMaxNumber(requestJson);
            session.setAttribute("userId",userId);
            return CommonUtil.successJson("success") ;
        }else {
            //先查看是否之前已授权过该小程序，空为首次登入
            JSONObject jsonObject = wxUserService.queryUserByUuId(map);
            if (jsonObject.isEmpty()){
                wxUserService.addByUser(map);
                //存入session中
                JSONObject maxNumber = wxUserService.getMaxNumber(requestJson);
                session.setAttribute("userId", userId);
                //cqy改(未测试)
                moduleService.addMessageH(maxNumber);
                return CommonUtil.successJson("success") ;

            }else {
//                String uuid = jsonObject.getString("uuid");
                session.setAttribute("userId", jsonObject.getString("userId"));
                return CommonUtil.successJson("success");
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
    @PostMapping("/myself")
    public JSONObject myself(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "userId");
        return wxUserService.mySelf(requestJson);
    }
    /**
     * 我的粉丝信息
     */
    @PostMapping("/myfans")
    public JSONObject mySelfFans(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "userId");
        return wxUserService.mySelfFans(requestJson);
    }
    /**
     * 我的点赞列表
     */
    @PostMapping("/mylikes")
    public JSONObject searchlike(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userId");
        return wxLikeService.myLike(requestJson);
    }
    /**
     * 我已发布的帖子列表
     */
    @PostMapping("/myposts")
    public JSONObject mypost(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userId");
        return wxMyPostService.myPost(requestJson);

    }
    /**
     * 前台展示用户浏览记录列表
     */
    @PostMapping("/myrecords")
    public JSONObject searchbrowse(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userId");
        return wxBrowserService.myBrowse(requestJson);

    }
}
