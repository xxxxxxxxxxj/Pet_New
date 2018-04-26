package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.SerchResult;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/26 17:14
 */
public class MainSerchResultAdapter extends BaseQuickAdapter<SerchResult, BaseViewHolder> {
    public MainSerchResultAdapter(int layoutResId, List<SerchResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SerchResult item) {
        TextView tv_item_mainserch_name = helper.getView(R.id.tv_item_mainserch_name);
        TextView tv_item_mainserch_address = helper.getView(R.id.tv_item_mainserch_address);
        if (item != null) {
            StringUtil.setText(tv_item_mainserch_address, item.getDesc(), "", View.VISIBLE, View.GONE);
            StringUtil.setText(tv_item_mainserch_name, item.getName(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}