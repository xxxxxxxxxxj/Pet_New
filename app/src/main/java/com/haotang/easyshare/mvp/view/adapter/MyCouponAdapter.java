package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.MyCoupon;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/29 11:52
 */
public class MyCouponAdapter extends BaseQuickAdapter<MyCoupon.DataBean.DatasetBean, BaseViewHolder> {
    public MyCouponAdapter(int layoutResId, List<MyCoupon.DataBean.DatasetBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCoupon.DataBean.DatasetBean item) {
        TextView ll_item_mycoupon_root = helper.getView(R.id.ll_item_mycoupon_root);
        TextView tv_item_mycoupon_time = helper.getView(R.id.tv_item_mycoupon_time);
        TextView tv_item_mycoupon_amount = helper.getView(R.id.tv_item_mycoupon_amount);
        TextView tv_item_mycoupon_txt = helper.getView(R.id.tv_item_mycoupon_txt);
        ImageView iv_item_mycoupon_type = helper.getView(R.id.iv_item_mycoupon_type);
        TextView tv_item_mycoupon_name = helper.getView(R.id.tv_item_mycoupon_name);
        if (item != null) {
            StringUtil.setText(tv_item_mycoupon_name, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mycoupon_time, "使用时间：" + item.getStartTime() + "---" + item.getEndTime(), "", View.VISIBLE, View.VISIBLE);
            if (item.getReduceType() == 1) {
                tv_item_mycoupon_txt.setText("元");
                tv_item_mycoupon_amount.setText(item.getAmount() + "");
                iv_item_mycoupon_type.setImageResource(R.mipmap.about_logo);
                ll_item_mycoupon_root.setBackgroundResource(R.mipmap.about_logo);
            } else if (item.getReduceType() == 2) {
                tv_item_mycoupon_txt.setText("折");
                iv_item_mycoupon_type.setImageResource(R.mipmap.icon_about);
                tv_item_mycoupon_amount.setText(item.getAmount() + "");
                ll_item_mycoupon_root.setBackgroundResource(R.mipmap.icon_about);
            }
        }
    }
}
