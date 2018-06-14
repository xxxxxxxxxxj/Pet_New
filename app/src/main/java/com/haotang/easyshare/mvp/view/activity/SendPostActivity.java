package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.di.component.activity.DaggerSendPostActivityCommponent;
import com.haotang.easyshare.di.module.activity.SendPostActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshEvent;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.model.entity.res.PhotoViewPagerImg;
import com.haotang.easyshare.mvp.presenter.SendPostPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CommentImgAdapter;
import com.haotang.easyshare.mvp.view.iview.ISendPostView;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * 发帖界面
 */
public class SendPostActivity extends BaseActivity<SendPostPresenter> implements ISendPostView {
    private static final int REQUESTCODE_BRANDCAR = 1111;
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.et_send_post)
    EditText etSendPost;
    @BindView(R.id.rv_send_post_img)
    RecyclerView rvSendPostImg;
    @BindView(R.id.tv_sendpost_wtc)
    TextView tv_sendpost_wtc;
    @BindView(R.id.tv_send_post_cx)
    TextView tv_send_post_cx;
    private List<CommentImg> imgList = new ArrayList<CommentImg>();
    private List<String> imgPathList = new ArrayList<String>();
    private CommentImgAdapter commentImgAdapter;
    private static final int IMG_NUM = 9;
    private int carId;
    private int category = 1;
    private String carName;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_send_post;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerSendPostActivityCommponent.builder().sendPostActivityModule(new SendPostActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarOther.setText("发送");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarTitle.setText("发帖");

        imgList.add(new CommentImg("", true));
        rvSendPostImg.setHasFixedSize(true);
        rvSendPostImg.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new NoScollFullGridLayoutManager(rvSendPostImg, this, 3, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvSendPostImg.setLayoutManager(noScollFullGridLayoutManager);
        rvSendPostImg.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                false));
        commentImgAdapter = new CommentImgAdapter(R.layout.item_comment_img
                , imgList);
        rvSendPostImg.setAdapter(commentImgAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        commentImgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommentImg commentImg = imgList.get(position);
                if (commentImg.isAdd()) {
                    Matisse.from(SendPostActivity.this)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .capture(true)
                            .captureStrategy(
                                    new CaptureStrategy(true, "com.haotang.easyshare.MatisseFileProvider"))
                            .maxSelectable((IMG_NUM + 1) - imgList.size())
                            .gridExpectedSize(
                                    getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(AppConfig.REQUEST_CODE_CHOOSE);
                } else {
                    SystemUtil.goPhotoView(SendPostActivity.this, position, imgPathList, true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == AppConfig.REQUEST_CODE_CHOOSE) {
                if (data != null) {
                    showDialog();
                    compressWithRx(Matisse.obtainPathResult(data));
                }
            } else if (requestCode == REQUESTCODE_BRANDCAR) {
                if (data != null) {
                    carId = data.getIntExtra("carId", 0);
                    carName = data.getStringExtra("carName");
                    StringUtil.setText(tv_send_post_cx, carName, "", View.VISIBLE, View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    private void compressWithRx(List<String> pathList) {
        Flowable.just(pathList)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        return Luban.with(SendPostActivity.this).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) throws Exception {
                        disMissDialog();
                        for (int i = 0; i < imgList.size(); i++) {
                            CommentImg commentImg = imgList.get(i);
                            if (commentImg.isAdd()) {
                                imgList.remove(i);
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            imgPathList.add(list.get(i).getAbsolutePath());
                            imgList.add(new CommentImg(list.get(i).getAbsolutePath(), false));
                        }
                        if (imgList.size() > IMG_NUM) {
                            for (int i = 0; i < imgList.size(); i++) {
                                CommentImg commentImg = imgList.get(i);
                                if (commentImg.isAdd()) {
                                    imgList.remove(i);
                                }
                            }
                        }
                        if (imgList.size() < IMG_NUM) {
                            boolean isAdd = false;
                            for (int i = 0; i < imgList.size(); i++) {
                                CommentImg commentImg = imgList.get(i);
                                if (commentImg.isAdd()) {
                                    isAdd = true;
                                    break;
                                }
                            }
                            if (!isAdd) {
                                imgList.add(new CommentImg("", true));
                            }
                        }
                        commentImgAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Subscribe
    public void getRemovePosition(PhotoViewPagerImg photoViewPagerImg) {
        String imgUrl = photoViewPagerImg.getImgUrl();
        int pagerPosition = photoViewPagerImg.getPagerPosition();
        boolean deleteAll = photoViewPagerImg.isDeleteAll();
        RingLog.d(TAG, "getRemovePosition imgUrl = " + imgUrl);
        RingLog.d(TAG, "getRemovePosition pagerPosition = " + pagerPosition);
        if (deleteAll) {
            imgPathList.clear();
            imgList.clear();
        } else {
            for (int i = 0; i < imgList.size(); i++) {
                CommentImg commentImg = imgList.get(i);
                if (commentImg.getImgUrl().equals(imgUrl)) {
                    imgList.remove(i);
                    imgPathList.remove(i);
                    break;
                }
            }
        }
        if (imgList.size() < IMG_NUM) {
            boolean isAdd = false;
            for (int i = 0; i < imgList.size(); i++) {
                CommentImg commentImg = imgList.get(i);
                if (commentImg.isAdd()) {
                    isAdd = true;
                    break;
                }
            }
            if (!isAdd) {
                imgList.add(new CommentImg("", true));
            }
        }
        commentImgAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other, R.id.tv_sendpost_wtc, R.id.rll_send_post_xzcx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rll_send_post_xzcx:
                startActivityForResult(new Intent(SendPostActivity.this, BrandCarActivity.class), REQUESTCODE_BRANDCAR);
                break;
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_sendpost_wtc:
                setWtc();
                break;
            case R.id.tv_titlebar_other:
                if (carId > 0) {
                    showDialog();
                    //构建body
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    builder.addFormDataPart("carId", String.valueOf(carId));
                    builder.addFormDataPart("category", String.valueOf(category));
                    builder.addFormDataPart("content", etSendPost.getText().toString().trim());
                    for (int i = 0; i < imgPathList.size(); i++) {
                        //构建要上传的文件
                        File file = new File(imgPathList.get(i));
                        builder.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream")
                                , file));
                    }
                    RequestBody build = builder.build();
                    mPresenter.save(build);
                } else {
                    RingToast.show("请先选择车型");
                    startActivityForResult(new Intent(SendPostActivity.this, BrandCarActivity.class), REQUESTCODE_BRANDCAR);
                }
                break;
        }
    }

    private void setWtc() {
        if (category == 1) {
            category = 2;
            tv_sendpost_wtc.setBackgroundResource(R.mipmap.bg_postlist_calss_select);
            tv_sendpost_wtc.setTextColor(getResources().getColor(R.color.white));
        } else if (category == 2) {
            category = 1;
            tv_sendpost_wtc.setBackgroundResource(R.mipmap.bg_postlist_calss_unselect);
            tv_sendpost_wtc.setTextColor(getResources().getColor(R.color.a666666));
        }
    }

    @Override
    public void saveSuccess(AddChargeBean data) {
        disMissDialog();
        DevRing.busManager().postEvent(new RefreshEvent(RefreshEvent.SEND_POST));
        finish();
    }

    @Override
    public void saveFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this,code);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
