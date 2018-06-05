package com.haotang.easyshare.mvp.view.adapter;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.view.activity.SendPostActivity;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
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
 * @date XJ on 2018/6/4 18:36
 */
public class BrandCarAdapter extends BaseQuickAdapter<HotCarBean.DataBean, BaseViewHolder> implements SectionIndexer {
    public BrandCarAdapter(int layoutResId, List<HotCarBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HotCarBean.DataBean item) {
        ImageView iv_item_brandcar = helper.getView(R.id.iv_item_brandcar);
        TextView tv_item_brandcar = helper.getView(R.id.tv_item_brandcar);
        RecyclerView rv_item_brandcar = helper.getView(R.id.rv_item_brandcar);
        TextView tv_item_brandcar_zm = helper.getView(R.id.tv_item_brandcar_zm);
        View vw_item_brandcar2 = helper.getView(R.id.vw_item_brandcar2);
        if (item != null) {
            GlideUtil.loadNetImg(mContext, item.getIcon(), iv_item_brandcar, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_brandcar, item.getBrand(), "", View.VISIBLE, View.VISIBLE);
            /*//根据position获取分类的首字母的Char ascii值
            int section = getSectionForPosition(helper.getLayoutPosition());
            // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
            if (helper.getLayoutPosition() == getPositionForSection(section)) {
                tv_item_brandcar_zm.setVisibility(View.VISIBLE);
                if ("@".equals(item.getSortLetters())) {
                } else {
                    tv_item_brandcar_zm.setText(item.getSortLetters());
                }
            } else {
                tv_item_brandcar_zm.setVisibility(View.GONE);
            }*/
            final List<HotSpecialCarBean.DataBean> carList = item.getCarList();
            if(carList != null && carList.size() > 0){
                rv_item_brandcar.setVisibility(View.VISIBLE);
                rv_item_brandcar.setHasFixedSize(true);
                rv_item_brandcar.setNestedScrollingEnabled(false);
                NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new
                        NoScollFullLinearLayoutManager(mContext);
                noScollFullLinearLayoutManager.setScrollEnabled(false);
                rv_item_brandcar.setLayoutManager(noScollFullLinearLayoutManager);
                if (rv_item_brandcar.getItemDecorationCount() <= 0) {
                    rv_item_brandcar.addItemDecoration(new DividerLinearItemDecoration(mContext,
                            LinearLayoutManager.HORIZONTAL, DensityUtil.dp2px(mContext, 1),
                            ContextCompat.getColor(mContext, R.color.aEEEEEE)));
                }
                BrandCarInfoAdapter brandCarInfoAdapter = new BrandCarInfoAdapter(R.layout.item_brandcar_info, carList);
                rv_item_brandcar.setAdapter(brandCarInfoAdapter);
                brandCarInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if(carList.size() > position){
                            HotSpecialCarBean.DataBean dataBean = carList.get(position);
                            if(dataBean != null){
                                mContext.startActivity(new Intent(mContext, SendPostActivity.class).putExtra("carId",dataBean.getId()));
                            }
                        }
                    }
                });
            }else{
                rv_item_brandcar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}