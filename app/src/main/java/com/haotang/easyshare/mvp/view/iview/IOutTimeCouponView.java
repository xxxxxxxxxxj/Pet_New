package com.haotang.easyshare.mvp.view.iview;

import com.haotang.easyshare.mvp.model.entity.res.MyCoupon;
import com.haotang.easyshare.mvp.view.iview.base.IBaseView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/21 16:47
 */
public interface IOutTimeCouponView extends IBaseView{
    void listSuccess(MyCoupon.DataBean data);

    void listFail(int code, String msg);
}
