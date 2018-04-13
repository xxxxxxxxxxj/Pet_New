package com.haotang.newpet.app.constant;

import android.app.Activity;

import com.haotang.newpet.util.SystemUtil;
import com.ljy.devring.DevRing;

import static com.haotang.newpet.util.SystemUtil.getCurrentVersion;
import static com.haotang.newpet.util.SystemUtil.getIMEI;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 11:30
 */
public class UrlConstants {
    private static int getEnvironmental() {
        return 3;//1.test环境---2.demo环境---3.线上环境
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
                url = "https://api.cwjia.cn/";
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
                url = "https://api.ichongwujia.com/";
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
                url = "https://m.cwjia.cn/";
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
                    + "&cellPhone="
                    + DevRing.cacheManager().spCache().getString("cellphone", "") + "&phoneModel="
                    + android.os.Build.BRAND + " " + android.os.Build.MODEL
                    + "&phoneSystemVersion=" + "Android "
                    + android.os.Build.VERSION.RELEASE + "&petTimeStamp="
                    + System.currentTimeMillis();
        } else {
            baseUrl = baseUrl
                    + "?system=android_" + SystemUtil.getCurrentVersion(activity)
                    + "&imei="
                    + SystemUtil.getIMEI(activity)
                    + "&cellPhone="
                    + DevRing.cacheManager().spCache().getString("cellphone", "") + "&phoneModel="
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
}
