package com.haotang.easyshare.mvp.presenter.base;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import okhttp3.RequestBody;

/**
 * author:  ljy
 * date：   2018/3/18
 * description: Presenter基类
 * <a>https://www.jianshu.com/p/1f91cfd68d48</a>
 */
public abstract class BasePresenter<V extends IBaseView,M extends IBaseModel>{

    protected V mIView;
    protected M mIModel;

    public BasePresenter(V iView,M iModel) {
        mIView = iView;
        mIModel = iModel;
    }

    public BasePresenter(V iView) {
        mIView = iView;
    }

    public BasePresenter() {
    }

    /**
     * 释放引用，防止内存泄露
     */
    public void destroy() {
        mIView = null;
    }
}
