package com.haotang.easyshare.mvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerScanCodeActivityCommponent;
import com.haotang.easyshare.di.module.activity.ScanCodeActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.presenter.ScanCodePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IScanCodeView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.SystemTypeUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.other.permission.PermissionListener;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 扫码界面
 */
public class ScanCodeActivity extends BaseActivity<ScanCodePresenter> implements IScanCodeView {
    @BindView(R.id.tv_scan_code_btn1)
    TextView tvScanCodeBtn1;
    @BindView(R.id.tv_scan_code_btn2)
    TextView tvScanCodeBtn2;
    @BindView(R.id.ll_scan_code_btn)
    LinearLayout llScanCodeBtn;
    @BindView(R.id.tv_scan_code_desc1)
    TextView tvScanCodeDesc1;
    @BindView(R.id.tv_scan_code_desc2)
    TextView tvScanCodeDesc2;
    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;
    @BindView(R.id.rl_scan_code_play)
    RelativeLayout rl_scan_code_play;
    private CaptureFragment captureFragment;
    @Inject
    PermissionDialog permissionDialog;
    public boolean isOpen = false;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_scan_code;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerScanCodeActivityCommponent.builder().
                scanCodeActivityModule(new ScanCodeActivityModule(this, this)).build().inject(this);
        permissionDialog.setMessage("该功能需您授予\"打开摄像头\"权限才可正常使用");
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        rl_scan_code_play.bringToFront();
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
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

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            RingLog.d("result = " + result);
            showDialog();
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            builder.addFormDataPart("code", result);
            RequestBody body = builder.build();
            mPresenter.start(body);
        }

        @Override
        public void onAnalyzeFailed() {
            RingLog.d("解析失败");
            RingToast.show("解析失败");
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {
        //申请必要权限
        DevRing.permissionManager().requestEachCombined(ScanCodeActivity.this, new PermissionListener() {
            @Override
            public void onGranted(String permissionName) {
            }

            @Override
            public void onDenied(String permissionName) {
                //如果用户拒绝了其中一个授权请求，则提醒用户
                RingToast.show("该功能需您授予\"打开摄像头\"权限才可正常使用");
            }

            @Override
            public void onDeniedWithNeverAsk(String permissionName) {
                //如果用户拒绝了其中一个授权请求，且勾选了不再提醒，则需要引导用户到权限管理页面开启
                permissionDialog.show();
            }
        }, Manifest.permission.CAMERA);
    }

    @Override
    protected void initEvent() {
        permissionDialog.setPositiveButton(R.string.permission_request_dialog_pos, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
                SystemTypeUtil.goToPermissionManager(ScanCodeActivity.this);
            }
        });
        permissionDialog.setNegativeButton(R.string.permission_request_dialog_nav, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
            }
        });
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_scan_code_btn1, R.id.tv_scan_code_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_scan_code_btn1:
                if (!isOpen) {
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }
                break;
            case R.id.tv_scan_code_btn2:
                startActivity(new Intent(ScanCodeActivity.this, InputChargeCodeActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @Override
    public void startFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void startSuccess(StartChargeing.DataBean data) {
        disMissDialog();
        if (data != null) {

        }
    }
}
