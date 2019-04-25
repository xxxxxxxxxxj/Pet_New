package com.haotang.easyshare.app;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.model.Message;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 11:22
 */
public class AppConfig {
    public static final String WX_SECRET = "4541b1896902768bafda8e717486e17a";
    public static final String UMENG_APPKEY = "5b04ccf0a40fa37bb50001b7";
    public static final int MAIN_MAIN = 1111;
    public static final int MAIN_HOT = 1112;
    public static final int MAIN_MY = 1113;
    public static final int EXIT_USER_CODE = 200003;
    public static final String URL = "http://www.sayiyinxiang.com";
    public static final String SHAREIMG_URL = "http://img.sayiyinxiang.com/api/charging/imgs/15382200559974283912.jpg";
    public static final String XIEYI_URL = "https://m.dzztrip.cn/h5/product/app_bld/agreement/index.html";
    public static final int HTTP_TIMEOUT = 100;
    public static List<Message> forwardMsg = new ArrayList<>();
    public static final String SERVER_KEY = "A16EF76FA2D6B5A1A743A489D9332D9A";
    public static final int SERVER_ERROR = 5201314;
    public static final String SERVER_ERROR_MSG = "服务器错误";
    public static final String QQ_ID = "";
    public static final String WX_ID = "wxf1749cddcb6224d1";
    public static final boolean isShowLog = false;
    public static final int environmental = 3;//1.test环境---2.demo环境---3.线上环境
    public static final int ALI_SDK_PAY_FLAG = 1000;
    public static final int REQUEST_CODE_CHOOSE = 23;
    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";
    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";
    public static String GaoDeMapPackageName = "com.autonavi.minimap";
    public static String BaiDuMapPackageName = "com.baidu.BaiduMap";
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
    public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
    public static final String DiskCache_Name = "diskcache_easydhare";
    public final static String ZHOUJUNXIA_USERNAME = "1234567890zhoujunxia";
    public final static String ZHOUJUNXIA_PASSWORD = "1234567890zhoujunxia";
    public final static String XUJUN_USERNAME = "1234567890xujun";
    public final static String XUJUN_PASSWORD = "1234567890xujun";
    public final static String DOWNLOAD_URL_YINGYONGBAO = "https://sj.qq.com/myapp/detail.htm?apkName=com.haotang.easyshare";
}
