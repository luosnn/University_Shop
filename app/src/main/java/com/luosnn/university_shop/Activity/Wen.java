package com.luosnn.university_shop.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.luosnn.university_shop.R;

/**
 * Created by 罗什什 on 2017/12/12.
 */

public class Wen extends Activity {
    Button backk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.wen);
        backwen();
    }
    public void backwen(){
        backk = findViewById(R.id.title_wback);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Wen.this,Main.class));
finish();
            }
        });
    }
}
