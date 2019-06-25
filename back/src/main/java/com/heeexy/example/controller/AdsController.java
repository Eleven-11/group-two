package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.AdsService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public JSONObject getAllAds(HttpServletRequest request){
        return adsService.getAllAds(CommonUtil.request2Json(request));
    }

    /**
     * 展示启用的广告信息
     */
    @GetMapping("/getEnableAds")
    public  JSONObject getEnableAds(HttpServletRequest request){
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
    public JSONObject updateAds(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "id,picture,title,link,status");
        return adsService.updateAds(requestJson);
    }

    /**
     * 添加广告信息
     */
    @PostMapping("/addAds")
    public JSONObject addAds(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "picture,title,link");
        return adsService.addAds(requestJson);
    }

    /**
     * 删除广告
     */
    @GetMapping("/removeAdsById/{id}")
    public JSONObject removeAdsById(@PathVariable int id){
        return adsService.removeAdsById(id);
    }

    @PostMapping("/upload")
    public Map imgUpload(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
        Map<String,Object> map = new HashMap<>();
        MultipartFile file = multiReq.getFile("file");
        String originalFilename = file.getOriginalFilename();
        String temp = UUID.randomUUID().toString();
        String desFilePath =
                "F:" + File.separator+"OTA"
                        + File.separator+"img"
                        + File.separator+"ads"
                        + "/" + temp
                        + originalFilename;
        System.out.println(desFilePath);
        File localFile  = new File(desFilePath);
        String srcUrl = "http://localhost:8080/OTA/img/ads/"+temp+originalFilename;
        localFile.createNewFile();
        file.transferTo(localFile);
        map.put("code", 0);
        map.put("msg", "上传成功");
        map.put("desFilePath", desFilePath);
        map.put("url", srcUrl);
        return map;
    }

}
