package com.jingpai.pos.customer.activity.show.My;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.presenter.login.ForgetPresenter;
import com.jingpai.pos.customer.utils.MD5Utils;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.utils.ToastUtils;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * function:
 */
public class NewNumberActivity extends BaseActivity {

    @BindView(R.id.formerly_password)
    EditText repetitionPasswords1;
    @BindView(R.id.for_password_iv1)
    ImageView forPasswordIv1;
    @BindView(R.id.new_password)
    EditText repetitionPasswords2;
    @BindView(R.id.for_password_iv2)
    ImageView forPasswordIv2;
    @BindView(R.id.repetition_passwords)
    EditText repetitionPasswords;
    @BindView(R.id.for_password_iv)
    ImageView forPasswordIv;
    @BindView(R.id.new_password_tv)
    TextView newPasswordTv;

    private boolean IsShowPassWord = false;
    private boolean IsShowPassWord1 = false;
    private boolean IsShowPassWord2 = false;

    @Override
    protected int getLayout() {
        return R.layout.new_password_activity;
    }


    @Override
    protected void initData() {

    }

    @OnClick(R.id.new_password_tv)
    public void onNewPasswordTvClicked() {
        String formerly_password = repetitionPasswords1.getText().toString();
        String new_password = repetitionPasswords2.getText().toString();
        String repetition_passwords = repetitionPasswords.getText().toString();

        if (new_password.equals(repetition_passwords)) {
            TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
            String passWord = MD5Utils.INSTANCE.digest(formerly_password);
            String newPassword = MD5Utils.INSTANCE.digest(new_password);

            baseRequest.put("password", passWord);
            baseRequest.put("newPassword", newPassword);
            ForgetPresenter changePassword = new ForgetPresenter();
            changePassword.getUpdatePassword(baseRequest, buildingBean -> {
                ToastUtils.INSTANCE.showToast("修改成功");
                finish();
            });
        } else {
            ToastUtils.INSTANCE.showToast("两次输入的密码不一致");
        }


    }



    @OnClick(R.id.for_password_iv)
    public void onForPasswordIvClicked() {
        //显示隐藏密码
        if (IsShowPassWord) {
            IsShowPassWord = false;
            forPasswordIv.setBackgroundResource(R.mipmap.icon_hide);
            repetitionPasswords.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            IsShowPassWord = true;
            forPasswordIv.setBackgroundResource(R.mipmap.icon_display);
            repetitionPasswords.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }

    @OnClick(R.id.for_password_iv1)
    public void onForPasswordIvClicked1() {
        //显示隐藏密码
        if (IsShowPassWord1) {
            IsShowPassWord1 = false;
            forPasswordIv1.setBackgroundResource(R.mipmap.icon_hide);
            repetitionPasswords1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            IsShowPassWord1 = true;
            forPasswordIv1.setBackgroundResource(R.mipmap.icon_display);
            repetitionPasswords1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }
    @OnClick(R.id.for_password_iv2)
    public void onForPasswordIvClicked2() {
        //显示隐藏密码
        if (IsShowPassWord2) {
            IsShowPassWord2 = false;
            forPasswordIv2.setBackgroundResource(R.mipmap.icon_hide);
            repetitionPasswords2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            IsShowPassWord2 = true;
            forPasswordIv2.setBackgroundResource(R.mipmap.icon_display);
            repetitionPasswords2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
    }
}
