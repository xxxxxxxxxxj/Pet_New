package com.haotang.easyshare.mvp.view.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.materialratingbar.MaterialRatingBar;
import com.haotang.easyshare.mvp.model.entity.res.FollowBean;
import com.haotang.easyshare.mvp.model.entity.res.SelectAddress;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

import static com.haotang.easyshare.R.id.mrb_item_myfollow_fenshu;
import static com.haotang.easyshare.R.id.rl_item_myfollow_root;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 16:12
 */
public class SelectAddressAdapter extends BaseQuickAdapter<SelectAddress, BaseViewHolder> {
    public SelectAddressAdapter(int layoutResId, List<SelectAddress> data) {
        super(layoutResId, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(final BaseViewHolder helper, SelectAddress item) {
        TextView tv_item_select_address = helper.getView(R.id.tv_item_select_address);
        if (item != null) {
            StringUtil.setText(tv_item_select_address, item.getAddress(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}