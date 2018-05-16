package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.MyCarBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:33
 */
public interface IMyFragmentView extends IBaseView {
    void homeSuccess(HomeBean data);

    void homeFail(int code, String msg);

    void mySuccess(List<MyCarBean.DataBean> data);

    void myFail(int code, String msg);
}
