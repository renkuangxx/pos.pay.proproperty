package com.jingpai.pos.customer.activity.census.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.GatherInfoPresenterOld;
import com.jingpai.pos.customer.activity.census.bean.PopulationBean;
import com.jingpai.pos.customer.activity.census.siderbar.SideBarActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.bean.User;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.customer.bean.show.InfoBean;
import com.jingpai.pos.customer.mvp.presenter.show.my.FileBatchPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.my.PersonalPresenterImp;
import com.jingpai.pos.customer.utils.CommonPopWindow;
import com.jingpai.pos.customer.utils.ImgUtils;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.MyGson;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.PickerScrollNewView;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.utils.ValidatorUtil;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author 86173
 */
public class OwnerInfoActivity extends BaseActivity {
    @BindView(R.id.member_et_name)
    EditText memberEtName;
    @BindView(R.id.tv_relationship_selector)
    TextView tvRelationshipSelector;
    @BindView(R.id.ll_relationship)
    LinearLayout llRelationship;
    @BindView(R.id.underline_relationship)
    View underlineRelationship;
    @BindView(R.id.tv_tel_selector)
    EditText tvTelSelector;
    @BindView(R.id.ll_tel)
    LinearLayout llTel;
    @BindView(R.id.underline_tel)
    View underlineTel;
    @BindView(R.id.tv_man)
    TextView tvMan;
    @BindView(R.id.tv_women)
    TextView tvWomen;
    @BindView(R.id.tv_edutation_select)
    TextView tvEdutationSelect;
    @BindView(R.id.tv_nationality_select)
    TextView tvNationalitySelect;
    @BindView(R.id.tv_nation_select)
    TextView tvNationSelect;
    @BindView(R.id.ll_nation)
    LinearLayout llNation;
    @BindView(R.id.underline_tv_nation)
    View underlineTvNation;
    @BindView(R.id.tv_id_type)
    EditText tvIdType;
    @BindView(R.id.underline_id_type)
    View underlineIdType;
    @BindView(R.id.iv_above)
    ImageView ivAbove;
    @BindView(R.id.iv_below)
    ImageView ivBelow;
    FileBatchPresenter fileBatchPresenter;
    private Dialog dialog;
    private String position;
    private String base;
    private String base1;
    private String guojia;
    private String minZu;
    private String minZu1;
    private String wenHua;
    private String guanXi;
    private GatherInfoPresenterOld gatherInfoPresenter;
    private PopulationBean populationBean;
    String name;
    String zhengJian;
    String phone;
    String sex;
    String filePath;
    String filePath2;
    String idNum;
    private File file;
    private PersonalPresenterImp personalPresenterImp;

