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
import com.luosnn.university_shop.File.UserInfo;
import com.luosnn.university_shop.R;

import java.io.File;
import java.util.Set;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 罗什什 on 2017/12/12.
 */

public class Setting extends Activity {
    Button backk;
    EditText nick;
    EditText sex;
    EditText year;
    EditText mynum;
    Button goods_setadd , selectsetPhoto ;
    ImageView setpicture;
    EditText xx;
    public static final int CHOOSE_PHOTO = 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.setting);

        findView();
        initListener();
    }

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
            setpicture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(Setting.this, "未得到图片", Toast.LENGTH_SHORT).show();
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
        selectsetPhoto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });


        goods_setadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name, desc, icon_path,num,add;
                final double price;
                name = nick.getText().toString();
                desc = sex.getText().toString();
                num = mynum.getText().toString().trim();
                price = Double.parseDouble(year.getText().toString());
                add = xx.getText().toString();
                //获取文件路径
                //icon_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/1.jpg";
                icon_path = imagePath;
                System.out.println(imagePath);
                final BmobFile bmobfile = new BmobFile(new File(icon_path));
                bmobfile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){

                            UserInfo goods = new UserInfo();
                            goods.setMyuserNick(name);
                            goods.setMyuserSex(desc);
                            goods.setMuuserYear(price);
                            goods.setMyuserNum(num);
                            goods.setMyuserAdd(add);
                            goods.setIcon(bmobfile);
                            goods.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e==null){
                                        Toast.makeText(Setting.this,"成功更新个人信息，如有买家请等待您的电话",Toast.LENGTH_LONG).show();
                                        // startActivity(new Intent(Sal.this,Main.class));
                                    }else {
                                        Toast.makeText(Setting.this,"更新个人信息失败，失败原因"+e.toString(),Toast.LENGTH_LONG).show();
                                        //startActivity(new Intent(Sal.this,Main.class));
                                        return;

                                    }
                                }
                            });
                        }else {

                            Toast.makeText(Setting.this,"更新个人信息失败，失败原因"+e.toString(),Toast.LENGTH_LONG).show();
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
        nick = (EditText) findViewById(R.id.s1);
        year = (EditText) findViewById(R.id.s3);
        sex = (EditText) findViewById(R.id.s2);
        goods_setadd = (Button) findViewById(R.id.goods_SETadd);
        setpicture = (ImageView) findViewById(R.id.SETpicture);
        selectsetPhoto = (Button) findViewById(R.id.select_SETphoto);
        mynum = (EditText) findViewById(R.id.s4);
        xx = (EditText)findViewById(R.id.s5);
    }
   /* public void backsss(){
        backk = findViewById(R.id.title_pback);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting.this,Main.class));
                finish();

            }
        });
    }*/
}
