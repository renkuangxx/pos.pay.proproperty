package com.jingpai.pos.activity.payment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.InvoiceHistoryAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.bean.common.PageBefore;
import com.jingpai.pos.customer.bean.payment.BillHistoryBean;
import com.jingpai.pos.customer.mvp.presenter.payment.PaymentPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * date 2020/5/26
 * function
 */
public class InvoiceActivity extends BaseActivity {
    @BindView(R.id.rv_pay_history)
    RecyclerView rvPayHistory;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecord;
    @BindView(R.id.srl_pay_history)
    SmartRefreshLayout srl_pay_history;
    private PaymentPresenter paymentPresenter;
    private InvoiceHistoryAdapter payBillAdapter;
    private String before = "0";
    private boolean hasMore = true;
    private List<BillHistoryBean> payBillList = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_payment_history;
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    protected void initData() {
        paymentPresenter = new PaymentPresenter();
        payBillAdapter = new InvoiceHistoryAdapter(this, R.layout.bill_history_item, payBillList);
        rvPayHistory.setLayoutManager(new LinearLayoutManager(this));
        rvPayHistory.setAdapter(payBillAdapter);

        initListener();

        initPay();
    }

    private void initListener() {
        if (srl_pay_history !=null){
            srl_pay_history.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //qingqiu
                    initPay();

                }
            });
        }

    }


    private void initPay() {
        paymentPresenter.queryBillHistory(before, 20, pageBefore -> {
            srl_pay_history.finishRefresh();
            initHistoryList(pageBefore);
            llNoRecord.setVisibility(payBillList.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void initHistoryList(PageBefore<JSONArray> pageBefore) {

        if (pageBefore != null) {
            JSONArray jsonArray = pageBefore.getData();
            if (jsonArray == null) return;
            List<BillHistoryBean> list = jsonArray.toJavaList(BillHistoryBean.class);
            payBillList.addAll(list);
            payBillAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
