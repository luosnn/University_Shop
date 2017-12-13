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

import com.luosnn.university_shop.File.FeedBack;
import com.luosnn.university_shop.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 罗什什 on 2017/12/12.
 */

public class Jian extends Activity {
    Button backk;
    Button feed;
    EditText feed_ed;
    ProgressBar jian;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.jian);
        feed_ed = findViewById(R.id.feedback_edit);
        feed = findViewById(R.id.feed_back_but);
        jian = findViewById(R.id.jian_pro);
        feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jian.setVisibility(View.VISIBLE);
                String string = feed_ed.getText().toString();
                if (string == null) {
                    jian.setVisibility(View.INVISIBLE);
                    Toast.makeText(Jian.this,"不可反馈空值",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    FeedBack feedBack = new FeedBack();
                    feedBack.setFeedback(string);
                    feedBack.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e==null){
                                jian.setVisibility(View.INVISIBLE);
                                Toast.makeText(Jian.this,"谢谢您的建议已被开发者收纳，会尽快为您适配的，请耐心等待",Toast.LENGTH_LONG).show();
                                feed_ed.setText("");
                                return;
                            }else {
                                jian.setVisibility(View.INVISIBLE);
                                Toast.makeText(Jian.this,"反馈失败，稍后再试",Toast.LENGTH_LONG).show();
                                feed_ed.setText("");
                                return;
                            }
                        }
                    });
                }
            }
        });

        backjian();

    }
    public void backjian(){
        backk = findViewById(R.id.title_jianback);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Jian.this,Main.class));
                finish();
            }
        });
    }
}
