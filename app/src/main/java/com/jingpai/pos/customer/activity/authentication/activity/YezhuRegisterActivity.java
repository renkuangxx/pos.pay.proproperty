package com.jingpai.pos.customer.activity.authentication.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.PhotoActivity;
import com.jingpai.pos.customer.activity.invite.InvireGridImageAdapter;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.PathBean;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.customer.views.CustomToolBar;
import com.jingpai.pos.customer.mvp.YezhuPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.my.FileBatchPresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.DialogUtils;
import com.jingpai.pos.customer.utils.FullyGridLayoutManager;
import com.jingpai.pos.customer.utils.GetPathFromUri;
import com.jingpai.pos.customer.utils.ImgUtils;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.QiniuBitmapUtils;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.customer.utils.SystemUtils;
import com.jingpai.pos.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * @author 86173
 * <p>
 * 邀请注册页面
 */
public class YezhuRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomToolBar toolbar;
    private EditText memberEtName;
    private TextView tvMan;
    private TextView tvWomen;
    private LinearLayout llTel;
    private EditText tvTelSelector;
    private TextView tvNationalitySelect;
    private LinearLayout llChoseIdType;
    private EditText tvIdType;
    private TextView tvTitle;
    private ImageView ivAbove;
    private ImageView ivBelow;
    private LinearLayout llZuhu;
    private LinearLayout llNationality, ll_renlian;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private RelativeLayout rlPhoto;
    private TextView tvStar2;
    private TextView tvTitleHetong;
    private RecyclerView rlHetong;
    private TextView tvNextInfo;
    private TextView tv_present;
    private FrameLayout flTimer;
    private YezhuPresenter yezhuPresenter;
    String tvMaritalStatusSelectStr;
    String tvEndStatusSelectStr;
    private int shenfen = 1;
    int sex;
    String phone;
    private Dialog dialog;
    private String renlianBase;
    private String zhengJian;
    private DialogUtils loading;
    String filePath;
    String filePath2;
    String roomNo;
    String type;
    int houseId;
    private FileBatchPresenter fileBatchPresenter;
    private File tempFile;
    private List<LocalMedia> selectList = new ArrayList<>();
    private InvireGridImageAdapter adapter;
    private RecyclerView mRecyclerView;
    private int maxSelectNum = 5;
    private ArrayList<File> files = new ArrayList<>();
    private List<FileBean> fileBeanList = new ArrayList<>();
    private TimePickerView pvTime;
    int isChanquanren;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yezhu_info);
        initView();
        initWidget();
        initListemer();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        memberEtName = findViewById(R.id.member_et_name);
        tvMan = findViewById(R.id.tv_man);
        tvWomen = findViewById(R.id.tv_women);
        llTel = findViewById(R.id.ll_tel);
        ll_renlian = findViewById(R.id.ll_renlian);
        tvTelSelector = findViewById(R.id.tv_tel_selector);
        tvNationalitySelect = findViewById(R.id.tv_nationality_select);
        llChoseIdType = findViewById(R.id.ll_chose_id_type);
        tvIdType = findViewById(R.id.tv_id_type);
        tvTitle = findViewById(R.id.tv_title);
        ivAbove = findViewById(R.id.iv_above);
        ivBelow = findViewById(R.id.iv_below);
        llZuhu = findViewById(R.id.ll_zuhu);
        llNationality = findViewById(R.id.ll_nationality);
        tvStartTime = findViewById(R.id.tv_start_time);
        tvEndTime = findViewById(R.id.tv_end_time);
        rlPhoto = findViewById(R.id.rl_photo);
        tvStar2 = findViewById(R.id.tv_star2);
        tvTitleHetong = findViewById(R.id.tv_title_hetong);
        mRecyclerView = findViewById(R.id.rl_hetong);
        tvNextInfo = findViewById(R.id.tv_next_info);
        tv_present = findViewById(R.id.tv_present);
        flTimer = findViewById(R.id.fl_timer);


        StatusBarUtil.INSTANCE.setStatusBarMode(this, true, R.color.black);
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));
        phone = TextUtils.isEmpty(LocalCache.getUser().getPhone())?"":LocalCache.getUser().getPhone();
        tvTelSelector.setText(phone);
        tvTelSelector.setClickable(false);
        tvTelSelector.setEnabled(false);
        fileBatchPresenter = new FileBatchPresenter();
        loading = new DialogUtils(this, R.style.CustomDialog);
        loading.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                YezhuRegisterActivity.this.finish();
                return false;
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            shenfen = intent.getIntExtra("shenFen", 1);
            isChanquanren = intent.getIntExtra("isChanquanren", 0);
            roomNo = intent.getStringExtra("roomNo");
            houseId = intent.getIntExtra("houseId", 0);
        }
        if (shenfen == 1) {
            type = "OWNER";
        } else if (shenfen == 2) {
            type = "FAMILY";
        } else {
            type = "TENANT";
        }
        tv_present.setText(roomNo);
