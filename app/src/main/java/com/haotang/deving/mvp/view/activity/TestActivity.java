package com.haotang.deving.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.haotang.deving.R;
import com.haotang.deving.app.constant.UrlConstants;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.haotang.deving.mvp.view.widget.ShareBottomDialog;
import com.haotang.deving.shareutil.LoginUtil;
import com.haotang.deving.shareutil.login.LoginListener;
import com.haotang.deving.shareutil.login.LoginPlatform;
import com.haotang.deving.shareutil.login.LoginResult;
import com.haotang.deving.shareutil.login.result.BaseToken;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import butterknife.OnClick;

/**
 * 测试类
 */
public class TestActivity extends BaseActivity {
    protected final static String TAG = TestActivity.class.getSimpleName();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);

    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.btn_test_webview, R.id.btn_test_share, R.id.btn_test_wxlogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_webview:
                int key = 2;
                String url = "";
                switch (key) {
                    /*Fragment 使用AgenWeb*/
                    case 0: //项目中请使用常量代替0 ， 代码可读性更高
                        url = "https://m.vip.com/?source=www&jump_https=1";
                        break;
                    /*下载文件*/
                    case 1:
                        url = "http://android.myapp.com/";
                        break;
                    /*input标签上传文件*/
                    case 2:
                        url = "file:///android_asset/upload_file/uploadfile.html";
                        break;
                    /*Js上传文件*/
                    case 3:
                        url = "file:///android_asset/upload_file/jsuploadfile.html";
                        break;
                    /*Js*/
                    case 4:
                        url = "file:///android_asset/js_interaction/hello.html";
                        break;
                    /*优酷*/
                    case 5:
                        url = "http://m.youku.com/video/id_XODEzMjU1MTI4.html";
                        break;
                    /*淘宝*/
                    case 6:
                        url = "https://m.taobao.com/?sprefer=sypc00";
                        break;
                    /*豌豆荚*/
                    case 7:
                        url = "http://www.wandoujia.com/apps";
                        break;
                    /*短信*/
                    case 8:
                        url = "file:///android_asset/sms/sms.html";
                        break;
                    /* 自定义 WebView */
                    case 9:
                        url = "http://m.youku.com/video/id_XODEzMjU1MTI4.html";
                        break;
                    /*回弹效果*/
                    case 10:
                        url = "http://m.mogujie.com/?f=mgjlm&ptp=_qd._cps______3069826.152.1.0";
                        break;
                    /*JsBridge 演示*/
                    case 11:
                        url = "file:///android_asset/jsbridge/demo.html";
                        break;
                    /*SmartRefresh 下拉刷新*/
                    case 12:
                        url = "http://www.163.com/";
                        break;
                    /*地图*/
                    case 13:
                        url = "https://map.baidu.com/mobile/webapp/index/index/#index/index/foo=bar/vt=map";
                        break;
                    /*首屏秒开*/
                    case 14:
                        url = "http://mc.vip.qq.com/demo/indexv3";
                        break;
                    default:
                        break;
                }
                startActivity(new Intent(this, WebViewActivity.class).putExtra("url_key", url));
                break;
            case R.id.btn_test_share:
                ShareBottomDialog dialog = new ShareBottomDialog();
                dialog.setShareInfo("测试", "测试",
                        "https://www.duba.com", UrlConstants.getServiceBaseUrl() + "/static/icon/shouye.png?3");
                dialog.show(getSupportFragmentManager());
                break;
            case R.id.btn_test_wxlogin:
                LoginUtil.login(TestActivity.this, LoginPlatform.WX, new LoginListener() {
                    @Override
                    public void loginSuccess(LoginResult result) {
                        RingLog.d(TAG, result.getUserInfo().getNickname());
                        RingLog.d(TAG, "登录成功");
                    }

                    @Override
                    public void beforeFetchUserInfo(BaseToken token) {
                        RingLog.d(TAG, "获取用户信息");
                    }

                    @Override
                    public void loginFailure(Exception e) {
                        e.printStackTrace();
                        RingLog.d(TAG, "登录失败e = " + e.toString());
                    }

                    @Override
                    public void loginCancel() {
                        RingLog.d(TAG, "登录取消");
                    }
                });
                break;
        }
    }
}
