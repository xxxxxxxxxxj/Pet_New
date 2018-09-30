package com.haotang.easyshare.mvp.model.imodel;

import com.haotang.easyshare.mvp.model.imodel.base.IBaseModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:32
 */
public interface ICommentModel extends IBaseModel {
    /**
     * 评论充电桩
     *
     * @param filedMap
     */
    Observable save(Map<String, String> headers,Map<String, RequestBody> filedMap);

    /**
     * 评论标签
     */
    Observable tags(Map<String, String> headers);
}
