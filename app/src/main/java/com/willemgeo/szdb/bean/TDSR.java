package com.willemgeo.szdb.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_tdsr")
public class TDSR implements Serializable {
    @DatabaseField(columnName = "id", generatedId = true)
    int id;
    @DatabaseField(columnName = "dbhid")
    int dbhid;
    @DatabaseField(columnName = "tdmj")
    double tdmj;
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

    public double getTdmj() {
        return tdmj;
    }
    public void setTdmj(double tdmj) {
        this.tdmj = tdmj;
    }

    public double getJe() {
        return je;
    }
    public void setJe(double je) {
        this.je = je;
    }

}
