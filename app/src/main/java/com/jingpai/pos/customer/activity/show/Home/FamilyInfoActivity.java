package com.jingpai.pos.customer.activity.show.Home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
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
import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.PhotoActivity;
import com.jingpai.pos.customer.activity.show.My.NewNumberActivity;
import com.jingpai.pos.customer.activity.show.My.NickNameActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.bean.User;
import com.jingpai.pos.customer.bean.show.FileBean;
import com.jingpai.pos.views.TipsDialog;
import com.jingpai.pos.customer.component.dialog.TwoChooseDialog;
import com.jingpai.pos.presenter.login.UserPresenter;
import com.jingpai.pos.customer.mvp.presenter.show.my.PersonalPresenterImp;
import com.jingpai.pos.customer.mvp.presenter.show.my.SexChangePresenterImp;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.DataCleanManager;
import com.jingpai.pos.customer.utils.ImgUtils;
import com.jingpai.pos.customer.utils.Intents;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.MyGson;
import com.jingpai.pos.utils.ToastUtils;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 时间: 2020/2/5
 * 功能:个人中心页面
 */
public class FamilyInfoActivity extends BaseActivity {
    //相册请求码
    private static final int AVATAR_ALBUM_REQUEST_CODE = 1001;
    //相机请求码
    private static final int AVATAR_CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int AVATAR_CROP_REQUEST_CODE = 3;

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 4;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 5;
    //相机请求码
    private static final int NEW_CAMERA_REQUEST_CODE = 10;

