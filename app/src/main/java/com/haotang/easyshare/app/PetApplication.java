package com.haotang.easyshare.app;

import android.app.Application;

import com.haotang.easyshare.MyEventBusIndex;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.db.greendao.GreenDBManager;
import com.haotang.easyshare.mvp.model.imageload.FrescoManager;
import com.haotang.easyshare.shareutil.ShareConfig;
import com.haotang.easyshare.shareutil.ShareManager;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.FileUtil;
import com.umeng.commonsdk.UMConfigure;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 11:28
 */
public class PetApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //内存泄露检测
//        if (LeakCanary.isInAnalyzerProcess(this)) return;
//        LeakCanary.install(this);

        //*********1.初始化**********
        DevRing.init(this);


        //*********2.根据你的需求进行相关模块的全局配置，下面对每个配置方法进行了说明**********

        /*//网络请求模块
        DevRing.configureHttp()//配置retrofit
                .setBaseUrl(UrlConstants.getServiceBaseUrl())//设置BaseUrl
                .setConnectTimeout(15)//设置请求超时时长，单位秒
                //.setMapHeader(UrlConstants.getMapHeader(getApplicationContext()))//设置全局的header信息
//                .setIsUseCache(true)//设置是否启用缓存，默认不启用
//                .setCacheFolder(file)//设置缓存地址，传入的file需为文件夹，默认保存在/storage/emulated/0/Android/data/com.xxx.xxx/cache/retrofit_http_cache下
//                .setCacheSize(size)//设置缓存大小，单位byte，默认20M
//                .setCacheTimeWithNet(time)//设置有网络时缓存保留时长，单位秒，默认60秒
//                .setCacheTimeWithoutNet(time)//设置无网络时缓存保留时长，单位秒，默认一周
//                .setIsUseRetryWhenError(true)//设置是否开启失败重试功能，目前仅支持普通的网络请求，上传下载不支持。默认不开启
//                .setMaxRetryCount(2)//设置失败后重试的最大次数，默认3次
//                .setTimeRetryDelay(5)//设置失败后重试的延迟时长，单位秒，默认3秒
                .setIsUseLog(AppConfig.isShowLog);//设置是否开启Log，默认不开启
        //如果提供的配置方法还无法满足你的需求，那可以通过以下方法获取builder进行你的定制
//        DevRing.configureHttp().getOkHttpClientBuilder();
//        DevRing.configureHttp().getRetrofitBuilder();*/

        DevRing.configureHttp()//配置retrofit
                .setBaseUrl(UrlConstants.getServiceBaseUrl())//设置BaseUrl
                .setConnectTimeout(15)//设置请求超时时长，单位秒
                .setIsUseLog(AppConfig.isShowLog);//设置是否开启Log，默认不开启


        //图片加载模块（可替换，demo演示了如何使用Fresco替换默认的Glide）
//        DevRing.configureImage()//配置默认的Glide
        DevRing.configureImage(new FrescoManager()) //传入Fresco管理者进行替换，注意，替换为Fresco后，相关的ImageView需换成SimpleDraweeView
                .setLoadingResId(R.mipmap.ic_image_load)//设置“加载中”状态时显示的图片
                .setErrorResId(R.mipmap.ic_image_load)//设置“加载失败”状态时显示的图片
                .setIsShowTransition(true)//设置是否开启状态切换时的过渡动画，默认false
//                .setIsUseOkhttp(false)//设置是否使用okhttp3作为网络组件，默认true
//                .setMemoryCacheSize(size)//设置内存缓存大小，不建议设置，使用框架默认设置的大小即可
//                .setBitmapPoolSize(size)//设置Bitmap池大小，设置内存缓存大小的话一般这个要一起设置，不建议设置，使用框架默认设置的大小即可
//                .setDiskCacheFile(file)//设置具体的磁盘缓存地址，传入的file需为文件夹
//                .setDiskCacheSize(200*1024*1024)//设置磁盘缓存大小，单位byte，默认250M
                .setIsDiskCacheExternal(true);//设置磁盘缓存地址是否在外部存储中，默认false


        //事件总线模块（可替换，demo演示了如何使用RxBus替换默认的EventBus）
        DevRing.configureBus()//配置默认的EventBus
                .setIndex(new MyEventBusIndex())//设置用于加速的Index
                .setIsUseIndex(true);//设置是否使用index进行加速
        //DevRing.configureBus(new RxBusManager());//传入RxBus的管理者进行替换

        //数据库模块（可替换，demo演示了如何使用原生数据库替换默认的GreenDao）
        DevRing.configureDB(new GreenDBManager());//传入GreenDao数据库的管理者
        //DevRing.configureDB(new NativeDBManager());//传入原生数据库的管理者

        //缓存模块
        DevRing.configureCache()//配置缓存
//                .setDiskCacheMaxSize(50*1024*1024)//设置磁盘缓存最大缓存大小，单位为byte，默认无上限
//                .setDiskCacheMaxCount(10)//设置磁盘缓存的文件夹数量上限，默认无上限
                //配置磁盘缓存的地址，传入的File需为文件夹，默认保存在/data/user/0/com.xxx.xxx/cache下
                .setDiskCacheFolder(FileUtil.getDirectory(FileUtil.getExternalCacheDir(this), "test_disk_cache"));


        //其他模块
        DevRing.configureOther()//配置其他
                .setIsUseCrashDiary(true)//设置是否开启崩溃日志功能，默认不开启
//                .setCrashDiaryFolder(file)//设置崩溃日志的地址，传入的file需为文件夹，默认保存在/storage/emulated/0/Android/data/com.xxx.xxx/cache/crash_log下
                .setIsShowRingLog(AppConfig.isShowLog);//设置是否显示Ringlog打印的内容，默认true


        //*********3.开始构建**********
        DevRing.create();

        JPushInterface.setDebugMode(AppConfig.isShowLog);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        String regId = JPushInterface.getRegistrationID(getApplicationContext());
        RingLog.e("regId", "regId = " + regId);
        if (StringUtil.isNotEmpty(regId)) {
            SharedPreferenceUtil.getInstance(getApplicationContext()).saveString("jpush_id", regId);
        }

        JMessageClient.setDebugMode(AppConfig.isShowLog);
        JMessageClient.init(getApplicationContext());

        ShareConfig config = ShareConfig.instance()
                .qqId(AppConfig.QQ_ID)
                .wxId(AppConfig.WX_ID)
                .wxSecret(AppConfig.WX_SECRET);
        ShareManager.init(config);

        //友盟设置
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //设置是否对日志信息进行加密, 默认false(不加密).
        UMConfigure.setEncryptEnabled(true);
        UMConfigure.init(getApplicationContext(), AppConfig.UMENG_APPKEY, "easyshare_1", UMConfigure.DEVICE_TYPE_PHONE, "");
        ZXingLibrary.initDisplayOpinion(this);
    }
}
