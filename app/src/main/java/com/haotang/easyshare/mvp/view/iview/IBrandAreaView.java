package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 16:48
 */
public interface IBrandAreaView extends IBaseView {
    void articleSuccess(List<HotPoint.DataBean> data);

    void articleFail(int code, String msg);

    void listSuccess(List<AdvertisementBean.DataBean> data);

    void listFail(int serverError, String s);

    void list1Success(List<AdvertisementBean.DataBean> data);

    void list1Fail(int serverError, String s);
}
