package com.example.mylibrary.utils.glide;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;

public class TransformationUtils extends ImageViewTarget<Bitmap> {
    private ImageView target;

    public TransformationUtils(ImageView view) {
        super(view);
        this.target = view;
    }

    @Override
    protected void setResource(@Nullable Bitmap resource) {
        view.setImageBitmap(resource);

        //获取原图的宽高
        int width = resource.getWidth();
        int height = resource.getHeight();

        //获取imageView的宽
        int imageViewWidth = target.getWidth();

        //计算缩放比例
        float sy = (float) (imageViewWidth * 0.1) / (float) (width * 0.1);

        //计算图片等比例放大后的高
        int imageViewHeight = (int) (height * sy);
        ViewGroup.LayoutParams params = target.getLayoutParams();
        params.height = imageViewHeight;
        target.setLayoutParams(params);
    }
}
