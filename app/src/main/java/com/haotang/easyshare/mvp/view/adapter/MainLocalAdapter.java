package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundLinearLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.util.StringUtil;

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
        RoundLinearLayout rll_item_mainlocal_root = helper.getView(R.id.rll_item_mainlocal_root);
        ImageView iv_item_mainlocal_ggorgr = helper.getView(R.id.iv_item_mainlocal_ggorgr);
        TextView tv_item_mainlocal_juli = helper.getView(R.id.tv_item_mainlocal_juli);
        TextView tv_item_mainlocal_name = helper.getView(R.id.tv_item_mainlocal_name);
        LinearLayout ll_item_mainlocal_kuaichong = helper.getView(R.id.ll_item_mainlocal_kuaichong);
        TextView tv_item_mainlocal_kuaichong_num = helper.getView(R.id.tv_item_mainlocal_kuaichong_num);
        LinearLayout ll_item_mainlocal_manchong = helper.getView(R.id.ll_item_mainlocal_manchong);
        TextView tv_item_mainlocal_manchong_num = helper.getView(R.id.tv_item_mainlocal_manchong_num);
        LinearLayout ll_item_mainlocal_kongxian = helper.getView(R.id.ll_item_mainlocal_kongxian);
        TextView tv_item_mainlocal_kongxian_num = helper.getView(R.id.tv_item_mainlocal_kongxian_num);
        TextView tv_item_mainlocal_kfsj = helper.getView(R.id.tv_item_mainlocal_kfsj);
        ImageView iv_item_mainlocal_daohang = helper.getView(R.id.iv_item_mainlocal_daohang);
        TextView tv_item_mainlocal_address = helper.getView(R.id.tv_item_mainlocal_address);
        if (item != null) {
            StringUtil.setText(tv_item_mainlocal_name, item.getName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_juli, item.getJuli(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_address, item.getAddress(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_kfsj, "开放时间：" + item.getKfsj(), "", View.VISIBLE, View.VISIBLE);
            if (item.getGgorgr() == 0) {//公共
                iv_item_mainlocal_ggorgr.setImageResource(R.mipmap.icon_gg);
            } else if (item.getGgorgr() == 1) {//个人
                iv_item_mainlocal_ggorgr.setImageResource(R.mipmap.icon_gr);
            }
            if (item.getKuaichongnum() > 0) {
                StringUtil.setText(tv_item_mainlocal_kuaichong_num, "快充" + item.getKuaichongnum() + "个", "", View.VISIBLE, View.VISIBLE);
                ll_item_mainlocal_kuaichong.setVisibility(View.VISIBLE);
            } else {
                ll_item_mainlocal_kuaichong.setVisibility(View.GONE);
            }
            if (item.getManchongnum() > 0) {
                StringUtil.setText(tv_item_mainlocal_manchong_num, "慢充" + item.getManchongnum() + "个", "", View.VISIBLE, View.VISIBLE);
                ll_item_mainlocal_manchong.setVisibility(View.VISIBLE);
            } else {
                ll_item_mainlocal_manchong.setVisibility(View.GONE);
            }
            if (item.getKongxiannum() > 0) {
                StringUtil.setText(tv_item_mainlocal_kongxian_num, "空闲" + item.getKongxiannum() + "个", "", View.VISIBLE, View.VISIBLE);
                ll_item_mainlocal_kongxian.setVisibility(View.VISIBLE);
            } else {
                ll_item_mainlocal_kongxian.setVisibility(View.GONE);
            }
            iv_item_mainlocal_daohang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            rll_item_mainlocal_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
