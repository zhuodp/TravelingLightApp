package com.example.a74021.project1.activity_and_fragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a74021.project1.R;
import com.example.a74021.project1.services.MusicService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener {

    private Button mTabZhuye;
    private Button mTabMap;
    private Button mTabPicture;
    private Button mTabSetting;
    private Button addpic;
    private ContentFragment mWeixin;
    private MapFragment mMap;
    private RecyclerFragment mPicture;
    private settingFragment mSetting;
    public static MusicService musicService;


    //RecyclerView mRecyclerView;






    public static List<Image> mList=new ArrayList<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //图片id
        List<Integer> resIds;
        initData();
        //初始化控件和声明时间
        mTabZhuye=(Button) findViewById(R.id.tab_bottom_zhuye);
        mTabMap=(Button) findViewById(R.id.tab_bottom_map);
        mTabPicture=(Button)findViewById(R.id.tab_bottom_picture);
        mTabSetting=(Button)findViewById(R.id.tab_bottom_setting);
        mTabSetting.setOnClickListener(this);
        mTabPicture.setOnClickListener(this);
        mTabZhuye.setOnClickListener(this);
        mTabMap.setOnClickListener(this);
        addpic=(Button)findViewById(R.id.add_pic) ;
        //设置默认的Fragment
        setDefaultFragment();
        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"上传照片功能待开发", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,SelectPicture.class);
                startActivityForResult(intent,0);
            }
        });

        bindServiceConnection();
        verifyStoragePerisssions(MainActivity.this);



    }//oncreate
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0&&data!=null)
        {
            String imageFilePath=data.getStringExtra("newPicture");
            Toast.makeText(getApplicationContext(),"图片的路径是："+imageFilePath,Toast.LENGTH_LONG).show();
            Drawable drawable=Drawable.createFromPath(imageFilePath);
            ImageView imageView = (ImageView) findViewById(R.id.newPictureTemp);
            //接下来将图片传给mlist
        }

    }


    private void initData(){

        mList.add(new Image(R.drawable.img02));
        mList.add(new Image(R.drawable.img03));
        mList.add(new Image(R.drawable.img04));
        mList.add(new Image(R.drawable.img05));
        mList.add(new Image(R.drawable.img06));
        mList.add(new Image(R.drawable.img07));
        mList.add(new Image(R.drawable.img08));
        mList.add(new Image(R.drawable.img09));
        mList.add(new Image(R.drawable.img10));
        mList.add(new Image(R.drawable.img02));
        mList.add(new Image(R.drawable.img03));
        mList.add(new Image(R.drawable.img04));
        mList.add(new Image(R.drawable.img05));
        mList.add(new Image(R.drawable.img06));
        mList.add(new Image(R.drawable.img07));
        mList.add(new Image(R.drawable.img08));
        mList.add(new Image(R.drawable.img02));
        mList.add(new Image(R.drawable.img03));
        mList.add(new Image(R.drawable.img04));
        mList.add(new Image(R.drawable.img05));
        mList.add(new Image(R.drawable.img06));
        mList.add(new Image(R.drawable.img07));
        mList.add(new Image(R.drawable.img08));
        mList.add(new Image(R.drawable.img09));
        mList.add(new Image(R.drawable.img10));
        mList.add(new Image(R.drawable.img02));
        mList.add(new Image(R.drawable.img03));
        mList.add(new Image(R.drawable.img04));
        mList.add(new Image(R.drawable.img05));
        mList.add(new Image(R.drawable.img06));
        mList.add(new Image(R.drawable.img07));
        mList.add(new Image(R.drawable.img08));
    }


    class Image{  //图片类
        int imgId;
        public int getImgId(){return imgId;}
        public Image(int imgId){
            this.imgId = imgId;
        }
        public void changeImageId(int newId){this.imgId=newId;}
    }


    private void setDefaultFragment()
    {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        mWeixin=new ContentFragment();
        transaction.replace(R.id.id_content,mWeixin);
        transaction.commit();
        mTabZhuye.getBackground().setAlpha(255);
        mTabSetting.getBackground().setAlpha(125);
        mTabPicture.getBackground().setAlpha(125);
        mTabMap.getBackground().setAlpha(125);
    }

    @Override
    public void onClick(View v)
    {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        switch (v.getId())
        {
            case R.id.tab_bottom_zhuye:
                mTabZhuye.getBackground().setAlpha(255);
                mTabSetting.getBackground().setAlpha(125);
                mTabPicture.getBackground().setAlpha(125);
                mTabMap.getBackground().setAlpha(125);
                if (mWeixin==null)
                {
                    mWeixin=new ContentFragment();
                }
                //使用当前Fragment的布局代替id_content的控件
                transaction.replace(R.id.id_content,mWeixin);
                break;
            case R.id.tab_bottom_picture:
                mTabZhuye.getBackground().setAlpha(125);
                mTabSetting.getBackground().setAlpha(125);
                mTabPicture.getBackground().setAlpha(255);
                mTabMap.getBackground().setAlpha(125);
                if (mPicture==null)
                {
                    mPicture=new RecyclerFragment();
                }
                transaction.replace(R.id.id_content,mPicture);
                break;
            case R.id.tab_bottom_map:
                mTabZhuye.getBackground().setAlpha(125);
                mTabSetting.getBackground().setAlpha(125);
                mTabPicture.getBackground().setAlpha(125);
                mTabMap.getBackground().setAlpha(255);
                if(mMap==null)
                {
                    mMap=new MapFragment();
                }
                transaction.replace(R.id.id_content,mMap);

                break;
            case R.id.tab_bottom_setting:
                mTabZhuye.getBackground().setAlpha(125);
                mTabSetting.getBackground().setAlpha(255);
                mTabPicture.getBackground().setAlpha(125);
                mTabMap.getBackground().setAlpha(125);
                if (mSetting==null)
                {
                    mSetting=new settingFragment();
                }
                transaction.replace(R.id.id_content,mSetting);
                break;
        }
         //transaction.addToBackStack();
        //事务提交
        transaction.commit();
    }


    //音乐服务


    private void bindServiceConnection()
    {
        Intent intent =new Intent(MainActivity.this,MusicService.class);
        startService(intent);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    //通过IBinder获取Service对象，实现Activity和Service的绑定
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService=((MusicService.MyBinder)(service)).getService();
            Log.i("musicService",musicService+"");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService=null;
        }
    };

    //确定音乐服务权限
    public  void verifyStoragePerisssions(Activity activity)
    {
        try {
            int permission = ActivityCompat.checkSelfPermission(activity, "android.permission.READ_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"notPermission",Toast.LENGTH_SHORT).show();

            } else {
                //hasPermission=true;
                Toast.makeText(getApplicationContext(),"已经有权限",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }






}
