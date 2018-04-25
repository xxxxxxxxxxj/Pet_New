package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 18:06
 */
public interface IMainFragmentView extends IBaseView {
    void getMainFragmentSuccess(List<MainFragmentData> MainFragmentData);

    void getMainFragmentFail(int status, String desc);
}