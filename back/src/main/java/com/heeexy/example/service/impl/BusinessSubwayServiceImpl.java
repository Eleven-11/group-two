package com.heeexy.example.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.bean.BusinessSubway;
import com.heeexy.example.common.MyException;
import com.heeexy.example.dao.BusinessSubwayDao;
import com.heeexy.example.service.BusinessSubwayService;
import com.heeexy.example.util.CommonUtil;
import com.heeexy.example.util.constants.ErrorEnum;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenqiangyong
 * @description:帖子标签表/增删改查的实现
 * @date: 2019/6/19 12:34
 * @vevsion 1.0
 */
@Service
public class BusinessSubwayServiceImpl implements BusinessSubwayService {
    @Autowired
    private BusinessSubwayDao businessSubwayDao;
    /**
     * 新增帖子标签
     * @param jsonObject  businessSubwayName(标签名),superiorId(上级id)
     * @return
     */
    @Override
    public JSONObject addBusinessSubway(JSONObject jsonObject) {
        int exist = businessSubwayDao.queryExistBusinessSubwayName( jsonObject );
        if( exist > 0 ){
            int i = businessSubwayDao.queryExistBusinessSubwayDisplay(jsonObject);
            if(i==1){
                return CommonUtil.errorJson( ErrorEnum.E_10009);
            }
        }
        businessSubwayDao.addBusinessSubway(jsonObject);
        return CommonUtil.successJson();
    }

    /**
     * 修改帖子标签的显示值
     * @param jsonObject  businessSubwayId（标签编号）display(状态值)
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject updateBusinessSubwayDisplay(JSONObject jsonObject) {
        businessSubwayDao.updateBusinessSubwayDisplay( jsonObject );
        return CommonUtil.successJson();
    }
    /**
     * 修改帖子标签
     * @param jsonObject businessSubwayName(标签名),superiorId(上级id)
     * @return
     */
    @Override
    public JSONObject updateBusinessSubway(JSONObject jsonObject) {
        businessSubwayDao.updateBusinessSubway( jsonObject );
        return CommonUtil.successJson();
    }
    /**
     * 查询帖子标签列表
     * @param jsonObject  input(输入框的内容，根据标签名模糊)
     * @return
     */
    @Override
    public JSONObject listBusinessSubway(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = businessSubwayDao.countBusinessSubway(jsonObject);
        List<JSONObject> list = businessSubwayDao.listBusinessSubway(jsonObject);

        return CommonUtil.successPage( jsonObject,list,count );
    }
    /**
     *查询所有的帖子标签
     * 在添加/修改帖子标签的时候要使用此方法
     * @return
     */
    @Override
    public JSONObject getAllBusinessSubway() {
        List<JSONObject> roles = businessSubwayDao.getAllBusinessSubway();
        return CommonUtil.successJson(roles);
    }


    /**
     * 增量导入
     * @param fileName 表格的文件名
     * @param file 表格的路径
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    @Override
    public JSONObject  batchImport(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<BusinessSubway> userList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");

        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        BusinessSubway businessSubway;
        for (int r = 2; r <= sheet.getLastRowNum(); r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
            if (row == null){
                continue;
            }

            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException

            businessSubway = new BusinessSubway();
            if( row.getCell(0).getCellType() !=1){//循环时，得到每一行的单元格进行判断
//                throw new MyException("导入失败(第"+(r+1)+"行,用户名请设为文本格式)");
                return CommonUtil.errorJson(ErrorEnum.E_9999);
            }
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String Superior = row.getCell(0).getStringCellValue();
            if(Superior == null || Superior.isEmpty()){
//                throw new MyException("导入失败(第"+(r+1)+"行,上级未填写)");
                return CommonUtil.errorJson(ErrorEnum.E_9999);
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值

            String businesssubwayname = row.getCell(1).getStringCellValue();
            if(businesssubwayname == null || businesssubwayname.isEmpty()){
//                throw new MyException("导入失败(第"+(r+1)+"行,名字未填写)");
                return CommonUtil.errorJson(ErrorEnum.E_9999);
            }


            int  presenceo = businessSubwayDao.countbusinessSubwayName( Superior );
            if( presenceo == 0 ){
                businessSubwayDao.addbusinessSubwayName(Superior);
            }
            int presencet = businessSubwayDao.countbusinessSubwayName( businesssubwayname );
            if ( presencet == 0){
                Integer superiorid = businessSubwayDao.selectSuperior( Superior );
                businessSubway.setBusinesssubwayname(businesssubwayname);
                businessSubway.setSuperiorid(superiorid);
                userList.add(businessSubway);
                for (BusinessSubway userResord : userList) {
                    Integer businessaubwayid = userResord.getBusinessaubwayid();
                    int cnt = businessSubwayDao.selectByName(businessaubwayid);
                    if (cnt == 0) {
                        businessSubwayDao.addUser(userResord);
                        System.out.println(" 插入 "+userResord);
                    }
//                    else {
//                        businessSubwayDao.updateUserByName(userResord);
//                        System.out.println(" 更新 "+userResord);
//                    }
                }
                userList.clear();
            }
        }

        return CommonUtil.successJson();
    }

    /**
     * 覆盖导入
     * @param fileName 表格的文件名
     * @param file 表格的路径
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject  batchImports(String fileName, MultipartFile file) throws Exception {

        businessSubwayDao.removeBusinessSubway();
        boolean notNull = false;
        List<BusinessSubway> userList = new ArrayList<>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw new MyException("上传文件格式不正确");

        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }
        BusinessSubway businessSubway;
        for (int r = 2; r <= sheet.getLastRowNum(); r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
            if (row == null){
                continue;
            }


            businessSubway = new BusinessSubway();
            if( row.getCell(0).getCellType() !=1){//循环时，得到每一行的单元格进行判断
//                throw new MyException("导入失败(第"+(r+1)+"行,用户名请设为文本格式)");
                return CommonUtil.errorJson(ErrorEnum.E_9999);
            }
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            String Superior = row.getCell(0).getStringCellValue();
            if(Superior == null || Superior.isEmpty()){
//                throw new MyException("导入失败(第"+(r+1)+"行,上级未填写)");
                return CommonUtil.errorJson(ErrorEnum.E_9999);
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值

            String businesssubwayname = row.getCell(1).getStringCellValue();
            if(businesssubwayname == null || businesssubwayname.isEmpty()){
//                throw new MyException("导入失败(第"+(r+1)+"行,名字未填写)");
                return CommonUtil.errorJson(ErrorEnum.E_9999);
            }


            int  presenceo = businessSubwayDao.countbusinessSubwayName( Superior );
            if( presenceo == 0 ){
                businessSubwayDao.addbusinessSubwayName(Superior);
            }
            int presencet = businessSubwayDao.countbusinessSubwayName( businesssubwayname );
            if ( presencet == 0){
                Integer superiorid = businessSubwayDao.selectSuperior( Superior );
                businessSubway.setBusinesssubwayname(businesssubwayname);
                businessSubway.setSuperiorid(superiorid);
                userList.add(businessSubway);
                for (BusinessSubway userResord : userList) {
                    Integer businessaubwayid = userResord.getBusinessaubwayid();
                    int cnt = businessSubwayDao.selectByName(businessaubwayid);
                    if (cnt == 0) {
                        businessSubwayDao.addUser(userResord);
                        System.out.println(" 插入 "+userResord);
                    }
//                    else {
//                        businessSubwayDao.updateUserByName(userResord);
//                        System.out.println(" 更新 "+userResord);
//                    }
                }
                userList.clear();
            }
        }
            return  CommonUtil.successJson();
        }


}
