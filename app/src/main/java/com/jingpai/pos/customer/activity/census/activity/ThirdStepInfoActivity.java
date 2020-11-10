package com.jingpai.pos.customer.activity.census.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.GatherInfoPresenterOld;
import com.jingpai.pos.customer.activity.census.bean.ChildBean;
import com.jingpai.pos.customer.activity.census.bean.ChildrenBean;
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
import butterknife.OnClick;

/**
 * @author 86173
 */
public class ThirdStepInfoActivity extends BaseActivity {
    List<ChildBean> childBeanList = new ArrayList<>();
    @BindView(R.id.tv_marital_status_select)
    TextView tvMaritalStatusSelect;
    @BindView(R.id.ll_marital_status)
    LinearLayout llMaritalStatus;
    @BindView(R.id.underline_name)
    View underlineName;
    @BindView(R.id.tv_marriage_change_select)
    TextView tvMarriageChangeSelect;
    @BindView(R.id.ll_marriage_change)
    LinearLayout llMarriageChange;
    @BindView(R.id.underline_marriage_change)
    View underlineMarriageChange;
    @BindView(R.id.tv_pregnancy_status_select)
    TextView tvPregnancyStatusSelect;
    @BindView(R.id.ll_pregnancy_status)
    LinearLayout llPregnancyStatus;
    @BindView(R.id.underline_pregnancy_status)
    View underlinePregnancyStatus;
    @BindView(R.id.tv_last_menstruation_selctor)
    TextView tvLastMenstruationSelctor;
    @BindView(R.id.ll_last_menstruation)
    LinearLayout llLastMenstruation;
    @BindView(R.id.underline_last_menstruation)
    View underlineLastMenstruation;
    @BindView(R.id.tv_birth_control_status_select)
    TextView tvBirthControlStatusSelect;
    @BindView(R.id.ll_birth_control_status)
    LinearLayout llBirthControlStatus;
    @BindView(R.id.underline_birth_control_status)
    View underlineBirthControlStatus;
    @BindView(R.id.tv_contraceptive_methods_select)
    TextView tvContraceptiveMethodsSelect;
    @BindView(R.id.ll_contraceptive_methods)
    LinearLayout llContraceptiveMethods;
    @BindView(R.id.underline_contraceptive_methods)
    View underlineContraceptiveMethods;
    @BindView(R.id.tv_fertility_status_selector)
    TextView tvFertilityStatusSelector;
    @BindView(R.id.ll_fertility_status)
    LinearLayout llFertilityStatus;
    @BindView(R.id.underline_fertility_status)
    View underlineFertilityStatus;
    @BindView(R.id.tv_man_1_child)
    TextView tvMan1Child;
    @BindView(R.id.tv_women_1_child)
    TextView tvWomen1Child;
    @BindView(R.id.rl_1_child)
    RelativeLayout rl1Child;
    @BindView(R.id.underline_1_child)
    View underline1Child;
    @BindView(R.id.tv_1_birth)
    TextView tv1Birth;
    @BindView(R.id.rl_1_birth)
    RelativeLayout rl1Birth;
    @BindView(R.id.underline_1_birth)
    View underline1Birth;
    @BindView(R.id.tv_2delete)
    TextView tv2delete;
    @BindView(R.id.underline_2_delete)
    View underline2Delete;
    @BindView(R.id.tv_man_2_child)
    TextView tvMan2Child;
    @BindView(R.id.tv_women_2_child)
    TextView tvWomen2Child;
    @BindView(R.id.rl_2_child)
    RelativeLayout rl2Child;
    @BindView(R.id.underline_2_child)
    View underline2Child;
    @BindView(R.id.tv_2_birth)
    TextView tv2Birth;
    @BindView(R.id.rl_2_birth)
    RelativeLayout rl2Birth;
    @BindView(R.id.underline_2_birth)
    View underline2Birth;
    @BindView(R.id.tv_3delete)
    TextView tv3delete;
    @BindView(R.id.underline_3_delete)
    View underline3Delete;
    @BindView(R.id.tv_man_3_child)
    TextView tvMan3Child;
    @BindView(R.id.tv_women_3_child)
    TextView tvWomen3Child;
    @BindView(R.id.rl_3_child)
    RelativeLayout rl3Child;
    @BindView(R.id.underline_3_child)
    View underline3Child;
    @BindView(R.id.tv_3_birth)
    TextView tv3Birth;
    @BindView(R.id.rl_3_birth)
    RelativeLayout rl3Birth;
    @BindView(R.id.underline_3_birth)
    View underline3Birth;
    @BindView(R.id.tv_4delete)
    TextView tv4delete;
    @BindView(R.id.underline_4_delete)
    View underline4Delete;
    @BindView(R.id.tv_man_4_child)
    TextView tvMan4Child;
    @BindView(R.id.tv_women_4_child)
    TextView tvWomen4Child;
    @BindView(R.id.rl_4_child)
    RelativeLayout rl4Child;
    @BindView(R.id.underline_4_child)
    View underline4Child;
    @BindView(R.id.tv_4_birth)
    TextView tv4Birth;
    @BindView(R.id.rl_4_birth)
    RelativeLayout rl4Birth;
    @BindView(R.id.underline_4_birth)
    View underline4Birth;
    @BindView(R.id.tv_5delete)
    TextView tv5delete;
    @BindView(R.id.underline_5_delete)
    View underline5Delete;
    @BindView(R.id.tv_man_5_child)
    TextView tvMan5Child;
    @BindView(R.id.tv_women_5_child)
    TextView tvWomen5Child;
    @BindView(R.id.rl_5_child)
    RelativeLayout rl5Child;
    @BindView(R.id.underline_5_child)
    View underline5Child;
    @BindView(R.id.tv_5_birth)
    TextView tv5Birth;
    @BindView(R.id.rl_5_birth)
    RelativeLayout rl5Birth;
    @BindView(R.id.underline_5_birth)
    View underline5Birth;
    @BindView(R.id.tv_add_child)
    TextView tvAddChild;
    @BindView(R.id.ll_children)
    LinearLayout llChildren;
    @BindView(R.id.btn_next_info)
    Button btnNextInfo;
    @BindView(R.id.fl_timer)
    FrameLayout flTimer;

