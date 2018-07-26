package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.InputChargeCodeModel;
import com.haotang.easyshare.mvp.model.imodel.IInputChargeCodeModel;
import com.haotang.easyshare.mvp.presenter.InputChargeCodePresenter;
import com.haotang.easyshare.mvp.view.iview.IInputChargeCodeView;
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
 * @date XJ on 2018/7/26 09:50
 */
@Module
public class InputChargeCodeActivityModule {
    private Context mContext;
    private IInputChargeCodeView mIInputChargeCodeView;

    public InputChargeCodeActivityModule(IInputChargeCodeView iInputChargeCodeView, Context context) {
        mIInputChargeCodeView = iInputChargeCodeView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IInputChargeCodeView iInputChargeCodeView() {
        return mIInputChargeCodeView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IInputChargeCodeModel iInputChargeCodeModel() {
        return new InputChargeCodeModel();
    }

    @Provides
    @ActivityScope
    InputChargeCodePresenter InputChargeCodePresenter(IInputChargeCodeView iInputChargeCodeView, IInputChargeCodeModel iInputChargeCodeModel) {
        return new InputChargeCodePresenter(iInputChargeCodeView, iInputChargeCodeModel);
    }
}
