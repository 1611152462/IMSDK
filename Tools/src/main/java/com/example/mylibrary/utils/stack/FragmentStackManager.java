package com.example.mylibrary.utils.stack;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.mylibrary.utils.Logger;

/**
 * 作者：Old.Boy 2019/1/18.
 */
public class FragmentStackManager {

    private static String TAG = "FragmentStackManager";
    public static FragmentStackManager manager;
    private FragmentManager fm;
    private FragmentTransaction transaction;

    public FragmentStackManager(FragmentManager fm) {
        this.fm = fm;
    }

    public static class Manager {
        public static synchronized FragmentStackManager getInstance(FragmentManager fm) {
            if (manager == null) {
                manager = new FragmentStackManager(fm);
            }
            return manager;
        }
    }

    private void getTransaction() {
        transaction = fm.beginTransaction();
    }

    public void addFragment(int viewID, Fragment fragment) {
        addFragmentToStack(viewID, fragment);
    }

    public void addFragment(int viewID, Fragment fragment, String tag) {
        addFragmentToStack(viewID, fragment, tag);
    }

    private void addFragmentToStack(int viewID, Fragment fragment) {
        addFragmentToStack(viewID, fragment, null);
    }

    private void addFragmentToStack(int viewID, Fragment fragment, String tag) {
        getTransaction();
        transaction.add(viewID, fragment, tag);
        transaction.commitNowAllowingStateLoss();
        FragmentStackFactory.pushFragmentToStack(fragment);
        showFragment(fragment);
    }

    public void showFragment(Fragment fragment) {
        showFragmentFromStack(fragment);
    }

    private void showFragmentFromStack(Fragment fragment) {
        getTransaction();
        if(!FragmentStackFactory.fragmentStack.contains(fragment)){
            transaction.add(fragment,"tag");
        }
        transaction.show(fragment);
        transaction.commitNowAllowingStateLoss();
        FragmentStackFactory.pushFragmentToStack(fragment);
        int location = FragmentStackFactory.getDesignateLocation(fragment);
        Logger.e(TAG, location + "" + fragment);
    }

    public void hideFragment(Fragment fragment) {
        hideFragmentFromStack(fragment);
    }

    private void hideFragmentFromStack(Fragment fragment) {
        getTransaction();
        transaction.hide(fragment);
        transaction.commitNowAllowingStateLoss();
    }

    public void removeFragment(Fragment fragment) {
        removeFragmentFromStack(fragment);
    }

    private void removeFragmentFromStack(Fragment fragment) {
        getTransaction();
        transaction.remove(fragment);
        transaction.commitNowAllowingStateLoss();
        FragmentStackFactory.finishAppointFragment(fragment);
    }

    public Fragment backFragment(Fragment fragment) {
        return backFragmentToStack(fragment);
    }

    private Fragment backFragmentToStack(Fragment fragment) {
        if (fragment.toString().split("Fragment")[0].equals("Home")) {
            return fragment;
        } else {
            removeFragment(fragment);
            Fragment currentFragment = FragmentStackFactory.fragmentStack.get(FragmentStackFactory.fragmentStack.size() - 1);
            return currentFragment;
        }
    }
}
