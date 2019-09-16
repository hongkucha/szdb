package com.willemgeo.szdb.bean;

import java.io.Serializable;

public class cun implements Serializable {
    public String CunName = "";
    public String CunCode = "";

    public cun(String name,String code){
        this.CunName = name;
        this.CunCode = code;
    }
    @Override
    public String toString() {
        return CunName + CunCode;
    }
}
