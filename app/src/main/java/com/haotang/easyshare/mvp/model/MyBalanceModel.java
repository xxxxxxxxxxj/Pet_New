package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.MyFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.IMyBalanceModel;
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
 * @date XJ on 2018/7/23 17:46
 */
public class MyBalanceModel implements IMyBalanceModel {
    /**
     * 用户主页信息
     */
    @Override
    public Observable home(Map<String, String> headers) {
        return DevRing.httpManager().getService(MyFragmentApiService.class).home(headers);
    }

    /**
     * 兑换码
     */
    @Override
    public Observable use(Map<String, String> headers, RequestBody build) {
        return DevRing.httpManager().getService(MyFragmentApiService.class).use(headers,build);
    }
}
