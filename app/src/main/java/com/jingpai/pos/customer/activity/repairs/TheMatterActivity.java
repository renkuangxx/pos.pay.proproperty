package com.jingpai.pos.customer.activity.repairs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.show.Home.BackResult;
import com.jingpai.pos.customer.adapter.GridImageAdapter;
import com.jingpai.pos.customer.adapter.ProblemTypeAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.BuildingBean;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.customer.bean.show.ReportBean;
import com.jingpai.pos.customer.mvp.presenter.show.my.FileBatchPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.my.ReportTypePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.CommonPopWindow;
import com.jingpai.pos.customer.utils.FullyGridLayoutManager;
import com.jingpai.pos.customer.utils.Intents;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.PickerScrollView;
import com.jingpai.pos.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 时间: 2020/2/18
 * 功能: 小区报事
 */
public class TheMatterActivity extends BaseActivity implements CommonPopWindow.ViewClickListener {

    @BindView(R.id.problem_type)
    RecyclerView problemType;
    @BindView(R.id.et_problem_description)
    EditText etProblemDescription;
    @BindView(R.id.ll_house)
    LinearLayout llHouse;
    @BindView(R.id.tv_my_house)
    TextView tvMyHouse;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private String houseId;
    private String type = "卫生";
    private String communityName;
    private ArrayList<File> files = new ArrayList<>();
    private List<FileBean> fileBeanList;
    private List<BuildingBean.DataBean> data;
    private GridImageAdapter adapter;
    private LoadingDialog loadingDialog;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 9;
    private Dialog dialog;

    private ReportTypePresenter reportTypePresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_matter;
    }

    @Override
    protected void initData() {


        reportTypePresenter = new ReportTypePresenter();
        loadingDialog = new LoadingDialog(TheMatterActivity.this);
        loadingDialog.setCanceledOnTouchOutside(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        /*
         * 请求房屋
         * 获取当前最新小区ID
         * */
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("communityId", LocalCache.getCurrentCommunityId());
        reportTypePresenter.HouseQuery(baseRequest, this::houseQueryData);
        reportTypePresenter.ReportTypeDate(this::reportTypeData);

        initWidget();
//        if (getIntent().getBooleanExtra("aiui",false)){
//            Intent intent = new Intent(TheMatterActivity.this, AiuiActivity.class);
//            startActivity(intent);
//            EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_BABX_TO_AIUI, 1));
//        }
    }

    private void initWidget() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
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
                        PictureSelector.create(TheMatterActivity.this).externalPicturePreview(position, selectList);
                        break;
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = () -> {
        //获取写的权限
        RxPermissions rxPermission = new RxPermissions(TheMatterActivity.this);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {// 用户已经同意该权限
                        showDialog();
                    } else {
                        Toast.makeText(TheMatterActivity.this, "拒绝", Toast.LENGTH_SHORT).show();
                    }
                });
    };

    private void showDialog() {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.takephoto_dialog, null);
        this.dialog.setContentView(view);
        Window window = this.dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(TheMatterActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .compress(true)// 是否压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PictureSelector.create(TheMatterActivity.this)
                        .openGallery(PictureMimeType.ofImage())//类型设置
                        .maxSelectNum(maxSelectNum-selectList.size())//最多选择的图片
                        .imageSpanCount(4)//每行显示的个数
                        .compress(true)// 是否压缩
                        .imageFormat(PictureMimeType.PNG)//拍照后照片格式
                        .selectionMode(PictureConfig.MULTIPLE)//多选
                        .forResult(PictureConfig.CHOOSE_REQUEST);//回调
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view1 -> dialog.dismiss());
    }

    // 图片选择结果回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                images = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(images);
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_right_btn, R.id.yes_btn, R.id.ll_house})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right_btn:
                Intents.getInstence().intent(this, MatterHistoryActivity.class);
                break;
            case R.id.yes_btn:
                String problemDescription = etProblemDescription.getText().toString();
                if (problemDescription.equals("")) {
                    ToastUtils.INSTANCE.showToast("请描述您的问题后再提交!");
                }
