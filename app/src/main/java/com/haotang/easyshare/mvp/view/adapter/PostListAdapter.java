package com.haotang.easyshare.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/3 14:30
 */
public class PostListAdapter extends BaseQuickAdapter<PostBean, BaseViewHolder> {
    private final int flag;
    public OnShareItemListener onShareItemListener = null;

    public interface OnShareItemListener {
        public void OnShareItem(int position);
    }

    public void setOnShareItemListener(OnShareItemListener onShareItemListener) {
        this.onShareItemListener = onShareItemListener;
    }

    public OnDeleteItemListener onDeleteItemListener = null;

    public interface OnDeleteItemListener {
        public void OnDeleteItem(int position);
    }

    public void setOnDeleteItemListener(OnDeleteItemListener onDeleteItemListener) {
        this.onDeleteItemListener = onDeleteItemListener;
    }

    public PostListAdapter(int layoutResId, List<PostBean> data, int flag) {
        super(layoutResId, data);
        this.flag = flag;
    }

    @Override
    protected void convert(final BaseViewHolder helper, PostBean item) {
        LinearLayout ll_item_mypost_root = helper.getView(R.id.ll_item_mypost_root);
        ImageView iv_item_mypost = helper.getView(R.id.iv_item_mypost);
        TextView tv_item_mypost_desc = helper.getView(R.id.tv_item_mypost_desc);
        TextView tv_item_mypost_date = helper.getView(R.id.tv_item_mypost_date);
        ImageView iv_item_mypost_delete = helper.getView(R.id.iv_item_mypost_delete);
        ImageView iv_item_mypost_share = helper.getView(R.id.iv_item_mypost_share);
        if (helper.getLayoutPosition() == 0 && flag == 0) {
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) ll_item_mypost_root.getLayoutParams();
            layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
            ll_item_mypost_root.setLayoutParams(layoutParams);
        }
        if (item != null) {
            StringUtil.setText(tv_item_mypost_desc, item.getDesc(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mypost_date, item.getDate(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetImg(mContext, item.getImg(), iv_item_mypost, R.mipmap.ic_image_load);
        }
        iv_item_mypost_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onShareItemListener != null) {
                    onShareItemListener.OnShareItem(helper.getLayoutPosition());
                }
            }
        });
        iv_item_mypost_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteItemListener != null) {
                    onDeleteItemListener.OnDeleteItem(helper.getLayoutPosition());
                }
            }
        });
    }
}