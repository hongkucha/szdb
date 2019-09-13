package com.willemgeo.szdb.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_zcxsr")
public class ZCXSR implements Serializable {
    @DatabaseField(columnName = "id", generatedId = true)
    int id;
    @DatabaseField(columnName = "dbhid")
    int dbhid;
    @DatabaseField(columnName = "btgl")
    String btgl;
    @DatabaseField(columnName = "sl")
    int sl;
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

    public String getBtgl() {
        return btgl;
    }
    public void setBtgl(String btgl) {
        this.btgl = btgl;
    }

    public int getSl() {
        return sl;
    }
    public void setSl(int sl) {
        this.sl = sl;
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
