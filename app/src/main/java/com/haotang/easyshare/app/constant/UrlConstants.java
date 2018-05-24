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
    /**
     * 20.广告
     */
    public static final String ADVERTISEMENT = "util/ad/list";
    /**
     * 21.热门品牌
     */
    public static final String HOT_CAR_BRAND = "brand/info/hot";
    /**
     * 22.所有品牌
     */
    public static final String ALL_CAR_BRAND = "brand/info/list";
    /**
     * 23.最新帖子列表
     */
    public static final String NEWEST_POINT = "article/info/new";
    /**
     * 24.热门帖子列表
     */
    public static final String HOT_POINT = "article/info/hot";
    /**
     * 25.问题车帖子列表
     */
    public static final String PROBLEM_CAR_POINT = "article/info/problem";
    /**
     * 26.品牌热帖
     */
    public static final String BRAND_HOT_POINT = "brand/info/article";
    /**
     * 27.发帖
     */
    public static final String SENDPOST = "article/info/save";
    /**
     * 28.热门车型
     */
    public static final String HOT_SPECIAL_CAR = "brand/car/special";
    /**
     * 29.评价用户
     */
    public static final String EVAL_USER = "user/eval";
    /**
     * 30.点赞
     */
    public static final String PRAISE_USER = "article/praise/save";
    /**
     * 31.用户车辆信息
     */
    public static final String MY_CAR = "user/car/my";
    /**
     * 32.保存或修改用户车辆信息
     */
    public static final String SAVE_ORUPDATE_USERCAR = "user/car/save";
    /**
     * 33.编辑充电桩
     */
    public static final String UPDATECHARGE = "charging/info/update";
    /**
     * 34.评论标签
     */
    public static final String COMMENT_TAGS = "charging/comment/tags";
    /**
     * 35.评价星级
     */
    public static final String STARS = "user/eval/stars";
    /**
     * 36.删除帖子
     */
    public static final String DELETE_POST = "article/info/delete";
    /**
     * 37.分享帖子成功回调
     */
    public static final String SHARE_POST = "article/info/share/callback";
    /**
     * 38.编辑用户信息
     */
    public static final String UPDATE_USER_INFO = "";

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

    public static final String GET_FLASH_DATA = getServiceBaseUrlNew() + "charging/comment/tags?";
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
