package com.luosnn.university_shop.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.luosnn.university_shop.R;
import com.luosnn.university_shop.User.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 罗什什 on 2017/12/11.
 */

public class Login extends Activity implements View.OnClickListener{
    private EditText euser;
    private EditText epass;
    private Button b1;
    private Button b2;
    private Button b3;
    private ProgressBar p1;
    private CheckBox checkBox;
    private  SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        find();
    }
    public void find(){
        euser = findViewById(R.id.login_user);
        epass = findViewById(R.id.login_password);
        b1 = findViewById(R.id.forgot);
        b1.setOnClickListener(this);
        b2 = findViewById(R.id.login_to_register);
        b2.setOnClickListener(this);
        b3 = findViewById(R.id.login_to_login);
        b3.setOnClickListener(this);
        p1 = findViewById(R.id.login_program);
        checkBox = findViewById(R.id.login_box);
        pref = getSharedPreferences("UserInfo",MODE_PRIVATE);
        editor = pref.edit();
        String name= pref.getString("Info_user","");
        String pass=  pref.getString("Info_pass","");
        if (name==null||pass==null){
            checkBox.setChecked(false);
        }else {
            checkBox.setChecked(true);
            euser.setText(name);
            epass.setText(pass);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
          case   R.id.forgot:
              startActivity(new Intent(Login.this,Forget.class));
            break;
            case   R.id.login_to_register:
                startActivity(new Intent(Login.this,Register.class));
                break;
            case   R.id.login_to_login:
                p1.setVisibility(View.VISIBLE);
               final String s1 = euser.getText().toString().trim();
                String s2 = epass.getText().toString().trim();
                if (s1 == null || s2 == null ) {
                    p1.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this, "登录失败，ID账号密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (s2.length() < 6) {
                    p1.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login.this, "密码必须大于六位", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    if (checkBox.isChecked()){
                        editor.putString("Info_user",s1);
                        editor.putString("Info_pass",s2);
                        editor.commit();

                    }else {
                        editor.remove("Info_user");
                        editor.remove("Info_pass");
                        editor.commit();
                    }

                    MyUser myUser = new MyUser();
                    myUser.setUsername(s1);
                    myUser.setPassword(s2);
                    myUser.login(new SaveListener<MyUser>() {

                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e==null){
                                p1.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(Login.this,Main.class);
                                intent.putExtra("ID",s1);
                                startActivity(intent);
                                finish();

                            }else {
                                p1.setVisibility(View.INVISIBLE);
                                Toast.makeText(Login.this, "登录失败", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
                break;
        }
    }
}
