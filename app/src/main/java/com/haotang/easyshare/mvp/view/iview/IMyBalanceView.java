package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/23 17:44
 */
public interface IMyBalanceView extends IBaseView{
    void homeSuccess(HomeBean data);

    void homeFail(int code, String msg);
}
