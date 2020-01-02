package com.ecareyun.im;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ecareyun.im.contract.MainContract;
import com.ecareyun.im.framwork.mvp.BaseActivity;
import com.ecareyun.im.framwork.mvp.BasePresenter;
import com.ecareyun.im.imsdk.R;
import com.ecareyun.im.imsdk.YbImClient;
import com.ecareyun.im.model.bean.message.BaseMessage;
import com.ecareyun.im.model.db.manager.DaoUtils;
import com.ecareyun.im.presenter.MainPstImpl;
import com.ecareyun.im.push.MPushMessageFactory;
import com.example.mylibrary.utils.DateUtils;
import com.example.mylibrary.utils.Logger;
import com.example.mylibrary.utils.SharedUtils;
import com.mpush.api.ack.AckCallback;
import com.mpush.api.ack.AckModel;
import com.mpush.api.protocol.Packet;
import com.mpush.api.push.MsgType;
import com.mpush.api.push.PushContext;
import com.mpush.api.push.PushMsg;
import com.mpush.client.AllotClient;

import java.util.List;
import java.util.Random;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity<MainPstImpl> implements MainContract.MainView,EasyPermissions.PermissionCallbacks {


    private static final String[] LOCATION_AND_CONTACTS ={
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
    };

    @Override
    protected void doBeforeInitView() {
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.btn_csh);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YbImClient.getInstance().init(MainActivity.this,"");
            }
        });
        Button btn1 = findViewById(R.id.btn_send);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YbImClient.getInstance().pushTextMsg("测试啊",10005,"栅栏", new YbImClient.OnSendMsgListener() {
                    @Override
                    public void sendMsgSuc(BaseMessage message) {

                    }

                    @Override
                    public void sendMsgError(String errMsg) {

                    }
                });
            }
        });
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void doAfterInitView() {
        locationAndContactsTask();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected MainPstImpl createPresenter() {
        return new MainPstImpl();
    }

    @Override
    public void showLoadingDialog(String msg) {

    }



    @Override
    public void dismissLoading() {

    }

    @Override
    public void dismissAlertDialog() {

    }

    @Override
    public void getRes() {

    }

    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()&&hasPhoneStatePermission()) {

        }  else{
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location_contacts),
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_AND_CONTACTS);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        switch (requestCode){
            case REQUEST_CODE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setTitle("提示");
                builder.setMessage("需要打开允许来自此来源，请去设置中开启此权限,是否同意?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startInstallPermissionSettingActivity();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();
                break;
            default:
                if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                    new AppSettingsDialog.Builder(this).build().show();
                }
                break;
        }
    }

    private static final int REQUEST_CODE = 10086;
    //  跳转到 设置界面去 开启权限
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Uri packageURI = Uri.parse("package:"+getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
        startActivityForResult(intent,REQUEST_CODE);
    }

    private boolean hasCameraPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA);
    }

    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }

    private boolean hasPhoneStatePermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE);
    }
    private boolean hasSmsPermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.SEND_SMS);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasCameraPermission() ? yes : no,
                            hasLocationAndContactsPermissions() ? yes : no,
                            hasSmsPermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
        if (requestCode == REQUEST_CODE ) {
            //  没有拿到权限，申请权限
            EasyPermissions.requestPermissions(MainActivity.this, "安装app需要您勾选允许安装未知应用权限", REQUEST_CODE, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES});
        }
    }
}
