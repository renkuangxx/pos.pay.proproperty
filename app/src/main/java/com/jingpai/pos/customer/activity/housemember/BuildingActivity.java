package com.jingpai.pos.customer.activity.housemember;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.invite.ExamineInviteRegisterActivity;
import com.jingpai.pos.customer.activity.invite.Invite2RegisterActivity;
import com.jingpai.pos.customer.activity.show.Home.ShowActivity;
import com.jingpai.pos.customer.adapter.BuildingAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.BuildingBean;
import com.jingpai.pos.customer.mvp.presenter.show.home.MemberPresenterImp;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 时间: 2020/2/6
 * 功能: 房屋管理
 */
public class BuildingActivity extends BaseActivity {

    @BindView(R.id.building_item)
    RecyclerView buildingItem;
    private MemberPresenterImp memberPresenterImp;
    private BuildingAdapter adapter;
    private int isFromYezhu = 0;

    @Override
    protected int getLayout() {
        memberPresenterImp = new MemberPresenterImp();

        return R.layout.building_activity;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            isFromYezhu = intent.getIntExtra("yezhu", 0);
        }
        buildingItem.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BuildingAdapter(R.layout.house_query_item);
        buildingItem.setAdapter(adapter);
        //获取小区Id
        refreshData();
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("communityId", LocalCache.getCurrentCommunityId());
        memberPresenterImp.HouseQueryData(baseRequest, this::memberData);
    }

    @OnClick(R.id.tv_right_btn)
    public void toHistory() {
        startActivity(new Intent(BuildingActivity.this, HouseHistoryInfoActivity.class));
    }

    @OnClick(R.id.iv_back)
    public void back() {
        if (isFromYezhu == 1) {
            startActivity(new Intent(BuildingActivity.this, ShowActivity.class));
            finish();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    //房屋查询
    public void memberData(JSONArray jsonArray) {
        smartRefreshLayout.finishRefresh();
        List<BuildingBean.DataBean> data = jsonArray.toJavaList(BuildingBean.DataBean.class);
        adapter.replaceData(data);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (data == null || data.get(position) == null) return;
            boolean isType = false;
            for (int i = 0; i < data.size(); i++) {
                if (("OWNER").equals(data.get(i).getType())) {
                    isType = true;
                }
            }
            LocalCache.saveTypeInfo("isType", isType);
            BuildingBean.DataBean houseBean = data.get(position);
            if (houseBean.getState() == 1) {
                Intent intent = new Intent(BuildingActivity.this, HouseMember.class);
                intent.putExtra("houseId", houseBean.getHouseId());
                intent.putExtra("houseName", houseBean.getHouseName());
                intent.putExtra("address", houseBean.getUnitNo() + houseBean.getRoomNo() + "室");
                intent.putExtra("type", houseBean.getType());
                startActivity(intent);
                finish();
            } else if (houseBean.getState() == -1) {
                Intent intent = new Intent(BuildingActivity.this, Invite2RegisterActivity.class);
                intent.putExtra("houseId", houseBean.getHouseId());
                intent.putExtra("expireTime", houseBean.getExpireTime());
                startActivity(intent);
            } else {
                Intent intent = new Intent(BuildingActivity.this, ExamineInviteRegisterActivity.class);
                intent.putExtra("auditId", houseBean.getAuditId());
                //用来拒绝后资料重填
                intent.putExtra("houseId", houseBean.getHouseId());
                intent.putExtra("expireTime", houseBean.getExpireTime());
                startActivity(intent);
            }
        });
    }
}