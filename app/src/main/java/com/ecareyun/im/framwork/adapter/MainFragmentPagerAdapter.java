package com.ecareyun.im.framwork.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 类名称：MainFragmentPagerAdapter
 * 类描述：主界面中承载Fragemnt的ViewPager的适配器
 * 创建人：xiezheng
 * 创建时间：2016/7/1 13:46
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<Fragment> mFragmentArrayList;

    public MainFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList) {
        super(fm);
        this.mFragmentArrayList = fragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        if (mFragmentArrayList != null || mFragmentArrayList.size() != 0) {
            return mFragmentArrayList.get(position);
        } else {
            return null;
        }

    }

    @Override
    public int getCount() {
        if (mFragmentArrayList != null || mFragmentArrayList.size() != 0) {
            return mFragmentArrayList.size();
        } else {
            return 0;
        }
    }
}
