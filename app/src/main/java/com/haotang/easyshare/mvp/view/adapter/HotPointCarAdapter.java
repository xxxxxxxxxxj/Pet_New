package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/1 15:54
 */
public class HotPointCarAdapter extends BaseQuickAdapter<HotCarBean.DataBean, BaseViewHolder> {
    public HotPointCarAdapter(int layoutResId, List<HotCarBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotCarBean.DataBean item) {
        ImageView iv_item_hotfrag_topcar = helper.getView(R.id.iv_item_hotfrag_topcar);
        TextView tv_item_hotfrag_topcar = helper.getView(R.id.tv_item_hotfrag_topcar);
        if (item != null) {
            GlideUtil.loadNetImg(mContext, item.getIcon(), iv_item_hotfrag_topcar, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_hotfrag_topcar, item.getBrand(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}
