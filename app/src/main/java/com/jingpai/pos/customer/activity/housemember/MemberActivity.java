package com.jingpai.pos.customer.activity.housemember;

import android.content.Intent;
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
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.house.MemberHouse;
import com.jingpai.pos.customer.bean.show.MemberBean;
import com.jingpai.pos.customer.mvp.presenter.show.home.MemberPresenter;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.utils.LocalCache;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberActivity extends BaseActivity {

    private static final int MEMBER_MANAGER = 20;

    @BindView(R.id.rootView)
    LinearLayout rootView;
    private MemberPresenter memberPresenter;
    private LayoutInflater inflater;
    private RequestManager requestManager;
    private RequestOptions requestOptions;

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
        refreshData();
        CommonUtil.hideKeyboard(MyApplication.getContext(), rootView);
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        memberPresenter.queryAll(memberArray -> {
            initMemberList(memberArray);
        });
    }

    private void initMemberList(JSONArray memberArray) {
        smartRefreshLayout.finishRefresh();
        if (memberArray == null) {
            return;
        }
        rootView.removeAllViews();
        List<MemberHouse> memberBeanList = memberArray.toJavaList(MemberHouse.class);

        for (MemberHouse memberHouse : memberBeanList) {
            TextView houseItem = (TextView)inflater.inflate(R.layout.member_house, null, false);
            houseItem.setText(memberHouse.getHouseName());
            rootView.addView(houseItem);

            List<MemberBean> memberList = memberHouse.getMemberList();
            for (MemberBean memberBean : memberList) {
                View memberItem = inflater.inflate(R.layout.member_item, null, false);
                ImageView ivAvatar = memberItem.findViewById(R.id.iv_avatar);
                if (!StringUtils.isEmpty(memberBean.getAvatar())) {
                    requestManager.load(memberBean.getAvatar()).apply(requestOptions).into(ivAvatar);
                }
                TextView tvName = memberItem.findViewById(R.id.tv_name);
                tvName.setText(memberBean.getName());

                if(memberBean.isOwner()){
                    TextView tvOwer = memberItem.findViewById(R.id.tv_myself);
                    tvOwer.setVisibility(View.VISIBLE);
                }

                TextView tvTypeName = memberItem.findViewById(R.id.tv_type_name);
                tvTypeName.setText(memberBean.getTypeName());

                if (LocalCache.getUserId().equals(memberBean.getUserId())) {
                    memberItem.findViewById(R.id.tv_myself).setVisibility(View.VISIBLE);
                }

                TextView tvArrow = memberItem.findViewById(R.id.tv_arrow);
                if (null!=memberBean.getFaceId()){
                    tvArrow.setText("已登记");
                    tvArrow.setTextColor(getResources().getColor(R.color.title_text_color));
                }else{
                    tvArrow.setTextColor(getResources().getColor(R.color.text_97));
                    tvArrow.setText("人脸登记");
                }
                memberItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MemberActivity.this, MemberEditActivity.class);
                        intent.putExtra("memberBean",memberBean);
                        intent.putExtra("houseName",memberHouse.getUnitNo()+memberHouse.getRoomNo()+"室");
                        startActivityForResult(intent,MEMBER_MANAGER);
                    }
                });
                rootView.addView(memberItem);


            }
        }
    }

    @OnClick(R.id.ll_and_btn)
    public void toAdd() {
        startActivityForResult(new Intent(MemberActivity.this, MemberManageActivity.class), MEMBER_MANAGER);
    }
    @OnClick(R.id.tv_right_btn)
    public void toHistory() {
        startActivity(new Intent(MemberActivity.this, HouseHistoryInfoActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==MEMBER_MANAGER&&resultCode==RESULT_OK){
            refreshData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
}
