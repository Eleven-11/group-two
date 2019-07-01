package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.BusinessSubwayService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenqiangyong
 * @description:帖子标签表相关controller
 * @date: 2019/6/19 15:32
 * @vevsion 1.0
 */

@RestController
@RequestMapping("/businesssubway")
public class BusinessSubwayController {
    @Autowired
    private BusinessSubwayService businessSubwayService;

    /**
     * 查询帖子标签列表
     * @param request input(输入框的内容，根据标签名模糊)
     * @return
     */
//   @RequiresPermissions( "businesssubway:list" )
    @GetMapping("/list")
    public JSONObject listBusinessSubway(HttpServletRequest request){
        return businessSubwayService.listBusinessSubway( CommonUtil.request2Json(request));
    }
    /**
     * 新增帖子标签
     * @param requestJson businessSubwayName(标签名),superiorId(上级id)
     * @return
     */
//    @RequiresPermissions("postcategorie:add")
    @PostMapping("addBusinessSubway")
    public JSONObject addBusinessSubway(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "businessSubwayName,superiorId");
        return businessSubwayService.addBusinessSubway(requestJson);
    }

    /**
     * 修改帖子标签
     * @param requestJson businessSubwayName(标签名),superiorId(上级id)
     * @return
     */
//  @RequiresPermissions("module:update")
    @PostMapping("/updateBusinessSubway")
    public JSONObject updateBusinessSubway(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "businessSubwayName,superiorId");
        return businessSubwayService.updateBusinessSubway(requestJson);
    }
    /**
     * 删除帖子标签
     * @param requestJson businessSubwayId（标签编号）display(状态值)
     * @return
     */
//    @RequiresPermissions("role:delete")
    @PostMapping("/updateBusinessSubwayDisplay")
    public JSONObject updateBusinessSubwayDisplay(@RequestBody JSONObject requestJson) {
        return businessSubwayService.updateBusinessSubwayDisplay(requestJson);
    }

    /**
     * 查询所有的帖子标签
     * 在添加/修改帖子标签的时候要使用此方法
     * @return
     */
//    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllBusinessSubway")
    public JSONObject getAllBusinessSubway() {
        return businessSubwayService.getAllBusinessSubway();
    }


    /**
     * 增量导入
     * @param file filename(表格的路径)
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/import")
    public JSONObject exImport(@RequestParam(value = "filename") MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        return businessSubwayService.batchImport(fileName, file);
    }

    /**
     * 覆盖导入
     * @param file filename(表格的路径)
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/imports")
    public JSONObject exImports(@RequestParam(value = "filename") MultipartFile file) throws Exception{
        String fileName = file.getOriginalFilename();
        return businessSubwayService.batchImports(fileName, file);
    }

}
