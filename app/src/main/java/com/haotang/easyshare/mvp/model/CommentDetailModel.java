package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.CommentDetailApiService;
import com.haotang.easyshare.mvp.model.imodel.ICommentDetailModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:39
 */
public class CommentDetailModel implements ICommentDetailModel {
    /**
     * 充电桩评论列表
     * @param uuid
     * @param mNextRequestPage
     */
    @Override
    public Observable list(Map<String, String> headers, String uuid, int mNextRequestPage) {
        return DevRing.httpManager().getService(CommentDetailApiService.class).list(headers,uuid,mNextRequestPage);
    }
}
