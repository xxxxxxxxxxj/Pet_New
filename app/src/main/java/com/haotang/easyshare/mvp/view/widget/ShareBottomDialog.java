package com.haotang.easyshare.mvp.view.widget;

import android.view.View;
import android.widget.Toast;

import com.haotang.easyshare.R;
import com.haotang.easyshare.shareutil.ShareUtil;
import com.haotang.easyshare.shareutil.share.ShareListener;
import com.haotang.easyshare.shareutil.share.SharePlatform;
import com.ljy.devring.other.RingLog;

import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 * Created by shaohui on 2016/12/10.
 */

public class ShareBottomDialog extends BaseBottomDialog implements View.OnClickListener {
    protected final static String TAG = ShareBottomDialog.class.getSimpleName();
    private ShareListener mShareListener;
    private String mTitle;
    private String mSummary;
    private String mTargetUrl;
    private String mThumbUrlOrPath;

    @Override
    public int getLayoutRes() {
        return R.layout.layout_bottom_share;
    }

    public void setShareInfo(String title, String summary, String targetUrl, String thumbUrlOrPath) {
        this.mTitle = title;
        this.mSummary = summary;
        this.mTargetUrl = targetUrl;
        this.mThumbUrlOrPath = thumbUrlOrPath;
    }

    @Override
    public void bindView(final View v) {
        v.findViewById(R.id.share_qq).setOnClickListener(this);
        v.findViewById(R.id.share_qzone).setOnClickListener(this);
        v.findViewById(R.id.share_weibo).setOnClickListener(this);
        v.findViewById(R.id.share_wx).setOnClickListener(this);
        v.findViewById(R.id.share_wx_timeline).setOnClickListener(this);

        mShareListener = new ShareListener() {
            @Override
            public void shareSuccess() {
                Toast.makeText(v.getContext(), "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareFailure(Exception e) {
                RingLog.d(TAG, "分享失败 e = " + e.toString());
                Toast.makeText(v.getContext(), "分享失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void shareCancel() {
                Toast.makeText(v.getContext(), "取消分享", Toast.LENGTH_SHORT).show();

            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_qq:
                ShareUtil.shareMedia(getContext(), SharePlatform.QQ, mTitle, mSummary,
                        mTargetUrl, mThumbUrlOrPath,
                        mShareListener);
                break;
            case R.id.share_qzone:
                ShareUtil.shareMedia(getContext(), SharePlatform.QZONE, mTitle, mSummary,
                        mTargetUrl, mThumbUrlOrPath,
                        mShareListener);
                break;
            case R.id.share_weibo:
                ShareUtil.shareMedia(getContext(), SharePlatform.WEIBO, mTitle, mSummary,
                        mTargetUrl, mThumbUrlOrPath,
                        mShareListener);
                break;
            case R.id.share_wx_timeline:
                ShareUtil.shareMedia(getContext(), SharePlatform.WX_TIMELINE, mTitle, mSummary,
                        mTargetUrl, mThumbUrlOrPath,
                        mShareListener);
                break;
            case R.id.share_wx:
                ShareUtil.shareMedia(getContext(), SharePlatform.WX, mTitle, mSummary,
                        mTargetUrl, mThumbUrlOrPath,
                        mShareListener);
                break;
        }
        dismiss();
    }
}
