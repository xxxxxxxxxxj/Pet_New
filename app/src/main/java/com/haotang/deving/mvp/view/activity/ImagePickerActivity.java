package com.haotang.deving.mvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.deving.R;
import com.haotang.deving.mvp.model.entity.res.ImgInfo;
import com.haotang.deving.mvp.model.imageload.GlideImageLoader;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.haotang.deving.mvp.view.adapter.TakePhotoImgAdapter;
import com.haotang.deving.mvp.view.widget.PermissionDialog;
import com.haotang.deving.util.PathUtils;
import com.haotang.deving.util.SystemTypeUtil;
import com.haotang.deving.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.other.permission.PermissionListener;
import com.ljy.devring.util.RingToast;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

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
    private List<ImgInfo> imgList = new ArrayList<ImgInfo>();
    private List<String> urlList = new ArrayList<String>();
    private List<String> pressUrlList = new ArrayList<String>();
    PermissionDialog permissionDialog;

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
        permissionDialog = new PermissionDialog(this);
        permissionDialog.setMessage(R.string.permission_request_WRITE_EXTERNAL_STORAGE);
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
        rvTakePhoto.setLayoutManager(new GridLayoutManager(this, 1));
        takePhotoImgAdapter = new TakePhotoImgAdapter(R.layout.item_takephoto_imginfo, imgList);
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
        permissionDialog.setPositiveButton(R.string.permission_request_dialog_pos, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
                SystemTypeUtil.goToPermissionManager(ImagePickerActivity.this);
            }
        });
        permissionDialog.setNegativeButton(R.string.permission_request_dialog_nav, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
            }
        });
        takePhotoImgAdapter.setOnChildItemListener(new TakePhotoImgAdapter.OnChildItemListener() {
            @Override
            public void OnChildItem(int viewId, int position) {
                switch (viewId) {
                    case R.id.iv_item_takephoto_imginfo:
                        SystemUtil.goPhotoView(ImagePickerActivity.this, 0, urlList);
                        break;
                    case R.id.iv_item_takephoto_imginfo_press:
                        SystemUtil.goPhotoView(ImagePickerActivity.this, 0, pressUrlList);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.zhihu, R.id.dracula})
    public void onViewClicked(final View view) {
        //申请必要权限
        DevRing.permissionManager().requestEach(ImagePickerActivity.this, new PermissionListener() {
            @Override
            public void onGranted(String permissionName) {
                //全部权限都被授予的话，则弹出底部选项
                switch (view.getId()) {
                    case R.id.zhihu:
                        Matisse.from(ImagePickerActivity.this)
                                .choose(MimeType.allOf())
                                .countable(true)
                                .capture(true)
                                .captureStrategy(
                                        new CaptureStrategy(true, "com.haotang.deving.MatisseFileProvider"))
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
            public void onDenied(String permissionName) {
                //如果用户拒绝了其中一个授权请求，则提醒用户
                RingToast.show(R.string.permission_request_WRITE_EXTERNAL_STORAGE);
            }

            @Override
            public void onDeniedWithNeverAsk(String permissionName) {
                //如果用户拒绝了其中一个授权请求，且勾选了不再提醒，则需要引导用户到权限管理页面开启
                permissionDialog.show();
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
                List<Uri> uris = Matisse.obtainResult(data);
                if (uris != null && uris.size() > 0) {
                    urlList.clear();
                    imgList.clear();
                    for (int i = 0; i < uris.size(); i++) {
                        Uri uri = uris.get(i);
                        RingLog.d(TAG, "uri =  " + uri.toString());
                        RingLog.d(TAG, "uri.getAuthority() =  " + uri.getAuthority());
                        String path = PathUtils.getPath(this, uris.get(i));
                        RingLog.d(TAG, "path = " + path);
                        File file = new File(path);
                        imgList.add(new ImgInfo(uri, path, file));
                        urlList.add(path);
                    }
                    compressWithRx(urlList);
                }
            }
            takePhotoImgAdapter.notifyDataSetChanged();
        }
    }

    private void compressWithRx(final List<String> photos) {
        Flowable.just(photos)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        return Luban.with(ImagePickerActivity.this).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) throws Exception {
                        pressUrlList.clear();
                        for (int i = 0; i < list.size(); i++) {
                            pressUrlList.add(list.get(i).getAbsolutePath());
                            imgList.get(i).setPressFile(list.get(i));
                        }
                        takePhotoImgAdapter.notifyDataSetChanged();
                    }
                });
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
