package com.haotang.easyshare.mvp.view.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.model.http.ChargeingFragmentApiService;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.other.RingLog;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/27 18:17
 */
public class ChargeingService extends Service {

    public static final String ACTION = "com.haotang.easyshare.mvp.view.services.ChargeingService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        new PollingThread().start();
    }

    class PollingThread extends Thread {
        @Override
        public void run() {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (SharedPreferenceUtil.getInstance(getApplicationContext()).getBoolean("is_open", false)) {
                builder.addFormDataPart("reset", "1");
            } else {
                builder.addFormDataPart("reset", "0");
            }
            RequestBody build = builder.build();
            DevRing.httpManager().commonRequest(DevRing.httpManager().getService(ChargeingFragmentApiService.class).ing(UrlConstants.getMapHeader(getApplicationContext()), build)
                    , new CommonObserver<StartChargeing>() {
                        @Override
                        public void onResult(StartChargeing result) {
                            if (result != null) {
                                DevRing.busManager().postEvent(result);
                            }
                        }

                        @Override
                        public void onError(int errType, String errMessage) {
                            RingLog.e("onError() status = " + errType + "---desc = " + errMessage);
                        }
                    }, null);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}