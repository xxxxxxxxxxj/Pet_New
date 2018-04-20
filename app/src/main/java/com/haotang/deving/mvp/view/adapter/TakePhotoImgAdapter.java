package com.haotang.deving.mvp.view.adapter;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.deving.R;
import com.haotang.deving.mvp.model.entity.res.ImgInfo;
import com.haotang.deving.util.FileSizeUtil;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/19 17:48
 */
public class TakePhotoImgAdapter extends BaseQuickAdapter<ImgInfo, BaseViewHolder> {
    private List<ImgInfo> newImgList;

    public TakePhotoImgAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImgInfo item) {
        ImageView iv_item_takephoto_imginfo = helper.getView(R.id.iv_item_takephoto_imginfo);
        ImageView iv_item_takephoto_imginfo_press = helper.getView(R.id.iv_item_takephoto_imginfo_press);
        if (item != null) {
            File file = item.getFile();
            String path = item.getPath();
            File pressFile = item.getPressFile();
            Uri uri = item.getUri();

            Glide.with(mContext).load(file).into(iv_item_takephoto_imginfo);
            String size = FileSizeUtil
                    .formatFileSize(file.length(), false);
            if (pressFile != null) {
                Glide.with(mContext).load(pressFile).into(iv_item_takephoto_imginfo_press);
                String pressSize = FileSizeUtil
                        .formatFileSize(pressFile.length(), false);
                helper.setText(R.id.tv_item_takephoto_imginfo, "Uri为：" + uri.toString() + "-----图片路径为：" + path +
                        "-----压缩前大小为：" + size + "-----压缩后大小为：" + pressSize);
            }
        }
    }
}
