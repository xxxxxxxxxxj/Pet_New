package com.haotang.newpet.mvp.view.iview;

import com.haotang.newpet.mvp.model.entity.res.BootmBarBean;
import com.haotang.newpet.mvp.model.entity.res.LastVersionBean;
import com.haotang.newpet.mvp.view.iview.base.IBaseView;

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
}
