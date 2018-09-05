package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/5 11:47
 */
public class ScreenCarAdapter extends BaseQuickAdapter<HotSpecialCarBean.DataBean, BaseViewHolder> {
    public ScreenCarAdapter(int layoutResId, List<HotSpecialCarBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotSpecialCarBean.DataBean item) {
        ImageView iv_item_allbands_img = helper.getView(R.id.iv_item_allbands_img);
        TextView tv_item_allbands_name = helper.getView(R.id.tv_item_allbands_name);
        TextView tv_item_allbands_xuhang = helper.getView(R.id.tv_item_allbands_xuhang);
        TextView tv_item_allbands_price = helper.getView(R.id.tv_item_allbands_price);
        TextView tv_item_allbands_tag = helper.getView(R.id.tv_item_allbands_tag);
        if (item != null) {
            if (StringUtil.isNotEmpty(item.getLabel())) {
                if (item.getLabel().equals("0")) {
                    tv_item_allbands_tag.setVisibility(View.GONE);
                } else {
                    tv_item_allbands_tag.setVisibility(View.VISIBLE);
                    tv_item_allbands_tag.bringToFront();
                    if (item.getLabel().equals("1")) {
                        tv_item_allbands_tag.setText("最新");
                    } else if (item.getLabel().equals("2")) {
                        tv_item_allbands_tag.setText("最热");
                    } else if (item.getLabel().equals("3")) {
                        tv_item_allbands_tag.setText("推荐");
                    }
                }
            } else {
                tv_item_allbands_tag.setVisibility(View.GONE);
            }
            GlideUtil.loadNetImg(mContext, item.getIcon(), iv_item_allbands_img, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_allbands_name, item.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_allbands_xuhang, item.getBatteryLife(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_allbands_price, "¥" + item.getPrice(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}
