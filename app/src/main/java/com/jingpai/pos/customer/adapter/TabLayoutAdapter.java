package com.jingpai.pos.customer.adapter;

import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By：jinheng.liu
 * on 2020/9/17
 */
public class TabLayoutAdapter extends FragmentPagerAdapter {
    private List<Pair<String, Fragment>> mItems = new ArrayList<>();
    public TabLayoutAdapter(FragmentManager fm, List<Pair<String, Fragment>> items) {
        super(fm);
        mItems = items;
    }

    @Override
    public Fragment getItem(int position) {
        return mItems.get(position).second;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItems.get(position).first;
    }
}
