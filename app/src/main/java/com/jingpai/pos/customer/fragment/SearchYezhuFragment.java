package com.jingpai.pos.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ToastUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.authentication.activity.YezhuRegisterSearchDanyuanActivity;
import com.jingpai.pos.customer.activity.authentication.FilterYezhuListener;
import com.jingpai.pos.customer.activity.census.adapter.ChoseYezhuAdapter;
import com.jingpai.pos.customer.bean.XiaoquDanyuanInfoBean;
import com.jingpai.pos.customer.bean.XiaoquFangwuInfoBean;
import com.jingpai.pos.customer.bean.YezhuXiaoquLoudongInfoBean;
import com.jingpai.pos.customer.mvp.YezhuPresenter;
import com.jingpai.pos.customer.utils.LocalCache;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 */
public class SearchYezhuFragment extends Fragment {
    private LinearLayout mLlSearch;
    private EditText mEtSearch;
    private TextView mTvCancel;
    private TextView mTvMyTip;
    private String[] mStrings;
    private SmartRefreshLayout mSrlSearch;
    private ListView listView;
    private ChoseYezhuAdapter adapter;
    List<String> list = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    int communityId;
    String info;
    String edT;
    List<YezhuXiaoquLoudongInfoBean.data> listBean = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chose_loudong, container, false);
        initView(view);
        initData();
        setListeners();
        return view;
    }

    private void initData() {
        list.clear();
        YezhuPresenter yezhuPresenter = new YezhuPresenter();
        communityId = LocalCache.getYezhuCommunityId("communityId");
        LocalCache.clearYezhuCommunityId("communityId");
        yezhuPresenter.getLouList(communityId, jsonArray -> {
            listBean = jsonArray.toJavaList(YezhuXiaoquLoudongInfoBean.data.class);
            if (listBean == null || listBean.size() == 0)
                return;
            for (int i = 0; i < listBean.size(); i++) {
                list.add(listBean.get(i).getNumber());
                list2.add(listBean.get(i).getBuildingId());

            }
            adapter = new ChoseYezhuAdapter(listBean, getContext(), new FilterYezhuListener() {
                @Override
                public void getFilterDataloudong(List<String> list, List<YezhuXiaoquLoudongInfoBean.data> dataList) {
                    // 这里可以拿到过滤后数据，所以在这里可以对搜索后的数据进行操作
                    Log.e("TAG", "接口回调成功");
                    Log.e("TAG", list.toString());
                    if (TextUtils.isEmpty(edT)) {
                        setItemClick(list, list2);
                    } else {
                        setItemClick1(dataList);

                    }
                }

                @Override
                public void getFilterDatadanyuan(List<String> list, List<XiaoquDanyuanInfoBean> dataList) {

                }

                @Override
                public void getFilterDatafangwu(List<String> list, List<XiaoquFangwuInfoBean> dataList) {

                }
            });
            listView.setAdapter(adapter);
        });


//        mStrings = getResources().getStringArray(R.array.date);
//        for (int a =0;a<mStrings.length;a++){
//            list.add(mStrings[a]);
//        }

        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取

    }

    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        if (TextUtils.isEmpty(edT)) {
            setItemClick(list, list2);
        } else {
            setItemClick1(listBean);

        }

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
                    edT = s.toString();
                    mTvMyTip.setText("搜索结果");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (TextUtils.isEmpty(s)) {
                    edT = s.toString();
                    ToastUtils.showShort("内容不能为空");
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
    protected void setItemClick(final List<String> filter_lists, final List<Integer> list2) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
//                Toast.makeText(getContext(), filter_lists.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), YezhuRegisterSearchDanyuanActivity.class);
                intent.putExtra("buildingId", list2.get(position));
                intent.putExtra("loudongInfo", info + filter_lists.get(position));
                startActivity(intent);
//                getActivity().finish();
            }
        });
    }

    protected void setItemClick1( final List<YezhuXiaoquLoudongInfoBean.data> listBean) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
//                Toast.makeText(getContext(), filter_lists.get(position), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), YezhuRegisterSearchDanyuanActivity.class);
                intent.putExtra("buildingId", listBean.get(position).getBuildingId());
                intent.putExtra("loudongInfo", info + listBean.get(position).getNumber());
                startActivity(intent);
//                getActivity().finish();
            }
        });
    }

    private void initView(View view) {
        mLlSearch = view.findViewById(R.id.ll_search);
        mEtSearch = view.findViewById(R.id.et_search);
        mTvCancel = view.findViewById(R.id.tv_cancel);
        mTvMyTip = view.findViewById(R.id.tv_my_tip);
        listView = view.findViewById(R.id.recyclerView_search);

        Bundle bundle = this.getArguments();//得到从Activity传来的数据
        info = null;
        if (bundle != null) {
            info = bundle.getString("info");
        }
    }

}