    private TimePickerView pvTime;
    boolean hasChild;
    int hasChildNum;
    boolean isMan1;
    boolean isMan2;
    boolean isMan3;
    boolean isMan4;
    boolean isMan5;
    private GatherInfoPresenterOld gatherInfoPresenter;
    private PopulationBean populationBean;
    private List<ChildrenBean> childrenBean;
    private String idPassportNo;
    private int id;
    private String idNum;

    @Override
    protected int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_third_step;
    }

    @Override
    protected void initData() {
        initView();
    }

    private void initView() {
        hasChild = false;
        hasChildNum = 0;
        isMan1 = false;
        isMan2 = false;
        isMan3 = false;
        isMan4 = false;
        isMan5 = false;
        gatherInfoPresenter = new GatherInfoPresenterOld();
        getLocalInfo();
    }

    //获取数据
    private void getLocalInfo() {
        idNum = getIntent().getStringExtra("idNum");
//        idPassportNo = getIntent().getStringExtra("idPassportNo");
        if (!TextUtils.isEmpty(idNum)) {
            populationBean = (PopulationBean) LocalCache.getInfo(idNum);
            if (populationBean != null) {
                if (populationBean.getChildren() != null && populationBean.getChildren().size() > 0) {
//                    for (int i=0;i<populationBean.getChildren().size();i++){
//                        childrenBean.add(populationBean.getChildren().get(i));
//                    }
                }
                if (!TextUtils.isEmpty(populationBean.getMaritalStatus())) { //婚姻状态
                    tvMaritalStatusSelect.setText(populationBean.getMaritalStatus());
                    if ("未婚".equals(populationBean.getMaritalStatus())) {
                        llMarriageChange.setVisibility(View.GONE);
                        underlineMarriageChange.setVisibility(View.GONE);
                    }
                }
                if (!TextUtils.isEmpty(populationBean.getMaritalDate())) {//婚姻变动日期
                    tvMarriageChangeSelect.setText(populationBean.getMaritalDate());
                }
                if (!TextUtils.isEmpty(populationBean.getPregnancyStatus())) {
                    tvPregnancyStatusSelect.setText(populationBean.getPregnancyStatus());
                }
                if (!TextUtils.isEmpty(populationBean.getLastMenstrualDate())) {
                    tvLastMenstruationSelctor.setText(populationBean.getLastMenstrualDate());
                }

                if (!TextUtils.isEmpty(populationBean.getIsOligogenics())) {
                    tvBirthControlStatusSelect.setText(populationBean.getIsOligogenics());
                }
                if (!TextUtils.isEmpty(populationBean.getIsOligogenics())) {
                    tvContraceptiveMethodsSelect.setText(populationBean.getOligogenicsStatus());
                    if ("未节育".equals(populationBean.getIsOligogenics())) {
                        llContraceptiveMethods.setVisibility(View.GONE);
                        underlineContraceptiveMethods.setVisibility(View.GONE);
                    }
                }
                if (populationBean.getId() != 0) {
                    id = populationBean.getId();
                }
                if ("男".equals(populationBean.getGender())) {
                    llPregnancyStatus.setVisibility(View.GONE);
                    underlinePregnancyStatus.setVisibility(View.GONE);
                    llLastMenstruation.setVisibility(View.GONE);
                    underlineLastMenstruation.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(populationBean.getIsFertility()) && populationBean.getIsFertility().equals("已生育")) {
                    tvFertilityStatusSelector.setText(populationBean.getIsFertility());
                    //1
                    if (populationBean.getChildren().size() > 0) {
                        if (populationBean.getChildren().get(0) != null && !TextUtils.isEmpty(populationBean.getChildren().get(0).getGender())) {
                            hasChild = true;
                            llChildren.setVisibility(View.VISIBLE);
                            tvAddChild.setVisibility(View.VISIBLE);
                            rl1Child.setVisibility(View.VISIBLE);
                            underline1Birth.setVisibility(View.VISIBLE);
                            hasChildNum = 1;
                            String gender = populationBean.getChildren().get(0).getGender();
                            if (gender.equals("男")) {
                                tvMan1Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvMan1Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvWomen1Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvWomen1Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            } else {
                                tvWomen1Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvWomen1Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvMan1Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvMan1Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            }
                            rl1Birth.setVisibility(View.VISIBLE);
                        }
                        if (populationBean.getChildren().get(0) != null && !TextUtils.isEmpty(populationBean.getChildren().get(0).getBirthDate())) {
                            tv1Birth.setText(populationBean.getChildren().get(0).getBirthDate());
                        }
                    }


                    if (populationBean.getChildren().size() == 2) {
                        rl2Child.setVisibility(View.VISIBLE);
                        if (populationBean.getChildren().get(1) != null && !TextUtils.isEmpty(populationBean.getChildren().get(1).getGender())) {
                            String gender = populationBean.getChildren().get(1).getGender();
                            if (gender.equals("男")) {
                                tvMan2Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvMan2Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvWomen2Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvWomen2Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            } else {
                                tvWomen2Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvWomen2Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvMan2Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvMan2Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            }
                            rl2Birth.setVisibility(View.VISIBLE);
                        }
                        if (populationBean.getChildren().get(1) != null && !TextUtils.isEmpty(populationBean.getChildren().get(1).getBirthDate())) {
                            tv2Birth.setText(populationBean.getChildren().get(1).getBirthDate());
                        }
                    }
                    if (populationBean.getChildren().size() == 3) {
                        rl3Child.setVisibility(View.VISIBLE);
                        if (populationBean.getChildren().get(2) != null && !TextUtils.isEmpty(populationBean.getChildren().get(2).getGender())) {
                            String gender = populationBean.getChildren().get(2).getGender();
                            if (gender.equals("男")) {
                                tvMan3Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvMan3Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvWomen3Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvWomen3Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            } else {
                                tvWomen3Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvWomen3Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvMan3Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvMan3Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            }
                            rl3Birth.setVisibility(View.VISIBLE);
                        }
                        if (populationBean.getChildren().get(2) != null && !TextUtils.isEmpty(populationBean.getChildren().get(2).getBirthDate())) {
                            tv3Birth.setText(populationBean.getChildren().get(2).getBirthDate());
                        }
                    }

                    if (populationBean.getChildren().size() == 4) {
                        rl4Child.setVisibility(View.VISIBLE);
                        if (populationBean.getChildren().get(3) != null && !TextUtils.isEmpty(populationBean.getChildren().get(3).getGender())) {
                            String gender = populationBean.getChildren().get(3).getGender();
                            if (gender.equals("男")) {
                                tvMan4Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvMan4Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvWomen4Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvWomen4Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            } else {
                                tvWomen4Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvWomen4Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvMan4Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvMan4Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            }
                            rl4Birth.setVisibility(View.VISIBLE);
                        }
                        if (populationBean.getChildren().get(3) != null && !TextUtils.isEmpty(populationBean.getChildren().get(3).getBirthDate())) {
                            tv4Birth.setText(populationBean.getChildren().get(3).getBirthDate());
                        }
                    }

                    if (populationBean.getChildren().size() == 5) {
                        rl5Child.setVisibility(View.VISIBLE);
                        if (populationBean.getChildren().get(4) != null && !TextUtils.isEmpty(populationBean.getChildren().get(4).getGender())) {
                            String gender = populationBean.getChildren().get(4).getGender();
                            if (gender.equals("男")) {
                                tvMan5Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvMan5Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvWomen5Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvWomen5Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            } else {
                                tvWomen5Child.setTextColor(getResources().getColor(R.color.bg_census));
                                tvWomen5Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                                tvMan5Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                                tvMan5Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                            }
                            rl5Birth.setVisibility(View.VISIBLE);
                        }
                        if (populationBean.getChildren().get(4) != null && !TextUtils.isEmpty(populationBean.getChildren().get(4).getBirthDate())) {
                            tv5Birth.setText(populationBean.getChildren().get(4).getBirthDate());
                        }
                    }
                } else if (!TextUtils.isEmpty(populationBean.getIsFertility()) && populationBean.getIsFertility().equals("未生育")) {
                    tvFertilityStatusSelector.setText(populationBean.getIsFertility());
                }
            }
        }
    }

    @OnClick({R.id.tv_2delete,R.id.tv_3delete,R.id.tv_4delete,R.id.tv_5delete})
    public void deleteChild(){
        if (hasChildNum == 5) {
            rl5Child.setVisibility(View.GONE);
            rl5Birth.setVisibility(View.GONE);
            tv5delete.setVisibility(View.GONE);
            underline5Delete.setVisibility(View.GONE);
            hasChildNum--;
        } else if (hasChildNum == 4) {
            rl4Child.setVisibility(View.GONE);
            rl4Birth.setVisibility(View.GONE);
            tv4delete.setVisibility(View.GONE);
            underline4Delete.setVisibility(View.GONE);
            hasChildNum--;
        } else if (hasChildNum == 3) {
            rl3Child.setVisibility(View.GONE);
            rl3Birth.setVisibility(View.GONE);
            tv3delete.setVisibility(View.GONE);
            underline3Delete.setVisibility(View.GONE);
            hasChildNum--;
        } else if (hasChildNum == 2) {
            rl2Child.setVisibility(View.GONE);
            rl2Birth.setVisibility(View.GONE);
            tv2delete.setVisibility(View.GONE);
            underline2Delete.setVisibility(View.GONE);
            hasChildNum--;
        }
    }

    @OnClick(R.id.tv_add_child)
    public void addChild(){
        if (hasChild && hasChildNum == 1) {
            rl2Child.setVisibility(View.VISIBLE);
            tv2delete.setVisibility(View.VISIBLE);
            underline2Delete.setVisibility(View.VISIBLE);
            underline2Child.setVisibility(View.VISIBLE);
            underline2Birth.setVisibility(View.VISIBLE);
            hasChildNum++;
        } else if (hasChild && hasChildNum == 2) {
            rl3Child.setVisibility(View.VISIBLE);
            tv3delete.setVisibility(View.VISIBLE);
            underline3Delete.setVisibility(View.VISIBLE);
            underline3Child.setVisibility(View.VISIBLE);
            underline3Birth.setVisibility(View.VISIBLE);
            hasChildNum++;
        } else if (hasChild && hasChildNum == 3) {
            rl4Child.setVisibility(View.VISIBLE);
            tv4delete.setVisibility(View.VISIBLE);
            underline4Delete.setVisibility(View.VISIBLE);
            underline4Child.setVisibility(View.VISIBLE);
            underline4Birth.setVisibility(View.VISIBLE);
            hasChildNum++;
        } else if (hasChild && hasChildNum == 4) {
            rl5Child.setVisibility(View.VISIBLE);
            tv5delete.setVisibility(View.VISIBLE);
            underline5Delete.setVisibility(View.VISIBLE);
            underline5Child.setVisibility(View.VISIBLE);
            underline5Birth.setVisibility(View.VISIBLE);
            tvAddChild.setVisibility(View.GONE);
            hasChildNum++;
        }
    }
    @OnClick({R.id.iv_back,R.id.tv_marital_status_select,R.id.tv_marriage_change_select,R.id.tv_pregnancy_status_select
            ,R.id.tv_last_menstruation_selctor,R.id.tv_birth_control_status_select,R.id.tv_contraceptive_methods_select
            ,R.id.tv_fertility_status_selector,R.id.tv_man_1_child,R.id.tv_women_1_child,R.id.tv_man_2_child
            ,R.id.tv_women_2_child,R.id.tv_man_3_child,R.id.tv_women_3_child,R.id.tv_man_4_child,R.id.tv_women_4_child
            ,R.id.tv_man_5_child,R.id.tv_women_5_child,R.id.btn_next_info,R.id.tv_1_birth,R.id.tv_2_birth,R.id.tv_3_birth
    ,R.id.tv_4_birth,R.id.tv_5_birth})
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //点击选择婚姻状况
            case R.id.tv_marital_status_select:
                setAddressSelectorPopup0(v);
                break;
            //点击选择婚姻变动日期
            case R.id.tv_marriage_change_select:
                flTimer.setVisibility(View.VISIBLE);
                btnNextInfo.setVisibility(View.GONE);
                initTimePicker();
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(1990, 0, 1);
                pvTime.setDate(selectedDate);
                pvTime.show(v, false);
                break;

            //点击选择怀孕状况
            case R.id.tv_pregnancy_status_select:
                setAddressSelectorPopup1(v);
                break;

            //点击选择末次月经情况
            case R.id.tv_last_menstruation_selctor:
                flTimer.setVisibility(View.VISIBLE);
                btnNextInfo.setVisibility(View.GONE);
                initTimePicker2();
                pvTime.show(v, false);
                break;

            //点击选择节育情况
            case R.id.tv_birth_control_status_select:
                setAddressSelectorPopup2(v);
                break;

            //点击选择节育方式
            case R.id.tv_contraceptive_methods_select:
                setAddressSelectorPopup3(v);
                break;

            //点击选择现生育状况
            case R.id.tv_fertility_status_selector:
                setAddressSelectorPopup4(v);
                break;
            //点击选择现1男孩
            case R.id.tv_man_1_child:
                tvMan1Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvMan1Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvWomen1Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvWomen1Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl1Birth.setVisibility(View.VISIBLE);
                underline1Birth.setVisibility(View.VISIBLE);
                isMan1 = true;
                break;
            //点击选择现1女孩
            case R.id.tv_women_1_child:
                tvWomen1Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvWomen1Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvMan1Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvMan1Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl1Birth.setVisibility(View.VISIBLE);
                underline1Birth.setVisibility(View.VISIBLE);
                isMan1 = false;
                break;

            //点击选择现2男孩
            case R.id.tv_man_2_child:
                tvMan2Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvMan2Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvWomen2Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvWomen2Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl2Birth.setVisibility(View.VISIBLE);
                underline2Birth.setVisibility(View.VISIBLE);
                isMan2 = true;
                break;
            //点击选择现2女孩
            case R.id.tv_women_2_child:
                tvWomen2Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvWomen2Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvMan2Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvMan2Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl2Birth.setVisibility(View.VISIBLE);
                underline2Birth.setVisibility(View.VISIBLE);
                isMan2 = false;
                break;

            //点击选择现3男孩
            case R.id.tv_man_3_child:
                tvMan3Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvMan3Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvWomen3Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvWomen3Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl3Birth.setVisibility(View.VISIBLE);
                underline3Birth.setVisibility(View.VISIBLE);
                isMan3 = true;
                break;
            //点击选择现3女孩
            case R.id.tv_women_3_child:
                tvWomen3Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvWomen3Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvMan3Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvMan3Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl3Birth.setVisibility(View.VISIBLE);
                underline3Birth.setVisibility(View.VISIBLE);
                isMan3 = false;
                break;
            //点击选择现4男孩
            case R.id.tv_man_4_child:
                tvMan4Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvMan4Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvWomen4Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvWomen4Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl4Birth.setVisibility(View.VISIBLE);
                underline4Birth.setVisibility(View.VISIBLE);
                isMan4 = true;
                break;
            //点击选择现4女孩
            case R.id.tv_women_4_child:
                tvWomen4Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvWomen4Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvMan4Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvMan4Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl4Birth.setVisibility(View.VISIBLE);
                underline4Birth.setVisibility(View.VISIBLE);
                isMan4 = false;
                break;

            //点击选择现5男孩
            case R.id.tv_man_5_child:
                tvMan5Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvMan5Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvWomen5Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvWomen5Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl5Birth.setVisibility(View.VISIBLE);
                underline5Birth.setVisibility(View.VISIBLE);
                isMan5 = true;
                break;
            //点击选择现5女孩
            case R.id.tv_women_5_child:
                tvWomen5Child.setTextColor(getResources().getColor(R.color.bg_census));
                tvWomen5Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvMan5Child.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvMan5Child.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                rl5Birth.setVisibility(View.VISIBLE);
                underline5Birth.setVisibility(View.VISIBLE);
                isMan5 = false;
                break;
            //点击选择现1生日
            case R.id.tv_1_birth:
                flTimer.setVisibility(View.VISIBLE);
                btnNextInfo.setVisibility(View.GONE);
                initTimePicker1(1);
                pvTime.show(v, true);
                break;
            //点击选择现2生日
            case R.id.tv_2_birth:
                flTimer.setVisibility(View.VISIBLE);
                btnNextInfo.setVisibility(View.GONE);
                initTimePicker1(2);
                pvTime.show(v, true);
                break;
            //点击选择现3生日
            case R.id.tv_3_birth:
                flTimer.setVisibility(View.VISIBLE);
                btnNextInfo.setVisibility(View.GONE);
                initTimePicker1(3);
                pvTime.show(v, true);
                break;
            //点击选择现4生日
            case R.id.tv_4_birth:
                flTimer.setVisibility(View.VISIBLE);
                btnNextInfo.setVisibility(View.GONE);
                initTimePicker1(4);
                pvTime.show(v, true);
                break;
            //点击选择现5生日
            case R.id.tv_5_birth:
                flTimer.setVisibility(View.VISIBLE);
                btnNextInfo.setVisibility(View.GONE);
                initTimePicker1(5);
                pvTime.show(v, true);
                break;

            //点击下一步
            case R.id.btn_next_info:
                //=====================================婚姻状况
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("id", idNum);                                       //id
                String tvMaritalStatusSelectStr = tvMaritalStatusSelect.getText().toString();
                if (TextUtils.isEmpty(tvMaritalStatusSelectStr) || tvMaritalStatusSelectStr.equals("请选择")) {
                    Toast.makeText(this, "请选择婚姻状况", Toast.LENGTH_SHORT).show();
                    return;
                }
                baseRequest.put("maritalStatus", tvMaritalStatusSelectStr);
                //=====================================婚姻变动日期
                String tvMarriageChangeSelectStr = tvMarriageChangeSelect.getText().toString();
                if (!tvMaritalStatusSelectStr.equals("未婚")){
                    if ("请选择".equals(tvMarriageChangeSelectStr)){
                        Toast.makeText(this, "请选择婚姻变动日期", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    baseRequest.put("maritalDate", tvMarriageChangeSelectStr);
                }
                //=====================================怀孕情况/末次月经日期
                String tvPregnancyStatusSelectStr = tvPregnancyStatusSelect.getText().toString();
                String tvLastMenstruationSelctorStr = tvLastMenstruationSelctor.getText().toString();
                if ("女".equals(populationBean.getGender())){
                    if ("请选择".equals(tvPregnancyStatusSelectStr)){
                        Toast.makeText(this, "请选择怀孕状况", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ("请选择".equals(tvLastMenstruationSelctorStr)){
                        Toast.makeText(this, "请选择末次月经日期", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    baseRequest.put("pregnancyStatus",  tvPregnancyStatusSelectStr);
                    baseRequest.put("lastMenstrualDate",  tvLastMenstruationSelctorStr);
                }
                //=====================================节育状况
                String tvBirthControlStatusSelectStr = tvBirthControlStatusSelect.getText().toString();
                if ("请选择".equals(tvBirthControlStatusSelectStr)){
                    Toast.makeText(this, "请选择节育状况", Toast.LENGTH_SHORT).show();
                    return;
                }
                baseRequest.put("isOligogenics",  tvBirthControlStatusSelectStr);

                //=====================================节育方式
                String tvContraceptiveMethodsSelectStr = tvContraceptiveMethodsSelect.getText().toString();
                if ("已节育".equals(tvBirthControlStatusSelectStr)) {
                    if ("请选择".equals(tvContraceptiveMethodsSelectStr)){
                        Toast.makeText(this, "请选择节育方式", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    baseRequest.put("oligogenicsStatus", tvContraceptiveMethodsSelectStr);
                }
                //=====================================生育状况
                String tvFertilityStatusSelectorStr = tvFertilityStatusSelector.getText().toString();
                if ("请选择".equals(tvFertilityStatusSelectorStr)) {
                    Toast.makeText(this, "请选择生育状况", Toast.LENGTH_SHORT).show();
                    return;
                }
                baseRequest.put("isFertility",  tvFertilityStatusSelectorStr);//生育情况

                //=====================================已生育
                if (tvFertilityStatusSelectorStr.equals("已生育")) {
                    String tv1BirthStr = tv1Birth.getText().toString();
                    if (tv1BirthStr.equals("选择出生日期（必选）")) {
                        Toast.makeText(this, "请选择一孩生日", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    childBeanList.add(new ChildBean(tv1BirthStr, isMan1 ? "男" : "女"));

                    if (rl2Child.getVisibility() == View.VISIBLE) {
                        String tv2BirthStr = tv2Birth.getText().toString();
                        if (tv2BirthStr.equals("选择出生日期（必选）")) {
                            Toast.makeText(this, "请选择二孩生日", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        childBeanList.add(new ChildBean(tv2BirthStr, isMan2 ? "男" : "女"));
                    }

                    if (rl3Child.getVisibility() == View.VISIBLE) {
                        String tv3BirthStr = tv3Birth.getText().toString();
                        if (tv3BirthStr.equals("选择出生日期（必选）")) {
                            Toast.makeText(this, "请选择三孩生日", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        childBeanList.add(new ChildBean(tv3BirthStr, isMan3 ? "男" : "女"));
                    }

                    if (rl4Child.getVisibility() == View.VISIBLE) {
                        String tv4BirthStr = tv4Birth.getText().toString();
                        if (tv4BirthStr.equals("选择出生日期（必选）")) {
                            Toast.makeText(this, "请选择四孩生日", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        childBeanList.add(new ChildBean(tv4BirthStr, isMan4 ? "男" : "女"));
                    }

                    if (rl5Child.getVisibility() == View.VISIBLE) {
                        String tv5BirthStr = tv5Birth.getText().toString();
                        if (tv5BirthStr.equals("选择出生日期（必选）")) {
                            Toast.makeText(this, "请选择五孩生日", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        childBeanList.add(new ChildBean(tv5BirthStr, isMan5 ? "男" : "女"));
                    }
                    baseRequest.put("children", childBeanList.size() == 0 ? null : childBeanList);
                }

                gatherInfoPresenter.gatherInfo(baseRequest, integer -> {
                    saveCache();
                    Intent intent1 = new Intent(this, LastStepInfoActivity.class);
                    intent1.putExtra("idNum", idNum);
                    startActivity(intent1);
                    finish();
                });
                break;
        }
    }
    @OnClick(R.id.btn_pre_info)
    public void preStep(){
        saveCache();
        Intent intent1 = new Intent(this, SecondStepInfoActivity.class);
        intent1.putExtra("idNum", idNum);
        startActivity(intent1);
        finish();
    }
    private void saveCache(){
        PopulationBean populationBean = LocalCache.getInfo(idNum);
        if (populationBean!=null){
            populationBean.setMaritalStatus(tvMaritalStatusSelect.getText().toString());
            populationBean.setMaritalDate(tvMarriageChangeSelect.getText().toString());
            populationBean.setPregnancyStatus(tvPregnancyStatusSelect.getText().toString());
            populationBean.setLastMenstrualDate(tvLastMenstruationSelctor.getText().toString());
            populationBean.setIsOligogenics(tvBirthControlStatusSelect.getText().toString());
            populationBean.setOligogenicsStatus(tvContraceptiveMethodsSelect.getText().toString());
            populationBean.setIsFertility(tvFertilityStatusSelector.getText().toString());
            if (hasChildNum>0){
                List<ChildrenBean> childBeanListTemp = new ArrayList<>();
                if (hasChildNum>=1){
                    ChildrenBean childrenBean1 = new ChildrenBean();
                    childrenBean1.setBirthDate(tv1Birth.getText().toString());
                    childrenBean1.setGender(isMan1 ? "男" : "女");
                    childBeanListTemp.add(childrenBean1);
                }
                if (hasChildNum>=2){
                    ChildrenBean childrenBean2 = new ChildrenBean();
                    childrenBean2.setBirthDate(tv2Birth.getText().toString());
                    childrenBean2.setGender(isMan2 ? "男" : "女");
                    childBeanListTemp.add(childrenBean2);
                }
                if (hasChildNum>=3){
                    ChildrenBean childrenBean3 = new ChildrenBean();
                    childrenBean3.setBirthDate(tv3Birth.getText().toString());
                    childrenBean3.setGender(isMan3 ? "男" : "女");
                    childBeanListTemp.add(childrenBean3);
                }
                if (hasChildNum>=4){
                    ChildrenBean childrenBean4 = new ChildrenBean();
                    childrenBean4.setBirthDate(tv4Birth.getText().toString());
                    childrenBean4.setGender(isMan4 ? "男" : "女");
                    childBeanListTemp.add(childrenBean4);
                }
                if (hasChildNum>=5){
                    ChildrenBean childrenBean5 = new ChildrenBean();
                    childrenBean5.setBirthDate(tv5Birth.getText().toString());
                    childrenBean5.setGender(isMan5 ? "男" : "女");
                    childBeanListTemp.add(childrenBean5);
                }
                populationBean.setChildren(childBeanListTemp);
            }

            LocalCache.saveInfo(idNum+"",populationBean);
        }
    }
    public void userInfoAdd(Object succeed) {
        if (succeed == null) {
            return;
        }
        setResult(RESULT_OK);
        startActivity(new Intent(ThirdStepInfoActivity.this, LastStepInfoActivity.class));
        finish();
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2029, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                tvMarriageChangeSelect.setText(getTime(date));
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

    private void initTimePicker1(int i) {
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
                if (i == 1) {
                    tv1Birth.setText(getTime(date));
                } else if (i == 2) {
                    tv2Birth.setText(getTime(date));
                } else if (i == 3) {
                    tv3Birth.setText(getTime(date));
                } else if (i == 4) {
                    tv4Birth.setText(getTime(date));
                } else if (i == 5) {
                    tv5Birth.setText(getTime(date));
                }

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

    private void initTimePicker2() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 0, 23);

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
                tvLastMenstruationSelctor.setText(getTime(date));
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

    //婚姻状况
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
                        data.add(new InfoBean("未婚"));
                        data.add(new InfoBean("初婚"));
                        data.add(new InfoBean("再婚"));
                        data.add(new InfoBean("离婚"));
                        data.add(new InfoBean("丧偶"));
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
                                tvMaritalStatusSelect.setText(selectedInfo.getInfo());
                                if ("未婚".equals(selectedInfo.getInfo())) {
                                    llMarriageChange.setVisibility(View.GONE);
                                    underlineMarriageChange.setVisibility(View.GONE);
                                } else {
                                    llMarriageChange.setVisibility(View.VISIBLE);
                                    underlineMarriageChange.setVisibility(View.VISIBLE);
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

    //怀孕状况
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
                        data.add(new InfoBean("未孕"));
                        data.add(new InfoBean("现孕"));
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
                                tvPregnancyStatusSelect.setText(selectedInfo.getInfo());
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

    //节育状况
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
                        data.add(new InfoBean("已节育"));
                        data.add(new InfoBean("未节育"));
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
                                tvBirthControlStatusSelect.setText(selectedInfo.getInfo());
                                if ("未节育".equals(selectedInfo.getInfo())) {
                                    llContraceptiveMethods.setVisibility(View.GONE);
                                    underlineContraceptiveMethods.setVisibility(View.GONE);
                                } else {
                                    llContraceptiveMethods.setVisibility(View.VISIBLE);
                                    underlineContraceptiveMethods.setVisibility(View.VISIBLE);
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

    //节育方式
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
                        data.add(new InfoBean("上环"));
                        data.add(new InfoBean("结扎"));
                        data.add(new InfoBean("避孕套"));
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
                                tvContraceptiveMethodsSelect.setText(selectedInfo.getInfo());
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

    //生育
    private void setAddressSelectorPopup4(View v) {
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
                        data.add(new InfoBean("已生育"));
                        data.add(new InfoBean("未生育"));
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
                                tvFertilityStatusSelector.setText(selectedInfo.getInfo());
                                if (tvFertilityStatusSelector.getText().toString().equals("已生育")) {
                                    hasChild = true;
                                    llChildren.setVisibility(View.VISIBLE);
                                    tvAddChild.setVisibility(View.VISIBLE);
                                    rl1Child.setVisibility(View.VISIBLE);
                                    underline1Birth.setVisibility(View.VISIBLE);
                                    hasChildNum = 1;
                                } else {
                                    llChildren.setVisibility(View.GONE);
                                    hasChild = false;
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

}
