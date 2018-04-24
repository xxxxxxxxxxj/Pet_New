package com.haotang.easyshare.mvp.model.imodel;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;
import com.tencent.mm.sdk.openapi.BaseResp;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/18 15:20
 */
public interface IWXPayEntryModel extends IBaseModel {
    void deleteFromMyCollect(BaseResp resp);
}
