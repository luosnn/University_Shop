package com.luosnn.university_shop.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.luosnn.university_shop.File.Goods;
import com.luosnn.university_shop.File.Goods_New;
import com.luosnn.university_shop.R;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 罗什什 on 2017/12/12.
 */

public class Sal extends Activity {
    Button backk;
    EditText goods_name;
    EditText goods_desc;
    EditText goods_price;
    EditText good_num;
    Button goods_add , selectPhoto ,add;
    ImageView picture;
    EditText good_add;
    public static final int CHOOSE_PHOTO = 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sal);

        findView();
        initListener();
    }
   /* public void backsal(){
        backk = findViewById(R.id.title_salback);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sal.this,Main.class));
                finish();

            }
        });
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19){
                        //4.4以上使用这个方法处理图片
                        handleIMageOnKitKat(data);
                    }else{
                        handleIMageBeforKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    String imagePath = null;
    @TargetApi(19)
    private void handleIMageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this , uri)){
            //如果是document类型的URI，则使用document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID +"=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri ,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果不是document类型的URI，则使用普通方式处理
            imagePath = getImagePath(uri , null);
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(Sal.this, "未得到图片", Toast.LENGTH_SHORT).show();
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri , null , selection , null , null);
        if (cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handleIMageBeforKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri , null);
        displayImage(imagePath);
    }



    private void initListener() {
        selectPhoto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name, desc, icon_path,num,add;
                final double price;
                name = goods_name.getText().toString();
                desc = goods_desc.getText().toString();
                num = good_num.getText().toString().trim();
                price = Double.parseDouble(goods_price.getText().toString());
                add = good_add.getText().toString();
                //获取文件路径
                //icon_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/1.jpg";
                icon_path = imagePath;
                System.out.println(imagePath);
                final BmobFile bmobfile = new BmobFile(new File(icon_path));
                bmobfile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){

                            Goods_New goods = new Goods_New();
                            goods.setGoodName(name);
                            goods.setGoodDesc(desc);
                            goods.setGoodPrice(price);
                            goods.setMyNum(num);
                            goods.setAdd(add);
                            goods.setIcon(bmobfile);
                            goods.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e==null){
                                        Toast.makeText(Sal.this,"成功上架您的物品，如有买家请等待您的电话",Toast.LENGTH_LONG).show();
                                       // startActivity(new Intent(Sal.this,Main.class));
                                    }else {
                                        Toast.makeText(Sal.this,"未成功上架您的物品，失败原因"+e.toString(),Toast.LENGTH_LONG).show();
                                        //startActivity(new Intent(Sal.this,Main.class));
                                        return;

                                    }
                                }
                            });
                        }else {

                            Toast.makeText(Sal.this,"未成功上架您的物品，失败原因"+e.toString(),Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(Sal.this,Main.class));
                            return;

                        }
                    }
                });

                finish();
            }

        });


        goods_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name, desc, icon_path,num,add;
                final double price;
                name = goods_name.getText().toString();
                desc = goods_desc.getText().toString();
                num = good_num.getText().toString().trim();
                price = Double.parseDouble(goods_price.getText().toString());
                add = good_add.getText().toString();
                //获取文件路径
                //icon_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/1.jpg";
                icon_path = imagePath;
                System.out.println(imagePath);
                final BmobFile bmobfile = new BmobFile(new File(icon_path));
                bmobfile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){

                            Goods goods = new Goods();
                            goods.setGoodName(name);
                            goods.setGoodDesc(desc);
                            goods.setGoodPrice(price);
                            goods.setMyNum(num);
                            goods.setAdd(add);
                            goods.setIcon(bmobfile);
                            goods.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e==null){
                                        Toast.makeText(Sal.this,"成功上架您的物品，如有买家请等待您的电话",Toast.LENGTH_LONG).show();
                                       // startActivity(new Intent(Sal.this,Main.class));
                                    }else {
                                        Toast.makeText(Sal.this,"未成功上架您的物品，失败原因"+e.toString(),Toast.LENGTH_LONG).show();
                                        //startActivity(new Intent(Sal.this,Main.class));
                                        return;

                                    }
                                }
                            });
                        }else {

                            Toast.makeText(Sal.this,"未成功上架您的物品，失败原因"+e.toString(),Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(Sal.this,Main.class));
                            return;

                        }
                    }
                });

                finish();
            }

        });
    }
    private void findView() {
        goods_name = (EditText) findViewById(R.id.goods_name);
        goods_price = (EditText) findViewById(R.id.goods_price);
        goods_desc = (EditText) findViewById(R.id.goods_desc);
        goods_add = (Button) findViewById(R.id.goods_add);
        picture = (ImageView) findViewById(R.id.picture);
        selectPhoto = (Button) findViewById(R.id.select_photo);
        good_num = (EditText) findViewById(R.id.mynumm);
        good_add = (EditText)findViewById(R.id.adder);
        add = findViewById(R.id.goods_add_new);
    }
}
