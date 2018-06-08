package com.haotang.easyshare.mvp.view.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.event.MainTabEvent;
import com.haotang.easyshare.mvp.view.activity.MainActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyJPushReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            RingLog.d(TAG, "[MyJPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                if (StringUtil.isNotEmpty(regId)) {
                    SharedPreferenceUtil.getInstance(context).saveString("jpush_id", regId);
                }
                RingLog.d(TAG, "[MyJPushReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                RingLog.d(TAG, "[MyJPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                RingLog.d(TAG, "[MyJPushReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                RingLog.d(TAG, "[MyJPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                RingLog.d(TAG, "[MyJPushReceiver] 用户点击打开了通知");
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                RingLog.e(TAG, "extras = " + extras);
                donePush(context, extras);
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                RingLog.d(TAG, "[MyJPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                RingLog.d(TAG, "[MyJPushReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                RingLog.d(TAG, "[MyJPushReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }
    }

    private void donePush(Context context, String json) {
        try {
            JSONObject jobj = new JSONObject(json);
            int display = 0;
            String destination = "";
            JSONObject jparameters = null;
            if (jobj.has("display") && !jobj.isNull("display")) {//展示方式(1:原生、2:H5)
                display = Integer.parseInt(jobj.getString("display"));
            }
            if (jobj.has("destination") && !jobj.isNull("destination")) {//跳转目的地,原生展示方式输入界面编号,H5展示方式输入页面url
                destination = jobj.getString("destination");
            }
            if (jobj.has("parameters") && !jobj.isNull("parameters")) {// json对象
                jparameters = jobj.getJSONObject("parameters");
            }
            if (display == 1) {
                switch (Integer.parseInt(destination)) {
                    case AppConfig.MAIN_MAIN:
                        context.startActivity(new Intent(context, MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        DevRing.busManager().postEvent(new MainTabEvent(0));
                        break;
                    case AppConfig.MAIN_HOT:
                        context.startActivity(new Intent(context, MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        DevRing.busManager().postEvent(new MainTabEvent(1));
                        break;
                    case AppConfig.MAIN_MY:
                        context.startActivity(new Intent(context, MainActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        DevRing.busManager().postEvent(new MainTabEvent(2));
                        break;
                }
            } else if (display == 2) {
                if (!isAppOpen(context)) {
                    context.startActivity(new Intent(context, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL_KEY, destination);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            } else {// 首页
                context.startActivity(new Intent(context, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        } catch (Exception e) {
            e.printStackTrace();
            RingLog.e("e = " + e.toString());
            context.startActivity(new Intent(context, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    private boolean isAppOpen(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals("com.haotang.easyshare")
                    || info.baseActivity.getPackageName().equals(
                    "com.haotang.easyshare")) {
                return true;
            }
        }
        return false;
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    RingLog.d(TAG, "This message has no Extra data");
                    continue;
                }
                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();
                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    RingLog.d(TAG, "Get message extra JSON error!");
                }
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
    }
}
