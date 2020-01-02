package com.ecareyun.im.api;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.ecareyun.im.AppConst;
import com.ecareyun.im.model.bean.OSSConfigBean;
import com.ecareyun.im.model.bean.UpLoadImgInfo;
import com.example.mylibrary.utils.DateUtils;
import com.example.mylibrary.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TODO OSS上传文件到阿里服务
 */
public class OSSUploadFileConfig {
    /**
     * 服务器的地址"http://oss-cn-beijing.aliyuncs.com"
     */
    public static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";

    private static final String TAG = OSSUploadFileConfig.class.getSimpleName();
    private static OSSUploadFileConfig ossUploadFileConfig;

    private ClientConfiguration config = null;
    private OSS oss = null;
    private PutObjectRequest request = null;
    private OSSAsyncTask task = null;
    private OnUploadFile uploadFile;
    //http://oss-demo.aliyuncs.com:23450
    private String mCallbackAddress = "";
    public static final String CALLBACK_URL = "callbackUrl";
    public static final String CALLBACK_BODY = "callbackBody";

    private Activity context;
    private List<String> imageUrls;
    private List<UpLoadImgInfo> files;

    public OSSUploadFileConfig(Activity context) {
        this.context = context;
    }

    public static synchronized OSSUploadFileConfig getInstance(Activity context) {
        if (ossUploadFileConfig == null) {
            synchronized (OSSUploadFileConfig.class) {
                if (ossUploadFileConfig == null) {
                    ossUploadFileConfig = new OSSUploadFileConfig(context);
                }
            }
        }
        return ossUploadFileConfig;
    }

    public void init(List<UpLoadImgInfo> files) {
        this.files = files;
        OKHttpUtils.post(AppConst.OSS_CONFIG, "", new OKHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(Object response) {
                try {
                    OSSConfigBean ossConfig = new Gson().fromJson(response.toString(), new TypeToken<OSSConfigBean>() {
                    }.getType());
                    OSSConfigBean.DataBean data = ossConfig.getData();
                    String accessKeyId = data.getAccessKeyId();
                    String accessKeySecret = data.getAccessKeySecret();
                    String securityToken = data.getSecurityToken();
                    long expiration = data.getExpiration();

                    initOSS(accessKeyId, accessKeySecret, securityToken, expiration);
                    imageUrls = new ArrayList<>();
                    for (int i = 0; i < files.size(); i++) {
                        File file = files.get(i).getFile();
                        String fileName = files.get(i).getFileName();
                        String yyyyMMdd = DateUtils.getDateFormat("yyyyMMdd");
                        uploadFile(fileName, file.getPath(), "topic" + yyyyMMdd, yyyyMMdd);
                    }
                } catch (Exception e) {
                    if (onNetListener != null) {
                        onNetListener.onError();
                    } else {
                        Logger.e(TAG, e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                try {
                    onNetListener.onError();
                } catch (Exception error) {
                    Logger.e(TAG, error.toString());
                }
            }
        });
    }

    /**
     * STS凭证的三个参数
     * 调用这个方法之前必须先设置accessKeyId，accessKeySecret，securityToken;
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param securityToken   签名token
     * @param expiration      过期时间
     */
    public void initOSS(String accessKeyId, String accessKeySecret, String securityToken, long expiration) {
        config = new ClientConfiguration();
        config.setConnectionTimeout(5 * 60 * 1000);//连接超时时间
        config.setSocketTimeout(5 * 60 * 1000);//锁定超时时间
        config.setMaxConcurrentRequest(9);//最大并行竞合请求
        config.setMaxErrorRetry(3);//最大错误重试次数
        OSSLog.enableLog();
        OSSFederationToken ossFederationToken = new OSSFederationToken(accessKeyId, accessKeySecret, securityToken, expiration);
        //配置签名密钥
        OSSStsTokenCredentialProvider provider = new OSSStsTokenCredentialProvider(ossFederationToken);
        oss = new OSSClient(context, ENDPOINT, provider, config);
    }

    /**
     * 上传文件
     *
     * @param uploadFileName
     * @param uploadFilePath
     * @param fileName
     * @param reminderId
     */
    public void uploadFile(String uploadFileName, String uploadFilePath, String fileName, String reminderId) {
        request = new PutObjectRequest(AppConst.OSS_TNAME, uploadFileName, uploadFilePath);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        request.setMetadata(metadata);
        request.setCRC64(OSSRequest.CRC64Config.YES);
        if (!TextUtils.isEmpty(mCallbackAddress)) {
            request.setCallbackParam(new HashMap<String, String>() {
                {
                    //这里 filename 中的object 不用替换，如果到oss了，object 将自动在服务器自动被上传文件名替换 并且是全路径名称
                    put(CALLBACK_URL, mCallbackAddress);
                    put(CALLBACK_BODY, "filename=${object}" + "&reminder_id=${x:" + reminderId + "}");
                }
            });
        }

        request.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Logger.d(TAG, request.getObjectKey() + "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        task = oss.asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Logger.d(TAG, "uploadSuccess");
                        if (uploadFile != null) {
                            //"https://public-tbank.oss-cn-beijing.aliyuncs.com/"
                            imageUrls.add(AppConst.BASE_URL_OSS + request.getObjectKey());
                            Log.e("aaaaaaa", AppConst.BASE_URL_OSS + request.getObjectKey());
                            if (files.size() == imageUrls.size()) {
                                uploadFile.onUploadFileSuccess(imageUrls);
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (uploadFile != null) {
                            try {
                                if (serviceException != null) {
                                    uploadFile.onUploadFileFailed(serviceException.toString());
                                } else {
                                    uploadFile.onUploadFileFailed("上传失败");
                                }
                            } catch (Exception e) {
                                uploadFile.onUploadFileFailed("上传失败");
                            }
                        }

                        if (clientException != null) {
                            // Local exception, such as a network exception
                            clientException.printStackTrace();
                        }
                        if (serviceException != null) {
                            // Service exception
                            Logger.d(TAG, "ErrorCode=" + serviceException.getErrorCode());
                            Logger.d(TAG, "RequestId=" + serviceException.getRequestId());
                            Logger.d(TAG, "HostId=" + serviceException.getHostId());
                            Logger.d(TAG, "RawMessage=" + serviceException.getRawMessage());
                        }
                    }
                });
            }
        });
    }

    //TODO OSS上传图片接口回调
    public void setOnUploadFile(OnUploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public interface OnUploadFile {
        void onUploadFileSuccess(List<String> info);

        void onUploadFileFailed(String errCode);
    }

    public interface OnNetListener {
        public abstract void onError();
    }

    private OnNetListener onNetListener;

    public void setOnNetListener(OnNetListener listener) {
        this.onNetListener = listener;
    }
}