    @BindView(R.id.iv_personal_image)
    ImageView ivPersonalImage;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_my_phone)
    TextView tvMyPhone;
    @BindView(R.id.tv_face_state)
    TextView tvFaceState;
    @BindView(R.id.tv_document_type)
    TextView tv_document_type;
    @BindView(R.id.tv_document_num)
    TextView tv_document_num;

    private File tempFile;
    private File file;
    private String filePath;
    private Dialog dialog;

    private PersonalPresenterImp personalPresenterImp;

    private UserPresenter userPresenter;


    @Override
    protected int getLayout() {
        return R.layout.activity_family_info;
    }

    @Override
    protected void initData() {
        personalPresenterImp = new PersonalPresenterImp();
        userPresenter = new UserPresenter();
        String avatar = LocalCache.getUserAvatar();
        if (StringUtils.isEmpty(avatar)) {
            Glide.with(FamilyInfoActivity.this).load(R.drawable.ic_headportraitdefault_my).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivPersonalImage);
        } else {
            Glide.with(FamilyInfoActivity.this).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivPersonalImage);
        }

        tvUserName.setText(LocalCache.getUserName());

        userPresenter.getUserInfo(user -> {
            setUserCache(user);
        });
    }

    /**
     * startActivityForResult执行后的回调方法，接收返回的图片
     *
     * @param requestCode 请求码
     * @param resultCode 返回码
     * @param data 数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AVATAR_CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(FamilyInfoActivity.this, "com.jingpai.pos.fileprovider", tempFile);
                        updateAvatar(contentUri);
                    } else {
                        updateAvatar(Uri.fromFile(tempFile));
                    }
                }
                break;
            case AVATAR_ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    updateAvatar(uri);
                }
                break;
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(FamilyInfoActivity.this, "com.jingpai.pos.fileprovider", tempFile);
                        updateFace(contentUri);
                    } else {
                        updateFace(Uri.fromFile(tempFile));
                    }
                }
                break;
            case ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    updateFace(uri);
                }
                break;
                case NEW_CAMERA_REQUEST_CODE:
                    if (data != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        String faceData = ImgUtils.smallImage(bitmap);
                        userPresenter.updateFace(faceData, result -> {
                            updateUserInfo();
                        });
                    }
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void updateAvatar(Uri uri) {
        RequestOptions options = new RequestOptions();
        options.override(400, 400);
        Glide.with(this).asBitmap().load(uri).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                saveImage("crop", resource);
                RequestBody requestBody = MultipartBody.create(MediaType.parse("multipart/form-data"), file);

                Log.d(Constant.TAG, "image size: " + (file.length() / 1024));

                RequestBody fb = RequestBody.create(MediaType.parse("fileType"), "IMAGE");
                MultipartBody.Part images = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                personalPresenterImp.fileData(images, fb, FamilyInfoActivity.this::file);
            }
        });
    }

    /**
     * 从相册获取图片
     */
    private void getPicFromAlbm(int requestCode) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, requestCode);
    }

    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, AVATAR_CROP_REQUEST_CODE);
    }

    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //上传头像 返回
    public void personalData(String succeed) {
        if (succeed == null) {
            ToastUtils.INSTANCE.showToast(FamilyInfoActivity.this, "更换失败，请稍后重试");
        } else {
            LocalCache.saveUserAvatar(filePath);
            Glide.with(FamilyInfoActivity.this).load(filePath).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivPersonalImage);
            ToastUtils.INSTANCE.showToast(FamilyInfoActivity.this, "更换成功");
        }
    }
    //上传
    public void file(String succeed) {
        if (!StringUtils.isEmpty(succeed)) {
            FileBean fileBean = MyGson.mGson(succeed, FileBean.class);
            filePath = fileBean.getFilePath();
            TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
            baseRequest.put("filePath", filePath);
            baseRequest.put("fileType", fileBean.getFileType());
            baseRequest.put("id", fileBean.getId());
            personalPresenterImp.avatarData(baseRequest, this::personalData);
            dialog.dismiss();
        }
    }
    TwoChooseDialog twoChooseDialog;
    @OnClick({R.id.iv_back, R.id.ll_sex, R.id.ll_user_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_sex:
                changeSex();
                break;
            case R.id.ll_user_name:
                Intents.getInstence().intent(FamilyInfoActivity.this, NickNameActivity.class);
                break;
        }
    }

    private void changeSex() {
        if (twoChooseDialog==null){
            twoChooseDialog=new TwoChooseDialog(this);
        }
        twoChooseDialog.show();
        twoChooseDialog.setTextOne("男");
        twoChooseDialog.setTextTwo("女");
        SexChangePresenterImp sexChangePresenterImp=new SexChangePresenterImp();
        twoChooseDialog.setTextOneClick(v -> {
            sexChangePresenterImp.modifySex("1",null);
            LocalCache.saveUserSex(1);
            tvSex.setText("男");
            twoChooseDialog.dismiss();
        });
        twoChooseDialog.setTextTwoClick(v -> {
            sexChangePresenterImp.modifySex("2",null);
            LocalCache.saveUserSex(2);
            tvSex.setText("女");
            twoChooseDialog.dismiss();

        });
    }

    @OnClick(R.id.iv_personal_image)
    public void openAvatar() {
        showBottomDialog(AVATAR_CAMERA_REQUEST_CODE, AVATAR_ALBUM_REQUEST_CODE);
    }

    @OnClick(R.id.ll_address)
    public void openAddress() {
        openShopPage("member/address/addresslist");
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = LocalCache.getUser();
        if (user != null) {
            tvSex.setText(user.getSex().intValue()==1?"男":user.getSex().intValue()==2?"女":"未知");
            tvUserName.setText(user.getName());
        }

        String phone = user.getPhone();
        String substring = phone.substring(3, 7);
        tvMyPhone.setText(phone.replace(substring,"****"));
        String avatar = user.getAvatar();
        if (StringUtils.isEmpty(avatar)) {
            Glide.with(FamilyInfoActivity.this).load(R.drawable.ic_headportraitdefault_my).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivPersonalImage);
        } else {
            Glide.with(FamilyInfoActivity.this).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivPersonalImage);
        }

        refreshFaceState(user);
    }

    private void refreshFaceState(User user) {
        if (StringUtils.isEmpty(user.getFaceId())) {
            tvFaceState.setText(R.string.personal_center_no_face);
        } else {
            tvFaceState.setText(R.string.personal_center_exist_face);
        }
    }

    @OnClick(R.id.tv_log_out)
    public void onTvLogOutClicked() {
        TipsDialog tipsDialog=new TipsDialog(this,"确定退出当前账号?");
        tipsDialog.show();
        tipsDialog.setOnOkClick(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                tipsDialog.dismiss();
                gotoLogin();
            }
        });
    }

    @OnClick(R.id.ll_change_password)
    public void toChangePassword() {
        startActivity(new Intent(FamilyInfoActivity.this, NewNumberActivity.class));
    }

    private void setUserCache(User user) {
        User localUser = LocalCache.getUser();
        localUser.setFaceId(user.getFaceId());
        localUser.setAvatar(user.getAvatar());
        localUser.setPhone(user.getPhone());
        localUser.setName(user.getName());
        localUser.setSex(user.getSex());
        LocalCache.saveUser(localUser);
        refreshFaceState(localUser);
    }


    private void updateUserInfo() {
        userPresenter.getUserInfo(user -> {
            if (StringUtils.isEmpty(user.getFaceId())) {
                com.blankj.utilcode.util.ToastUtils.showShort("登记失败，请稍候重试");
                return;
            }

            setUserCache(user);
            com.blankj.utilcode.util.ToastUtils.showShort("登记成功");
        });
    }

    private void updateFace(Uri uri) {
        RequestOptions options = new RequestOptions();
        options.override(400, 400);
        Glide.with(this).asBitmap().load(uri).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Bitmap bitmap = resource;
                String faceData = ImgUtils.smallImage(bitmap);
                userPresenter.updateFace(faceData, result -> {
                    updateUserInfo();
                });
            }
        });
    }

    @OnClick(R.id.ll_face)
    public void openFaceDialog() {
        showBottomDialog(CAMERA_REQUEST_CODE, ALBUM_REQUEST_CODE);
    }

    public void showBottomDialog(int cameraRequestCode, int albmRequestCode) {
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
//        dialog.findViewById(R.id.tv_take_photo).setVisibility(View.GONE);
        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getPicFromCamera(cameraRequestCode);
                Intent intent =new Intent(FamilyInfoActivity.this, PhotoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 10);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_take_pic).setVisibility(View.GONE);
        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPicFromAlbm(albmRequestCode);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void getPicFromCamera(int requestCode) {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(FamilyInfoActivity.this, "com.jingpai.pos.fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, requestCode);
    }

    public void cleanCache() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置提示框的标题
        builder.setMessage("你确定清空缓存吗").
                // 设置确定按钮
                        setPositiveButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataCleanManager.cleanApplicationData(FamilyInfoActivity.this);
                        Toast.makeText(FamilyInfoActivity.this, "清除成功", Toast.LENGTH_SHORT).show();
                    }
                }).
                // 设置取消按钮,null是什么都不做，并关闭对话框
                        setNegativeButton("取消", null);
        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();
    }
}