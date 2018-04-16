package com.haotang.deving.mvp.view.iview;

import com.haotang.deving.mvp.model.entity.res.FlashBean;
import com.haotang.deving.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 18:09
 */
public interface IFlashView extends IBaseView {
    void getFlashSuccess(FlashBean flashBean);

    void getFlashFail(int status, String desc);
}
