package com.haotang.easyshare.mvp.model.imodel;

import android.app.Activity;

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
 * @date XJ on 2018/4/11 18:39
 */
public interface IFlashModel extends IBaseModel {
    Observable startPageConfig(Map<String, String> headers, Activity activity);

    Observable openAppCallback(Map<String, String> headers, RequestBody body);
}
