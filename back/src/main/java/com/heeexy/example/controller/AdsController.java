package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.AdsService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author : 林成
 * @Description : 广告相关Controller
 * @Date : 2019-06-18 14:47
 * @Version : 1.0
 **/
@RestController
@RequestMapping("/ads")
public class AdsController {
    @Autowired
    private AdsService adsService;

    /**
     * 展示所有广告信息
     */
    @GetMapping("/getAllAds")
    public List<JSONObject> getAllAds(HttpServletRequest request){
        return adsService.getAllAds(CommonUtil.request2Json(request));
    }

    /**
     * 展示启用的广告信息
     */
    @GetMapping("/getEnableAds")
    public  List<JSONObject> getEnableAds(HttpServletRequest request){
        return  adsService.getEnableAds(CommonUtil.request2Json(request));
    }

    /**
     * 展示一条广告信息
     */
    @GetMapping("/getAdsById/{id}")
    public JSONObject getAdsById(@PathVariable int id){
        return adsService.getAdsById(id);
    }

    /**
     * 修改广告信息
     */
    @PostMapping("/updateAds")
    public JSONObject updateAds(HttpServletRequest request){
        return adsService.updateAds(CommonUtil.request2Json(request));
    }

    /**
     * 添加广告信息
     */
    @PostMapping("/addAds")
    public JSONObject addAds(HttpServletRequest request){
        return adsService.addAds(CommonUtil.request2Json(request));
    }

    /**
     * 删除广告
     */
    @GetMapping("/removeAdsById/{id}")
    public JSONObject removeAdsById(@PathVariable int id){
        return adsService.removeAdsById(id);
    }

}
