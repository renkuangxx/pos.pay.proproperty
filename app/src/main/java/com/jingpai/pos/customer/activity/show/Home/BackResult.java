package com.jingpai.pos.customer.activity.show.Home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.repairs.MatterHistoryActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.utils.Intents;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * function: 返回结果页面
 */
public class BackResult extends BaseActivity {

    @BindView(R.id.tv_succeed)
    TextView tvSucceed;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.result_btn)
    TextView resultBtn;
    @BindView(R.id.result_btn_show)
    TextView resultBtnShow;

    @Override
    protected int getLayout() {
        return R.layout.activity_add_result;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        int resultCode = intent.getIntExtra("resultCode", 0);
        switch (resultCode) {
            //添加车辆成功
            case 0:
                resultBtnShow.setVisibility(View.GONE);
                tvMessage.setText("添加车牌和车可以在线缴费哦");
                resultBtn.setText("返回列表页");
                tvSucceed.setText("添加成功");
                resultBtn.setOnClickListener(v -> {
                    finish();
                });
                break;
            //报事上传成功
            case 1:
                resultBtnShow.setVisibility(View.VISIBLE);
                tvMessage.setText("您已报事成功!");
                resultBtn.setText("返回报事历史");
                tvSucceed.setText("成功");
                resultBtn.setOnClickListener(v -> {
                    Intents.getInstence().intent(BackResult.this, MatterHistoryActivity.class);
                    finish();
                });
                break;
        }
    }

    @OnClick(R.id.result_btn_show)
    public void onViewClicked(View view) {

        finish();


    }
}
