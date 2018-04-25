package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.MainFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.IMainFragmentModel;
import com.haotang.easyshare.mvp.presenter.MainFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.IMainFragmentView;
import com.ljy.devring.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 18:26
 */
@Module
public class MainFragmentModule {
    private IMainFragmentView mIMainFragmentView;
    private Context mContext;

    public MainFragmentModule(IMainFragmentView mIMainFragmentView, Context context) {
        this.mIMainFragmentView = mIMainFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    IMainFragmentView iMainFragmentView() {
        return mIMainFragmentView;
    }

    @Provides
    @FragmentScope
    IMainFragmentModel iMainFragmentModel() {
        return new MainFragmentModel();
    }

    @Provides
    @FragmentScope
    Context context() {
        return mContext;
    }

    @Provides
    @FragmentScope
    MainFragmentPresenter mainFragmentPresenter(IMainFragmentView IMainFragmentView, IMainFragmentModel iMainFragmentModel) {
        return new MainFragmentPresenter(IMainFragmentView, iMainFragmentModel);
    }
}
