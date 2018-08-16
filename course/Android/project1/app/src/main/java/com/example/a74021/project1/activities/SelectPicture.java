package com.example.a74021.project1.activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.a74021.project1.R;
import com.example.a74021.project1.selfDefinedDataType.SelfDefinedView;

import java.security.Permission;

/**
 * Created by 74021 on 2018/3/15.
 */

public class SelectPicture extends AppCompatActivity{
    private static final int IMAGE =1;
    private String[] storagePermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.select_picture);
        //动态获取需要的权限
        //版本判断。当手机api版本大于23时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            //检查权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this,storagePermission[0]);
            if (i != PackageManager.PERMISSION_GRANTED){
                //如果没有获得权限，则动态申请权限
                requestPermission();
            }
        }
    }
    public void onClick(View v)
    {
        Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent,IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        SelfDefinedView selfDefinedView = new SelfDefinedView(getApplicationContext(),null);
        TextView textView=(TextView)selfDefinedView;
        //获取图片路径
      if (requestCode==IMAGE&&resultCode== Activity.RESULT_OK && data!=null)
        {
            Uri selectedImage= data.getData();
            String[] filePathColumns={MediaStore.Images.Media.DATA};
            Cursor c=getContentResolver().query(selectedImage,filePathColumns,null,null,null);
            c.moveToFirst();
            int columIndex =c.getColumnIndex(filePathColumns[0]);
            String imagePath=c.getString(columIndex);
            c.close();


            Intent intent =new Intent();
            intent.putExtra("newPicture",imagePath);
            setResult(0,intent);
            finish();
        }

}


    private void requestPermission(){
        ActivityCompat.requestPermissions(this,storagePermission,0);
    }
/*    //权限申请的回调方法（是否再次申请，用户是否点击了不再提示）
    @Override
    public void onRequestPermissionResult(int requestCode , @NonNull String[] permisssions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permisssions,grantResults);

    }*/
}
