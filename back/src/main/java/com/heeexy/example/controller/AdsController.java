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
     * 获取广告列表
     * @param request （key: status 状态 可选；pageRow 一页条数；pageNum 页数）
     * @return JSONObject （key：code 状态码；msg 消息；info{totalPage 总页数；list[] 数据列表；totalCount 数据总条数} 数据）
     *         list[（id 编号；picture 图片路径；link 广告链接；title 广告标题；status 状态）]
     */
    @GetMapping("/getAds")
    public JSONObject getAllAds(HttpServletRequest request){
        return adsService.getAds(CommonUtil.request2Json(request));
    }

    /**
     * 根据广告编号获取一条广告信息
     * @param id 编号 必选
     * @return JSONObject （key：code 状态码；msg 消息；info{} 数据）
     *         info（id 编号；picture 图片路径；link 广告链接；title 广告标题；status 状态）
     */
    @GetMapping("/getAdsById/{id}")
    public JSONObject getAdsById(@PathVariable int id){
        return adsService.getAdsById(id);
    }

    /**
     * 修改广告
     * @param requestJson （key：id 编号 必选；picture 图片路径 必选；title 标题 必选；link 链接 必选；status 状态 必选）
     * @return JSONObject （key：code 状态码；msg 消息；info{} 数据）
     */
    @PostMapping("/updateAds")
    public JSONObject updateAds(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "id,picture,title,link,status");
        return adsService.updateAds(requestJson);
    }

    /**
     * 添加广告
     * @param requestJson （picture 图片路径 必选；title 标题 必选；link 链接 必选）
     * @return JSONObject （key：code 状态码；msg 消息；info{} 数据）
     */
    @PostMapping("/addAds")
    public JSONObject addAds(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "picture,title,link");
        return adsService.addAds(requestJson);
    }

    /**
     * 删除广告
     * @param id 编号
     * @return JSONObject （key：code 状态码；msg 消息；info{} 数据）
     */
    @GetMapping("/removeAdsById/{id}")
    public JSONObject removeAdsById(@PathVariable int id){
        return adsService.removeAdsById(id);
    }

    /**
     * 上传图片，返回图片路径
     * @param multiReq 上传的图片
     * @return map（key：code 状态码；msg 消息；url ）图片路径
     * @throws IOException
     */
    @PostMapping("/upload")
    public Map imgUpload(MultipartHttpServletRequest multiReq) throws IOException {
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
        File localFile  = new File(desFilePath);
        String srcUrl = "http://localhost:8080/OTA/img/ads/"+temp+originalFilename;
        localFile.createNewFile();
        file.transferTo(localFile);
        map.put("code", 0);
        map.put("msg", "上传成功");
        map.put("url", srcUrl);
        return map;
    }

}
