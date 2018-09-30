package com.haotang.easyshare.mvp.model.imodel;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;

import java.util.Map;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:37
 */
public interface ICommentDetailModel extends IBaseModel{
    /**
     * 充电桩评论列表
     * @param uuid
     * @param mNextRequestPage
     */
    Observable list(Map<String, String> headers, String uuid, int mNextRequestPage);
}
