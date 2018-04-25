package com.haotang.easyshare.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.activity.PhotoViewPagerActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 17:02
 */
public class SystemUtil {
    /**
     * 隐藏虚拟按键，并且全屏
     */
    @SuppressLint("NewApi")
    public static void hideBottomUIMenu(Activity context) {
        // 隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower
            // api
            View v = context.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            // for new api versions.
            View decorView = context.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static Bitmap readResToBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 获取IMEI码
     *
     * @param activity
     * @return
     */
    public static String getIMEI(final Activity activity) {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(activity.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static void cellPhone(Context context, String phone) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 获取当前版本号
     *
     * @param context
     * @return
     */
    public static String getCurrentVersion(Context context) {
        String curVersion = "0";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);// getPackageName()是你当前类的包名，0代表是获取版本信息
            curVersion = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curVersion;
    }

    /**
     * 检测wifi是否连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static void goPhotoView(Context context, int position, List<String> urlList) {
        Intent intent = new Intent(context, PhotoViewPagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(PhotoViewPagerActivity.EXTRA_IMAGE_URLS, urlList.toArray(new String[urlList.size()]));
        intent.putExtra(PhotoViewPagerActivity.EXTRA_IMAGE_INDEX, position);
        context.startActivity(intent);
    }

    /**
     * RxJava方式保存图片到本地
     *
     * @param context
     * @param mImageView
     * @param petCircle
     * @param fileName
     */
    public static void savePic(final Context context,
                               final ImageView mImageView, final File petCircle,
                               final String fileName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(
                new String[]{context.getResources().getString(
                        R.string.save_picture)},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveImageView(getViewBitmap(mImageView), petCircle,
                                context, fileName);
                    }
                });
        builder.show();
    }

    private static class SaveObservable implements
            Observable.OnSubscribe<String> {
        private Bitmap drawingCache = null;
        private File petCircle;
        private String fileName;

        public SaveObservable(Bitmap drawingCache, File petCircle,
                              String fileName) {
            this.drawingCache = drawingCache;
            this.petCircle = petCircle;
            this.fileName = fileName;
        }

        @Override
        public void call(Subscriber<? super String> subscriber) {
            if (drawingCache == null) {
                subscriber.onError(new NullPointerException(
                        "imageview的bitmap获取为null,请确认imageview显示图片了"));
            } else {
                try {
                    File imageFile = new File(this.petCircle, this.fileName);
                    FileOutputStream outStream;
                    outStream = new FileOutputStream(imageFile);
                    drawingCache.compress(Bitmap.CompressFormat.JPEG, 100,
                            outStream);
                    subscriber.onNext(Environment.getExternalStorageDirectory()
                            .getPath());
                    subscriber.onCompleted();
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        }
    }

    private static class SaveSubscriber extends Subscriber<String> {
        private Context context;

        public SaveSubscriber(Context context) {
            this.context = context;
        }

        @Override
        public void onCompleted() {
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            Log.i(getClass().getSimpleName(), e.toString());
            Toast.makeText(context, "保存失败——> " + e.toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(String s) {/*
                                     * Toast.makeText(context, "保存路径为：-->  " +
									 * s, Toast.LENGTH_SHORT) .show();
									 */
        }
    }

    public static void saveImageView(Bitmap drawingCache, File petCircle,
                                     Context context, String fileName) {
        Observable
                .create(new SaveObservable(drawingCache, petCircle, fileName))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SaveSubscriber(context));
    }

    /**
     * 某些机型直接获取会为null,在这里处理一下防止国内某些机型返回null
     */
    public static Bitmap getViewBitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    /*private void goNavigation(Context context,double lat,double lng,String saddr,
                              String daddr,String city){
        if(isInstallByread("com.baidu.BaiduMap")){
            try {
                Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:"+lat+","+lng+"|name:"+saddr+"&destination="+daddr+"&mode=driving&region="+city+"&src=宠物家#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); //启动调用
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //当特定手机调用百度出现 no activity found to handle 时 内部异常尝试调用高德或网页调用
                Utils.mLogError("==-->swim 2  "+e.getMessage());
                if (isInstallByread("com.autonavi.minimap")) {
                    try{
                        Utils.mLogError("==-->swim 3 shoplat  "+shoplat+" shoplng "+shoplng );
                        //double[] gglatlng = bd_decrypt(shoplat, shoplng);
                        double[] bd_decrypt = Utils.bd_decrypt(shoplat, shoplng);
//					 	double[] gglatlng = bd_decrypt(lat, lng);
//			            Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=宠物家&poiname="+daddr+"&lat="+shoplat*//*+gglatlng[0]*//*+"&lon="+shoplng*//*gglatlng[1]*//*+"&dev=0&style=2");
//			            startActivity(intent);
						*//*if (shopGaodelng!=0||shopGaodelat!=0) {
							goToNaviActivity(mContext, "宠物家","",shopGaodelat+"", shopGaodelng+"", "1", "2");
						}else {
							goToNaviActivity(mContext, "宠物家","", gglatlng[0]+"", gglatlng[1]+"", "1", "2");
						}*//*
                        goToNaviActivity(mContext, "宠物家","", bd_decrypt[0]+"", bd_decrypt[1]+"", "1", "2");
                    } catch (Exception e2){
                        e2.printStackTrace();
                    }
                }else{
                    String url = "http://api.map.baidu.com/direction?origin=latlng:"+lat+","+lng+
                            "|name:"+saddr+"&destination="+daddr+"&mode=driving&region="+city+
                            "&output=html&src=宠物家";
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.putExtra(Global.ANIM_DIRECTION(), Global.ANIM_COVER_FROM_RIGHT());
                    getIntent().putExtra(Global.ANIM_DIRECTION(), Global.ANIM_COVER_FROM_LEFT());
                    startActivity(intent);
                }
            }
        }else if(isInstallByread("com.autonavi.minimap")){
            try{
                double[] bd_decrypt = bd_decrypt(shoplat, shoplng);
                goToNaviActivity(mContext, "宠物家","", bd_decrypt[0]+"", bd_decrypt[1]+"", "1", "2");
            } catch (Exception e){
                e.printStackTrace();
            }
        }else{
            String url = "http://api.map.baidu.com/direction?origin=latlng:"+lat+","+lng+
                    "|name:"+saddr+"&destination="+daddr+"&mode=driving&region="+city+
                    "&output=html&src=宠物家";
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra(Global.ANIM_DIRECTION(), Global.ANIM_COVER_FROM_RIGHT());
            getIntent().putExtra(Global.ANIM_DIRECTION(), Global.ANIM_COVER_FROM_LEFT());
            startActivity(intent);
        }
    }*/

    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    private double[] bd_decrypt(double bd_lat, double bd_lon)
    {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        return new double[]{z * Math.sin(theta),z * Math.cos(theta)};
    }

    public void goToNaviActivity(Context context,String sourceApplication , String poiname , String lat , String lon , String dev , String style){
        StringBuffer stringBuffer  = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)){
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);
        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }
}
