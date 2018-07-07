package com.jc.st.fragmentdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jc.st.fragmentdemo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　       	██ ━██  ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * Created by st on 2018/1/17.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<BaseFragment>mbaseFragments=new ArrayList<>();
//    private final List<String>mbaseFragmentsTitle=new ArrayList<>();
    private final String[] mTitles;

//    public TabViewPagerAdapter(FragmentManager fm ) {
//        super(fm);
//    }
//    public void addFragments(Fragment fragment , String fragmentTitle ){
//        mbaseFragments.add((BaseFragment) fragment);
//
//        mbaseFragmentsTitle.add(fragmentTitle);
//
//    }

    public TabViewPagerAdapter(FragmentManager fm, ArrayList<BaseFragment> mbaseFragments, String[] mTitles) {
        super(fm);
        this.mbaseFragments = mbaseFragments;
        this.mTitles = mTitles;
    }


//    @Override
//    public Fragment getItem(int position) {
//        return mbaseFragments.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return mbaseFragments.size();
//    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mbaseFragmentsTitle.get(position);
//    }


    @Override
    public int getCount() {
        return mbaseFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mbaseFragments.get(position);
    }
}
