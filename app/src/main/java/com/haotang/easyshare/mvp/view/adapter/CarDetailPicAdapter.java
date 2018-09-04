package com.haotang.easyshare.mvp.view.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.view.widget.ScaleImageView;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.ScreenUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 14:34
 */
public class CarDetailPicAdapter extends BaseQuickAdapter<AdvertisementBean.DataBean, BaseViewHolder> {
    private final int windowWidth;

    public CarDetailPicAdapter(int layoutResId, List<AdvertisementBean.DataBean> data, Activity activity) {
        super(layoutResId, data);
        windowWidth = ScreenUtil.getWindowWidth(activity);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvertisementBean.DataBean item) {
        final ScaleImageView siv_item_cardetail_pic = helper.getView(R.id.siv_item_cardetail_pic);
        if (item != null) {
            if (StringUtil.isNotEmpty(item.getImg())) {
                if (!item.getImg().startsWith("http://")
                        && !item.getImg().startsWith("https://")) {
                    item.setImg(UrlConstants.getServiceBaseUrl() + item.getImg());
                }
                final String finalItem = item.getImg();
                Glide.with(mContext)
                        .load(finalItem)
                        .asBitmap()//强制Glide返回一个Bitmap对象
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();
                                int imgHeight = windowWidth * height / width;
                                Log.e("TAG", "width = " + width);
                                Log.e("TAG", "height = " + height);
                                Log.e("TAG", "windowWidth = " + windowWidth);
                                Log.e("TAG", "imgHeight = " + imgHeight);
                                siv_item_cardetail_pic.setImageWidth(windowWidth);
                                siv_item_cardetail_pic.setImageHeight(imgHeight);
                                GlideUtil.loadNetImg(mContext, finalItem, siv_item_cardetail_pic, R.mipmap.ic_image_load,
                                        windowWidth, imgHeight);
                            }
                        });
            }
        }
    }
}