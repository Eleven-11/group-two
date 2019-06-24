package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostCollectService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenqiangyong
 * @description:帖子收藏相关的controller
 * @date: 2019/6/20 14:06
 * @vevsion 1.0
 */


@RestController
@RequestMapping("/postcollect")
public class PostCollectController {
    @Autowired
    private PostCollectService postCollectService;

    /**
     * 查询帖子收藏
     */
    //    @RequiresPermissions( "postcollect:list" )
    @GetMapping("/list")
    public JSONObject listModule(HttpServletRequest request){
        return postCollectService.listPostCollect( CommonUtil.request2Json(request));
    }

    /**
     * 新增帖子收藏
     */
//    @RequiresPermissions("module:add")
    @PostMapping("addPostCollect")
    public JSONObject addModule(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "postId,userId");
        return postCollectService.addPostCollect(requestJson);
    }

    /**
     * 修改帖子收藏
     */
//    @RequiresPermissions("module:update")
    @PostMapping("/updatePostCollect")
    public JSONObject updateModule(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "postId,userId");
        return postCollectService.updatePostCollect(requestJson);
    }

    /**
     * 删除帖子收藏
     */
//    @RequiresPermissions("role:delete")
    @PostMapping("//updatePostCollectDisplay")
    public JSONObject removeModuleDisplay(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "moduleID");
        return postCollectService.updatePostCollectDisplay(requestJson);
    }
    /**
     * 查询所有的帖子收藏
     * 在添加/修改帖子收藏的时候要使用此方法
     */
//    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllPostCollect")
    public JSONObject getAllRoles() {
        return postCollectService.getAllPostCollect();
    }
}
