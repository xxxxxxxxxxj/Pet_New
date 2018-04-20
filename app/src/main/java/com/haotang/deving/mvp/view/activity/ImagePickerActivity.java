package com.haotang.deving.mvp.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.deving.R;
import com.haotang.deving.mvp.model.imageload.GlideImageLoader;
import com.haotang.deving.mvp.view.adapter.TakePhotoImgAdapter;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.ColorBar;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 相册操作
 */
public class ImagePickerActivity extends TakePhotoActivity implements OnBannerListener {
    protected final static String TAG = ImagePickerActivity.class.getSimpleName();
    @BindColor(R.color.colorPrimary)
    protected int mColor;
    @BindView(R.id.rv_take_photo)
    RecyclerView rvTakePhoto;
    Banner banner;
    private TakePhoto takePhoto;
    private Uri imageUri;
    private boolean showProgressBar = true;//是否显示压缩进度
    private boolean enableRawFile = true;//拍照压缩后是否保存原图
    private boolean withOwnGallery = true;//是否使用TakePhoto自带相册
    private boolean correctImage = true;//是否纠正拍照的照片旋转角度
    private int limit = 1;//最多选择张数
    private boolean iscrop = false;//是否纠正拍照的照片旋转角度
    private int selectFrom = 2;//从哪选择：1从相册，2从文件
    private boolean withWonCrop = true;//裁切工具：true:TakePhoto自带,false:第三方
    private boolean rgCropSize = true;//尺寸/比例：true:宽/高,false:宽x高
    private int width = 800;//压缩，裁剪的宽
    private int height = 800;//压缩，裁剪的高
    private int maxSize = 102400;//压缩完的大小不超多maxSize
    private TakePhotoImgAdapter takePhotoImgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentLayout() != 0) {
            setContentView(getContentLayout());
            ButterKnife.bind(this);
        }
        initBarColor();//初始化状态栏/导航栏颜色，需在设置了布局后再调用
        initView(savedInstanceState);//由具体的activity实现，做视图相关的初始化
        setView(savedInstanceState);//由具体的activity实现，做视图相关的设置
        initData(savedInstanceState);//由具体的activity实现，做数据的初始化
        initEvent();//由具体的activity实现，做事件监听的初始化
    }

    private void initBarColor() {
        ViewGroup parent = (ViewGroup) findViewById(android.R.id.content);
        if (parent.getChildAt(0) instanceof DrawerLayout) {
            ColorBar.newDrawerBuilder()
                    .applyNav(true)
                    .navColor(mColor)
                    .navDepth(0)
                    .statusColor(mColor)
                    .statusDepth(0)
                    .build(this)
                    .apply();
        } else {
            ColorBar.newColorBuilder()
                    .applyNav(true)
                    .navColor(mColor)
                    .navDepth(0)
                    .statusColor(mColor)
                    .statusDepth(0)
                    .build(this)
                    .apply();
        }
    }

    protected int getContentLayout() {
        return R.layout.activity_take_photo;
    }

    private ArrayList<TImage> mDataList = new ArrayList<TImage>();

    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
    }

    protected void setView(Bundle savedInstanceState) {
        setTakePhoto();
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

    private void setTakePhoto() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(file);
        takePhoto = getTakePhoto();//获取实例
        LubanOptions option = new LubanOptions.Builder()
                .setMaxHeight(height)
                .setMaxWidth(width)
                .setMaxSize(maxSize)
                .create();
        CompressConfig config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);//拍照压缩后是否保存原图
        takePhoto.onEnableCompress(config, showProgressBar);//指定压缩工具,是否显示压缩进度

        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(withOwnGallery);//是否使用TakePhoto自带相册
        builder.setCorrectImage(correctImage);//是否纠正拍照的照片旋转角度
        takePhoto.setTakePhotoOptions(builder.create());
    }

    protected void initData(Bundle savedInstanceState) {
    }

    protected void initEvent() {
    }

    @Override
    public void takeSuccess(TResult result) {
        if (result != null) {
            ArrayList<TImage> images = result.getImages();
            if (images != null && images.size() > 0) {
                mDataList.addAll(images);
                takePhotoImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        RingLog.e(TAG, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.btnPickByTake, R.id.btnPickBySelect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPickByTake:
                if (iscrop) {//是否裁剪
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                } else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                break;
            case R.id.btnPickBySelect:
                if (limit > 1) {//图片选择张数
                    if (iscrop) {//是否裁剪
                        takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
                    } else {
                        takePhoto.onPickMultiple(limit);
                    }
                    return;
                }
                if (selectFrom == 2) {//从哪选择：1从相册，2从文件
                    if (iscrop) {//是否裁剪
                        takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
                    } else {
                        takePhoto.onPickFromDocuments();
                    }
                    return;
                } else {
                    if (iscrop) {//是否裁剪
                        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                    } else {
                        takePhoto.onPickFromGallery();
                    }
                }
                break;
        }
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        if (rgCropSize) {
            builder.setAspectX(width).setAspectY(height);
        } else {
            builder.setOutputX(width).setOutputY(height);
        }
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
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
