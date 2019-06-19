package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostCategorieService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenqiangyong
 * @description:帖子类别相关controller
 * @date: 2019/6/18 21:51
 * @vevsion
 */
@RestController
@RequestMapping("/postcategorie")
public class PostCategorieController {
    @Autowired
    private PostCategorieService postCategorieService;

    /**
     * 查询帖子类别列表
     */
//   @RequiresPermissions( "postcategorie:list" )
    @GetMapping("/list")
    public JSONObject listPostCategorie(HttpServletRequest request){
        return postCategorieService.listPostCategorie( CommonUtil.request2Json(request));
    }
    /**
     * 新增帖子类别
     */
//    @RequiresPermissions("postcategorie:add")
    @PostMapping("addPostCategorie")
    public JSONObject addPostCategorie(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "categoriesName,categoriesImg");
        return postCategorieService.addPostCategorie(requestJson);
    }
    /**
     * 修改帖子类别
     */
//    @RequiresPermissions("module:update")
    @PostMapping("/updatePostCategorie")
    public JSONObject updatePostCategorie(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "categoriesName,categoriesImg");
        return postCategorieService.updatePostCategorie(requestJson);
    }
    /**
     * 删除帖子类别
     */
//    @RequiresPermissions("role:delete")
    @PostMapping("/updatePostCategorieDisplay")
    public JSONObject updatePostCategorieDisplay(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "moduleID");
        return postCategorieService.updatePostCategorieDisplay(requestJson);
    }
    /**
     * 查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     */
//    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllPostCategorie")
    public JSONObject getAllPostCategorie() {
        return postCategorieService.getAllPostCategorie();
    }

}
