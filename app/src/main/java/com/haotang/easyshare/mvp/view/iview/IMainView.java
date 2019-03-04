package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.BootmBarBean;
import com.haotang.easyshare.mvp.model.entity.res.LastVersionBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 14:49
 */
public interface IMainView extends IBaseView {
    void getLatestVersionSuccess(LastVersionBean lastVersionBean);

    void getLatestVersionFail(int status, String desc);

    void getBootmBarFail(int status, String desc);

    void getBootmBarSuccess(BootmBarBean bootmBarBean);

    void listSuccess(List<AdvertisementBean.DataBean> data);

    void listFail(int code, String msg);
}
