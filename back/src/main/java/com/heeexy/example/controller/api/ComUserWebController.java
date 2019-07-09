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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * 解密用户敏感数据
     *获取秘钥并处理解密的controller
     * @param encryptedData 明文,加密数据
     * @param iv            加密算法的初始向量
     * @param code          用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取 session_key api，将 code 换成 openid 和 session_key
     * @return
     */
    @PostMapping(value = "/decodeUser")
    public Map decodeUser(String encryptedData, String iv, String code) {

        Map map = new HashMap();

        //登录凭证不能为空
        if (code == null || code.length() == 0) {
            map.put("status", 0);
            map.put("msg", "code 不能为空");
            return map;
        }

        //小程序唯一标识   (在微信小程序管理后台获取)
        String wxspAppid = "wxd8980e77d335c871";
        //小程序的 app secret (在微信小程序管理后台获取)
        String wxspSecret = "85d29ab4fa8c797423f2d7da5dd514cf";
        //授权（必填）
        String grant_type = "authorization_code";


        //////////////// 1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
        //请求参数
        String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
        //发送请求
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析相应内容（转换成json对象）
        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");

        //////////////// 2、对encryptedData加密数据进行AES解密 ////////////////
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");

                net.sf.json.JSONObject userInfoJSON = net.sf.json.JSONObject.fromObject(result);
                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
//                String username=(String) userInfoJSON.get("nickName");
                /*进行表情昵称处理*/
            /*    String unicode = emjoy.unicode(username);
                userInfoJSON.put("nickName", unicode);*/
                /*=============*/
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", 0);
        map.put("msg", "解密失败");
        return map;
    }
    /**
     * 添加普通用户
     */
    @PostMapping("/addUser")
    public JSONObject addUser(@RequestBody JSONObject requestJson,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map map =new HashMap();
        String userUuid = (String)session.getAttribute("unionId");
        String nickName = (String)session.getAttribute("nickName");
        String avatarUrl = (String)session.getAttribute("avatarUrl");
        String gender = (String)session.getAttribute("gender");
        /*根据当前时间随机生成用户Id*/
        Date now = new Date();
        long time = now.getTime()/1000000;
        String s = Long.toString(time);
        UUID random = UUID.randomUUID();
        String userId=random+s;
        map.put("userId", userId);
        map.put("userUuid", userUuid);
        map.put("userName", nickName);
        map.put("userPhoto", avatarUrl);
        map.put("userSex", gender);
//        CommonUtil.hasAllRequired(requestJson, "uuid,userName,userPhoto,userSex");
//        String userUuid = requestJson.getString("uuid");
        //判断uuid，是否为空，空为游客，未授权，有值为授权
        if (userUuid ==null){
            wxUserService.addGuestUser(map);
//            JSONObject maxNumber = wxUserService.getMaxNumber(requestJson);
            session.setAttribute("onUserId",userId);
            return CommonUtil.successJson("success") ;
        }else {
            //先查看是否之前已授权过该小程序，空为首次登入
            JSONObject jsonObject = wxUserService.queryUserByUuId(map);
            if (jsonObject.isEmpty()){
                wxUserService.addByUser(map);
                //存入session中
                JSONObject maxNumber = wxUserService.getMaxNumber(requestJson);
                session.setAttribute("onUserId", userId);
                //cqy改(未测试)
                moduleService.addMessageH(maxNumber);
                return CommonUtil.successJson("success") ;

            }else {
//                String uuid = jsonObject.getString("uuid");
                session.setAttribute("onUserId", jsonObject.getString("userId"));
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
    @GetMapping("/myself")
    public JSONObject myself(HttpServletRequest request){
        return wxUserService.mySelf(CommonUtil.request2Json(request));
    }
    /**
     * 我的粉丝信息
     */
    @GetMapping("/myfans")
    public JSONObject mySelfFans(HttpServletRequest request){
        return wxUserService.mySelfFans(CommonUtil.request2Json(request));
    }
}
