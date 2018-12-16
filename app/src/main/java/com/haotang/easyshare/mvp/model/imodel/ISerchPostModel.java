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
 * @date zhoujunxia on 2018/12/16 10:06
 */
public interface ISerchPostModel extends IBaseModel {
    /**
     * 文章热门搜索关键字
     *
     * @param headers
     */
    Observable keys(Map<String, String> headers);

    /**
     * 搜索文章
     *
     * @param headers
     */
    Observable list(Map<String, String> headers, RequestBody body);
}
