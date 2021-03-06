package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 09:49
 */
public interface IInputChargeCodeView extends IBaseView{
    void startSuccess(StartChargeing.DataBean data);

    void startFail(int code, String msg);
}
