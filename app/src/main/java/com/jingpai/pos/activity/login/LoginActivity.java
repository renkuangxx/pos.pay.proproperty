package com.jingpai.pos.activity.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.activity.payment.PropertySearchActivity;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.bean.Community;
import com.jingpai.pos.bean.User;
import com.jingpai.pos.customer.utils.Intents;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.MD5Utils;
import com.jingpai.pos.customer.utils.PermissionHelper;
import com.jingpai.pos.customer.utils.PermissionInterface;
import com.jingpai.pos.customer.utils.PermissionUtil;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.customer.utils.StatusBarUtils;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.utils.WorkUtil;
import com.jingpai.pos.presenter.login.UserPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements PermissionInterface {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    private LoadingDialog loadingDialog;
    private PermissionHelper permissionHelper;
    private UserPresenter userPresenter;
    @Override
    protected int getLayout() {
        return R.layout.activity_home_login;
    }

    @Override
    protected void initData() {
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.translucent));
        StatusBarUtils.immersive(this);
        StatusBarUtils.setPaddingSmart(this, rlMain);
        userPresenter = new UserPresenter();
        permissionHelper = new PermissionHelper(this, this);
        permissionHelper.requestPermissions();
        Glide.get(LoginActivity.this).clearMemory();
    }

    private void loginCallback(User user) {
        if (user == null) {
            return;
        }
        user.setPassword(MD5Utils.INSTANCE.digest(etPassword.getText().toString()));
        LocalCache.saveUserAndToken(user);
        Community community = LocalCache.getCurrentCommunity();
        if (community == null) {
            getCommunity();
        }else{
            LocalCache.saveUserIdNum("userIdNum", TextUtils.isEmpty(user.getCertificateNo()) ? "" : user.getCertificateNo());
            Intents.getInstence().intent(this, PropertySearchActivity.class);
            loadingDialog.dismiss();
            finish();
        }
    }

    private void getCommunity() {
        userPresenter.getSelectVillage(communityArray -> {
            loadingDialog.dismiss();
            if (communityArray != null) {
                List<Community> communityList = communityArray.toJavaList(Community.class);
                if (communityList != null && !communityList.isEmpty()) {
                    if (communityList.size()==1){
                        User user = LocalCache.getUser();
                        user.setCommunities(communityList);
                        LocalCache.saveUser(user);
                        LocalCache.setCurrentCommunity(communityList.get(0));
                        startActivity(new Intent(LoginActivity.this, PropertySearchActivity.class));
                        finish();
                    }else{
                        User user = LocalCache.getUser();
                        user.setCommunities(communityList);
                        LocalCache.saveUser(user);
                        startActivity(new Intent(LoginActivity.this, SelectVillageActivity.class));
                        finish();
                    }

                } else {
                    ToastUtils.INSTANCE.showToast(this, "您账号尚无小区");
                }
            }else{
                ToastUtils.INSTANCE.showToast(this, "您账号尚无小区");
            }
        });
    }
    @OnClick(R.id.forget_password_tv)
    public void toForgetPasswordActivity() {
        toActivity(ForgerActivity.class);
    }

    @OnClick(R.id.login)
    public void login() {
        String userName = etUserName.getText().toString();
        String passWord = etPassword.getText().toString();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
            ToastUtils.INSTANCE.showToast(this, "账号或密码不能为空");
        } else if (userName.length() == 13) {
            //分割字符串
            StringBuilder sb = new StringBuilder();
            sb.append(userName.split(" ")[0]).append(userName.split(" ")[1]).append(userName.split(" ")[2]);
            String phone = sb.toString();

            loadingDialog = new LoadingDialog(LoginActivity.this);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.show();

            if (WorkUtil.isNetworkConnected(this)) {
                userPresenter.login(phone, MD5Utils.INSTANCE.digest(passWord), user -> {
                    loadingDialog.dismiss();
                    loginCallback(user);
                });
            } else {
                loadingDialog.dismiss();
                ToastUtils.INSTANCE.showToast(this, "请检查当前网络是否连接");
            }
        } else {
            ToastUtils.INSTANCE.showToast(this, "手机号输入有误");
        }
    }

    @Override
    public int getPermissionsRequestCode() {
        return 0;
    }

    @Override
    public String[] getPermissions() {
        return PermissionUtil.mPermissions;
    }

    @Override
    public void requestPermissionsSuccess() {

    }

    @Override
    public void requestPermissionsFail() {
        StringBuilder sb = new StringBuilder();
        PermissionUtil.mPermissions = PermissionUtil.getDeniedPermissions(this, PermissionUtil.mPermissions);
        for (String s : PermissionUtil.mPermissions) {
            if (s.equals(Manifest.permission.CAMERA)) {
                sb.append("相机权限(用于拍照，视频聊天);\n");
            } else if (s.equals(Manifest.permission.READ_EXTERNAL_STORAGE) || s.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                sb.append("存储,读取权限(用于存储必要信息，缓存数据);\n");
            }
        }
        //PermissionUtil.PermissionDialog(this, "程序运行需要如下权限：\n" + sb.toString() + "请在应用权限管理进行设置！");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissionHelper.requestPermissionsResult(requestCode, PermissionUtil.mPermissions, grantResults)) {
            //权限请求结果，并已经处理了该回调
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                String phone = data.getStringExtra("phone");
                etUserName.setText(phone);
            }
        }
    }

    @OnClick(R.id.tv_server_agreement)
    public void toServerAgreement() {
        Intent intent = new Intent(LoginActivity.this, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, Constant.AGREEMENT_URL + "?code=0");
        startActivity(intent);
    }

    @OnClick(R.id.tv_privacy_agreement)
    public void toPrivacyAgreement() {
        Intent intent = new Intent(LoginActivity.this, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, Constant.AGREEMENT_URL + "?code=1");
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}