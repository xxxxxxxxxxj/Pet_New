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
 * @date XJ on 2018/5/7 18:04
 */
public interface IMyPostModel extends IBaseModel {
    /**
     * 用户帖子列表
     */
    Observable list(Map<String, String> headers, RequestBody body);

    /**
     * 删除帖子
     */
    Observable delete(Map<String, String> headers,RequestBody body);
}
