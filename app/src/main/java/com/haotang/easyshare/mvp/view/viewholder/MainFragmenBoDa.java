package com.haotang.easyshare.mvp.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/26 14:56
 */
public class MainFragmenBoDa {
    @BindView(R.id.iv_mainbottom_bg)
    ImageView ivMainbottomBg;
    @BindView(R.id.ll_mainbottom_xq)
    LinearLayout llMainbottomXq;
    @BindView(R.id.ll_mainbottom_pl)
    LinearLayout llMainbottomPl;
    @BindView(R.id.ll_mainbottom_bottom)
    LinearLayout llMainbottomBottom;
    @BindView(R.id.iv_mainbottom_daohang)
    ImageView ivMainbottomDaohang;
    @BindView(R.id.tv_mainbottom_juli)
    TextView tvMainbottomJuli;
    @BindView(R.id.ll_mainbottom_daohang)
    LinearLayout llMainbottomDaohang;
    @BindView(R.id.iv_mainbottom_ggorgr)
    ImageView ivMainbottomGgorgr;
    @BindView(R.id.tv_mainbottom_name)
    TextView tvMainbottomName;
    @BindView(R.id.tv_mainbottom_kuaichong_num)
    TextView tvMainbottomKuaichongNum;
    @BindView(R.id.ll_mainbottom_kuaichong)
    LinearLayout llMainbottomKuaichong;
    @BindView(R.id.tv_mainbottom_manchong_num)
    TextView tvMainbottomManchongNum;
    @BindView(R.id.ll_mainbottom_manchong)
    LinearLayout llMainbottomManchong;
    @BindView(R.id.tv_mainbottom_kongxian_num)
    TextView tvMainbottomKongxianNum;
    @BindView(R.id.ll_mainbottom_kongxian)
    LinearLayout llMainbottomKongxian;
    @BindView(R.id.tv_mainbottom_yys)
    TextView tvMainbottomYys;
    @BindView(R.id.tv_mainbottom_kfsj)
    TextView tvMainbottomKfsj;
    @BindView(R.id.tv_mainbottom_zffs)
    TextView tvMainbottomZffs;
    @BindView(R.id.tv_mainbottom_tcf)
    TextView tvMainbottomTcf;
    @BindView(R.id.tv_mainbottom_xxdz)
    TextView tvMainbottomXxdz;
    @BindView(R.id.tv_mainbottom_pl)
    TextView tv_mainbottom_pl;
    @BindView(R.id.ll_mainbottom)
    LinearLayout ll_mainbottom;

    public LinearLayout getLl_mainbottom() {
        return ll_mainbottom;
    }

    public void setLl_mainbottom(LinearLayout ll_mainbottom) {
        this.ll_mainbottom = ll_mainbottom;
    }

    public ImageView getIvMainbottomBg() {
        return ivMainbottomBg;
    }

    public LinearLayout getLlMainbottomXq() {
        return llMainbottomXq;
    }

    public LinearLayout getLlMainbottomPl() {
        return llMainbottomPl;
    }

    public LinearLayout getLlMainbottomBottom() {
        return llMainbottomBottom;
    }

    public ImageView getIvMainbottomDaohang() {
        return ivMainbottomDaohang;
    }

    public TextView getTvMainbottomJuli() {
        return tvMainbottomJuli;
    }

    public LinearLayout getLlMainbottomDaohang() {
        return llMainbottomDaohang;
    }

    public ImageView getIvMainbottomGgorgr() {
        return ivMainbottomGgorgr;
    }

    public TextView getTvMainbottomName() {
        return tvMainbottomName;
    }

    public TextView getTvMainbottomKuaichongNum() {
        return tvMainbottomKuaichongNum;
    }

    public LinearLayout getLlMainbottomKuaichong() {
        return llMainbottomKuaichong;
    }

    public TextView getTvMainbottomManchongNum() {
        return tvMainbottomManchongNum;
    }

    public LinearLayout getLlMainbottomManchong() {
        return llMainbottomManchong;
    }

    public TextView getTvMainbottomKongxianNum() {
        return tvMainbottomKongxianNum;
    }

    public LinearLayout getLlMainbottomKongxian() {
        return llMainbottomKongxian;
    }

    public TextView getTvMainbottomYys() {
        return tvMainbottomYys;
    }

    public TextView getTvMainbottomKfsj() {
        return tvMainbottomKfsj;
    }

    public TextView getTvMainbottomZffs() {
        return tvMainbottomZffs;
    }

    public TextView getTvMainbottomTcf() {
        return tvMainbottomTcf;
    }

    public TextView getTvMainbottomXxdz() {
        return tvMainbottomXxdz;
    }

    public TextView getTv_mainbottom_pl() {
        return tv_mainbottom_pl;
    }

    public MainFragmenBoDa(View headerRootView) {
        ButterKnife.bind(this, headerRootView);
    }
}
