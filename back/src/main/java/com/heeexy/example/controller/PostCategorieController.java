package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostCategorieService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: chenqiangyong
 * @description:帖子类别相关controller
 * @date: 2019/6/18 21:51
 * @vevsion 1.0
 */
@RestController
@RequestMapping("/postcategorie")
public class PostCategorieController {
    @Autowired
    private PostCategorieService postCategorieService;
//    List<String> list = new ArrayList<>();
    String string=new String();


    @RequestMapping(value = "/upload")
    public Map imgUpload(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
        Map<String,Object> map = new HashMap<>();
        MultipartFile file = multiReq.getFile("file");
        String originalFilename = file.getOriginalFilename();
        String desFilePath =
                "F:" + File.separator+"ideaworkspace"
                        + File.separator+"group-two"
                        + File.separator+"vue"
                        + File.separator+"src"
                        + File.separator+"assets"
                        + File.separator+"image"
                        + "/" + originalFilename;
        File localFile  = new File(desFilePath);
        String srcUrl = "http://localhost:9520/static/img/"+originalFilename;
        string=srcUrl;
        if (!localFile.exists()) {
            localFile.createNewFile();
            file.transferTo(localFile);
        }
        map.put("code", 0);
        map.put("msg", "上传成功");
        map.put("url", srcUrl);
        return map;
    }


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
        CommonUtil.hasAllRequired(requestJson, "categoriesName");
        requestJson.put( "categoriesImg",string );
        return postCategorieService.addPostCategorie(requestJson);

    }

    /**
     * 修改帖子类别
     */
//    @RequiresPermissions("module:update")
    @PostMapping("/updatePostCategorie")
    public JSONObject updatePostCategorie(@RequestBody JSONObject requestJson){
        CommonUtil.hasAllRequired(requestJson, "categoriesName");
        requestJson.put( "categoriesImg",string );
        System.out.println(requestJson.get( "categoriesImg" ));
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
