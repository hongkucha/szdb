package com.willemgeo.szdb.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class xian implements Serializable {
    public String XianName = "";
    public String XianCode = "";
    public List<cun> cunList = new ArrayList<cun>();

    public xian(String name,String code){
        this.XianName = name;
        this.XianCode = code;
    }
    @Override
    public String toString() {
        return XianName + XianCode;
    }
}
