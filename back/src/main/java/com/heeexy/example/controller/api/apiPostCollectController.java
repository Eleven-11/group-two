package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostCollectService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: chenqiangyong
 * @description:
 * @date: 2019/6/30
 * @vevsion
 */
@RestController
@RequestMapping("/api/frontPostCollect")
public class apiPostCollectController {
    @Autowired
    private PostCollectService postCollectService;

    /**
     * 根据用户查询所有的帖子收藏
     */
//    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllPostCollect")
    public JSONObject getAllRoles(JSONObject jsonObject) {
        return postCollectService.getAllPostCollectByUserId( jsonObject );
    }
    /**
     * 修改帖子收藏
     */
//    @RequiresPermissions("role:delete")
    @PostMapping("/updatePostCollectDisplay")
    public JSONObject updatePostCollectDisplay(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "postId,userId");
        return postCollectService.updatePostCollectDisplays(requestJson);
    }

}
