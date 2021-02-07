package com.haiyisoft.excelutils.entity;

import java.io.Serializable;

public class ExcelDownload implements Serializable {
    private Long id;
    private String name;
    private String columnDefine;
    private String sql;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnDefine() {
        return columnDefine;
    }

    public void setColumnDefine(String columnDefine) {
        this.columnDefine = columnDefine;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
