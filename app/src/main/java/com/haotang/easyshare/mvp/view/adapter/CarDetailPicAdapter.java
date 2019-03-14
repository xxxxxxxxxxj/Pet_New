package com.haotang.easyshare.mvp.view.adapter;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.view.widget.ScaleImageView;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.ScreenUtil;

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
            GlideUtil.loadNetImg(mContext, item.getImg(), siv_item_cardetail_pic, R.mipmap.ic_image_load);
        }
    }
}