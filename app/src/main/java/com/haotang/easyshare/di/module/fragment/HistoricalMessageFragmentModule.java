package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.HistoricalMessageFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.IHistoricalMessageFragmentModel;
import com.haotang.easyshare.mvp.presenter.HistoricalMessageFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.IHistoricalMessageFragmentView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:27
 */
@Module
public class HistoricalMessageFragmentModule {
    private IHistoricalMessageFragmentView mIHistoricalMessageFragmentView;
    private Context mContext;

    public HistoricalMessageFragmentModule(IHistoricalMessageFragmentView mIHistoricalMessageFragmentView, Context context) {
        this.mIHistoricalMessageFragmentView = mIHistoricalMessageFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    IHistoricalMessageFragmentView iHistoricalMessageFragmentView() {
        return mIHistoricalMessageFragmentView;
    }

    @Provides
    @FragmentScope
    IHistoricalMessageFragmentModel iHistoricalMessageFragmentModel() {
        return new HistoricalMessageFragmentModel();
    }

    @Provides
    @FragmentScope
    Context context() {
        return mContext;
    }

    @Provides
    @FragmentScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @FragmentScope
    HistoricalMessageFragmentPresenter HistoricalMessageFragmentPresenter(IHistoricalMessageFragmentView IHistoricalMessageFragmentView, IHistoricalMessageFragmentModel iHistoricalMessageFragmentModel) {
        return new HistoricalMessageFragmentPresenter(IHistoricalMessageFragmentView, iHistoricalMessageFragmentModel);
    }
}
