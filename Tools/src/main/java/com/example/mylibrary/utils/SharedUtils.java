package com.example.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 作者：Old.Boy 2019/1/9.
 */
public class SharedUtils {
    private static SharedPreferences sp;
    private static SharedPreferences sp1;
    private static SharedPreferences sp2;
    private final static String TAG = "SharedUtils";

    public static SharedPreferences getSp(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(Constant.SHARED_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static SharedPreferences getSpNote(Context context) {
        if (sp1 == null) {
            sp1 = context.getSharedPreferences(Constant.SHARED_NOTENAME, Context.MODE_PRIVATE);
        }
        return sp1;
    }

    public static SharedPreferences getSpUrl(Context context) {
        if (sp2 == null) {
            sp2 = context.getSharedPreferences(Constant.SHARED_URL, Context.MODE_PRIVATE);
        }
        return sp2;
    }

    /**
     * 存入TOKEN
     *
     * @param context 上下文
     * @param value   字符串的值
     */
    public static void putToken(Context context, String value) {
        SharedPreferences preferences = getSp(context);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("appToken", value);
        editor.commit();
        Logger.e(TAG, "appToken存储------>" + value);
    }

    /**
     * 获取TOKEN
     *
     * @param context 上下文
     * @return 得到的字符串
     */
    public static String getToken(Context context) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, "appToken------>" + sp.getString("appToken", ""));
        return sp.getString("appToken", "");
    }

    //唯一ID
    public void setRandomUUID(Context context, String deviceID) {
        SharedPreferences preferences = getSp(context);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("DEVICEID", deviceID);
        editor.commit();
    }

    public String getRandomUUID(Context context) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, "DEVICEID------>" + sp.getString("DEVICEID", ""));
        return sp.getString("DEVICEID", "");
    }

    /**
     * 存入字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的值
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = getSp(context);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
        Logger.e(TAG, key + "存储------>" + value);
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @return 得到的字符串
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, key + "------>" + sp.getString(key, ""));
        return sp.getString(key, "");
    }

    /**
     * 存入字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的值
     */
    public static void putNoteString(Context context, String key, String value) {
        SharedPreferences preferences = getSpNote(context);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
        Logger.e(TAG, key + "存储------>" + value);
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @return 得到的字符串
     */
    public static String getNoteString(Context context, String key) {
        SharedPreferences sp = getSpNote(context);
        Logger.e(TAG, key + "------>" + sp.getString(key, ""));
        return sp.getString(key, "");
    }

    public static void clearNote(Context context) {
        SharedPreferences sp = getSpNote(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的默认值
     * @return 得到的字符串
     */
    public static String getString(Context context, String key, String value) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, key + "获取------>存储值-->" + sp.getString(key, value) + "=====默认值-->" + value);
        return sp.getString(key, value);
    }

    /**
     * 保存布尔值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
        Logger.e(TAG, key + "存储------>" + value);
    }

    /**
     * 获取布尔值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, key + "获取------>存储值-->" + sp.getBoolean(key, defValue) + "=====默认值-->" + defValue);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存布尔值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putUrlBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSpUrl(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
        Logger.e(TAG, key + "存储------>" + value);
    }

    /**
     * 获取布尔值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    public static boolean getUrlBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, key + "获取------>存储值-->" + sp.getBoolean(key, defValue) + "=====默认值-->" + defValue);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 清除URL存储
     *
     * @param context
     */
    public static void clearUrl(Context context) {
        SharedPreferences sp = getSpUrl(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存布尔值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putFloat(Context context, String key, float value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.commit();
        Logger.e(TAG, key + "存储------>" + value);
    }

    /**
     * 获取布尔值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    public static float getFloat(Context context, String key, float defValue) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, key + "获取------>存储值-->" + sp.getFloat(key, defValue) + "=====默认值-->" + defValue);
        return sp.getFloat(key, defValue);
    }

    /**
     * 保存long值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putLong(Context context, String key, long value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
        Logger.e(TAG, key + "存储------>" + value);
    }

    /**
     * 获取long值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static long getLong(Context context, String key, long defValue) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, key + "获取------>存储值-->" + sp.getLong(key, defValue) + "=====默认值-->" + defValue);
        return sp.getLong(key, defValue);
    }

    /**
     * 保存int值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putInt(Context context, String key, int value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
        Logger.e(TAG, key + "存储------>" + value);
    }

    /**
     * 获取long值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = getSp(context);
        Logger.e(TAG, key + "获取------>存储值-->" + sp.getInt(key, defValue) + "=====默认值-->" + defValue);
        return sp.getInt(key, defValue);
    }

    /**
     * 保存对象
     *
     * @param context 上下文
     * @param key     键
     * @param obj     要保存的对象（Serializable的子类）
     * @param <T>     泛型定义
     */
    public static <T extends Serializable> void putObject(Context context, String key, T obj) {
        try {
            put(context, key, obj);
            Logger.e(TAG, key + "存储------>" + obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象
     *
     * @param context 上下文
     * @param key     键
     * @param <T>     指定泛型
     * @return 泛型对象
     */
    public static <T extends Serializable> T getObject(Context context, String key) {
        try {
            Logger.e(TAG, key + "获取------>存储值-->" + ((T) get(context, key)).toString());
            return (T) get(context, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储List集合
     *
     * @param context 上下文
     * @param key     存储的键
     * @param list    存储的集合
     */
    public static void putList(Context context, String key, List<? extends Serializable> list) {
        try {
            put(context, key, list);
            Logger.e(TAG, key + "存储------>" + list.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取List集合
     *
     * @param context 上下文
     * @param key     键
     * @param <E>     指定泛型
     * @return List集合
     */
    public static <E extends Serializable> List<E> getList(Context context, String key) {
        try {
            Logger.e(TAG, key + "获取------>存储值-->" + ((List<E>) get(context, key)).toString());
            return (List<E>) get(context, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储Map集合
     *
     * @param context 上下文
     * @param key     键
     * @param map     存储的集合
     * @param <K>     指定Map的键
     * @param <V>     指定Map的值
     */
    public static <K extends Serializable, V extends Serializable> void putMap(Context context, String key, Map<K, V> map) {
        try {
            put(context, key, map);
            Logger.e(TAG, key + "存储------>" + map.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Map集合
     *
     * @param context 上下文
     * @param key     键
     * @param <K>     指定Map的键
     * @param <V>     指定Map的值
     * @return 获取的集合
     */
    public static <K extends Serializable, V extends Serializable> Map<K, V> getMap(Context context, String key) {
        try {
            Logger.e(TAG, key + "获取------>存储值-->" + ((Map<K, V>) get(context, key)).toString());
            return (Map<K, V>) get(context, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储对象
     */
    private static void put(Context context, String key, Object obj)
            throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        putString(context, key, objectStr);
    }

    /**
     * 获取对象
     */
    private static Object get(Context context, String key)
            throws IOException, ClassNotFoundException {
        String wordBase64 = getString(context, key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }
}
