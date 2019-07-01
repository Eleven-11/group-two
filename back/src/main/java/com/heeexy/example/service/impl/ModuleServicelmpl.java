package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.ModuleDao;
import com.heeexy.example.service.ModuleService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenqiangyong
 * @description:模块/增删改查的实现
 * @date: 2019/6/18
 * @vevsion 1.0
 */
@Service
public class ModuleServicelmpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    /**
     * 添加模块
     * @param jsonObject moduleName(模块名字),moduleContent(模块内容)
     * @return
     */
    @Override
    public JSONObject addModule(JSONObject jsonObject) {
        int exist = moduleDao.queryExistModuleName(jsonObject);
        if( exist > 0 ){
            int i = moduleDao.queryExistModuleDisplay(jsonObject);
            if(i==1){
                return CommonUtil.errorJson(ErrorEnum.E_10009);
            }
        }
        moduleDao.addModule( jsonObject );
        return CommonUtil.successJson();
    }

    /**
     * 新增消息
     * @param jsonObject
     * @return
     */
    @Override
    public JSONObject addMessage(JSONObject jsonObject) {
        List<Integer> allUserId = moduleDao.getAllUserId();
        if(allUserId.size()!=0){
            jsonObject.put("ulist",allUserId);
            moduleDao.addChat( jsonObject );
        }
        List<Integer> allLcChatId = moduleDao.getAllLcChatId();
        jsonObject.put("clist",allLcChatId);
        moduleDao.addMessage( jsonObject );
        return CommonUtil.successJson();
    }


    /**
     * 删除模块（假）
     * @param jsonObject  moduleId(模块id)
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject removeModuleDisplay(JSONObject jsonObject) {
        moduleDao.removeModuleDisplay( jsonObject );
        return  CommonUtil.successJson();
    }

    /**
     * 修改模块内容
     * @param jsonObject  moduleName(模板名字)，moduleContent(模板内容)，moduleId(模板id)
     * @return
     */
    @Override
    public JSONObject updateModule(JSONObject jsonObject) {
        moduleDao.updateModuleName( jsonObject );
        return CommonUtil.successJson();
    }

    /**
     * 查询模块列表
     * @param jsonObject
     * @return
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
     * @return
     */
    @Override
    public JSONObject getAllModule() {
        List<JSONObject> roles = moduleDao.getAllModule();
        return CommonUtil.successPage(roles);
    }
}
