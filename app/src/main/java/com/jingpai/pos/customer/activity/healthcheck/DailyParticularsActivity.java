package com.jingpai.pos.customer.activity.healthcheck;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.DailyParticularsBean;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.customer.bean.show.InfoBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.mvp.presenter.show.home.ParticularsHealthPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.home.UpdatePresenter;
import com.jingpai.pos.customer.mvp.presenter.show.my.FileBatchPresenter;
import com.jingpai.pos.customer.utils.CommonPopWindow;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.PickerScrollNewView;
import com.jingpai.pos.utils.ToastUtils;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * function: 疫情上报历史详情
 */
public class DailyParticularsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.finish_btn)
    TextView finishBtn;
    @BindView(R.id.yes_update)
    TextView yesUpdate;


    @BindView(R.id.tv_is_name)
    EditText tvIsName;
    @BindView(R.id.t_name)
    TextView tName;
    @BindView(R.id.tv_idType)
    TextView tvIdType;
    @BindView(R.id.ll_idType)
    LinearLayout llIdType;
    @BindView(R.id.tv_certificate_number)
    EditText tvCertificateNumber;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_unit_name)
    EditText tvUnitName;
    @BindView(R.id.tv_daily_politics)
    TextView tvDailyPolitics;
    @BindView(R.id.ll_politics_status)
    LinearLayout llPoliticsStatus;
    @BindView(R.id.tv_association)
    EditText tvAssociation;
    @BindView(R.id.tv_animal_heat)
    EditText tvAnimalHeat;
    @BindView(R.id.rb_health)
    RadioButton rbHealth;
    @BindView(R.id.rb_have_fever)
    RadioButton rbHaveFever;
    @BindView(R.id.rb_else)
    RadioButton rbElse;
    @BindView(R.id.rb_suspected)
    RadioButton rbSuspected;
    @BindView(R.id.rg_health_condition)
    RadioGroup rgHealthCondition;
    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.rg_yes_or_not)
    RadioGroup rgYesOrNot;
    @BindView(R.id.rg_yes_not)
    RadioGroup rgYesNot;
    @BindView(R.id.yes_submit)
    TextView yesSubmit;
    @BindView(R.id.sv_content)
    ScrollView svContent;
    @BindView(R.id.iv_particulars)
    ImageView ivParticulars;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.rbYes)
    RadioButton mRbYes;
    @BindView(R.id.rbNo)
    RadioButton mRbNo;

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.iv_del)
    ImageView ivDel;
    @BindView(R.id.ll_del)
    LinearLayout llDel;
    @BindView(R.id.rl_gone)
    RelativeLayout rlGone;
    @BindView(R.id.iv_imag)
    ImageView ivImag;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    private String healthState;
    private boolean touchPatient;
    private boolean beenToEpidemicAreas;
    private int id;
    private int REQUEST_SYSTEM_PIC = 1;
    private ArrayList<File> files = new ArrayList<>();
    private List<FileBean> fileBeans;
    private String filePath;
    private LoadingDialog loadingDialog;
    private int update;
    @Override
    protected int getLayout() {
        return R.layout.daily_particulars_activity;
    }


    @Override
    protected void initData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        loadingDialog = new LoadingDialog(DailyParticularsActivity.this);
        loadingDialog.setCanceledOnTouchOutside(false);


        //获取当前日期
        SimpleDateFormat year = new SimpleDateFormat("yyyy");//获取年份
        SimpleDateFormat month = new SimpleDateFormat("MM");//获取月份
        SimpleDateFormat data = new SimpleDateFormat("dd");//获取月份
        String years = year.format(new Date());
        String MM = month.format(new Date());
        String dd = data.format(new Date());

        String text = "10. 14天内是否接触过疑似病患、接待过来自湖北的亲戚朋友、或者经过武汉*";
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        //设置指定位置textView的背景颜色
        style.setSpan(new ForegroundColorSpan(Color.RED), 37, 38, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置文字的颜色
        tvText.setText(style);

        radioGroupState();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        ParticularsHealthPresenter particularsHealthPresenter = new ParticularsHealthPresenter();
        particularsHealthPresenter.getParticulars(id, new BasePresenter_Old.Callback<DailyParticularsBean>() {
            @Override
            public void success(DailyParticularsBean dailyParticularsBean) {

                String name = dailyParticularsBean.getName();
                String idType = dailyParticularsBean.getIdType();
                String idNumber = dailyParticularsBean.getIdNumber();
                String phone = dailyParticularsBean.getPhone();
                String company = dailyParticularsBean.getCompany();
                String politic = dailyParticularsBean.getPolitic();
                String organization = dailyParticularsBean.getOrganization();
                String temperature = dailyParticularsBean.getTemperature();
                filePath = dailyParticularsBean.getHealthCode();
                String date = dailyParticularsBean.getDate();
                boolean beenToEpidemicAreas = dailyParticularsBean.isBeenToEpidemicAreas();
                boolean touchPatient = dailyParticularsBean.isTouchPatient();
                String healthState = dailyParticularsBean.getHealthState();

                if (date.equals(years + "-" + MM + "-" + dd)){

                }else{
                    yesUpdate.setVisibility(View.GONE);
                }

                tvDate.setText("登记日期" + " " + date);

                if (!beenToEpidemicAreas){
                    rbNo.setChecked(true);
                }else{
                    rbYes.setChecked(true);
                }
                if (!touchPatient){
                    mRbNo.setChecked(true);
                }else{
                    mRbYes.setChecked(true);
                }

                if (healthState.equals("健康")) {
                    rbHealth.setChecked(true);
                } else if (healthState.equals("有发烧、咳嗽等症状")) {
                    rbHaveFever.setChecked(true);
                } else if (healthState.equals("其他症状")) {
                    rbElse.setChecked(true);
                } else {
                    rbSuspected.setChecked(true);
                }

                tvDailyPolitics.setText(politic);
                String s = tvDailyPolitics.getText().toString();
                if (s.isEmpty()){
                    tvDailyPolitics.setText("请选择");
                }
                tvIsName.setText(name);
                tvIdType.setText(idType);
                tvCertificateNumber.setText(idNumber);
                tvPhone.setText(phone);
                tvUnitName.setText(company);
                tvAssociation.setText(organization);
                tvAnimalHeat.setText(temperature);
                Glide.with(DailyParticularsActivity.this).load(filePath).into(ivParticulars);

                SharedPreferences sp = getSharedPreferences("new", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("name", name);
                edit.putString("idType", idType);
                edit.putString("idNumber", idNumber);
                edit.putString("phone", phone);
                edit.putString("company", company);
                edit.putString("politic", politic);
                edit.putString("organization", organization);
                edit.putString("temperature", temperature);
                edit.putString("healthState", healthState);
                edit.putBoolean("touchPatient", touchPatient);
                edit.putBoolean("beenToEpidemicAreas", beenToEpidemicAreas);
                edit.apply();

                if (dailyParticularsBean.getDate().equals(years + "-" + MM + "-" + dd)) {
                    state();
                } else {
                    state();
                }
            }
        });

        rgHealthCondition.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_health:
                    healthState = "健康";
                    break;
                case R.id.rb_have_fever:
                    healthState = "有发烧、咳嗽等症状";
                    break;
                case R.id.rb_else:
                    healthState = "其他症状";
                    break;
                case R.id.rb_suspected:
                    healthState = "疑似/确诊感染";
                    break;
            }
        });
        // rgYesOrNot 是否接触患者
        rgYesOrNot.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_yes:
                    touchPatient = true;
                    break;
                case R.id.rb_no:
                    touchPatient = false;
                    break;
            }
        });
        // rgYesNot 是否去过疫区
        rgYesNot.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbYes:
                    beenToEpidemicAreas = true;
                    break;
                case R.id.rbNo:
                    beenToEpidemicAreas = false;
                    break;
            }
        });

    }

    //RadioGroup不可操作
    private void rgHealthConditionFalseRadioGroup(RadioGroup rgHealthCondition) {
        for (int i = 0; i < rgHealthCondition.getChildCount(); i++) {
            rgHealthCondition.getChildAt(i).setEnabled(false);
        }
    }

    private void rgYesOrNotFalseRadioGroup(RadioGroup rgYesOrNot) {
        for (int i = 0; i < rgYesOrNot.getChildCount(); i++) {
            rgYesOrNot.getChildAt(i).setEnabled(false);
        }
    }

    private void rgYesNotFalseRadioGroup(RadioGroup rgYesNot) {
        for (int i = 0; i < rgYesNot.getChildCount(); i++) {
            rgYesNot.getChildAt(i).setEnabled(false);
        }
    }

    //可以操作
    private void rgHealthConditionTrueRadioGroup(RadioGroup rgHealthCondition) {
        for (int i = 0; i < rgHealthCondition.getChildCount(); i++) {
            rgHealthCondition.getChildAt(i).setEnabled(true);
        }
    }

    private void rgYesOrNotTrueRadioGroup(RadioGroup rgHealthCondition) {
        for (int i = 0; i < rgHealthCondition.getChildCount(); i++) {
            rgHealthCondition.getChildAt(i).setEnabled(true);
        }
    }

    private void rgYesNotTrueRadioGroup(RadioGroup rgHealthCondition) {
        for (int i = 0; i < rgHealthCondition.getChildCount(); i++) {
            rgHealthCondition.getChildAt(i).setEnabled(true);
        }
    }

    @OnClick(R.id.finish_btn)
    public void onFinishBtnClicked() {
        finish();
    }

    @OnClick(R.id.yes_update)
    public void onYesUpdateClicked() {

        finishBtn.setVisibility(View.GONE);
        tvCancel.setVisibility(View.VISIBLE);

        tvIsName.setEnabled(true);
        tvCertificateNumber.setEnabled(true);
        tvPhone.setEnabled(true);
        tvUnitName.setEnabled(true);
        tvDailyPolitics.setEnabled(true);
        tvAssociation.setEnabled(true);
        tvAnimalHeat.setEnabled(true);
        rgHealthConditionTrueRadioGroup(rgHealthCondition);
        rgYesOrNotTrueRadioGroup(rgYesOrNot);
        rgYesNotTrueRadioGroup(rgYesNot);

        yesUpdate.setVisibility(View.GONE);
        yesSubmit.setVisibility(View.VISIBLE);

        llIdType.setOnClickListener(this);
        llPoliticsStatus.setOnClickListener(this);

        llDel.setVisibility(View.VISIBLE);
        ivDel.setOnClickListener(this);
    }

    //修改后提交
    @OnClick(R.id.yes_submit)
    public void onYesSubmitClicked() {

        if (update == 1) {

            loadingDialog.show();

            String name = tvIsName.getText().toString();
            String number = tvCertificateNumber.getText().toString();
            String phone = tvPhone.getText().toString();
            String unitName = tvUnitName.getText().toString();
            String politics = tvDailyPolitics.getText().toString();
            String association = tvAssociation.getText().toString();
            String animalHeat = tvAnimalHeat.getText().toString();
            String certificate = tvIdType.getText().toString();

            FileBatchPresenter fileBatchPresenter = new FileBatchPresenter();
            fileBatchPresenter.getFileBatch(files, "IMAGE", fileArray -> {
                fileBeans = fileArray.toJavaList(FileBean.class);
                filePath = fileBeans.get(0).getFilePath();

                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("beenToEpidemicAreas", beenToEpidemicAreas);
                baseRequest.put("company", unitName);
                baseRequest.put("healthCode", filePath);
                baseRequest.put("healthState", healthState);
                baseRequest.put("idNumber", number);
                baseRequest.put("idType", certificate);
                baseRequest.put("name", name);
                baseRequest.put("organization", association);
                baseRequest.put("phone", phone);
                baseRequest.put("politic", politics);
                baseRequest.put("temperature", animalHeat);
                baseRequest.put("touchPatient", touchPatient);
                baseRequest.put("id", id);
                UpdatePresenter updatePresenter = new UpdatePresenter();
                updatePresenter.getUpdate(baseRequest, reportBean -> {
                    ToastUtils.INSTANCE.showToast("修改成功");
                    loadingDialog.dismiss();
                    finish();
                });
            });

        } else {

            loadingDialog.show();

            String name = tvIsName.getText().toString();
            String number = tvCertificateNumber.getText().toString();
            String phone = tvPhone.getText().toString();
            String unitName = tvUnitName.getText().toString();
            String politics = tvDailyPolitics.getText().toString();
            String association = tvAssociation.getText().toString();
            String animalHeat = tvAnimalHeat.getText().toString();
            String certificate = tvIdType.getText().toString();

            TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
            baseRequest.put("beenToEpidemicAreas", beenToEpidemicAreas);
            baseRequest.put("company", unitName);
            baseRequest.put("healthCode", filePath);
            baseRequest.put("healthState", healthState);
            baseRequest.put("idNumber", number);
            baseRequest.put("idType", certificate);
            baseRequest.put("name", name);
            baseRequest.put("organization", association);
            baseRequest.put("phone", phone);
            baseRequest.put("politic", politics);
            baseRequest.put("temperature", animalHeat);
            baseRequest.put("touchPatient", touchPatient);
            baseRequest.put("id", id);
            UpdatePresenter updatePresenter = new UpdatePresenter();
            updatePresenter.getUpdate(baseRequest, reportBean -> {
                ToastUtils.INSTANCE.showToast("修改成功");
                loadingDialog.dismiss();
                finish();
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_idType:
//                //1、使用Dialog、设置style
//                Dialog log = new Dialog(this, R.style.DialogTheme);
//                //2、设置布局
//                View inflate1 = View.inflate(this, R.layout.idtype_dialog, null);
//                log.setContentView(inflate1);
//                Window windows = log.getWindow();
//                //设置弹出位置
//                windows.setGravity(Gravity.BOTTOM);
//                //设置弹出动画
//                windows.setWindowAnimations(R.style.main_menu_animStyle);
//                //设置对话框大小
//                windows.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                log.show();
//                log.findViewById(R.id.tv_identity_card).setOnClickListener(view1 -> {
//                    tvIdType.setText("居民身份证");
//                    log.dismiss();
//                });
//                log.findViewById(R.id.tv_passport).setOnClickListener(view12 -> {
//                    tvIdType.setText("护照");
//                    log.dismiss();
//                });
//                log.findViewById(R.id.tv_else).setOnClickListener(view13 -> {
//                    tvIdType.setText("其他");
//                    log.dismiss();
//                });
                setAddressSelectorPopup1(v);
                break;
            case R.id.ll_politics_status:
                setAddressSelectorPopup(v);
                break;
            case R.id.iv_del:
                //清除图片
                update = 1;
                ivImag.setVisibility(View.VISIBLE);
                rlGone.setVisibility(View.GONE);
                files.clear();
                ivParticulars.setImageBitmap(null);
                break;
        }
    }

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
                        List<InfoBean> data=new ArrayList<>();
                        data.add(new InfoBean("群众"));
                        data.add(new InfoBean("中共党员"));
                        data.add(new InfoBean("共青团员"));
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
                                tvDailyPolitics.setText(selectedInfo.getInfo());
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
                        List<InfoBean> data=new ArrayList<>();
                        data.add(new InfoBean("身份证"));
                        data.add(new InfoBean("护照"));
                        data.add(new InfoBean("其他证件"));
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
                                tvIdType.setText(selectedInfo.getInfo());
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

    @OnClick(R.id.iv_imag)
    public void onIvImagClicked() {

        if (ContextCompat.checkSelfPermission(DailyParticularsActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(DailyParticularsActivity.this, new
                    String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            //打开系统相册
            openAlbum();
        }

    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SYSTEM_PIC);//打开系统相册
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SYSTEM_PIC && resultCode == RESULT_OK && null != data) {
            if (Build.VERSION.SDK_INT >= 19) {
                handleImageOnKitkat(data);
            } else {
                handleImageBeforeKitkat(data);
            }
        }
    }

    private void handleImageBeforeKitkat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private void handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content:" +
                        "//downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是File类型的uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            files.clear();
            File file = new File(imagePath);
            files.add(file);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivImag.setVisibility(View.GONE);
            rlGone.setVisibility(View.VISIBLE);
            Glide.with(DailyParticularsActivity.this).load(bitmap).into(ivParticulars);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    //取消恢复到未修改前数据
    @OnClick(R.id.tv_cancel)
    public void onTvCancelClicked() {

        llDel.setVisibility(View.GONE);

        state();

        SharedPreferences aNew = getSharedPreferences("new", MODE_PRIVATE);
        String name = aNew.getString("name", "");
        String idType = aNew.getString("idType", "");
        String idNumber = aNew.getString("idNumber", "");
        String phone = aNew.getString("phone", "");
        String company = aNew.getString("company", "");
        String politic = aNew.getString("politic", "");
        String organization = aNew.getString("organization", "");
        String temperature = aNew.getString("temperature", "");
        String healthState = aNew.getString("healthState", "");
        boolean touchPatient = aNew.getBoolean("touchPatient", true);
        boolean beenToEpidemicAreas = aNew.getBoolean("beenToEpidemicAreas", true);

        if (beenToEpidemicAreas) {
            rbYes.setChecked(true);
        } else {
            rbNo.setChecked(true);
        }
        if (touchPatient) {
            mRbYes.setChecked(true);
        } else {
            mRbNo.setChecked(true);
        }
        if (healthState.equals("健康")) {
            rbHealth.setChecked(true);
        } else if (healthState.equals("有发烧、咳嗽等症状")) {
            rbHaveFever.setChecked(true);
        } else if (healthState.equals("其他症状")) {
            rbElse.setChecked(true);
        } else {
            rbSuspected.setChecked(true);
        }
        tvIsName.setText(name);
        tvIdType.setText(idType);
        tvCertificateNumber.setText(idNumber);
        tvPhone.setText(phone);
        tvUnitName.setText(company);
        tvDailyPolitics.setText(politic);
        tvAssociation.setText(organization);
        tvAnimalHeat.setText(temperature);
        Glide.with(DailyParticularsActivity.this).load(filePath).into(ivParticulars);

        yesSubmit.setVisibility(View.GONE);
        yesUpdate.setVisibility(View.VISIBLE);

        finishBtn.setVisibility(View.VISIBLE);
        tvCancel.setVisibility(View.GONE);

        radioGroupState();

    }

    private void state() {
        tvIsName.setEnabled(false);
        tvCertificateNumber.setEnabled(false);
        tvPhone.setEnabled(false);
        tvUnitName.setEnabled(false);
        tvDailyPolitics.setEnabled(false);
        tvAssociation.setEnabled(false);
        tvAnimalHeat.setEnabled(false);
    }

    public void radioGroupState() {
        rgYesNotFalseRadioGroup(rgYesNot);
        rgYesOrNotFalseRadioGroup(rgYesOrNot);
        rgHealthConditionFalseRadioGroup(rgHealthCondition);
    }

}