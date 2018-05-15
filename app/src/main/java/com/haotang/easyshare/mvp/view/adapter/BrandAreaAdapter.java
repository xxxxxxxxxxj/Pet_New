package com.haotang.easyshare.mvp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.BrandAreaBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.SelectedCarBean;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.view.activity.ButlerActivity;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 11:20
 */
public class BrandAreaAdapter extends RecyclerView.Adapter {
    public OnBannerItemListener onBannerItemListener = null;

    public interface OnBannerItemListener {
        public void OnBannerItem(int position);
    }

    public void setOnBannerItemListener(OnBannerItemListener onBannerItemListener) {
        this.onBannerItemListener = onBannerItemListener;
    }
    /**
     * 4种类型
     */
    /**
     * 类型1：banner--使用banner实现
     */
    public static final int BANNER_BANNER1 = 1;
    /**
     * 类型2：热销--使用TextView实现
     */
    public static final int REXIAO_TV2 = 2;
    /**
     * 类型3：热销车加帖子--使用LinearLayout实现
     */
    public static final int REXIAOCAR_LL3 = 3;

    /**
     * 类型4：广告--使用RecyclerView实现
     */
    public static final int AD_GV4 = 4;


    /**
     * 当前类型
     */
    public int currentType = BANNER_BANNER1;

    private final Context mContext;
    private final List<BrandAreaBean> brandAreaList;
    /**
     * 以后用它来初始化布局
     */
    private final LayoutInflater mLayoutInflater;

