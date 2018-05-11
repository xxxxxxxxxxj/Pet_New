package com.haotang.easyshare.mvp.view.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.BrandAreaBean;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.util.GlideUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 12:58
 */
public class BrandAreaAdAdapter extends BaseQuickAdapter<BrandAreaBean.AdBean, BaseViewHolder> {
    public BrandAreaAdAdapter(int layoutResId, List<BrandAreaBean.AdBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final BrandAreaBean.AdBean item) {
        ImageView iv_item_brandareaad_img = helper.getView(R.id.iv_item_brandareaad_img);
        if (item != null) {
            GlideUtil.loadNetImg(mContext, item.getImg(), iv_item_brandareaad_img, R.mipmap.ic_image_load);
            iv_item_brandareaad_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getDisplay() == 1) {//原生

                    } else if (item.getDisplay() == 2) {//H5
                        mContext.startActivity(new Intent(mContext, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, item.getDestination()));
                    }
                }
            });
        }
    }
}