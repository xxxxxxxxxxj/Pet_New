package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.RechargePayInfo;
import com.haotang.easyshare.mvp.model.entity.res.RechargeTemp;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 16:46
 */
public interface IRechargeView extends IBaseView {
    void listFail(int serverError, String s);

    void listSuccess(List<RechargeTemp.DataBean> data);

    void buildSuccess(RechargePayInfo.DataBean data);

    void buildFail(int code, String msg);
}
