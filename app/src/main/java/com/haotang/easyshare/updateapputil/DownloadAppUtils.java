package com.haotang.easyshare.updateapputil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.haotang.easyshare.mvp.view.widget.MyNotification;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;


/**
 * Created by Teprinciple on 2016/12/13.
 */
public class DownloadAppUtils {
    private static final String TAG = DownloadAppUtils.class.getSimpleName();
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径

    /**
     * 通过浏览器下载APK包
     *
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void download(final Context context, String url, final String serverVersionName) {
        String packageName = context.getPackageName();
        String filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//外部存储卡
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            Log.i(TAG, "没有SD卡");
            return;
        }
        String apkLocalPath = filePath + File.separator + packageName + "_" + serverVersionName + ".apk";
        downloadUpdateApkFilePath = apkLocalPath;
        FileDownloader.setup(context);
        FileDownloader.getImpl().create(url)
                .setPath(apkLocalPath)
                .setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void started(BaseDownloadTask task) {
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, long soFarBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        send(context, (int) (soFarBytes * 100.0 / totalBytes), serverVersionName, MyNotification.DOWNLOADING);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        send(context, 100, serverVersionName, MyNotification.DOWNLOAD_COMPLETE);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        send(context, 100, serverVersionName, MyNotification.DOWNLOAD_FAIL);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    private static void send(Context context, int progress, String serverVersionName, int state) {
        Intent intent = new Intent("teprinciple.update");
        intent.putExtra("progress", progress);
        intent.putExtra("state", state);
        intent.putExtra("title", serverVersionName);
        context.sendBroadcast(intent);
    }
}
