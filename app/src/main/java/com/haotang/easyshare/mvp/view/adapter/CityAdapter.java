package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CityBean;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 13:36
 */
public class CityAdapter extends BaseQuickAdapter<CityBean, BaseViewHolder> {
    public CityAdapter(int layoutResId, List<CityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean item) {
        RoundTextView rtv_item_city = helper.getView(R.id.rtv_item_city);
        if (item != null) {
            StringUtil.setText(rtv_item_city, item.getCityName(), "", View.VISIBLE, View.VISIBLE);
            if (item.isSelete()) {
                rtv_item_city.setTextColor(mContext.getResources().getColor(R.color.a0271F0));
                rtv_item_city.getDelegate().setStrokeColor(mContext.getResources().getColor(R.color.a0271F0));
            } else {
                rtv_item_city.setTextColor(mContext.getResources().getColor(R.color.a666666));
                rtv_item_city.getDelegate().setStrokeColor(mContext.getResources().getColor(R.color.white));
            }
        }
    }
}