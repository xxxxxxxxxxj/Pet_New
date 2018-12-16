package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.SerchKeysBean;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/16 10:06
 */
public interface ISerchPostView extends IBaseView{
    void keysSuccess(List<SerchKeysBean.DataBean> data);

    void keysFail(int code, String msg);

    void listSuccess(List<HotPoint.DataBean> data);

    void listFail(int code, String msg);
}
