package com.jingpai.pos.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.tabs.TabLayout;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebFragment;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

public class ServerTabFragment extends Fragment {

    private TabLayout tab;
    private ViewPager viewPager;
    private LinearLayout ll_visibility;
    private RelativeLayout rl_main;
    private List<Pair<String, Fragment>> items;
    LifePresenter lifePresenter;
    private View view;
    public static ServerTabFragment getInstance() {
        return new ServerTabFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_server_tab, container, false);
            initView(view);
        }
        return view;
    }

    protected void initView(View view) {

        tab = (TabLayout) view.findViewById(R.id.tab);
        ll_visibility = (LinearLayout) view.findViewById(R.id.ll_visibility);
        rl_main = (RelativeLayout) view.findViewById(R.id.rl_main);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        StatusBarUtils.immersive(getActivity());
        StatusBarUtils.setPaddingSmart(getActivity(), rl_main);
        lifePresenter = new LifePresenter();
        items = new ArrayList<>();
        getDate();
    }

    public void reload(){
//        List<Fragment> fragmentList = getChildFragmentManager().getFragments();
//        List<Fragment> fragmentList1 = getFragmentManager().getFragments();
//        for (int i=0;i<fragmentList.size();i++){
//            if (fragmentList.get(i) instanceof CityLifeWebFragment){
//                ((CityLifeWebFragment) fragmentList.get(i)).loadUrl();
//            }
//        }

        getDate();
    }

    private void getDate(){

        lifePresenter.getTabList(new BasePresenter_Old.Callback<JSONArray>() {
            @Override
            public void success(JSONArray jsonArray) {
                items.clear();
                ll_visibility.setVisibility(jsonArray.size()==0?View.VISIBLE:View.GONE);

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = (JSONObject) JSONObject.toJSON(jsonArray.get(i));
                    String url = obj.getString("channelUrl");
                    String name = obj.getString("channelName");
                    if (TextUtils.isEmpty(url)) {
//                        items.add(new Pair<String, Fragment>(name, new LifeFragmentNew()));
                    } else {
                        CityLifeWebFragment cityLifeWebFragment = new CityLifeWebFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("url",url);//这里的titles就是我们要传的值
                        cityLifeWebFragment.setArguments(bundle);
                        items.add(new Pair<String, Fragment>(name,cityLifeWebFragment ));
                    }

                }
                if (items.size() > 4) {
                    tab.setTabMode(TabLayout.MODE_SCROLLABLE);
                } else {
                    tab.setTabMode(TabLayout.MODE_FIXED);
                }
                viewPager.setOffscreenPageLimit(jsonArray.size()-1);
                viewPager.setAdapter(new MainAdapter(getChildFragmentManager()));
                tab.setupWithViewPager(viewPager);
            }
        });
    }

    public void setTabVisibility(boolean visibility) {
        if (visibility) {
            tab.setVisibility(View.VISIBLE);
        } else {
            tab.setVisibility(View.GONE);
        }
    }
    public void setTabIndex(int index) {
        tab.getTabAt(index).select();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (getChildFragmentManager().getFragments().size() > 0) {
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            for (Fragment mFragment : fragments) {
                mFragment.onActivityResult(requestCode, resultCode, intent);
            }
        }
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
