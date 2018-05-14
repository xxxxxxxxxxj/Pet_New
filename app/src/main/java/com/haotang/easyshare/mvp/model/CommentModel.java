package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.CommentApiService;
import com.haotang.easyshare.mvp.model.imodel.ICommentModel;
import com.ljy.devring.DevRing;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/7 17:35
 */
public class CommentModel implements ICommentModel {
    /**
     * 评论充电桩
     *
     * @param filedMap
     */
    @Override
    public Observable save(Map<String, RequestBody> filedMap) {
        return DevRing.httpManager().getService(CommentApiService.class).save(filedMap);
    }
}
