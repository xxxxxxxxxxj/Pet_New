package com.haotang.easyshare.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.MyCoupon;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 17:00
 */
public class SelectCouponAdapter  extends BaseQuickAdapter<MyCoupon.DataBean.DatasetBean, BaseViewHolder> {

    public SelectCouponAdapter(int layoutResId, List<MyCoupon.DataBean.DatasetBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCoupon.DataBean.DatasetBean item) {
        LinearLayout ll_item_mycoupon_root = helper.getView(R.id.ll_item_mycoupon_root);
        TextView tv_item_mycoupon_time = helper.getView(R.id.tv_item_mycoupon_time);
        TextView tv_item_mycoupon_amount = helper.getView(R.id.tv_item_mycoupon_amount);
        TextView tv_item_mycoupon_txt = helper.getView(R.id.tv_item_mycoupon_txt);
        ImageView iv_item_mycoupon_type = helper.getView(R.id.iv_item_mycoupon_type);
        ImageView iv_item_mycoupon_select = helper.getView(R.id.iv_item_mycoupon_select);
        TextView tv_item_mycoupon_name = helper.getView(R.id.tv_item_mycoupon_name);
        if (helper.getLayoutPosition() == 0) {
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) ll_item_mycoupon_root.getLayoutParams();
            layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
            ll_item_mycoupon_root.setLayoutParams(layoutParams);
        }
        if (item != null) {
            if(item.isSelect()){
                iv_item_mycoupon_select.setImageResource(R.mipmap.icon_coupon_select);
            }else{
                iv_item_mycoupon_select.setImageResource(R.mipmap.icon_coupon_unselect);
            }
            StringUtil.setText(tv_item_mycoupon_name, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mycoupon_time, "使用时间：" + item.getStartTime() + "---" + item.getEndTime(), "", View.VISIBLE, View.VISIBLE);
            if (item.getReduceType() == 1) {
                tv_item_mycoupon_txt.setText("元");
                tv_item_mycoupon_amount.setText(item.getAmount() + "");
                iv_item_mycoupon_type.setImageResource(R.mipmap.icon_coupon_money);
                if(item.getIsAvali() == 1){
                    ll_item_mycoupon_root.setBackgroundResource(R.mipmap.bg_coupon_money);
                }else if(item.getIsAvali() == 0){
                    ll_item_mycoupon_root.setBackgroundResource(R.mipmap.bg_coupon_guoqi);
                }
            } else if (item.getReduceType() == 2) {
                tv_item_mycoupon_txt.setText("折");
                iv_item_mycoupon_type.setImageResource(R.mipmap.icon_coupon_zhekou);
                tv_item_mycoupon_amount.setText(item.getAmount() + "");
                if(item.getIsAvali() == 1){
                    ll_item_mycoupon_root.setBackgroundResource(R.mipmap.bg_coupon_zhekou);
                }else if(item.getIsAvali() == 0){
                    ll_item_mycoupon_root.setBackgroundResource(R.mipmap.bg_coupon_guoqi);
                }
            }
        }
    }
}
