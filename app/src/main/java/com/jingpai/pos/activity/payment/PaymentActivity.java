package com.jingpai.pos.activity.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.bean.BillOrderBean;
import com.jingpai.pos.bean.NoPayBillBean;
import com.jingpai.pos.customer.adapter.BillAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.house.PayItemBean;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.MathUtil;
import com.jingpai.pos.customer.utils.PixelUtil;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.presenter.BasePresenter;
import com.jingpai.pos.presenter.PayPresenter;
import com.jingpai.pos.utils.ToastUtils;
import com.stx.xhb.androidx.OnDoubleClickListener;
import com.ums.synthpayplugin.SynthPayPluginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity implements BillAdapter.OnChecked {
    @BindView(R.id.ll_no_record)
    View noRecordView;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.no_pay_layout)
    View noPayLayout;
    @BindView(R.id.rv_bill)
    RecyclerView rvBill;
    @BindView(R.id.tv_room_owner_name)
    TextView tvRoomOwnerName;
    @BindView(R.id.tv_room_no_name)
    TextView tvRoomNoName;
    @BindView(R.id.tv_park_no_name)
    TextView tvParkNoName;
    @BindView(R.id.iv_multi)
    ImageView ivMulti;
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.tv_pay)
    TextView tv_pay;

    private PayPresenter paymentPresenter;
    private List<PayItemBean> noPayBillList = new ArrayList<>();
    private BillAdapter noPayBillAdapter;
    private ArrayList<String> parkingId = new ArrayList<>();
    private double totalPrice;
    private String date;
    private String parkString = "";
    private NoPayBillBean noPayBillBean;
    @Override
    protected int getLayout() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initData() {
        paymentPresenter = new PayPresenter();
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, PixelUtil.dp2px(45));
        layoutParams.setMargins(0, StatusBarUtil.INSTANCE.getStatusBarHeight(this), 0, 0);
        mToolBar.setLayoutParams(layoutParams);
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setTranslucentStatus(this);
        noPayBillAdapter = new BillAdapter(R.layout.bill_item, noPayBillList, this);
        rvBill.setLayoutManager(new LinearLayoutManager(this));
        rvBill.setAdapter(noPayBillAdapter);

        tvRoomName.setText(LocalCache.getCurrentCommunityName());
        getRoomData();
        initListener();
    }

    private void initListener() {
        tv_pay.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                payMethod();
            }
        });
    }

    private void getRoomData() {
        noPayBillBean = (NoPayBillBean) getIntent().getSerializableExtra("noPayBillBean");
        ivMulti.setVisibility(View.GONE);
        tvRoomOwnerName.setText(noPayBillBean.getName());
        tvRoomNoName.setText(noPayBillBean.getHouse());
        parkString = "";
        if (noPayBillBean.getParking() != null) {
            for (int i = 0; i < noPayBillBean.getParking().size(); i++) {
                if (i != 0) {
                    parkString += "/";
                }
                parkString += noPayBillBean.getParking().get(i).getParkingPlace() + noPayBillBean.getParking().get(i).getParkingNo() + "号";
                parkingId.add(noPayBillBean.getParking().get(i).getParkingId());
            }
            tvParkNoName.setText(parkString);
        } else {
            tvParkNoName.setText("-");
        }

        noRecordView.setVisibility(View.GONE);
        noPayLayout.setVisibility(View.VISIBLE);
        noPayBillList.addAll(noPayBillBean.getUnpaidBillList());
        noPayBillAdapter.notifyDataSetChanged();
        getTotalPrice();
    }

    @Override
    public void myChecked(int position, boolean isChecked) {
        noPayBillList.get(position).setChecked(isChecked);
        getTotalPrice();
    }

    private void getTotalPrice() {
        totalPrice = 0;
        for (PayItemBean item : noPayBillList) {
            if (item.isChecked()) {
                totalPrice = MathUtil.add(totalPrice, item.getMoney());
            }
        }
        tvMoney.setText("¥" + MathUtil.format00(totalPrice));
//        tvMoney.setText("¥" + totalPrice);
    }

    @OnClick(R.id.tv_right_btn)
    public void onPaymentRecords() {
//        Intent intent = new Intent(PaymentActivity.this, PayHistoryActivity.class);
//        startActivity(intent);
    }


    private int unCheckedCount = 0;

    private void payMethod() {
        unCheckedCount = 0;
        totalPrice = 0;
        date = "";
        for (PayItemBean item : noPayBillList) {
            if (item.isChecked()) {
                if (unCheckedCount != 0) {
                    ToastUtils.INSTANCE.showToast(getResources().getString(R.string.pay_tip));
                    return;
                }
                totalPrice += item.getMoney();
                date = item.getTitle();
            } else {
                unCheckedCount++;
            }
        }

        if (unCheckedCount == noPayBillList.size()) {
            ToastUtils.INSTANCE.showToast(getResources().getString(R.string.pay_tip1));
            return;
        }
        paymentPresenter.billOrder(noPayBillBean.getHouseId(),parkingId, date, new BasePresenter.Callback<BillOrderBean>() {
            @Override
            public void success(BillOrderBean billOrderBean) {
                if (billOrderBean==null){
                    ToastUtils.INSTANCE.showToast("创建支付订单失败");
                    return;
                }
//                Intent intent = new Intent(PaymentActivity.this, CheckOutActivity.class);
////                intent.putExtra(CheckOutActivity.DATA, billOrderBean);
////                startActivityForResult(intent, 0);

//                YinlianPayInstance.getInstance().yinlianCollect(PaymentActivity.this,billOrderBean.getOrderNo());



                JSONObject transData = null;
                try {
                    transData.put("appId", "880ff13a339749dda54efa40cfef3887");//appId
                    transData.put("amt", 1);//金额
                    transData.put("isNeedPrintReceipt", false);//交易结束后自动打单
                    transData.put("extOrderNo", billOrderBean.getOrderNo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent cashierPluginIntent = new Intent(PaymentActivity.this, SynthPayPluginActivity.class);
                cashierPluginIntent.putExtra("transData", transData.toString());
                startActivityForResult(cashierPluginIntent, 100);

            }

            @Override
            public void failure(BillOrderBean billOrderBean) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

        } else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

