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
 * @date zhoujunxia on 2019/1/9 18:30
 */
public interface IHotPostFragmentModel extends IBaseModel{
    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    Observable list(Map<String, String> headers, RequestBody body);

    /**
     * 最新帖子列表
     */
    Observable newest(Map<String, String> headers,RequestBody body);

    /**
     * 热门帖子列表
     */
    Observable hot(Map<String, String> headers,RequestBody body);

    /**
     * 问题车帖子列表
     */
    Observable problemCar(Map<String, String> headers,RequestBody body);
}