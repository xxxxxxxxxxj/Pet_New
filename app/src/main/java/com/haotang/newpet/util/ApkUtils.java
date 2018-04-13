package com.haotang.newpet.util;

import android.os.Environment;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2017/5/24 15:55
 */
public class ApkUtils {
    /**
     * 获取apk存储路径
     *
     * @param version
     * @return
     */
    public static String getApkFilePath(String version) {
        return Environment.getExternalStorageDirectory() + "/newpet_" + version + ".apk";
    }
}
