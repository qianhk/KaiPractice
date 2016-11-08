package com.njnu.kai.practice.location;

import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

/**
 * Created by kai
 * since 16/11/7
 */
public class AMapLocationTestFragment extends BaseTestListFragment {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

    private boolean mLocationStarted;

    @TestFunction("start or stop location")
    public void onStartLocation() {
        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        } else {
            mLocationClient.startLocation();
        }

//        if (mLocationStarted) {
//            mLocationClient.stopLocation();
//        } else {
//            mLocationClient.startLocation();
//        }
//        mLocationStarted = !mLocationStarted;
    }


    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
                setResult(LocationUtils.getLocationStr(loc));
            } else {
                setResult("定位失败，loc is null");
            }
        }
    };


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLocation();
    }

    @Override
    public void onDestroy() {
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
        super.onDestroy();
    }

    private void initLocation(){
        makeDefaultOption();
        //初始化client
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位参数
//        mLocationOption.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(mLocationOption);
        // 设置定位监听
        mLocationClient.setLocationListener(mLocationListener);
    }

    private AMapLocationClientOption makeDefaultOption(){
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mLocationOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mLocationOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mLocationOption.setInterval(5000);//可选，设置定位间隔。默认为2秒
        mLocationOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mLocationOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mLocationOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mLocationOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        return mLocationOption;
    }
}
