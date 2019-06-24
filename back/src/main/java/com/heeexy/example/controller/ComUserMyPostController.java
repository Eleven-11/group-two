package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WxBrowserService;
import com.heeexy.example.service.WxLikeService;
import com.heeexy.example.service.WxMyPostService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
     * 网页展示用户帖子列表
     */
    @GetMapping("/listUserPostById")
    public JSONObject getListUserPost(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "onUserId");
        return wxMyPostService.getMyPostListById(requestJson);
    }
    /**
     * 删除用户帖子(修改帖子隐藏显示状态)
     */
    @PostMapping("/updateUserPost")
    public JSONObject updateFans(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "postId,onUserId,myPostState");
        return wxMyPostService.updateMyPostById(requestJson);
    }
    /**
     * 删除用户帖子(批量修改帖子隐藏显示状态)（不成功）
     */
    @PostMapping("/updateUserPostMany")
    public JSONObject updatePostMany(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "posts");
        ArrayList<Map<String,String>> posts = (ArrayList<Map<String,String>>)requestJson.get("posts");
        for (Map<String, String> post : posts) {
            System.out.println(post);
        }
        return wxMyPostService.updateByPostIdMany(requestJson);
    }
}
