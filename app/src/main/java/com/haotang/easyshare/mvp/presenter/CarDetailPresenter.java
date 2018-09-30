package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.CarDetail;
import com.haotang.easyshare.mvp.model.imodel.ICarDetailModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ICarDetailView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 12:54
 */
public class CarDetailPresenter extends BasePresenter<ICarDetailView, ICarDetailModel> {
    public CarDetailPresenter(ICarDetailView iCarDetailView, ICarDetailModel iCarDetailModel) {
        super(iCarDetailView, iCarDetailModel);
    }

    /**
     * 车型详情
     *
     * @param body
     */
    public void detail(Map<String, String> headers, RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.detail(headers,body), new CommonObserver<CarDetail>() {
            @Override
            public void onResult(CarDetail result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.detailSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.detailFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.detailFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.detailFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}