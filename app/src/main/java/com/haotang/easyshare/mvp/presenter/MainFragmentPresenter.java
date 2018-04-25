package com.haotang.easyshare.mvp.presenter;

import android.app.Activity;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.FlashBean;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.imodel.IMainFragmentModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IMainFragmentView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 18:07
 */
public class MainFragmentPresenter extends BasePresenter<IMainFragmentView, IMainFragmentModel> {
    public MainFragmentPresenter(IMainFragmentView iMainFragmentView, IMainFragmentModel iMainFragmentModel) {
        super(iMainFragmentView, iMainFragmentModel);
    }

    /**
     * 获取广告页数据
     */
    public void getMainFragData(Activity activity) {
        DevRing.httpManager().commonRequest(mIModel.getMainFragData(activity), new CommonObserver<HttpResult<List<MainFragmentData>>>() {
            @Override
            public void onResult(HttpResult<List<MainFragmentData>> result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.getMainFragmentSuccess(result.getData());
                        } else {
                            if (!StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.getMainFragmentFail(AppConfig.SERVER_ERROR, result.getMsg());
                            } else {
                                mIView.getMainFragmentFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.getMainFragmentFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
