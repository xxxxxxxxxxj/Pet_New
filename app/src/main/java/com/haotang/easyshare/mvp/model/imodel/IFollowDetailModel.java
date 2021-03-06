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
 * @date XJ on 2018/5/7 17:42
 */
public interface IFollowDetailModel extends IBaseModel {
    /**
     * 用户信息
     *
     * @param uuid
     */
    Observable info(Map<String, String> headers, String uuid);

    /**
     * 用户帖子列表
     */
    Observable list(Map<String, String> headers,RequestBody body);

    /**
     * 关注用户
     *
     * @param build
     */
    Observable follow(Map<String, String> headers,RequestBody build);

    /**
     * 取消关注用户
     *
     * @param build
     */
    Observable cancel(Map<String, String> headers,RequestBody build);

    /**
     * 评价用户
     */
    Observable eval(Map<String, String> headers,RequestBody body);

    /**
     * 点赞
     */
    Observable praise(Map<String, String> headers,RequestBody body);

    /**
     * 评价星级
     */
    Observable stars(Map<String, String> headers);
}