//        String xiaoqu = intent.getStringExtra("shenFen");
//        tv_present.setText(TextUtils.isEmpty(xiaoqu)?"":xiaoqu);
        //展示隐藏逻辑
        llZuhu.setVisibility(shenfen == 3 ? View.VISIBLE : View.GONE);
        yezhuPresenter = new YezhuPresenter();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_start_time:
                flTimer.setVisibility(View.VISIBLE);
                initTimePicker2(tvStartTime);
                pvTime.show(v, false);
                break;
            case R.id.tv_end_time:
                flTimer.setVisibility(View.VISIBLE);
                initTimePicker2(tvEndTime);
                pvTime.show(v, false);
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

            //人脸登记
            case R.id.ll_renlian:
                showBottomDialog(2);
                break;

            // 身份证 上
            case R.id.iv_above:
                showBottomDialog(0);
                break;

            //身份证 反
            case R.id.iv_below:
                showBottomDialog(1);
                break;

            //提交按钮
            case R.id.tv_next_info:
                if (SystemUtils.isFastClick())return;
                loading.show();
                // 姓名校验
                String name = memberEtName.getText().toString();
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
                    sex = 2;
                } else if (manColor.equals("-22208")) {
                    sex = 1;
                } else {
                    sex = 0;
                }

                //校验手机号码
//                if (phone.isEmpty()) {
//                    ToastUtils.INSTANCE.showToast(this, "请填写联系电话");
//                    return;
//                }
//                if (!ValidatorUtil.isMobile(phone)) {
//                    ToastUtils.INSTANCE.showToast(this, "联系电话格式不正确");
//                    return;
//                }

                //证件校验
                zhengJian = tvIdType.getText().toString();
                if (TextUtils.isEmpty(zhengJian)) {
                    ToastUtils.INSTANCE.showToast(this, "请输入证件号");
                    return;
                }
                if (TextUtils.isEmpty(renlianBase)) {
                    ToastUtils.INSTANCE.showToast(this, "请进行人脸拍照");
                    return;
                }
                if (TextUtils.isEmpty(filePath2) || TextUtils.isEmpty(filePath)) {
                    ToastUtils.INSTANCE.showToast(this, "身份证正反面图片不能为空");
                    return;
                }

                // =========================================获取图片批量url

                //上传数据  必传
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("operatorType", type);              //操作者类型
                baseRequest.put("type", type);              //类型
                baseRequest.put("houseId", houseId);              //fangwu id
                baseRequest.put("operateType", isChanquanren);
//                if (isChanquanren == 1) {
//                    baseRequest.put("operateType", "ERROR_CHANGE");
//                }else if (isChanquanren == 2){
//                    baseRequest.put("operateType", "CHANGE");
//                }else{
//                    baseRequest.put("operateType", "ADD");
//                }
                baseRequest.put("name", name);
                baseRequest.put("phone", phone);                             //电话
                baseRequest.put("gender", sex);
                baseRequest.put("idCard", zhengJian);              //证件号
                baseRequest.put("certificateType", "ID_CARD");              //证件号

                baseRequest.put("face", renlianBase);              //人脸
                baseRequest.put("idCardPositive", filePath);              //正  身份证
                baseRequest.put("idCardNegative", filePath2);              //反 身份证

