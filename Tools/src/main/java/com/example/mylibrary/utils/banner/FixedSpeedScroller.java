package com.example.mylibrary.utils.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 作者：Old.Boy 2019/1/16.
 */
public class FixedSpeedScroller extends Scroller {


    private int mDuration = 200;

    public FixedSpeedScroller(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);

    }


    @Override


    public void startScroll(int startX, int startY, int dx, int dy, int duration) {

        // Ignore received duration, use fixed one instead

        super.startScroll(startX, startY, dx, dy, mDuration);

    }


    @Override


    public void startScroll(int startX, int startY, int dx, int dy) {

// Ignore received duration, use fixed one instead

        super.startScroll(startX, startY, dx, dy, mDuration);

    }


    public void setmDuration(int time) {

        mDuration = time;

    }


    public int getmDuration() {

        return mDuration;

    }


}
