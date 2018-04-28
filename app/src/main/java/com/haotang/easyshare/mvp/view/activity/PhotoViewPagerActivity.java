package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.PhotoViewPagerImg;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.widget.HackyViewPager;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.haotang.easyshare.R.id.rl_pv_titlebar;

public class PhotoViewPagerActivity extends BaseActivity {
    protected final static String TAG = PhotoViewPagerActivity.class.getSimpleName();
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IMAGE_ISDELETE = "image_isdelete";
    @BindView(R.id.pager)
    HackyViewPager pager;
    @BindView(R.id.iv_pv_titlebar_back)
    ImageView ivPvTitlebarBack;
    @BindView(R.id.iv_pv_titlebar_delete)
    ImageView ivPvTitlebarDelete;
    @BindView(R.id.tv_pv_titlebar_indicator)
    TextView tvPvTitlebarIndicator;
    @BindView(rl_pv_titlebar)
    RelativeLayout rlPvTitlebar;
    private int pagerPosition;
    private List<String> urls = new ArrayList<String>();
    private File photoViewFile;
    private SamplePagerAdapter samplePagerAdapter;
    private boolean isDelete;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_photo_view_pager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setBarColor(getResources().getColor(R.color.a3a3636));
        DevRing.activityStackManager().pushOneActivity(this);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        isDelete = getIntent().getBooleanExtra(EXTRA_IMAGE_ISDELETE, false);
        urls.clear();
        List<String> strings = Arrays.asList(getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS));
        urls.addAll(strings);
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        rlPvTitlebar.startAnimation(AnimationUtils.loadAnimation(PhotoViewPagerActivity.this,
                R.anim.commodity_detail_show));//开始动画
        rlPvTitlebar.bringToFront();
        rlPvTitlebar.setVisibility(View.VISIBLE);
        if (isDelete) {
            ivPvTitlebarDelete.setVisibility(View.VISIBLE);
        } else {
            ivPvTitlebarDelete.setVisibility(View.GONE);
        }
        samplePagerAdapter = new SamplePagerAdapter();
        pager.setAdapter(samplePagerAdapter);
        CharSequence text = getString(R.string.viewpager_indicator, pagerPosition + 1, pager
                .getAdapter().getCount());
        tvPvTitlebarIndicator.setText(text);
        pager.setCurrentItem(pagerPosition);
        photoViewFile = new File(Environment.getExternalStorageDirectory(),
                "PhotoViewFile");
        if (!photoViewFile.exists()) {
            photoViewFile.mkdirs();
        }
    }

    @OnClick({R.id.iv_pv_titlebar_back, R.id.iv_pv_titlebar_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pv_titlebar_back:
                finish();
                break;
            case R.id.iv_pv_titlebar_delete:
                int position = getPosition(pagerPosition);
                RingLog.d(TAG, "pagerPosition = " + pagerPosition);
                RingLog.d(TAG, "position = " + position);
                String imgUrl = urls.get(pagerPosition);
                urls.remove(pagerPosition);
                if (urls.size() <= 0) {
                    samplePagerAdapter.notifyDataSetChanged();
                    PhotoViewPagerImg photoViewPagerImg = new PhotoViewPagerImg();
                    photoViewPagerImg.setDeleteAll(true);
                    DevRing.busManager().postEvent(photoViewPagerImg);
                    finish();
                } else {
                    PhotoViewPagerImg photoViewPagerImg = new PhotoViewPagerImg();
                    photoViewPagerImg.setImgUrl(imgUrl);
                    photoViewPagerImg.setPagerPosition(pagerPosition);
                    photoViewPagerImg.setDeleteAll(false);
                    DevRing.busManager().postEvent(photoViewPagerImg);
                    samplePagerAdapter.notifyDataSetChanged();
                    pager.setCurrentItem(position);
                    pagerPosition = position;
                    CharSequence text = getString(R.string.viewpager_indicator,
                            position + 1, pager.getAdapter().getCount());
                    tvPvTitlebarIndicator.setText(text);
                }
                break;
        }
    }

    private int getPosition(int removePosition) {
        int position = 0;
        if (urls.size() <= 1) {
            position = -1;
        } else {
            if (removePosition == urls.size() - 1) {
                position = urls.size() - 2;
            } else if (removePosition == 0) {
                position = 0;
            } else {
                position = removePosition;
            }
        }
        return position;
    }

    class SamplePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            PhotoViewAttacher mAttacher = photoView.getAttacher();
            mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    if (rlPvTitlebar.getVisibility() == View.VISIBLE) {
                        rlPvTitlebar.startAnimation(AnimationUtils.loadAnimation(PhotoViewPagerActivity.this,
                                R.anim.commodity_detail_hide));//开始动画
                        rlPvTitlebar.setVisibility(View.GONE);
                    } else {
                        rlPvTitlebar.startAnimation(AnimationUtils.loadAnimation(PhotoViewPagerActivity.this,
                                R.anim.commodity_detail_show));//开始动画
                        rlPvTitlebar.bringToFront();
                        rlPvTitlebar.setVisibility(View.VISIBLE);
                    }
                }
            });
            mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String fileName = "Pet_Circle_"
                            + String.valueOf(System.currentTimeMillis() + ".jpg");
                    SystemUtil.savePic(PhotoViewPagerActivity.this, photoView, photoViewFile, fileName);
                    return true;
                }
            });
            Glide.with(PhotoViewPagerActivity.this).load(urls.get(position)).error(R.mipmap.ic_image_load)
                    .placeholder(R.mipmap.ic_image_load).into(photoView);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        // 更新下标
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                RingLog.d(TAG, "onPageSelected arg0 = " + arg0);
                pagerPosition = arg0;
                CharSequence text = getString(R.string.viewpager_indicator,
                        arg0 + 1, pager.getAdapter().getCount());
                tvPvTitlebarIndicator.setText(text);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }
}
