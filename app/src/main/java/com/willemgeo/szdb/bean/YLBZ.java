package com.willemgeo.szdb.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_ylbz")
public class YLBZ implements Serializable {
    @DatabaseField(columnName = "id", generatedId = true)
    int id;
    @DatabaseField(columnName = "dbhid")
    int dbhid;
    @DatabaseField(columnName = "ylze")
    double ylze;
    @DatabaseField(columnName = "hzbx")
    double hzbx;
    @DatabaseField(columnName = "zffy")
    double zffy;

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

    public double getYlze() {
        return ylze;
    }
    public void setYlze(double ylze) {
        this.ylze = ylze;
    }

    public double getHzbx() {
        return hzbx;
    }
    public void setHzbx(double hzbx) {
        this.hzbx = hzbx;
    }

    public double getZffy() {
        return zffy;
    }
    public void setZffy(double zffy) {
        this.zffy = zffy;
    }

}
