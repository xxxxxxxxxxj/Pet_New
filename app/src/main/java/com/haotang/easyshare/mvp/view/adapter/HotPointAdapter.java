package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/1 14:58
 */
public class HotPointAdapter extends BaseQuickAdapter<HotPoint.DataBean, BaseViewHolder> {
    public HotPointAdapter(int layoutResId, List<HotPoint.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotPoint.DataBean item) {
        ImageView iv_item_hotpoint_img = helper.getView(R.id.iv_item_hotpoint_img);
        TextView tv_item_hotpoint_date = helper.getView(R.id.tv_item_hotpoint_date);
        TextView tv_item_hotpoint_num = helper.getView(R.id.tv_item_hotpoint_num);
        ImageView iv_item_hotpoint_userimg = helper.getView(R.id.iv_item_hotpoint_userimg);
        TextView tv_item_hotpoint_username = helper.getView(R.id.tv_item_hotpoint_username);
        TextView tv_item_hotpoint_name = helper.getView(R.id.tv_item_hotpoint_name);
        if (item != null) {
            GlideUtil.loadNetCircleImg(mContext, item.getHeadImg(), iv_item_hotpoint_userimg, R.mipmap.ic_image_load_circle);
            GlideUtil.loadNetImg(mContext, item.getIcon(), iv_item_hotpoint_img, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_hotpoint_date, item.getCreateTime(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotpoint_num, item.getVisitors() + "阅读", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotpoint_username, item.getUserName(), "", View.VISIBLE, View.VISIBLE);
            if (StringUtil.isNotEmpty(item.getTitle())) {
                StringUtil.setText(tv_item_hotpoint_name, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
            } else if (StringUtil.isNotEmpty(item.getContent())) {
                StringUtil.setText(tv_item_hotpoint_name, item.getContent(), "", View.VISIBLE, View.VISIBLE);
            }
        }
    }
}
