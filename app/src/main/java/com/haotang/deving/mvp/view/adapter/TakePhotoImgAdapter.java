package com.haotang.deving.mvp.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.deving.R;
import com.haotang.deving.util.FileSizeUtil;
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
        String compressSize = FileSizeUtil
                .formatFileSize(
                        new File(item.getCompressPath())
                                .length(), false);
        String originalSize = FileSizeUtil
                .formatFileSize(
                        new File(item.getOriginalPath())
                                .length(), false);
        helper.setText(R.id.tv_item_takephoto_imginfo, "原图大小为：" + originalSize + "压缩后大小为：" + compressSize);
        //DevRing.imageManager().loadFile(new File(item.getCompressPath()), imageView);
        Glide.with(mContext).load(new File(item.getCompressPath())).into(imageView);
    }
}
