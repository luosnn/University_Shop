package com.luosnn.university_shop.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by 罗什什 on 2017/12/11.
 */

public class MyUser extends BmobUser {
    private String myuserName;
    private String myuserAdd;
    private String myuserNum;
    private String myuserNu;
    private String myuserSay;
    private String myuserNick;

    public String getMyuserNick() {
        return myuserNick;
    }

    public void setMyuserNick(String myuserNick) {
        this.myuserNick = myuserNick;
    }

    public String getMyuserName() {
        return myuserName;
    }

    public void setMyuserName(String myuserName) {
        this.myuserName = myuserName;
    }

    public String getMyuserAdd() {
        return myuserAdd;
    }

    public void setMyuserAdd(String myuserAdd) {
        this.myuserAdd = myuserAdd;
    }

    public String getMyuserNum() {
        return myuserNum;
    }

    public void setMyuserNum(String myuserNum) {
        this.myuserNum = myuserNum;
    }

    public String getMyuserNu() {
        return myuserNu;
    }

    public void setMyuserNu(String myuserNu) {
        this.myuserNu = myuserNu;
    }

    public String getMyuserSay() {
        return myuserSay;
    }

    public void setMyuserSay(String myuserSay) {
        this.myuserSay = myuserSay;
    }
}
