package com.haotang.easyshare.mvp.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.view.adapter.CarDetailPicAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.ljy.devring.other.RingLog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 14:12
 */
public class CarDetailPicFragment extends BaseFragment {
    @BindView(R.id.rv_car_detail_pic)
    RecyclerView rvCarDetailPic;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.cardetail_pic;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        ArrayList<AdvertisementBean.DataBean> imgs = arguments.getParcelableArrayList("imgs");
        RingLog.e("imgs = "+imgs);
        if(imgs != null && imgs.size() > 0){
            rvCarDetailPic.setHasFixedSize(true);
            rvCarDetailPic.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvCarDetailPic.setAdapter(new CarDetailPicAdapter(R.layout.item_cardetail_pic, imgs,getActivity()));
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void requestData() {

    }
}
