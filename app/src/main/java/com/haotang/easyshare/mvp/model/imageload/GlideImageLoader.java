package com.haotang.easyshare.mvp.model.imageload;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.widget.GlideRoundTransform;
import com.youth.banner.loader.ImageLoader;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/19 18:12
 */
public class GlideImageLoader extends ImageLoader {
    private int radius;

    public GlideImageLoader() {
    }

    public GlideImageLoader(int radius) {
        this.radius = radius;
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if(radius > 0){
            Glide.with(context.getApplicationContext()).load(path)
                    .placeholder(R.mipmap.ic_image_load) // 占位图
                    .error(R.mipmap.ic_image_load) // 出错的占位图
                    .bitmapTransform(new GlideRoundTransform(context.getApplicationContext(), radius))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }else{
            Glide.with(context.getApplicationContext())
                    .load(path).error(R.mipmap.ic_image_load)
                    .placeholder(R.mipmap.ic_image_load)
                    .into(imageView);
        }
    }
}
