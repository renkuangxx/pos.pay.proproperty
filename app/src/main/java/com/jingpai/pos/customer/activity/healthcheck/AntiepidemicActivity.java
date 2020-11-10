package com.jingpai.pos.customer.activity.healthcheck;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
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
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.customer.bean.show.InfoBean;
import com.jingpai.pos.customer.bean.show.ReportBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.mvp.presenter.show.LastSubmitPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.home.AntiepidemicPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.my.FileBatchPresenter;
import com.jingpai.pos.customer.utils.CommonPopWindow;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.PickerScrollNewView;
import com.jingpai.pos.utils.ToastUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 时间: 2020/3/8
 * 功能: 今日登记
 */
public class AntiepidemicActivity extends BaseActivity {

    @BindView(R.id.tv_is_name)
    EditText tvIsName;
    @BindView(R.id.t_name)
    TextView tName;
    @BindView(R.id.tv_idType)
    TextView tvIdType;
    @BindView(R.id.tv_certificate_number)
    EditText tvCertificateNumber;
    @BindView(R.id.tv_phone)
    EditText tvPhone;
    @BindView(R.id.tv_unit_name)
    EditText tvUnitName;
    @BindView(R.id.tv_association)
    EditText tvAssociation;
    @BindView(R.id.rg_health_condition)
    RadioGroup rgHealthCondition;
    @BindView(R.id.rg_yes_or_not)
    RadioGroup rgYesOrNot;
    @BindView(R.id.rg_yes_not)
    RadioGroup rgYesNot;
    @BindView(R.id.sv_content)
    ScrollView svContent;
    @BindView(R.id.rl_btn)
    RelativeLayout rlBtn;
    @BindView(R.id.tv_politics)
    TextView tvPolitics;
    @BindView(R.id.tv_animal_heat)
    EditText tvAnimalHeat;
    @BindView(R.id.ll_idType)
    LinearLayout llIdType;
    @BindView(R.id.ll_politics_status)
    LinearLayout llPoliticsStatus;
    @BindView(R.id.rb_health)
    RadioButton rbHealth;
    @BindView(R.id.rb_have_fever)
    RadioButton rbHaveFever;
    @BindView(R.id.rb_else)
    RadioButton rbElse;
    @BindView(R.id.rb_suspected)
    RadioButton rbSuspected;
    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.r_no)
    RadioButton rNo;
    @BindView(R.id.r_yes)
    RadioButton rYes;
    @BindView(R.id.iv_imag)
    ImageView ivImag;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_del)
    ImageView ivDel;
    @BindView(R.id.ll_del)
    LinearLayout llDel;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.rl_gone)
    RelativeLayout rlGone;
    @BindView(R.id.tv_text)
    TextView tvText;
    private ArrayList<File> files = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<FileBean> fileBeanList;
    private boolean beenToEpidemicAreas;
    private boolean touchPatient;
    private String healthState;
    private int REQUEST_SYSTEM_PIC = 1;
    private LoadingDialog loadingDialog;
    private String healthCode;

    @Override
    protected int getLayout() {
        return R.layout.antiepidemic_activity;
    }


    @Override
    protected void initData() {

        loadingDialog = new LoadingDialog(AntiepidemicActivity.this);
        loadingDialog.setCanceledOnTouchOutside(false);

        String text = "10. 14天内是否接触过疑似病患、接待过来自湖北的亲戚朋友、或者经过武汉*";
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        //设置指定位置textView的背景颜色
        style.setSpan(new ForegroundColorSpan(Color.RED), 37, 38, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置文字的颜色
        tvText.setText(style);

        //获取当前日期
        SimpleDateFormat year = new SimpleDateFormat("yyyy");//获取年份
        SimpleDateFormat month = new SimpleDateFormat("MM");//获取月份
        SimpleDateFormat data = new SimpleDateFormat("dd");//获取月份
        String years = year.format(new Date());
        String MM = month.format(new Date());
        String dd = data.format(new Date());
        Log.e("当前日期时间", years + "-" + MM + "-" + dd);
        tvDate.setText("登记日期 " + years + "-" + MM + "-" + dd);

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
        // rgYesOrNot 是否接触过患者
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
                case R.id.r_yes:
                    beenToEpidemicAreas = true;
                    break;
                case R.id.r_no:
                    beenToEpidemicAreas = false;
                    break;
            }
        });

        LastSubmitPresenter antiepidemicPresenter = new LastSubmitPresenter();
        antiepidemicPresenter.getLastSubmit(dailyParticularsBean -> {
            String name = dailyParticularsBean.getName();
            String idType = dailyParticularsBean.getIdType();
            String idNumber = dailyParticularsBean.getIdNumber();
            String phone = dailyParticularsBean.getPhone();
            String company = dailyParticularsBean.getCompany();
            String politic = dailyParticularsBean.getPolitic();
            String organization = dailyParticularsBean.getOrganization();
            String temperature = dailyParticularsBean.getTemperature();
            healthCode = dailyParticularsBean.getHealthCode();
            String date = dailyParticularsBean.getDate();
            boolean beenToEpidemicAreas = dailyParticularsBean.isBeenToEpidemicAreas();
            boolean touchPatient = dailyParticularsBean.isTouchPatient();
            healthState = dailyParticularsBean.getHealthState();
            if (!TextUtils.isEmpty(date)) {
                tvDate.setText("登记日期 " + date);
            }

            if (!beenToEpidemicAreas) {
                rbNo.setChecked(true);
            } else {
                rbYes.setChecked(true);
            }
            if (!touchPatient) {
                rNo.setChecked(true);
            } else {
                rYes.setChecked(true);
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
            tvPolitics.setText(politic);
            tvAssociation.setText(organization);
            tvAnimalHeat.setText(temperature);

            rlGone.setVisibility(View.VISIBLE);
            ivImag.setVisibility(View.GONE);
            Glide.with(AntiepidemicActivity.this).load(healthCode).into(ivImage);

        });

    }

    @OnClick({R.id.ll_politics_status, R.id.ll_idType, R.id.iv_imag, R.id.ll_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_del:
                files.clear();
                ivImage.setImageBitmap(null);
                ivImag.setVisibility(View.VISIBLE);
                rlGone.setVisibility(View.GONE);
                break;
            case R.id.iv_imag:
                if (ContextCompat.checkSelfPermission(AntiepidemicActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AntiepidemicActivity.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    //打开系统相册
                    openAlbum();
                }
                break;
            case R.id.ll_politics_status:
                setAddressSelectorPopup(view);
                break;
            case R.id.ll_idType:
                setAddressSelectorPopup1(view);
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
                                tvPolitics.setText(selectedInfo.getInfo());
                            }
                        });
                    }
                })
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(AntiepidemicActivity.this)
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
                .build(AntiepidemicActivity.this)
                .showAsBottom(v);
    }

    @OnClick(R.id.yes_btn)
    public void submit() {
        String name = tvIsName.getText().toString();
        String number = tvCertificateNumber.getText().toString();
        String phone = tvPhone.getText().toString();
        String unitName = tvUnitName.getText().toString();
        String politics = tvPolitics.getText().toString();
        String association = tvAssociation.getText().toString();
        String animalHeat = tvAnimalHeat.getText().toString();
        String certificate = tvIdType.getText().toString();

        if (name.isEmpty()) {
            ToastUtils.INSTANCE.showToast("请输入姓名");
        } else if (phone.length() > 11) {
            ToastUtils.INSTANCE.showToast("请输入合法手机号");
        } else if (tvIdType.getText().equals("请选择证件类型")) {
            ToastUtils.INSTANCE.showToast("请选择证件类型");
        } else if (number.isEmpty()) {
            ToastUtils.INSTANCE.showToast("请输入证件号");
        } else if (animalHeat.isEmpty()) {
            ToastUtils.INSTANCE.showToast("请输入测量体温");
        } else if (files.size() == 0 && StringUtils.isEmpty(healthCode)) {
            ToastUtils.INSTANCE.showToast("请上传健康码");
        } else if (healthState == null) {
            ToastUtils.INSTANCE.showToast("请选择健康状态");
        } else if (!rbYes.isChecked() && !rbNo.isChecked()) {
            Toast.makeText(this, "请勾选完整", Toast.LENGTH_SHORT).show();
        } else if (!rYes.isChecked() && !rNo.isChecked()) {
            ToastUtils.INSTANCE.showToast("请勾选完整");
        } else {
            if (!files.isEmpty()) {
                FileBatchPresenter fileBatchPresenter = new FileBatchPresenter();
                fileBatchPresenter.getFileBatch(files, "IMAGE", fileArray -> {
                    fileBeanList = fileArray.toJavaList(FileBean.class);
                    String filePath = fileBeanList.get(0).getFilePath();

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

                    AntiepidemicPresenter antiepidemicPresenter = new AntiepidemicPresenter();
                    loadingDialog.show();

                    antiepidemicPresenter.getAntiepidemic(baseRequest, new BasePresenter_Old.Callback<ReportBean>() {
                        @Override
                        public void success(ReportBean reportBean) {
                            loadingDialog.dismiss();
                            if (reportBean == null) {
                                return;
                            }
                            ToastUtils.INSTANCE.showToast("登记成功");

                            finish();
                        }
                    });
                });
            } else {
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("beenToEpidemicAreas", beenToEpidemicAreas);
                baseRequest.put("company", unitName);
                baseRequest.put("healthCode", healthCode);
                baseRequest.put("healthState", healthState);
                baseRequest.put("idNumber", number);
                baseRequest.put("idType", certificate);
                baseRequest.put("name", name);
                baseRequest.put("organization", association);
                baseRequest.put("phone", phone);
                baseRequest.put("politic", politics);
                baseRequest.put("temperature", animalHeat);
                baseRequest.put("touchPatient", touchPatient);
                loadingDialog.show();
                AntiepidemicPresenter antiepidemicPresenter = new AntiepidemicPresenter();
                antiepidemicPresenter.getAntiepidemic(baseRequest, reportBean -> {
                    loadingDialog.dismiss();
                    if (reportBean == null) {
                        return;
                    }
                    ToastUtils.INSTANCE.showToast("登记成功");

                    finish();
                });
            }
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
            File file = new File(imagePath);
            files.add(file);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            ivImag.setVisibility(View.GONE);
            rlGone.setVisibility(View.VISIBLE);
            Glide.with(AntiepidemicActivity.this).load(bitmap).into(ivImage);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}