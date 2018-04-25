package com.haotang.easyshare.mvp.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.mvp.model.entity.res.ImgInfo;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 14:51
 */
public class MainLocalAdapter extends BaseQuickAdapter<MainFragmentData, BaseViewHolder> {
    public MainLocalAdapter(int layoutResId, List<MainFragmentData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainFragmentData item) {

    }
}
