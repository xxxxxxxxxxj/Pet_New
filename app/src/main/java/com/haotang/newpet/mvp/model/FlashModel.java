package com.haotang.newpet.mvp.model;

import com.haotang.newpet.app.constant.UrlConstants;
import com.haotang.newpet.mvp.model.http.FlashApiService;
import com.haotang.newpet.mvp.model.imodel.IFlashModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 11:39
 */
public class FlashModel implements IFlashModel {
    /**
     * 获取广告页数据
     */
    @Override
    public Observable startPageConfig() {
        return DevRing.httpManager().getService(FlashApiService.class).startPageConfig(UrlConstants.GET_FLASH_DATA);
    }
}
