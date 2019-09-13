package com.willemgeo.szdb.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_syr")
public class SYR implements Serializable {
    @DatabaseField(columnName = "id", generatedId = true)
    int id;
    @DatabaseField(columnName = "dbhid")
    int dbhid;
    @DatabaseField(columnName = "xm")
    String xm;
    @DatabaseField(columnName = "ydbhgx")
    String ydbhgx;
    @DatabaseField(columnName = "sfzh")
    String sfzh;
    @DatabaseField(columnName = "syrjtzk")
    String syrjtzk;
    @DatabaseField(columnName = "syf")
    double syf;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getDbhid() {
        return dbhid;
    }
    public void setDbhid(int dbhid) {
        this.dbhid = dbhid;
    }

    public String getXm() {
        return xm;
    }
    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getYdbhgx() {
        return ydbhgx;
    }
    public void setYdbhgx(String ydbhgx) {
        this.ydbhgx = ydbhgx;
    }

    public String getSfzh() {
        return sfzh;
    }
    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getSyrjtzk() {
        return syrjtzk;
    }
    public void setSyrjtzk(String syrjtzk) {
        this.syrjtzk = syrjtzk;
    }

    public double getSyf() {
        return syf;
    }
    public void setSyf(double syf) {
        this.syf = syf;
    }
}
