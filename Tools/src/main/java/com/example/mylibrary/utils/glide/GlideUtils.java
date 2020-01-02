package com.example.mylibrary.utils.glide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.example.mylibrary.utils.DensityUtil;
import com.example.mylibrary.utils.SharedUtils;
import com.example.mylibrary.utils.Logger;
import com.luck.picture.lib.tools.StringUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Old.Boy 2019/1/14.
 */
public class GlideUtils {

    //    private static Context context = AppStackManager.getStackTopActivity();
    private static String TAG = "GlideUtils";
    private static String date;
    private static Handler handler = new Handler();
    private static int width;
    private static int height;

    /**
     * 普通设置(placeholder()：
     *
     * @param path
     * @param mContext
     * @param view
     */
    public static void setImage(Object path, Context mContext, int defaultImg, ImageView view) {
        if (path != null) {
            try {
                String process = "?x-oss-process=image/resize,m_fill,h_400,w_400";
                RequestOptions options = new RequestOptions();

                Glide.with(mContext)
                        .load(path + process)
                        .placeholder(defaultImg)
                        .error(defaultImg)
                        .into(view);
            } catch (Exception error) {
                error.printStackTrace();
            }
        } else {
            Logger.e(TAG, "path is null");
        }
    }


    /**
     * 普通设置(placeholder()：设置加载中的占位图；error()：加载失败的图片；priority()：设置加载优先级)
     *
     * @param path
     * @param mContext
     * @param view
     */
    public static void setImage(Object path, Context mContext, int defaultImage, ImageView view, int width, int height) {
        if (path != null) {
            try {
                String process = "?x-oss-process=image/resize,m_fill,h_4000,w_400";
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(defaultImage)//图片加载出来前，显示的图片
                        .fallback(defaultImage) //url为空的时候,显示的图片
                        .error(defaultImage);//图片加载失败后，显示的图片
                Glide.with(mContext)
                        .load(path + process)
                        .apply(options)
                        .into(view);
            } catch (Exception error) {
                error.printStackTrace();
            }
        } else {
            Logger.e(TAG, "path is null");
        }
    }

    /**
     * 普通预览图设置(placeholder()：设置加载中的占位图；error()：加载失败的图片；priority()：设置加载优先级)
     *
     * @param path
     * @param mContext
     * @param view
     */
    public static void setImagePreview(Object path, Context mContext, int defaultImage, ImageView view) {
        if (path != null) {
            try {
                RequestOptions options = new RequestOptions()
                        .fitCenter()
                        .placeholder(defaultImage)//图片加载出来前，显示的图片
                        .fallback(defaultImage) //url为空的时候,显示的图片
                        .error(defaultImage);//图片加载失败后，显示的图片
                Glide.with(mContext)
                        .load(path)
                        .apply(options)
                        .into(view);
            } catch (Exception error) {
                error.printStackTrace();
            }
        } else {
            Logger.e(TAG, "path is null");
        }
    }


    /**
     * 设置GIF动态图
     *
     * @param path
     * @param view
     */
    public static void setImageAsGif(Object path, Context context, ImageView view) {
        if (path != null) {
            try {
                RequestOptions options = new RequestOptions();
                Glide.with(context)
                        .asGif()
                        .load(path)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(view);
            } catch (Exception error) {
                error.printStackTrace();
            }
        } else {
            Logger.e(TAG, "path is null");
        }
    }

    /**
     * 设置GIF静态图
     *
     * @param path
     * @param context
     * @param defaultImage
     * @param fallbackImage
     * @param errorImage
     * @param view
     */
    public static void setImageAsBitmap(Object path, Context context, int defaultImage,
                                        int fallbackImage, int errorImage, ImageView view) {
        if (path != null) {
            try {
                RequestOptions options = new RequestOptions()
                        .placeholder(defaultImage)//图片加载出来前，显示的图片
                        .fallback(fallbackImage) //url为空的时候,显示的图片
                        .error(errorImage);//图片加载失败后，显示的图片

                Glide.with(context)
                        .asBitmap()
                        .load(path)
                        .into(view);
            } catch (Exception error) {
                error.printStackTrace();
            }
        } else {
            Logger.e(TAG, "path is null");
        }
    }

