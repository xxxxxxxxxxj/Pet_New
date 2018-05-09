package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 10:42
 */
public class BrandAreaBean {
    private int itemType;
    private List<BannerBean> bannerBean;
    private ReXiaOBean reXiaOBean;
    private CarInfoBean carInfoBean;
    private List<AdBean> adBean;


    public BrandAreaBean(int itemType,CarInfoBean carInfoBean) {
        this.carInfoBean = carInfoBean;
        this.itemType = itemType;
    }

    public BrandAreaBean(int itemType,ReXiaOBean reXiaOBean) {
        this.reXiaOBean = reXiaOBean;
        this.itemType = itemType;
    }

    public BrandAreaBean(int itemType,List<BannerBean> bannerBean) {
        this.bannerBean = bannerBean;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public List<BannerBean> getBannerBean() {
        return bannerBean;
    }

    public void setBannerBean(List<BannerBean> bannerBean) {
        this.bannerBean = bannerBean;
    }

    public ReXiaOBean getReXiaOBean() {
        return reXiaOBean;
    }

    public void setReXiaOBean(ReXiaOBean reXiaOBean) {
        this.reXiaOBean = reXiaOBean;
    }

    public CarInfoBean getCarInfoBean() {
        return carInfoBean;
    }

    public void setCarInfoBean(CarInfoBean carInfoBean) {
        this.carInfoBean = carInfoBean;
    }

    public List<AdBean> getAdBean() {
        return adBean;
    }

    public void setAdBean(List<AdBean> adBean) {
        this.adBean = adBean;
    }

    public BrandAreaBean() {
    }

    public BrandAreaBean(int itemType, List<BannerBean> bannerBean, ReXiaOBean reXiaOBean, CarInfoBean carInfoBean,
                         List<AdBean> adBean) {
        this.itemType = itemType;
        this.bannerBean = bannerBean;
        this.reXiaOBean = reXiaOBean;
        this.carInfoBean = carInfoBean;
        this.adBean = adBean;
    }

    public static class BannerBean {
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public BannerBean() {
        }

        public BannerBean(String img) {
            this.img = img;
        }
    }

    public static class ReXiaOBean {
        private String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public ReXiaOBean() {
        }

        public ReXiaOBean(String desc) {
            this.desc = desc;
        }
    }

    public static class CarInfoBean {
        private SelectedCarBean selectedCarBean;
        private HotPoint hotPoint;

        public SelectedCarBean getSelectedCarBean() {
            return selectedCarBean;
        }

        public void setSelectedCarBean(SelectedCarBean selectedCarBean) {
            this.selectedCarBean = selectedCarBean;
        }

        public HotPoint getHotPoint() {
            return hotPoint;
        }

        public void setHotPoint(HotPoint hotPoint) {
            this.hotPoint = hotPoint;
        }

        public CarInfoBean() {
        }

        public CarInfoBean(SelectedCarBean selectedCarBean, HotPoint hotPoint) {
            this.selectedCarBean = selectedCarBean;
            this.hotPoint = hotPoint;
        }
    }

    public static class AdBean {
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public AdBean() {
        }

        public AdBean(String img) {
            this.img = img;
        }
    }
}
