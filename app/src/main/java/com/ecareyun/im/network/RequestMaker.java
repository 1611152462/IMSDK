package com.ecareyun.im.network;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ecareyun.im.AppConst;
import com.lzy.okgo.OkGo;
import java.util.UUID;

/**
 * 类名称：RequestMaker
 * 类描述：
 * 创建人：xiezheng
 * 创建时间：2016/10/24 17:57
 */
public class RequestMaker {

    /**
     * 获取域名
     *
     * @return 生产域名或是测试域名
     */
    public static String getBaseAddress() {
        if (AppConst.isTestServer)
            return AppConst.TEST_SERVER_BASE_ADDRESS;
        else
            return AppConst.SERVER_BASE_ADDRESS;
    }



    /**
     * post 请求(不加密)
     *
     * @param paramsJson   请求参数
     * @param serverMethod 接口方法
     * @param tag          取消请求标识
     * @param callback     响应回调
     * @param <T>          响应泛型
     */
    public static <T> void doPost(String paramsJson, String serverMethod, Object tag, JsonCallback<T> callback) {
        OkGo.<T>post(getBaseAddress() + serverMethod).tag(tag)
                .params("ecareJson", paramsJson)
                .execute(callback);
    }

    /**
     * get 请求
     *
     * @param paramsJson   请求参数
     * @param serverMethod 接口方法
     * @param tag          取消请求标识
     * @param callback     响应回调
     * @param <T>          响应泛型
     */
    public static <T> void doGet( String page,String psize,String serverMethod, Object tag, JsonCallback<T> callback) {
        OkGo.<T>get(getBaseAddress() + serverMethod).tag(tag)
                .params("page", page)
                .params("psize", psize)
                .execute(callback);
    }
}