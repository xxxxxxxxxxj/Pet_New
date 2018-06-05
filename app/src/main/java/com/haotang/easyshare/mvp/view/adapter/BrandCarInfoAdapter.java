package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/6/5 20:17
 */
class BrandCarInfoAdapter extends BaseQuickAdapter<HotSpecialCarBean.DataBean, BaseViewHolder> {
    public BrandCarInfoAdapter(int layoutResId, List<HotSpecialCarBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotSpecialCarBean.DataBean item) {
        TextView tv_item_brandcarinfo_name = helper.getView(R.id.tv_item_brandcarinfo_name);
        if (item != null) {
            StringUtil.setText(tv_item_brandcarinfo_name, item.getCar(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}
