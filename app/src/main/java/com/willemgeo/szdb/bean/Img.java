package com.willemgeo.szdb.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * 照片实体
 * Created by pjh on 2019/9/15.
 */
@DatabaseTable(tableName = "Img")
public class Img {
    @DatabaseField(columnName = "id", generatedId = true)
    long id ;
    @DatabaseField(columnName = "uid")
    String uid = "";
    @DatabaseField(columnName = "zjhm")
    String zjhm = "";
    @DatabaseField(columnName = "dbrmc")
    String dbrmc = "";
    @DatabaseField(columnName = "imgtype")
    String imgtype = "";
    @DatabaseField(columnName = "imgpath")
    String imgpath = "";
    @DatabaseField(columnName = "isupload")
    boolean isupload = false;
    @DatabaseField(columnName = "xjbm")
    String xjbm = "";
    @DatabaseField(columnName = "cjbm")
    String cjbm = "";
    @DatabaseField(columnName = "createTime")
    Date createTime ;//拍摄时间

    //拍摄位置
    @DatabaseField(columnName = "x")
    double x = 0;
    @DatabaseField(columnName = "y")
    double y = 0;

    @DatabaseField(columnName = "syrzjhm")
    String syrzjhm = "";
    @DatabaseField(columnName = "syrmc")
    String syrmc = "";

    public String getSyrzjhm() {
        return syrzjhm;
    }

    public void setSyrzjhm(String syrzjhm) {
        this.syrzjhm = syrzjhm;
    }

    public String getSyrmc() {
        return syrmc;
    }

    public void setSyrmc(String syrmc) {
        this.syrmc = syrmc;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }


    public String getXjbm() {
        return xjbm;
    }

    public void setXjbm(String xjbm) {
        this.xjbm = xjbm;
    }

    public String getCjbm() {
        return cjbm;
    }

    public void setCjbm(String cjbm) {
        this.cjbm = cjbm;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getDbrmc() {
        return dbrmc;
    }

    public void setDbrmc(String dbrmc) {
        this.dbrmc = dbrmc;
    }

    public String getImgtype() {
        return imgtype;
    }

    public void setImgtype(String imgtype) {
        this.imgtype = imgtype;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public boolean isIsupload() {
        return isupload;
    }

    public void setIsupload(boolean isupload) {
        this.isupload = isupload;
    }



}
