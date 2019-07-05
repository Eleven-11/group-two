package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostCollectService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenqiangyong
 * @description: 收藏页面的小程序接口
 * @date: 2019/6/30   10：23
 * @vevsion 1.0
 */
@RestController
@RequestMapping("/api")
public class apiPostCollectController {
    @Autowired
    private PostCollectService postCollectService;

    /**
     * 根据用户查询所有的帖子收藏
     * @param requestJson userId(用户id)
     * @return
     */
//    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @PostMapping("/collection/getcollection")
    public JSONObject getAllRoles(@RequestBody  JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "userId");
        return postCollectService.getAllPostCollectByUserId(requestJson);
    }
    /**
     * 修改帖子收藏
     * @param requestJson postId(帖子id),userId(用户id)
     * @return
     */
//    @RequiresPermissions("role:delete")
    @PostMapping("/collect/collect")
    public JSONObject updatePostCollectDisplay(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "postId,userId");
        return postCollectService.updatePostCollectDisplays(requestJson);
    }

}
