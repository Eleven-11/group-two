package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.WxMyCommentService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Author     ：良优
 * @ Date       ：Created in 11:19 2019/6/21
 * @ Description：前台用户评论
 * @ Version: 1.0
 */
@RestController
@RequestMapping("/api/comUserCommentWeb")
public class ComUserCommentWebController {
    @Autowired
    private WxMyCommentService wxMyCommentService;
    /**
    *展示用户评论记录列表
     */
    @GetMapping("/listUserCommentByUserId")
    public JSONObject getlistUserComment(HttpServletRequest request) {

        return wxMyCommentService.getMyCommentListById(CommonUtil.request2Json(request));

    }
}
