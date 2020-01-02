package com.example.mylibrary.utils.banner;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.custombanner.R;
import com.example.mylibrary.utils.glide.GlideRoundTransform;
import com.example.mylibrary.utils.glide.GlideUtils;

import java.util.List;

/**
 * 作者：Old.Boy 2019/1/16.
 */

public class BannerPagerAdapter extends PagerAdapter {
    private List<Object> imageList;
    private Context mContext;
    private int defaultImg = R.mipmap.ic_banner_load;//默认加载中图片
    private int errorImg = R.mipmap.ic_launcher;//默认加载失败图片
    private int fallbackImage = R.mipmap.ic_launcher;//默认加载失败图片
    private int mRoundCorners = -1;

    public BannerPagerAdapter(Activity mContext, List<Object> imageList) {
        this.imageList = imageList;
        this.mContext = mContext;
    }

    public void setDefaultImg(int defaultImg) {
        this.defaultImg = defaultImg;
    }

    public void setErrorImg(int errorImg) {
        this.errorImg = errorImg;
    }

    public void setRoundCorners(int mRoundCorners) {
        this.mRoundCorners = mRoundCorners;
    }

    @Override
    public int getCount() {
        return imageList.size() * 10000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View mLayout = null;
        ViewHolder holder;
        if (mLayout == null) {
            mLayout = View.inflate(mContext, R.layout.banner_img_layout, null);
            holder = new ViewHolder();
            mLayout.setTag(holder);
        } else {
            holder = (ViewHolder) mLayout.getTag();
        }
        holder.image = mLayout.findViewById(R.id.img);
        int index = position % imageList.size();
        LoadImage(imageList.get(index), holder.image);
        container.addView(mLayout);
        return mLayout;
    }

    /**
     * 加载图片
     */
    public void LoadImage(Object url, ImageView imageview) {
        if (mContext != null) {
            if (mRoundCorners == -1) {
                GlideUtils.setImage(url, mContext, defaultImg, imageview, imageview.getWidth(), imageview.getHeight());
            } else {
                GlideUtils.setRoundImage(url, mContext, defaultImg, imageview, mRoundCorners, GlideRoundTransform.CornerType.ALL);
            }
        }
    }

    private class ViewHolder {
        public ImageView image;
    }
}