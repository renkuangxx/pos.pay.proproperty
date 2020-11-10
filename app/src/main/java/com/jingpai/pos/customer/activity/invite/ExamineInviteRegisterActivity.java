package com.jingpai.pos.customer.activity.invite;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.ContractFilesBean;
import com.jingpai.pos.customer.bean.ExamineBean;
import com.jingpai.pos.customer.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 * 审核详情页面
 */
public class ExamineInviteRegisterActivity extends AppCompatActivity {
    private TextView tvFanli;
    private ImageView ivAbove;
    private ImageView ivBelow;
    private RecyclerView rvHetong;
    private TextView tvStatus;
    private TextView tv_time;
    private RelativeLayout rlRefuse;
    private TextView tvRefuse;
    private TextView yesBtn;
    private String auditId;
    private RelativeLayout rl_btn;

    private String houseId;
    private String expireTime;
    Invite2RegisterPresenter invite2RegisterPresenter;
    ExamineBean examineBeans;
    List<ContractFilesBean> contractFilesBeans;

    private ExamineAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_register_examine);
        initView();
        initData();
    }
    private void initView() {
        tvFanli = findViewById(R.id.tv_fanli);
        tv_time = findViewById(R.id.tv_time);
        rl_btn = findViewById(R.id.rl_btn);
        ivAbove = findViewById(R.id.iv_above);
        ivBelow = findViewById(R.id.iv_below);
        rvHetong = findViewById(R.id.rv_hetong);
        tvStatus = findViewById(R.id.tv_status);
        rlRefuse = findViewById(R.id.rl_refuse);
        tvRefuse = findViewById(R.id.tv_refuse);
        yesBtn = findViewById(R.id.yes_btn);
        rlRefuse.setVisibility(View.INVISIBLE);



        StatusBarUtil.INSTANCE.setStatusBarMode(this, true, R.color.black);
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));

        Intent intent = getIntent();
        auditId = intent.getStringExtra("auditId");
        houseId = intent.getStringExtra("houseId");
        expireTime = intent.getStringExtra("expireTime");

        contractFilesBeans = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rvHetong.setLayoutManager(layoutManager);
        adapter = new ExamineAdapter(this, contractFilesBeans);
        rvHetong.setAdapter(adapter);
    }

    private void initData() {
        invite2RegisterPresenter = new Invite2RegisterPresenter();
        invite2RegisterPresenter.examineInfo(auditId, examineBean -> {
            if (examineBean == null) {
                return;
            }
            examineBeans = examineBean;
            String backUrl = TextUtils.isEmpty(examineBeans.getBackUrl()) ? "" : examineBeans.getBackUrl();
            String expireTime = TextUtils.isEmpty(examineBeans.getExpireTime()) ? "" : examineBeans.getExpireTime();
            String frontUrl = TextUtils.isEmpty(examineBeans.getFrontUrl()) ? "" : examineBeans.getFrontUrl();
            String remark = TextUtils.isEmpty(examineBeans.getRemark()) ? "" : examineBeans.getRemark();
            String auditResult = TextUtils.isEmpty(examineBeans.getAuditResult()) ? "" : examineBeans.getAuditResult();
            tv_time.setText(expireTime);
            tvStatus.setText(auditResult);
            tvRefuse.setText(remark);
            if (TextUtils.isEmpty(remark)){
                tvStatus.setTextColor(getResources().getColor(R.color.text_55));
            }else {
                tvStatus.setTextColor(getResources().getColor(R.color.text_red_new));
            }

            rlRefuse.setVisibility(TextUtils.isEmpty(remark) ? View.INVISIBLE : View.VISIBLE);
            rl_btn.setVisibility(TextUtils.isEmpty(remark) ? View.GONE : View.VISIBLE);
            rl_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExamineInviteRegisterActivity.this, Invite2RegisterActivity.class);
                    intent.putExtra("houseId",houseId);
                    intent.putExtra("expireTime", expireTime);
                    startActivity(intent);
                    finish();
                }
            });
            Glide.with(this)
                    .load(frontUrl)
                    .into(ivAbove);
            Glide.with(this)
                    .load(backUrl)
                    .into(ivBelow);
            if (examineBeans.getContractFiles() != null) {
                contractFilesBeans.addAll(examineBeans.getContractFiles());
                adapter.setData(contractFilesBeans);
            }
        });
    }


}