//=============================显示情况 提交==========================
                if (shenfen == 3) {
                    //开始时间
                    tvMaritalStatusSelectStr = tvStartTime.getText().toString();
                    if (TextUtils.isEmpty(tvMaritalStatusSelectStr) || tvMaritalStatusSelectStr.equals("请选择")) {
                        Toast.makeText(this, "请选择合同开始时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //结束时间
                    tvEndStatusSelectStr = tvEndTime.getText().toString();
                    if (TextUtils.isEmpty(tvEndStatusSelectStr) || tvEndStatusSelectStr.equals("请选择")) {
                        Toast.makeText(this, "请选择合同开始时间", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    files.clear();
                    for (int i = 0; i < selectList.size(); i++) {
                        String compressPath = selectList.get(i).getCompressPath();
                        File file = new File(compressPath);
                        files.add(file);
                    }

                    if (files.size() == 0) {
                        ToastUtils.INSTANCE.showLongToast(this, "租赁合同图片不能为空");
                        return;
                    }
                    FileBatchPresenter fileBatchPresenter = new FileBatchPresenter();
                    fileBatchPresenter.getFileBatch(files, "IMAGE", fileArray -> {
                        fileBeanList = fileArray.toJavaList(FileBean.class);
                        ArrayList<PathBean> list = new ArrayList<>();
                        for (int i = 0; i < fileBeanList.size(); i++) {
                            String filePath = fileBeanList.get(i).getFilePath();
                            list.add(new PathBean(filePath));
                        }
//                        ToastUtils.INSTANCE.showToast("批量成功");

                        baseRequest.put("leaseStartTime", tvMaritalStatusSelectStr);              //开始时间
                        baseRequest.put("leaseEndTime", tvEndStatusSelectStr);              //结束时间
                        baseRequest.put("leaseContract", list);              //list 照片 合同

                        yezhuPresenter.upYezhuInfo(baseRequest, s -> {
                            loading.dismiss();
                            if (s == null) return;
//                            ToastUtils.INSTANCE.showLongToast(this, "提交成功");
                            Intent intent = new Intent(this, YezhuSuccessActivity.class);
                            intent.putExtra("shenfen", type);
                            startActivity(intent);
                            //关闭所有activity
                            finish();
                        });
                    });
                    return;
                }

//====================================显示 提交===============================
                //shen fen ==2
                loading.show();
                loading.dismiss();
                yezhuPresenter.upYezhuInfo(baseRequest, s -> {
                    if (s == null) return;
                    Intent intent = new Intent(this, YezhuSuccessActivity.class);
                    intent.putExtra("shenfen", type);
                    startActivity(intent);
                    //关闭所有activity
                    finish();
                });
                break;
        }
    }

    private void initListemer() {
        tvNextInfo.setOnClickListener(this);
        ivAbove.setOnClickListener(this);
        ivBelow.setOnClickListener(this);
        tvMan.setOnClickListener(this);
        tvWomen.setOnClickListener(this);
        ll_renlian.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
//        yes_btn.setOnClickListener(this);
    }


    private void initWidget() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new InvireGridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, v) -> {
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        PictureSelector.create(YezhuRegisterActivity.this).externalPicturePreview(position, selectList);
                        break;
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private InvireGridImageAdapter.onAddPicClickListener onAddPicClickListener = () -> {
        //获取写的权限
        RxPermissions rxPermission = new RxPermissions(YezhuRegisterActivity.this);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {// 用户已经同意该权限
                        showBottomDialog(3);
                    } else {
                        Toast.makeText(this, "请同意权限申请", Toast.LENGTH_SHORT).show();
                    }
                });
    };
    private void initTimePicker2(TextView view) {
//控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 0, 23);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2049, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
//                Button btn = (Button) v;
//                btn.setText(getTime(date));
                view.setText(getTime(date));
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
                                flTimer.setVisibility(View.GONE);
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                flTimer.setVisibility(View.GONE);
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
                .setRangDate(startDate, endDate)
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

    private void up2Info() {
        files.clear();
        for (int i = 0; i < selectList.size(); i++) {
            String compressPath = selectList.get(i).getCompressPath();
            File file = new File(compressPath);
            files.add(file);
        }
        if (TextUtils.isEmpty(filePath2) || TextUtils.isEmpty(filePath)) {
            ToastUtils.INSTANCE.showToast(this, "身份证正反面图片不能为空");
            return;
        }
        if (files.size() == 0) {
            ToastUtils.INSTANCE.showLongToast(this, "租赁合同图片不能为空");
            return;
        }
        FileBatchPresenter fileBatchPresenter = new FileBatchPresenter();
        loading.show();
        fileBatchPresenter.getFileBatch(files, "IMAGE", fileArray -> {
            fileBeanList = fileArray.toJavaList(FileBean.class);
            ArrayList<PathBean> list = new ArrayList<>();
            for (int i = 0; i < fileBeanList.size(); i++) {
                String filePath = fileBeanList.get(i).getFilePath();
                list.add(new PathBean(filePath));
            }
        });
    }


    // 身份证 上 弹窗
    private void showBottomDialog(int type) {
        dialog = new Dialog(this, R.style.DialogTheme);
        View view = View.inflate(this, R.layout.takephoto_dialog, null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animStyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        if (type==2){
            dialog.findViewById(R.id.tv_take_pic).setVisibility(View.GONE);
        }
        // 身份证 上 拍照
        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(view1 -> {
            if (type==0){//身份证正
                getPicFromCamera(1);
            }else if(type==1){//身份证反
                getPicFromCamera(3);
            }else if (type==2){//人脸
                Intent intent = new Intent(this, PhotoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 10);
            }else if(type ==3){
                PictureSelector.create(YezhuRegisterActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .compress(true)// 是否压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dialog.dismiss();
            }
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            if (type==0) {//身份证正
                startActivityForResult(intent, 2);
            }else if (type==1){//身份证反
                startActivityForResult(intent, 4);
            }else if (type==3){
                PictureSelector.create(YezhuRegisterActivity.this)
                        .openGallery(PictureMimeType.ofImage())//类型设置
                        .maxSelectNum(maxSelectNum - selectList.size())//最多选择的图片
                        .imageSpanCount(4)//每行显示的个数
                        .compress(true)// 是否压缩
                        .imageFormat(PictureMimeType.PNG)//拍照后照片格式
                        .selectionMode(PictureConfig.MULTIPLE)//多选
                        .forResult(PictureConfig.CHOOSE_REQUEST);//回调
            }
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view13 -> dialog.dismiss());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        try {
            if (requestCode == 1||requestCode == 3) {// 身份证上拍照
                //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri uri = FileProvider.getUriForFile(YezhuRegisterActivity.this,
                                "com.jingpai.pos.fileprovider", tempFile);
//                    updateAvatar(contentUri);
//                        String path = GetPathFromUri.getTakePhotoPath(data);
                        Bitmap bitmap = null;
                        try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                            bitmap = QiniuBitmapUtils.compressImage(bitmap,0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                    Bitmap bm = getBitmap(bitmap);
                        if(requestCode==1){
                            ivAbove.setImageBitmap(bitmap);
                        }else if (requestCode==3){
                            ivBelow.setImageBitmap(bitmap);
                        }
                        loading.show();
                        fileBatchPresenter.updateFile(bitmap, fileBean -> {
                            if (fileBean == null) {
                                dialog.dismiss();
                                return;
                            }
                            if(requestCode==1){
                                filePath = fileBean.getFilePath();
                            }else if (requestCode==3){
                                filePath2 = fileBean.getFilePath();
                            }
                            loading.dismiss();
                        });
                        dialog.dismiss();
                    } else {
                        if (data==null)return;
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        File file = saveFile(bitmap);
                        Log.d(Constant.TAG, "image size first: " + (file.length() / 1024));
                        if(requestCode==1){
                            ivAbove.setImageBitmap(bitmap);
                        }else if (requestCode==3){
                            ivBelow.setImageBitmap(bitmap);
                        }
                        fileBatchPresenter.updateFile(bitmap, fileBean -> {
                            if (fileBean == null) {
                                dialog.dismiss();
                                return;
                            }
                            if(requestCode==1){
                                filePath = fileBean.getFilePath();
                            }else if (requestCode==3){
                                filePath2 = fileBean.getFilePath();
                            }
                            loading.dismiss();
                        });
                    }
                }
            }

            if (data == null) {
                return;
            }
            if (requestCode == 2||requestCode == 4) {     //身份证取相册 正2；反4
                try {
                    loading.show();
                    Uri uri = data.getData();
//                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    String path = GetPathFromUri.getPath(YezhuRegisterActivity.this, data.getData());
                    Bitmap bitmap = QiniuBitmapUtils.getImage(path,0);
                    if(requestCode==2){
                        ivAbove.setImageBitmap(bitmap);
                    }else if (requestCode==4){
                        ivBelow.setImageBitmap(bitmap);
                    }

                    fileBatchPresenter.updateFile(bitmap, fileBean -> {
                        if (fileBean == null) {
                            dialog.dismiss();
                            return;
                        }
                        if(requestCode==2){
                            filePath = fileBean.getFilePath();
                        }else if (requestCode==4){
                            filePath2 = fileBean.getFilePath();
                        }
                        loading.dismiss();
                    });
//                memberFace.setText("已登记");
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                images = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(images);
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }

            //人脸登记 拍照回调
            if (requestCode == 10) {
                byte buf[] = data.getByteArrayExtra("data");
                Bitmap bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                renlianBase = ImgUtils.smallImage(bitmap);
                tvNationalitySelect.setText("已登记");
                dialog.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public File saveFile(Bitmap bitmap) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "citylift_image_temp.jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void getPicFromCamera(int requestCode) {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(YezhuRegisterActivity.this, "com.jingpai.pos.fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, requestCode);
    }

    private Bitmap getBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setScale(0.2f, 0.2f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }
}
