package com.ecareyun.im.network;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * 类名称：JsonCallback
 * 类描述：
 * 创建人：xiezheng
 * 创建时间：2016/10/24 16:59
 */
public abstract class JsonCallback<T> extends AbsCallback<T> implements Callback<T> {

    @Override
    public T convertResponse(Response response) throws Throwable {
        //以下代码是通过泛型解析实际参数,泛型必须传
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        String string = response.body().string();

        response.close();
        return JSON.parseObject(string, params[0]);
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
    }
}
