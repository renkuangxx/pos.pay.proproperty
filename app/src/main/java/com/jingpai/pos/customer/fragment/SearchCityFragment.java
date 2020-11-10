package com.jingpai.pos.customer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jingpai.pos.customer.CitySelect.util.PinyinUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.FilterListener;
import com.jingpai.pos.customer.activity.census.adapter.ChoseMinzuAdapter;
import com.jingpai.pos.customer.bean.City;
import com.jingpai.pos.customer.bean.CityListBean;
import com.jingpai.pos.customer.mvp.YezhuPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 86173
 */
public class SearchCityFragment extends Fragment {
    private LinearLayout mLlSearch;
    private EditText mEtSearch;
    private TextView mTvCancel;
    private TextView mTvMyTip;
    private String[] mStrings;
    private SmartRefreshLayout mSrlSearch;
    private ListView listView;
    private ChoseMinzuAdapter adapter;
    List<City> cityList = new ArrayList<>();
    List<String> list = new ArrayList<>();
    List<CityListBean.DataBean> listBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chose_minzu, container, false);
        initView(view);
        initData();
        setListeners();
        return view;
    }

    private void initData() {
        list.clear();
//        String json = ReadAssetsFileUtil.getJson(getContext(), "city.json");
//        CityPickerBean bean = GsonUtil.getBean(json, CityPickerBean.class);

        YezhuPresenter yezhuPresenter = new YezhuPresenter();
        yezhuPresenter.cityList(jsonArray -> {
            if (jsonArray == null) return;
            HashSet<City> citys = new HashSet<>();
            listBeans = jsonArray.toJavaList(CityListBean.DataBean.class);
            if (listBeans.size() == 0) return;
            for (int i = 0; i < listBeans.size(); i++) {
                String name = listBeans.get(i).getCityName();
                citys.add(new City(Integer.parseInt(listBeans.get(i).getCityCode()), name, PinyinUtils.getPinYin(name), false));
            }

//            String json = ReadAssetsFileUtil.getJson(this, "city.json");
//            CityPickerBean bean = GsonUtil.getBean(json, CityPickerBean.class);
//            for (AreasBean areasBean : bean.data.areas) {
//                String name = areasBean.name.replace("　", "");
//                citys.add(new City(areasBean.id, name, PinyinUtils.getPinYin(name), areasBean.is_hot == 1));
//                for (AreasBean.ChildrenBeanX childrenBeanX : areasBean.children) {
//                    citys.add(new City(childrenBeanX.id, childrenBeanX.name, PinyinUtils.getPinYin(childrenBeanX.name), childrenBeanX.is_hot == 1));
//                }
//            }
            //set转换list
            ArrayList<City> cities = new ArrayList<>(citys);
            //按照字母排序
            Collections.sort(cities, new Comparator<City>() {
                @Override
                public int compare(City city, City t1) {
                    return city.getPinyin().compareTo(t1.getPinyin());
                }
            });
            for (int a = 0; a < cities.size(); a++) {
                list.add(cities.get(a).getName());
            }

            // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
            adapter = new ChoseMinzuAdapter(list, getContext(), new FilterListener() {
                @Override
                public void getFilterData(List<String> list) {
                    // 这里可以拿到过滤后数据，所以在这里可以对搜索后的数据进行操作
                    Log.e("TAG", "接口回调成功");
                    Log.e("TAG", list.toString());
                    setItemClick(list);
                }
            });
            listView.setAdapter(adapter);
        });


//        HashSet<City> citys = new HashSet<>();
//        for (AreasBean areasBean : bean.data.areas) {
//            String name = areasBean.name.replace("　", "");
//            citys.add(new City(areasBean.id, name, PinyinUtils.getPinYin(name), areasBean.is_hot == 1));
//            for (AreasBean.ChildrenBeanX childrenBeanX : areasBean.children) {
//                citys.add(new City(childrenBeanX.id, childrenBeanX.name, PinyinUtils.getPinYin(childrenBeanX.name), childrenBeanX.is_hot == 1));
//            }
//        }
//        //set转换list
//        ArrayList<City> cities = new ArrayList<>(citys);
//        //按照字母排序
//        Collections.sort(cities, new Comparator<City>() {
//            @Override
//            public int compare(City city, City t1) {
//                return city.getPinyin().compareTo(t1.getPinyin());
//            }
//        });
//        for (int a =0;a<cities.size();a++){
//            list.add(cities.get(a).getName());
//        }


    }

    public void getCityData() {

    }

    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);

        /**
         * 对编辑框添加文本改变监听，搜索的具体功能在这里实现
         * 很简单，文本该变的时候进行搜索。关键方法是重写的onTextChanged（）方法。
         */
        mEtSearch.addTextChangedListener(new TextWatcher() {

            /**
             *
             * 编辑框内容改变的时候会执行该方法
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 如果adapter不为空的话就根据编辑框中的内容来过滤数据
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                    mTvMyTip.setVisibility(View.VISIBLE);
                    mTvMyTip.setText("搜索结果");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                mTvMyTip.setText("等待搜索");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(s)) {
//                    ToastUtils.showShort("内容不能为空");
                }
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    /**
     * 给listView添加item的单击事件
     *
     * @param filter_lists 过滤后的数据集
     */
    protected void setItemClick(final List<String> filter_lists) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
//                Toast.makeText(getContext(), filter_lists.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("cityName", filter_lists.get(position));
                getActivity().setResult(101, intent);
                getActivity().finish();
            }
        });
    }

    private void initView(View view) {
        mLlSearch = view.findViewById(R.id.ll_search);
        mEtSearch = view.findViewById(R.id.et_search);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mTvMyTip = view.findViewById(R.id.tv_my_tip);
        listView = view.findViewById(R.id.recyclerView_search);
        mTvMyTip.setVisibility(View.GONE);

        mEtSearch.setFocusable(true);
        mEtSearch.setFocusableInTouchMode(true);
        mEtSearch.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(mEtSearch, 0);
                           }
                       },
                200);
    }

}
