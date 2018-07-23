package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.MyBalanceModel;
import com.haotang.easyshare.mvp.model.imodel.IMyBalanceModel;
import com.haotang.easyshare.mvp.presenter.MyBalancePresenter;
import com.haotang.easyshare.mvp.view.iview.IMyBalanceView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 17:45
 */
@Module
public class MyBalanceActivityModule {
    private Context mContext;
    private IMyBalanceView mIMyBalanceView;

    public MyBalanceActivityModule(IMyBalanceView iMyBalanceView, Context context) {
        mIMyBalanceView = iMyBalanceView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IMyBalanceView iMyBalanceView() {
        return mIMyBalanceView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IMyBalanceModel iMyBalanceModel() {
        return new MyBalanceModel();
    }

    @Provides
    @ActivityScope
    MyBalancePresenter MyBalancePresenter(IMyBalanceView iMyBalanceView, IMyBalanceModel iMyBalanceModel) {
        return new MyBalancePresenter(iMyBalanceView, iMyBalanceModel);
    }
}
