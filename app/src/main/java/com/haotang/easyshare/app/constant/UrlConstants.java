package com.haotang.easyshare.app.constant;

import android.app.Activity;
import android.content.Context;

import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
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
    /**
     * 1.下发验证码
     */
    public static final String SENDVERIFYCODE = "user/info/sendVerifyCode";
    /**
     * 2.登陆
     */
    public static final String LOGIN = "user/info/login";
    /**
     * 3.用户主页信息
     */
    public static final String HOME = "user/info/home";
    /**
     * 4.首页
     */
    public static final String HOMEINDEX = "home/index";
    /**
     * 5.附近充电桩
     */
    public static final String NEARBY = "charging/info/nearby";
    /**
     * 6.充电桩详情
     */
    public static final String CHARGEDETAIL = "charging/info/detail";
    /**
     * 7.上传充电桩
     */
    public static final String SAVECHARGE = "charging/info/save";
    /**
     * 8.充电桩评论列表
     */
    public static final String COMMENT_LIST = "charging/comment/list";
    /**
     * 9.充电桩评论
     */
    public static final String COMMENT_SAVE = "charging/comment/save";
    /**
     * 10.管家留言列表
     */
    public static final String HISTORYMSG = "user/message/history";
    /**
     * 11.发布留言
     */
    public static final String SAVEMSG = "user/message/save";
    /**
     * 12.收藏的充电桩列表
     */
    public static final String COLLECT_CHARGE = "user/charging/list";
    /**
     * 13.收藏充电桩
     */
    public static final String FOLLOW_CHARGE = "user/charging/follow";
    /**
     * 14.取消收藏充电桩
     */
    public static final String CANCEL_FOLLOW_CHARGE = "user/charging/cancel";
    /**
     * 15.关注的人列表
     */
    public static final String FOLLOW_LIST = "user/idol/list";
    /**
     * 16.用户帖子列表
     */
    public static final String USERINFO_POST = "article/info/list";
    /**
     * 17.用户信息
     */
    public static final String USERINFO_UUID = "user/info";
    /**
     * 18.关注用户
     */
    public static final String FOLLOW_USER = "user/idol/follow";
    /**
     * 19.取消关注用户
     */
    public static final String CANCEL_FOLLOW_USER = "user/idol/cancel";

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

    public static Map<String, String> getMapHeader(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("system", "android_" + SystemUtil.getCurrentVersion(context));
        map.put("imei", SystemUtil.getIMEI(context));
        if (StringUtil.isNotEmpty(SharedPreferenceUtil.getInstance(context).getString("cellphone", ""))) {
            map.put("phone", SharedPreferenceUtil.getInstance(context).getString("cellphone", ""));
        }
        /*map.put("phoneModel", android.os.Build.BRAND + " " + android.os.Build.MODEL);
        map.put("phoneSystemVersion", "Android "
                + android.os.Build.VERSION.RELEASE);
        map.put("petTimeStamp", String.valueOf(System.currentTimeMillis()));*/
        return map;
    }

    public static Map<String, String> getMapHeaderNoImei(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("system", "android_" + SystemUtil.getCurrentVersion(context));
        if (StringUtil.isNotEmpty(SharedPreferenceUtil.getInstance(context).getString("cellphone", ""))) {
            map.put("phone", SharedPreferenceUtil.getInstance(context).getString("cellphone", ""));
        }
        /*map.put("phoneModel", android.os.Build.BRAND + " " + android.os.Build.MODEL);
        map.put("phoneSystemVersion", "Android "
                + android.os.Build.VERSION.RELEASE);
        map.put("petTimeStamp", String.valueOf(System.currentTimeMillis()));*/
        return map;
    }
}
