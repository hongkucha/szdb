package com.willemgeo.szdb.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "tb_dbh")
public class DBH implements Serializable {
    @DatabaseField(columnName = "id", generatedId = true)
    int id;
    @DatabaseField(columnName = "dbhxm")
    String dbhxm;
    @DatabaseField(columnName = "sfzh")
    String sfzh;
    @DatabaseField(columnName = "lxdh")
    String lxdh;
    @DatabaseField(columnName = "ksbzsj")
    String ksbzsj;
    @DatabaseField(columnName = "sflk")
    boolean sflk;
    @DatabaseField(columnName = "bzje")
    double bzje;
    @DatabaseField(columnName = "zyzpyy")
    String zyzpyy;
    @DatabaseField(columnName = "jtzfqk")
    String jtzfqk;
    @DatabaseField(columnName = "jtccqk")
    String jtccqk;
    @DatabaseField(columnName = "jyxsr")
    double jyxsr;
    @DatabaseField(columnName = "qtsr")
    double qtsr;
    @DatabaseField(columnName = "sysrze")
    double sysrze;
    @DatabaseField(columnName = "qtqk")
    String qtqk;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDbhxm() {
        return dbhxm;
    }

    public void setDbhxm(String dbhxm) {
        this.dbhxm = dbhxm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getKsbzsj() {
        return ksbzsj;
    }

    public void setKsbzsj(String minX) {
        this.ksbzsj = ksbzsj;
    }

    public boolean getSflk() {
        return sflk;
    }

    public void setSflk(boolean sflk) {
        this.sflk = sflk;
    }

    public double getBzje() {
        return bzje;
    }

    public void setBzje(double bzje) {
        this.bzje = bzje;
    }

    public String getZyzpyy() {
        return zyzpyy;
    }

    public void setZyzpyy(String zyzpyy) {
        this.zyzpyy = zyzpyy;
    }

    public String getJtzfqk() {
        return jtzfqk;
    }

    public void setJtzfqk(String jtzfqk) {
        this.jtzfqk = jtzfqk;
    }

    public String getJtccqk() {
        return jtccqk;
    }

    public void setJtccqk(String jtccqk) {
        this.jtccqk = jtccqk;
    }

    public double getJyxsr() {
        return jyxsr;
    }

    public void setJyxsr(double jyxsr) {
        this.jyxsr = jyxsr;
    }

    public double getQtsr() {
        return qtsr;
    }

    public void setQtsr(double qtsr) {
        this.qtsr = qtsr;
    }

    public double getSysrze() {
        return sysrze;
    }

    public void setSysrze(double sysrze) {
        this.sysrze = sysrze;
    }

    public String getQtqk() {
        return qtqk;
    }

    public void setQtqk(String qtqk) {
        this.qtqk = qtqk;
    }

    /***
     * 家庭成员
     */
    public List<JTCY> JtcyList = new ArrayList<JTCY>();

    /***
     * 赡养人
     */
    public List<SYR> SyrList = new ArrayList<SYR>();

    /***
     * 政策性收入
     */
    public List<ZCXSR> ZcxsrList = new ArrayList<ZCXSR>();

    /***
     * 工资性收入
     */
    public List<GZXSR> GzxsrList = new ArrayList<GZXSR>();

    /***
     * 土地收入
     */
    public List<TDSR> TdsrList = new ArrayList<TDSR>();

    /***
     * 医疗保障
     */
    public List<YLBZ> YlbzList = new ArrayList<YLBZ>();

    /***
     * 教育保障
     */
    public List<JYBZ> JybzList = new ArrayList<JYBZ>();
}
