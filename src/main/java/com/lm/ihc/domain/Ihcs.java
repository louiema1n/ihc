package com.lm.ihc.domain;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ihcs {

    private int id;
    private int number;
    private int son;
    private int total;
    private Timestamp time;
    private String timeP;
    private String remark;
    private boolean state;

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    private String item;
    private int userid;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSon() {
        return son;
    }

    public void setSon(int son) {
        this.son = son;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimeP() {
        return String.valueOf(getTime());
    }

    @Override
    public String toString() {
        return "Ihcs{" +
                "id=" + id +
                ", number=" + number +
                ", son=" + son +
                ", total=" + total +
                ", time=" + time +
                ", timeP='" + timeP + '\'' +
                ", remark='" + remark + '\'' +
                ", state=" + state +
                ", item='" + item + '\'' +
                ", userid=" + userid +
                ", user=" + user +
                '}';
    }
}