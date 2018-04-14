package com.haotang.newpet.di.module.activity;

import android.content.Context;

import com.haotang.newpet.mvp.model.MainModel;
import com.haotang.newpet.mvp.model.imodel.IMainModel;
import com.haotang.newpet.mvp.presenter.MainPresenter;
import com.haotang.newpet.mvp.view.fragment.MainFragment;
import com.haotang.newpet.mvp.view.fragment.MyFragment;
import com.haotang.newpet.mvp.view.fragment.PetCircleFragment;
import com.haotang.newpet.mvp.view.fragment.ShopMarketFragment;
import com.haotang.newpet.mvp.view.iview.IMainView;
import com.haotang.newpet.mvp.view.widget.PermissionDialog;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 17:23
 */
@Module
public class MainActivityModule {
    private Context mContext;
    private IMainView mIMainView;

    public MainActivityModule(IMainView iMainView, Context context) {
        mIMainView = iMainView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IMainView iMainView() {
        return mIMainView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IMainModel iMainModel() {
        return new MainModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    MainPresenter MainPresenter(IMainView iMainView, IMainModel iMainModel) {
        return new MainPresenter(iMainView, iMainModel);
    }

    @Provides
    @ActivityScope
    MainFragment mainFragment() {
        return new MainFragment();
    }

    @Provides
    @ActivityScope
    ShopMarketFragment shopMarketFragment() {
        return new ShopMarketFragment();
    }

    @Provides
    @ActivityScope
    PetCircleFragment petCircleFragment() {
        return new PetCircleFragment();
    }

    @Provides
    @ActivityScope
    MyFragment myFragment() {
        return new MyFragment();
    }
}
