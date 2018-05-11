package com.haotang.easyshare.mvp.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;

import java.util.ArrayList;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/11 13:48
 */
public class MyFragChargePagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment> mFragments;

    public MyFragChargePagerAdapter(FragmentManager fm, ArrayList<BaseFragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}