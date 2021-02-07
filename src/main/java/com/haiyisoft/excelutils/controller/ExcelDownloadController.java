package com.haiyisoft.excelutils.controller;

import com.haiyisoft.excelutils.entity.ExcelDownload;
import com.haiyisoft.excelutils.service.ExcelDownloadService;
import com.haiyisoft.excelutils.util.ExportExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

@RestController
public class ExcelDownloadController {

    @Autowired
    private ExcelDownloadService excelDownloadService;

    @GetMapping(value = "/getItems")
    public List<ExcelDownload> getItems() {
        List<ExcelDownload> items = null;
        try {
            items = excelDownloadService.getItems();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @GetMapping(value = "/excelDownload")
    public void excelDownload(HttpServletRequest request, HttpServletResponse response, String name, String columnDefine, String sql) {

        String fileName = null;
        HSSFWorkbook wb = null;
        OutputStream os = null;

        try {
            //获取数据
            List<Object[]> items = excelDownloadService.excelDownload(sql);

            //excel标题
            String[] title = columnDefine.split(" ");

            //excel文件名
            fileName = name + System.currentTimeMillis() + ".xls";

            // sheet名
            String sheetName = name;

            // 存放内容的数组
            String [][] content = new String[items.size()][title.length];

            for (int i = 0; i < items.size(); i++) {
                Object[] item = items.get(i);
                for (int j = 0; j < title.length; j++) {
                    content[i][j] = String.valueOf(item[j]);
                }
            }

            //创建HSSFWorkbook
            wb = ExportExcelUtil.getHSSFWorkbook(sheetName, title, content, null);

            //响应到客户端
            setResponseHeader(response, fileName);
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * 发送响应流方法
     */
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            //response.addHeader("Pargam", "no-cache");
            //response.addHeader("Cache-Control", "no-cache");
            response.setHeader("Access-Control-Allow-Origin","*");
            response.setHeader("Access-Control-Allow-Methods","GET");
            response.setHeader("Access-Control-Allow-Headers",":x-requested-with,content-type");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}