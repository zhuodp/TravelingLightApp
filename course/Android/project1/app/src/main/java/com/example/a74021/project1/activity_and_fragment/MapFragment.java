package com.example.a74021.project1.activity_and_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.a74021.project1.R;

/**
 * Created by 74021 on 2018/1/6.
 */

public class MapFragment extends Fragment {
    private MapView mapView=null;
    private BaiduMap bdMap;
    private LocationClient locationClient;
    public MyLocationListener myListener=new MyLocationListener();
    boolean isFirtLoc=true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        mapView=(MapView)getActivity().findViewById(R.id.map_view);
/*       //获取百度地图对象
        if (mapView.getMap()!=null) {
            bdMap = mapView.getMap();
            //开启定位图层
            bdMap.setMyLocationEnabled(true);

            //声明定位SDK核心类
            locationClient = new LocationClient(getActivity());
            //注册监听
            locationClient.registerLocationListener(myListener);
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true);//打开GPS
            option.setCoorType("bd0911");//坐标类型
            option.setScanSpan(1000);//定位请求时间间隔
            locationClient.setLocOption(option);
            locationClient.start();
        }*/
           return inflater.inflate(R.layout.fragment_map,container,false);
    }
    @Override
    public void onActivityCreated(Bundle saveInsatanceState)
    {
        super.onActivityCreated(saveInsatanceState);

    }


    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location){
            //mapview 销毁后不在处理新接收的位置
            if (location==null||mapView==null)
            {return ;}

            MyLocationData locData=new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLatitude()).build();
            bdMap.setMyLocationData(locData);
            if (isFirtLoc)
            {
                isFirtLoc=false;
                LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
                MapStatus.Builder builder=new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                bdMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }


}

