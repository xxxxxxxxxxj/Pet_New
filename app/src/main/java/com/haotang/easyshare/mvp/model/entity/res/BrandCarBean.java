package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/6/7 10:01
 */
public class BrandCarBean {

    /**
     * code : 0
     * data : [{"dataset":[{"cars":[{"car":"奥迪A3新能源","price":"39.98-39.98万元","brandId":3,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549069375366021.jpg","id":28,"batteryLife":""},{"car":"奥迪Q7新能源","price":"92.88-92.88万元","brandId":3,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549072013203471.jpg","id":30,"batteryLife":""}],"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15247102182087551003.jpg","id":3,"brand":"奥迪","firstLetter":"A"}],"firstLetter":"A"}]
     */

    private int code;
    private List<DataBean> data;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dataset : [{"cars":[{"car":"奥迪A3新能源","price":"39.98-39.98万元","brandId":3,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549069375366021.jpg","id":28,"batteryLife":""},{"car":"奥迪Q7新能源","price":"92.88-92.88万元","brandId":3,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549072013203471.jpg","id":30,"batteryLife":""}],"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15247102182087551003.jpg","id":3,"brand":"奥迪","firstLetter":"A"}]
         * firstLetter : A
         */

        private String firstLetter;
        private List<DatasetBean> dataset;

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public List<DatasetBean> getDataset() {
            return dataset;
        }

        public void setDataset(List<DatasetBean> dataset) {
            this.dataset = dataset;
        }

        public static class DatasetBean {
            /**
             * cars : [{"car":"奥迪A3新能源","price":"39.98-39.98万元","brandId":3,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549069375366021.jpg","id":28,"batteryLife":""},{"car":"奥迪Q7新能源","price":"92.88-92.88万元","brandId":3,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549072013203471.jpg","id":30,"batteryLife":""}]
             * icon : http://img.sayiyinxiang.com/api/brand/imgs/15247102182087551003.jpg
             * id : 3
             * brand : 奥迪
             * firstLetter : A
             */

            private String icon;
            private int id;
            private String brand;
            private String firstLetter;
            private List<CarsBean> cars;
            private boolean isClick;

            public boolean isClick() {
                return isClick;
            }

            public void setClick(boolean click) {
                isClick = click;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getFirstLetter() {
                return firstLetter;
            }

            public void setFirstLetter(String firstLetter) {
                this.firstLetter = firstLetter;
            }

            public List<CarsBean> getCars() {
                return cars;
            }

            public void setCars(List<CarsBean> cars) {
                this.cars = cars;
            }

            public static class CarsBean {
                /**
                 * car : 奥迪A3新能源
                 * price : 39.98-39.98万元
                 * brandId : 3
                 * icon : http://img.sayiyinxiang.com/api/brand/imgs/15246549069375366021.jpg
                 * id : 28
                 * batteryLife :
                 */

                private String car;
                private String price;
                private int brandId;
                private String icon;
                private int id;
                private String batteryLife;

                public String getCar() {
                    return car;
                }

                public void setCar(String car) {
                    this.car = car;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getBrandId() {
                    return brandId;
                }

                public void setBrandId(int brandId) {
                    this.brandId = brandId;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getBatteryLife() {
                    return batteryLife;
                }

                public void setBatteryLife(String batteryLife) {
                    this.batteryLife = batteryLife;
                }
            }
        }
    }
}
