package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WxBrowserService;
import com.heeexy.example.service.WxLikeService;
import com.heeexy.example.service.WxMyPostService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 12:14 2019/6/20
 * @ Description：用户帖子
 * @Version: 1.0
 */
@RestController
@RequestMapping("/comUserPost")
public class ComUserMyPostController {
    @Autowired
    private WxMyPostService wxMyPostService;
    /**
     * 删除用户帖子
     */
    @PostMapping("/updateUserPost")
    public JSONObject updateFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "myPostId,myPostState");
        return wxMyPostService.updateMyPostById(requestJson);
    }
    /**
     * 删除用户帖子
     */
    @PostMapping("/updateUserPostMany")
    public JSONObject updatePostMany(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "myPostId,myPostState");
        return wxMyPostService.updateByPostId(requestJson);
    }
}
