package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.AdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 林成
 * @Description :
 * @Date : 2019-07-04 10:24
 * @Version : 1.0
 **/
@RestController
@RequestMapping("/adsApi")
public class apiAdsController {
    @Autowired
    private AdsService adsService;
    /**
     * 获取广告列表
     */
    @GetMapping("/getAds")
    public JSONObject getAllAds(){
        return adsService.swiper();
    }
}
