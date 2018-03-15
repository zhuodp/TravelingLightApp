package com.example.a74021.project1.activity_and_fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.a74021.project1.R;

/**
 * Created by 74021 on 2018/3/15.
 */

public class SelectPicture extends AppCompatActivity{
    private static final int IMAGE =1;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.select_picture);

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
}
