package com.haotang.newpet.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.haotang.newpet.R;
import com.haotang.newpet.di.component.activity.DaggerFlashActivityCommponent;
import com.haotang.newpet.di.module.activity.FlashActivityModule;
import com.haotang.newpet.mvp.model.entity.res.FlashBean;
import com.haotang.newpet.mvp.presenter.FlashPresenter;
import com.haotang.newpet.mvp.view.activity.base.BaseActivity;
import com.haotang.newpet.mvp.view.iview.IFlashView;
import com.haotang.newpet.util.CountdownUtil;
import com.haotang.newpet.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:欢迎页</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 17:47
 */
public class FlashActivity extends BaseActivity<FlashPresenter> implements IFlashView {
    private final static String TAG = FlashActivity.class.getSimpleName();
    private String backup;
    private String imgUrl;
    private String jumpUrl;
    private int point;

    @Override
    public void getFlashSuccess(FlashBean flashBean) {
        backup = flashBean.getBackup();
        imgUrl = flashBean.getImgUrl();
        jumpUrl = flashBean.getJumpUrl();
        point = flashBean.getPoint();
        initTimer(1);
    }

    @Override
    public void getFlashFail(int status, String desc) {
        initTimer(0);
        RingLog.e(TAG, "FlashActivity getFlashFail() status = " + status + "---desc = " + desc);
        RingToast.show("FlashActivity getFlashFail() status = " + status + "---desc = " + desc);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_flash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //使用Dagger2对本类中相关变量进行初始化
        //使用Dagger2对本类中相关变量进行初始化
        DaggerFlashActivityCommponent.builder().flashActivityModule(new FlashActivityModule(this)).build().inject(this);
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        SystemUtil.hideBottomUIMenu(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (DevRing.cacheManager().spCache().getBoolean("guide", false)) {
            mPresenter.startPageConfig();
        } else {
            initTimer(0);
        }
    }

    private void initTimer(final int flag) {
        CountdownUtil.getInstance().newTimer(3000, 1000, new CountdownUtil.ICountDown() {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (DevRing.cacheManager().spCache().getBoolean("guide", false)) {
                    if (flag == 1) {
                        goNext(StartPageActivity.class);
                    } else {
                        goNext(MainActivity.class);
                    }
                } else {
                    goNext(GuideActivity.class);
                }
            }
        }, "FLASH_TIMER");
    }

    private void goNext(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("img_url", imgUrl);
        intent.putExtra("jump_url", jumpUrl);
        intent.putExtra("backup", backup);
        intent.putExtra("point", point);
        startActivity(intent);
        finish();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountdownUtil.getInstance().cancel("FLASH_TIMER");
    }

    @Override
    protected void initEvent() {

    }
}
