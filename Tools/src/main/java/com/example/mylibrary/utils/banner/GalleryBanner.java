package com.example.mylibrary.utils.banner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.custombanner.R;
import com.example.mylibrary.utils.DensityUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 作者：Old.Boy 2019/1/16.
 */
public class GalleryBanner extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnTouchListener {
    private View mLayout;//布局
    private Activity mContext;//上下文
    private List<Object> imageList;//图片URL集合
    private ViewPager mViewPager;//ViewPager(图片加载器)
    private LinearLayout mlineIndicator;//Indicator(指示器)
    private ZoomPageTransformer zoomPageTransformer;//轮播图滑动动画
    private BannerPagerAdapter mPageAdapter;//适配器
    public static final int defaultScrollBarFaceDuration = 3000;//默认滚动条淡出隐藏过程时长
    public static final float defaultAlpha = 1.0F;//默认滑动动画的透明度
    private FixedSpeedScroller mScroller;//自定义scroll
    private static int rollTime = 5000;//默认展示时间（毫秒）
    private static int defaultDuration = 1000;//默认切换时间（毫秒）
    private static int resId_sel_style = R.mipmap.ic_banner_sel;//指示器选中状态
    private static int resId_nor_style = R.mipmap.ic_banner_nor;//指示器未选中状态
    private boolean isPoint = false;//是否开启指示器
    private ImageView[] imgArray;//指示器集合
    private int currentIndex = 0;//当前显示Page
    private OnBannerClickListener mBannerListener;
    private Handler mHandler;
    private AutoRollRunnable autoRollRunnable;
    private long firstTime = 0, secondTime = 0;

    public GalleryBanner(Context context) {
        super(context);
    }

