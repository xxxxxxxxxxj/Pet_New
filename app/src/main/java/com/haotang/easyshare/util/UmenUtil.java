package com.haotang.easyshare.util;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/6 09:30
 */
public class UmenUtil {
    /**
     * 统计开关
     */
    private static boolean isStatistics = true;
    public static final String yxzx18 = "yxzx18";//会员特权页面
    public static final String yxzx17 = "yxzx17";//充电记录列表
    public static final String yxzx16 = "yxzx16";//充值页面
    public static final String yxzx15 = "yxzx15";//添加充电桩信息页面
    public static final String yxzx14 = "yxzx14";//我的页面
    public static final String yxzx13 = "yxzx13";//搜索充电桩
    public static final String yxzx12 = "yxzx12";//充电扫码页
    public static final String yxzx11 = "yxzx11";//填写个人信息页面
    public static final String yxzx10 = "yxzx10";//车的详情
    public static final String yxzx9 = "yxzx9";//卖车车辆所有品牌
    public static final String yxzx8 = "yxzx8";//卖车首页
    public static final String yxzx7 = "yxzx7";//帖子车品牌专区
    public static final String yxzx6 = "yxzx6";//热点帖子所有品牌页
    public static final String yxzx5 = "yxzx5";//热点帖子详情
    public static final String yxzx4 = "yxzx4";//热点首页
    public static final String yxzx3 = "yxzx3";//充电桩详情
    public static final String yxzx2 = "yxzx2";//附近充电桩列表页
    public static final String yxzx1 = "yxzx1";//首页

    /**
     * 自定义事件只统计次数
     *
     * @param context
     * @param eventId 统计事件唯一标识
     */
    public static void UmengEventStatistics(Context context, String eventId) {
        if (!isStatistics) {
            return;
        }
        MobclickAgent.onEvent(context, eventId);
    }
}
