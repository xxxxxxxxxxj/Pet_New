package com.haotang.easyshare.mvp.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/1 15:37
 */
public class HotFragmenHeader {
    @BindView(R.id.banner_top_hotfrag)
    Banner bannerTopHotfrag;
    @BindView(R.id.rl_top_hotfrag_rmpp_more)
    LinearLayout rlTopHotfragRmppMore;
    @BindView(R.id.rl_top_hotfrag_rmpp)
    RelativeLayout rlTopHotfragRmpp;
    @BindView(R.id.rv_top_hotfrag)
    RecyclerView rvTopHotfrag;
    @BindView(R.id.rl_top_hotfrag_htpd_more)
    LinearLayout rlTopHotfragHtpdMore;
    @BindView(R.id.rl_top_hotfrag_htpd)
    RelativeLayout rlTopHotfragHtpd;
    @BindView(R.id.rl_banner_top_hotfrag)
    RelativeLayout rl_banner_top_hotfrag;
    @BindView(R.id.tv_banner_top_hotfrag)
    TextView tv_banner_top_hotfrag;

    public RelativeLayout getRl_banner_top_hotfrag() {
        return rl_banner_top_hotfrag;
    }

    public void setRl_banner_top_hotfrag(RelativeLayout rl_banner_top_hotfrag) {
        this.rl_banner_top_hotfrag = rl_banner_top_hotfrag;
    }

    public TextView getTv_banner_top_hotfrag() {
        return tv_banner_top_hotfrag;
    }

    public void setTv_banner_top_hotfrag(TextView tv_banner_top_hotfrag) {
        this.tv_banner_top_hotfrag = tv_banner_top_hotfrag;
    }

    public Banner getBannerTopHotfrag() {
        return bannerTopHotfrag;
    }

    public void setBannerTopHotfrag(Banner bannerTopHotfrag) {
        this.bannerTopHotfrag = bannerTopHotfrag;
    }

    public LinearLayout getRlTopHotfragRmppMore() {
        return rlTopHotfragRmppMore;
    }

    public void setRlTopHotfragRmppMore(LinearLayout rlTopHotfragRmppMore) {
        this.rlTopHotfragRmppMore = rlTopHotfragRmppMore;
    }

    public RelativeLayout getRlTopHotfragRmpp() {
        return rlTopHotfragRmpp;
    }

    public void setRlTopHotfragRmpp(RelativeLayout rlTopHotfragRmpp) {
        this.rlTopHotfragRmpp = rlTopHotfragRmpp;
    }

    public RecyclerView getRvTopHotfrag() {
        return rvTopHotfrag;
    }

    public void setRvTopHotfrag(RecyclerView rvTopHotfrag) {
        this.rvTopHotfrag = rvTopHotfrag;
    }

    public LinearLayout getRlTopHotfragHtpdMore() {
        return rlTopHotfragHtpdMore;
    }

    public void setRlTopHotfragHtpdMore(LinearLayout rlTopHotfragHtpdMore) {
        this.rlTopHotfragHtpdMore = rlTopHotfragHtpdMore;
    }

    public RelativeLayout getRlTopHotfragHtpd() {
        return rlTopHotfragHtpd;
    }

    public void setRlTopHotfragHtpd(RelativeLayout rlTopHotfragHtpd) {
        this.rlTopHotfragHtpd = rlTopHotfragHtpd;
    }

    public HotFragmenHeader(View headerRootView) {
        ButterKnife.bind(this, headerRootView);
    }
}
