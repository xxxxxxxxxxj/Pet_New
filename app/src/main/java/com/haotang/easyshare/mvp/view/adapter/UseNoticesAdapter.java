package com.haotang.easyshare.mvp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.ChargeDetailBean;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.verticalbanner.BaseBannerAdapter;
import com.haotang.easyshare.verticalbanner.VerticalBannerView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/25 14:49
 */
public class UseNoticesAdapter extends BaseBannerAdapter<ChargeDetailBean.UseNotices> {
    private List<ChargeDetailBean.UseNotices> mDatas;

    public UseNoticesAdapter(List<ChargeDetailBean.UseNotices> datas) {
        super(datas);
    }

    @Override
    public View getView(VerticalBannerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_usenotices_adapter, null);
    }

    @Override
    public void setItem(final View view, final ChargeDetailBean.UseNotices data) {
        TextView tv_item_usenotices_time = (TextView) view
                .findViewById(R.id.tv_item_usenotices_time);
        TextView tv_item_usenotices_name = (TextView) view
                .findViewById(R.id.tv_item_usenotices_name);
        if (data != null) {
            StringUtil.setText(tv_item_usenotices_time, data.getTime(), "",
                    View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_usenotices_name, data.getContent(), "",
                    View.VISIBLE, View.VISIBLE);
        }
    }

    public OnItemClickListener onItemClickListener = null;

    public interface OnItemClickListener {
        public void OnItemClick(ChargeDetailBean.UseNotices data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
