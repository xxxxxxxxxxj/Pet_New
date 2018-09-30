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
 * @date XJ on 2018/5/8 16:52
 */
public interface IPostListModel extends IBaseModel {
    /**
     * 最新帖子列表
     */
    Observable newest(Map<String, String> headers, RequestBody body);

    /**
     * 热门帖子列表
     */
    Observable hot(Map<String, String> headers,RequestBody body);

    /**
     * 问题车帖子列表
     */
    Observable problemCar(Map<String, String> headers,RequestBody body);
}
