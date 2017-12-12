package com.luosnn.university_shop.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.luosnn.university_shop.Adapter.MyAdapter;
import com.luosnn.university_shop.File.Goods;
import com.luosnn.university_shop.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 罗什什 on 2017/12/11.
 */

public class Product extends Activity {
    ImageView imageView ;

    ListView listView;
    Button backk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        listView = findViewById(R.id.lv);
        back();
        imageView =  findViewById(R.id.product_img);
        listView = findViewById(R.id.lv);
        BmobQuery<Goods> bmobQuery = new BmobQuery<Goods>();
        bmobQuery.findObjects(new FindListener<Goods>() {  //按行查询，查到的数据放到List<Goods>的集合
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e == null){

                    final String[] name = new String[list.size()];
                    final String[] desc = new String[list.size()];
                    final String[] price = new String[list.size()];
                    final  String[] num = new String[list.size()];
                    final BmobFile[] image = new BmobFile[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        name[i] = list.get(i).getGoodName();
                        desc[i] = list.get(i).getGoodDesc();
                        //price[i] = list.get(i).getPrice();
                        num[i] = list.get(i).getMyNum();
                        list.get(i).getIcon();//loadImageThumbnail(GroundActivity.this, imageView, 300, 300);
                        image[i] = list.get(i).getIcon();
                        System.out.println("****************************2222");
                    }
                        listView.setAdapter(new MyAdapter(Product.this , list));
                }

            }
        });

    }
    public void back(){
        backk = findViewById(R.id.title_pback);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Product.this,Main.class));
            }
        });
    }
//---------------------------------------------------------------------------------------------

            }



