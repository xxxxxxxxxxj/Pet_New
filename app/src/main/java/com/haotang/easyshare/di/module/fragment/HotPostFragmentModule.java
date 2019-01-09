package com.haotang.easyshare.di.module.fragment;

import android.content.Context;

import com.haotang.easyshare.mvp.model.HotPostFragmentModel;
import com.haotang.easyshare.mvp.model.imodel.IHotPostFragmentModel;
import com.haotang.easyshare.mvp.presenter.HotPostFragmentPresenter;
import com.haotang.easyshare.mvp.view.iview.IHotPostFragmentView;
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
 * @date zhoujunxia on 2019/1/9 18:34
 */
@Module
public class HotPostFragmentModule {
    private IHotPostFragmentView mIHotPostFragmentView;
    private Context mContext;

    public HotPostFragmentModule(IHotPostFragmentView mIHotPostFragmentView, Context context) {
        this.mIHotPostFragmentView = mIHotPostFragmentView;
        mContext = context;
    }

    @Provides
    @FragmentScope
    IHotPostFragmentView iHotPostFragmentView() {
        return mIHotPostFragmentView;
    }

    @Provides
    @FragmentScope
    IHotPostFragmentModel iHotPostFragmentModel() {
        return new HotPostFragmentModel();
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
    HotPostFragmentPresenter HotPostFragmentPresenter(IHotPostFragmentView IHotPostFragmentView, IHotPostFragmentModel iHotPostFragmentModel) {
        return new HotPostFragmentPresenter(IHotPostFragmentView, iHotPostFragmentModel);
    }
}
