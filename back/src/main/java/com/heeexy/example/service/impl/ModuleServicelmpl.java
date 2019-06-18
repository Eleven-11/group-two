package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ModuleDao;
import com.heeexy.example.service.ModuleService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: chenqiangyong
 * @description:
 * @date: 2019/6/18
 * @vevsion
 */
@Service
public class ModuleServicelmpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 添加模块
     */
    @Override
    public JSONObject addModule(JSONObject jsonObject) {
        int exist = moduleDao.queryExistModuleName( jsonObject );
        if( exist > 0 ){
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        moduleDao.addModule( jsonObject );
        return CommonUtil.successJson();
    }


    /**
     * 删除模块
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject removeModuleDisplay(JSONObject jsonObject) {
        moduleDao.removeModuleDisplay( jsonObject );
        return  CommonUtil.successJson();
    }

    /**
     * 修改模块
     */
    @Override
    public JSONObject updateModuleName(JSONObject jsonObject) {
        moduleDao.updateModuleName( jsonObject );
        return CommonUtil.successJson();
    }

    /**
     * 查询模块列表
     */
    @Override
    public JSONObject listModule(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = moduleDao.countModule( jsonObject );
        List<JSONObject> list = moduleDao.listModule( jsonObject );
        return CommonUtil.successPage( jsonObject,list,count );
    }
    /**
     * 查询所有的模块
     * 在添加/修改模块的时候要使用此方法
     */
    @Override
    public JSONObject getAllModule() {
        List<JSONObject> roles = moduleDao.getAllModule();
        return CommonUtil.successPage(roles);
    }
}
