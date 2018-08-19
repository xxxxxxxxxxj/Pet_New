package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.HistoryList;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 12:05
 */
public interface IRechargeFragmentView extends IBaseView{
    void listSuccess(HistoryList.DataBean data);

    void listFail(int code, String msg);
}
