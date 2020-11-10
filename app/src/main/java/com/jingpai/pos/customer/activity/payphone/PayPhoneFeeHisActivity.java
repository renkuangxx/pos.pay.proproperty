package com.jingpai.pos.customer.activity.payphone;

import android.view.View;
import android.view.ViewStub;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.PayPhoneFeeHisAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.bean.User;
import com.jingpai.pos.customer.bean.payphone.PayFeeHisBean;
import com.jingpai.pos.customer.mvp.presenter.payphone.GetAddFeeHisPresenter;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 手机充值历史记录
 */
public class PayPhoneFeeHisActivity extends BaseActivity {


    @BindView(R.id.rv_his)
    RecyclerView rvHis;
    @BindView(R.id.vs_no_data)
    ViewStub vsNoData;
    private PayPhoneFeeHisAdapter adapter;
    private GetAddFeeHisPresenter presenter;
    private HashMap<String, String> map = new HashMap<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_pay_phone_fee_his;
    }

    @Override
    protected void initData() {
        rvHis.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PayPhoneFeeHisAdapter(R.layout.item_pay_phone_his);
        rvHis.setAdapter(adapter);


        presenter = new GetAddFeeHisPresenter();

        User user = LocalCache.getUser();
        if (user == null) {
            ToastUtils.INSTANCE.showToast("未获得用户手机号");
            return;
        }
        map.put("mobile", user.getPhone());
        refreshData();
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        presenter.getAddFeeHisList(map, this::showData);
    }

    public void showData(JSONArray array) {
        smartRefreshLayout.finishRefresh();
        if (array == null) {
            return;
        }
        List<PayFeeHisBean> payFeeHisBeans = array.toJavaList(PayFeeHisBean.class);
        if (payFeeHisBeans.isEmpty()) {
            showNoData();
            return;
        }
        adapter.replaceData(payFeeHisBeans);
        adapter.notifyDataSetChanged();

    }

    private void showNoData() {
        rvHis.setVisibility(View.GONE);
        vsNoData.inflate();

    }


}
