package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/10 17:26
 */
public class ImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private List<String> pathList = new ArrayList<String>();
    private int imgWidth;
    private int imgHeight;

    public ImgAdapter(int layoutResId, List<String> data, int imgWidth, int imgHeight) {
        super(layoutResId, data);
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        pathList.clear();
        for (int i = 0; i < mData.size(); i++) {
            pathList.add(mData.get(i));
        }
    }

    public ImgAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
        pathList.clear();
        for (int i = 0; i < mData.size(); i++) {
            pathList.add(mData.get(i));
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        ImageView iv_item_img = helper.getView(R.id.iv_item_img);
        if (imgWidth > 0 && imgHeight > 0) {
            GlideUtil.loadNetImg(mContext, item, iv_item_img, R.mipmap.ic_image_load, imgWidth, imgHeight);
        } else {
            GlideUtil.loadNetImg(mContext, item, iv_item_img, R.mipmap.ic_image_load);
        }
        iv_item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.goPhotoView(mContext, helper.getLayoutPosition(), pathList, false);
            }
        });
    }
}