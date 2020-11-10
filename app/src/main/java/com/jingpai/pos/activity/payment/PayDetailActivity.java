package com.jingpai.pos.activity.payment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jingpai.pos.BuildConfig;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.adapter.Apply4InvoiceAdapter;
import com.jingpai.pos.customer.adapter.BillDetailAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.bean.Apply4InvoiceBean;
import com.jingpai.pos.customer.bean.payment.BillDetailBean;
import com.jingpai.pos.bean.ChargeOrderDetailListVo;
import com.jingpai.pos.customer.mvp.presenter.payment.PaymentPresenter;
import com.jingpai.pos.customer.utils.LocalCache;
import com.stx.xhb.androidx.OnDoubleClickListener;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Create by liujinheng
 * date 2020/5/26
 * function
 */
public class PayDetailActivity extends BaseActivity {
    @BindView(R.id.rv_pay_detail)
    RecyclerView rvPayDetail;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_room_no_name)
    TextView tvRoomNoName;
    @BindView(R.id.tv_park_no_name)
    TextView tvParkNoName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_invoice)
    TextView tv_invoice;
    private PaymentPresenter paymentPresenter;
    private BillDetailAdapter payBillAdapter;
    private List<BillDetailBean> billDetailList = new ArrayList<>();
    private String parking;
    private String houseName;
    private String date;
    private String price;
    private String state;
    private String orderId;
    private String picUrl;
    private Apply4InvoiceBean apply4InvoiceBean;
    List<ChargeOrderDetailListVo> listVos = new ArrayList<>();
    private BigDecimal totalPrice;
    private Dialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_payment_detail;
    }

    @Override
    protected void initData() {
        paymentPresenter = new PaymentPresenter();
        parking = getIntent().getStringExtra("parking");
        houseName = getIntent().getStringExtra("houseName");
        date = getIntent().getStringExtra("date");
        price = getIntent().getStringExtra("price");
        if (getIntent() != null){
            picUrl = getIntent().getStringExtra("pic");
            state = getIntent().getStringExtra("state");
        }
        tvDate.setText(date);
        tvRoomNoName.setText(houseName);
        tvParkNoName.setText(parking);
        tvPrice.setText("¥" + price);
        orderId = getIntent().getStringExtra("orderId");
        payBillAdapter = new BillDetailAdapter(R.layout.bill_detail_item, billDetailList);
        rvPayDetail.setLayoutManager(new LinearLayoutManager(this));
        rvPayDetail.setAdapter(payBillAdapter);

        //0:隐藏,1:申请开票,2:已开票
        if (TextUtils.equals("2",state)){
            tv_invoice.setText("查看开票");
        }else if (TextUtils.equals("3",state)){
            tv_invoice.setText("开票中");
            tv_invoice.setEnabled(false);
            tv_invoice.setClickable(false);
        }else if (TextUtils.equals("1",state)){
            tv_invoice.setText("申请开票");
        }else {
            tv_invoice.setVisibility(View.GONE);
        }
        initPay();
        tv_invoice.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (TextUtils.equals("1",state)) {
                    apply4InvoiceMethod();
                } else if (TextUtils.equals("2",state)){
                    watchInvoice();

                }
            }


        });
    }

    private void apply4InvoiceMethod() {
        Map<String, Object> param = new HashMap<>();
        param.put("billType", "2");
        param.put("chargeOrderId", orderId);
        paymentPresenter.apply4Invoice(param, apply4InvoiceBean1 -> {
            if (apply4InvoiceBean1 == null) return;
            apply4InvoiceBean = apply4InvoiceBean1;
            totalPrice = apply4InvoiceBean.getAllAccount();
            listVos = apply4InvoiceBean1.getChargeOrderDetailList();

            if (totalPrice.compareTo(BigDecimal.ZERO) == 0) {
                apply4billingDialogNoprice();
            } else {
                if (listVos == null || listVos.size() == 0) return;
                apply4billingDialog(listVos);
            }
        });
    }

    private void watchInvoice() {
        String url = TextUtils.isEmpty(picUrl) ? "" : picUrl;
//            startWebViewActivity(PayDetailActivity.this, url, "");
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.invoice_image_dialog, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        ImageView iv_pic = dialog.findViewById(R.id.iv_pic);
        Glide.with(this)
                .load(picUrl)
                .into(iv_pic);
    }
    private void initPay() {
        paymentPresenter.queryBillDetail(orderId, jsonArray -> {
            if (jsonArray != null && !jsonArray.isEmpty()) {
                List<BillDetailBean> list = jsonArray.toJavaList(BillDetailBean.class);
                initHistoryList(list);
            }
        });
    }

    private void initHistoryList(List<BillDetailBean> beanArrayList) {
        billDetailList.addAll(beanArrayList);
        payBillAdapter.notifyDataSetChanged();
    }

    private void apply4billingDialogNoprice() {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.invoice_no_price_dialog, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        TextView tv_cancel= dialog.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private void apply4billingDialog(List<ChargeOrderDetailListVo> listVos) {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.invoice_dialog, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        TextView tvTotalPrice = dialog.findViewById(R.id.tv_total_price);
        RecyclerView rvDetil = dialog.findViewById(R.id.rv_detil);
        TextView tvApplyGo = dialog.findViewById(R.id.tv_apply_go);
        TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
        RelativeLayout rl_cancel = dialog.findViewById(R.id.rl_cancel);

        tvTotalPrice.setText(totalPrice + " 元");

        Apply4InvoiceAdapter apply4InvoiceAdapter = new Apply4InvoiceAdapter(this, listVos);
        rvDetil.setLayoutManager(new LinearLayoutManager(this));
        rvDetil.setAdapter(apply4InvoiceAdapter);

        tvApplyGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = BuildConfig.INVOICE_URL
                        + "communityId="
                        + LocalCache.getCurrentCommunityId()
                        + "&token="
                        + LocalCache.getToken()
                        + "&orderId="
                        + orderId ;
                startWebViewActivity(PayDetailActivity.this, url, "");
                dialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
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
        finish();
    }
}