    /**
     * 加载成圆形图片
     *
     * @param path
     * @param view
     */
    public static void setCircleImage(Object path, Context context, int defaultImage, final ImageView view) {
        if (path != null) {
            try {
                String process = "?x-oss-process=image/resize,m_fill,h_400,w_400";
                RequestOptions options = new RequestOptions()
                        .circleCrop()
                        .placeholder(defaultImage)//图片加载出来前，显示的图片
                        .fallback(defaultImage) //url为空的时候,显示的图片
                        .error(defaultImage);//图片加载失败后，显示的图片
                Glide.with(context)
                        .load(path + process)
                        .apply(options)
                        .into(view);
            } catch (Exception error) {
                error.printStackTrace();
            }
        } else {
            Logger.e(TAG, "path is null");
        }
    }

    /**
     * 加载成自定义圆角图片
     *
     * @param path
     * @param mContext
     * @param view
     * @param radius
     */
    public static void setRoundImage(Object path, Context mContext, int defaultImage,
                                     final ImageView view, int radius, GlideRoundTransform.
                                             CornerType type) {
        if (path != null) {
            try {
                String process = "?x-oss-process=image/resize,m_fill,h_400,w_400";
                RequestOptions options = new RequestOptions()
                        .optionalTransform(new GlideRoundTransform(DensityUtil.dip2px(mContext, radius), type))
                        .placeholder(defaultImage)//图片加载出来前，显示的图片
                        .fallback(defaultImage) //url为空的时候,显示的图片
                        .error(defaultImage);//图片加载失败后，显示的图片
                String[] split = path.toString().split("\\?");
                String shareToken = SharedUtils.getString(mContext, split[0], "");
                if (path.toString().contains("https://")) {
                    Glide.with(mContext)
                            .setDefaultRequestOptions(RequestOptions.centerCropTransform()
                                    //图片签名信息，相同url下如果需要刷新图片，signature不同则会加载网络端的图片资源
                                    .signature(new ObjectKey(shareToken)).placeholder(view.getDrawable()))
                            .load(path + process)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .apply(options)
                            .into(view);
                } else {
                    Glide.with(mContext)
                            .setDefaultRequestOptions(RequestOptions.centerCropTransform()
                                    //图片签名信息，相同url下如果需要刷新图片，signature不同则会加载网络端的图片资源
                                    .signature(new ObjectKey(shareToken)).placeholder(view.getDrawable()))
                            .load(path)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .apply(options)
                            .into(view);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Logger.e(TAG, "path is null");
        }
    }

    /**
     * 加载成自定义圆角图片动态的
     *
     * @param path
     * @param mContext
     * @param view
     * @param radius
     */
    public static void setRoundImageDynamic(Object path, Context mContext, int defaultImage,
                                            final ImageView view, int radius, GlideRoundTransform.
                                                    CornerType type) {
        if (path != null) {
            try {
                String process = "?x-oss-process=image/resize,m_fill,h_400,w_400";
                RequestOptions options = new RequestOptions()
                        .optionalTransform(new GlideRoundTransform(DensityUtil.dip2px(mContext, radius), type))
                        .placeholder(defaultImage)//图片加载出来前，显示的图片
                        .fallback(defaultImage) //url为空的时候,显示的图片
                        .error(defaultImage);//图片加载失败后，显示的图片
                String[] split = path.toString().split("\\?");
                String shareToken = SharedUtils.getString(mContext, split[0], "");
                Glide.with(mContext)
                        .setDefaultRequestOptions(RequestOptions.centerCropTransform()
                                //图片签名信息，相同url下如果需要刷新图片，signature不同则会加载网络端的图片资源
                                .signature(new ObjectKey(shareToken)).placeholder(view.getDrawable()))
                        .load(path)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .apply(options)
                        .into(view);
                Logger.e(TAG + "------------->>", path + process);
            } catch (Exception error) {
                error.printStackTrace();
            }
        } else {
            Logger.e(TAG, "path is null");
        }
    }

    /**
     * 加载成自定义圆角图片
     *
     * @param path
     * @param mContext
     * @param view
     * @param radius
     */
    public static void setRoundImageChat(Object path, Context mContext, int defaultImage,
                                         final ImageView view, int radius, GlideRoundTransform.
                                                 CornerType type) {
        if (path != null) {
            try {
                String process = "?x-oss-process=image/resize,m_fill,h_400,w_400";
                RequestOptions options = new RequestOptions()
                        .optionalTransform(new GlideRoundTransform(DensityUtil.dip2px(mContext, radius), type))
                        .placeholder(defaultImage)//图片加载出来前，显示的图片
                        .fallback(defaultImage) //url为空的时候,显示的图片
                        .error(defaultImage);//图片加载失败后，显示的图片
                Glide.with(mContext)
                        .load(path + process)
                        .apply(options)
                        .into(view);
                Logger.e(TAG + "------------->>", path + process);
            } catch (Exception error) {
                Logger.e(TAG, error.toString());
            }
            Logger.e(TAG, path.toString());
        } else {
            Logger.e(TAG, "path is null");
        }
    }

    public static Map<String, Integer> getImageUrlSize(final Context context, final String url) {
        Map<String, Integer> size = new HashMap<>();
        String[] split = url.split("_");
        int viewWidth = 0;
        int viewHeight = 0;
        if (split.length == 4) {
            String width = split[1];
            String height = split[2];
            int w = Integer.valueOf(width);
            int h = Integer.valueOf(height);

            int maxSize = DensityUtil.dip2px(context, 222);
            int minSize = DensityUtil.dip2px(context, 160);

            if (h > w) {
                viewWidth = maxSize / h * w;
                viewHeight = maxSize;
                if (viewWidth < minSize) {
                    viewWidth = minSize;
                }
            } else if (w > h) {
                viewHeight = maxSize / w * h;
                viewWidth = maxSize;
                if (viewHeight < minSize) {
                    viewHeight = minSize;
                }
            } else {
                viewHeight = maxSize;
                viewWidth = maxSize;
            }
        }
        size.put("width", viewWidth);
        size.put("height", viewHeight);
        return size;
    }

    public static Map<String, Integer> getChatImageUrlSize(final Context context, final String url) {
        Map<String, Integer> size = new HashMap<>();
        String[] split = url.split("_");
        int viewWidth = 0;
        int viewHeight = 0;
        if (split.length == 4) {
            String width = split[1];
            String height = split[2];
            int w = Integer.valueOf(width);
            int h = Integer.valueOf(height);

            int maxSize = DensityUtil.dip2px(context, 160);
            int minSize = DensityUtil.dip2px(context, 100);

            if (h > w) {
                viewWidth = maxSize / h * w;
                viewHeight = maxSize;
                if (viewWidth < minSize) {
                    viewWidth = minSize;
                }
            } else if (w > h) {
                viewHeight = maxSize / w * h;
                viewWidth = maxSize;
                if (viewHeight < minSize) {
                    viewHeight = minSize;
                }
            } else {
                viewHeight = maxSize;
                viewWidth = maxSize;
            }
        }
        size.put("width", viewWidth);
        size.put("height", viewHeight);
        return size;
    }

    /**
     * 加载相同url的图片  背景
     *
     * @param url 图片url
     */
    public static void setBg(final int type, final String url, final Context context, final int defaultImg, final ImageView img) {
        String[] split = url.split("\\?");
        final String shareToken = SharedUtils.getString(context, split[0], "");

        if (split.length > 1) {
            final String[] remoteToken = split[1].split("=");
            if (type == 1) {
                glideLoadBg(context, "" + remoteToken, url, img);
            } else {
                glideLoadRound(context, "" + remoteToken, url, img);
            }
        } else {
            if (type == 1) {
                glideLoadBg(context, "" + shareToken, url, img);
            } else {
                glideLoadRound(context, "" + shareToken, url, img);
            }
        }
    }

    /**
     * 使用Glide加载图片资源改变的     *
     *
     * @param context   上下文
     * @param key       Last-Modified或Etag
     * @param url       图片url
     * @param imageView 图片控件
     */
    private static void glideLoadBg(final Context context, final String key, final String url, final ImageView imageView) {
        try {
            new Handler(context.getMainLooper()) {
                @SuppressLint("NewApi")
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (!((Activity) context).isDestroyed()) {
                        Glide.with(context)
                                .setDefaultRequestOptions(RequestOptions.centerCropTransform()
                                        //图片签名信息，相同url下如果需要刷新图片，signature不同则会加载网络端的图片资源
                                        .signature(new ObjectKey(key)).placeholder(imageView.getDrawable()))
                                .load(url)
                                .into(imageView);
                    }
                }
            }.sendEmptyMessage(0);
        } catch (Exception error) {
            Logger.e(TAG, error.toString());
        }
    }

    /**
     * 使用Glide加载图片资源改变的     *
     *
     * @param context   上下文
     * @param key       Last-Modified或Etag
     * @param url       图片url
     * @param imageView 图片控件
     */
    private static void glideLoadRound(final Context context, final String key, final String url, final ImageView imageView) {
        new Handler(context.getMainLooper()) {
            @SuppressLint("NewApi")
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                try {
                    if (!((Activity) context).isDestroyed()) {
                        Glide.with(context)
                                .setDefaultRequestOptions(RequestOptions.bitmapTransform(new GlideRoundTransform(DensityUtil.dip2px(context, 6), GlideRoundTransform.CornerType.ALL))
                                        //图片签名信息，相同url下如果需要刷新图片，signature不同则会加载网络端的图片资源
                                        .signature(new ObjectKey(key)).placeholder(imageView.getDrawable()))
                                .load(url)
                                .into(imageView);
                    }
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        }.sendEmptyMessage(0);
    }

    public static void setChatAvatar(String url, Context context, int defaultImg, ImageView img, int width, int height) {
        String[] split = url.split("\\?");
        String shareToken = SharedUtils.getString(context, split[0], "");

        glideLoadImg(context, "" + shareToken, defaultImg, url, img);
    }

    /**
     * 加载头像
     *
     * @param url 图片url
     */
    public static void setAvatar(Object url, Context context, int defaultImg, final ImageView img) {
        try {
            ViewTreeObserver viewTreeObserver = img.getViewTreeObserver();
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    width = img.getWidth() * 2;
                    height = img.getHeight() * 2;
                }
            });

            String process = "?x-oss-process=image/resize,m_fill,h_" + height + ",w_" + width;
            String[] split = ((String) url).split("\\?");
            String shareToken = SharedUtils.getString(context, split[0], "");
            if (split.length > 1) {
                String[] remoteToken = split[1].split("=");
                if (!shareToken.equals(remoteToken[1])) {
                    SharedUtils.putString(context, split[0], remoteToken[1]);
                    if (width == 0 || height == 0) {
                        glideLoadImg(context, "" + remoteToken[1], defaultImg, String.valueOf(url), img);
                    } else {
                        glideLoadImg(context, "" + remoteToken[1], defaultImg, url + process, img);
                    }
                } else {
                    if (width == 0 || height == 0) {
                        glideLoadImg(context, "" + shareToken, defaultImg, String.valueOf(url), img);
                    } else {
                        glideLoadImg(context, "" + shareToken, defaultImg, url + process, img);
                    }
                }
            } else {
                if (width == 0 || height == 0) {
                    glideLoadImg(context, "" + shareToken, defaultImg, String.valueOf(url), img);
                } else {
                    glideLoadImg(context, "" + shareToken, defaultImg, url + process, img);
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }


    /**
     * 使用Glide加载图片资源改变的     *
     *
     * @param context    上下文
     * @param key        Last-Modified或Etag
     * @param defaultImg
     * @param url        图片url
     * @param imageView  图片控件
     */
    @SuppressLint("NewApi")
    private static void glideLoadImg(Context context, String key, int defaultImg, String url, ImageView imageView) {
        try {
            if (!((Activity) context).isDestroyed()) {

                Logger.e(TAG, "GlideUrl-Key=========>>>>>" + key);

                Glide.with(context)
                        .load(url)
                        .circleCrop()
                        .signature(new ObjectKey(key))
                        .placeholder(imageView.getDrawable())
                        .error(defaultImg)
                        .into(imageView);
            }
        } catch (Exception error) {
            Logger.e(TAG, error.toString());
        }
    }
}
