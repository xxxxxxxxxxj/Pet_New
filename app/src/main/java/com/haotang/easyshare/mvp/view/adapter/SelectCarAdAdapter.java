package com.haotang.easyshare.mvp.view.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.util.GlideUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/24 10:48
 */
public class SelectCarAdAdapter extends BaseQuickAdapter<AdvertisementBean.DataBean, BaseViewHolder> {
    public SelectCarAdAdapter(int layoutResId, List<AdvertisementBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvertisementBean.DataBean item) {
        ImageView iv_item_selectcat_ad = helper.getView(R.id.iv_item_selectcat_ad);
        if (item != null) {
            GlideUtil.loadNetImg(mContext, item.getImg(), iv_item_selectcat_ad, R.mipmap.ic_image_load);
        }
    }
}