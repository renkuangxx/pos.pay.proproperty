package com.jingpai.pos.customer.activity.show.Home;
/*
 * 车辆管理页面
 * */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.CarQueryShowAdapter;
import com.jingpai.pos.customer.adapter.CarStallShowAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.CarStallBean;
import com.jingpai.pos.customer.bean.show.CarQueryBean;
import com.jingpai.pos.views.TipsDialog;
import com.jingpai.pos.customer.mvp.presenter.show.home.CarQueryPresenterImp;
import com.jingpai.pos.customer.utils.Intents;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.utils.ToastUtils;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

public class CarManageActivity extends BaseActivity {

    @BindView(R.id.manage_add_car)
    TextView manageAddCar;
    @BindView(R.id.visibility)
    LinearLayout visibility;
    @BindView(R.id.car_show)
    RecyclerView carShow;
    @BindView(R.id.add_cars)
    TextView addCars;
    @BindView(R.id.car_stall_show)
    RecyclerView carStallShow;
    @BindView(R.id.tv_car)
    TextView tvCar;
    @BindView(R.id.tv_stall)
    TextView tvStall;
    @BindView(R.id.rl)
    RelativeLayout rlCar;
    @BindView(R.id.rl_stall)
    RelativeLayout rlStall;
    @BindView(R.id.r_layout)
    LinearLayout rlAddCar;
    private LoadingDialog loadingDialog;
    private List<CarStallBean.DataBean> stallData;
    private List<CarQueryBean.DataBean> data;

    private CarQueryPresenterImp carQueryPresenterImp;
    private CarQueryShowAdapter carQueryShowAdapter;
    private CarStallShowAdapter carStallShowAdapter;
    private OnDoubleClickListener onDoubleClickListener;

    private TipsDialog tipsDialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_car_manage;
    }


    @Override
    protected void initData() {
        tipsDialog = new TipsDialog(this, "");
        carQueryPresenterImp = new CarQueryPresenterImp();
        if (null != LocalCache.getCurrentCommunity()){
            String name = TextUtils.isEmpty(LocalCache.getCurrentCommunity().getCommunityName())?"":LocalCache.getCurrentCommunity().getCommunityName();
            tvCar.setText("家庭车辆(" + name + ")");
            tvStall.setText("家庭车位(" + name + ")");
        }

        carShow.setLayoutManager(new LinearLayoutManager(CarManageActivity.this));
        carShow.setNestedScrollingEnabled(false);
        carShow.setHasFixedSize(true);
        carQueryShowAdapter = new CarQueryShowAdapter(R.layout.car_query_item, data);
        carShow.setAdapter(carQueryShowAdapter);

        onDoubleClickListener = new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (carQueryShowAdapter.isShowDelete()) {
                    carQueryShowAdapter.setShowDelete(!carQueryShowAdapter.isShowDelete());
                    mToolBar.setRightTitle("编辑");
                    rlAddCar.setVisibility(View.VISIBLE);

                } else {
                    carQueryShowAdapter.setShowDelete(!carQueryShowAdapter.isShowDelete());
                    mToolBar.setRightTitle("完成");
                    rlAddCar.setVisibility(View.GONE);
                }
            }
        };
        mToolBar.setRightTvTitleClick(onDoubleClickListener);

        carQueryShowAdapter.CarDeleteCallBack(new CarQueryShowAdapter.CarDeleteCallBack() {
            @Override
            public void carDeleteCallBack(CarQueryBean.DataBean item) {
                tipsDialog.show();
                tipsDialog.setTip("删除提示");
                tipsDialog.setTitle("确认删除?");
                tipsDialog.setOnOkClick(new OnDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        TreeMap<String, Object> deleteRequest = NetWorkUtil.getInstance().getBaseRequest();
                        deleteRequest.put("id", item.getId());
                        deleteRequest.put("plateNumber", item.getPlateNumber());
                        deleteRequest.put("visitorCar", item.isVisitorCar());
                        deleteRequest.put("visitorRegistrationId", item.getVisitorRegistrationId());
                        carQueryPresenterImp.carDeleteData(deleteRequest, CarManageActivity.this::carDeleteData);
                        loadingDialog = new LoadingDialog(CarManageActivity.this);
                        loadingDialog.setCanceledOnTouchOutside(false);
                        tipsDialog.dismiss();
                        loadingDialog.show();
                    }
                });
            }

            @Override
            public void inOutHis(CarQueryBean.DataBean item) {
                Bundle bundle = new Bundle();
                bundle.putString("plateNumber", item.getPlateNumber());
//                Intents.getInstence().intent(CarManageActivity.this, CarInOutHisActivity.class, bundle);
            }
        });
        carStallShow.setLayoutManager(new LinearLayoutManager(CarManageActivity.this));
        carStallShow.setNestedScrollingEnabled(false);
        carStallShow.setHasFixedSize(true);
        carStallShowAdapter = new CarStallShowAdapter(R.layout.car_stall_item, stallData);
        carStallShow.setAdapter(carStallShowAdapter);
    }

    @Override
    protected void refreshData() {
        super.refreshData();
        onResume();
    }

    private int backDataTime;

    public void carAddData(JSONArray jsonArray) {
        smartRefreshLayout.finishRefresh();
        backDataTime++;
        data = jsonArray.toJavaList(CarQueryBean.DataBean.class);
        showNoCarView();
        if (data.size() == 0) {
            rlCar.setVisibility(View.GONE);

        } else {

            rlCar.setVisibility(View.VISIBLE);
            carQueryShowAdapter.replaceData(data);

        }
    }


    public void carStallData(JSONArray jsonArray) {
        smartRefreshLayout.finishRefresh();
        backDataTime++;
        stallData = jsonArray.toJavaList(CarStallBean.DataBean.class);
        showNoCarView();
        if (stallData.size() == 0) {
            rlStall.setVisibility(View.GONE);
            return;
        }
        rlStall.setVisibility(View.VISIBLE);
        carStallShowAdapter.replaceData(stallData);

    }

    public void showNoCarView() {
        if (backDataTime == 2) {
            if (data.size() == 0 && stallData.size() == 0) {
                visibility.setVisibility(View.VISIBLE);
                rlAddCar.setVisibility(View.GONE);
                mToolBar.getmRightTvTitle().setVisibility(View.GONE);
            } else {
                visibility.setVisibility(View.GONE);
                rlAddCar.setVisibility(View.VISIBLE);
                mToolBar.getmRightTvTitle().setVisibility(View.VISIBLE);
            }
        }
    }

    public void carDeleteData(Object object) {
        backDataTime = 1;
        if (object == null) {
            return;
        }
        ToastUtils.INSTANCE.showToast(this, "删除成功");
        loadingDialog.dismiss();
        carQueryPresenterImp.CarQueryData(this::carAddData);

    }


    public void carStallDeleteData(Object object) {
        backDataTime = 1;
        if (object == null) {
            return;
        }
        ToastUtils.INSTANCE.showToast(this, "删除成功");
        String id = LocalCache.getCurrentCommunity().getCommunityId();
        carQueryPresenterImp.carStallquery(id, this::carStallData);
        loadingDialog.dismiss();

    }

    @OnClick({R.id.manage_add_car, R.id.add_cars})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.manage_add_car:
            case R.id.add_cars:
                Intents.getInstence().intent(this, CarAddActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        backDataTime = 0;
        super.onResume();
        carQueryPresenterImp.CarQueryData(this::carAddData);
        String id = LocalCache.getCurrentCommunity().getCommunityId();
        carQueryPresenterImp.carStallquery(id, this::carStallData);
    }
}