package com.jingpai.pos.activity.login;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.presenter.login.ForgetPresenter;
import com.jingpai.pos.customer.utils.MD5Utils;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.utils.WorkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgerActivity extends BaseActivity {

    @BindView(R.id.forget_et_user_phone)
    EditText forgetEtUserPhone;
    @BindView(R.id.forget_et_verification_code)
    EditText forgetEtVerificationCode;
    @BindView(R.id.forget_verification_tv)
    TextView forgetVerificationTv;
    @BindView(R.id.forget_et_passwords)
    EditText forgetEtPassword;
    @BindView(R.id.for_password_iv)
    ImageView forPasswordIv;
    private boolean IsShowPassWord = false;
    private TimeCount time;
    private ForgetPresenter forgetPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_forger_password;
    }

    @Override
    protected void initData() {
        forgetPresenter = new ForgetPresenter();
        time = new TimeCount(60000, 1000);
        //rLoadingDialog = new RLoadingDialog(this,true);
    }


    public void forgetData(String succeed) {
        //找回密码
        if (succeed==null){
            return;
        }
        ToastUtils.INSTANCE.showToast(this, "修改成功");
        time.cancel();
        finish();

    }


    public void verificationData(String succeed) {
        if (succeed==null){
            return;
        }
        time.start();
        //验证码
        try {
            JSONObject jsonObject = new JSONObject(succeed);
            String returnCode = jsonObject.getString("returnCode");
            switch (returnCode) {
                case "0":
                    ToastUtils.INSTANCE.showToast(this, "验证码发送成功");
                    break;
                case "102":
                    ToastUtils.INSTANCE.showToast(this, "验证码输入有误");
                    break;
                case "101":
                    ToastUtils.INSTANCE.showToast(this, "验证码超时");
                    break;
                case "1002":
                    ToastUtils.INSTANCE.showToast(this, "手机号输入有误");
                    break;
                case "1000":
                    ToastUtils.INSTANCE.showToast(this, "User用户中心接口调用异常");
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.iv_back, R.id.for_password_iv, R.id.forget_verification_tv, R.id.forget_clean_phone, R.id.forget_password_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                //返回销毁页面
                finish();
                time.cancel();
                break;
            case R.id.for_password_iv:
                //显示隐藏密码
                if (IsShowPassWord) {
                    IsShowPassWord = false;
                    forPasswordIv.setBackgroundResource(R.mipmap.icon_hide);
                    forgetEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    IsShowPassWord = true;
                    forPasswordIv.setBackgroundResource(R.mipmap.icon_display);
                    forgetEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                break;
            case R.id.forget_verification_tv:
                String phone = forgetEtUserPhone.getText().toString();
                if (phone.length() == 13) {
                    //分割字符串
                    StringBuilder sb = new StringBuilder();
                    sb.append(phone.split(" ")[0]).append(phone.split(" ")[1]).append(phone.split(" ")[2]);
                    //新的字符串
                    String phones = sb.toString();
                    Log.e("新的字符串", phones);
                    TreeMap<String, Object> forgetRequest = NetWorkUtil.getInstance().getBaseRequest();
                    forgetRequest.put("phone", phones);
                    forgetRequest.put("captcha", "FORGOT_PWD");
                    forgetRequest.put("type", "FORGOT_PWD");
                    forgetPresenter.verificationRequest(forgetRequest, this::verificationData);
                } else {
                    ToastUtils.INSTANCE.showToast(this, "手机号格式有误");
                }
                break;
            case R.id.forget_clean_phone:
                //清空输入框
                if (forgetEtUserPhone != null) {
                    forgetEtUserPhone.setText("");
                }
                break;
            case R.id.forget_password_tv:
                if (WorkUtil.isNetworkConnected(this)) {
                    String phoneTow = forgetEtUserPhone.getText().toString();
                    String code = forgetEtVerificationCode.getText().toString();
                    String password = forgetEtPassword.getText().toString();
                    if (TextUtils.isEmpty(phoneTow) || TextUtils.isEmpty(code) || TextUtils.isEmpty(password)) {
                        ToastUtils.INSTANCE.showToast(this, "输入框不能为空");
                    } else if (phoneTow.length() == 13) {
                        phoneTow = phoneTow.replaceAll(" ","");
                        TreeMap<String, Object> passwordRequest = NetWorkUtil.getInstance().getBaseRequest();
                        passwordRequest.put("captcha", code);
                        passwordRequest.put("password", MD5Utils.INSTANCE.digest(password));
                        passwordRequest.put("phone", phoneTow);
                        forgetPresenter.forgetData(passwordRequest, this::forgetData);
                    } else {
                        ToastUtils.INSTANCE.showToast(this, "手机号输入有误");
                    }
                } else {
                    ToastUtils.INSTANCE.showToast(this, "没网");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        time.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        time.cancel();
    }

    class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {
            forgetVerificationTv.setClickable(false);
            forgetVerificationTv.setText("已发送(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            forgetVerificationTv.setText("重新获取验证码");
            forgetVerificationTv.setClickable(true);
        }
    }
}