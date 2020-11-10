package com.jingpai.pos.customer.activity.show.Home;
/*
 * 添加车辆页面
 * */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.house.CarPositionBean;
import com.jingpai.pos.customer.component.keyboard.Keyboard;
import com.jingpai.pos.customer.component.keyboard.PlateNumber;
import com.jingpai.pos.customer.mvp.presenter.show.home.CarAddPresenterImp;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.utils.ToastUtils;

import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

public class CarAddActivity extends BaseActivity {
    private String number;
    @BindView(R.id.keyboard_layout)
    LinearLayout linearLayout;
    @BindView(R.id.plate_number_layout)
    LinearLayout plateNumberLayout;
    private PlateNumber plateNumber;
    private CarAddPresenterImp carAddPresenterImp;

    @Override
    protected int getLayout() {
        return R.layout.activty_add_car;
    }


    @Override
    protected void initData() {
        carAddPresenterImp = new CarAddPresenterImp();
        Keyboard keyboard = new Keyboard(this, linearLayout);
        plateNumber = new PlateNumber(this, keyboard, plateNumberLayout);
    }

    public void carAddData(Object succeed) {
        if (succeed==null){
            return;
        }
        Intent intent = new Intent(CarAddActivity.this, BackResult.class);
        intent.putExtra("returnCode", 0);
        startActivity(intent);
        finish();

    }


    @OnClick(R.id.add_car_btn)
    public void addCar(View view) {
        number = plateNumber.getPlateNumber();
        if (number.length() < PlateNumber.MIN_NUMBER) {
            ToastUtils.INSTANCE.showToast(this, R.string.plate_number_tip);
            return;
        }
        //查询是否有房屋
        carAddPresenter(number);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10&&resultCode==RESULT_OK){
            carAddData("succeed");
        }
    }

    public void carAddPresenter(String number) {
        carAddPresenterImp.carAddChoseStall(this::addSuccess);
    }

    //根据是否有房屋进行页面处理
    public void addSuccess( JSONArray jsonArray) {
        if (jsonArray == null) {
            return;
        }
        List<CarPositionBean.DataBean> dataBeanList = jsonArray.toJavaList(CarPositionBean.DataBean.class);
        if (dataBeanList == null) {
            return;
        }
        if (dataBeanList.size() == 0) {
            addCarUp(number);
        } else {
            //跳转
            Bundle bundle = new Bundle();
            bundle.putString("plateNumber", number);
//            Intents.getInstence().startActivityForResult(this, CarAddChoseStallActivity.class, 10, bundle);
        }
    }

    private void addCarUp(String number) {
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("plateNumber", number);
        baseRequest.put("parkingId", "");
        baseRequest.put("parkingType", "");
        carAddPresenterImp.CarAddData(baseRequest,this::addCarSuccessfull);
    }

    public void addCarSuccessfull(Object o) {
        if (o==null){
            return;
        }
        carAddData("succeed");
    }
}