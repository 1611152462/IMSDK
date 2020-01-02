package com.ecareyun.im.contract;

import com.ecareyun.im.framwork.mvp.MvpPresenter;
import com.ecareyun.im.framwork.mvp.MvpView;

public interface MainContract {
    interface MainView extends MvpView{
        void getRes();
    }
    interface MainPresenter extends MvpPresenter{

        void req();
    }
}
