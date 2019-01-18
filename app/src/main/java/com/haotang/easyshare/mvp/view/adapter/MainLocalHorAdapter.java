package com.haotang.easyshare.mvp.view.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.MainFragChargeBean;
import com.haotang.easyshare.mvp.view.activity.ChargingPileDetailActivity;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/13 17:26
 */
public class MainLocalHorAdapter extends BaseQuickAdapter<MainFragChargeBean, BaseViewHolder> {
    private double lat;
    private double lng;

    public MainLocalHorAdapter(int layoutResId, List<MainFragChargeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MainFragChargeBean item) {
        LinearLayout rll_item_mainlocal_root = helper.getView(R.id.rll_item_mainlocal_root);
        TextView tv_item_mainlocal_ggorgr = helper.getView(R.id.tv_item_mainlocal_ggorgr);
        TextView tv_item_mainlocal_juli = helper.getView(R.id.tv_item_mainlocal_juli);
        TextView tv_item_mainlocal_name = helper.getView(R.id.tv_item_mainlocal_name);
        TextView tv_item_mainlocal_kfsj = helper.getView(R.id.tv_item_mainlocal_kfsj);
        ImageView iv_item_mainlocal_daohang = helper.getView(R.id.iv_item_mainlocal_daohang);
        ImageView iv_item_mainlocal_img = helper.getView(R.id.iv_item_mainlocal_img);
        TextView tv_item_mainlocal_cdf = helper.getView(R.id.tv_item_mainlocal_cdf);
        if (item != null) {
            RingLog.e("item.getHeadImg() = " + item.getHeadImg());
            GlideUtil.loadNetRoundImg(mContext, item.getHeadImg(), iv_item_mainlocal_img, R.mipmap.charge_default_img_round, 5);
            StringUtil.setText(tv_item_mainlocal_name, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_cdf, item.getElectricityPrice(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_juli, "距您" + item.getDistance(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_kfsj, item.getOpenTime(), "", View.VISIBLE, View.INVISIBLE);
            if (item.getIsPrivate() == 0) {//公共
                tv_item_mainlocal_ggorgr.setText("公共");
                tv_item_mainlocal_ggorgr.setBackgroundResource(R.drawable.bg_round_yew5);
            } else if (item.getIsPrivate() == 1) {//个人
                tv_item_mainlocal_ggorgr.setText("个人");
                tv_item_mainlocal_ggorgr.setBackgroundResource(R.drawable.bg_round_red5);
            }
            iv_item_mainlocal_daohang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtil.goNavigation(mContext, item.getLat(), item.getLng(), "", item.getAddress(), lat, lng, item.getUuid());
                }
            });
            rll_item_mainlocal_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ChargingPileDetailActivity.class);
                    intent.putExtra("uuid", item.getUuid());
                    intent.putExtra("serchLat", lat);
                    intent.putExtra("serchLng", lng);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public void setLatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        notifyDataSetChanged();
    }
}
