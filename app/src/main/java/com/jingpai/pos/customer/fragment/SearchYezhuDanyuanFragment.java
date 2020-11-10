package com.jingpai.pos.customer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.jingpai.pos.customer.activity.authentication.activity.YezhuRegisterSearchFangwuActivity;
import com.jingpai.pos.customer.activity.authentication.FilterYezhuListener;
import com.jingpai.pos.customer.activity.census.adapter.ChoseYezhuDanyuanAdapter;
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
public class SearchYezhuDanyuanFragment extends Fragment {
    private LinearLayout mLlSearch;
    private EditText mEtSearch;
    private TextView mTvCancel;
    private TextView mTvMyTip;
    private String[] mStrings;
    private SmartRefreshLayout mSrlSearch;
    private ListView listView;
    private ChoseYezhuDanyuanAdapter adapter;
    List<String> list = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    String danyunInfo;
    int buildingId;
    List<XiaoquDanyuanInfoBean> listBean = new ArrayList<>();
    String info;
    String edT;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chose_danyuan, container, false);
        initView(view);
        initData();
        setListeners();
        return view;
    }

    private void initData() {
        list.clear();
        YezhuPresenter yezhuPresenter = new YezhuPresenter();
        buildingId = LocalCache.getYezhuBuildingId("buildingId");
        LocalCache.clearYezhuBuildingId("buildingId");
        yezhuPresenter.getDanyuanseList(buildingId, jsonArray -> {
            listBean = jsonArray.toJavaList(XiaoquDanyuanInfoBean.class);
            if (listBean == null || listBean.size() == 0)
                return;

            for (int a = 0 ; a < listBean.size() ; a++){
                list.add(listBean.get(a).getName());
                list2.add(listBean.get(a).getUnitId());
            }


            // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
            adapter = new ChoseYezhuDanyuanAdapter(listBean, getContext(), new FilterYezhuListener() {
                @Override
                public void getFilterDataloudong(List<String> list, List<YezhuXiaoquLoudongInfoBean.data> dataList) {

                }

                @Override
                public void getFilterDatadanyuan(List<String> list, List<XiaoquDanyuanInfoBean> dataList) {
                    if (TextUtils.isEmpty(edT)) {
                        setItemClick(list, list2);
                    } else {
                        setItemClick1(dataList);

                    }
                }

                @Override
                public void getFilterDatafangwu(List<String> list, List<XiaoquFangwuInfoBean> dataList) {

                }

            });
            listView.setAdapter(adapter);
        });
    }

    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
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
                if(adapter != null){
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
                if (TextUtils.isEmpty(s)){
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
    protected void setItemClick(final List<String> filter_lists, final List<Integer> list2)  {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
//                Toast.makeText(getContext(), filter_lists.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), YezhuRegisterSearchFangwuActivity.class);
                intent.putExtra("unitId",list2.get(position));
                intent.putExtra("danyunInfo",danyunInfo+filter_lists.get(position));
                startActivity(intent);
//                getActivity().finish();
            }
        });
    }

    protected void setItemClick1(final List<XiaoquDanyuanInfoBean> listBean) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
//                Toast.makeText(getContext(), filter_lists.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), YezhuRegisterSearchFangwuActivity.class);
                intent.putExtra("unitId",listBean.get(position).getUnitId());
                intent.putExtra("danyunInfo",danyunInfo+listBean.get(position).getName());
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
        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        danyunInfo = null;
        if(bundle!=null){
            danyunInfo = bundle.getString("loudongInfo");
        }
    }

}
