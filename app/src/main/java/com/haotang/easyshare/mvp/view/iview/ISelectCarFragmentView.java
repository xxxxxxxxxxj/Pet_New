package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.CarType;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:48
 */
public interface ISelectCarFragmentView extends IBaseView {
    void hotSuccess(List<HotCarBean.DataBean> data);

    void hotFail(int code, String msg);

    void specialSuccess(List<HotSpecialCarBean.DataBean> data);

    void specialFail(int code, String msg);

    void listFail(int errType, String errMessage);

    void listSuccess(List<AdvertisementBean.DataBean> data);

    void carTypeSuccess(List<CarType.DataBean> data);

    void carTypeFail(int code, String msg);
}