//                else if (selectList.size() == 0) {
//                    ToastUtils.INSTANCE.showToast("请上传图片!");
//                }
                else if (houseId == null) {
                    ToastUtils.INSTANCE.showToast("请选择报事房屋!");
                } else {

                    loadingDialog.show();

                    if (selectList.size() > 0 ){
                        for (int i = 0; i < selectList.size(); i++) {
                            String compressPath = selectList.get(i).getCompressPath();
                            File file = new File(compressPath);
                            files.add(file);
                        }

                        FileBatchPresenter fileBatchPresenter = new FileBatchPresenter();
                        fileBatchPresenter.getFileBatch(files, "IMAGE", fileArray -> {
                            fileBeanList = fileArray.toJavaList(FileBean.class);
                            ArrayList<String> list = new ArrayList<>();
                            for (int i = 0; i < fileBeanList.size(); i++) {
                                String fileId = fileBeanList.get(i).getId();
                                list.add(fileId);
                            }
                            TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                            baseRequest.put("description", problemDescription);
                            baseRequest.put("fileIds", list);
                            baseRequest.put("houseId", houseId);
                            baseRequest.put("type", type);
                            reportTypePresenter.ReportDate(baseRequest, this::reportData);
                        });
                    }else {
                        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                        baseRequest.put("description", problemDescription);
                        baseRequest.put("houseId", houseId);
                        baseRequest.put("type", type);
                        reportTypePresenter.ReportDate(baseRequest, this::reportData);
                    }
                }
                break;
            case R.id.ll_house:
                if (data==null||data.size() == 0) {
                    Toast.makeText(TheMatterActivity.this, "当前小区尚未添加房屋", Toast.LENGTH_SHORT).show();
                } else {
                    setAddressSelectorPopup(view);
                }
                break;
        }
    }

    //报事类型成功返回
    public void reportTypeData(JSONArray jsonArray) {
        List<String> strings = jsonArray.toJavaList(String.class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(TheMatterActivity.this, 4);
        problemType.setLayoutManager(gridLayoutManager);
        //设置适配器
        ProblemTypeAdapter adapter = new ProblemTypeAdapter(TheMatterActivity.this, strings);
        problemType.setAdapter(adapter);
        adapter.setGetListener(position -> {
            adapter.setmPosition(position);
            adapter.notifyDataSetChanged();
            type = strings.get(position);
        });
    }

    //查询房屋成功后返回
    public void houseQueryData(JSONArray jsonArray) {
        data = jsonArray.toJavaList(BuildingBean.DataBean.class);

    }

    public void reportData(ReportBean reportBean) {
        if (reportBean == null) {
            loadingDialog.dismiss();
            return;
        }

        loadingDialog.dismiss();
        Intent intent = new Intent(TheMatterActivity.this, BackResult.class);
        intent.putExtra("resultCode", 1);
        startActivity(intent);
        finish();

    }

    //底部选择器
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
                TextView imageBtn = view.findViewById(R.id.img_guanbi);
                TextView cancel = view.findViewById(R.id.img_cancel);
                PickerScrollView addressSelector = view.findViewById(R.id.address);
                // 设置数据，默认选择第一条
                addressSelector.setData(data);
                //滚动监听
                addressSelector.setOnSelectListener(new PickerScrollView.onSelectListener() {
                    @Override
                    public void onSelect(BuildingBean.DataBean pickers) {
                        communityName = pickers.getHouseName();
                        houseId = pickers.getHouseId();
                    }
                });
                //完成按钮
                imageBtn.setOnClickListener(v -> {
                    mPopupWindow.dismiss();
                    if (TextUtils.isEmpty(communityName)){
                        BuildingBean.DataBean house = data.get(0);
                        if (house != null) {
                            communityName = house.getHouseName();
                            houseId = house.getHouseId();
                        }
                    }
                    tvMyHouse.setText(communityName);
                });
                //完成按钮
                cancel.setOnClickListener(v -> {
                    mPopupWindow.dismiss();
                });
                break;
        }
    }
}