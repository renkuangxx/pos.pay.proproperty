package com.jingpai.pos.customer.activity.repairs;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.activity.SlideRecyclerView;
import com.jingpai.pos.customer.activity.show.My.PlusImageActivity;
import com.jingpai.pos.customer.adapter.GridImageViewAdapter;
import com.jingpai.pos.customer.adapter.MatterDealwithAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.MatterDetailBean;
import com.jingpai.pos.customer.mvp.presenter.show.my.MatterHistoryPresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.MainConstant;
import com.jingpai.pos.customer.utils.MyGridView;
import com.jingpai.pos.utils.ToastUtils;

import java.util.ArrayList;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 时间: 2020/2/22
 * 功能:
 */
public class MatterDetailActivity extends BaseActivity implements MatterDealwithAdapter.OnPicClick {

    @BindView(R.id.tv_details_state)
    TextView tvDetailsState;
    @BindView(R.id.ll_color)
    LinearLayout llColor;
    //处理人员
    @BindView(R.id.ll_processing)
    RelativeLayout llProcessing;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_declare_time)
    TextView tvDeclareTime;
    @BindView(R.id.rv_iv)
    MyGridView rvIv;
    @BindView(R.id.ll_cancel)
    LinearLayout llCancel;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.tv_person_phone)
    TextView tvPersonPhone;
    @BindView(R.id.tv_urging)
    TextView tvUrging;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.ll_deal_with)
    LinearLayout llDealWith;
    @BindView(R.id.rl_cancel)
    RelativeLayout rlCancel;
    @BindView(R.id.tv_cancel_time)
    TextView tvCancelTime;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_limit_num)
    TextView tvLimitNum;
    @BindView(R.id.tv_char_count)
    TextView tvCharCount;
    @BindView(R.id.ll_judge)
    LinearLayout llJudge;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.rv_dealwith)
    SlideRecyclerView rvDealwith;
    @BindView(R.id.ll_pic)
    LinearLayout ll_pic;
    private ArrayList<String> mList = new ArrayList<>();
    private MatterHistoryPresenter matterHistoryPresenter;
    private String id;
    private int urgingCount = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_the_matter_details;
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        matterHistoryPresenter = new MatterHistoryPresenter();
        refreshData();
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        changeViewState(id);
    }

    private void changeViewState(String id) {
        matterHistoryPresenter.getMatterDetail(id, matterDetailBean -> {
//            smartRefreshLayout.finishRefresh();

            tvType.setText(matterDetailBean.getType());
            tvDeclareTime.setText(matterDetailBean.getCreateTime());
            tvDescription.setText(matterDetailBean.getDescription());
            mList = new ArrayList<>();
            mList.addAll(matterDetailBean.getFiles());
            GridImageViewAdapter gridViewAdapter = new GridImageViewAdapter(this, mList);
            rvIv.setAdapter(gridViewAdapter);
            rvIv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    viewPluImg(position);
                }
            });
            ll_pic.setVisibility(mList.size() == 0 ? View.GONE : View.VISIBLE);
            int state = matterDetailBean.getState();
            switch (state) {
                case -1://已取消
                    llProcessing.setVisibility(View.GONE);
                    llDealWith.setVisibility(View.GONE);
                    rlCancel.setVisibility(View.VISIBLE);
                    llCancel.setVisibility(View.GONE);
                    llColor.setBackgroundColor(Color.parseColor("#F0F0F0"));
                    tvDetailsState.setText("已取消");
                    tvDetailsState.setTextColor(Color.parseColor("#979797"));
                    tvCancelTime.setText(matterDetailBean.getModifyTime());

                    break;
                case 0://待处理
                case 1://已分配
                    llProcessing.setVisibility(View.GONE);
                    llDealWith.setVisibility(View.GONE);
                    llColor.setBackgroundColor(Color.parseColor("#FFE9C9"));
                    tvDetailsState.setText("待处理");
                    tvDetailsState.setTextColor(Color.parseColor("#FF9C11"));
                    tvCount.setText(getResources().getString(R.string.urging_count, matterDetailBean.getUrge()));
                    urgingCount = matterDetailBean.getUrge();
                    break;

                case 2://进行中
                    tvCancel.setVisibility(View.GONE);
                    llDealWith.setVisibility(View.GONE);
                    llColor.setBackgroundColor(Color.parseColor("#DCF2ED"));
                    tvDetailsState.setText("进行中");
                    tvDetailsState.setTextColor(Color.parseColor("#44D7B6"));

                    tvPersonName.setText(matterDetailBean.getManagerName());
                    tvPersonPhone.setText(matterDetailBean.getManagerPhone());
                    tvCount.setText(getResources().getString(R.string.urging_count, matterDetailBean.getUrge()));
                    urgingCount = matterDetailBean.getUrge();
                    break;
                case 3://待确认
                    //取消按钮隐藏
                    tvCancel.setVisibility(View.GONE);
                    llColor.setBackgroundColor(Color.parseColor("#DCF2ED"));
                    tvDetailsState.setText("待客服确认");
                    tvDetailsState.setTextColor(Color.parseColor("#44D7B6"));

                    tvPersonName.setText(matterDetailBean.getManagerName());
                    tvPersonPhone.setText(matterDetailBean.getManagerPhone());
                    tvCount.setText(getResources().getString(R.string.urging_count, matterDetailBean.getUrge()));
                    urgingCount = matterDetailBean.getUrge();
                    dealWithResult(matterDetailBean);
                    break;
                case 4://待评价
                    llProcessing.setVisibility(View.GONE);
                    llCancel.setVisibility(View.GONE);
                    llJudge.setVisibility(View.VISIBLE);
                    tvDetailsState.setText("待评价");
                    ratingBar.setIsIndicator(false);
                    etContent.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            tvCharCount.setText(s.length() + "");
                        }
                    });
                    dealWithResult(matterDetailBean);
                    break;
                case 5://已完成
                    llProcessing.setVisibility(View.GONE);
                    llCancel.setVisibility(View.GONE);
                    tvDetailsState.setText("已完成");

                    if (!TextUtils.isEmpty(matterDetailBean.getComment())) {
                        llJudge.setVisibility(View.VISIBLE);
                        tvConfirm.setVisibility(View.GONE);
                        ratingBar.setIsIndicator(true);
                        etContent.setEnabled(false);
                        ratingBar.setRating(matterDetailBean.getStar());
                        etContent.setText(matterDetailBean.getComment());
                        tvCharCount.setText(matterDetailBean.getComment().length() + "");
                    }
                    dealWithResult(matterDetailBean);
                    break;
            }
        });
    }

    private void dealWithResult(MatterDetailBean matterDetailBean) {
        MatterDealwithAdapter noPayBillAdapter = new MatterDealwithAdapter(R.layout.item_matter_detail_dealwith, matterDetailBean.getHandlers(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvDealwith.setLayoutManager(layoutManager);
        rvDealwith.setAdapter(noPayBillAdapter);
    }

    @OnClick(R.id.tv_confirm)
    public void ConfirmClick() {
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        String content = etContent.getText().toString();
        float star = ratingBar.getRating();
        baseRequest.put("comment", content);
        baseRequest.put("star", star);
        baseRequest.put("id", id);
        matterHistoryPresenter.evaluateMatter(baseRequest, this::matterJudge);
    }

    @OnClick(R.id.tv_urging)
    public void UrgingClick() {
        matterHistoryPresenter.reportUrging(id, this::matterUrging);
    }

    @OnClick(R.id.tv_cancel)
    public void onTvCancelClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MatterDetailActivity.this);
        builder.setMessage("确认取消报事?");
        builder.setTitle("取消提示");
        builder.setPositiveButton("确定", (dialog, which) -> {
            matterHistoryPresenter.reportCancel(id, this::matterCancel);
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
        });
        builder.show();
    }

    public void matterUrging(String msg) {
        if (msg != null) {
            ToastUtils.INSTANCE.showToast("催办成功");
            tvCount.setText(getResources().getString(R.string.urging_count, urgingCount + 1));
//            tvUrging.setBackground(getResources().getDrawable(R.drawable.shape_e7e7e7_5dp));
        } else {
//            ToastUtils.INSTANCE.showToast("催办失败");
        }
    }

    public void matterJudge(String msg) {
        if (msg != null) {
            ToastUtils.INSTANCE.showToast("评价成功");
            tvConfirm.setVisibility(View.GONE);
            ratingBar.setIsIndicator(true);
            etContent.setEnabled(false);
        }
    }

    public void matterCancel(String msg) {
        if (msg != null) {
            ToastUtils.INSTANCE.showToast("取消报事成功");
            refreshData();
        }
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(MatterDetailActivity.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onPicClick(int position, ArrayList<String> list) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, list);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }
}