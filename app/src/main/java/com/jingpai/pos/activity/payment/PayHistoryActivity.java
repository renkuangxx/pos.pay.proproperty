package com.jingpai.pos.activity.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.BuildConfig;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.adapter.BillHistoryAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.bean.common.PageBefore;
import com.jingpai.pos.customer.bean.payment.BillHistoryBean;
import com.jingpai.pos.customer.mvp.presenter.payment.PaymentPresenter;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.RecyclerViewUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Create by liujinheng
 * date 2020/5/26
 * function
 */
public class PayHistoryActivity extends BaseActivity {
    @BindView(R.id.rv_pay_history)
    RecyclerView rvPayHistory;
    @BindView(R.id.ll_no_record)
    LinearLayout llNoRecord;
    @BindView(R.id.srl_pay_history)
    SmartRefreshLayout srl_pay_history;
    private PaymentPresenter paymentPresenter;
    private BillHistoryAdapter payBillAdapter;
    private String before = "0";
    private boolean hasMore = true;
    private List<BillHistoryBean> payBillList = new ArrayList<>();
    private List<BillHistoryBean> applyBillList = new ArrayList<>();


    @Override
    protected int getLayout() {
        return R.layout.activity_payment_history;
    }

    @Override
    protected void initData() {
        paymentPresenter = new PaymentPresenter();
        payBillAdapter = new BillHistoryAdapter(this, R.layout.bill_history_item, payBillList);
        rvPayHistory.setLayoutManager(new LinearLayoutManager(this));
        rvPayHistory.setAdapter(payBillAdapter);

        rvPayHistory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (RecyclerViewUtil.isSlideToBottom(recyclerView)) {
                    if (hasMore) {
                        initPay();
                    }

                }
            }
        });
        initPay();
//        initApplyInfo();
        initListener();
    }

    private void initListener() {
        if (srl_pay_history != null) {
            srl_pay_history.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    initPay();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPay();

    }

    private void initApplyInfo() {
        paymentPresenter.queryBillHistory(before, 99, pageBefore -> {
            initHistoryList(pageBefore);
            llNoRecord.setVisibility(payBillList.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @OnClick(R.id.tv_right_btn)
    public void onPaymentRecords() {
        String url = BuildConfig.INVOICE_HISTORY_URL
                + "communityId="
                + LocalCache.getCurrentCommunityId()
                + "+&token="
                + LocalCache.getToken();
        startWebViewActivity(this, url, "");
    }

    /**
     * WebView打开公司简介url
     *
     * @param context
     * @param url
     * @throws JSONException
     */
    private void startWebViewActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, url);
        intent.putExtra(Constant.WEB_BACK, true);
        intent.putExtra(Constant.WEB_TITLE, title);
        context.startActivity(intent);
    }

    private void initPay() {
        paymentPresenter.queryBillHistory(before, 99, pageBefore -> {
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
            payBillList.clear();
            payBillList.addAll(list);
            payBillAdapter.notifyDataSetChanged();
            hasMore = pageBefore.isHasMore();
//            before = pageBefore.getBefore();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
