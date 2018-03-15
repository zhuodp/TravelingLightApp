package com.example.a74021.project1.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

/**
 * Created by 74021 on 2017/11/22.
 */

public class MusicService extends Service {
    public MediaPlayer mediaPlayer;
    public  boolean tag=false;





    public MusicService()
    {
        mediaPlayer =new MediaPlayer();
        try{
            // mediaPlayer.setDataSource("/data/test");
            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/qqmusic/song/test.mp3");
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //通过binder保持activity和service的通信
    public MyBinder binder=new MyBinder();
    public class MyBinder extends Binder{
        public MusicService getService()
        {
            return MusicService.this;
        }
    }
    public void playOrPause()
    {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
        }
        else
        {
            //mediaPlayer.prepare();
            mediaPlayer.start();
        }
    }


    public void stop()
    {
        if (mediaPlayer!=null)
        {
            mediaPlayer.stop();
            try{
                mediaPlayer.reset();
                //mediaPlayer.setDataSource("/data/test");
                mediaPlayer.setDataSource(Environment.getExternalStorageDirectory() + "/qqmusic/song/test.mp3");
                mediaPlayer.prepare();
                mediaPlayer.seekTo(0);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }




    @Override
    public IBinder onBind(Intent intent)
    {
        return binder;
    }
}
