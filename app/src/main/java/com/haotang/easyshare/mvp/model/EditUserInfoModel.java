package com.haotang.easyshare.mvp.model;

import com.haotang.easyshare.mvp.model.http.EditUserInfoApiService;
import com.haotang.easyshare.mvp.model.http.MyFragmentApiService;
import com.haotang.easyshare.mvp.model.imodel.IEditUserInfoModel;
import com.ljy.devring.DevRing;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/23 18:56
 */
public class EditUserInfoModel implements IEditUserInfoModel {
    /**
     * 用户主页信息
     */
    @Override
    public Observable home() {
        return DevRing.httpManager().getService(MyFragmentApiService.class).home();
    }

    /**
     * 上传用户信息
     *
     * @param body
     */
    @Override
    public Observable save(RequestBody body) {
        return DevRing.httpManager().getService(EditUserInfoApiService.class).save(body);
    }
}
