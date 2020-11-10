package com.jingpai.pos.customer.activity.repairs;


import android.view.View;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.fragment.MatterHistoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
    * 时间: 2020/2/18
    * 功能:
    */
    public class MatterHistoryActivity extends BaseActivity {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Pair<String, Fragment>> items;

    @Override
    protected int getLayout() {
        return R.layout.activity_matter_history;
    }


    @Override
    protected void initData() {
        items = new ArrayList<>();
        items.add(new Pair<String, Fragment>("处理中",  new MatterHistoryFragment(0)));
//        items.add(new Pair<String, Fragment>("进行中", new MatterHistoryFragment(2)));
//        items.add(new Pair<String, Fragment>("待确认", new MatterHistoryFragment(3)));
        items.add(new Pair<String, Fragment>("已完成", new MatterHistoryFragment(5)));
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
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