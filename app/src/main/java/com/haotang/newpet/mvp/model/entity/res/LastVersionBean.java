package com.haotang.newpet.mvp.model.entity.res;

import java.io.Serializable;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 14:52
 */
public class LastVersionBean implements Serializable {
    private String nversion;
    private int mandate;
    private String download;
    private String text;

    public String getNversion() {
        return nversion;
    }

    public void setNversion(String nversion) {
        this.nversion = nversion;
    }

    public int getMandate() {
        return mandate;
    }

    public void setMandate(int mandate) {
        this.mandate = mandate;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
