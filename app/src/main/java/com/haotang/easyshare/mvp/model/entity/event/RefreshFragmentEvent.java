package com.haotang.easyshare.mvp.model.entity.event;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/24 14:53
 */
public class RefreshFragmentEvent {
    public static final int REFRESH_WEBVIEW_LOGIN = 5;
    private int refreshIndex;
    public final static int REFRESH_MAINFRAGMET = 1;
    public final static int REFRESH_HOTFRAGMET = 2;
    public final static int REFRESH_MYFRAGMET = 3;
    public final static int REFRESH_HISTORYMESSAGEFRAGMET = 4;

    public int getRefreshIndex() {
        return refreshIndex;
    }

    public void setRefreshIndex(int refreshIndex) {
        this.refreshIndex = refreshIndex;
    }

    public RefreshFragmentEvent() {
    }

    public RefreshFragmentEvent(int refreshIndex) {
        this.refreshIndex = refreshIndex;
    }
}
