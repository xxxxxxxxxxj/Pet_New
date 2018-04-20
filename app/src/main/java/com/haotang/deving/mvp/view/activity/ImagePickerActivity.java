package com.haotang.deving.mvp.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.deving.R;
import com.haotang.deving.mvp.model.imageload.GlideImageLoader;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.haotang.deving.mvp.view.adapter.TakePhotoImgAdapter;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 相册操作
 */
public class ImagePickerActivity extends BaseActivity implements OnBannerListener {
    protected final static String TAG = ImagePickerActivity.class.getSimpleName();
    @BindColor(R.color.colorPrimary)
    protected int mColor;
    @BindView(R.id.rv_take_photo)
    RecyclerView rvTakePhoto;
    Banner banner;
    private TakePhotoImgAdapter takePhotoImgAdapter;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private List<Uri> mDataList = new ArrayList<Uri>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_take_photo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        setAdapter();
        setBanner();
    }

    private void setBanner() {
        //本地图片数据（资源文件）
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    private void setAdapter() {
        rvTakePhoto.setHasFixedSize(true);
        rvTakePhoto.setLayoutManager(new GridLayoutManager(this, 3));
        takePhotoImgAdapter = new TakePhotoImgAdapter(R.layout.item_takephoto_imginfo, mDataList);
        takePhotoImgAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        View top = getLayoutInflater().inflate(R.layout.imagepicker_top_view, (ViewGroup) rvTakePhoto.getParent(), false);
        banner = top.findViewById(R.id.banner);
        takePhotoImgAdapter.addHeaderView(top);
        rvTakePhoto.setAdapter(takePhotoImgAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.zhihu, R.id.dracula})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhihu:
                Matisse.from(ImagePickerActivity.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .capture(true)
                        .captureStrategy(
                                new CaptureStrategy(true, "com.haotang.deving.mvp.model.fileprovider.MatisseFileProvider"))
                        .maxSelectable(9)
                        //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(
                                getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        //.setOnSelectedListener()
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
            case R.id.dracula:
                Matisse.from(ImagePickerActivity.this)
                        .choose(MimeType.allOf())
                        .theme(R.style.Matisse_Dracula)
                        .countable(false)
                        .maxSelectable(9)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mDataList.clear();
            mDataList.addAll(Matisse.obtainResult(data));
            takePhotoImgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnBannerClick(int position) {
        RingLog.e(TAG, "position:" + position);
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
