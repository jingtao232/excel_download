package com.haiyisoft.excelutils.service;

import com.haiyisoft.excelutils.entity.ExcelDownload;
import java.sql.SQLException;
import java.util.List;

public interface ExcelDownloadService {

    List<ExcelDownload> getItems() throws SQLException;

    List<Object[]> excelDownload(String sql) throws SQLException;

}
