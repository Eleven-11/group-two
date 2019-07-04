package com.heeexy.example.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.service.BusinessSubwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: chenqiangyong
 * @description: 帖子标签的小程序访问接口
 * @date: 2019/7/2 22:18
 * @vevsion
 */
@RestController
@RequestMapping("/api/frontBusinesssubway")
public class apiBusinesssubwayController {
    @Autowired
    private BusinessSubwayService businessSubwayService;

    /**
     * 查询所有的帖子标签
     * @return
     */
    @PostMapping("/FgetAllBusinessSubway")
    public JSONObject getAllBusinessSubway() {
        return businessSubwayService.FgetAllBusinessSubway();
    }
}
