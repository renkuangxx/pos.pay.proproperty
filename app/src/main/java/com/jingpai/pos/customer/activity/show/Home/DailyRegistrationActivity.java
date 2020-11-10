package com.jingpai.pos.customer.activity.show.Home;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.healthcheck.AntiepidemicActivity;
import com.jingpai.pos.customer.activity.healthcheck.DailyParticularsActivity;
import com.jingpai.pos.customer.adapter.DailyRegistrationAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.DailyRegistrationBean;
import com.jingpai.pos.customer.mvp.presenter.show.home.HealthQueryPresenter;
import com.jingpai.pos.customer.utils.Intents;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 时间: 2020/3/9
 * 功能: 防疫 每日登记
 */
public class DailyRegistrationActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    String before = "0";
    String pageSize = "20";
    @BindView(R.id.visibility)
    LinearLayout visibility;
    @BindView(R.id.yes_btn)
    TextView yesBtn;
    @BindView(R.id.rl_daily_btn)
    RelativeLayout rlDailyBtn;

    private HealthQueryPresenter healthQueryPresenter;
    private DailyRegistrationAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.daily_registration_activity;
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DailyRegistrationAdapter(R.layout.daily_registration_item);
        mRecyclerView.setAdapter(adapter);

        healthQueryPresenter = new HealthQueryPresenter();
        healthQueryPresenter.HealthData(before, pageSize, this::healthData);
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        healthQueryPresenter.HealthData(before, pageSize, this::healthData);
    }

    public void healthData(DailyRegistrationBean.DataBeanX dataBeanX) {
        smartRefreshLayout.finishRefresh();
        if (dataBeanX.getList() == null) {
            visibility.setVisibility(View.VISIBLE);
        } else {
            visibility.setVisibility(View.GONE);
            getData(dataBeanX);
        }
        if (dataBeanX.isSubmit()) {
            rlDailyBtn.setVisibility(View.GONE);
        } else {
            rlDailyBtn.setVisibility(View.VISIBLE);
        }

    }

    private void getData(DailyRegistrationBean.DataBeanX dataBeanX) {
        List<DailyRegistrationBean.DataBeanX.ListBean.DataBean> data = dataBeanX.getList().getData();

        adapter.replaceData(data);
        adapter.DailyParticularsCallBack(id -> {
            Intent intent = new Intent(DailyRegistrationActivity.this, DailyParticularsActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });
    }

    @OnClick(R.id.yes_btn)
    public void onViewClicked() {
        Intents.getInstence().intent(DailyRegistrationActivity.this, AntiepidemicActivity.class);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        healthQueryPresenter.HealthData(before, pageSize, this::healthData);
    }


}