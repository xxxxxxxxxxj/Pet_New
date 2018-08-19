package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.ReFundExplain;
import com.haotang.easyshare.mvp.model.entity.res.ReFundSubmit;
import com.haotang.easyshare.mvp.model.entity.res.ReFundTag;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 14:12
 */
public interface IRefundView extends IBaseView{
    void listSuccess(List<ReFundTag.DataBean> data);

    void listFail(int serverError, String s);

    void explainFail(int errType, String errMessage);

    void explainSuccess(ReFundExplain.DataBean data);

    void refundSuccess(ReFundSubmit.DataBean data);

    void refundFail(int serverError, String s);
}
