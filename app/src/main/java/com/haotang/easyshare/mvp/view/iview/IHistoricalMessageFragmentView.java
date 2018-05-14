package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.HistoricalMsg;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:25
 */
public interface IHistoricalMessageFragmentView extends IBaseView {
    void historySuccess(List<List<HistoricalMsg.DataBean>> data);

    void historyFail(int code, String msg);
}
