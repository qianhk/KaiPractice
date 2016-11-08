package com.njnu.kai.practice.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.ToastUtils;
import com.njnu.kai.support.ViewUtils;

/**
 * Created by kai
 * since 16/11/8
 */
public class AMap2DTestFragment extends BaseTestFragment implements View.OnClickListener, AMapLocationListener {

    private static final String TAG = "AMap2DTestFragment";
    private MapView mMapView;
    private AMap mMap;

    private AMapLocationClient mLocationClient;

    private LocationSource.OnLocationChangedListener mLocationChangedListener;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_amap_2d, viewGroup, false);
        mMapView = (MapView) view.findViewById(R.id.mapview);
        mMapView.onCreate(getArguments());
        mMap = mMapView.getMap();

        UiSettings uiSettings = mMap.getUiSettings();
//        uiSettings.setScrollGesturesEnabled(false);
//        uiSettings.setZoomGesturesEnabled(false);
        uiSettings.setCompassEnabled(true); //指南针、罗盘
        uiSettings.setZoomControlsEnabled(false); //缩放+-控件
        uiSettings.setScaleControlsEnabled(true); //比例尺
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);


        uiSettings.setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setLocationSource(new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {
                LogUtils.i(TAG, "lookLocation activate");
                mLocationChangedListener = onLocationChangedListener;
                mLocationClient.startLocation();
            }

            @Override
            public void deactivate() {
                LogUtils.i(TAG, "lookLocation deactivate");
                mLocationClient.stopLocation();
                mLocationChangedListener = null;
            }
        });

        ViewUtils.bindClickListener(view, this, R.id.basicmap, R.id.rsmap);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setOnceLocation(true);
        locationOption.setNeedAddress(true);

        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationOption(locationOption);
        // 设置定位监听
        mLocationClient.setLocationListener(this);

        AMapLocation lastKnownLocation = mLocationClient.getLastKnownLocation();
        if (lastKnownLocation != null) {
            LatLng lastKnownLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownLatLng, 18));
        }

        mMapView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLocationClient.startLocation();
            }
        }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.basicmap) {
            mMap.setMapType(AMap.MAP_TYPE_NORMAL);
        } else if (viewId == R.id.rsmap) {
            mMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        }
    }

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null && location.getErrorCode() == 0) {
            ToastUtils.showToast("获取定位信息成功: poi=" + location.getPoiName());
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
            mMap.animateCamera(cameraUpdate, 3000, new AMap.CancelableCallback() {
                @Override
                public void onFinish() {
                }

                @Override
                public void onCancel() {
                }
            });
        } else {
            ToastUtils.showToast("获取定位信息失败");
        }
        if (mLocationChangedListener != null) {
            mLocationChangedListener.onLocationChanged(location);
        }
    }
}
