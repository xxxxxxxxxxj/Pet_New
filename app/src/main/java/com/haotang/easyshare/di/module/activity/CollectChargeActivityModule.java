package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.CollectChargeModel;
import com.haotang.easyshare.mvp.model.imodel.ICollectChargeModel;
import com.haotang.easyshare.mvp.presenter.CollectChargePresenter;
import com.haotang.easyshare.mvp.view.iview.ICollectChargeView;
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
 * @date XJ on 2018/5/7 17:28
 */
@Module
public class CollectChargeActivityModule {
    private Context mContext;
    private ICollectChargeView mICollectChargeView;

    public CollectChargeActivityModule(ICollectChargeView iCollectChargeView, Context context) {
        mICollectChargeView = iCollectChargeView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    ICollectChargeView iCollectChargeView() {
        return mICollectChargeView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    ICollectChargeModel iCollectChargeModel() {
        return new CollectChargeModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    CollectChargePresenter CollectChargePresenter(ICollectChargeView iCollectChargeView, ICollectChargeModel iCollectChargeModel) {
        return new CollectChargePresenter(iCollectChargeView, iCollectChargeModel);
    }
}
