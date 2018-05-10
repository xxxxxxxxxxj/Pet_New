package com.haotang.easyshare.app.constant;

import android.app.Activity;
import android.content.Context;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.SystemUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 11:30
 */
public class UrlConstants {

    public static final String LOGIN = "user/info/login";

    private static int getEnvironmental() {
        return AppConfig.environmental;//1.test环境---2.demo环境---3.线上环境
    }

    /**
     * 获取带端口的IP地址
     *
     * @return
     */
    public static String getServiceBaseUrl() {
        String url = "";
        switch (getEnvironmental()) {
            case 1://test环境
                url = "http://192.168.0.252/";
                break;
            case 2://demo环境
                url = "http://demo.cwjia.cn/";
                break;
            case 3://线上环境
                url = "https://api.sayiyinxiang.com/api/";
                break;
            default:
                break;
        }
        return url;
    }

    public static String getServiceBaseUrlNew() {
        String url = "";
        switch (getEnvironmental()) {
            case 1://test环境
                url = "http://192.168.0.252/pet-api/";
                break;
            case 2://demo环境
                url = "http://demo.cwjia.cn/pet-api/";
                break;
            case 3://线上环境
                url = "https://api.sayiyinxiang.com/api/";
                break;
            default:
                break;
        }
        return url;
    }

    public static String getWebBaseUrl() {
        String url = "";
        switch (getEnvironmental()) {
            case 1://test环境
                url = "http://192.168.0.247/";
                break;
            case 2://demo环境
                url = "http://192.168.0.248/";
                break;
            case 3://线上环境
                url = "https://api.sayiyinxiang.com/api/";
                break;
            default:
                break;
        }
        return url;
    }

    public static String getGlobalParam(String baseUrl, Activity activity) {
        if (baseUrl.contains("?")) {
            baseUrl = baseUrl
                    + "&system=android_" + SystemUtil.getCurrentVersion(activity)
                    + "&imei="
                    + SystemUtil.getIMEI(activity)
                    + "&phone="
                    + SharedPreferenceUtil.getInstance(activity).getString("cellphone", "") + "&phoneModel="
                    + android.os.Build.BRAND + " " + android.os.Build.MODEL
                    + "&phoneSystemVersion=" + "Android "
                    + android.os.Build.VERSION.RELEASE + "&petTimeStamp="
                    + System.currentTimeMillis();
        } else {
            baseUrl = baseUrl
                    + "?system=android_" + SystemUtil.getCurrentVersion(activity)
                    + "&imei="
                    + SystemUtil.getIMEI(activity)
                    + "&phone="
                    + SharedPreferenceUtil.getInstance(activity).getString("cellphone", "") + "&phoneModel="
                    + android.os.Build.BRAND + " " + android.os.Build.MODEL
                    + "&phoneSystemVersion=" + "Android "
                    + android.os.Build.VERSION.RELEASE + "&petTimeStamp="
                    + System.currentTimeMillis();
        }
        return baseUrl;
    }

    public static final String GET_FLASH_DATA = getServiceBaseUrlNew() + "startPageConfig/startShowImg?";
    public static final String GET_LASTVERSION_DATA = getServiceBaseUrlNew() + "user/checkversion?";
    public static final String GET_BOTTOMBAR_DATA = getServiceBaseUrl() + "pet/user/index?";
    public static final String GET_MAINFRAG_DATA = getServiceBaseUrl() + "pet/user/index?";
    /**
     * 下发验证码
     */
    public static final String SENDVERIFYCODE = "user/info/sendVerifyCode";

    public static Map<String, String> getMapHeader(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("system", "android_" + SystemUtil.getCurrentVersion(context));
        map.put("imei", SystemUtil.getIMEI(context));
        map.put("phone", SharedPreferenceUtil.getInstance(context).getString("cellphone", ""));
        map.put("phoneModel", android.os.Build.BRAND + " " + android.os.Build.MODEL);
        map.put("phoneSystemVersion", "Android "
                + android.os.Build.VERSION.RELEASE);
        map.put("petTimeStamp", String.valueOf(System.currentTimeMillis()));
        return map;
    }

    public static Map<String, String> getMapHeaderNoImei(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("system", "android_" + SystemUtil.getCurrentVersion(context));
        map.put("phone", SharedPreferenceUtil.getInstance(context).getString("cellphone", ""));
        map.put("phoneModel", android.os.Build.BRAND + " " + android.os.Build.MODEL);
        map.put("phoneSystemVersion", "Android "
                + android.os.Build.VERSION.RELEASE);
        map.put("petTimeStamp", String.valueOf(System.currentTimeMillis()));
        return map;
    }
}
