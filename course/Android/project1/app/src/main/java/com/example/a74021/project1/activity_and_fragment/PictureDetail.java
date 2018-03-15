package com.example.a74021.project1.activity_and_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a74021.project1.R;

/**
 * Created by 74021 on 2018/1/9.
 */

public class PictureDetail extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_photo_entry);
        final Activity activity = this;


        ImageView img = (ImageView)this.findViewById(R.id.large_image);
            int picId = getIntent().getExtras().getInt("pictureId");
            img.setImageResource(picId);
        Toast toast = Toast.makeText(this, "点击图片即可返回",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
        img.setOnClickListener(new View.OnClickListener() { // 点击返回
            public void onClick(View paramView) {
                activity.finish();
            }
        });
    }
}
