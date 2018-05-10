package com.haotang.easyshare.app;

import com.amap.api.maps.model.LatLng;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 11:22
 */
public class AppConfig {
    public static final int SERVER_ERROR = 5201314;
    public static final String SERVER_ERROR_MSG = "服务器错误";
    public static final String QQ_ID = "1104724367";
    public static final String WX_ID = "wx1668e9f200eb89b2";
    public static final boolean isShowLog = true;
    public static final int environmental = 3;//1.test环境---2.demo环境---3.线上环境
    public static final int ALI_SDK_PAY_FLAG = 1000;
    public static final int REQUEST_CODE_CHOOSE = 23;
    public static String GaoDeMapPackageName = "com.autonavi.minimap";
    public static String BaiDuMapPackageName = "com.baidu.BaiduMap";
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
    public static final String DiskCache_Name = "diskcache_easydhare";
}
