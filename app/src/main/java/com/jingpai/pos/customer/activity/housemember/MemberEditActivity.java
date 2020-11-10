package com.jingpai.pos.customer.activity.housemember;
/*
 * 添加成员页面
 * */

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.PhotoActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.BuildingBean;
import com.jingpai.pos.customer.bean.show.InfoBean;
import com.jingpai.pos.customer.bean.show.MemberBean;
import com.jingpai.pos.customer.views.CustomToolBar;
import com.jingpai.pos.customer.mvp.presenter.show.home.MemberPresenterImp;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.CommonPopWindow;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.utils.DialogUtils;
import com.jingpai.pos.customer.utils.ImgUtils;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.PickerScrollNewView;
import com.jingpai.pos.customer.utils.PickerScrollView;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.views.TwoButtonDialog;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

public class MemberEditActivity extends BaseActivity implements CommonPopWindow.ViewClickListener {

    @BindView(R.id.house_name)
    TextView houseName;
    @BindView(R.id.member_iv)
    ImageView memberIv;
    @BindView(R.id.member_et_name)
    EditText memberEtName;
    @BindView(R.id.underline_name)
    View underlineName;
    @BindView(R.id.member_et_phone)
    EditText memberEtPhone;
    @BindView(R.id.underline_phone)
    View underlinePhone;
    @BindView(R.id.identity_card)
    EditText identityCard;

    @BindView(R.id.member_face)
    TextView memberFace;

