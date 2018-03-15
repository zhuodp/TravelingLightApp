package com.example.a74021.project1.activity_and_fragment;
import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.example.a74021.project1.services.MusicService;
import com.example.a74021.project1.R;

import static com.example.a74021.project1.activity_and_fragment.MainActivity.musicService;


/**
 * Created by 74021 on 2018/1/5.
 */

public class TitleFragment extends Fragment {
    private Button mLeftMenu;
    private Button add_pic;
    private PopupWindow popupWindow;
    private  int from=0;

    private  boolean musicOn=false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        
        View view=inflater.inflate(R.layout.fragment_title,container,false);
        add_pic=(Button)view.findViewById(R.id.add_pic);
        mLeftMenu=(Button)view.findViewById(R.id.id_title_left_btn);

        mLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from= Location.LEFT.ordinal();
                initPopupWindow();
            }
        });

        return view;
    }


    //添加弹出时的popWin关闭事件，将背景透明度改回来
    class popupDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss(){
            backgroundAlpha(1f);
        }
    }
    protected void initPopupWindow(){
        View popupWindowView = getActivity().getLayoutInflater().inflate(R.layout.pop, null);
        //内容，高度，宽度

        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        //动画效果
            popupWindow.setAnimationStyle(R.style.AnimationLeftFade);
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        //宽度
        //popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        //高度
        //popupWindow.setHeight(LayoutParams.FILL_PARENT);
        //显示位置
        popupWindow.showAtLocation(getActivity().getLayoutInflater().inflate(R.layout.fragment_title, null), Gravity.LEFT, 0, 500);
        //设置背景半透明
        backgroundAlpha(0.5f);
        //关闭事件
        popupWindow.setOnDismissListener(new popupDismissListener());

        popupWindowView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
				/*if( popupWindow!=null && popupWindow.isShowing()){
					popupWindow.dismiss();
					popupWindow=null;
				}*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });

        final Button open = (Button)popupWindowView.findViewById(R.id.open);
        final Button save = (Button)popupWindowView.findViewById(R.id.save);
        final Button close = (Button)popupWindowView.findViewById(R.id.close);


        open.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (musicOn==false)
                {
                    musicOn=true;
                    musicService.playOrPause();
                    open.setText("音乐开关");
                }
                else
                {
                    musicOn=false;
                    musicService.playOrPause();
                }
                popupWindow.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "功能待开发", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "功能待开发", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
    }
    //设置添加屏幕的背景透明度
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp=getActivity().getWindow().getAttributes();
        lp.alpha=bgAlpha;//0-1
        getActivity().getWindow().setAttributes(lp);
    }
    //菜单弹出方向
    public enum Location{
        LEFT,
        RIGHT,
        TOP,
        BOTTOM;
    }


}
