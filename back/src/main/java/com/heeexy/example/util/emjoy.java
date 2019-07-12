package com.heeexy.example.util;


import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang.time.DateUtils.parseDate;


/**
 * @ Author     ：良优
 * @ Date       ：Created in 17:23 2019/7/4
 * @ Description：表情转换
 * @Version: 1.0
 */
public class emjoy {
    public static String unicode(String source){
        StringBuffer sb = new StringBuffer();
        char [] source_char = source.toCharArray();
        String unicode = null;
        for (int i=0;i<source_char.length;i++) {
            unicode = Integer.toHexString(source_char[i]);
            if (unicode.length() <= 2) {
                unicode = "00" + unicode;
            }
            sb.append("\\u" + unicode);
        }
        return sb.toString();
    }
    public static String decodeUnicode(String unicode) {
        StringBuffer sb = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            int data = Integer.parseInt(hex[i], 16);
            sb.append((char) data);
        }
        return sb.toString();
    }
    /**
     * 把时间根据时、分、秒转换为时间段
     * @param  str  可选其1值{ "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
     * @author String ？日前或？小时前或？分钟前？秒前
     */
    public static String getTimes(Object str){
        String resultTimes = "";
        Date now = new Date();
        Date date= null;
        try {
            date = parseDate((String) str,new String[]{"yyyy-MM-dd HH:mm:ss".intern()});
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*Date date=parseDate（str）;*/
        long times = now.getTime() - date.getTime();
        long day = times / (24 * 60 * 60 * 1000);
        long hour = (times / (60 * 60 * 1000) - day * 24);
        long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        StringBuffer sb = new StringBuffer();
        if(day>0){
            sb.append(day + "天前");
        }else if (hour > 0) {
            sb.append(hour + "小时前");
        } else if (min > 0) {
            sb.append(min + "分钟前");
        } /*else if (sec >0){
            sb.append(sec + "秒前");
        }else if(times>=0){
            sb.append(times + "毫秒前");
        }else{
            sb.append("超前毫秒数:"+times);
        }*/
        resultTimes = sb.toString();
        return resultTimes;
    }
    /**
     * 解密用户敏感数据
     *获取秘钥并处理解密的controller
     * @param encryptedData 明文,加密数据
     * @param iv            加密算法的初始向量
     * @param code          用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取 session_key api，将 code 换成 openid 和 session_key
     * @return
     */
    public static Map decodeUser(String encryptedData, String iv, String code) {

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
}
