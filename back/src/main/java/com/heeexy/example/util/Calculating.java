package com.heeexy.example.util;

import java.util.Date;

/**
 * @author: chenqiangyong
 * @description: 计算时间差
 * @date: 2019/7/4 15：02
 * @vevsion 1.0
 */
public class Calculating {
    public static String differentDaysByMillisecond(Date date1)
    {
        String time = "";
        Date Today=new Date();
        int maxtime = 15;
        int days = (int) ((Today.getTime() - date1.getTime()) / (1000*3600*24));
        if (days <= 1){
            time = "1天内";
        }else if(days > 1 && days <= maxtime){
            time = days + "天前";
        }else if(days > maxtime){
            time = "15天前";
        }
        return time;
    }
}
