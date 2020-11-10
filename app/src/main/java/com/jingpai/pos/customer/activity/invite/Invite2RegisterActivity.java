package com.jingpai.pos.customer.activity.invite;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.housemember.BuildingActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.PathBean;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.customer.mvp.presenter.show.my.FileBatchPresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.DialogUtils;
import com.jingpai.pos.customer.utils.FullyGridLayoutManager;
import com.jingpai.pos.customer.utils.ImgUtils;
import com.jingpai.pos.customer.utils.StatusBarUtil;
import com.jingpai.pos.utils.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author 86173
 * <p>
 * 邀请注册页面
 */
public class Invite2RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 到期时间
     */
    private TextView tv_time;
    private TextView tv_fanli;
    private ImageView iv_above;
    private ImageView iv_below;
    private Dialog dialog;
    private TextView yes_btn;
    private RecyclerView mRecyclerView;
    private List<LocalMedia> selectList = new ArrayList<>();
    private InvireGridImageAdapter adapter;

    private List<FileBean> fileBeanList;
    private ArrayList<File> files = new ArrayList<>();
    private String base;
    private String base1;
    private String houseId;
    private String expireTime;
    String filePath;
    String filePath2;
    private FileBatchPresenter fileBatchPresenter;
    private Invite2RegisterPresenter invite2RegisterPresenter;
    private int maxSelectNum = 5;
    private DialogUtils loading;
    private File tempFile;
    private File file;
    //相机请求码
    private static final int AVATAR_CAMERA_REQUEST_CODE = 1001;
//    private File tempFile2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_register);
        initView();
        initWidget();
        initListemer();
    }

    private void initView() {
        tv_time = findViewById(R.id.tv_time);
        tv_fanli = findViewById(R.id.tv_fanli);
        iv_above = findViewById(R.id.iv_above);
        iv_below = findViewById(R.id.iv_below);
        yes_btn = findViewById(R.id.yes_btn);
        mRecyclerView = findViewById(R.id.rl_hetong);

        Intent intent = getIntent();
        houseId = intent.getStringExtra("houseId");
        expireTime = intent.getStringExtra("expireTime");

        tv_time.setText(TextUtils.isEmpty(expireTime) ? "" : expireTime);
        StatusBarUtil.INSTANCE.setStatusBarMode(this, true, R.color.black);
        StatusBarUtil.INSTANCE.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, getResources().getColor(R.color.white));
        fileBatchPresenter = new FileBatchPresenter();
        invite2RegisterPresenter = new Invite2RegisterPresenter();
        fileBeanList = new ArrayList<>();
        loading = new DialogUtils(Invite2RegisterActivity.this,R.style.CustomDialog);
        yes_btn.setEnabled(true);
        yes_btn.setClickable(true);


        yes_btn.setOnClickListener(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                up2Info();
            }
        });
    }

    private void initListemer() {
        tv_fanli.setOnClickListener(this);
        iv_above.setOnClickListener(this);
        iv_below.setOnClickListener(this);
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
                        PictureSelector.create(Invite2RegisterActivity.this).externalPicturePreview(position, selectList);
                        break;
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private InvireGridImageAdapter.onAddPicClickListener onAddPicClickListener = () -> {
        //获取写的权限
        RxPermissions rxPermission = new RxPermissions(Invite2RegisterActivity.this);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {// 用户已经同意该权限
                        showDialog();
                    } else {
                        Toast.makeText(this, "拒绝", Toast.LENGTH_SHORT).show();
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
                PictureSelector.create(Invite2RegisterActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .compress(true)// 是否压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(Invite2RegisterActivity.this)
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_fanli:
                //暂时不需要这个功能
                break;
            case R.id.iv_above:
                showBottomDialog();
                break;
            case R.id.iv_below:
                showBottomDialog2();
                break;
//            case R.id.yes_btn:
//                up2Info();
//                break;
        }
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
        if (files.size()==0){
            ToastUtils.INSTANCE.showLongToast(this,"租赁合同图片不能为空");
            return;
        }
        FileBatchPresenter fileBatchPresenter = new FileBatchPresenter();
        loading.show();
        yes_btn.setEnabled(false);
        yes_btn.setClickable(false);
        fileBatchPresenter.getFileBatch(files, "IMAGE", fileArray -> {
            fileBeanList = fileArray.toJavaList(FileBean.class);
            ArrayList<PathBean> list = new ArrayList<>();
            for (int i = 0; i < fileBeanList.size(); i++) {
                String filePath = fileBeanList.get(i).getFilePath();
                list.add(new PathBean(filePath));
            }
            TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
            baseRequest.put("houseId", TextUtils.isEmpty(houseId) ? "" : houseId);
            baseRequest.put("contractFiles", list);
            baseRequest.put("backUrl", filePath2);
            baseRequest.put("frontUrl", filePath);
            invite2RegisterPresenter.updata(baseRequest, string -> {
                if (string == null) return;
                ToastUtils.INSTANCE.showLongToast(this, "提交成功");
                startActivity(new Intent(this, BuildingActivity.class));
                loading.dismiss();
                finish();
            });
        });
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
            getPicFromCamera(1);
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 1);
//            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            photoPath = Environment.getExternalStorageDirectory() + "/text/text.jpg";
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
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 3);
            getPicFromCamera(3);
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
        List<LocalMedia> images;
        if (requestCode == 1){
            //调用相机后返回
            if (resultCode == RESULT_OK) {
                //用相机返回的照片去调用剪裁也需要对Uri进行处理
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri uri = FileProvider.getUriForFile(Invite2RegisterActivity.this,
                            "com.jingpai.pos.fileprovider", tempFile);
//                    updateAvatar(contentUri);
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bm = getBitmap(bitmap);
                    iv_above.setImageBitmap(bm);
                    loading.show();
                    base = ImgUtils.smallImage(bm);
                    fileBatchPresenter.updateFile(bm, fileBean -> {
                        if (fileBean == null) {
                            return;
                        }
                        filePath = fileBean.getFilePath();
                        loading.dismiss();
                    });
                    dialog.dismiss();
                } else {
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        File file =saveFile( bitmap);
                        Log.d(Constant.TAG, "image size first: " + (file.length() / 1024));
                        base = ImgUtils.smallImage(bitmap);
                        iv_above.setImageBitmap(bitmap);
                        fileBatchPresenter.updateFile(bitmap, fileBean -> {
                            if (fileBean == null) {
                                return;
                            }
                            filePath = fileBean.getFilePath();
                            loading.dismiss();
                        });

                    }catch (Exception e){}
                }
            }
        }
        if (requestCode == 3){
            //调用相机后返回
            if (resultCode == RESULT_OK) {
                //用相机返回的照片去调用剪裁也需要对Uri进行处理
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri uri = FileProvider.getUriForFile(Invite2RegisterActivity.this,
                            "com.jingpai.pos.fileprovider", tempFile);
//                    updateAvatar(contentUri);
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bm = getBitmap(bitmap);
                    iv_below.setImageBitmap(bm);
                    loading.show();
                    base = ImgUtils.smallImage(bm);
                    fileBatchPresenter.updateFile(bm, fileBean -> {
                        if (fileBean == null) {
                            return;
                        }
                        filePath2 = fileBean.getFilePath();
                        loading.dismiss();
                    });
                    dialog.dismiss();
                } else {
                    try {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        File file =saveFile( bitmap);
                        Log.d(Constant.TAG, "image size first: " + (file.length() / 1024));
                        base = ImgUtils.smallImage(bitmap);
                        iv_above.setImageBitmap(bitmap);
                        fileBatchPresenter.updateFile(bitmap, fileBean -> {
                            if (fileBean == null) {
                                return;
                            }
                            filePath2 = fileBean.getFilePath();
                            loading.dismiss();
                        });
                    }catch (Exception e){}

                }
            }
        }
        if (data == null) {
            return;
        }
