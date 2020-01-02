package com.example.mylibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 作者：Old.Boy 2019/1/9.
 */
public class OKHttpUtils {

    private static final String TAG = "OKHttpUtils";
    private static OKHttpUtils mInstance;
    private OkHttpClient client;
    private Handler mHandler;
    private boolean isCache = false;//是否缓存
    private File cacheFile;//缓存文件夹
    private Cache cache;

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private Map<String, String> mMap;
    private Bitmap bitmap;

    private OKHttpUtils() {
        client = new OkHttpClient();//构建OkhttpClient
        client.setConnectTimeout(10, TimeUnit.SECONDS);//连接的超时时间
        client.setWriteTimeout(10, TimeUnit.SECONDS);//响应的超时时间
        client.setReadTimeout(30, TimeUnit.SECONDS);//请求的超时时间
        client.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));//允许使用cook
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                bitmap = (Bitmap) data.get("bitmap");
            }
        };//获取主线程Handler
    }

    private static synchronized OKHttpUtils getInstance() {
        if (mInstance == null) {
            mInstance = new OKHttpUtils();
        }
        return mInstance;
    }

    /**
     * 设置是否进行数据缓存
     */
    private void setIsCache(Context context) {
        if (isCache) {
            cacheFile = new File(context.getExternalCacheDir().toString(), "cache");
            int cacheSize = 10 * 1024 * 1024;//缓存大小：10MB
            cache = new Cache(cacheFile, cacheSize);
            client.setCache(cache);
        } else {
            client.setCache(null);
        }
    }

    /**
     * 构造get请求
     *
     * @param url
     * @param resultCallBack
     */
    private void getRequest(String url, final ResultCallBack resultCallBack) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        processResult(resultCallBack, request);
    }

    /**
     * 构造get请求
     *
     * @param url
     */
    public void getRequest(final Context context, String url, final ImageView avatar) {
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        avatar.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }


    /**
     * 构造post请求
     *
     * @param url
     * @param params
     * @param resultCallBack
     */
    private void postRequest(String url, Map<String, String> params, final ResultCallBack resultCallBack) {
        Request request = buildPostRequest(url, params);
        processResult(resultCallBack, request);
    }

    /**
     * 遍历map,添加请求参数
     *
     * @param url
     * @param params
     * @return
     */
    private Request buildPostRequest(String url, Map<String, String> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params != null) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                builder.add(param.getKey(), param.getValue());
            }
        }
        RequestBody body = builder.build();
        return new Request.Builder().url(url).post(body).build();
    }

    /**
     * 构建post上传图片、文件发送请求
     *
     * @param url
     * @param params
     * @param files
     * @param resultCallBack
     */
    public void postFileRequest(String url, Map<String, String> params, List<File> files, final ResultCallBack resultCallBack) {
        Request request = buildPostFileRequest(url, params, files);
        processResult(resultCallBack, request);
    }

    /**
     * 添加上传图片文件以及其他参数
     *
     * @param url
     * @param params
     * @param files
     * @return
     */
    private Request buildPostFileRequest(String url, Map<String, String> params, List<File> files) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (Map.Entry<String, String> param : params.entrySet()) {
            builder.addFormDataPart(param.getKey(), param.getValue());
        }
        for (File file : files) {
            builder.addFormDataPart(Constant.FILE_KEY, null, RequestBody.create(MEDIA_TYPE_PNG, file));
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * 处理请求结果，回调
     *
     * @param resultCallBack
     * @param request
     */
    private void processResult(final ResultCallBack resultCallBack, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailCallBack(resultCallBack, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String resultData = response.body().string();
                sendSuccessCallBack(resultCallBack, resultData);
            }
        });
    }

    /**
     * 下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储文件夹
     * @param callback
     */
    private void getDownloadFile(final String url, final String destFileDir, final ResultCallBack callback) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailCallBack(callback, e);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    double current = 0;
                    double total = response.body().contentLength();

                    is = response.body().byteStream();
                    File file = new File(destFileDir, "download");
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        Logger.w(TAG, "download current------>" + current);
                        sendProgress(total, current, callback);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessCallBack(callback, file.getAbsolutePath());
                } catch (IOException e) {
                    sendFailCallBack(callback, e);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 下载文件进度
     *
     * @param total
     * @param current
     * @param callback
     */
    private void sendProgress(final double total, final double current, final ResultCallBack callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onProgress(total, current);
                }
            }
        });
    }

    /**
     * 发送成功的回调
     *
     * @param resultCallBack
     * @param resultData
     */
    private void sendSuccessCallBack(final ResultCallBack resultCallBack, final String resultData) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (resultCallBack != null) {
                    resultCallBack.onSuccess(resultData);
                }
            }
        });
    }

    /**
     * 发送失败的回调
     *
     * @param resultCallBack
     * @param e
     */
    private void sendFailCallBack(final ResultCallBack resultCallBack, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (resultCallBack != null) {
                    resultCallBack.onFailure(e);
                }
            }
        });
    }

    /**
     * 对外提供的get请求方法
     *
     * @param url
     * @param callBack
     */
    public static void get(String url, ResultCallBack callBack) {
        getInstance().getRequest(url, callBack);
    }

    public static void get(Context context, String url, ImageView avatar) {
        getInstance().getRequest(context, url, avatar);
    }

    /**
     * 对外提供的post请求方法
     *
     * @param url
     * @param params
     * @param callBack
     */
    public static void post(String url, Map<String, String> params, ResultCallBack callBack) {
        getInstance().postRequest(url, params, callBack);
    }

    /**
     * 对外提供的post上传图片、文件的请求方法
     *
     * @param url
     * @param params
     * @param callBack
     */
    public static void postFile(String url, Map<String, String> params, List<File> files, ResultCallBack callBack) {
        getInstance().postFileRequest(url, params, files, callBack);
    }

    /**
     * 返回数据回调类
     *
     * @param <T>
     */
    public static abstract class ResultCallBack<T> {

        public abstract void onSuccess(T response);

        public void onProgress(double total, double current) {
        }

        public abstract void onFailure(Exception e);
    }
}
