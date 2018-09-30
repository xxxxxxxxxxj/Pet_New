package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.CurrentMessageApiService;
import com.haotang.easyshare.mvp.model.imodel.ICurrentMessageFragmentModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 18:24
 */
public class CurrentMessageFragmentModel implements ICurrentMessageFragmentModel {
    /**
     * 发布留言
     */
    @Override
    public Observable save(Map<String, String> headers, int contentType, String content) {
        return DevRing.httpManager().getService(CurrentMessageApiService.class).save(headers,contentType,content);
    }
}
