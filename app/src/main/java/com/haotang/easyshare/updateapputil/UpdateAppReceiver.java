package com.haotang.easyshare.updateapputil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.widget.MyNotification;
import com.ljy.devring.other.RingLog;

import java.io.File;


/**
 * Created by Teprinciple on 2017/11/3.
 */

public class UpdateAppReceiver extends BroadcastReceiver {
    private final static String TAG = UpdateAppReceiver.class.getSimpleName();
    private MyNotification mNotification;

    @Override
    public void onReceive(Context context, Intent intent) {
        int progress = intent.getIntExtra("progress", 0);
        String title = intent.getStringExtra("title");
        int state = intent.getIntExtra("state", 0);
        RingLog.d(TAG, "progress = " + progress + "---title = " + title);
        if (UpdateAppUtils.showNotification) {
            mNotification = new MyNotification(context, null, 1);
            mNotification.showCustomizeNotification(R.mipmap.logo, R.string.app_name + title,
                    R.layout.download_notif, progress);
        }
        if (state == MyNotification.DOWNLOAD_COMPLETE) {
            if (mNotification != null) {
                mNotification.changeNotificationStatus(state);
                mNotification.removeNotification();
            }
            if (DownloadAppUtils.downloadUpdateApkFilePath != null) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                File apkFile = new File(DownloadAppUtils.downloadUpdateApkFilePath);
                if (UpdateAppUtils.needFitAndroidN && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(
                            context, context.getPackageName() + ".fileprovider", apkFile);
                    i.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    i.setDataAndType(Uri.fromFile(apkFile),
                            "application/vnd.android.package-archive");
                }
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        } else if (state == MyNotification.DOWNLOAD_FAIL) {
            if (mNotification != null) {
                mNotification.changeNotificationStatus(state);
                mNotification.removeNotification();
            }
        }
    }
}
