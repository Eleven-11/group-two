package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostCategorieService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

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


    /**
     * 获取上传图片
     * @param req 图片地址
     * @param multiReq 图片名字
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload")
    public Map imgUpload(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
        Map<String,Object> map = new HashMap<>();
        MultipartFile file = multiReq.getFile("file");
        String originalFilename = file.getOriginalFilename();
        String temp = UUID.randomUUID().toString();
        String desFilePath =
                "F:"      + File.separator+"OTA"
//                        + File.separator+"ideaworkspace"
//                        + File.separator+"group-two"
//                        + File.separator+"back"
//                        + File.separator+"src"
//                        + File.separator+"main"
//                        + File.separator+"resources"
//                        + File.separator+"OTA"
                        + "/"
                        + temp
                        + originalFilename;
        File localFile  = new File(desFilePath);
//        String srcUrl = "F:/OTA/"+ temp +originalFilename;
        String srcUrl = desFilePath.replaceFirst( "F:\\\\", "http://localhost:8080/");
        string=srcUrl;
        if (!localFile.exists()) {
            localFile.createNewFile();
            file.transferTo(localFile);
        }
        BufferedInputStream in = new BufferedInputStream( new FileInputStream( desFilePath ) );
        Image bi = ImageIO.read(in);
        BufferedImage tag = new BufferedImage( 80,80, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage( bi,0,0,80,80,null );
        localFile.delete();
        BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream(desFilePath) );
        ImageIO.write( tag,"JPG",out);
        in.close();
        out.close();
        map.put("code", 0);
        map.put("msg", "上传成功");
        map.put("url", desFilePath);
        return map;
    }
//    /**
//     * 删除图片
//     * @param jsonObject
//     */
//    @PostMapping("/delete")
//    public void delete (@RequestBody JSONObject jsonObject) {
//       string="";
//        File file = new File(jsonObject.getString("desFilePath"));
//        file.delete();
//    }
    /**
     * 查询帖子类别列表
     * @param request
     * @return
     */
//   @RequiresPermissions( "postcategorie:list" )
    @GetMapping("/list")
    public JSONObject listPostCategorie(HttpServletRequest request){
        return postCategorieService.listPostCategorie( CommonUtil.request2Json(request));
    }

    /**
     * 新增帖子类别
     * @param requestJson categoriesName(类别名)
     * @return
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
     * @param requestJson  categoriesId（类别id）,categoriesName(类别名),categoriesImg(类别图片)
     * @return
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
     * @param requestJson categoriesId（类别id）,display(状态值)
     * @return
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
     * @return
     */
//    @RequiresPermissions(value = {"user:add", "user:update"}, logical = Logical.OR)
    @GetMapping("/getAllPostCategorie")
    public JSONObject getAllPostCategorie() {
        return postCategorieService.getAllPostCategorie();
    }

}
