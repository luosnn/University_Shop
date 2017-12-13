package com.luosnn.university_shop.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 罗什什 on 2017/12/13.
 */

public class UserInfo extends BmobObject{;
   private String myuserNick;
    private String myuserSex;
    private double muuserYear;
    private String myuserNum;
    private String myuserAdd;

    public String getMyuserNick() {
        return myuserNick;
    }

    public void setMyuserNick(String myuserNick) {
        this.myuserNick = myuserNick;
    }

    public String getMyuserSex() {
        return myuserSex;
    }

    public void setMyuserSex(String myuserSex) {
        this.myuserSex = myuserSex;
    }

    public double getMuuserYear() {
        return muuserYear;
    }

    public void setMuuserYear(double muuserYear) {
        this.muuserYear = muuserYear;
    }

    public String getMyuserNum() {
        return myuserNum;
    }

    public void setMyuserNum(String myuserNum) {
        this.myuserNum = myuserNum;
    }

    public String getMyuserAdd() {
        return myuserAdd;
    }

    public void setMyuserAdd(String myuserAdd) {
        this.myuserAdd = myuserAdd;
    }
    private BmobFile icon;

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }
}
