package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WxBrowserService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 12:17 2019/6/20
 * @ Description：用户浏览记录
 * @Version: 1.0
 */
@RestController
@RequestMapping("/api/comUserBrowseWeb")
public class ComUserBrowseWebController {

    @Autowired
    private WxBrowserService wxBrowserService;
    /**
     * 添加用户浏览帖记录
     */
    @PostMapping("/addBrowse")
    public JSONObject addFans(@RequestBody JSONObject requestJson) {

        CommonUtil.hasAllRequired(requestJson, "onUserId,postId");
        return wxBrowserService.addBrowsePost(requestJson);
    }
    /**
     * 前台展示用户浏览记录列表
     */
    @GetMapping("/listBrowseByUser")
    public JSONObject getListUserBrowse(HttpServletRequest request) {

        return wxBrowserService.getBrowseByUser(CommonUtil.request2Json(request));

    }
    /**
     * 前台展示用户浏览记录列表
     */
    @GetMapping("/myhistory")
    public JSONObject searchbrowse(HttpServletRequest request) {

        return wxBrowserService.myBrowse(CommonUtil.request2Json(request));

    }

}
