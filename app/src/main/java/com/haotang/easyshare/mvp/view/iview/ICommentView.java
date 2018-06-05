package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:31
 */
public interface ICommentView extends IBaseView{
    void saveSuccess(AddChargeBean data);

    void saveFail(int code, String msg);

    void tagsSuccess(List<String> data);

    void tagsFail(int serverError, String s);
}
