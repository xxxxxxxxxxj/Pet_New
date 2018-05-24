package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/23 18:52
 */
public interface IEditUserInfoView extends IBaseView{
    void homeSuccess(HomeBean data);

    void homeFail(int code, String msg);

    void saveSuccess(AddChargeBean data);

    void saveFail(int serverError, String s);
}
