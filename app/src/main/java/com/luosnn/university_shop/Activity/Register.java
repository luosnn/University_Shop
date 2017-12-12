package com.luosnn.university_shop.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class Register extends Activity {
    private EditText euser;
    private EditText epass;
    private EditText eemail;
    private ProgressBar epro;
    private Button b1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.register);
        register();
    }
    public void register(){
        euser = findViewById(R.id.register_user);
        epass = findViewById(R.id.register_password);
        eemail = findViewById(R.id.register_email);
        epro = findViewById(R.id.register_program);
        b1 = findViewById(R.id.register);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epro.setVisibility(View.VISIBLE);
                String s1 = euser.getText().toString().trim();
                String s2 = epass.getText().toString().trim();
                String s3 = eemail.getText().toString().trim();
                if (s1 == null || s2 == null || s3 == null) {
                    epro.setVisibility(View.INVISIBLE);
                    Toast.makeText(Register.this, "注册失败，ID账号邮箱不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (s3.length() < 6) {
                    epro.setVisibility(View.INVISIBLE);
                    Toast.makeText(Register.this, "密码设置必须大于六位", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    MyUser myUser = new MyUser();
                    myUser.setUsername(s1);
                    myUser.setPassword(s2);
                    myUser.setEmail(s3);
                    myUser.signUp(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            if (e == null) {
                                epro.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(Register.this, Login.class));
                                finish();
                            } else {
                                epro.setVisibility(View.INVISIBLE);
                                Toast.makeText(Register.this, "注册失败，稍后再试", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
                }
            }
        });


    }

}
