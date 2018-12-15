package com.haotang.easyshare.mvp.model.entity.event;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/15 12:00
 */
public class UpdateAppEvent {
    public final static int DOWNLOAD_COMPLETE = 3;
    public final static int DOWNLOAD_FAIL = 2;
    public static final int DOWNLOADING = 1;
    private long soFarBytes;
    private long totalBytes;
    private int state;

    public UpdateAppEvent(int state) {
        this.state = state;
    }

    public UpdateAppEvent() {
    }

    public UpdateAppEvent(long soFarBytes, long totalBytes, int state) {
        this.soFarBytes = soFarBytes;
        this.totalBytes = totalBytes;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getSoFarBytes() {
        return soFarBytes;
    }

    public void setSoFarBytes(long soFarBytes) {
        this.soFarBytes = soFarBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }
}
