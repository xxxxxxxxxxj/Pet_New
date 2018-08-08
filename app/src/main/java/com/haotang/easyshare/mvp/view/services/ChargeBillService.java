package com.haotang.easyshare.mvp.view.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/8/8 17:13
 */
public class ChargeBillService extends Service {

    public static final String ACTION = "com.haotang.easyshare.mvp.view.services.ChargeBillService";
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