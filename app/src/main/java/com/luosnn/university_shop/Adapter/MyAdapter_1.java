package com.luosnn.university_shop.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luosnn.university_shop.File.Goods;
import com.luosnn.university_shop.File.UserInfo;
import com.luosnn.university_shop.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 罗什什 on 2017/12/11.
 */

public class MyAdapter_1 extends BaseAdapter {
    private Context context ;
    private List<UserInfo> list;
    public MyAdapter_1(Context context, List<UserInfo> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            String name;
            String desc,add;
            double price;
            String num;
            BmobFile image;
            num = list.get(position).getMyuserNum();
            name = list.get(position).getMyuserNick();
            desc = list.get(position).getMyuserSex();
            price = list.get(position).getMuuserYear();
            image = list.get(position).getIcon();
            add = list.get(position).getMyuserAdd();
            System.out.println(image.getFileUrl());
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.tou_img = (ImageView) convertView.findViewById(R.id.product_img);
            viewHolder.mynick = (TextView) convertView.findViewById(R.id.product_name_id);
            viewHolder.myyear = (TextView) convertView.findViewById(R.id.product_price_id);
            viewHolder.mysex = (TextView) convertView.findViewById(R.id.product_desc_id);
            viewHolder.myxx = convertView.findViewById(R.id.adderer);
            viewHolder.mynum = convertView.findViewById(R.id.num);
            convertView.setTag(viewHolder);
            //不能直接在主线程中进行从网络端获取图片，而需要单独开一个子线程完成从网络端获取图片
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //根据表中图片的url地址来得到图片（Bitmap类型）
                    final Bitmap bitmap=getPicture(list.get(position).getIcon().getFileUrl());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    viewHolder.tou_img.post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("*********************************");
                            viewHolder.tou_img.setImageBitmap(bitmap);//将图片放到视图中去
                        }
                    });
                }
            }).start();
            viewHolder.mynick.setText(name);
            viewHolder.myyear.setText(String.valueOf(price));   //将double数据放到textView中
            viewHolder.mysex.setText(desc);
            viewHolder.mynum.setText(num);
            viewHolder.myxx.setText(add);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        ImageView tou_img;
        TextView mynick;
        TextView myyear;
        TextView mysex;
        TextView mynum;
        TextView myxx;
    }
    public Bitmap getPicture(String path){
        Bitmap bm=null;
        try{
            URL url=new URL(path);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bm;
    }
}