    @Override
    protected int getLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.activity_other_info;
    }

    @Override
    protected void initData() {
        initView();
        gatherInfoPresenter = new GatherInfoPresenterOld();
        fileBatchPresenter = new FileBatchPresenter();
        personalPresenterImp = new PersonalPresenterImp();

    }
    private void initView() {

        if("请选择".equals(tvNationalitySelect.getText().toString())){
            tvNationalitySelect.setText("中国");
        }
        if (getIntent() != null) {
            Intent intent = getIntent();
            idNum = getIntent().getStringExtra("idNum");
            position = intent.getStringExtra("position");
            if (position == null) return;
            if (position.equals("0")) {//户主
                llRelationship.setVisibility(View.GONE);
                underlineRelationship.setVisibility(View.GONE);
                llTel.setVisibility(View.GONE);
                underlineTel.setVisibility(View.GONE);
            }else if (position.equals("1")) {
                llRelationship.setVisibility(View.VISIBLE);
                underlineRelationship.setVisibility(View.VISIBLE);
                llTel.setVisibility(View.VISIBLE);
                underlineTel.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(idNum)) {
                populationBean = (PopulationBean) LocalCache.getInfo(idNum);
                if (populationBean==null)return;
                if (!TextUtils.isEmpty(populationBean.getName())) {
                    memberEtName.setText(populationBean.getName());
                }
                if ("0".equals(position)) {
                    setSexChecked(LocalCache.getUserSex() == 1);
                }else{
                    setSexChecked("男".equals(populationBean.getGender()));
                }

                if (!TextUtils.isEmpty(populationBean.getRelationshipHouseOwner())) {
                    tvRelationshipSelector.setText(populationBean.getRelationshipHouseOwner());
                }
                if (!TextUtils.isEmpty(populationBean.getPhone())) {
                    tvTelSelector.setText(populationBean.getPhone());
                }
                if (!TextUtils.isEmpty(populationBean.getEducationLevel())) {
                    tvEdutationSelect.setText(populationBean.getEducationLevel());
                }
                if (!TextUtils.isEmpty(populationBean.getCountry())) {
                    tvNationalitySelect.setText(populationBean.getCountry());
                    //民族，图像显示，展示身份证图片
                    if ("中国".equals(populationBean.getCountry())) {
                        llNation.setVisibility(View.VISIBLE);
                        underlineTvNation.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(populationBean.getNational())) {
                            tvNationSelect.setText(populationBean.getNational());
                        }
                    }else{
                        llNation.setVisibility(View.GONE);
                        underlineTvNation.setVisibility(View.GONE);
                    }
                }
                if (!TextUtils.isEmpty(populationBean.getIdPassportNo())) {
                    tvIdType.setText(populationBean.getIdPassportNo());
                }

                if (!TextUtils.isEmpty(populationBean.getFrontPhoto())) {
                    filePath = populationBean.getFrontPhoto();
                    Glide.with(this).load(populationBean.getFrontPhoto()).into(ivAbove);
                }
                if (!TextUtils.isEmpty(populationBean.getReversePhoto())) {
                    filePath2 = populationBean.getReversePhoto();
                    Glide.with(this).load(populationBean.getReversePhoto()).into(ivBelow);
                }
            }else{
                if ("0".equals(position)){
                    if (!TextUtils.isEmpty(LocalCache.getUserName())) {
                        User user = LocalCache.getUser();
                        if (null == user){return;}
                        memberEtName.setText(TextUtils.isEmpty(user.getName())?"":user.getName());
                    }
                    setSexChecked(LocalCache.getUserSex() == 1);
                    String userIdNum = LocalCache.getUserIdNum("userIdNum");
                    if (!TextUtils.isEmpty(userIdNum)) {
                        tvIdType.setText(userIdNum);
                    }
                }
            }

        }
    }

    @OnClick({R.id.iv_back,R.id.tv_edutation_select,R.id.tv_relationship_selector
    ,R.id.tv_nationality_select,R.id.tv_nation_select,R.id.iv_above,R.id.iv_below
    ,R.id.tv_man,R.id.tv_women,R.id.tv_next_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //点击选择文化程度
            case R.id.tv_edutation_select:
                setAddressSelectorPopup1(view);
                break;

            //点击选择与户主关系
            case R.id.tv_relationship_selector:
                setAddressSelectorPopup(view);
                break;

            //点击选择国籍
            case R.id.tv_nationality_select:
                setAddressSelectorPopup2(view);
                break;

            //点击选择民族 todo
            case R.id.tv_nation_select:
                //搜索页面
                Intent intent = new Intent();
                intent.setClass(this, SideBarActivity.class);
                startActivityForResult(intent, 0);
                break;

            //点击正面身份证拍照
            case R.id.iv_above:
                showBottomDialog();
                break;

            //点击下面身份证拍照
            case R.id.iv_below:
                showBottomDialog2();
                break;

            //点击男性
            case R.id.tv_man:
                tvMan.setTextColor(getResources().getColor(R.color.bg_census));
                tvMan.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvWomen.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvWomen.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                break;

            //点击女性
            case R.id.tv_women:
                tvWomen.setTextColor(getResources().getColor(R.color.bg_census));
                tvWomen.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
                tvMan.setTextColor(getResources().getColor(R.color.bg_census_default));
                tvMan.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
                break;

            //点击下一步
            case R.id.tv_next_info:

                // 姓名校验
                name = memberEtName.getText().toString();
                if (name.isEmpty()) {
                    ToastUtils.INSTANCE.showToast(this, "请输入姓名");
                    return;
                }
                if (name.length() < 2) {
                    ToastUtils.INSTANCE.showToast(this, "姓名应在2-50字符内");
                    return;
                }

                //性别校验
                int textViewColor = tvMan.getCurrentTextColor();
                int color = tvWomen.getCurrentTextColor();
                String manColor = textViewColor + "";
                String womanColor = color + "";
                if (!manColor.equals("-22208") && !womanColor.equals("-22208")) {
                    ToastUtils.INSTANCE.showToast(this, "请选择性别");
                    return;
                } else if (womanColor.equals("-22208")) {
                    sex = "女";
                } else if (manColor.equals("-22208")) {
                    sex = "男";
                }

                //校验手机号码
                if (llTel.getVisibility() == View.VISIBLE) {
                    guanXi = tvRelationshipSelector.getText().toString();
                    if ("请选择".equals(guanXi)) {
                        ToastUtils.INSTANCE.showToast(this, "请选择与户主关系");
                        return;
                    }
                    phone = tvTelSelector.getText().toString();
                    if (phone.isEmpty()) {
                        ToastUtils.INSTANCE.showToast(this, "请填写联系电话");
                        return;
                    }
                    if (!ValidatorUtil.isMobile(phone)) {
                        ToastUtils.INSTANCE.showToast(this, "联系电话格式不正确");
                        return;
                    }
                }

                wenHua = tvEdutationSelect.getText().toString();
                if ("请选择".equals(wenHua)) {
                    ToastUtils.INSTANCE.showToast(this, "请选择文化程度");
                    return;
                }

                minZu1 = tvNationSelect.getText().toString();
                guojia = tvNationalitySelect.getText().toString();
                if ("请选择".equals(guojia)) {
                    ToastUtils.INSTANCE.showToast(this, "请选择国籍");
                    return;
                } else if (guojia.equals("中国")&&"请选择".equals(minZu1)) {
                    ToastUtils.INSTANCE.showToast(this, "请选择民族");
                    return;
                }
                //证件校验
                zhengJian = tvIdType.getText().toString();
                if (TextUtils.isEmpty(zhengJian)) {
                    ToastUtils.INSTANCE.showToast(this, "请输入证件号");
                    return;
                }

                if (TextUtils.isEmpty(filePath2) || TextUtils.isEmpty(filePath)) {
                    ToastUtils.INSTANCE.showToast(this, "请上传证件照");
                    return;
                }

                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("name", name); //姓名
                if(llTel.getVisibility() == View.VISIBLE){
                    baseRequest.put("relationshipHouseOwner", guanXi);          //户主关系
                    baseRequest.put("phone", phone);                             //电话
                }
                baseRequest.put("gender", sex);
                baseRequest.put("idPassportNo", zhengJian);              //证件号
                baseRequest.put("country", guojia);                                     //国籍
                if ("中国".equals(guojia)){
                    baseRequest.put("national", minZu1);       //名族
                }
                baseRequest.put("educationLevel", wenHua);                              //文化程度
                baseRequest.put("frontPhoto", filePath);                                         //照片
                baseRequest.put("reversePhoto", filePath2);                                       //照片
                baseRequest.put("id", idNum);                                       //id
                gatherInfoPresenter.gatherInfo(baseRequest, integer -> {
                    if (integer!=null){
                        saveCache(integer);
                        Intent intent1 = new Intent(this, SecondStepInfoActivity.class);
                        intent1.putExtra("idNum", integer+"");
                        intent1.putExtra("position", position+"");
                        startActivity(intent1);
                        finish();
                    }
                });
                break;
        }
    }

    private void saveCache(Object integer){
        PopulationBean populationBean = LocalCache.getInfo(idNum);
        if (populationBean==null){
            populationBean = new PopulationBean();
        }
        populationBean.setId((int)integer);
        populationBean.setName(name);
        populationBean.setRelationshipHouseOwner(guanXi);
        populationBean.setPhone(phone);
        populationBean.setGender(sex);
        populationBean.setIdPassportNo(zhengJian);
        populationBean.setCountry(guojia);
        populationBean.setNational(minZu1);
        populationBean.setEducationLevel(wenHua);
        populationBean.setFrontPhoto(filePath);
        populationBean.setReversePhoto(filePath2);
        populationBean.setPosition(position);
        LocalCache.saveInfo(integer+"",populationBean);
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        minZu = this.getIntent().getStringExtra("minZu");
        if (!TextUtils.isEmpty(minZu)) {
            tvNationSelect.setText(minZu);
        }
    }
    private void setSexChecked(boolean man){

        if (man) {
            tvMan.setTextColor(getResources().getColor(R.color.bg_census));
            tvMan.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
            tvWomen.setTextColor(getResources().getColor(R.color.bg_census_default));
            tvWomen.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
        } else {
            tvWomen.setTextColor(getResources().getColor(R.color.bg_census));
            tvWomen.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex));
            tvMan.setTextColor(getResources().getColor(R.color.bg_census_default));
            tvMan.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_census_sex_default));
        }
        if ("0".equals(position)){
            tvMan.setEnabled(false);
            tvWomen.setEnabled(false);
        }

    }

    //户主关系
    private void setAddressSelectorPopup(View v) {
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
                        data.add(new InfoBean("亲属"));
                        data.add(new InfoBean("朋友"));
                        data.add(new InfoBean("租户"));
                        data.add(new InfoBean("保姆"));
                        data.add(new InfoBean("钟点工"));
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
                                tvRelationshipSelector.setText(selectedInfo.getInfo());
                                if (!TextUtils.isEmpty(tvRelationshipSelector.getText().toString())) {
                                    guanXi = tvRelationshipSelector.getText().toString();
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


    //文化
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
                        data.add(new InfoBean("博士研究生以上"));
                        data.add(new InfoBean("硕士研究生"));
                        data.add(new InfoBean("本科"));
                        data.add(new InfoBean("大专"));
                        data.add(new InfoBean("中专"));
                        data.add(new InfoBean("高中"));
                        data.add(new InfoBean("初中"));
                        data.add(new InfoBean("小学"));
                        data.add(new InfoBean("文盲"));
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
                                tvEdutationSelect.setText(selectedInfo.getInfo());
                                if (!TextUtils.isEmpty(tvEdutationSelect.getText().toString())) {
                                    wenHua = tvEdutationSelect.getText().toString();
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

    //国籍
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
                        data.add(new InfoBean("港、澳、台"));
                        data.add(new InfoBean("中国"));
                        data.add(new InfoBean("其他"));
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
                                if (selectedInfo.getInfo().equals("中国")) {
                                    //民族，图像显示，展示身份证图片
                                    llNation.setVisibility(View.VISIBLE);
                                    underlineTvNation.setVisibility(View.VISIBLE);
//                        tvIdType.setHint("请输入身份证号");
                                    Glide.with(OwnerInfoActivity.this)
                                            .load(R.mipmap.sfzzheng)
                                            .into(ivAbove);
                                    Glide.with(OwnerInfoActivity.this)
                                            .load(R.mipmap.sfzfan)
                                            .into(ivBelow);
                                } else if (selectedInfo.getInfo().equals("港、澳、台")) {
                                    //图像显示，展示通行证
                                    //民族，图像显示，展示身份证图片
                                    llNation.setVisibility(View.GONE);
                                    underlineTvNation.setVisibility(View.GONE);
//                        tvIdType.setHint("请输入港澳台通行证号");
                                    Glide.with(OwnerInfoActivity.this)
                                            .load(R.mipmap.gatzheng)
                                            .into(ivAbove);
                                    Glide.with(OwnerInfoActivity.this)
                                            .load(R.mipmap.gatfan)
                                            .into(ivBelow);
                                } else if (selectedInfo.getInfo().equals("其他")) {
                                    //展示护照
                                    llNation.setVisibility(View.GONE);
                                    underlineTvNation.setVisibility(View.GONE);
//                        tvIdType.setHint("请输入护照号");
                                    Glide.with(OwnerInfoActivity.this)
                                            .load(R.mipmap.hzzheng)
                                            .into(ivAbove);
                                    Glide.with(OwnerInfoActivity.this)
                                            .load(R.mipmap.hzfan)
                                            .into(ivBelow);
                                }
                                tvNationalitySelect.setText(selectedInfo.getInfo());
                                if (!TextUtils.isEmpty(tvNationalitySelect.getText().toString())) {
                                    guojia = tvNationalitySelect.getText().toString();
                                }
                                underlineIdType.setVisibility(View.VISIBLE);
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


    private void showBottomDialog() {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.takephoto_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(view1 -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
            dialog.dismiss();
        });
        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view13 -> dialog.dismiss());
    }

    private void showBottomDialog2() {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.takephoto_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(view1 -> {
//            Intent intent =new Intent(this, PhotoActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivityForResult(intent, 10);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 3);
        });
        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            startActivityForResult(intent, 4);
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view13 -> dialog.dismiss());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 0) {
            minZu = data.getStringExtra("minzu");
            if (!TextUtils.isEmpty(minZu)) {
                tvNationSelect.setText(minZu);
            }
        }
        if (requestCode == 1) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            base = ImgUtils.smallImage(bitmap);
            ivAbove.setImageBitmap(bitmap);
            fileBatchPresenter.updateFile(bitmap, fileBean -> {
                if (fileBean == null) {
                    return;
                }
                filePath = fileBean.getFilePath();
            });
        }
        if (requestCode == 2) {
            try {
                Uri uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                ivAbove.setImageBitmap(bitmap);
                base = ImgUtils.smallImage(bitmap);
                fileBatchPresenter.updateFile(bitmap, fileBean -> {
                    if (fileBean == null) {
                        return;
                    }
                    filePath = fileBean.getFilePath();
                });
//                memberFace.setText("已登记");
                dialog.dismiss();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 3) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            base1 = ImgUtils.bitmapToBase64(bitmap);
            ivBelow.setImageBitmap(bitmap);
            fileBatchPresenter.updateFile(bitmap, fileBean -> {
                if (fileBean == null) {
                    return;
                }
                filePath2 = fileBean.getFilePath();
            });
            dialog.dismiss();
        }
        if (requestCode == 4) {
            try {
                Uri uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                ivBelow.setImageBitmap(bitmap);
                base1 = ImgUtils.smallImage(bitmap);
                fileBatchPresenter.updateFile(bitmap, fileBean -> {
                    if (fileBean == null) {
                        return;
                    }
                    filePath2 = fileBean.getFilePath();
                });
                dialog.dismiss();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
//        if (requestCode == 10) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            base = ImgUtils.smallImage(bitmap);
//            memberFace.setText("已登记");
//            dialog.dismiss();
//        }
    }

    private void updateAvatar(Uri uri) {
        Glide.with(this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                RequestBody requestBody = MultipartBody.create(MediaType.parse("multipart/form-data"), file);
                Log.d(Constant.TAG, "image size: " + (file.length() / 1024));
                RequestBody fb = RequestBody.create(MediaType.parse("fileType"), "IMAGE");
                MultipartBody.Part images = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                personalPresenterImp.fileData(images, fb, OwnerInfoActivity.this::file);
            }
        });
    }

    public void file(String succeed) {
        if (!StringUtils.isEmpty(succeed)) {
            FileBean fileBean = MyGson.mGson(succeed, FileBean.class);
            filePath = fileBean.getFilePath();
        }
    }

    public void file1(String succeed) {
        if (!StringUtils.isEmpty(succeed)) {
            FileBean fileBean = MyGson.mGson(succeed, FileBean.class);
            filePath2 = fileBean.getFilePath();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
