package com.haiyisoft.excelutils.service.impl;

import com.haiyisoft.excelutils.entity.ExcelDownload;
import com.haiyisoft.excelutils.service.ExcelDownloadService;
import com.haiyisoft.excelutils.util.DruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service("excelDownloadService")
public class ExcelDownloadServiceImpl implements ExcelDownloadService {

    @Override
    public List<ExcelDownload> getItems() throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        String sql = "select * from excel_download";
        List<ExcelDownload> list = qr.query(sql, new BeanListHandler<ExcelDownload>(ExcelDownload.class));
        return list;
    }

    @Override
    public List<Object[]> excelDownload(String sql) throws SQLException {
        QueryRunner qr = new QueryRunner(DruidUtils.getDataSource());
        List<Object[]> list = qr.query(sql, new ArrayListHandler());
        return list;
    }

}