    public GalleryBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = (Activity) context;
    }

    public GalleryBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GalleryBanner initBanner(List<Object> images, boolean isGallery) {
        return this.initBanner(images, isGallery, defaultAlpha, defaultDuration);
    }

    public GalleryBanner initBanner(List<Object> images, boolean isGallery, float alpha) {
        return this.initBanner(images, isGallery, alpha, defaultDuration);
    }

    public GalleryBanner initBanner(List<Object> images, boolean isGallery, int duration) {
        return this.initBanner(images, isGallery, defaultAlpha, duration);
    }

    /**
     * 初始化Banner布局加载，图片加载，效果设置，滑动动画设置
     *
     * @param images    图片集合
     * @param isGallery 是否开启画廊效果
     * @param alpha     设置滑动动画的透明度变化
     * @return
     */
    @SuppressLint("NewApi")
    public GalleryBanner initBanner(List<Object> images, boolean isGallery, float alpha, int duration) {
        defaultDuration = duration;
        imageList = images;
        //引入布局
        mLayout = View.inflate(mContext, R.layout.banner_view_layout, null);
        mViewPager = mLayout.findViewById(R.id.viewPager);
        mlineIndicator = mLayout.findViewById(R.id.lineIndicator);
        //加载适配器
        mPageAdapter = new BannerPagerAdapter(mContext, imageList);
        mViewPager.setAdapter(mPageAdapter);
        //设置轮播图滑动动画
        if (isGallery) {
            zoomPageTransformer = new ZoomPageTransformer(alpha);
            mViewPager.setPageTransformer(true, zoomPageTransformer);
        }
        //设置切换滑动动画时长
        try {
            // 通过class文件获取mScroller属性
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(mViewPager.getContext(), new AccelerateInterpolator());
            mField.set(mViewPager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置默认开始轮播的位置
        mViewPager.setCurrentItem(images.size() * 500);
        // 切换时间，毫秒值
        mScroller.setmDuration(defaultDuration);
        //设置item两侧预加载数量，这里设置预加载2，预加载正在展示item左边两个右边两个
        mViewPager.setOffscreenPageLimit(2);
        //设置滑动监听
        mViewPager.setOnTouchListener(this);
        //设置条目切换监听器
        mViewPager.addOnPageChangeListener(this);
        return this;
    }

    /**
     * 设置加载占位图
     *
     * @param defaultImg
     * @return
     */
    public GalleryBanner setDefaultImage(@IdRes int defaultImg) {
        mPageAdapter.setDefaultImg(defaultImg);
        return this;
    }

    /**
     * 设置加载失败图
     *
     * @param errorImg
     * @return
     */
    public GalleryBanner setErrorImage(@IdRes int errorImg) {
        mPageAdapter.setErrorImg(errorImg);
        return this;
    }

    /**
     * 设置图片圆角
     *
     * @param mRoundCorners
     * @return
     */
    public GalleryBanner setRoundCorners(int mRoundCorners) {
        mPageAdapter.setRoundCorners(mRoundCorners);
        return this;
    }

    /**
     * 设置Page之间以及page对外的间距(当设置画廊效果后，pageMargin尽量设小，在初始化画廊效果已经对X,Y进行了缩放)
     *
     * @param pageMargin 两个page之间的间距
     * @param rowMargin  page的外边距
     * @return
     */
    public GalleryBanner setPageMargin(float pageMargin, float rowMargin) {
        mViewPager.setPageMargin(DensityUtil.dip2px(mContext, pageMargin));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(DensityUtil.dip2px(mContext, rowMargin), 0, DensityUtil.dip2px(mContext, rowMargin), 0);
        mViewPager.setLayoutParams(layoutParams);
        return this;
    }

    public GalleryBanner setIndicatorPadding(float distance) {
        return setIndicatorPadding(distance, resId_sel_style, resId_nor_style);
    }

    /**
     * 设置指示器间距
     *
     * @param distance  间距
     * @param sel_style 选中样式
     * @param nor_style 未选中样式
     * @return
     */
    public GalleryBanner setIndicatorPadding(float distance, int sel_style, int nor_style) {
        //开启指示器
        isPoint = true;
        //设置自定义样式
        resId_sel_style = sel_style;
        resId_nor_style = nor_style;
        //初始化指示器数组
        imgArray = new ImageView[imageList.size()];
        //添加，设置间距、样式
        for (int i = 0; i < imageList.size(); i++) {
            ImageView img = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(DensityUtil.dip2px(mContext, distance) / 2, 0, DensityUtil.dip2px(mContext, distance) / 2, 0);
            img.setLayoutParams(params);
            if (i == currentIndex) {
                img.setBackgroundResource(resId_sel_style);
            } else {
                img.setBackgroundResource(resId_nor_style);
            }
            imgArray[i] = img;
            mlineIndicator.addView(img);
        }
        return this;
    }

    /**
     * 设置指示器边距
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public GalleryBanner setIndicatorMargin(int left, int top, int right, int bottom) {
        mlineIndicator.setPadding(left, top, right, bottom);
        return this;
    }

    public GalleryBanner start() {
        return start(rollTime,false);
    }

    /**
     * 开始轮播
     *
     * @param time 设置停留时间
     * @return
     */
    public GalleryBanner start(int time,boolean isScale) {
        if(isScale){
            rollTime = time;
            if (mHandler == null) {
                mHandler = new Handler();
            }
            if (autoRollRunnable == null) {
                autoRollRunnable = new AutoRollRunnable();
            }
            autoRollRunnable.start();
        }
        this.addView(mLayout);
        return this;
    }

    /**
     * 停止轮播
     *
     * @return
     */
    public GalleryBanner stop() {
        autoRollRunnable.stop();
        GalleryBanner.this.removeView(mLayout);
        return this;
    }

    /**
     * 设置banner条目点击事件监听
     *
     * @param listener
     * @return
     */
    public GalleryBanner setOnBannerClickListener(OnBannerClickListener listener) {
        mBannerListener = listener;
        return this;
    }

    /**
     * 改变指示器
     *
     * @param selectItemsIndex
     */
    private void setImageBackground(int selectItemsIndex) {
        if (isPoint) {
            for (int i = 0; i < imgArray.length; i++) {
                if (i == selectItemsIndex) {
                    imgArray[i].setImageResource(resId_sel_style);
                } else {
                    imgArray[i].setImageResource(resId_nor_style);
                }
            }
        }
    }

    //--------------------------------ViewPager滑动事件------------------------------------------------
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * 滑动时同步改变底部小圆点
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        currentIndex = position % imageList.size();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mBannerListener != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    firstTime = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    secondTime = System.currentTimeMillis();
                    if (secondTime - firstTime < 100) {
                        mBannerListener.onBannerClick(currentIndex);
                    }
                    break;
            }
        }
        return false;
    }

    /**
     * 点击事件回调接口
     */
    public interface OnBannerClickListener {
        void onBannerClick(int position);
    }

    /**
     * 处理轮播时间，轮播开始、停止
     */
    private class AutoRollRunnable implements Runnable {

        private boolean isRunning = false;

        public void start() {
            if (!isRunning) {
                isRunning = true;
                setImageBackground(0);
                mHandler.removeCallbacks(this);
                mHandler.postDelayed(this, rollTime);
            }
        }

        @Override
        public void run() {
            if (isRunning) {
                int index = mViewPager.getCurrentItem() + 1;
                mViewPager.setCurrentItem(index);
                currentIndex = index % imageList.size();
                setImageBackground(currentIndex);
                mHandler.postDelayed(this, rollTime);
            }
        }

        public void stop() {
            if (isRunning) {
                mHandler.removeCallbacks(this);
                isRunning = false;
            }
        }
    }
}
