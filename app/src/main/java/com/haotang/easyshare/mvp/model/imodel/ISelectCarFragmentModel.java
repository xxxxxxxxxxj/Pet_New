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
 * @date XJ on 2018/7/20 14:49
 */
public interface ISelectCarFragmentModel extends IBaseModel {
    /**
     * 热门品牌
     */
    Observable hot(Map<String, String> headers);

    /**
     * 热门车型
     */
    Observable special(Map<String, String> headers);

    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    Observable list(Map<String, String> headers,RequestBody body);

    /**
     * 选车首页
     * @param headers
     * @param body
     */
    Observable carType(Map<String, String> headers, RequestBody body);
}
