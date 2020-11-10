package com.jingpai.pos.customer.activity.housemember;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.MemberBean;
import com.jingpai.pos.customer.views.CustomToolBar;
import com.jingpai.pos.customer.mvp.presenter.show.home.MemberPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 时间: 2020/3/1
 * 功能:
 */
public class HouseMember extends BaseActivity {

    private static final int MEMBER_MANAGER = 20;

    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.toolbar)
    CustomToolBar toolbar;

    private MemberPresenter memberPresenter;

    private LayoutInflater inflater;

    private RequestManager requestManager;
    private RequestOptions requestOptions;
    private String houseId;
    private String houseName;
    private String type;

    @Override
    protected int getLayout() {
        return R.layout.activity_member;
    }

    @Override
    protected void initData() {
        inflater = LayoutInflater.from(this);
        requestManager = Glide.with(this);
        requestOptions = RequestOptions.bitmapTransform(new CircleCrop());
        memberPresenter = new MemberPresenter();
        toolbar.setRightTitleVisibility(false);
        Intent intent = getIntent();
        houseId = intent.getStringExtra("houseId");
        houseName = intent.getStringExtra("houseName");
        type = intent.getStringExtra("type");

        refreshData();

    }

    @Override
    protected void refreshData() {
        super.refreshData();
        memberPresenter.queryHouseMember(houseId, type, memberArray -> {
            smartRefreshLayout.finishRefresh();
            initMemberList(memberArray);
        });
    }

    private void initMemberList(JSONArray memberArray) {
        smartRefreshLayout.finishRefresh();
        rootView.removeAllViews();
        if (memberArray == null) {
            return;
        }

        List<MemberBean> memberList = memberArray.toJavaList(MemberBean.class);
        for (MemberBean memberBean : memberList) {
            View memberItem = inflater.inflate(R.layout.member_item, null, false);
            ImageView ivAvatar = memberItem.findViewById(R.id.iv_avatar);
            if (!StringUtils.isEmpty(memberBean.getAvatar())) {
                requestManager.load(memberBean.getAvatar()).apply(requestOptions).into(ivAvatar);
            }
            TextView tvName = memberItem.findViewById(R.id.tv_name);
            tvName.setText(memberBean.getName());

            if (memberBean.isOwner()) {
                TextView tvOwer = memberItem.findViewById(R.id.tv_myself);
                tvOwer.setVisibility(View.VISIBLE);
            }
            TextView tvTypeName = memberItem.findViewById(R.id.tv_type_name);
            tvTypeName.setText(memberBean.getTypeName());
            TextView tvArrow = memberItem.findViewById(R.id.tv_arrow);
            if (null != memberBean.getFaceId()) {
                tvArrow.setText("已登记");
                tvArrow.setTextColor(getResources().getColor(R.color.title_text_color));
            } else {
                tvArrow.setText("人脸登记");
                tvArrow.setTextColor(getResources().getColor(R.color.text_97));
            }
            String address = getIntent().getStringExtra("address");
            memberItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HouseMember.this, MemberEditActivity.class);
                    intent.putExtra("memberBean", memberBean);
                    intent.putExtra("houseName", address);
                    startActivityForResult(intent, MEMBER_MANAGER);
                }
            });
            rootView.addView(memberItem);
        }
    }

    @OnClick(R.id.tv_right_btn)
    public void toHistory() {
        startActivity(new Intent(HouseMember.this, HouseHistoryInfoActivity.class));
    }

    @OnClick(R.id.ll_and_btn)
    public void toAdd() {
        Intent intent = new Intent(HouseMember.this, MemberManageActivity.class);
        intent.putExtra("houseId", houseId);
        intent.putExtra("houseName", houseName);
        intent.putExtra("type", type);
        startActivityForResult(intent, MEMBER_MANAGER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}