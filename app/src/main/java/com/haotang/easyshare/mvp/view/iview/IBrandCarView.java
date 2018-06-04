package com.haotang.easyshare.mvp.view.iview;

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
 * @date XJ on 2018/6/4 18:12
 */
public interface IBrandCarView extends IBaseView{
    void listSuccess(List<HotCarBean.DataBean> data);

    void listFail(int code, String msg);

    void carListSuccess(List<HotSpecialCarBean.DataBean> data);

    void carListFail(int code, String msg);
}
