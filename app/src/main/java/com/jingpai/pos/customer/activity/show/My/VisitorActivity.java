package com.jingpai.pos.customer.activity.show.My;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.house.VisitHisBean;
import com.jingpai.pos.customer.bean.show.BuildingBean;
import com.jingpai.pos.customer.component.dialog.KeyBoardDialog;
import com.jingpai.pos.customer.component.keyboard.Keyboard;
import com.jingpai.pos.customer.component.keyboard.PlateNumber;
import com.jingpai.pos.customer.mvp.presenter.show.home.HousePresenter;
import com.jingpai.pos.customer.mvp.presenter.show.my.VisitorPresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.CommonPopWindow;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.PickerScrollView;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.utils.WxShareUtils;

import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 时间: 2020/2/12
 * 功能:访客登记
 */
public class VisitorActivity extends BaseActivity implements CommonPopWindow.ViewClickListener, Keyboard.OnClickListener {
    @BindView(R.id.visitor_car)
    LinearLayout visitorCar;
    @BindView(R.id.tv_visitor)
    TextView tvVisitor;
    @BindView(R.id.tv_visitor_car)
    TextView tvVisitorCar;
    @BindView(R.id.v_view)
    View vView;
    @BindView(R.id.et_visitor_name)
    EditText etVisitorName;
    @BindView(R.id.et_visitor_phone)
    EditText etVisitorPhone;
    @BindView(R.id.et_car_number)
    TextView etCarNumber;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_house_name)
    TextView tvHouseName;

    @BindView(R.id.et_certificate_num)
    EditText etCertificateNum;
    @BindView(R.id.ll_visit_time)
    LinearLayout llVisitTime;
    @BindView(R.id.tv_visit_time)
    TextView tvVisitTime;
    @BindView(R.id.iv_less_time)
    ImageView ivLessTime;
    @BindView(R.id.iv_add_time)
    ImageView ivAddTime;
    @BindView(R.id.tv_car_parkingId)
    TextView tvCarParkingId;
    private String visitType = "VISITOR";
    private String date;
    private String visitorDate = "请选择";
    private String carDate = "请选择";
    //访问时长
    private int visitTime = 1;
    private int hourOfDay = 0;
    private List<BuildingBean.DataBean> buildingBean;
    private String houseName;
    private String houseId;
    private KeyBoardDialog keyBoardDialog;
    private VisitorPresenter visitorPresenter;

    private LoadingDialog loadingDialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_visitor;
    }


    @Override
    protected void initData() {
        visitorPresenter = new VisitorPresenter();
        loadingDialog=new LoadingDialog(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("communityId", LocalCache.getCurrentCommunityId());
        HousePresenter housePresenter = new HousePresenter();
        housePresenter.getHouse(baseRequest, jsonArray -> {
            buildingBean = jsonArray.toJavaList(BuildingBean.DataBean.class);
            if (buildingBean != null && !buildingBean.isEmpty()) {
                BuildingBean.DataBean houseData = buildingBean.get(0);
                houseId = houseData.getHouseId();
                houseName = houseData.getBuildNo()+houseData.getHouseName();
                tvHouseName.setText(houseName);
            }
        });


        keyBoardDialog = new KeyBoardDialog(this, this);
        keyBoardDialog.setOnClickListener(this);

        mToolBar.setRightTvTitleClick((v) -> {
            //来访记录
//            Intents.getInstence().intent(VisitorActivity.this, VisitHisActivity.class);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            VisitHisBean.DataBean bean = (VisitHisBean.DataBean) extras.getSerializable("bean");
            etVisitorName.setText(bean.getVisitorName());
            etVisitorPhone.setText(bean.getVisitorPhone());
            etCertificateNum.setText(bean.getVisitorIdCard());
            etCarNumber.setText(bean.getLicensePlateNo());
            onViewClicked(tvVisitorCar);
        }
    }

    @OnClick({R.id.tv_visitor, R.id.tv_visitor_car, R.id.et_visitor_name, R.id.et_visitor_phone, R.id.iv_date,
            R.id.tv_date, R.id.yes_btn, R.id.iv_my_name, R.id.iv_add_time, R.id.iv_less_time, R.id.ll_chose_car_position})
    public void onViewClicked(View view) {
        etVisitorName.setFocusable(false);
        etVisitorPhone.setFocusable(false);
        switch (view.getId()) {
            case R.id.tv_visitor:
                tvDate.setText(visitorDate);
                //访客
                visitType = "VISITOR";
                visitorCar.setVisibility(View.GONE);
                llVisitTime.setVisibility(View.GONE);
                vView.setVisibility(View.GONE);
                tvVisitorCar.setBackgroundResource(R.drawable.ic_tabunselected);
                tvVisitor.setBackgroundResource(R.drawable.ic_tabselected);
                tvVisitor.setTextColor(Color.parseColor("#FFFFFF"));
                tvVisitorCar.setTextColor(Color.parseColor("#FF8A49"));
                break;
            case R.id.tv_visitor_car:
                tvDate.setText(carDate);
                //访车
                visitType = "VISITCAR";
                visitorCar.setVisibility(View.VISIBLE);
                llVisitTime.setVisibility(View.VISIBLE);
                tvVisitor.setBackgroundResource(R.drawable.ic_tabunselected);
                tvVisitorCar.setBackgroundResource(R.drawable.ic_tabselected);
                vView.setVisibility(View.VISIBLE);
                tvVisitorCar.setTextColor(Color.parseColor("#FFFFFF"));
                tvVisitor.setTextColor(Color.parseColor("#FF8A49"));
                break;
            case R.id.et_visitor_name:
                etVisitorName.setCursorVisible(true);
                etVisitorName.setFocusable(true);
                etVisitorName.setFocusableInTouchMode(true);
                etVisitorName.requestFocus();
                break;
            case R.id.et_visitor_phone:
                etVisitorPhone.setCursorVisible(true);
                etVisitorPhone.setFocusable(true);
                etVisitorPhone.setFocusableInTouchMode(true);
                etVisitorPhone.requestFocus();
                break;
            case R.id.yes_btn:
                String carNumber = etCarNumber.getText().toString();
                String name = etVisitorName.getText().toString();
                String phone = etVisitorPhone.getText().toString();
                String certificateNum = etCertificateNum.getText().toString();
                String userId = LocalCache.getUserId();
                String parkingId = this.parkingId;
                if ("VISITCAR".equals(visitType)) {
                    if (TextUtils.isEmpty(parkingId)) {
                        ToastUtils.INSTANCE.showToast("请选择停车位");
                        return;
                    }
                    if (TextUtils.isEmpty(carNumber)) {
                        ToastUtils.INSTANCE.showToast("请输入车牌号");
                        return;
                    }
                }
                if (name.isEmpty()) {
                    ToastUtils.INSTANCE.showToast("请输入访客姓名");
                } else {

                    if (TextUtils.isEmpty(phone)) {
                        ToastUtils.INSTANCE.showToast("请输入手机号");
                        return;
                    }

                    TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                    baseRequest.put("communityId", LocalCache.getCurrentCommunityId());
                    baseRequest.put("houseId", houseId);
                    baseRequest.put("visitorName", name);
                    baseRequest.put("visitorPhone", phone);
                    baseRequest.put("visitorIdCard", certificateNum);
                    baseRequest.put("userId", userId);
                    baseRequest.put("visitType", visitType);
                    if (visitType.equals("VISITOR")) {
                        date = visitorDate;
                    } else {
                        //访车
                        date = carDate;
                        baseRequest.put("licensePlateNo", carNumber);
                        baseRequest.put("parkingId", parkingId);
                        baseRequest.put("parkingHouseId", parkingHouseId);
                        baseRequest.put("visitUseTime", visitTime);
                    }
                    if ("请选择".equals(date)) {
                        ToastUtils.INSTANCE.showToast("请输入日期");
                        return;
                    }
                    baseRequest.put("visitDate", date);
                    loadingDialog.show();
                    visitorPresenter.getVisitor(baseRequest, visitorBean -> {
                        loadingDialog.dismiss();
                        String password = visitorBean.getPassword();
                        String qrCode = visitorBean.getQrCode();
                        String text = visitorBean.getText();
                        if (qrCode == null || qrCode.equals("")) {
                            ToastUtils.INSTANCE.showToast("添加成功!");
                            finish();
                        } else {
                            WxShareUtils.showShareDialog(VisitorActivity.this, password, qrCode, text);
                        }

                    });
                }
                break;
            case R.id.tv_date:
            case R.id.iv_date:
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(this, (view12, year, month, dayOfMonth) -> {
                    if (year < calendar.get(Calendar.YEAR)) {
                        ToastUtils.INSTANCE.showToast("不能选择过去的时间");
                        return;
                    }
                    if (year == calendar.get(Calendar.YEAR) && month < calendar.get(Calendar.MONTH)) {
                        ToastUtils.INSTANCE.showToast("不能选择过去的时间");
                        return;
                    }
                    if (month == calendar.get(Calendar.MONTH) && dayOfMonth < calendar.get(Calendar.DAY_OF_MONTH)) {
                        ToastUtils.INSTANCE.showToast("不能选择过去的时间");
                        return;
                    }
                    if (month < 9) {
                        String NewMonth = "0" + (month + 1);
                        if (dayOfMonth < 10) {
                            String NewDayOfMonth = "0" + dayOfMonth;
                            date = year + "-" + (NewMonth) + "-" + NewDayOfMonth;
                        } else {
                            date = year + "-" + (NewMonth) + "-" + dayOfMonth;
                        }
                    } else {
                        if (dayOfMonth < 10) {
                            String NewDayOfMonth = "0" + dayOfMonth;
                            date = year + "-" + (month + 1) + "-" + NewDayOfMonth;
                        } else {
                            date = year + "-" + (month + 1) + "-" + dayOfMonth;
                        }
                    }

                    if (visitType.equals("VISITOR")) {
                        //访客不需要显示时间
                        visitorDate = date;
                        tvDate.setText(date);
                        return;
                    }
                    //显示选择时间
                    new TimePickerDialog(this, (timeView, h, m) -> {
                        if (dayOfMonth == calendar.get(Calendar.DAY_OF_MONTH)) {
                            if (h < calendar.get(Calendar.HOUR_OF_DAY)) {
                                ToastUtils.INSTANCE.showToast("不能选择过去的时间");
                                h = calendar.get(Calendar.HOUR_OF_DAY);

                            }

                            if ( h == calendar.get(Calendar.HOUR_OF_DAY)&& m< calendar.get(Calendar.MINUTE)) {
                                ToastUtils.INSTANCE.showToast("不能选择过去的时间");
                                m = calendar.get(Calendar.MINUTE);
                            }
                        }


                        if (h < 10) {
                            date = date + " 0" + h;
                        } else {
                            date = date + " " + h;
                        }

                        if (m < 10) {
                            date = date + ":0" + m;
                        } else {
                            date = date + ":" + m;
                        }
                        //记录选择的小时
                        hourOfDay = h;
                        //重置时长
                        visitTime = 1;
                        tvVisitTime.setText(visitTime + "小时");
                        addTime(false);
                        //设置车访日期
                        carDate = date;
                        tvDate.setText(date);
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
                        , calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.iv_my_name:
                if (buildingBean.size() == 0) {
                    Toast.makeText(this, "当前小区尚未添加房屋", Toast.LENGTH_SHORT).show();
                } else {
                    setAddressSelectorPopup(view);
                }
                break;

            case R.id.iv_less_time:
                //减少时长
                addTime(false);
                break;
            case R.id.iv_add_time:
                //增加时长
                addTime(true);
                break;

            case R.id.ll_chose_car_position:
//                Intents.getInstence().startActivityForResult(this, VisitChoseHouseActivity.class, 10);

                break;
        }
    }


    private void addTime(boolean isAdd) {
        if (hourOfDay == 0) {
            ToastUtils.INSTANCE.showToast("请选择来访日期");
            return;
        }
        if (isAdd) {
            visitTime++;
        } else {
            visitTime--;
        }

        if (visitTime > (24 - hourOfDay)) {
            ivAddTime.setImageResource(R.mipmap.plus_np);
            visitTime = 24 - hourOfDay;
        } else {
            ivAddTime.setImageResource(R.mipmap.plus);
        }
        if (24 - hourOfDay <= 1) {
            ivAddTime.setImageResource(R.mipmap.plus_np);
        }

        if (visitTime <= 1) {
            ivLessTime.setImageResource(R.mipmap.less_np);
            visitTime = 1;
        } else {
            ivLessTime.setImageResource(R.mipmap.less);
        }
        tvVisitTime.setText(visitTime + "小时");
    }


    @OnClick(R.id.et_car_number)
    public void showKeyboard() {
        keyBoardDialog.show();
        etVisitorName.setFocusable(false);
        etVisitorPhone.setFocusable(false);
        String oldValue = etCarNumber.getText().toString();
        if (StringUtils.isEmpty(oldValue)) {
            keyBoardDialog.showProvince();
        } else {
            keyBoardDialog.showKeyword();
        }
    }

    @Override
    public void onKeyboard(String value) {
        String oldValue = etCarNumber.getText().toString();
        if (StringUtils.isEmpty(value)) {
            if (oldValue.length() > 0) {
                etCarNumber.setText(oldValue.substring(0, oldValue.length() - 1));
            }
        } else if (oldValue.length() < PlateNumber.MAX_NUMBER) {
            etCarNumber.setText(oldValue + value);
        }


        String plateNumber = etCarNumber.getText().toString();

        if (StringUtils.isEmpty(plateNumber)) {
            keyBoardDialog.showProvince();
        } else if (plateNumber.length() < PlateNumber.MAX_NUMBER) {
            keyBoardDialog.showKeyword();
        } else {
            keyBoardDialog.dismiss();
        }
    }

    /**
     * 将选择器放在底部弹出框
     *
     * @param v
     */
    private void setAddressSelectorPopup(View v) {
        int screenHeigh = getResources().getDisplayMetrics().heightPixels;
        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_picker_selector_bottom)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.3f))
                .setViewOnClickListener(this)
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(this)
                .showAsBottom(v);
    }

    @Override
    public void getChildView(final PopupWindow mPopupWindow, View view, int mLayoutResId) {
        switch (mLayoutResId) {
            case R.layout.pop_picker_selector_bottom:
                TextView imgCancel = view.findViewById(R.id.img_cancel);
                TextView imageBtn = view.findViewById(R.id.img_guanbi);


                PickerScrollView addressSelector = view.findViewById(R.id.address);

                // 设置数据，默认选择第一条
                addressSelector.setData(buildingBean);
                //滚动监听
                addressSelector.setOnSelectListener(new PickerScrollView.onSelectListener() {
                    @Override
                    public void onSelect(BuildingBean.DataBean pickers) {
                        houseName = pickers.getHouseName();
                        houseId = pickers.getHouseId();
                    }
                });
                //完成按钮
                imageBtn.setOnClickListener(v -> {
                    mPopupWindow.dismiss();
                    tvHouseName.setText(houseName);
                });
                //完成按钮
                imgCancel.setOnClickListener(v -> {
                    mPopupWindow.dismiss();
                });
                break;
        }
    }

    private int parkingHouseId;
    private String parkingId;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                parkingId = data.getStringExtra("parkingId");
                String parkingPlace = data.getStringExtra("parkingPlace");
                parkingHouseId = data.getIntExtra("houseId", -1);
                if (!TextUtils.isEmpty(parkingId)) {
                    tvCarParkingId.setText(parkingPlace);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}