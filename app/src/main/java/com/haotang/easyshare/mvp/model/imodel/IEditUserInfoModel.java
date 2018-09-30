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
 * @date XJ on 2018/5/23 18:53
 */
public interface IEditUserInfoModel extends IBaseModel {
    /**
     * 用户主页信息
     */
    Observable home(Map<String, String> headers);

    /**
     * 上传用户信息
     *
     * @param body
     */
    Observable save(Map<String, String> headers,RequestBody body);
}
