package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:41
 */
public interface IFollowDetailView extends IBaseView{
    void infoSuccess(HomeBean data);

    void infoFail(int code, String msg);

    void listSuccess(List<PostBean.DataBean> data);

    void listFail(int serverError, String s);

    void followSuccess(AddChargeBean data);

    void followFail(int code, String msg);

    void cancelSuccess(AddChargeBean data);

    void cancelFail(int code, String msg);

    void evalSuccess(AddChargeBean data);

    void evalFail(int errType, String errMessage);

    void praiseSuccess(AddChargeBean data);

    void praiseFail(int code, String msg);

    void starsSuccess(List<String> data);

    void starsFail(int code, String msg);
}