    public BrandAreaAdapter(Context mContext, List<BrandAreaBean> brandAreaList) {
        this.mContext = mContext;
        this.brandAreaList = brandAreaList;
        //以后用它来初始化布局
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 相当于getView创建ViewHolder布局
     *
     * @param parent
     * @param viewType 当前的类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER_BANNER1) {
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.brandarea_banner_viewpager, parent, false));
        } else if (viewType == REXIAO_TV2) {
            return new ReXiaoTvViewHolder(mContext, mLayoutInflater.inflate(R.layout.brandarea_rexiao_tv, parent, false));
        } else if (viewType == REXIAOCAR_LL3) {
            return new ReXiaoCarLlViewHolder(mContext, mLayoutInflater.inflate(R.layout.brandarea_rexiao_ll, parent, false));
        } else if (viewType == AD_GV4) {
            return new AdGvViewHolder(mContext, mLayoutInflater.inflate(R.layout.brandarea_ad_gv, parent, false));
        }
        return null;
    }

    /**
     * 相当于getView中的绑定数据模块
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER_BANNER1) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(brandAreaList.get(position).getBannerBean());
        } else if (getItemViewType(position) == REXIAO_TV2) {
            ReXiaoTvViewHolder reXiaoTvViewHolder = (ReXiaoTvViewHolder) holder;
            reXiaoTvViewHolder.setData(brandAreaList.get(position).getReXiaOBean());
        } else if (getItemViewType(position) == REXIAOCAR_LL3) {
            ReXiaoCarLlViewHolder reXiaoCarLlViewHolder = (ReXiaoCarLlViewHolder) holder;
            //reXiaoCarLlViewHolder.setData(brandAreaList.get(position).getCarInfoBean());
        } else if (getItemViewType(position) == AD_GV4) {
            AdGvViewHolder adGvViewHolder = (AdGvViewHolder) holder;
            adGvViewHolder.setData(brandAreaList.get(position).getAdBean());
        }
    }

    /**
     * 总共有多少个item
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return brandAreaList.size();
    }

    /**
     * 得到类型
     */
    @Override
    public int getItemViewType(int position) {
        switch (brandAreaList.get(position).getItemType()) {
            case BANNER_BANNER1:
                currentType = BANNER_BANNER1;
                break;
            case REXIAO_TV2:
                currentType = REXIAO_TV2;
                break;
            case REXIAOCAR_LL3:
                currentType = REXIAOCAR_LL3;
                break;
            case AD_GV4:
                currentType = AD_GV4;
                break;
        }
        return currentType;
    }


    class AdGvViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private RecyclerView rv_brand_area_ad;
        private ImageView iv_brand_area_ad_close;

        public AdGvViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            rv_brand_area_ad = (RecyclerView) itemView.findViewById(R.id.rv_brand_area_ad);
            iv_brand_area_ad_close = (ImageView) itemView.findViewById(R.id.iv_brand_area_ad_close);
        }

        public void setData(List<BrandAreaBean.AdBean> dapeiqs6data) {
            rv_brand_area_ad.setHasFixedSize(true);
            rv_brand_area_ad.setNestedScrollingEnabled(false);
            NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                    NoScollFullGridLayoutManager(rv_brand_area_ad, mContext, 3, GridLayoutManager.VERTICAL, false);
            noScollFullGridLayoutManager.setScrollEnabled(false);
            rv_brand_area_ad.setLayoutManager(noScollFullGridLayoutManager);
            if (rv_brand_area_ad.getItemDecorationCount() <= 0) {
                rv_brand_area_ad.addItemDecoration(new GridSpacingItemDecoration(3,
                        mContext.getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                        mContext.getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                        false));
            }
            rv_brand_area_ad.setAdapter(new BrandAreaAdAdapter(R.layout.item_brandarea_ad
                    , dapeiqs6data));
            iv_brand_area_ad_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerItemListener != null) {
                        onBannerItemListener.OnBannerItem(getLayoutPosition());
                    }
                }
            });
        }
    }


    class ReXiaoTvViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private TextView tv_brandarea_rexiaotv;

        public ReXiaoTvViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_brandarea_rexiaotv = (TextView) itemView.findViewById(R.id.tv_brandarea_rexiaotv);
        }

        public void setData(BrandAreaBean.ReXiaOBean module1data) {
            StringUtil.setText(tv_brandarea_rexiaotv, module1data.getDesc(), "", View.VISIBLE, View.VISIBLE);
        }
    }

    static class ReXiaoCarLlViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private ImageView iv_item_allbands_lxgj;
        private ImageView iv_item_allbands_img;
        private TextView tv_item_allbands_name;
        private TextView tv_item_allbands_xuhang;
        private TextView tv_item_allbands_price;

        private ImageView iv_item_hotpoint_img;
        private TextView tv_item_hotpoint_date;
        private TextView tv_item_hotpoint_num;
        private ImageView iv_item_hotpoint_userimg;
        private TextView tv_item_hotpoint_username;
        private TextView tv_item_hotpoint_name;

        ReXiaoCarLlViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            iv_item_allbands_lxgj = (ImageView) itemView.findViewById(R.id.iv_item_allbands_lxgj);
            iv_item_allbands_img = (ImageView) itemView.findViewById(R.id.iv_item_allbands_img);
            tv_item_allbands_name = (TextView) itemView.findViewById(R.id.tv_item_allbands_name);
            tv_item_allbands_xuhang = (TextView) itemView.findViewById(R.id.tv_item_allbands_xuhang);
            tv_item_allbands_price = (TextView) itemView.findViewById(R.id.tv_item_allbands_price);

            iv_item_hotpoint_img = (ImageView) itemView.findViewById(R.id.iv_item_hotpoint_img);
            tv_item_hotpoint_date = (TextView) itemView.findViewById(R.id.tv_item_hotpoint_date);
            tv_item_hotpoint_num = (TextView) itemView.findViewById(R.id.tv_item_hotpoint_num);
            iv_item_hotpoint_userimg = (ImageView) itemView.findViewById(R.id.iv_item_hotpoint_userimg);
            tv_item_hotpoint_username = (TextView) itemView.findViewById(R.id.tv_item_hotpoint_username);
            tv_item_hotpoint_name = (TextView) itemView.findViewById(R.id.tv_item_hotpoint_name);
        }

        public void setData(HotPoint.DataBean pinpai2data) {
            if (pinpai2data != null) {
                GlideUtil.loadNetImg(mContext, pinpai2data.getCarIcon(), iv_item_allbands_img, R.mipmap.ic_image_load);
                StringUtil.setText(tv_item_allbands_name, pinpai2data.getCarName(), "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tv_item_allbands_xuhang, pinpai2data.getBatteryLife(), "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tv_item_allbands_price, "¥" + pinpai2data.getCarPrice() + "万", "", View.VISIBLE, View.VISIBLE);
                iv_item_allbands_lxgj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, ButlerActivity.class));
                    }
                });
                GlideUtil.loadNetCircleImg(mContext, pinpai2data.getHeadImg(), iv_item_hotpoint_userimg, R.mipmap.ic_image_load_circle);
                GlideUtil.loadNetImg(mContext, pinpai2data.getIcon(), iv_item_hotpoint_img, R.mipmap.ic_image_load);
                StringUtil.setText(tv_item_hotpoint_date, pinpai2data.getCreateTime(), "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tv_item_hotpoint_num, pinpai2data.getVisitors() + "阅读", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tv_item_hotpoint_username, pinpai2data.getUserName(), "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tv_item_hotpoint_name, pinpai2data.getTitle(), "", View.VISIBLE, View.VISIBLE);
            }
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            banner = (Banner) itemView.findViewById(R.id.banner_switch_city);
        }

        public void setData(List<BrandAreaBean.BannerBean> module0data) {
            //得到图片地址的集合
            List<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < module0data.size(); i++) {
                String image = module0data.get(i).getImg();
                imageUrls.add(image);
            }
            banner.setImages(imageUrls).setImageLoader(new GlideImageLoader()).start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                }
            });
        }
    }
}
