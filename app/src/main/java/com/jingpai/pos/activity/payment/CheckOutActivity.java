package com.jingpai.pos.activity.payment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alipay.sdk.app.OpenAuthTask;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.bean.BillOrderBean;
import com.jingpai.pos.bean.PayBean;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.mvp.presenter.payment.PaymentPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.home.HomePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.AliPayUtils;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.utils.ToastUtils;

import org.json.JSONException;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckOutActivity extends BaseActivity implements OpenAuthTask.Callback {

    public static final String DATA = "data";
    @BindView(R.id.tv_type)
    TextView tvTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rb_zfb)
    RadioButton rbZfb;
    @BindView(R.id.rb_wechat)
    RadioButton rbWechat;
    @BindView(R.id.rg_pay)
    RadioGroup rgPay;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_confirm_pay)
    TextView tv_confirm_pay;
    @BindView(R.id.tv_title)
    TextView tv_title;
    float billNum = 0.00f;
    private BillOrderBean billOrderBean;
    private PayBean payBean;
    private PaymentPresenter paymentPresenter;
    private LoadingDialog loadingDialog;
    private Dialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_check_out;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        billOrderBean = (BillOrderBean) intent.getSerializableExtra(DATA);
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (billOrderBean != null) {
            tvTitle.setText(billOrderBean.getType());
            billNum = billOrderBean.getAmount();
            tvMoney.setText(billNum+"");
        }
        initListener();
        paymentPresenter = new PaymentPresenter();
        loadingDialog = new LoadingDialog(this);
        //计算扣除积分后的提交按钮价格
        tv_confirm_pay.setText("应付 "+billNum+" 元");
    }

    //是否弹出认证弹窗
    private void monryRuleDialog() {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.money_rule_dialog, null);
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
        ImageView iv_cancel = dialog.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(view1 -> {
            //TODO
            dialog.dismiss();
        });
    }

    private void initListener() {

        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monryRuleDialog();
            }
        });
    }


    @OnClick(R.id.tv_confirm_pay)
    public void order() {
        switch (rgPay.getCheckedRadioButtonId()) {
            case R.id.rb_zfb:
                AliPayUtils.openAuthScheme(this,this);
//                monryRuleDialog();
                break;
            case R.id.rb_wechat:
                payWeChat();
                break;
        }
    }

    private void payZfb(String authCode) {
        billOrderBean.setType("1");
        billOrderBean.setOrderNo(billOrderBean.getOrderNo());
//        paymentPresenter.pay(billOrderBean,0,authCode, payBean -> {
//            this.payBean = payBean;
//            Intent intent;
//            try {
//                intent = Intent.parseUri("alipays://platformapi/startapp?appId=20000067&url="+payBean.getPayUrl(),
//                        Intent.URI_INTENT_SCHEME);
//                intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                intent.setComponent(null);
//                startActivity(intent);
//            } catch (Exception e) {
//                //e.printStackTrace();
//            }
//        });
    }

    private void payWeChat() {
        billOrderBean.setType("2");
        billOrderBean.setOrderNo(billOrderBean.getOrderNo());
//        paymentPresenter.pay(billOrderBean,0, payBean -> {
//            this.payBean = payBean;
//            if (payBean == null)return;
//            int finalValue = payBean.getPayAmount()==null?(int)(billOrderBean.getAmount()*100):payBean.getPayAmount().multiply(new BigDecimal("100")).intValue();
//            try {
//                 OpenWxUtil.openWX(this);
//                OpenWxUtil.wxPay(this, billOrderBean.getOrderNo(), finalValue, "CITYLIFE");
//                setResult(Activity.RESULT_OK);
//            } catch (Exception e) {
//                //e.printStackTrace();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (payBean != null) {
//            loadingDialog.show();
//            paymentPresenter.queryAliPayState(billOrderBean.getOrderNo(), payResultBean -> {
//                loadingDialog.hide();
//                if (payResultBean.getPayState() == PayState.PAY_SUCCESS.getCode()) {
//                    com.blankj.utilcode.util.ToastUtils.showShort(R.string.pay_success);
//                    setResult(Activity.RESULT_OK);
//                    setGameTimes();
//                    finish();
//                } else {
//                    com.blankj.utilcode.util.ToastUtils.showLong(PayState.getNameByCode(payResultBean.getPayState()));
//                    com.blankj.utilcode.util.ToastUtils.showLong("支付失败");
////                    com.jingling.citylife.customer.utils.ToastUtils.INSTANCE.showToast("支付失败");
//                    setResult(Activity.RESULT_CANCELED);
//                    finish();
//                }
//            });
        }
    }

    private void setGameTimes() {
        HomePresenter homePresenter = new HomePresenter();
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("mobile", LocalCache.getUser().getPhone());
        baseRequest.put("kind", "5");
        homePresenter.getGameTimesMethod(baseRequest,gameTimesResultBean -> {
            if (gameTimesResultBean==null)return;
            String code = LocalCache.getGameTimesCode("errorCode");
            if (TextUtils.equals(code,"4")){
                ToastUtils.INSTANCE.showToast("次数增加已到上限");
            }
        });
    }

    /**
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
        intent.putExtra(Constant.WEB_BAOBING_BACK, 1);
        intent.putExtra(Constant.WEB_TITLE_RIGHT, 1);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

//    OpenAuthTask.OK =  9000               - 调用成功
//    OpenAuthTask.Duplex =  5000           -  3 s 内快速发起了多次支付 / 授权调用。稍后重试即可。
//    OpenAuthTask.NOT_INSTALLED =  4001    - 用户未安装支付宝 App。
//    OpenAuthTask.SYS_ERR =  4000          - 其它错误，如参数传递错误。
    @Override
    public void onResult(int resultCode, String memo, Bundle bundle) {
        if  (resultCode == OpenAuthTask.OK) {
            com.blankj.utilcode.util.ToastUtils.showShort("授权成功");
            String authCode = bundle.getString("auth_code");
            payZfb(authCode);
        }  else  {
            com.blankj.utilcode.util.ToastUtils.showShort("授权失败");
        }
    }
}
