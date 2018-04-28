package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.ButlerModel;
import com.haotang.easyshare.mvp.model.imodel.IButlerModel;
import com.haotang.easyshare.mvp.model.imodel.IMainModel;
import com.haotang.easyshare.mvp.presenter.ButlerPresenter;
import com.haotang.easyshare.mvp.presenter.MainPresenter;
import com.haotang.easyshare.mvp.view.fragment.CurrentMessageFragment;
import com.haotang.easyshare.mvp.view.fragment.HistoricalMessageFragment;
import com.haotang.easyshare.mvp.view.iview.IButlerView;
import com.haotang.easyshare.mvp.view.iview.IMainView;
import com.ljy.devring.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 15:45
 */
@Module
public class ButlerActivityModule {
    private Context mContext;
    private IButlerView mIButlerView;

    public ButlerActivityModule(IButlerView iButlerView, Context context) {
        mIButlerView = iButlerView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IButlerView iButlerView() {
        return mIButlerView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IButlerModel iButlerModel() {
        return new ButlerModel();
    }


    @Provides
    @ActivityScope
    ButlerPresenter ButlerPresenter(IButlerView iButlerView, IButlerModel iButlerModel) {
        return new ButlerPresenter(iButlerView, iButlerModel);
    }

    @Provides
    @ActivityScope
    CurrentMessageFragment currentMessageFragment() {
        return new CurrentMessageFragment();
    }

    @Provides
    @ActivityScope
    HistoricalMessageFragment historicalMessageFragment() {
        return new HistoricalMessageFragment();
    }
}
