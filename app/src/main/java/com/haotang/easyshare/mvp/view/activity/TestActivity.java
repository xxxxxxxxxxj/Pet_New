package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.ALiPayResult;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.TestAdapter;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.ShareBottomDialog;
import com.haotang.easyshare.shareutil.LoginUtil;
import com.haotang.easyshare.shareutil.login.LoginListener;
import com.haotang.easyshare.shareutil.login.LoginPlatform;
import com.haotang.easyshare.shareutil.login.LoginResult;
import com.haotang.easyshare.shareutil.login.result.BaseToken;
import com.haotang.easyshare.util.PayUtils;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 测试类
 */
public class TestActivity extends BaseActivity {
    protected final static String TAG = TestActivity.class.getSimpleName();
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_test)
    RecyclerView rv_test;
    private String listArr[] = {"webview", "share", "微信登录", "微信支付", "支付宝支付", "高德地图", "扫描二维码", "图片选择",
            "BaseRecyclerViewAdapterHelper", "banner", "jchat", "动画", "vLayout"};
    private TestAdapter testAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("测试");

        rv_test.setHasFixedSize(true);
        rv_test.setLayoutManager(new
                GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rv_test.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing10),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing10),
                true));
        testAdapter = new TestAdapter(R.layout.item_test
                , Arrays.asList(listArr));
        rv_test.setAdapter(testAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        testAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RingLog.e("position = " + position);
                switch (position) {
                    case 0://webview
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
                        startActivity(new Intent(TestActivity.this, WebViewActivity.class).putExtra("url_key", url));
                        break;
                    case 1://share
                        ShareBottomDialog dialog = new ShareBottomDialog();
                        dialog.setShareInfo("测试", "测试",
                                "https://www.duba.com", UrlConstants.getServiceBaseUrl() + "/static/icon/shouye.png?3");
                        dialog.completeUrl(TestActivity.this);
                        dialog.show(getSupportFragmentManager());
                        break;
                    case 2://微信登录
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
                    case 3://微信支付
                        break;
                    case 4://支付宝支付
                        PayUtils.payByAliPay(TestActivity.this, "", mHandler);
                        break;
                    case 5://高德地图
                        startActivity(new Intent(TestActivity.this, MapActivity.class));
                        break;
                    case 6://扫描二维码
                        break;
                    case 7://图片选择
                        startActivity(new Intent(TestActivity.this, ImagePickerActivity.class));
                        break;
                    case 8://BaseRecyclerViewAdapterHelper
                        break;
                    case 9://banner
                        break;
                    case 10://jchat
                        startActivity(new Intent(TestActivity.this, JChatTestActivity.class));
                        break;
                    case 11://动画
                        startActivity(new Intent(TestActivity.this, AnimationsActivity.class));
                        break;
                    case 12://vLayout
                        startActivity(new Intent(TestActivity.this, VLayoutActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AppConfig.ALI_SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    ALiPayResult payResult = new ALiPayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        RingLog.d(TAG, "支付成功");
                        RingToast.show("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        RingLog.d(TAG, "支付失败");
                        RingToast.show("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Subscribe
    public void onWXPayResult(BaseResp baseResp) {

    }

    @OnClick({R.id.iv_titlebar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
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
