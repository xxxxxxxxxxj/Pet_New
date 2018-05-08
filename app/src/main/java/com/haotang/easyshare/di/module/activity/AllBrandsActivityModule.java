package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.AllBrandsModel;
import com.haotang.easyshare.mvp.model.imodel.IAllBrandsModel;
import com.haotang.easyshare.mvp.presenter.AllBrandsPresenter;
import com.haotang.easyshare.mvp.view.iview.IAllBrandsView;
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
 * @date XJ on 2018/5/8 16:45
 */
@Module
public class AllBrandsActivityModule {
    private Context mContext;
    private IAllBrandsView mIAllBrandsView;

    public AllBrandsActivityModule(IAllBrandsView iAllBrandsView, Context context) {
        mIAllBrandsView = iAllBrandsView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IAllBrandsView iAllBrandsView() {
        return mIAllBrandsView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IAllBrandsModel iAllBrandsModel() {
        return new AllBrandsModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    AllBrandsPresenter AllBrandsPresenter(IAllBrandsView iAllBrandsView, IAllBrandsModel iAllBrandsModel) {
        return new AllBrandsPresenter(iAllBrandsView, iAllBrandsModel);
    }
}
