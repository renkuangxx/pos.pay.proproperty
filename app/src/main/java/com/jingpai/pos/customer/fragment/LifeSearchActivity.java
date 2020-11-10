package com.jingpai.pos.customer.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.LifeSearchAdapter;
import com.jingpai.pos.customer.bean.SearchBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.customer.views.UpdataInterface;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author 86173
 */
public class LifeSearchActivity extends AppCompatActivity implements UpdataInterface {
    private LinearLayout mLlSearch;
    private EditText mEtSearch;
    private TextView mTvCancel;
    private TextView mTvMyTip;
    private String[] mStrings;
    private SmartRefreshLayout mSrlSearch;
    private RecyclerView rv_list;
    private LifePresenter lifePresenter;
    private LifeSearchAdapter lifeListAdapter;
    private String  content;
    List<SearchBean.ListBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_life_search);
        initView();
        initListener();
    }

    private void initListener() {


        mEtSearch.addTextChangedListener(new TextWatcher() {

            /**
             * 编辑框内容改变的时候会执行该方法
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 如果adapter不为空的话就根据编辑框中的内容来过滤数据
//                if(adapter != null){
//                    adapter.getFilter().filter(s);
//                    mTvMyTip.setText("搜索结果");
//                }
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
                    ToastUtils.showShort("内容不能为空");
                    return;
                }
                content = s.toString();
                initData(content);
            }
        });
    }

    private void initData(String content) {
        list.clear();

        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("pageNum", 1);
        baseRequest.put("pageSize", 999);
        baseRequest.put("searchText", content);
        baseRequest.put("currentUserId", TextUtils.isEmpty(LocalCache.getUserId()) ? "" : LocalCache.getUserId());
        lifePresenter.searchQuery(baseRequest,searchBean -> {
            if (searchBean == null)return;
            list = searchBean.getList();
            if (list == null || list.size()==0)return;
            lifeListAdapter.setData(list);
        });
    }


    private void initView() {
        mLlSearch = findViewById(R.id.ll_search);
        mEtSearch = findViewById(R.id.et_search);
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvMyTip = findViewById(R.id.tv_my_tip);
        rv_list = findViewById(R.id.rv_list);

        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.INSTANCE.setTranslucentStatus(this);
        StatusBarUtil.INSTANCE.setStatusBarDarkTheme(this, true);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));

        lifePresenter = new LifePresenter();

        LinearLayoutManager horizontal = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_list.setLayoutManager(horizontal);
        lifeListAdapter = new LifeSearchAdapter(this, list,lifePresenter,new UpdataInterface() {
            @Override
            public void updataInterface() {
                initData(content);
            }
        });
        rv_list.setAdapter(lifeListAdapter);

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void updataInterface() {
        initData(content);
    }
}
