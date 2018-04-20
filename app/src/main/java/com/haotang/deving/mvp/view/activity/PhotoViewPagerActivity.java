package com.haotang.deving.mvp.view.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.haotang.deving.R;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.haotang.deving.mvp.view.widget.HackyViewPager;
import com.haotang.deving.util.SystemUtil;
import com.ljy.devring.DevRing;

import java.io.File;

import butterknife.BindView;

public class PhotoViewPagerActivity extends BaseActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    @BindView(R.id.pager)
    HackyViewPager pager;
    @BindView(R.id.indicator)
    TextView indicator;
    private int pagerPosition;
    private String[] urls;
    private File photoViewFile;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_photo_view_pager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        urls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        pager.setAdapter(new SamplePagerAdapter());
        CharSequence text = getString(R.string.viewpager_indicator, 1, pager
                .getAdapter().getCount());
        indicator.setText(text);
        pager.setCurrentItem(pagerPosition);
        photoViewFile = new File(Environment.getExternalStorageDirectory(),
                "PhotoViewFile");
        if (!photoViewFile.exists()) {
            photoViewFile.mkdirs();
        }
    }

    class SamplePagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return urls.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            PhotoViewAttacher mAttacher = photoView.getAttacher();
            mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    finish();
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
            Glide.with(PhotoViewPagerActivity.this).load(urls[position]).error(R.mipmap.ic_image_load)
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
                CharSequence text = getString(R.string.viewpager_indicator,
                        arg0 + 1, pager.getAdapter().getCount());
                indicator.setText(text);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }
}
