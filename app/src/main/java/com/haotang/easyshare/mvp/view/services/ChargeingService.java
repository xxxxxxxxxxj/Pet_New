package com.haotang.easyshare.mvp.view.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.model.http.ChargeingFragmentApiService;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.other.RingLog;

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
            DevRing.httpManager().commonRequest(DevRing.httpManager().getService(ChargeingFragmentApiService.class).ing()
                    , new CommonObserver<StartChargeing>() {
                        @Override
                        public void onResult(StartChargeing result) {
                            if (result != null) {
                                if (result.getCode() == 0) {
                                    DevRing.busManager().postEvent(result.getData());
                                } else {
                                    SystemUtil.Exit(ChargeingService.this, result.getCode());
                                    if (StringUtil.isNotEmpty(result.getMsg())) {
                                        RingLog.e("onError() status = " + result.getCode() + "---desc = " + result.getMsg());
                                    } else {
                                        RingLog.e("onError() status = " + AppConfig.SERVER_ERROR + "---desc = " + AppConfig.SERVER_ERROR_MSG);
                                    }
                                }
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