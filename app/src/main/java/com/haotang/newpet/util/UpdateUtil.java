package com.haotang.newpet.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.haotang.newpet.mvp.view.widget.InstallDialog;
import com.haotang.newpet.updateapputil.Callback;
import com.haotang.newpet.updateapputil.ConfirmDialog;
import com.haotang.newpet.updateapputil.DownloadAppUtils;
import com.ljy.devring.other.RingLog;

import java.io.File;


public class UpdateUtil {
    public static final int UPDATEFORDIALOG = 1;
    public static final int UPDATEFORNOTIFICATION = 2;

    public static void showForceUpgradeDialog(final Context context, String msg, final String path,
                                              final String version) {
        InstallDialog mDialog = new InstallDialog.Builder(context)
                .setTitle("升级到版本V." + version)
                .setType(InstallDialog.DIALOGTYPE_ALERT).setMessage(msg)
                .setCancelable(false).setOKStr("极速升级")
                .positiveListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        updateApk(context, path, version, UPDATEFORNOTIFICATION);
                    }
                }).build();
        mDialog.show();
    }

    public static void showUpgradeDialog(final Context context, String msg, final String path, final String version) {
        InstallDialog mDialog = new InstallDialog.Builder(context)
                .setTitle("升级到版本V." + version)
                .setType(InstallDialog.DIALOGTYPE_CONFIRM).setMessage(msg)
                .setCancelStr("残忍拒绝").setOKStr("极速升级")
                .positiveListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        updateApk(context, path, version, UPDATEFORNOTIFICATION);
                    }
                }).build();
        mDialog.show();
    }

    /**
     * 下载apk,两种模式
     *
     * @param context
     * @param updateType
     */
    public static void updateApk(final Context context, final String apkPath, final String serverVersionName, int updateType) {
        switch (updateType) {
            case UPDATEFORDIALOG:
                //BgUpdate.updateForDialog(context, url, filePath);
                if (SystemUtil.isWifiConnected(context)) {
                    DownloadAppUtils.download(context, apkPath, serverVersionName);
                } else {
                    new ConfirmDialog(context, new Callback() {
                        @Override
                        public void callback(int position) {
                            DownloadAppUtils.download(context, apkPath, serverVersionName);
                        }
                    }).setContent("目前手机不是WiFi状态\n确认是否继续下载更新？").show();
                }
                break;
            case UPDATEFORNOTIFICATION:
                //BgUpdate.updateForNotification(context, url, filePath);
                break;
        }
    }

    /**
     * 比较版本号
     *
     * @param serviceVersion
     * @param localVersion
     * @return
     */
    public static boolean compareVersion(String serviceVersion,
                                         String localVersion) {
        RingLog.d("serviceVersion:" + serviceVersion + " localVersion:"
                + localVersion);
        boolean result = false;
        serviceVersion = serviceVersion.replace(".", "");
        localVersion = localVersion.replace(".", "");
        int flagLength = 0;
        int versionLength = serviceVersion.length() > localVersion.length() ? localVersion
                .length() : serviceVersion.length();
        for (int i = 0; i < versionLength; i++) {
            if (Integer.parseInt(serviceVersion.charAt(i) + "") > Integer
                    .parseInt(localVersion.charAt(i) + "")) {
                result = true;
                break;
            } else if (Integer.parseInt(serviceVersion.charAt(i) + "") < Integer
                    .parseInt(localVersion.charAt(i) + "")) {
                break;
            } else {
                flagLength = i;
            }
        }
        if (!result
                && flagLength + 1 == versionLength
                && serviceVersion.length() > localVersion.length()
                && 0 < Integer.parseInt(serviceVersion.charAt(versionLength)
                + "")) {
            result = true;
        }
        return result;
    }

    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    public static void installAPK(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
