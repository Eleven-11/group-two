package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.BusinessSubwayService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenqiangyong
 * @description:帖子标签表相关controller
 * @date: 2019/6/19 15:32
 * @vevsion
 */
@RestController
@RequestMapping("/businesssubway")
public class BusinessSubwayController {
    @Autowired
    private BusinessSubwayService businessSubwayService;
    /**
     * 查询帖子类别列表
     */
//   @RequiresPermissions( "businesssubway:list" )
    @GetMapping("/list")
    public JSONObject listBusinessSubway(HttpServletRequest request){
        return businessSubwayService.listBusinessSubway( CommonUtil.request2Json(request));
    }
    /**
     * 新增帖子标签
     */
//    @RequiresPermissions("postcategorie:add")
    @PostMapping("addBusinessSubway")
    public JSONObject addBusinessSubway(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "businessSubwayName,superiorId");
        return businessSubwayService.addBusinessSubway(requestJson);
    }
    /**
     * 修改帖子类别
     */
//    @RequiresPermissions("module:update")
    @PostMapping("/updateBusinessSubway")
    public JSONObject updateBusinessSubway(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "businessSubwayName,superiorId");
        return businessSubwayService.updateBusinessSubway(requestJson);
    }
    /**
     * 删除帖子类别
     */
//    @RequiresPermissions("role:delete")
    @PostMapping("/updateBusinessSubwayDisplay")
    public JSONObject updateBusinessSubwayDisplay(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "moduleID");
        return businessSubwayService.updateBusinessSubwayDisplay(requestJson);
    }
    /**
     * 查询所有的帖子类别
     * 在添加/修改帖子类别的时候要使用此方法
     */
//    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllPostCategorie")
    public JSONObject getAllBusinessSubway() {
        return businessSubwayService.getAllBusinessSubway();
    }
}
