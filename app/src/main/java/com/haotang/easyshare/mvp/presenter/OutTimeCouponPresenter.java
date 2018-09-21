package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.MyCoupon;
import com.haotang.easyshare.mvp.model.imodel.IOutTimeCouponModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.IOutTimeCouponView;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.util.RxLifecycleUtil;

import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/21 16:47
 */
public class OutTimeCouponPresenter extends BasePresenter<IOutTimeCouponView, IOutTimeCouponModel> {
    public OutTimeCouponPresenter(IOutTimeCouponView iOutTimeCouponView, IOutTimeCouponModel iOutTimeCouponModel) {
        super(iOutTimeCouponView, iOutTimeCouponModel);
    }

    /**
     * 过期优惠券列表
     *
     * @param body
     */
    public void list(RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.list(body), new CommonObserver<MyCoupon>() {
            @Override
            public void onResult(MyCoupon result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.listSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.listFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.listFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.listFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }
}
