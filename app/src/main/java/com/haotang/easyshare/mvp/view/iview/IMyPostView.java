package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:04
 */
public interface IMyPostView extends IBaseView{
    void listSuccess(List<PostBean.DataBean> data);

    void listFail(int code, String msg);

    void deleteSuccess(AddChargeBean data);

    void deleteFail(int code, String msg);
}
