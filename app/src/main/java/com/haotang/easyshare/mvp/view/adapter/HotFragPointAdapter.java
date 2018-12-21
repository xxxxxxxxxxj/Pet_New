package com.haotang.easyshare.mvp.view.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/10 17:10
 */
public class HotFragPointAdapter extends BaseQuickAdapter<HotPoint.DataBean, BaseViewHolder> {
    public HotFragPointAdapter(int layoutResId, List<HotPoint.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotPoint.DataBean item) {
        ImageView iv_item_hotfrag_point_img = helper.getView(R.id.iv_item_hotfrag_point_img);
        TextView tv_item_hotfrag_point_title = helper.getView(R.id.tv_item_hotfrag_point_title);
        RecyclerView rv_item_hotfrag_point_img = helper.getView(R.id.rv_item_hotfrag_point_img);
        TextView tv_item_hotfrag_point_time = helper.getView(R.id.tv_item_hotfrag_point_time);
        TextView tv_item_hotfrag_point_num = helper.getView(R.id.tv_item_hotfrag_point_num);
        ImageView iv_item_hotfrag_point_userimg = helper.getView(R.id.iv_item_hotfrag_point_userimg);
        TextView tv_item_hotfrag_point_name = helper.getView(R.id.tv_item_hotfrag_point_name);
        if (item != null) {
            GlideUtil.loadNetCircleImg(mContext, item.getHeadImg(), iv_item_hotfrag_point_userimg, R.mipmap.ic_image_load_circle);
            if (item.getMedia() != null && item.getMedia().size() > 0) {
                if (item.getMedia().size() > 1) {
                    iv_item_hotfrag_point_img.setVisibility(View.GONE);
                    rv_item_hotfrag_point_img.setVisibility(View.VISIBLE);
                    rv_item_hotfrag_point_img.setHasFixedSize(true);
                    rv_item_hotfrag_point_img.setNestedScrollingEnabled(false);
                    NoScollFullGridLayoutManager noScollFullGridLayoutManager = new NoScollFullGridLayoutManager(rv_item_hotfrag_point_img, mContext, 3, GridLayoutManager.VERTICAL, false);
                    noScollFullGridLayoutManager.setScrollEnabled(false);
                    rv_item_hotfrag_point_img.setLayoutManager(noScollFullGridLayoutManager);
                    ImgAdapter imgAdapter = new ImgAdapter(R.layout.item_img, item.getMedia(), 197, 137);
                    rv_item_hotfrag_point_img.setAdapter(imgAdapter);
                } else {
                    iv_item_hotfrag_point_img.setVisibility(View.VISIBLE);
                    rv_item_hotfrag_point_img.setVisibility(View.GONE);
                    GlideUtil.loadNetImg(mContext, item.getMedia().get(0), iv_item_hotfrag_point_img, R.mipmap.ic_image_load);
                }
            } else {
                iv_item_hotfrag_point_img.setVisibility(View.VISIBLE);
                rv_item_hotfrag_point_img.setVisibility(View.GONE);
                GlideUtil.loadNetImg(mContext, "", iv_item_hotfrag_point_img, R.mipmap.ic_image_load);
            }
            if (StringUtil.isNotEmpty(item.getTitle())) {
                StringUtil.setText(tv_item_hotfrag_point_title, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
            } else if (StringUtil.isNotEmpty(item.getContent())) {
                StringUtil.setText(tv_item_hotfrag_point_title, item.getContent(), "", View.VISIBLE, View.VISIBLE);
            }
            StringUtil.setText(tv_item_hotfrag_point_time, item.getCreateTime(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotfrag_point_num, item.getVisitors() + "评论", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotfrag_point_name, item.getUserName(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}