//        if (requestCode == 1) {
//            loading.show();
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            File file =saveFile( bitmap);
//            Log.d(Constant.TAG, "image size first: " + (file.length() / 1024));
//            base = ImgUtils.smallImage(bitmap);
//            iv_above.setImageBitmap(bitmap);
//            fileBatchPresenter.updateFile(bitmap, fileBean -> {
//                if (fileBean == null) {
//                    return;
//                }
//                filePath = fileBean.getFilePath();
//                loading.dismiss();
//            });
//        }
        if (requestCode == 2) {
            try {
                loading.show();
                Uri uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                iv_above.setImageBitmap(bitmap);
                base = ImgUtils.smallImage(bitmap);
                fileBatchPresenter.updateFile(bitmap, fileBean -> {
                    if (fileBean == null) {
                        return;
                    }
                    filePath = fileBean.getFilePath();
                    loading.dismiss();
                });
//                memberFace.setText("已登记");
                dialog.dismiss();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
//        if (requestCode == 3) {
//            loading.show();
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            base1 = ImgUtils.bitmapToBase64(bitmap);
//            iv_below.setImageBitmap(bitmap);
//            fileBatchPresenter.updateFile(bitmap, fileBean -> {
//                if (fileBean == null) {
//                    return;
//                }
//                filePath2 = fileBean.getFilePath();
//                loading.dismiss();
//            });
//            dialog.dismiss();
//        }
        if (requestCode == 4) {
            try {
                loading.show();
                Uri uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                iv_below.setImageBitmap(bitmap);
//                base1 = ImgUtils.smallImage(bitmap);
                fileBatchPresenter.updateFile(bitmap, fileBean -> {
                    if (fileBean == null) {
                        return;
                    }
                    filePath2 = fileBean.getFilePath();
                    loading.dismiss();
                });
                dialog.dismiss();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }



        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            images = PictureSelector.obtainMultipleResult(data);
            selectList.addAll(images);
            adapter.setList(selectList);
            adapter.notifyDataSetChanged();
        }
    }


    @SuppressLint("CheckResult")
    private void updateAvatar(Uri uri) {
        RequestOptions options = new RequestOptions();
        options.override(400, 400);
        Glide.with(this).asBitmap().load(uri).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                saveImage("crop", resource);
                RequestBody requestBody = MultipartBody.create(MediaType.parse("multipart/form-data"), file);

                Log.d(Constant.TAG, "image size: " + (file.length() / 1024));

                RequestBody fb = RequestBody.create(MediaType.parse("fileType"), "IMAGE");
                MultipartBody.Part images = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//                personalPresenterImp.fileData(images, fb, Invite2RegisterActivity.this::file);
            }
        });
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
            Uri contentUri = FileProvider.getUriForFile(Invite2RegisterActivity.this, "com.jingpai.pos.fileprovider", tempFile);
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
