package com.example.mylibrary.utils.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andsync.xpermission.XPermissionUtils;
import com.example.custombanner.R;
import com.example.mylibrary.utils.Constant;
import com.example.mylibrary.utils.Logger;
import com.example.mylibrary.utils.SharedUtils;
import com.example.mylibrary.utils.eventbus.Event;
import com.example.mylibrary.utils.eventbus.EventBusUtil;
import com.example.mylibrary.utils.popupwindow.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取位置工具类
 *
 * @author AndSync
 * @date 2017/10/30
 * Copyright © 2014-2017 AndSync All rights reserved.
 */
public class LocationUtils {
    private static final String TAG = "LocationUtil";
    public static boolean isOpen = false;
    public static double latitude = 0.0;
    public static double longitude = 0.0;
    private static String addressLine = "";
    private static Activity context;
    private static TextView toSetting;
    private static TextView cancel;
    private static LocationManager locationManager;

    public static void requestLocation(final Activity context) {
        if (context != null) {
            LocationUtils.context = context;
            XPermissionUtils.requestPermissions(context, RequestCode.LOCATION, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            }, new XPermissionUtils.OnPermissionListener() {
                @Override
                public void onPermissionGranted() {
                    //6.0以下这个无法明确判断是否获取位置权限
//                    startLocation(context);
                }

                @Override
                public void onPermissionDenied(String[] deniedPermissions, boolean alwaysDenied) {
                    if (alwaysDenied) {
                        DialogUtil.showPermissionManagerDialog(context, context.getString(R.string.permissionLocation));
                    } else {
                        context.finish();
                    }
                }
            });
        }
    }

    public static String getAddress(Context context) {
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> locations = geocoder.getFromLocation(LocationUtils.latitude, LocationUtils.longitude, 1);
            addressLine = "";
            if (locations != null && locations.size() > 0) {
                Address address = locations.get(0);
                String adminArea = address.getAdminArea();
                String locality = address.getLocality();
                String subLocality = address.getSubLocality();
                String thoroughfare = address.getThoroughfare();
                String subThoroughfare = address.getSubThoroughfare();
                String featureName = address.getFeatureName();

                if (adminArea != null) {
                    addressLine += adminArea;
                }

                if (locality != null && !locality.equals(adminArea)) {
                    addressLine += locality;
                }

                if (subLocality != null) {
                    addressLine += subLocality;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressLine;
    }

    @SuppressLint("MissingPermission")
    private static void startLocation(Context context) {
        //获取地理位置管理器
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers == null) {
            return;
        } else if (!providers.contains("gps")) {
            // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
            boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!gps) {
                openGPS(context);
            }
        }

        //获取Location
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //对提供者进行排序，gps、net、passive
        List<String> providerSortList = new ArrayList<>();
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            Log.d(TAG, "GPS_PROVIDER");
            providerSortList.add(LocationManager.GPS_PROVIDER);
        }
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            Log.d(TAG, "NETWORK_PROVIDER");
            providerSortList.add(LocationManager.NETWORK_PROVIDER);
        }
        if (providers.contains(LocationManager.PASSIVE_PROVIDER)) {
            Log.d(TAG, "PASSIVE_PROVIDER");
            providerSortList.add(LocationManager.PASSIVE_PROVIDER);
        }
        String locationProvider = "";
        for (int i = 0; i < providerSortList.size(); i++) {
            String provider = providerSortList.get(i);
            Log.d(TAG, "正在尝试：" + provider);
            Location location = locationManager.getLastKnownLocation(provider);

            if (location == null) {
                continue;
            }

            if (location != null) {
                locationProvider = provider;
                Log.d(TAG, "定位成功：" + provider);
                isOpen = true;
                saveLocation(location);
                break;
            } else {

                Log.d(TAG, "定位失败：" + provider);
            }
        }
        /*if (!TextUtils.isEmpty(locationProvider)) {
            locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
        }*/
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        GPSIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(GPSIntent);
    }

    public static void stopGPS() {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }

    static LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            saveLocation(location);
            if (context != null) {
                String address = getAddress(context);
                if (!address.trim().isEmpty()) {
                    SharedUtils.putString(context, "address", address);
                }
            }
        }
    };

    /**
     * 保存地理位置经度和纬度信息
     */
    private static void saveLocation(Location location) {
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            if (context != null) {
                if (longitude != 0.0 && latitude != 0.0) {
                    SharedUtils.putString(context, "lon", String.valueOf(longitude));
                    SharedUtils.putString(context, "lat", String.valueOf(latitude));
                }
            }
        }
    }
}
