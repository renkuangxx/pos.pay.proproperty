package com.jingpai.pos.customer.activity.authentication.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.mvp.YezhuPresenter;

import butterknife.BindView;

/*
 * 业主认证 选择身份
 * */
public class SelectIdActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.rb_zfb)
    RadioButton rb_zfb;
    @BindView(R.id.rb_family)
    RadioButton rb_family;
    @BindView(R.id.rb_zuhu)
    RadioButton rb_zuhu;
    @BindView(R.id.tv_confirm_pay)
    TextView tv_confirm_pay;
    @BindView(R.id.tv_present)
    TextView tv_present;
    private int selected = 1;
    String roomNo;
    int houseId;
    private Dialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_choise_shenfen;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            roomNo = intent.getStringExtra("finalInfo");
            houseId = intent.getIntExtra("houseId", 0);
            tv_present.setText(roomNo);
        }
        initListener();
    }

    private void initListener() {
        tv_confirm_pay.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_confirm_pay:
                // todo
                if (rb_zfb.isChecked()) {
                    selected = 1;
                    //产权人
                    YezhuPresenter yezhuPresenter = new YezhuPresenter();
                    yezhuPresenter.upYezhuChanInfo(houseId, s -> {
                        if (TextUtils.isEmpty(s)) return;
                        if ("true".equals(s)) {
                            isNeedDialog();
                            return;
                        } else {
                            jum2Register();
                        }
                    });

                } else if (rb_family.isChecked()) {
                    selected = 2;
                    jum2Register();
                } else {
                    selected = 3;
                    jum2Register();
                }

                break;
        }
    }

    private void jum2Register() {
        Intent intent = new Intent(this, YezhuRegisterActivity.class);
        intent.putExtra("shenFen", selected);
        intent.putExtra("roomNo", roomNo);
        intent.putExtra("houseId", houseId);
        intent.putExtra("isChanquanren", 0);
        startActivity(intent);
    }

    //是否弹出认证弹窗
    private void isNeedDialog() {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.ye_info_dialog, null);
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
        TextView tv_wrong = dialog.findViewById(R.id.tv_wrong);
        tv_wrong.setOnClickListener(view1 -> {
            //TODO
            Intent intent = new Intent(this, YezhuRegisterActivity.class);
            intent.putExtra("shenFen", selected);
            intent.putExtra("roomNo", roomNo);
            intent.putExtra("houseId", houseId);
            intent.putExtra("isChanquanren", 1);
            startActivity(intent);
            dialog.dismiss();
        });
        TextView tv_change = dialog.findViewById(R.id.tv_change);
        tv_change.setOnClickListener(view1 -> {
            //TODO
            Intent intent = new Intent(this, YezhuRegisterActivity.class);
            intent.putExtra("shenFen", selected);
            intent.putExtra("roomNo", roomNo);
            intent.putExtra("houseId", houseId);
            intent.putExtra("isChanquanren", 2);
            startActivity(intent);
            dialog.dismiss();
        });

    }
}