package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.ChargeDetailBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:17
 */
public interface IChargingPileDetailView extends IBaseView{

    void detailSuccess(ChargeDetailBean data);

    void detailFail(int code, String msg);

    void followSuccess(AddChargeBean data);

    void followFail(int code, String msg);

    void cancelSuccess(AddChargeBean data);

    void cancelFail(int code, String msg);
}
