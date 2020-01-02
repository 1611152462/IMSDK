package com.example.mylibrary.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mylibrary.utils.Logger;
import com.example.mylibrary.utils.OKHttpUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 作者：Old.Boy 2019/1/11.
 */
public class BaseViewModel<T> extends ViewModel {

    private static String TAG = "BaseViewModel";
    private MutableLiveData<T> liveData = new MutableLiveData<>();
    private Gson mGson;

    public BaseViewModel() {
    }

    public MutableLiveData<T> getLiveData(String url, T t, Map<String, String> params) {
        TAG = t.toString();
        requestApiData(url, t, params);
        return liveData;
    }

    public MutableLiveData<T> getInputFileStatu(String url, T t, Map<String, String> params, List<File> files) {
        TAG = t.toString();
        requestApiData(url, t, params, files);
        return liveData;
    }

    private void requestApiData(String url, final T t, Map<String, String> params) {
        OKHttpUtils.post(url, params, new OKHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(Object response) {
                mGson = new Gson();
                T dataBean = mGson.fromJson(response.toString(), (Class<T>) t);
                liveData.setValue(dataBean);
            }

            @Override
            public void onFailure(Exception e) {
                Logger.e(TAG, e.toString());
            }
        });
    }

    private void requestApiData(String url, final T t, Map<String, String> params, List<File> files) {
        OKHttpUtils.postFile(url, params, files, new OKHttpUtils.ResultCallBack() {
            @Override
            public void onSuccess(Object response) {
                mGson = new Gson();
                T dataBean = mGson.fromJson(response.toString(), (Class<T>) t);
                liveData.setValue(dataBean);
            }

            @Override
            public void onFailure(Exception e) {
                Logger.e(TAG, e.toString());
            }
        });
    }
}
