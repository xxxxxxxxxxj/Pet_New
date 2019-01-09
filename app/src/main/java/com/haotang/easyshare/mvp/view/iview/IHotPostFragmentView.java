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
 * @date zhoujunxia on 2019/1/9 18:29
 */
public interface IHotPostFragmentView extends IBaseView{
    void listSuccess(List<AdvertisementBean.DataBean> data);

    void listFail(int code, String msg);

    void newestSuccess(List<HotPoint.DataBean> data);

    void newestFail(int code, String msg);
}
