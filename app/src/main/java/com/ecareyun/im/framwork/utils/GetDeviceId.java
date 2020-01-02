package com.ecareyun.im.framwork.utils;

import android.content.Context;
import android.os.Environment;

import com.ecareyun.im.Global;
import com.example.mylibrary.utils.SharedUtils;

import org.jsoup.helper.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.security.MessageDigest;
import java.util.UUID;

public class GetDeviceId {
    //保存文件的路径
    private static final String CACHE_IMAGE_DIR = "aray/cache/devices";
    //保存的文件 采用隐藏文件的形式进行保存
    private static final String DEVICES_FILE_NAME = ".DEVICES";

    /**
     * 获取设备唯一标识符
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        //读取保存的在sd卡中的唯一标识符
        String deviceId = readDeviceID(context);

        //判断是否已经生成过,
        if (deviceId != null && !"".equals(deviceId)) {
            return deviceId;
        }

        //用于生成最终的唯一标识符
        StringBuffer s = new StringBuffer();
        //如果以上搜没有获取相应的则自己生成相应的UUID作为相应设备唯一标识符
        if (s == null || s.length() <= 0) {
            UUID uuid = UUID.randomUUID();
            deviceId = uuid.toString().replace("-", "");
            s.append(deviceId);
        }
        //为了统一格式对设备的唯一标识进行md5加密 最终生成32位字符串
        String md5 = getMD5(s.toString(), false);
        if (s.length() > 0) {
            //持久化操作, 进行保存到SD卡中
            saveDeviceID(md5, context);
        }
        return md5;
    }


    /**
     * 读取固定的文件中的内容,这里就是读取sd卡中保存的设备唯一标识符
     *
     * @param context
     * @return
     */
    public static String readDeviceID(Context context) {
        File file = getDevicesDir(context);
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            Reader in = new BufferedReader(isr);
            int i;
            while ((i = in.read()) > -1) {
                buffer.append((char) i);
            }
            in.close();
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存 内容到 SD卡中,  这里保存的就是 设备唯一标识符
     * @param str
     * @param context
     */
    public static void saveDeviceID(String str, Context context) {
        File file = getDevicesDir(context);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对挺特定的 内容进行 md5 加密
     * @param message  加密明文
     * @param upperCase  加密以后的字符串是是大写还是小写  true 大写  false 小写
     * @return
     */
    public static String getMD5(String message, boolean upperCase) {
        String md5str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] input = message.getBytes();

            byte[] buff = md.digest(input);

            md5str = bytesToHex(buff, upperCase);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }


    public static String bytesToHex(byte[] bytes, boolean upperCase) {
        StringBuffer md5str = new StringBuffer();
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];

            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        if (upperCase) {
            return md5str.toString().toUpperCase();
        }
        return md5str.toString().toLowerCase();
    }

    /**
     * 统一处理设备唯一标识 保存的文件的地址
     * @param context
     * @return
     */
    private static File getDevicesDir(Context context) {
        File mCropFile = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cropdir = new File(Environment.getExternalStorageDirectory(), CACHE_IMAGE_DIR);
            if (!cropdir.exists()) {
                cropdir.mkdirs();
            }
            mCropFile = new File(cropdir, DEVICES_FILE_NAME); // 用当前时间给取得的图片命名
        } else {
            File cropdir = new File(context.getFilesDir(), CACHE_IMAGE_DIR);
            if (!cropdir.exists()) {
                cropdir.mkdirs();
            }
            mCropFile = new File(cropdir, DEVICES_FILE_NAME);
        }
        return mCropFile;
    }


    /**
     * 与上面内容无关
     * @param biz
     * @return
     */
    public static String createRandomUUID(Context context){
        String readDeviceID ="";
        try {
            //获取保存在sd中的 设备唯一标识符
            readDeviceID = GetDeviceId.readDeviceID(context);
            //获取缓存在  sharepreference 里面的 设备唯一标识
            String string = SharedUtils.getString(context, Global.APP_IMEI, "");

            //最初安装 没有SP和文件存储
            if ( !AppUtils.isEmptyString(string) && !AppUtils.isEmptyString(readDeviceID)){//两个都是空的 生成UUID

                readDeviceID = GetDeviceId.getDeviceId(context);

            } else if(AppUtils.isEmptyString(string) && !AppUtils.isEmptyString(readDeviceID)){// SP不为空  文件为空

                if (StringUtil.isBlank(readDeviceID) && !string.equals(readDeviceID)) {
                    // 取有效地 app缓存 进行更新操作
                    if (StringUtil.isBlank(readDeviceID) && !StringUtil.isBlank(string)) {
                        readDeviceID = string;
                        GetDeviceId.saveDeviceID(readDeviceID, context);
                    }
                }

            }
            //最后后再次更新app 的缓存
            SharedUtils.putString(context, Global.APP_IMEI, readDeviceID);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return readDeviceID;
    }
}
