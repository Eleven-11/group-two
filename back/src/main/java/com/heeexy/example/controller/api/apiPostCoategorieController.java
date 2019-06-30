package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.PostCategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: chenqiangyong
 * @description: 八大模块的小程序访问接口
 * @date: 2019/6/28   11：37
 * @vevsion 1.0
 */
@RestController
@RequestMapping("/api/frontPostCategorie")
public class apiPostCoategorieController {

    @Autowired
    private PostCategorieService postCategorieService;

    @GetMapping("/getAllPostCategorie")
    public JSONObject getAllPostCategorie() {
        return postCategorieService.RgetAllPostCategorie();
    }
}
