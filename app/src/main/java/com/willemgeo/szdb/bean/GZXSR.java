package com.willemgeo.szdb.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_gzxsr")
public class GZXSR implements Serializable {
    @DatabaseField(columnName = "id", generatedId = true)
    int id;
    @DatabaseField(columnName = "dbhid")
    int dbhid;
    @DatabaseField(columnName = "zl")
    String zl;
    @DatabaseField(columnName = "gz")
    String gz;
    @DatabaseField(columnName = "wgdd")
    String wgdd;
    @DatabaseField(columnName = "sc")
    int sc;
    @DatabaseField(columnName = "bz")
    double bz;
    @DatabaseField(columnName = "je")
    double je;

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

    public String getZl() {
        return zl;
    }
    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getGz() {
        return gz;
    }
    public void setGz(String gz) {
        this.gz = gz;
    }

    public String getWgdd() {
        return wgdd;
    }
    public void setWgdd(String wgdd) {
        this.wgdd = wgdd;
    }

    public int getSc() {
        return sc;
    }
    public void setSc(int sc) {
        this.sc = sc;
    }

    public double getBz() {
        return bz;
    }
    public void setBz(double bz) {
        this.bz = bz;
    }

    public double getJe() {
        return je;
    }
    public void setJe(double je) {
        this.je = je;
    }

}
