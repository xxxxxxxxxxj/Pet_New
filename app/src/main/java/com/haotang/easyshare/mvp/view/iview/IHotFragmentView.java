package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.SerchKeysBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:30
 */
public interface IHotFragmentView extends IBaseView{
    void listFail(int code, String msg);

    void listSuccess(List<AdvertisementBean.DataBean> data);

    void hotSuccess(List<HotCarBean.DataBean> data);

    void hotFail(int code, String msg);

    void newestSuccess(List<HotPoint.DataBean> data);

    void newestFail(int code, String msg);

    void keysSuccess(List<SerchKeysBean.DataBean> data);

    void keysFail(int serverError, String s);
}
