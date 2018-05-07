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
import com.haotang.easyshare.di.component.activity.DaggerCommentActivityCommponent;
import com.haotang.easyshare.di.module.activity.CommentActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.model.entity.res.CommentTag;
import com.haotang.easyshare.mvp.model.entity.res.PhotoViewPagerImg;
import com.haotang.easyshare.mvp.presenter.CommentPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CommentImgAdapter;
import com.haotang.easyshare.mvp.view.adapter.CommentTagAdapter;
import com.haotang.easyshare.mvp.view.iview.ICommentView;
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

public class CommentActivity extends BaseActivity<CommentPresenter> implements ICommentView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.rv_comment_img)
    RecyclerView rvCommentImg;
    @BindView(R.id.rv_comment_tag)
    RecyclerView rvCommentTag;
    private String[] tags = {"充电便利", "停车免费", "不用排队", "价格便宜", "充电快"};
    private List<CommentImg> imgList = new ArrayList<CommentImg>();
    private List<CommentTag> tagList = new ArrayList<CommentTag>();
    private List<String> imgPathList = new ArrayList<String>();
    private CommentImgAdapter commentImgAdapter;
    private CommentTagAdapter commentTagAdapter;
    private static final int IMG_NUM = 9;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerCommentActivityCommponent.builder().commentActivityModule(new CommentActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("评论");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarOther.setText("提交");
        for (int i = 0; i < tags.length; i++) {
            tagList.add(new CommentTag(tags[i], false));
        }
        imgList.add(new CommentImg("", true));
        rvCommentImg.setHasFixedSize(true);
        rvCommentImg.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new NoScollFullGridLayoutManager(rvCommentImg, this, 3, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvCommentImg.setLayoutManager(noScollFullGridLayoutManager);
        rvCommentImg.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                false));
        commentImgAdapter = new CommentImgAdapter(R.layout.item_comment_img
                , imgList);
        rvCommentImg.setAdapter(commentImgAdapter);

        rvCommentTag.setHasFixedSize(true);
        rvCommentTag.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager1 = new NoScollFullGridLayoutManager(rvCommentTag, this, 4, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager1.setScrollEnabled(false);
        rvCommentTag.setLayoutManager(noScollFullGridLayoutManager1);
        rvCommentTag.addItemDecoration(new GridSpacingItemDecoration(4,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                false));
        commentTagAdapter = new CommentTagAdapter(R.layout.item_comment_tag, tagList);
        rvCommentTag.setAdapter(commentTagAdapter);
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
                    Matisse.from(CommentActivity.this)
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
                    SystemUtil.goPhotoView(CommentActivity.this, position, imgPathList, true);
                }
            }
        });
        commentTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                tagList.get(position).setCheck(!tagList.get(position).isCheck());
                commentTagAdapter.notifyDataSetChanged();
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
                        return Luban.with(CommentActivity.this).load(list).get();
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

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                break;
        }
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
}
