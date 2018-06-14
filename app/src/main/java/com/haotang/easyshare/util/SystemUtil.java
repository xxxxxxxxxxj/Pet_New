package com.haotang.easyshare.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.LngLat;
import com.haotang.easyshare.mvp.model.entity.res.base.HttpResult;
import com.haotang.easyshare.mvp.model.http.NavApiService;
import com.haotang.easyshare.mvp.view.activity.PhotoViewPagerActivity;
import com.ljy.devring.DevRing;
import com.ljy.devring.http.support.observer.CommonObserver;
import com.ljy.devring.other.RingLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MultipartBody;
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
     * 获取手机屏幕的宽高
     *
     * @param activity
     * @return
     */
    public static int[] getDisplayMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return new int[]{dm.widthPixels, dm.heightPixels};
    }

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
    public static String getIMEI(final Context activity) {
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

    public static void goPhotoView(Context context, int position, List<String> urlList, boolean isDelete) {
        Intent intent = new Intent(context, PhotoViewPagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(PhotoViewPagerActivity.EXTRA_IMAGE_URLS, urlList.toArray(new String[urlList.size()]));
        intent.putExtra(PhotoViewPagerActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra(PhotoViewPagerActivity.EXTRA_IMAGE_ISDELETE, isDelete);
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

    /**
     * 将bitmap处理为圆形
     */
    public static Bitmap toCircleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public static boolean checkLogin(Context context) {
        boolean isLogin = false;
        if (StringUtil.isNotEmpty(SharedPreferenceUtil.getInstance(context).getString("cellphone", ""))) {
            isLogin = true;
        }
        return isLogin;
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public static void Exit(Context activity, int code) {
        if (code == AppConfig.EXIT_USER_CODE) {
            SharedPreferenceUtil.getInstance(activity).removeData("cellphone");
            DevRing.configureHttp().getMapHeader().put("phone", "");
        }
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

    /**
     * 保存文件
     *
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(File file, Bitmap bm, String fileName) throws IOException {
        File imageFile = new File(file, fileName);
        FileOutputStream outStream;
        outStream = new FileOutputStream(imageFile);
        bm.compress(Bitmap.CompressFormat.JPEG, 100,
                outStream);
        outStream.flush();
        outStream.close();
        return imageFile.getAbsolutePath();
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

    public static void goneJP(Context context) {
        try {
            ((InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(((Activity) context)
                                    .getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void goNavigation(final Context context, final double lat, final double lng, final String saddr,
                                    final String daddr, final double slat, final double slon, final String uuid) {
        ViewGroup customView = (ViewGroup) View.inflate(context, R.layout.map_bottom_dialog, null);
        TextView tv_map_bottomdia_gaode = (TextView) customView.findViewById(R.id.tv_map_bottomdia_gaode);
        TextView tv_map_bottomdia_baidu = (TextView) customView.findViewById(R.id.tv_map_bottomdia_baidu);
        RoundTextView tv_map_bottomdia_cancel = (RoundTextView) customView.findViewById(R.id.tv_map_bottomdia_cancel);
        ImageView iv_mapbottom_bg = (ImageView) customView.findViewById(R.id.iv_mapbottom_bg);
        LinearLayout ll_mapbottom = (LinearLayout) customView.findViewById(R.id.ll_mapbottom);
        final PopupWindow pWinBottomDialog = new PopupWindow(customView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        pWinBottomDialog.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        pWinBottomDialog.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        pWinBottomDialog.setOutsideTouchable(true);
        //设置可以点击
        pWinBottomDialog.setTouchable(true);
        //进入退出的动画
        pWinBottomDialog.setAnimationStyle(R.style.mypopwindow_anim_style);
        pWinBottomDialog.setWidth(SystemUtil.getDisplayMetrics((Activity) context)[0]);
        pWinBottomDialog.showAtLocation(customView, Gravity.BOTTOM, 0, 0);
        iv_mapbottom_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pWinBottomDialog.dismiss();
            }
        });
        tv_map_bottomdia_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pWinBottomDialog.dismiss();
            }
        });
        ll_mapbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_map_bottomdia_gaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferenceUtil.getInstance(context).getBoolean("isSerch", false) && StringUtil.isNotEmpty(uuid)) {
                    MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                            .addFormDataPart("uuid", uuid)
                            .build();
                    DevRing.httpManager().commonRequest(DevRing.httpManager().getService(NavApiService.class).callback(body)
                            , new CommonObserver<HttpResult<AddChargeBean>>() {
                                @Override
                                public void onResult(HttpResult<AddChargeBean> result) {
                                    if (result != null) {
                                        if (result.getCode() == 0) {
                                            RingLog.e("导航回调成功");
                                        } else {
                                            SystemUtil.Exit(context, result.getCode());
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
                    SharedPreferenceUtil.getInstance(context).removeData("isSerch");
                }
                pWinBottomDialog.dismiss();
                if (checkApkExist(context, AppConfig.GaoDeMapPackageName)) {
                    try {
                        Intent intent = Intent.getIntent("androidamap://route?sourceApplication=" + context.getResources().getString(R.string.app_name) +
                                "&sname=" + saddr + "&slat=" + slat + "&slon=" + slon + "&dlat=" + lat + "&dlon=" + lng + "&dname=" + daddr +
                                "&dev=0&m=0&t=0");
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        // 驾车导航
                        intent.setData(Uri.parse("http://uri.amap.com/navigation?from=" + slon + "," + slat + "&to=" + lng + "," + lat +
                                "&mode=car&src=nyx_super"));
                        context.startActivity(intent); // 启动调用
                    }
                } else {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    // 驾车导航
                    intent.setData(Uri.parse("http://uri.amap.com/navigation?from=" + slon + "," + slat + "&to=" + lng + "," + lat +
                            "&mode=car&src=nyx_super"));
                    context.startActivity(intent); // 启动调用
                }
            }
        });
        tv_map_bottomdia_baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreferenceUtil.getInstance(context).getBoolean("isSerch", false) && StringUtil.isNotEmpty(uuid)) {
                    MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                            .addFormDataPart("uuid", uuid)
                            .build();
                    DevRing.httpManager().commonRequest(DevRing.httpManager().getService(NavApiService.class).callback(body)
                            , new CommonObserver<HttpResult<AddChargeBean>>() {
                                @Override
                                public void onResult(HttpResult<AddChargeBean> result) {
                                    if (result != null) {
                                        if (result.getCode() == 0) {
                                            RingLog.e("导航回调成功");
                                        } else {
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
                    SharedPreferenceUtil.getInstance(context).removeData("isSerch");
                }
                pWinBottomDialog.dismiss();
                if (checkApkExist(context, AppConfig.BaiDuMapPackageName)) {
                    try {
                        LngLat lngLat = CoodinateCovertor.bd_encrypt(new LngLat(lng, lat));
                        LngLat slngLat = CoodinateCovertor.bd_encrypt(new LngLat(slon, slat));
                        Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:" + slngLat.getLantitude()
                                + "," + slngLat.getLongitude() +
                                "|name:" + saddr + "&destination=latlng:" + lngLat.getLantitude()
                                + "," + lngLat.getLongitude() + "|name:" + daddr + "&mode=driving" +
                                "&src=" + context.getResources().getString(R.string.app_name) + "#Intent;" +
                                "scheme=bdapp;package=com.baidu.BaiduMap;end");
                        context.startActivity(intent); //启动调用
                    } catch (Exception e) {
                        e.printStackTrace();
                        LngLat slngLat = CoodinateCovertor.bd_encrypt(new LngLat(slon, slat));
                        LngLat lngLat = CoodinateCovertor.bd_encrypt(new LngLat(lng, lat));
                        String url = "http://api.map.baidu.com/direction?origin=latlng:" + slngLat.getLantitude() +
                                "," + slngLat.getLongitude() +
                                "|name:" + saddr + "&destination=latlng:" + lngLat.getLantitude()
                                + "," + lngLat.getLongitude() + "|name:" + daddr + "&mode=driving" +
                                "&output=html&src=" + context.getResources().getString(R.string.app_name);
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                } else {
                    LngLat slngLat = CoodinateCovertor.bd_encrypt(new LngLat(slon, slat));
                    LngLat lngLat = CoodinateCovertor.bd_encrypt(new LngLat(lng, lat));
                    String url = "http://api.map.baidu.com/direction?origin=latlng:" + slngLat.getLantitude() +
                            "," + slngLat.getLongitude() +
                            "|name:" + saddr + "&destination=latlng:" + lngLat.getLantitude()
                            + "," + lngLat.getLongitude() + "|name:" + daddr + "&mode=driving" +
                            "&output=html&src=" + context.getResources().getString(R.string.app_name);
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
            }
        });
    }

    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * double类型转换为int类型
     *
     * @param num
     * @return
     */
    public static int formatDouble(double num) {
        return Integer.parseInt(new java.text.DecimalFormat("0").format(num));
    }

    public static boolean isDoubleEndWithZero(double price) {
        boolean bool = false;
        try {
            String str = String.valueOf(price);
            String[] split = str.split("\\.");
            if (Integer.parseInt(split[1]) > 0) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {
            bool = false;
            e.printStackTrace();
        }
        return bool;
    }
}
