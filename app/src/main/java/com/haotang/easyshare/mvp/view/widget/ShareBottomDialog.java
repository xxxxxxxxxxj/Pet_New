package com.haotang.easyshare.mvp.view.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.http.ShareApiService;
import com.haotang.easyshare.shareutil.ShareUtil;
import com.haotang.easyshare.shareutil.share.ShareListener;
import com.haotang.easyshare.shareutil.share.SharePlatform;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.other.RingLog;

import me.shaohui.bottomdialog.BaseBottomDialog;
import okhttp3.MultipartBody;

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
    private String uuid;
    private int type;
    private Context context;

    public ShareBottomDialog() {
        context = getActivity();
    }

    public ShareBottomDialog(Context context) {
        this.context = context;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_bottom_share;
    }

    public void setShareInfo(String title, String summary, String targetUrl, String thumbUrlOrPath) {
        this.mTitle = title;
        this.mSummary = summary;
        this.mTargetUrl = targetUrl;
        this.mThumbUrlOrPath = thumbUrlOrPath;
        if (StringUtil.isEmpty(this.mTitle)) {
            this.mTitle = context.getResources().getString(R.string.app_name);
        }
        if (StringUtil.isEmpty(this.mSummary)) {
            this.mSummary = context.getResources().getString(R.string.app_name);
        }
        if (StringUtil.isEmpty(this.mTargetUrl)) {
            this.mTargetUrl = AppConfig.URL;
        }
        if (StringUtil.isEmpty(this.mThumbUrlOrPath)) {
            this.mThumbUrlOrPath = AppConfig.SHAREIMG_URL;
        }
        RingLog.e("this.mTitle = " + this.mTitle);
        RingLog.e("this.mSummary = " + this.mSummary);
        RingLog.e("this.mTargetUrl = " + this.mTargetUrl);
        RingLog.e("this.mThumbUrlOrPath = " + this.mThumbUrlOrPath);
    }

    public void completeUrl(Activity activity) {
        if (this.mTargetUrl != null && !TextUtils.isEmpty(this.mTargetUrl)) {
            if (!this.mTargetUrl.startsWith("http:")
                    && !this.mTargetUrl.startsWith("https:") && !this.mTargetUrl.startsWith("file:///")) {
                this.mTargetUrl = UrlConstants.getServiceBaseUrl() + this.mTargetUrl;
            }
            if (this.mTargetUrl.contains("?")) {
                this.mTargetUrl = this.mTargetUrl + "&system=android_" + SystemUtil.getCurrentVersion(activity)
                        + "&imei="
                        + SystemUtil.getIMEI(activity)
                        + "&phone="
                        + SharedPreferenceUtil.getInstance(activity).getString("cellphone", "") + "&phoneModel="
                        + android.os.Build.BRAND + " " + android.os.Build.MODEL
                        + "&phoneSystemVersion=" + "Android "
                        + android.os.Build.VERSION.RELEASE + "&petTimeStamp="
                        + System.currentTimeMillis();
            } else {
                this.mTargetUrl = this.mTargetUrl + "?system=android_" + SystemUtil.getCurrentVersion(activity)
                        + "&imei="
                        + SystemUtil.getIMEI(activity)
                        + "&phone="
                        + SharedPreferenceUtil.getInstance(activity).getString("cellphone", "") + "&phoneModel="
                        + android.os.Build.BRAND + " " + android.os.Build.MODEL
                        + "&phoneSystemVersion=" + "Android "
                        + android.os.Build.VERSION.RELEASE + "&petTimeStamp="
                        + System.currentTimeMillis();
            }
        }
        if (this.mThumbUrlOrPath != null && !TextUtils.isEmpty(this.mThumbUrlOrPath)) {
            if (!this.mThumbUrlOrPath.startsWith("http:")
                    && !this.mThumbUrlOrPath.startsWith("https:") && !this.mThumbUrlOrPath.startsWith("file:///")) {
                this.mThumbUrlOrPath = UrlConstants.getServiceBaseUrl() + this.mThumbUrlOrPath;
            }
        }
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
                shareCallback(v);
            }

            @Override
            public void shareFailure(Exception e) {
                RingLog.d(TAG, "分享失败 e = " + e.toString());
                Toast.makeText(v.getContext(), "分享失败", Toast.LENGTH_SHORT).show();
                shareCallback(v);
            }

            @Override
            public void shareCancel() {
                Toast.makeText(v.getContext(), "取消分享", Toast.LENGTH_SHORT).show();
                shareCallback(v);
            }
        };
    }

    private void shareCallback(final View v) {
        if (type == 1) {//分享帖子成功回调
            MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                    .addFormDataPart("uuid", uuid)
                    .build();
            DevRing.httpManager().commonRequest(DevRing.httpManager().getService(ShareApiService.class).callback(UrlConstants.getMapHeader(context), body)
                    , new CommonObserver<HttpResult<AddChargeBean>>() {
                        @Override
                        public void onResult(HttpResult<AddChargeBean> result) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    RingLog.e("分享回调成功");
                                } else {
                                    SystemUtil.Exit(v.getContext(), result.getCode());
                                    if (StringUtil.isNotEmpty(result.getMsg())) {
                                        RingLog.e("onError() status = " + result.getCode() + "---desc = " + result.getMsg());
                                    } else {
                                        RingLog.e("onError() status = " + AppConfig.SERVER_ERROR + "---desc = " + AppConfig.SERVER_ERROR_MSG);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(int errType, String errMessage) {
                            RingLog.e("onError() status = " + errType + "---desc = " + errMessage);
                        }
                    }, null);
        } else if (type == 2) {//分享站点详情成功回调
            MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                    .addFormDataPart("uuid", uuid)
                    .build();
            DevRing.httpManager().commonRequest(DevRing.httpManager().getService(ShareApiService.class).callbackChargeing(UrlConstants.getMapHeader(context), body)
                    , new CommonObserver<HttpResult<AddChargeBean>>() {
                        @Override
                        public void onResult(HttpResult<AddChargeBean> result) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    RingLog.e("分享回调成功");
                                } else {
                                    SystemUtil.Exit(v.getContext(), result.getCode());
                                    if (StringUtil.isNotEmpty(result.getMsg())) {
                                        RingLog.e("onError() status = " + result.getCode() + "---desc = " + result.getMsg());
                                    } else {
                                        RingLog.e("onError() status = " + AppConfig.SERVER_ERROR + "---desc = " + AppConfig.SERVER_ERROR_MSG);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(int errType, String errMessage) {
                            RingLog.e("onError() status = " + errType + "---desc = " + errMessage);
                        }
                    }, null);
        } else if (type == 3) {//分享车辆详情成功回调
            MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                    .addFormDataPart("id", uuid)
                    .build();
            DevRing.httpManager().commonRequest(DevRing.httpManager().getService(ShareApiService.class).callbackCar(UrlConstants.getMapHeader(context), body)
                    , new CommonObserver<HttpResult<AddChargeBean>>() {
                        @Override
                        public void onResult(HttpResult<AddChargeBean> result) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    RingLog.e("分享回调成功");
                                } else {
                                    SystemUtil.Exit(v.getContext(), result.getCode());
                                    if (StringUtil.isNotEmpty(result.getMsg())) {
                                        RingLog.e("onError() status = " + result.getCode() + "---desc = " + result.getMsg());
                                    } else {
                                        RingLog.e("onError() status = " + AppConfig.SERVER_ERROR + "---desc = " + AppConfig.SERVER_ERROR_MSG);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(int errType, String errMessage) {
                            RingLog.e("onError() status = " + errType + "---desc = " + errMessage);
                        }
                    }, null);
        }
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
