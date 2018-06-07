package com.haotang.easyshare.mvp.view.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.BrandCarBean;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/6/7 10:46
 */
public class BrandCarForFirstLetterAdapter extends BaseQuickAdapter<BrandCarBean.DataBean, BaseViewHolder> implements SectionIndexer {
    public BrandCarForFirstLetterAdapter(int layoutResId, List<BrandCarBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final BrandCarBean.DataBean item) {
        RecyclerView rv_item_brandcar = helper.getView(R.id.rv_item_brandcar);
        TextView tv_item_brandcar_zm = helper.getView(R.id.tv_item_brandcar_zm);
        if (item != null) {
            StringUtil.setText(tv_item_brandcar_zm, item.getFirstLetter(), "", View.VISIBLE, View.VISIBLE);
            final List<BrandCarBean.DataBean.DatasetBean> dataset = item.getDataset();
            if (dataset != null && dataset.size() > 0) {
                rv_item_brandcar.setVisibility(View.VISIBLE);
                rv_item_brandcar.setHasFixedSize(true);
                rv_item_brandcar.setNestedScrollingEnabled(false);
                NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new
                        NoScollFullLinearLayoutManager(mContext);
                noScollFullLinearLayoutManager.setScrollEnabled(false);
                rv_item_brandcar.setLayoutManager(noScollFullLinearLayoutManager);
                BrandCarAdapter brandCarAdapter = new BrandCarAdapter(R.layout.item_brand_car, dataset);
                brandCarAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        for (int i = 0; i < mData.size(); i++) {
                            BrandCarBean.DataBean dataBean = mData.get(i);
                            if (dataBean != null) {
                                List<BrandCarBean.DataBean.DatasetBean> dataset1 = dataBean.getDataset();
                                for (int k = 0; k < dataset1.size(); k++) {
                                    BrandCarBean.DataBean.DatasetBean datasetBean = dataset1.get(k);
                                    if (datasetBean != null) {
                                        datasetBean.setClick(false);
                                    }
                                }
                            }
                        }
                        BrandCarBean.DataBean.DatasetBean datasetBean = dataset.get(position);
                        if (datasetBean != null) {
                            datasetBean.setClick(true);
                            notifyDataSetChanged();
                        }
                    }
                });
                rv_item_brandcar.setAdapter(brandCarAdapter);
            } else {
                rv_item_brandcar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getFirstLetter().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getFirstLetter();
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