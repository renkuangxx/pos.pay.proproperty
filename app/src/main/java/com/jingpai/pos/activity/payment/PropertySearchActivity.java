package com.jingpai.pos.activity.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.activity.login.PersonalCenterActivity;
import com.jingpai.pos.bean.NoPayBillBean;
import com.jingpai.pos.bean.house.BuildingBean;
import com.jingpai.pos.bean.house.ParkBean;
import com.jingpai.pos.bean.house.RoomBean;
import com.jingpai.pos.bean.house.SearchByPhoneBean;
import com.jingpai.pos.bean.house.ShopBean;
import com.jingpai.pos.bean.house.UnitBean;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter;
import com.jingpai.pos.customer.mvp.presenter.payment.PaymentPresenter;
import com.jingpai.pos.customer.utils.PickViewUtils;
import com.jingpai.pos.customer.utils.SystemUtils;
import com.jingpai.pos.customer.views.CustomToolBar;
import com.jingpai.pos.customer.views.DownLoadDialog;
import com.jingpai.pos.presenter.house.HousePresenter;
import com.jingpai.pos.presenter.login.VersionPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PropertySearchActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    CustomToolBar toolbar;
    @BindView(R.id.tv_resource_select)
    TextView tvResourceSelect;
    @BindView(R.id.ll_resource)
    LinearLayout llResource;
    @BindView(R.id.tv_build_select)
    TextView tvBuildSelect;
    @BindView(R.id.ll_build)
    LinearLayout llBuild;
    @BindView(R.id.tv_unit_select)
    TextView tvUnitSelect;
    @BindView(R.id.ll_unit)
    LinearLayout llUnit;
    @BindView(R.id.tv_room_select)
    TextView tvRoomSelect;
    @BindView(R.id.ll_room)
    LinearLayout llRoom;
    @BindView(R.id.tv_parking_select)
    TextView tvParkingSelect;
    @BindView(R.id.ll_parking)
    LinearLayout llParking;
    @BindView(R.id.tv_shop_select)
    TextView tvShopSelect;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.iv_me)
    ImageView ivMe;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rl_main)
    RelativeLayout rlMain;
    @BindView(R.id.view_build)
    View viewBuild;
    @BindView(R.id.view_unit)
    View viewUnit;
    @BindView(R.id.view_room)
    View viewRoom;
    @BindView(R.id.view_parking)
    View viewParking;
    @BindView(R.id.view_shop)
    View viewShop;
    @BindView(R.id.view_phone)
    View viewPhone;
    @BindView(R.id.et_phone_select)
    EditText etPhoneSelect;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_require_select)
    TextView tvRequireSelect;
    @BindView(R.id.ll_require)
    LinearLayout llRequire;
    private PaymentPresenter paymentPresenter;
    private HousePresenter housePresenter;

    private ArrayList<String> resourceList = new ArrayList<String>() {{
        add("楼栋");
        add("商铺");
        add("车位");
    }};
    private ArrayList<String> requireList = new ArrayList<String>() {{
        add("按资源查询");
        add("按手机号查询");
    }};
    private ArrayList<BuildingBean> buildingList;
    private ArrayList<String> buildingStrList;
    private ArrayList<UnitBean> unitList;
    private ArrayList<String> unitStrList;
    private ArrayList<RoomBean> roomList;
    private ArrayList<String> roomStrList;
    private ArrayList<ParkBean> parkList;
    private ArrayList<String> parkStrList;
    private ArrayList<ShopBean> shopList;
    private ArrayList<String> shopStrList;
    private LoadingDialog loadingDialog;
    private BuildingBean currentBuild;
    private UnitBean currentUnit;
    private RoomBean currentRoom;
    private ParkBean currentPark;
    private ShopBean currentShop;
    private int searchType = 0;
    private int resouceType = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_property_search;
    }

    @Override
    protected void initData() {
        loadingDialog = new LoadingDialog(PropertySearchActivity.this);
        loadingDialog.setCanceledOnTouchOutside(false);
        paymentPresenter = new PaymentPresenter();
        housePresenter = new HousePresenter();
        toolbar.setBackVisitivity(false);
        checkUpdate();
    }
    @OnClick(R.id.ll_require)
    public void onSearchRequireSelect() {
        PickViewUtils.showOptionsPicker(PropertySearchActivity.this, requireList, new PickViewUtils.SelectedItem() {
            @Override
            public void selectItem(String item, int position) {
                tvRequireSelect.setText(item);
                reSetData();
                switch (position) {
                    case 0://资源
                        searchType = 1;
                        viewPhone.setVisibility(View.GONE);
                        llPhone.setVisibility(View.GONE);
                        viewRoom.setVisibility(View.GONE);
                        llRoom.setVisibility(View.GONE);
                        break;
                    case 1://手机号
                        searchType = 2;
                        viewBuild.setVisibility(View.GONE);
                        llBuild.setVisibility(View.GONE);
                        viewUnit.setVisibility(View.GONE);
                        llUnit.setVisibility(View.GONE);
                        viewRoom.setVisibility(View.VISIBLE);
                        llRoom.setVisibility(View.VISIBLE);
                        viewParking.setVisibility(View.GONE);
                        llParking.setVisibility(View.GONE);
                        viewShop.setVisibility(View.GONE);
                        llShop.setVisibility(View.GONE);
                        viewPhone.setVisibility(View.VISIBLE);
                        llPhone.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @OnClick(R.id.tv_resource_select)
    public void onResourceSelect() {
        if (searchType==0){
            ToastUtils.showShort("请选择查询条件");
            return;
        }
        PickViewUtils.showOptionsPicker(PropertySearchActivity.this, resourceList, new PickViewUtils.SelectedItem() {
            @Override
            public void selectItem(String item, int position) {
                tvResourceSelect.setText(item);
                reSetData();
                switch (position) {
                    case 0://楼栋
                        resouceType = 1;
                        if (searchType==2){
                            viewRoom.setVisibility(View.VISIBLE);
                            llRoom.setVisibility(View.VISIBLE);
                            viewParking.setVisibility(View.GONE);
                            llParking.setVisibility(View.GONE);
                            viewShop.setVisibility(View.GONE);
                            llShop.setVisibility(View.GONE);
                            return;
                        }
                        viewBuild.setVisibility(View.VISIBLE);
                        llBuild.setVisibility(View.VISIBLE);
                        viewUnit.setVisibility(View.VISIBLE);
                        llUnit.setVisibility(View.VISIBLE);
                        viewRoom.setVisibility(View.VISIBLE);
                        llRoom.setVisibility(View.VISIBLE);
                        viewParking.setVisibility(View.GONE);
                        llParking.setVisibility(View.GONE);
                        viewShop.setVisibility(View.GONE);
                        llShop.setVisibility(View.GONE);
                        viewPhone.setVisibility(View.GONE);
                        llPhone.setVisibility(View.GONE);
//                        if (buildingList==null){
//                            getBuildingList();
//                        }else {
//                            showBuildingPicker();
//                        }
                        break;
                    case 1://商铺
                        resouceType = 3;
                        if (searchType==2){
                            viewRoom.setVisibility(View.GONE);
                            llRoom.setVisibility(View.GONE);
                            viewParking.setVisibility(View.GONE);
                            llParking.setVisibility(View.GONE);
                            viewShop.setVisibility(View.VISIBLE);
                            llShop.setVisibility(View.VISIBLE);
                            return;
                        }
                        viewBuild.setVisibility(View.GONE);
                        llBuild.setVisibility(View.GONE);
                        viewUnit.setVisibility(View.GONE);
                        llUnit.setVisibility(View.GONE);
                        viewRoom.setVisibility(View.GONE);
                        llRoom.setVisibility(View.GONE);
                        viewParking.setVisibility(View.GONE);
                        llParking.setVisibility(View.GONE);
                        viewShop.setVisibility(View.VISIBLE);
                        llShop.setVisibility(View.VISIBLE);
//                        if (buildingList==null){
//                            getShopList();
//                        }else {
//                            showShopPicker();
//                        }

                        break;
                    case 2://车位
                        resouceType = 2;
                        if (searchType==2){
                            viewRoom.setVisibility(View.GONE);
                            viewRoom.setVisibility(View.GONE);
                            llRoom.setVisibility(View.GONE);
                            viewParking.setVisibility(View.VISIBLE);
                            llParking.setVisibility(View.VISIBLE);
                            viewShop.setVisibility(View.GONE);
                            llShop.setVisibility(View.GONE);
                            return;
                        }
                        viewBuild.setVisibility(View.GONE);
                        llBuild.setVisibility(View.GONE);
                        viewUnit.setVisibility(View.GONE);
                        llUnit.setVisibility(View.GONE);
                        viewRoom.setVisibility(View.GONE);
                        llRoom.setVisibility(View.GONE);
                        viewParking.setVisibility(View.VISIBLE);
                        llParking.setVisibility(View.VISIBLE);
                        viewShop.setVisibility(View.GONE);
                        llShop.setVisibility(View.GONE);
//                        if (buildingList==null){
//                            getParkList();
//                        }else {
//                            showParkPicker();
//                        }
                        break;
                }
            }
        });
    }


    private void getBuildingList() {
        if (resouceType==0){
            ToastUtils.showShort("请选择资源");
            return;
        }
        loadingDialog.show();
        housePresenter.getBuildingList(new BasePresenter.Callback<JSONArray>() {
            @Override
            public void success(JSONArray objects) {
                loadingDialog.dismiss();
                buildingList = (ArrayList<BuildingBean>) objects.toJavaList(BuildingBean.class);
                showBuildingPicker();
            }

            @Override
            public void failure(JSONArray objects) {
                loadingDialog.dismiss();
            }
        });
    }

    private void getUnitList() {
        if (currentBuild == null) {
            ToastUtils.showShort("请选择楼栋");
            return;
        }
        housePresenter.getUnitList(currentBuild.getId(), new BasePresenter.Callback<JSONArray>() {

            @Override
            public void success(JSONArray objects) {
                loadingDialog.dismiss();
                unitList = (ArrayList<UnitBean>) objects.toJavaList(UnitBean.class);
                showUnitPicker();
            }

            @Override
            public void failure(JSONArray objects) {
                loadingDialog.dismiss();
            }
        });
    }

    private void getRoomList() {
        if (searchType==2){
//            ValidatorUtil.isMobile(phone)
            cheakPhone();
        }else{
            if (currentBuild == null || currentUnit == null) {
                ToastUtils.showShort("请选择单元");
                return;
            }
            housePresenter.getRoomList(currentBuild.getId(), currentUnit.getId(), new BasePresenter.Callback<JSONArray>() {

                @Override
                public void success(JSONArray objects) {
                    loadingDialog.dismiss();
                    roomList = (ArrayList<RoomBean>) objects.toJavaList(RoomBean.class);
                    showRoomPicker();
                }

                @Override
                public void failure(JSONArray objects) {
                    loadingDialog.dismiss();
                }
            });
        }

    }

    private void cheakPhone(){
        if (resouceType==0){
            ToastUtils.showShort("请选择资源");
            return;
        }
        String phone = etPhoneSelect.getText().toString();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showShort("请输入手机号");
            return;
        }
        if (phone.length()!=11){
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        housePresenter.getListByPhone(resouceType, phone, new BasePresenter.Callback<SearchByPhoneBean>() {

            @Override
            public void success(SearchByPhoneBean searchByPhoneBean) {
                loadingDialog.dismiss();

                if (resouceType==1){
                    roomList = searchByPhoneBean.getHouseSimpleList();
                    showRoomPicker();
                }else if (resouceType==2){
                    parkList =  searchByPhoneBean.getParkingEntityList();
                    showParkPicker();
                }else if (resouceType==3){
                    shopList = searchByPhoneBean.getShopResultVOList();
                    showShopPicker();
                }
            }

            @Override
            public void failure(SearchByPhoneBean searchByPhoneBean) {
                loadingDialog.dismiss();
            }
        });
    }
    private void getParkList() {
        if (searchType==2){
            cheakPhone();
        }else{
            housePresenter.getParkList(new BasePresenter.Callback<JSONArray>() {

                @Override
                public void success(JSONArray objects) {
                    loadingDialog.dismiss();
                    parkList = (ArrayList<ParkBean>) objects.toJavaList(ParkBean.class);
                    showParkPicker();
                }

                @Override
                public void failure(JSONArray objects) {
                    loadingDialog.dismiss();
                }
            });
        }

    }

    private void getShopList() {
        if (searchType==2){
            cheakPhone();
        }else{
            housePresenter.getShopList(new BasePresenter.Callback<JSONArray>() {

                @Override
                public void success(JSONArray objects) {
                    loadingDialog.dismiss();
                    shopList = (ArrayList<ShopBean>) objects.toJavaList(ShopBean.class);
                    showShopPicker();
                }

                @Override
                public void failure(JSONArray objects) {
                    loadingDialog.dismiss();
                }
            });
        }

    }

    private void showBuildingPicker() {
        if (buildingList == null || buildingList.size() == 0) return;
        buildingStrList = new ArrayList<>();
        for (BuildingBean item : buildingList) {
            buildingStrList.add(item.getBuildingName());
        }
        PickViewUtils.showOptionsPicker(PropertySearchActivity.this, buildingStrList, new PickViewUtils.SelectedItem() {
            @Override
            public void selectItem(String item, int position) {
                currentBuild = buildingList.get(position);
                tvBuildSelect.setText(item);
            }
        });
    }

    private void showUnitPicker() {
        if (unitList == null || unitList.size() == 0) return;
        unitStrList = new ArrayList<>();
        for (UnitBean item : unitList) {
            unitStrList.add(item.getUnitName());
        }
        PickViewUtils.showOptionsPicker(PropertySearchActivity.this, unitStrList, new PickViewUtils.SelectedItem() {
            @Override
            public void selectItem(String item, int position) {
                tvUnitSelect.setText(item);
                currentUnit = unitList.get(position);
            }
        });
    }

    private void showRoomPicker() {
        if (roomList == null || roomList.size() == 0) {
            ToastUtils.showShort("没有符合条件的房间");
            return;
        }
        roomStrList = new ArrayList<>();
        for (RoomBean item : roomList) {
            roomStrList.add(item.getRoomNo());
        }
        PickViewUtils.showOptionsPicker(PropertySearchActivity.this, roomStrList, new PickViewUtils.SelectedItem() {
            @Override
            public void selectItem(String item, int position) {
                tvRoomSelect.setText(item);
                currentRoom = roomList.get(position);
            }
        });
    }

    private void showParkPicker() {
        if (parkList == null || parkList.size() == 0) {
            ToastUtils.showShort("没有符合条件的车位");
            return;
        }
        parkStrList = new ArrayList<>();
        for (ParkBean item : parkList) {
            parkStrList.add(item.getName() + ":" + item.getParkingPlace() + item.getParkingNo() + "号车位");
        }

        PickViewUtils.showOptionsPicker(PropertySearchActivity.this, parkStrList, new PickViewUtils.SelectedItem() {
            @Override
            public void selectItem(String item, int position) {
                tvParkingSelect.setText(item);
                currentPark = parkList.get(position);
            }
        });
    }

    private void showShopPicker() {
        if (shopList == null || shopList.size() == 0) {
            ToastUtils.showShort("没有符合条件的商铺");
            return;
        }
        shopStrList = new ArrayList<>();
        for (ShopBean item : shopList) {
            shopStrList.add(item.getNumber());
        }

        PickViewUtils.showOptionsPicker(PropertySearchActivity.this, shopStrList, new PickViewUtils.SelectedItem() {
            @Override
            public void selectItem(String item, int position) {
                tvShopSelect.setText(item);
                currentShop = shopList.get(position);
            }
        });
    }

    @OnClick(R.id.tv_build_select)
    public void onBuildSelect() {
//        if (buildingList==null||buildingList.size()==0){
        if (SystemUtils.isFastClick())return;
        getBuildingList();
//        }else {
//            showBuildingPicker();
//        }
    }

    @OnClick(R.id.tv_unit_select)
    public void onUnitSelect() {
//        if (unitList==null||unitList.size()==0){
        if (SystemUtils.isFastClick())return;
        getUnitList();
//        }else {
//            showUnitPicker();
//        }
    }

    @OnClick(R.id.tv_room_select)
    public void onRoomSelect() {
//        if (roomList==null||roomList.size()==0){
        if (SystemUtils.isFastClick())return;
        getRoomList();
//        }else {
//            showRoomPicker();
//        }
    }

    @OnClick(R.id.tv_parking_select)
    public void onParkingSelect() {
//        if (parkList==null||parkList.size()==0){
        if (SystemUtils.isFastClick())return;
        getParkList();
//        }else {
//            showParkPicker();
//        }
    }

    @OnClick(R.id.tv_shop_select)
    public void onShopSelect() {
//        if (shopList==null||shopList.size()==0){
        if (SystemUtils.isFastClick())return;
        getShopList();
//        }else {
//            showShopPicker();
//        }
    }

    @OnClick(R.id.tv_search)
    public void onSearchClick() {
        if (SystemUtils.isFastClick())return;
        getNoPayBillInfo();
    }

    private void getNoPayBillInfo() {
        if (searchType == 0) {
            ToastUtils.showShort("请选择查询条件");
            return;
        }
        if (resouceType == 0) {
            ToastUtils.showShort("请选择资源");
            return;
        }
        String sreaId = "";
        if (resouceType == 1) {
            if (currentRoom == null) {
                ToastUtils.showShort("请选择房间");
                return;
            }
            sreaId = currentRoom.getId();
        } else if (resouceType == 2) {
            if (currentPark == null) {
                ToastUtils.showShort("请选择车位");
                return;
            }
            sreaId = currentPark.getId();
        } else if (resouceType == 3) {
            if (currentShop == null) {
                ToastUtils.showShort("请选择商铺");
                return;
            }
            sreaId = currentShop.getId();
        }
        housePresenter.getNoPayBillInfo(sreaId, resouceType, new BasePresenter.Callback<NoPayBillBean>() {

            @Override
            public void success(NoPayBillBean noPayBillBean) {
                loadingDialog.dismiss();
                if (noPayBillBean == null || noPayBillBean.getUnpaidBillList().isEmpty()) {
                    ToastUtils.showShort("您还没有待缴费记录哦");
                    return;
                }
                Intent intent = new Intent(PropertySearchActivity.this, PaymentActivity.class);
                intent.putExtra("noPayBillBean", noPayBillBean);
                startActivity(intent);
            }

            @Override
            public void failure(NoPayBillBean objects) {
                loadingDialog.dismiss();
            }
        });
    }

    @OnClick(R.id.iv_me)
    public void onGotoMeClick() {
        if (SystemUtils.isFastClick())return;
        Intent intent = new Intent(PropertySearchActivity.this, PersonalCenterActivity.class);
        startActivity(intent);
    }

    private void reSetData(){
        currentRoom = null;
        currentShop = null;
        currentPark = null;
        currentUnit = null;
        currentBuild = null;
        tvRoomSelect.setText("请选择房间");
        tvParkingSelect.setText("请选择车位");
        tvShopSelect.setText("请选择商铺");
//        etPhoneSelect.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void checkUpdate(){
        VersionPresenter versionPresenter = new VersionPresenter(this);
        versionPresenter.checkUpdate(versionBean -> {
            if (versionBean != null && versionBean.getVersionCode() > AppUtils.getAppVersionCode()) {

                rlMain.post(new Runnable() {
                    @Override
                    public void run() {
                        DownLoadDialog downLoadDialog = new DownLoadDialog(PropertySearchActivity.this,versionBean);
                        downLoadDialog.setOkListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                versionPresenter.startDownLoad( versionBean.getDownloadUrl());
                            }
                        });
                        downLoadDialog.show(rlMain);
                    }
                });

            }
        });
    }
}

