package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/8/1 11:57
 */
public class TestAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TestAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        TextView tv_item_test = helper.getView(R.id.tv_item_test);
        StringUtil.setText(tv_item_test, item, "", View.VISIBLE, View.VISIBLE);
    }
}