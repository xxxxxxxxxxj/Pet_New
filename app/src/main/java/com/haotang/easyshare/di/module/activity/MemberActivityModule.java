package com.haotang.easyshare.di.module.activity;

import android.content.Context;

import com.haotang.easyshare.mvp.model.MemberModel;
import com.haotang.easyshare.mvp.model.imodel.IMemberModel;
import com.haotang.easyshare.mvp.presenter.MemberPresenter;
import com.haotang.easyshare.mvp.view.iview.IMemberView;
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
 * @date XJ on 2018/5/7 17:58
 */
@Module
public class MemberActivityModule {
    private Context mContext;
    private IMemberView mIMemberView;

    public MemberActivityModule(IMemberView iMemberView, Context context) {
        mIMemberView = iMemberView;
        mContext = context;
    }

    @Provides
    @ActivityScope
    IMemberView iMemberView() {
        return mIMemberView;
    }

    @Provides
    @ActivityScope
    Context context() {
        return mContext;
    }

    @Provides
    @ActivityScope
    IMemberModel iMemberModel() {
        return new MemberModel();
    }

    @Provides
    @ActivityScope
    PermissionDialog permissionDialog(Context context) {
        return new PermissionDialog(context);
    }

    @Provides
    @ActivityScope
    MemberPresenter MemberPresenter(IMemberView iMemberView, IMemberModel iMemberModel) {
        return new MemberPresenter(iMemberView, iMemberModel);
    }
}
