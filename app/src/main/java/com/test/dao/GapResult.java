package com.test.dao;

import java.io.Serializable;
import java.util.Date;

public class GapResult implements Serializable {
    private String account;
    private String uname;
    private Date mtime;//测量时间
    private String mstatus;//测量状态 空腹、餐后几个钟等
    private Float measurement;

    public GapResult() {
    }

    public GapResult(String account, String uname, Date mtime, String mstatus, Float measurement) {
        this.account = account;
        this.uname = uname;
        this.mtime = mtime;
        this.mstatus = mstatus;
        this.measurement = measurement;
    }

    public GapResult(String account, String uname, String mstatus, Float measurement) {
        this.account = account;
        this.uname = uname;
        this.mstatus = mstatus;
        this.measurement = measurement;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public String getMstatus() {
        return mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public Float getmeasurement() {
        return measurement;
    }

    public void setmeasurement(Float measurement) {
        this.measurement = measurement;
    }

    @Override
    public String toString() {
        return "GapResult{" +
                "account='" + account + '\'' +
                ", uname='" + uname + '\'' +
                ", mtime=" + mtime +
                ", mstatus='" + mstatus + '\'' +
                ", measurement=" + measurement +
                '}'+ '\n';
    }
}
