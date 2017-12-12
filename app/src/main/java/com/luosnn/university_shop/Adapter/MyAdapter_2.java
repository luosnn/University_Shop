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
import com.luosnn.university_shop.File.Goods_New;
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

public class MyAdapter_2 extends BaseAdapter {
    private Context context ;
    private List<Goods_New> list;
    public MyAdapter_2(Context context, List<Goods_New> list){
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
            num = list.get(position).getMyNum();
            name = list.get(position).getGoodName();
            desc = list.get(position).getGoodDesc();
            price = list.get(position).getGoodPrice();
            image = list.get(position).getIcon();
            add = list.get(position).getAdd();
            System.out.println(image.getFileUrl());
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item, null);//实例化一个布局文件
            viewHolder = new ViewHolder();
            viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.product_img);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.product_name_id);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.product_price_id);
            viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.product_desc_id);
            viewHolder.tv_add = convertView.findViewById(R.id.adderer);
            viewHolder.tv_num = convertView.findViewById(R.id.num);
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
                    viewHolder.iv_img.post(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("*********************************");
                            viewHolder.iv_img.setImageBitmap(bitmap);//将图片放到视图中去
                        }
                    });
                }
            }).start();
            viewHolder.tv_name.setText(name);
            viewHolder.tv_price.setText(String.valueOf(price));   //将double数据放到textView中
            viewHolder.tv_desc.setText(desc);
            viewHolder.tv_num.setText(num);
            viewHolder.tv_add.setText(add);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
    class ViewHolder{
        ImageView iv_img;
        TextView tv_name;
        TextView tv_price;
        TextView tv_desc;
        TextView tv_num;
        TextView tv_add;
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
