package com.haotang.deving.mvp.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.deving.R;
import com.haotang.deving.util.FileSizeUtil;
import com.haotang.deving.util.StringUtil;
import com.jph.takephoto.model.TImage;

import java.io.File;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/19 17:48
 */
public class TakePhotoImgAdapter extends BaseQuickAdapter<TImage, BaseViewHolder> {
    public TakePhotoImgAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TImage item) {
        ImageView imageView = helper.getView(R.id.iv_item_takephoto_imginfo);
        if (item != null) {
            String compressPath = item.getCompressPath();
            String originalPath = item.getOriginalPath();
            String compressSize = "";
            String originalSize = "";
            if (StringUtil.isNotEmpty(compressPath)) {
                compressSize = FileSizeUtil
                        .formatFileSize(
                                new File(compressPath)
                                        .length(), false);
                Glide.with(mContext).load(new File(compressPath)).into(imageView);
            }
            if (StringUtil.isNotEmpty(originalPath)) {
                originalSize = FileSizeUtil
                        .formatFileSize(
                                new File(originalPath)
                                        .length(), false);
            }
            helper.setText(R.id.tv_item_takephoto_imginfo, "原图大小为：" + originalSize + "压缩后大小为：" + compressSize);
        }
    }
}
