package com.luosnn.university_shop.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.luosnn.university_shop.File.UserInfo;
import com.luosnn.university_shop.R;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        Bmob.initialize(this, "df878b162b32277b6a91bb0aba1adb07");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation().save();
        // 启动推送服务
        BmobPush.startWork(this);
        look();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this,YiShiZhuXing.class));
                overridePendingTransition(R.anim.leftin, R.anim.leftout);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.tiao) {
            startActivity(new Intent(this,Product.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftout);

        } else if (id == R.id.xin) {

            startActivity(new Intent(this,News.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftout);

        } else if (id == R.id.news) {
            startActivity(new Intent(this,Wen.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftout);

        } else if (id == R.id.setting) {
            startActivity(new Intent(this,Setting.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftout);

        } else if (id == R.id.bai) {
            startActivity(new Intent(this,Biao.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftin);

        } else if (id == R.id.shi) {
            startActivity(new Intent(this,Shi.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftout);

        }else if (id == R.id.fan){
            startActivity(new Intent(this,Jian.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftout);

        }
        else if (id == R.id.salr){
            startActivity(new Intent(this,Sal.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftout);

        }else if (id == R.id.wo_fa){
            startActivity(new Intent(this,FaBu.class));
            overridePendingTransition(R.anim.leftin, R.anim.leftout);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void look(){
        int i = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        if (i!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(Main.this,"不开启权限会导致程序无法运行，定位失败，无法上架您的物品",Toast.LENGTH_SHORT).show();
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION
            ,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.WRITE_CONTACTS
            ,Manifest.permission.GET_ACCOUNTS,Manifest.permission.READ_CONTACTS},1);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(Main.this,"手机权限获取成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Main.this,"手机权限获取失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
