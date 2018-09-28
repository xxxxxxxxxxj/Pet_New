package com.haotang.easyshare.mvp.view.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.haotang.easyshare.mvp.model.entity.res.ChargeingState;
import com.haotang.easyshare.mvp.model.http.ChargeingFragmentApiService;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.other.RingLog;

import okhttp3.MultipartBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/8/8 17:12
 */
public class ChargeStateService extends Service {

    public static final String ACTION = "com.haotang.easyshare.mvp.view.services.ChargeStateService";
    private int orderId;

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
        orderId = intent.getIntExtra("orderId", 0);
        new PollingThread().start();
    }

    class PollingThread extends Thread {
        @Override
        public void run() {
            MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                    .addFormDataPart("orderId", orderId + "")
                    .build();
            DevRing.httpManager().commonRequest(DevRing.httpManager().getService(ChargeingFragmentApiService.class).state(body)
                    , new CommonObserver<ChargeingState>() {
                        @Override
                        public void onResult(ChargeingState result) {
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