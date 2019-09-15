package com.willemgeo.szdb.bean;

/**
 * 照片实体
 * Created by pjh on 2019/9/15.
 */

public class Img {

    String id = "";
    String zjhm = "";
    String dbrmc = "";
    String imgtype = "";
    String imgpath = "";
    boolean isupload = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
