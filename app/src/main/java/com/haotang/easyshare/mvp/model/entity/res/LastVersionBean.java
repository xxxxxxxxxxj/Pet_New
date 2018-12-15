package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 14:52
 */
public class LastVersionBean {
    private int compulsory;
    private String content;
    private String title;
    private String version;
    private String download;

    public void setDownload(String download) {
        this.download = download;
    }

    public int getCompulsory() {
        return compulsory;
    }

    public void setCompulsory(int compulsory) {
        this.compulsory = compulsory;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownload() {
        return download;
    }
}
