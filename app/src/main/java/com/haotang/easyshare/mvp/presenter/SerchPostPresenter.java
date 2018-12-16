package com.haotang.easyshare.mvp.presenter;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.SerchKeysBean;
import com.haotang.easyshare.mvp.model.imodel.ISerchPostModel;
import com.haotang.easyshare.mvp.presenter.base.BasePresenter;
import com.haotang.easyshare.mvp.view.iview.ISerchPostView;
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
 * @date zhoujunxia on 2018/12/16 10:05
 */
public class SerchPostPresenter extends BasePresenter<ISerchPostView, ISerchPostModel> {
    public SerchPostPresenter(ISerchPostView iSerchPostView, ISerchPostModel iSerchPostModel) {
        super(iSerchPostView, iSerchPostModel);
    }

    /**
     * 文章热门搜索关键字
     *
     * @param headers
     */
    public void keys(Map<String, String> headers) {
        DevRing.httpManager().commonRequest(mIModel.keys(headers), new CommonObserver<SerchKeysBean>() {
            @Override
            public void onResult(SerchKeysBean result) {
                if (mIView != null) {
                    if (result != null) {
                        if (result.getCode() == 0) {
                            mIView.keysSuccess(result.getData());
                        } else {
                            if (StringUtil.isNotEmpty(result.getMsg())) {
                                mIView.keysFail(result.getCode(), result.getMsg());
                            } else {
                                mIView.keysFail(AppConfig.SERVER_ERROR, AppConfig.SERVER_ERROR_MSG + "-code=" + result.getCode());
                            }
                        }
                    }
                }
            }

            @Override
            public void onError(int errType, String errMessage) {
                if (mIView != null) {
                    mIView.keysFail(errType, errMessage);
                }
            }
        }, RxLifecycleUtil.bindUntilDestroy(mIView));
    }

    /**
     * 搜索文章
     *
     * @param headers
     */
    public void list(Map<String, String> headers, RequestBody body) {
        DevRing.httpManager().commonRequest(mIModel.list(headers, body), new CommonObserver<HotPoint>() {
            @Override
            public void onResult(HotPoint result) {
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