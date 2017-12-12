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
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 罗什什 on 2017/12/11.
 */

public class Forget extends Activity {
    private EditText e;
    private Button b;
    private ProgressBar p;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.forget);
        e = findViewById(R.id.forgot_email);
        b = findViewById(R.id.forgot_to);
        p = findViewById(R.id.fp);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.setVisibility(View.VISIBLE);
                String s1 = e.getText().toString().trim();
                if (s1 == null) {
                    Toast.makeText(Forget.this, "请输入正确邮箱", Toast.LENGTH_SHORT).show();
                } else {
                    MyUser.resetPasswordByEmail(s1, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                    if (e == null) {
                                        p.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Forget.this, "发送邮箱成功，请在邮箱验证更改密码", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Forget.this, Login.class));
                                        finish();
                                    } else {
                                        p.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Forget.this, "发送邮箱失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            }
                    );
                }
            }
        });
    }
}
