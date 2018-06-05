package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.di.component.activity.DaggerEditUserInfoActivityCommponent;
import com.haotang.easyshare.di.module.activity.EditUserInfoActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.presenter.EditUserInfoPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IEditUserInfoView;
import com.haotang.easyshare.mvp.view.widget.GlideCircleTransform;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
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
 * 编辑用户资料界面
 */
public class EditUserInfoActivity extends BaseActivity<EditUserInfoPresenter> implements IEditUserInfoView {
    protected final static String TAG = EditUserInfoActivity.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.iv_edituserinfo_img)
    ImageView ivEdituserinfoImg;
    @BindView(R.id.et_edituserinfo_username)
    EditText etEdituserinfoUsername;
    private static final int IMG_NUM = 1;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    private File userImgFile;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerEditUserInfoActivityCommponent.builder().
                editUserInfoActivityModule(new EditUserInfoActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("编辑资料");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarOther.setText("保存");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        mPresenter.home();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
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

    @OnClick({R.id.iv_titlebar_back, R.id.rl_edituserinfo_img, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_titlebar_other:
                showDialog();
                //构建body
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("userName", etEdituserinfoUsername.getText().toString().trim());
                if (userImgFile != null && userImgFile.length() > 0) {
                    builder.addFormDataPart("file", userImgFile.getName(), RequestBody.create(MediaType.parse("application/octet-stream")
                            , userImgFile));
                }
                RequestBody body = builder.build();
                mPresenter.save(body);
                break;
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.rl_edituserinfo_img:
                Matisse.from(EditUserInfoActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .capture(true)
                        .captureStrategy(
                                new CaptureStrategy(true, "com.haotang.easyshare.MatisseFileProvider"))
                        .maxSelectable(IMG_NUM)
                        .gridExpectedSize(
                                getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(AppConfig.REQUEST_CODE_CHOOSE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
                showDialog();
                compressWithRx(Matisse.obtainPathResult(data));
            }
        }
    }

    private void compressWithRx(List<String> pathList) {
        Flowable.just(pathList)
                .observeOn(Schedulers.io())
                .map(new Function<List<String>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<String> list) throws Exception {
                        return Luban.with(EditUserInfoActivity.this).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) throws Exception {
                        disMissDialog();
                        if (list != null && list.size() > 0) {
                            userImgFile = list.get(0);
                            Glide.with(EditUserInfoActivity.this).load(userImgFile).error(R.mipmap.ic_image_load_circle)
                                    .placeholder(R.mipmap.ic_image_load_circle)
                                    .bitmapTransform(new GlideCircleTransform(EditUserInfoActivity.this))
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .into(ivEdituserinfoImg);
                        }
                    }
                });
    }

    @Override
    public void homeSuccess(HomeBean data) {
        disMissDialog();
        RingLog.e(TAG, "homeSuccess()");
        if (data != null) {
            StringUtil.setText(etEdituserinfoUsername, data.getUserName(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetCircleImg(this, data.getHeadImg(), ivEdituserinfoImg, R.mipmap.ic_image_load_circle);
        }
    }

    @Override
    public void homeFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "homeFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void saveSuccess(AddChargeBean data) {
        disMissDialog();
        DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MYFRAGMET));
        finish();
    }

    @Override
    public void saveFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "homeFail() status = " + code + "---desc = " + msg);
    }
}
