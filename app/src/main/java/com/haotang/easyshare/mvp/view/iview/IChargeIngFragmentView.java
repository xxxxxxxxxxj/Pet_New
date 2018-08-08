package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.ChargeingState;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:42
 */
public interface IChargeIngFragmentView extends IBaseView {

    void ingSuccess(StartChargeing.DataBean data);

    void ingFail(int code, String msg);

    void stateSuccess(ChargeingState.DataBean data);

    void stateFail(int code, String msg);

    void stopFail(int errType, String errMessage);

    void stopSuccess(ChargeingState.DataBean data);

    void billSuccess(ChargeingState.DataBean data);

    void billFail(int code, String msg);
}
