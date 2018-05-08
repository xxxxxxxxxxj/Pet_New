package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.di.component.activity.DaggerAddChargeActivityCommponent;
import com.haotang.easyshare.di.module.activity.AddChargeActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.model.entity.res.PhotoViewPagerImg;
import com.haotang.easyshare.mvp.presenter.AddChargePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CommentImgAdapter;
import com.haotang.easyshare.mvp.view.iview.IAddChargeView;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
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
import top.zibin.luban.Luban;

/**
 * 添加充电站界面
 */
public class AddChargeActivity extends BaseActivity<AddChargePresenter> implements IAddChargeView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_addcharge_img)
    RecyclerView rvAddchargeImg;
    @BindView(R.id.et_addcharge_zmc)
    EditText etAddchargeZmc;
    @BindView(R.id.tv_addcharge_zdz)
    TextView tvAddchargeZdz;
    @BindView(R.id.rl_addcharge_zdz)
    RelativeLayout rlAddchargeZdz;
    @BindView(R.id.et_addcharge_sl)
    EditText etAddchargeSl;
    @BindView(R.id.iv_addcharge_kuai)
    ImageView ivAddchargeKuai;
    @BindView(R.id.ll_addcharge_kuai)
    LinearLayout llAddchargeKuai;
    @BindView(R.id.iv_addcharge_man)
    ImageView ivAddchargeMan;
    @BindView(R.id.ll_addcharge_man)
    LinearLayout llAddchargeMan;
    @BindView(R.id.ll_addcharge_kuaiorman)
    LinearLayout llAddchargeKuaiorman;
    @BindView(R.id.et_addcharge_phone)
    EditText etAddchargePhone;
    @BindView(R.id.et_addcharge_cdf)
    EditText etAddchargeCdf;
    @BindView(R.id.et_addcharge_fwf)
    EditText etAddchargeFwf;
    @BindView(R.id.et_addcharge_tcf)
    EditText etAddchargeTcf;
    @BindView(R.id.tv_addcharge_zffs)
    TextView tvAddchargeZffs;
    @BindView(R.id.rl_addcharge_zffs)
    RelativeLayout rlAddchargeZffs;
    @BindView(R.id.iv_addcharge_up)
    ImageView ivAddchargeUp;
    @BindView(R.id.ll_addcharge_up)
    LinearLayout llAddchargeUp;
    @BindView(R.id.iv_addcharge_down)
    ImageView ivAddchargeDown;
    @BindView(R.id.ll_addcharge_down)
    LinearLayout llAddchargeDown;
    @BindView(R.id.ll_addcharge_upordown)
    LinearLayout llAddchargeUpordown;
    @BindView(R.id.tv_addcharge_kfsj)
    TextView tvAddchargeKfsj;
    @BindView(R.id.rl_addcharge_kfsj)
    RelativeLayout rlAddchargeKfsj;
    @BindView(R.id.et_addcharge_bzsm)
    EditText etAddchargeBzsm;
    private List<CommentImg> imgList = new ArrayList<CommentImg>();
    private List<String> imgPathList = new ArrayList<String>();
    private CommentImgAdapter commentImgAdapter;
    private static final int IMG_NUM = 3;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_add_charge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerAddChargeActivityCommponent.builder().
                addChargeActivityModule(new AddChargeActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("新建充电桩");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarOther.setText("保存");

        imgList.add(new CommentImg("", true));
        rvAddchargeImg.setHasFixedSize(true);
        rvAddchargeImg.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                NoScollFullGridLayoutManager(rvAddchargeImg, this, 3, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvAddchargeImg.setLayoutManager(noScollFullGridLayoutManager);
        rvAddchargeImg.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        commentImgAdapter = new CommentImgAdapter(R.layout.item_comment_img
                , imgList);
        rvAddchargeImg.setAdapter(commentImgAdapter);

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
                    Matisse.from(AddChargeActivity.this)
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
                    SystemUtil.goPhotoView(AddChargeActivity.this, position, imgPathList, true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConfig.REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            if (data != null) {
                compressWithRx(Matisse.obtainPathResult(data));
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
                        return Luban.with(AddChargeActivity.this).load(list).get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> list) throws Exception {
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
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    private void setKuaiOrMan(int flag) {
        if (flag == 0) {//快充
            ivAddchargeKuai.setImageResource(R.mipmap.icon_addcharge_select);
            ivAddchargeMan.setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 1) {//慢充
            ivAddchargeKuai.setImageResource(R.mipmap.icon_addcharge_unselect);
            ivAddchargeMan.setImageResource(R.mipmap.icon_addcharge_select);
        }
    }

    private void setUpOrDown(int flag) {
        if (flag == 0) {//地上
            ivAddchargeUp.setImageResource(R.mipmap.icon_addcharge_select);
            ivAddchargeDown.setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 1) {//地下
            ivAddchargeUp.setImageResource(R.mipmap.icon_addcharge_unselect);
            ivAddchargeDown.setImageResource(R.mipmap.icon_addcharge_select);
        }
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other, R.id.rl_addcharge_zdz, R.id.ll_addcharge_kuai, R.id.ll_addcharge_man, R.id.rl_addcharge_zffs, R.id.ll_addcharge_up, R.id.ll_addcharge_down, R.id.rl_addcharge_kfsj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                break;
            case R.id.rl_addcharge_zdz:
                break;
            case R.id.ll_addcharge_kuai:
                setKuaiOrMan(0);
                break;
            case R.id.ll_addcharge_man:
                setKuaiOrMan(1);
                break;
            case R.id.rl_addcharge_zffs:
                break;
            case R.id.ll_addcharge_up:
                setUpOrDown(0);
                break;
            case R.id.ll_addcharge_down:
                setUpOrDown(1);
                break;
            case R.id.rl_addcharge_kfsj:
                break;
        }
    }
}
