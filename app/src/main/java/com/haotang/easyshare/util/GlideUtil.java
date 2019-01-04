package com.haotang.easyshare.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.view.widget.GlideCircleTransform;
import com.haotang.easyshare.mvp.view.widget.GlideRoundTransform;

public class GlideUtil {
    public static void loadNetImg(Context mContext, String imgUrl,
                                  ImageView imageView, int placeholderResourceId) {
        if (StringUtil.isNotEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlConstants.getServiceBaseUrl() + imgUrl;
            }
            if (imgUrl.toUpperCase().endsWith(".GIF")) {
                Glide.with(mContext)
                        .load(imgUrl)
                        .asGif()
                        .placeholder(placeholderResourceId)
                        .error(placeholderResourceId)
                        .dontAnimate() //去掉显示动画
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE) //DiskCacheStrategy.NONE
                        .into(imageView);
            } else {
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId)
                        .error(placeholderResourceId)
                        .crossFade()
                        .fitCenter()
                        .into(imageView);
            }
        }
    }

    public static void loadNetImg(Context mContext, String imgUrl,
                                  ImageView imageView, int placeholderResourceId, int imgWidth, int imgHeight) {
        if (StringUtil.isNotEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlConstants.getServiceBaseUrl() + imgUrl;
            }
            if (imgUrl.toUpperCase().endsWith(".GIF")) {
                Glide.with(mContext)
                        .load(imgUrl)
                        .asGif()
                        .override(imgWidth, imgHeight)
                        .placeholder(placeholderResourceId)
                        .error(placeholderResourceId)
                        .dontAnimate() //去掉显示动画
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE) //DiskCacheStrategy.NONE
                        .into(imageView);
            } else {
                Glide.with(mContext)
                        .load(imgUrl)
                        .override(imgWidth, imgHeight)
                        .placeholder(placeholderResourceId)
                        .error(placeholderResourceId)
                        .crossFade()
                        .fitCenter()
                        .into(imageView);
            }
        }
    }

    public static void loadNetImg(Context mContext, String imgUrl,
                                  ImageView imageView, int placeholderResourceId, RequestListener listener) {
        if (StringUtil.isNotEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlConstants.getServiceBaseUrl() + imgUrl;
            }
            if (imgUrl.toUpperCase().endsWith(".GIF")) {
                Glide.with(mContext)
                        .load(imgUrl)
                        .asGif()
                        .placeholder(placeholderResourceId)
                        .error(placeholderResourceId)
                        .dontAnimate() //去掉显示动画
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE) //DiskCacheStrategy.NONE
                        .into(imageView);
            } else {
                Glide.with(mContext)
                        .load(imgUrl)
                        .placeholder(placeholderResourceId)
                        .error(placeholderResourceId)
                        .crossFade()
                        .fitCenter()
                        .into(imageView);
            }
        }
    }

    public static void loadNetImg(Context mContext, String imgUrl,
                                  ImageView imageView, int placeholderResourceId, RequestListener listener,int imgWidth, int imgHeight) {
        if (StringUtil.isNotEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlConstants.getServiceBaseUrl() + imgUrl;
            }
            if (imgUrl.toUpperCase().endsWith(".GIF")) {
                Glide.with(mContext)
                        .load(imgUrl)
                        .asGif()
                        .override(imgWidth, imgHeight)
                        .placeholder(placeholderResourceId)
                        .error(placeholderResourceId)
                        .dontAnimate() //去掉显示动画
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE) //DiskCacheStrategy.NONE
                        .into(imageView);
            } else {
                Glide.with(mContext)
                        .load(imgUrl)
                        .override(imgWidth, imgHeight)
                        .placeholder(placeholderResourceId)
                        .error(placeholderResourceId)
                        .crossFade()
                        .fitCenter()
                        .into(imageView);
            }
        }
    }

    public static void loadNetCircleImg(Context mContext, String imgUrl,
                                        ImageView imageView, int placeholderResourceId) {
        if (StringUtil.isNotEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlConstants.getServiceBaseUrl() + imgUrl;
            }
            Glide.with(mContext).load(imgUrl)
                    .placeholder(placeholderResourceId) // 占位图
                    .error(placeholderResourceId) // 出错的占位图
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }
    }

    public static void loadNetCircleImg(Context mContext, String imgUrl,
                                        ImageView imageView, int placeholderResourceId, RequestListener listener) {
        if (StringUtil.isNotEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlConstants.getServiceBaseUrl() + imgUrl;
            }
            Glide.with(mContext).load(imgUrl).listener(listener)
                    .placeholder(placeholderResourceId) // 占位图
                    .error(placeholderResourceId) // 出错的占位图
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        }
    }

    public static void loadNetRoundImg(Context mContext, String imgUrl,
                                       ImageView imageView, int placeholderResourceId, int round) {
        if (StringUtil.isNotEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlConstants.getServiceBaseUrl() + imgUrl;
            }
            Glide.with(mContext).load(imgUrl)
                    .placeholder(placeholderResourceId) // 占位图
                    .error(placeholderResourceId) // 出错的占位图
                    .bitmapTransform(new GlideRoundTransform(mContext, round))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }

    public static void loadNetRoundImg(Context mContext, String imgUrl,
                                       ImageView imageView, int placeholderResourceId, int round, RequestListener listener) {
        if (StringUtil.isNotEmpty(imgUrl)) {
            if (!imgUrl.startsWith("http://")
                    && !imgUrl.startsWith("https://")) {
                imgUrl = UrlConstants.getServiceBaseUrl() + imgUrl;
            }
            Glide.with(mContext).load(imgUrl).listener(listener)
                    .placeholder(placeholderResourceId) // 占位图
                    .error(placeholderResourceId) // 出错的占位图
                    .bitmapTransform(new GlideRoundTransform(mContext, round))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }
}
