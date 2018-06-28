package com.lm.ihc.domain;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ihcs {

    private int id;
    private String number;
    private String son;
    private int total;
    private Timestamp time;
    private String timeP;
    private String remark;
    private boolean state;
    private String confirm;
    private String prj;
    private String results;
    private Boolean ismatch;
    private String doctor;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Boolean getIsmatch() {
        return ismatch;
    }

    public void setIsmatch(Boolean ismatch) {
        this.ismatch = ismatch;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getPrj() {
        return prj;
    }

    public void setPrj(String prj) {
        this.prj = prj;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
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
