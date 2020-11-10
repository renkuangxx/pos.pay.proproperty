package com.jingpai.pos.customer.activity.census.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.GatherInfoPresenterOld;
import com.jingpai.pos.customer.activity.census.bean.PopulationBean;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.InfoBean;
import com.jingpai.pos.customer.utils.CommonPopWindow;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.PickerScrollNewView;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 86173
 */
public class LastStepInfoActivity extends BaseActivity  {

    @BindView(R.id.tv_political_outlook_selector)
    TextView tvPoliticalOutlookSelector;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.underline_location)
    View underlineLocation;
    @BindView(R.id.tv_time_joining_selector)
    TextView tvTimeJoiningSelector;
    @BindView(R.id.ll_time_joining)
    LinearLayout llTimeJoining;
    @BindView(R.id.underline_time_joining)
    View underlineTimeJoining;
    @BindView(R.id.tv_employment_retirement_selector)
    TextView tvEmploymentRetirementSelector;
    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.ll_company_name)
    LinearLayout llCompanyName;
    @BindView(R.id.underline_employer_name)
    View underlineEmployerName;
    @BindView(R.id.tv_true)
    TextView tvTrue;
    @BindView(R.id.tv_false)
    TextView tvFalse;
    @BindView(R.id.underline_only)
    View underlineOnly;
    @BindView(R.id.et_certificate_num)
    EditText etCertificateNum;
    @BindView(R.id.tv_veterans_select)
    TextView tvVeteransSelect;
    @BindView(R.id.tv_returned_overseas_select)
    TextView tvReturnedOverseasSelect;
    @BindView(R.id.btn_next_info)
    Button btnNextInfo;
    @BindView(R.id.fl_timer)
    FrameLayout flTimer;

    private TimePickerView pvTime;
    private GatherInfoPresenterOld gatherInfoPresenter;
    private int isSingletonStatus =0;//1未独生子女，2未非独生
    private PopulationBean populationBean;
    private String idPassportNo;
    private int id;
    private String idNum;

    @Override
    protected int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_last_step;
    }

    @Override
    protected void initData() {
        initView();
    }

    private void initView() {
        gatherInfoPresenter = new GatherInfoPresenterOld();
        idNum = getIntent().getStringExtra("idNum");
        if (!TextUtils.isEmpty(idNum)) {
            populationBean = (PopulationBean) LocalCache.getInfo(idNum);
            if (populationBean != null) {
                if (!TextUtils.isEmpty(populationBean.getPoliticalLandscape())) {
                    tvPoliticalOutlookSelector.setText(populationBean.getPoliticalLandscape());
                    if ("中共党员".equals(populationBean.getPoliticalLandscape())) {
                        llLocation.setVisibility(View.VISIBLE);
                        underlineLocation.setVisibility(View.VISIBLE);
                        llTimeJoining.setVisibility(View.VISIBLE);
                        underlineTimeJoining.setVisibility(View.VISIBLE);
                    } else {
                        llLocation.setVisibility(View.GONE);
                        underlineLocation.setVisibility(View.GONE);
                        llTimeJoining.setVisibility(View.GONE);
                        underlineTimeJoining.setVisibility(View.GONE);
                    }
                }
                if (!TextUtils.isEmpty(populationBean.getLocationOrgRelationship())) { //关系所在地 locationOrgRelationship
                    etLocation.setText(populationBean.getLocationOrgRelationship());
                }
                if (populationBean.getId() != 0) {
                    id = populationBean.getId();
                }
                if (!TextUtils.isEmpty(populationBean.getJoinPartyDate())) {
                    tvTimeJoiningSelector.setText(populationBean.getJoinPartyDate());
                }
                if (!TextUtils.isEmpty(populationBean.getEmploymentStatus())) {
                    tvEmploymentRetirementSelector.setText(populationBean.getEmploymentStatus());

                }
                if ("在职".equals(populationBean.getEmploymentStatus())) {
                    llCompanyName.setVisibility(View.VISIBLE);
                    underlineEmployerName.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(populationBean.getCompanyName())) {
                        etCompanyName.setText(populationBean.getCompanyName());
                    }
                }else {
                    llCompanyName.setVisibility(View.GONE);
                    underlineEmployerName.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(populationBean.getSingletonStatus())) {
                    etCertificateNum.setVisibility(View.VISIBLE);
                    underlineOnly.setVisibility(View.VISIBLE);
                    String singletonStatus = populationBean.getSingletonStatus();
                    if (singletonStatus.equals("是")) {
                        tvTrue.setTextColor(getResources().getColor(R.color.bg_census));
                        tvTrue.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                        tvFalse.setTextColor(getResources().getColor(R.color.bg_census_default));
                        tvFalse.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                        isSingletonStatus = 1;
                    } else {
                        tvFalse.setTextColor(getResources().getColor(R.color.bg_census));
                        tvFalse.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                        tvTrue.setTextColor(getResources().getColor(R.color.bg_census_default));
                        tvTrue.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                        isSingletonStatus = 2;
                        etCertificateNum.setVisibility(View.GONE);
                    }
                    if (!TextUtils.isEmpty(populationBean.getSingletonCardNo())) {
                        etCertificateNum.setText(populationBean.getSingletonCardNo());
                    }
                }
                if (!TextUtils.isEmpty(populationBean.getSoldier())) {
                    tvVeteransSelect.setText(populationBean.getSoldier());
                }
                if (!TextUtils.isEmpty(populationBean.getOversea())) {
                    tvReturnedOverseasSelect.setText(populationBean.getOversea());
                }
            }
        }
    }


    @OnClick({R.id.tv_political_outlook_selector,R.id.tv_time_joining_selector,R.id.tv_employment_retirement_selector
    ,R.id.tv_true,R.id.tv_false,R.id.tv_veterans_select,R.id.tv_returned_overseas_select,R.id.btn_next_info})
    public void onClick(View v) {
        switch (v.getId()) {
            //点击选择政治面貌
            case R.id.tv_political_outlook_selector:
                setAddressSelectorPopup0(v);
                break;

            //点击选择入党时间
            case R.id.tv_time_joining_selector:
                flTimer.setVisibility(View.VISIBLE);
                btnNextInfo.setVisibility(View.GONE);
                initTimePicker();
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(2000, 0, 1);
                pvTime.setDate(selectedDate);
                pvTime.show(v, false);
                break;
            //点击选择就业/离退休
            case R.id.tv_employment_retirement_selector:
                setAddressSelectorPopup1(v);
                break;
            //点击选择独生女 是
            case R.id.tv_true:
                etCertificateNum.setVisibility(View.VISIBLE);
                underlineOnly.setVisibility(View.VISIBLE);
                tvTrue.setTextColor(getResources().getColor(R.color.bg_census));
                tvTrue.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvFalse.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvFalse.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                isSingletonStatus = 1;
                break;
            //点击选择独生女 否
            case R.id.tv_false:
                //隐藏填写证号
                etCertificateNum.setVisibility(View.GONE);
                underlineOnly.setVisibility(View.GONE);
                tvFalse.setTextColor(getResources().getColor(R.color.bg_census));
                tvFalse.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvTrue.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvTrue.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                isSingletonStatus = 2;
                break;
            //点击选择退役军人
            case R.id.tv_veterans_select:
                setAddressSelectorPopup2(v);
                break;

            //点击选择现归侨人员
            case R.id.tv_returned_overseas_select:
                setAddressSelectorPopup3(v);
                break;

            //点击下一步
            case R.id.btn_next_info:
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("id", idNum);                                       //id
                //======================政治面貌
                String zhengZhi = tvPoliticalOutlookSelector.getText().toString();
                if (TextUtils.isEmpty(zhengZhi) || zhengZhi.equals("请选择")) {
                    Toast.makeText(this, "请选择政治面貌", Toast.LENGTH_SHORT).show();
                    return;
                }
                baseRequest.put("politicalLandscape", zhengZhi);
                //=====================入党时间/关系所在地
                String addTime = tvTimeJoiningSelector.getText().toString();
                String local = etLocation.getText().toString();
                if (zhengZhi.equals("中共党员")) {
                    if (TextUtils.isEmpty(addTime) || addTime.equals("请选择")) {
                        Toast.makeText(this, "入党时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(local)) {
                        Toast.makeText(this, "请填写组织关系所在地", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (local.length()<5) {
                        Toast.makeText(this, "组织关系所在地不得少于5个字符", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    baseRequest.put("joinPartyDate", addTime);
                    baseRequest.put("locationOrgRelationship", local);
                }
                //=============================就业/离退休
                String workStatus = tvEmploymentRetirementSelector.getText().toString();
                if (TextUtils.isEmpty(workStatus) || workStatus.equals("请选择")) {
                    Toast.makeText(this, "请选择就业/离退休", Toast.LENGTH_SHORT).show();
                    return;
                }
                baseRequest.put("employmentStatus", workStatus);
                // ======================工作单位名称
                String etEmployerNameStr = etCompanyName.getText().toString();
                if ("在职".equals(workStatus)){
                    if (TextUtils.isEmpty(etEmployerNameStr)) {
                        Toast.makeText(this, "请输入工作单位名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etEmployerNameStr.length()<5) {
                        Toast.makeText(this, "工作单位名称不得少于5个字符", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    baseRequest.put("companyName", etEmployerNameStr);
                }
                //===================退役军人
                String tvVeteransSelectStr = tvVeteransSelect.getText().toString();
                if (TextUtils.isEmpty(tvVeteransSelectStr) || tvVeteransSelectStr.equals("请选择")) {
                    Toast.makeText(this, "请选择退役军人", Toast.LENGTH_SHORT).show();
                    return;
                }
                baseRequest.put("soldier", tvVeteransSelectStr);
                //===============归侨人员
                String tvReturnedOverseasSelectStr = tvReturnedOverseasSelect.getText().toString();
                if (TextUtils.isEmpty(tvReturnedOverseasSelectStr) || tvReturnedOverseasSelectStr.equals("请选择")) {
                    Toast.makeText(this, "请选择归侨人员", Toast.LENGTH_SHORT).show();
                    return;
                }
                baseRequest.put("oversea", tvReturnedOverseasSelectStr);
                //==================是否独生子女
                if (isSingletonStatus==0) {
                    Toast.makeText(this, "请选择是否独生子女", Toast.LENGTH_SHORT).show();
                    return;
                }
                baseRequest.put("singletonStatus", isSingletonStatus==1 ? "是" : "否");
                //=====================独生子女证
                String singletonCardNo = etCertificateNum.getText().toString();
                if (isSingletonStatus==1 ) {
                    if (TextUtils.isEmpty(workStatus)||singletonCardNo.length() < 8){
                        Toast.makeText(this, "独生子女证号至少8位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    baseRequest.put("singletonCardNo", singletonCardNo);
                }
//                baseRequest.put("idPassportNo", idPassportNo);                    //=====================shenfenzheng

                gatherInfoPresenter.gatherInfo(baseRequest, integer -> {

                    ToastUtils.showShort("登记成功");
                    finish();
                });

                break;
        }
    }
    @OnClick(R.id.btn_pre_info)
    public void preStep(){
        saveCache();
        Intent intent1 = new Intent(this, ThirdStepInfoActivity.class);
        intent1.putExtra("idNum", idNum);
        startActivity(intent1);
        finish();
    }
    private void saveCache(){
        PopulationBean populationBean = LocalCache.getInfo(idNum);
        if (populationBean!=null){
            populationBean.setPoliticalLandscape(tvPoliticalOutlookSelector.getText().toString());
            populationBean.setJoinPartyDate(tvTimeJoiningSelector.getText().toString());
            populationBean.setLocationOrgRelationship(etLocation.getText().toString());
            populationBean.setEmploymentStatus(tvEmploymentRetirementSelector.getText().toString());
            populationBean.setCompanyName(etCompanyName.getText().toString());
            populationBean.setSoldier(tvVeteransSelect.getText().toString());
            populationBean.setOversea(tvReturnedOverseasSelect.getText().toString());
            populationBean.setSingletonStatus(isSingletonStatus==1 ? "是" : "否");
            populationBean.setSingletonCardNo(etCertificateNum.getText().toString());
            LocalCache.saveInfo(idNum+"",populationBean);
        }
    }
    public void userInfoAdd(Object succeed) {
        if (succeed == null) {
            return;
        }
        setResult(RESULT_OK);
//        com.blankj.utilcode.util.ToastUtils.showShort("登记成功");
//        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2029, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
//                Button btn = (Button) v;
//                btn.setText(getTime(date));
                tvTimeJoiningSelector.setText(getTime(date));
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        TextView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                btnNextInfo.setVisibility(View.VISIBLE);
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                btnNextInfo.setVisibility(View.VISIBLE);
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setDecorView(flTimer)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    //政治面貌
    private void setAddressSelectorPopup0(View v) {
        int screenHeigh = getResources().getDisplayMetrics().heightPixels;
        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_picker_selector_bottom1)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.3f))
                .setViewOnClickListener(new CommonPopWindow.ViewClickListener() {
                    @Override
                    public void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId) {
                        TextView imageBtn = view.findViewById(R.id.img_guanbi);
                        TextView cancel = view.findViewById(R.id.img_cancel);
                        PickerScrollNewView pickerScrollNewView = view.findViewById(R.id.address);
                        List<InfoBean> data = new ArrayList<>();
                        data.add(new InfoBean("共青团员"));
                        data.add(new InfoBean("民主党派"));
                        data.add(new InfoBean("中共党员"));
                        data.add(new InfoBean("群众"));
                        pickerScrollNewView.setData(data);
                        cancel.setOnClickListener(new OnDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View v) {
                                mPopupWindow.dismiss();
                            }
                        });
                        imageBtn.setOnClickListener(new OnDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View v) {
                                mPopupWindow.dismiss();
                                InfoBean selectedInfo = pickerScrollNewView.getSelectedInfo();
                                tvPoliticalOutlookSelector.setText(selectedInfo.getInfo());
                                if ("中共党员".equals(selectedInfo.getInfo())) {
                                    llLocation.setVisibility(View.VISIBLE);
                                    underlineLocation.setVisibility(View.VISIBLE);
                                    llTimeJoining.setVisibility(View.VISIBLE);
                                    underlineTimeJoining.setVisibility(View.VISIBLE);
                                } else {
                                    llLocation.setVisibility(View.GONE);
                                    underlineLocation.setVisibility(View.GONE);
                                    llTimeJoining.setVisibility(View.GONE);
                                    underlineTimeJoining.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                })
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(this)
                .showAsBottom(v);
    }

    //就业/离退休
    private void setAddressSelectorPopup1(View v) {
        int screenHeigh = getResources().getDisplayMetrics().heightPixels;
        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_picker_selector_bottom1)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.3f))
                .setViewOnClickListener(new CommonPopWindow.ViewClickListener() {
                    @Override
                    public void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId) {
                        TextView imageBtn = view.findViewById(R.id.img_guanbi);
                        TextView cancel = view.findViewById(R.id.img_cancel);
                        PickerScrollNewView pickerScrollNewView = view.findViewById(R.id.address);
                        List<InfoBean> data = new ArrayList<>();
                        data.add(new InfoBean("在职"));
                        data.add(new InfoBean("离职"));
                        data.add(new InfoBean("退休"));
                        data.add(new InfoBean("未就业"));

                        pickerScrollNewView.setData(data);
                        cancel.setOnClickListener(new OnDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View v) {
                                mPopupWindow.dismiss();
                            }
                        });
                        imageBtn.setOnClickListener(new OnDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View v) {
                                mPopupWindow.dismiss();
                                InfoBean selectedInfo = pickerScrollNewView.getSelectedInfo();
                                tvEmploymentRetirementSelector.setText(selectedInfo.getInfo());
                                if ("在职".equals(selectedInfo.getInfo())) {
                                    llCompanyName.setVisibility(View.VISIBLE);
                                    underlineEmployerName.setVisibility(View.VISIBLE);
                                } else {
                                    llCompanyName.setVisibility(View.GONE);
                                    underlineEmployerName.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                })
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(this)
                .showAsBottom(v);
    }

    //退役军人
    private void setAddressSelectorPopup2(View v) {
        int screenHeigh = getResources().getDisplayMetrics().heightPixels;
        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_picker_selector_bottom1)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.3f))
                .setViewOnClickListener(new CommonPopWindow.ViewClickListener() {
                    @Override
                    public void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId) {
                        TextView imageBtn = view.findViewById(R.id.img_guanbi);
                        TextView cancel = view.findViewById(R.id.img_cancel);
                        PickerScrollNewView pickerScrollNewView = view.findViewById(R.id.address);
                        List<InfoBean> data = new ArrayList<>();
                        data.add(new InfoBean("是"));
                        data.add(new InfoBean("否"));
                        pickerScrollNewView.setData(data);
                        cancel.setOnClickListener(new OnDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View v) {
                                mPopupWindow.dismiss();
                            }
                        });
                        imageBtn.setOnClickListener(new OnDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View v) {
                                mPopupWindow.dismiss();
                                InfoBean selectedInfo = pickerScrollNewView.getSelectedInfo();
                                tvVeteransSelect.setText(selectedInfo.getInfo());
                            }
                        });
                    }
                })
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(this)
                .showAsBottom(v);
    }

    //归侨人员
    private void setAddressSelectorPopup3(View v) {
        int screenHeigh = getResources().getDisplayMetrics().heightPixels;
        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_picker_selector_bottom1)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.3f))
                .setViewOnClickListener(new CommonPopWindow.ViewClickListener() {
                    @Override
                    public void getChildView(PopupWindow mPopupWindow, View view, int mLayoutResId) {
                        TextView imageBtn = view.findViewById(R.id.img_guanbi);
                        TextView cancel = view.findViewById(R.id.img_cancel);
                        PickerScrollNewView pickerScrollNewView = view.findViewById(R.id.address);
                        List<InfoBean> data = new ArrayList<>();
                        data.add(new InfoBean("是"));
                        data.add(new InfoBean("否"));
                        pickerScrollNewView.setData(data);
                        cancel.setOnClickListener(new OnDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View v) {
                                mPopupWindow.dismiss();
                            }
                        });
                        imageBtn.setOnClickListener(new OnDoubleClickListener() {
                            @Override
                            public void onNoDoubleClick(View v) {
                                mPopupWindow.dismiss();
                                InfoBean selectedInfo = pickerScrollNewView.getSelectedInfo();
                                tvReturnedOverseasSelect.setText(selectedInfo.getInfo());
                            }
                        });
                    }
                })
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(this)
                .showAsBottom(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
