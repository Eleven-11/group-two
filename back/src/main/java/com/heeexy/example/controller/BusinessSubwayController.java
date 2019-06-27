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
    @GetMapping("/getAllBusinessSubway")
    public JSONObject getAllBusinessSubway() {
        return businessSubwayService.getAllBusinessSubway();
    }


    /**
     * 增量导入
     */
    @RequestMapping(value = "/import")
    public String exImport(@RequestParam(value = "filename") MultipartFile file) {

        boolean a = false;

        String fileName = file.getOriginalFilename();

        try {
            a = businessSubwayService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }
    /**
     * 覆盖导入
     */
    @RequestMapping(value = "/imports")
    public String exImports(@RequestParam(value = "filename") MultipartFile file) {

        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = businessSubwayService.batchImports(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:index";
    }




    /**
     * 导出
     */
//    @RequestMapping(value = "/export")
//    @ResponseBody
//    public void export(HttpServletResponse response) throws IOException {
//        List<BusinessSubway> users = businessSubwayService.selectUsers();
//
//        HSSFWorkbook wb = new HSSFWorkbook();
//
//        HSSFSheet sheet = wb.createSheet("获取excel测试表格");
//
//        HSSFRow row = null;
//
//        row = sheet.createRow(0);//创建第一个单元格
//        row.setHeight((short) (26.25 * 20));
//        row.createCell(0).setCellValue("列表");//为第一行单元格设值
//
//        /*为标题设计空间
//         * firstRow从第1行开始
//         * lastRow从第0行结束
//         *
//         *从第1个单元格开始
//         * 从第3个单元格结束
//         */
//        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
//        sheet.addMergedRegion(rowRegion);
//
//		/*CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
//		sheet.addMergedRegion(columnRegion);*/
//
//
//        /*
//         * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
//         * 第一个table_name 表名字
//         * 第二个table_name 数据库名称
//         * */
//        row = sheet.createRow(1);
//        row.setHeight((short) (22.50 * 20));//设置行高
//        row.createCell(0).setCellValue("编号");//为第一个单元格设值
//        row.createCell(1).setCellValue("名");//为第二个单元格设值
//        row.createCell(2).setCellValue("上级id");//为第三个单元格设值
//        row.createCell(3).setCellValue("状态");//为第三个单元格设值
//
//        for (int i = 0; i < users.size(); i++) {
//            row = sheet.createRow(i + 2);
//            BusinessSubway businessSubway = users.get(i);
//            row.createCell(0).setCellValue(businessSubway.getBusinessaubwayid());
//            row.createCell(1).setCellValue(businessSubway.getBusinesssubwayname());
//            row.createCell(2).setCellValue(businessSubway.getSuperiorid());
//            row.createCell(3).setCellValue(businessSubway.getDisplay());
//        }
//        sheet.setDefaultRowHeight((short) (16.5 * 20));
//        //列宽自适应
//        for (int i = 0; i <= 13; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//        OutputStream os = response.getOutputStream();
//        response.setHeader("Content-disposition", "attachment;filename=user.xls");//默认Excel名称
//        wb.write(os);
//        os.flush();
//        os.close();
//
//
//    }

}
