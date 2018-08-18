package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.entity.res.ScreenCarItem;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 10:34
 */
public interface IScreenCarView extends IBaseView {
    void itemsSuccess(ScreenCarItem.DataBean data);

    void itemsFail(int serverError, String s);

    void querySuccess(List<HotSpecialCarBean.DataBean> data);

    void queryFail(int code, String msg);
}
