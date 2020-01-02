package com.example.mylibrary.utils.stack;

import android.support.v4.app.Fragment;

import java.util.Stack;

/**
 * 作者：Old.Boy 2019/1/18.
 */
class FragmentStackFactory {

    public static Stack<Fragment> fragmentStack = new Stack<>();

    /**
     * 将Fragment添加到栈队列（顺序添加）
     *
     * @param fragment
     */
    public static void pushFragmentToStack(Fragment fragment) {
        if (fragmentStack.contains(fragment)) {
            finishAppointFragment(fragment);
        }
        fragmentStack.push(fragment);
    }

    /**
     * 将Fragment添加到指定位置(默认栈顶)
     *
     * @param position
     * @param fragment
     */
    public static void addFragmentToStack(int position, Fragment fragment) {
        if (fragmentStack.contains(fragment)) {
            finishAppointFragment(fragment);
        }
        fragmentStack.add(position, fragment);
    }

    /**
     * 获取栈顶Fragment
     *
     * @return
     */
    public static Fragment getStackTopFragment() {
        return fragmentStack.peek();
    }

    /**
     * 获取指定Fragment在此栈从1开始的位置
     *
     * @param fragment
     * @return
     */
    public static int getDesignateLocation(Fragment fragment) {
        return fragmentStack.search(fragment);
    }

    /**
     * 移除此栈顶Fragment
     */
    public static void finishCurrentFragment() {
        Fragment fragment = fragmentStack.pop();
        fragmentStack.remove(fragment);
    }

    /**
     * 移除指定Fragment
     *
     * @param fragment
     */
    public static void finishAppointFragment(Fragment fragment) {
        if (fragment != null) {
            if (fragmentStack.contains(fragment)) {
                fragmentStack.remove(fragment);
            }
        }
    }

    /**
     * 移除所有的Fragment
     */
    private static void finishAllFragment() {
        for (Fragment fragment : fragmentStack) {
            if (fragment != null) {
                fragmentStack.remove(fragment);
            }
        }
    }

}
