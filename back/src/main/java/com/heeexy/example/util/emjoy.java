package com.heeexy.example.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;

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
}
