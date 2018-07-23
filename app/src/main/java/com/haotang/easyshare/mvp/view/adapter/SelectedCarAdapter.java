package com.haotang.easyshare.mvp.view.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.view.activity.ButlerActivity;
import com.haotang.easyshare.mvp.view.activity.LoginActivity;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 15:51
 */
public class SelectedCarAdapter extends BaseQuickAdapter<HotSpecialCarBean.DataBean, BaseViewHolder> {
    public SelectedCarAdapter(int layoutResId, List<HotSpecialCarBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotSpecialCarBean.DataBean item) {
        ImageView iv_item_allbands_lxgj = helper.getView(R.id.iv_item_allbands_lxgj);
        ImageView iv_item_allbands_img = helper.getView(R.id.iv_item_allbands_img);
        TextView tv_item_allbands_name = helper.getView(R.id.tv_item_allbands_name);
        TextView tv_item_allbands_xuhang = helper.getView(R.id.tv_item_allbands_xuhang);
        TextView tv_item_allbands_price = helper.getView(R.id.tv_item_allbands_price);
        TextView tv_item_allbands_tag = helper.getView(R.id.tv_item_allbands_tag);
        tv_item_allbands_tag.setVisibility(View.VISIBLE);
        tv_item_allbands_tag.bringToFront();
        if (item != null) {
            GlideUtil.loadNetImg(mContext, item.getIcon(), iv_item_allbands_img, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_allbands_name, item.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_allbands_xuhang, item.getBatteryLife(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_allbands_price, "¥" + item.getPrice(), "", View.VISIBLE, View.VISIBLE);
            iv_item_allbands_lxgj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemUtil.checkLogin(mContext)) {
                        mContext.startActivity(new Intent(mContext, ButlerActivity.class));
                    } else {
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    }
                }
            });
        }
    }
}