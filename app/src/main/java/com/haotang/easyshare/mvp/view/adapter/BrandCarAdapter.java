package com.haotang.easyshare.mvp.view.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.BrandCarBean;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;

import java.util.List;

import static com.haotang.easyshare.R.id.ll_item_comment_root;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/6/4 18:36
 */
public class BrandCarAdapter extends BaseQuickAdapter<BrandCarBean.DataBean.DatasetBean, BaseViewHolder> {
    public BrandCarAdapter(int layoutResId, List<BrandCarBean.DataBean.DatasetBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final BrandCarBean.DataBean.DatasetBean item) {
        ImageView iv_item_brandcar = helper.getView(R.id.iv_item_brandcar);
        TextView tv_item_brandcar = helper.getView(R.id.tv_item_brandcar);
        RecyclerView rv_item_brandcar = helper.getView(R.id.rv_item_brandcar);
        LinearLayout ll_item_brandcar_root = helper.getView(R.id.ll_item_brandcar_root);
        View ew_item_brandcar_top = helper.getView(R.id.ew_item_brandcar_top);
        View ew_item_brandcar_bottom = helper.getView(R.id.ew_item_brandcar_bottom);
        if (item != null) {
            GlideUtil.loadNetImg(mContext, item.getIcon(), iv_item_brandcar, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_brandcar, item.getBrand(), "", View.VISIBLE, View.VISIBLE);
            final List<BrandCarBean.DataBean.DatasetBean.CarsBean> cars = item.getCars();
            if (cars != null && cars.size() > 0 && item.isClick()) {
                ew_item_brandcar_bottom.setVisibility(View.VISIBLE);
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
                BrandCarInfoAdapter brandCarInfoAdapter = new BrandCarInfoAdapter(R.layout.item_brandcar_info, cars);
                rv_item_brandcar.setAdapter(brandCarInfoAdapter);
                brandCarInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (cars.size() > position) {
                            BrandCarBean.DataBean.DatasetBean.CarsBean carsBean = cars.get(position);
                            if (carsBean != null) {
                                DevRing.busManager().postEvent(carsBean);
                            }
                        }
                    }
                });
            } else {
                if (helper.getLayoutPosition() == mData.size() - 1) {
                    ew_item_brandcar_bottom.setVisibility(View.VISIBLE);
                } else {
                    ew_item_brandcar_bottom.setVisibility(View.GONE);
                }
                rv_item_brandcar.setVisibility(View.GONE);
            }
        }
    }
}