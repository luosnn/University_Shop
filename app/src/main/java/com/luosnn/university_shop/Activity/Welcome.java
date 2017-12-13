package com.luosnn.university_shop.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;


import com.luosnn.university_shop.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * Created by 罗什什 on 2017/12/11.
 *
 * APP欢迎页面，定义延时跳转
 * 初始化个推服务
 * 初始化Bmob服务器
 */

public class Welcome extends Activity {
    Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);
        Bmob.initialize(this, "df878b162b32277b6a91bb0aba1adb07");
        BmobUpdateAgent.initAppVersion();
        BmobUpdateAgent.update(this);
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Welcome.this,Login.class));
                finish();
            }
        },3000);
    }
}
