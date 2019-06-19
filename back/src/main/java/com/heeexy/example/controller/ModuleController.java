package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.ModuleService;
import com.heeexy.example.util.CommonUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenqiangyong
 * @description:模板相关controller
 * @date: 2019/6/18 9:03
 */
@RestController
@RequestMapping("/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    /**
     * 查询模板列表
     */
//    @RequiresPermissions( "module:list" )
    @GetMapping("/list")
    public JSONObject listModule(HttpServletRequest request){
        return moduleService.listModule(CommonUtil.request2Json(request));
    }

    /**
     * 新增模块
     */
//    @RequiresPermissions("module:add")
    @PostMapping("addModule")
    public JSONObject addModule(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "moduleName,moduleContent");
        return moduleService.addModule(requestJson);
    }

    /**
     * 修改模块
     */
//    @RequiresPermissions("module:update")
    @PostMapping("/updateModule")
    public JSONObject updateModule(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, " moduleName,moduleContent");
        return moduleService.updateModule(requestJson);
    }

    /**
     * 删除角色
     */
//    @RequiresPermissions("role:delete")
    @PostMapping("/removeModuleDisplay")
    public JSONObject removeModuleDisplay(@RequestBody JSONObject requestJson) {
//        CommonUtil.hasAllRequired(requestJson, "moduleID");
        return moduleService.removeModuleDisplay(requestJson);
    }
    /**
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     */
//    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllRoles")
    public JSONObject getAllRoles() {
        return moduleService.getAllModule();
    }

}
