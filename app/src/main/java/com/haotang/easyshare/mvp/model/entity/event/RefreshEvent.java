package com.haotang.easyshare.mvp.model.entity.event;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/14 13:50
 */
public class RefreshEvent {
    public static int COLLECT_OR_CANCEL_CHARGE = 2;
    private int refreshIndex;
    public final static int SAVE_CHARGE_COMMENT = 1;

    public int getRefreshIndex() {
        return refreshIndex;
    }

    public void setRefreshIndex(int refreshIndex) {
        this.refreshIndex = refreshIndex;
    }

    public RefreshEvent() {
    }

    public RefreshEvent(int refreshIndex) {
        this.refreshIndex = refreshIndex;
    }
}