    @BindView(R.id.add_member_btn)
    TextView addMemberBtn;
    @BindView(R.id.tv_id_type)
    TextView tvIdType;
    @BindView(R.id.underline_id_type)
    View underlineIdType;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    TextView imageBtn;
    TextView cancel;
    PickerScrollView addressSelector;
    PickerScrollNewView pickerScrollNewView;
    @BindView(R.id.toolbar)
    CustomToolBar toolbar;
    @BindView(R.id.tv_id_type_tip)
    TextView tvIdTypeTip;
    @BindView(R.id.ll_chose_id_type)
    LinearLayout llChoseIdType;
    @BindView(R.id.tv_face_tip)
    TextView tvFaceTip;
    @BindView(R.id.register)
    ConstraintLayout register;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_invite_type)
    TextView tvInviteType;

    private String base;
    private String type = "FAMILY";
    //人员类型(OWNER:产权人；TENANT:租户；FAMILY:家属)
    private String operatorType = "OWNER";
    private Dialog dialog;
    private String myHouseName;
    private String info;
    private String houseId;
    private List<BuildingBean.DataBean> data;
    private List<InfoBean> infoBeans;
    private String communityId;
    private MemberPresenterImp memberPresenterImp;
    //证件类型
    private String certificateType;
    private MemberBean memberBean;
    private DialogUtils loading;

    @Override
    protected int getLayout() {
        return R.layout.activity_member_edit;
    }


    @Override
    protected void initData() {
        infoBeans = new ArrayList<>();
        infoBeans.clear();
        for (int i = 0; i < 3; i++) {
            InfoBean infoBean = new InfoBean();
            if (i == 1) {
                infoBean.setInfo("身份证");
            }
            if (i == 2) {
                infoBean.setInfo("护照");
            }
            if (i == 0) {
                infoBean.setInfo("其他证件");
            }
            infoBeans.add(infoBean);
        }

        memberPresenterImp = new MemberPresenterImp();
        //获取小区ID
        communityId = LocalCache.getCurrentCommunityId();
        //请求房屋信息
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("communityId", communityId);
        memberPresenterImp.HouseQueryData(baseRequest, this::memberData);
        memberBean = (MemberBean) getIntent().getSerializableExtra("memberBean");
        if (memberBean != null) {

            tvInviteType.setText(memberBean.getTypeName());
            tvInviteType.setCompoundDrawables(null, null, null, null);
            tvInviteType.setEnabled(false);
            memberEtName.setText(memberBean.getName());
            if (TextUtils.isEmpty(memberBean.getPhone())) {
                llPhone.setVisibility(View.GONE);
                underlinePhone.setVisibility(View.GONE);
            } else {
                String phone = CommonUtil.strEncry(memberBean.getPhone(), 3, 6);
                memberEtPhone.setText(phone);
                memberEtPhone.setEnabled(false);
            }

            if (TextUtils.isEmpty(memberBean.getCertificateTypeName())) {
                llChoseIdType.setVisibility(View.GONE);
            } else {
                tvIdType.setText(memberBean.getCertificateTypeName());
                tvIdType.setEnabled(false);
                tvIdType.setCompoundDrawables(null, null, null, null);
                String cardNo = "";
                if ("身份证".equals(memberBean.getCertificateTypeName())){
                    cardNo = CommonUtil.strEncry(memberBean.getIdCard(), 6, 13);
                }else{
                    int length = memberBean.getIdCard().length();
                    if (length==1||length==2){
                        cardNo=memberBean.getIdCard();
                    }else if (length<=5){
                        cardNo = CommonUtil.strEncry(memberBean.getIdCard(), 1, length-2);
                    }else {
                        int index = (length-4)/2;
                        cardNo = CommonUtil.strEncry(memberBean.getIdCard(), index, index+3);
                    }
                }

                identityCard.setText(cardNo);
                identityCard.setEnabled(false);
                identityCard.setVisibility(View.VISIBLE);
                underlineIdType.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(memberBean.getFaceId())){
                memberFace.setText("已登记");
            }
            if (!memberBean.getEditable()){
                memberEtName.setEnabled(false);
                register.setEnabled(false);
                memberFace.setTextColor(getResources().getColor(R.color.text_97));
                memberFace.setCompoundDrawables(null, null, null, null);
                addMemberBtn.setVisibility(View.GONE);
            }
            if (memberBean.getDeletable()){
                toolbar.setRightTitleVisibility(true);
            }else{
                toolbar.setRightTitleVisibility(false);
            }
        }

        loading = new DialogUtils(MemberEditActivity.this,R.style.CustomDialog);
    }

    public void memberData(JSONArray jsonArray) {
        data = jsonArray.toJavaList(BuildingBean.DataBean.class);

        if (!data.isEmpty()) {
            String houseName = getIntent().getStringExtra("houseName");
            if (TextUtils.isEmpty(houseName)) {
                BuildingBean.DataBean house = data.get(0);
                myHouseName = house.getHouseName();
                houseId = house.getHouseId();
                operatorType = house.getType();
                this.houseName.setText(myHouseName);
                return;
            }

            myHouseName = houseName;
            houseId = getIntent().getStringExtra("houseId");
            this.houseName.setText(myHouseName);
            memberIv.setVisibility(View.GONE);
        }
    }

    public void memberAdd(Object succeed) {
        loading.dismiss();
        if (succeed == null) {
            return;
        }
        //1、使用Dialog、设置style
        Dialog dialog = new Dialog(MemberEditActivity.this, R.style.DialogTheme);
        dialog.setCanceledOnTouchOutside(false);
        //2、设置布局
        View view = View.inflate(MemberEditActivity.this, R.layout.member_dialog, null);
        TextView tv_tips = (TextView) view.findViewById(R.id.tv_tips);
        if (memberBean != null) {
            tv_tips.setText("您已成功修改成员信息");
        }
        dialog.setContentView(view);

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    setResult(RESULT_OK);
                    finish();
                    return true;
                }
                return false;
            }
        });
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                setResult(RESULT_OK);
                finish();
            }
        });

    }
    public void memberDelete(Object succeed) {
        if (succeed == null) {
            return;
        }
        ToastUtils.INSTANCE.showToast("删除成功");
        setResult(RESULT_OK);
        finish();
    }
    @OnClick(R.id.tv_invite_type)
    public void setTvInviteType(View v){
        setInviteSelectorPopup(v);
    }
    @OnClick(R.id.tv_right_btn)
    public void setDeleteMember(View v){
        initDialog();
    }

    @OnClick({R.id.add_member_btn, R.id.register,
            R.id.member_et_name, R.id.member_iv, R.id.ll_chose_id_type})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.add_member_btn:
                String name = memberEtName.getText().toString();

                if (name.isEmpty()) {
                    ToastUtils.INSTANCE.showToast("请输入姓名");
                    break;
                }

                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();

                baseRequest.put("face", base);
                baseRequest.put("houseId", memberBean.getHouseId());
                baseRequest.put("name", name);
                String types = getIntent().getStringExtra("type");
                if (TextUtils.isEmpty(types)) {
                    types = operatorType;
                }
                baseRequest.put("operatorType", types);
                baseRequest.put("type", memberBean.getType());
                if (memberBean != null) {
                    baseRequest.put("memberRelationshipId", memberBean.getMemberRelationshipId());
                    loading.show();
                    memberPresenterImp.MemberUpdate(baseRequest, this::memberAdd);
                }
                break;

            case R.id.register:
                showBottomDialog();
                break;

            case R.id.member_iv:
                if (data.size() == 0) {
                    Toast.makeText(this, "当前小区尚未添加房屋", Toast.LENGTH_SHORT).show();
                } else {
                    setAddressSelectorPopup(v);
                }
                break;
            case R.id.ll_chose_id_type:
                if (memberBean == null) {
                    setAddressSelectorPopup1(v);
                }
                break;
        }
    }

    /**
     * 初始化删除窗口
     */
    private void initDialog() {
        final TwoButtonDialog deleteDialog = new TwoButtonDialog(MemberEditActivity.this);
        deleteDialog.setDialogMsg("是否删除该成员");
//        deleteDialog.setDialogTitle(R.string.talked_people_delete_title);
//        deleteDialog.setDialogCancel(R.string.btn_text_cancel);
//        deleteDialog.setDialogOk(R.string.btn_delete);

        deleteDialog.setOkListener(new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("memberRelationshipId", memberBean.getMemberRelationshipId());
                memberPresenterImp.MemberDelete(baseRequest, MemberEditActivity.this::memberDelete);
            }
        });
        deleteDialog.show();
    }
    //户主关系
    private void setInviteSelectorPopup(View v) {
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
                        data.add(new InfoBean("家人"));
                        data.add(new InfoBean("租客"));
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
                                tvInviteType.setText(selectedInfo.getInfo());

                                if ("家人".equals(selectedInfo.getInfo())){
                                    //家人
                                    type = "FAMILY";
                                    tvTip.setVisibility(View.VISIBLE);
                                    memberEtName.setHint("请输入成员姓名");
                                    memberEtPhone.setHint("请输入成员手机");
                                    identityCard.setHint("请输入成员证件号");
                                }else{
                                    //租客
                                    type = "TENANT";
                                    tvTip.setVisibility(View.GONE);
                                    memberEtName.setHint("请输入租客姓名");
                                    memberEtPhone.setHint("请输入租客手机");
                                    identityCard.setHint("请输入租客证件号（必填）");
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
            Intent intent = new Intent(MemberEditActivity.this, PhotoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, 10);
            dialog.dismiss();
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(intent, 1);
        });
        dialog.findViewById(R.id.tv_take_pic).setVisibility(View.GONE);
        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view13 -> dialog.dismiss());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == 1) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            base = ImgUtils.smallImage(bitmap);
            memberFace.setText("已登记");
            dialog.dismiss();
        }
        if (requestCode == 2) {
            try {
                Uri uri = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//                base = ImgUtils.smallImage(bitmap);
                memberFace.setText("已登记");
                dialog.dismiss();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 10) {
            byte buf[] = data.getByteArrayExtra("data");
            Bitmap bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            base = ImgUtils.smallImage(bitmap);
            memberFace.setText("已登记");
            dialog.dismiss();
        }
    }

    /**
     * 将选择器放在底部弹出框
     *
     * @param v
     */
    private void setAddressSelectorPopup(View v) {
        int screenHeigh = getResources().getDisplayMetrics().heightPixels;
        CommonPopWindow.newBuilder()
                .setView(R.layout.pop_picker_selector_bottom)
                .setBackgroundDrawable(new BitmapDrawable())
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT, Math.round(screenHeigh * 0.3f))
                .setViewOnClickListener(MemberEditActivity.this)
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
                .setViewOnClickListener(MemberEditActivity.this)
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(MemberEditActivity.this)
                .showAsBottom(v);
    }

    @Override
    public void getChildView(final PopupWindow mPopupWindow, View view, int mLayoutResId) {
        switch (mLayoutResId) {
            case R.layout.pop_picker_selector_bottom:
                imageBtn = view.findViewById(R.id.img_guanbi);
                cancel = view.findViewById(R.id.img_cancel);
                addressSelector = view.findViewById(R.id.address);

                // 设置数据，默认选择第一条
                addressSelector.setData(data);
                //滚动监听
                addressSelector.setOnSelectListener(pickers -> {
                    myHouseName = pickers.getHouseName();
                    houseId = pickers.getHouseId();
                    operatorType = pickers.getType();
                    Log.e("houseId", houseId);
                });
                //完成按钮
                imageBtn.setOnClickListener(v -> {
                    mPopupWindow.dismiss();
                    houseName.setText(myHouseName);
                });
                //完成按钮
                cancel.setOnClickListener(v -> {
                    mPopupWindow.dismiss();
                });
                break;
            case R.layout.pop_picker_selector_bottom1:
                imageBtn = view.findViewById(R.id.img_guanbi);
                cancel = view.findViewById(R.id.img_cancel);
                pickerScrollNewView = view.findViewById(R.id.address);

                // 设置数据，默认选择第一条
                pickerScrollNewView.setData(infoBeans);
                //滚动监听
                pickerScrollNewView.setOnSelectListener(pickers -> {
                    info = pickers.getInfo();
                });
                //完成按钮
                imageBtn.setOnClickListener(v -> {
                    mPopupWindow.dismiss();
                    if (TextUtils.isEmpty(info)) {
                        info = "身份证";
                    }
                    tvIdType.setText(info);
                    underlineIdType.setVisibility(View.VISIBLE);
                    identityCard.setVisibility(View.VISIBLE);
                    try {
                        if (myHouseName.equals("身份证")) {
                            certificateType = "ID_CARD";
                        } else if (myHouseName.equals("护照")) {
                            certificateType = "PASSPORT";
                        } else if (myHouseName.equals("其他证件")) {
                            certificateType = "OTHER";
                        }
                    } catch (Exception e) {
                        Log.d("mma", e.toString());
                    }


                });
                //完成按钮
                cancel.setOnClickListener(v -> {
                    mPopupWindow.dismiss();
                });
                break;
        }
    }
}