package com.haotang.easyshare.mvp.model.http;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/14 19:56
 */
public interface HotFragmentApiService {
    /**
     * 广告
     * 广告类别(1:首页活动弹窗、2:热点首页顶部广告、3:车型专区首页顶部广告、4:车型专区首页中间广告)
     *
     * @param body
     */
    @POST(UrlConstants.ADVERTISEMENT)
    Observable<AdvertisementBean> list(@Body() RequestBody body);

    /**
     * 热门品牌
     */
    @POST(UrlConstants.HOT_CAR_BRAND)
    Observable<HotCarBean> hot();

    /**
     * 最新帖子列表
     */
    @POST(UrlConstants.NEWEST_POINT)
    Observable<HotPoint> newest(@Body() RequestBody body);
}
