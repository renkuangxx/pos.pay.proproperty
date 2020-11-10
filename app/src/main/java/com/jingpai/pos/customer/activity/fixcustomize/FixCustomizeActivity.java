package com.jingpai.pos.customer.activity.fixcustomize;


import android.view.View;
import android.view.WindowManager;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.fragment.FixCustomizeFirstFragment;
import com.jingpai.pos.customer.fragment.FixCustomizeSecondFragment;
import com.jingpai.pos.customer.fragment.FixCustomizeThreeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
    * 时间: 2020/2/18
    * 功能:
    */
    public class FixCustomizeActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Pair<String, Fragment>> items;

    @Override
    protected int getLayout() {
        getWindow().setSoftInputMode(   WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return R.layout.activity_fix_customize;
    }


    @Override
    protected void initData() {
        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>("房屋信息",  new FixCustomizeFirstFragment(0)));
        items.add(new Pair<String, Fragment>("居住信息",  new FixCustomizeSecondFragment(1)));
        items.add(new Pair<String, Fragment>("装修计划", new FixCustomizeThreeFragment(2)));
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }
    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        finish();
    }

        private class MainAdapter extends FragmentPagerAdapter {

            MainAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {
                return items.get(position).second;
            }

            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return items.get(position).first;
            }
        }
}