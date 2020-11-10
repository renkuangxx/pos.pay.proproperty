package com.jingpai.pos.customer.activity.show.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hb.dialog.dialog.LoadingDialog;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.GridImageAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.customer.mvp.presenter.show.home.FeedbackPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.my.FileBatchPresenter;
import com.jingpai.pos.customer.utils.FullyGridLayoutManager;
import com.jingpai.pos.customer.network.NetWorkUtil;
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
 * 时间: 2020/2/28
 * 功能:
 */
public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.et_problem)
    EditText etProblem;
    private int maxSelectNum = 9;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private PopupWindow pop;
    private ArrayList<File> files = new ArrayList<>();
    private List<FileBean> fileBeanList;
    private String et_problem;
    private GridImageAdapter adapter;
    private LoadingDialog loadingDialog;
    private List<LocalMedia> selectList = new ArrayList<>();
    private Dialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.feedback_activity;
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if (title != null) {
            mToolBar.setTitle(title);
        }

        etProblem.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        //文本显示的位置在EditText的最上方
        etProblem.setGravity(Gravity.TOP);
        //改变默认的单行模式
        etProblem.setSingleLine(false);
        //水平滚动设置为False
        etProblem.setHorizontallyScrolling(false);

        loadingDialog = new LoadingDialog(FeedbackActivity.this);
        loadingDialog.setCanceledOnTouchOutside(false);

        initWidget();
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
                        // 预览图片 可自定长按保存路径
                        PictureSelector.create(FeedbackActivity.this).externalPicturePreview(position, selectList);
                        break;
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = () -> {
        //获取写的权限
        RxPermissions rxPermission = new RxPermissions(FeedbackActivity.this);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {// 用户已经同意该权限
                        showDialog();
                    } else {
                        Toast.makeText(FeedbackActivity.this, "拒绝", Toast.LENGTH_SHORT).show();
                    }
                });
    };

    private void showDialog() {
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
        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(FeedbackActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .compress(true)// 是否压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(FeedbackActivity.this)
                        .openGallery(PictureMimeType.ofImage())//类型设置
                        .maxSelectNum(maxSelectNum)//最多选择的图片
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

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(images);
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick(R.id.tv_serve_btn)
    public void onTvServeBtnClicked() {
        loadingDialog.show();
        for (int i = 0; i < selectList.size(); i++) {
            String path = selectList.get(i).getCompressPath();
            File file = new File(path);
            files.add(file);
        }
        et_problem = etProblem.getText().toString();
        if (et_problem.equals("")) {
            ToastUtils.INSTANCE.showToast("请填写相关评价或建议后再提交哦!");
        } else {
            if (files.size() == 0) {
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("clientType", "ANDROID");
                baseRequest.put("content", et_problem);
                FeedbackPresenter feedbackPresenter = new FeedbackPresenter();
                feedbackPresenter.getGuardList(baseRequest, jsonArray -> {
                    Toast.makeText(FeedbackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                    finish();
                });
            } else {
                loadingDialog.show();
                request();
            }
        }
    }

    public void request() {
        FileBatchPresenter fileBatchPresenter = new FileBatchPresenter();
        fileBatchPresenter.getFileBatch(files, "IMAGE", fileArray -> {
            fileBeanList = fileArray.toJavaList(FileBean.class);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < fileBeanList.size(); i++) {
                String fileId = fileBeanList.get(i).getId();
                list.add(fileId);
            }
            TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
            baseRequest.put("clientType", "ANDROID");
            baseRequest.put("content", et_problem);
            baseRequest.put("imageList", list);
            FeedbackPresenter feedbackPresenter = new FeedbackPresenter();
            feedbackPresenter.getGuardList(baseRequest, jsonArray -> {
                Toast.makeText(FeedbackActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            });
        });
    }

}