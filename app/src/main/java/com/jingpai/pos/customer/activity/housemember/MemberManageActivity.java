package com.jingpai.pos.customer.activity.housemember;
/*
 * 添加成员页面
 * */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.HouseHolderBean;
import com.jingpai.pos.customer.bean.show.BuildingBean;
import com.jingpai.pos.customer.bean.show.InfoBean;
import com.jingpai.pos.customer.views.CustomToolBar;
import com.jingpai.pos.customer.mvp.presenter.show.home.HomePresenter;
import com.jingpai.pos.customer.mvp.presenter.show.home.MemberPresenterImp;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.CommonPopWindow;
import com.jingpai.pos.customer.utils.DialogUtils;
import com.jingpai.pos.customer.utils.ImgUtils;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.PickerScrollNewView;
import com.jingpai.pos.customer.utils.PickerScrollView;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.utils.ValidatorUtil;
import com.jingpai.pos.customer.views.TwoButtonEditDialog;

import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberManageActivity extends BaseActivity implements CommonPopWindow.ViewClickListener {

    @BindView(R.id.house_name)
    TextView houseName;
    @BindView(R.id.member_iv)
    ImageView memberIv;
    @BindView(R.id.member_home)
    TextView memberHome;
    @BindView(R.id.member_guest)
    TextView memberGuest;
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
    private String roomNo;
    private HouseHolderBean houseHolderBean;
    private DialogUtils loading;
    //身份
    String identity;
    //姓名
    String memberName;
    //手机
    String memberPhone;
    //身份证
    String idCard;

    @Override
    protected int getLayout() {
        return R.layout.activity_member_manage;
    }


    @Override
    protected void initData() {
        infoBeans = new ArrayList<>();
        infoBeans.clear();
        for (int i = 0; i < 3; i++) {
            InfoBean infoBean = new InfoBean();
            if (i == 1) { infoBean.setInfo("身份证"); }
            if (i == 2) { infoBean.setInfo("护照"); }
            if (i == 0) { infoBean.setInfo("其他证件"); }
            infoBeans.add(infoBean);
        }
        memberPresenterImp = new MemberPresenterImp();
        //获取小区ID
        communityId = LocalCache.getCurrentCommunityId();
        //请求房屋信息
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("communityId", communityId);
        memberPresenterImp.HouseQueryData(baseRequest, this::memberData);
        loading = new DialogUtils(MemberManageActivity.this,R.style.CustomDialog);
    }

    //提交审核失败进入页面重新传值
    private void needFillInfo() {
        houseHolderBean = (HouseHolderBean)getIntent().getSerializableExtra("houseMemberBean");
        if(houseHolderBean==null)return;
        identity = houseHolderBean.getIdentity();
        memberName = houseHolderBean.getMemberName();
        memberPhone = houseHolderBean.getMemberPhone();
        certificateType = houseHolderBean.getCertificateType();
        roomNo = houseHolderBean.getRoomNo();
        idCard = houseHolderBean.getIdCard();
        houseId = houseHolderBean.getHouseId();
        houseName.setText(roomNo);
        if (!TextUtils.isEmpty(identity) && identity.equals("FAMILY")){          //========================邀请类型
              //            jiaren
            type = "FAMILY";
            memberGuest.setBackgroundResource(R.drawable.ic_tabunselected);
            memberHome.setBackgroundResource(R.drawable.ic_tabselected);
            tvTip.setVisibility(View.VISIBLE);

            memberHome.setTextColor(Color.parseColor("#FFFFFF"));
            memberGuest.setTextColor(Color.parseColor("#FF8A49"));
            memberEtName.setHint("请输入成员姓名");
            memberEtPhone.setHint("请输入成员手机");
            identityCard.setHint("请输入成员证件号");
        }else if ("TENANT".equals(identity)){
            //租客
            type = "TENANT";
            memberHome.setBackgroundResource(R.drawable.ic_tabunselected);
            memberGuest.setBackgroundResource(R.drawable.ic_tabselected);
            tvTip.setVisibility(View.GONE);
            memberGuest.setTextColor(Color.parseColor("#FFFFFF"));
            memberHome.setTextColor(Color.parseColor("#FF8A49"));
            memberEtName.setHint("请输入租客姓名");
            memberEtPhone.setHint("请输入租客手机");
            identityCard.setHint("请输入租客证件号（必填）");
        }
        if (!TextUtils.isEmpty(memberName)){          //==============================姓名
            //设置姓名
            memberEtName.setText(memberName);
        }
        if (!TextUtils.isEmpty(memberPhone)){          //==============================手机号
            //设置手机
            memberEtPhone.setText(memberPhone);
        }
        if (!TextUtils.isEmpty(certificateType)){          //==============================证件类型
            //设置证件类型及证件号
            if ("OTHER".equals(certificateType)){
                tvIdType.setText("其他类型");
            }else if ("PASSPORT".equals(certificateType)){
                tvIdType.setText("护照");
            }else if ("ID_CARD".equals(certificateType)){
                tvIdType.setText("身份证");
            }
            llChoseIdType.setVisibility(View.VISIBLE);
            tvIdType.setCompoundDrawables(null, null, null, null);
//            String cardNo = CommonUtil.strEncry(memberBean.getIdCard(), 6, 13);
            identityCard.setText(idCard);
            identityCard.setEnabled(true);
            identityCard.setVisibility(View.VISIBLE);
            underlineIdType.setVisibility(View.VISIBLE);

        }
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
            }else{
                myHouseName = houseName;
                houseId = getIntent().getStringExtra("houseId");
                this.houseName.setText(myHouseName);
                memberIv.setVisibility(View.GONE);
            }
        }
        //住户审核失败需要填值
        needFillInfo();
    }

    public void memberAdd(Object succeed) {
        loading.dismiss();
        if (succeed == null) {
            return;
        }
        //1、使用Dialog、设置style
        Dialog dialog = new Dialog(MemberManageActivity.this, R.style.DialogTheme);
        dialog.setCanceledOnTouchOutside(false);
        //2、设置布局
        View view = View.inflate(MemberManageActivity.this, R.layout.member_dialog, null);
        TextView tv_tips = (TextView) view.findViewById(R.id.tv_tips);
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
//                setGameTimes();
                finish();
            }
        });

    }

    @OnClick({R.id.add_member_btn, R.id.member_guest, R.id.member_home, R.id.register,
            R.id.member_et_name, R.id.member_iv, R.id.ll_chose_id_type})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.add_member_btn:
                String name = memberEtName.getText().toString();
                String phone = memberEtPhone.getText().toString();
                String identityCards = identityCard.getText().toString();
                if (name.isEmpty()) {
                    ToastUtils.INSTANCE.showToast("请输入姓名");
                    return;
                }
                if ("TENANT".equals(type)) {
                    //租客
                    if (phone.isEmpty()) {
                        ToastUtils.INSTANCE.showToast("请输入手机号");
                        return;
                    }
                    if (!ValidatorUtil.isMobile(phone)) {
                        ToastUtils.INSTANCE.showToast("手机号输入有误");
                        return;
                    }
                    if (identityCards.isEmpty()) {
                        ToastUtils.INSTANCE.showToast("请正确输入证件号");
                        return;
                    }
                }
                //家人  手机号和身份证号二选一
                if (phone.isEmpty() && identityCards.isEmpty()) {
                    ToastUtils.INSTANCE.showToast("请填写手机号或证件号中的一个");
                    return;
                }
                if (!phone.isEmpty() && !ValidatorUtil.isMobile(phone)) {
                    ToastUtils.INSTANCE.showToast("手机号输入有误");
                    return;
                }
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("communityId", communityId);
                baseRequest.put("face", base);
                baseRequest.put("houseId", houseId);
                baseRequest.put("idCard", identityCards);
                baseRequest.put("name", name);
                if(!phone.isEmpty()){
                    baseRequest.put("phone", phone);
                }

                //证件类型,可用值:ID_CARD,PASSPORT,DEFAULT,OTHER
                if(certificateType!=null&&!certificateType.isEmpty()){
                    baseRequest.put("certificateType", certificateType);
                }

                String types = getIntent().getStringExtra("type");
                if (TextUtils.isEmpty(types)) {
                    types = operatorType;
                }
                baseRequest.put("operatorType", types);
                baseRequest.put("type", this.type);
                memberPresenterImp.needDialog(baseRequest, string -> {
                    if (string == null) return;
                    if ("true".equals(string)) {
                        //弹窗
                        showReasonDialog(baseRequest);
                    } else {
                        //接口验证不需要弹窗发起请求 老逻辑
                        loading.show();
                        memberPresenterImp.MemberAdd(baseRequest, this::memberAdd);
                    }

                });
                break;
            case R.id.member_home:
                //家人
                type = "FAMILY";
                memberGuest.setBackgroundResource(R.drawable.ic_tabunselected);
                memberHome.setBackgroundResource(R.drawable.ic_tabselected);
                tvTip.setVisibility(View.VISIBLE);

                memberHome.setTextColor(Color.parseColor("#FFFFFF"));
                memberGuest.setTextColor(Color.parseColor("#FF8A49"));
                memberEtName.setHint("请输入成员姓名");
                memberEtPhone.setHint("请输入成员手机");
                identityCard.setHint("请输入成员证件号");
                break;
            case R.id.member_guest:
                //租客
                type = "TENANT";
                memberHome.setBackgroundResource(R.drawable.ic_tabunselected);
                memberGuest.setBackgroundResource(R.drawable.ic_tabselected);
                tvTip.setVisibility(View.GONE);
                memberGuest.setTextColor(Color.parseColor("#FFFFFF"));
                memberHome.setTextColor(Color.parseColor("#FF8A49"));
                memberEtName.setHint("请输入租客姓名");
                memberEtPhone.setHint("请输入租客手机");
                identityCard.setHint("请输入租客证件号（必填）");
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
                setAddressSelectorPopup1(v);
                break;
        }
    }

    private void setGameTimes() {
        HomePresenter homePresenter = new HomePresenter();
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("mobile", LocalCache.getUser().getPhone());
        baseRequest.put("kind", "2");
        homePresenter.getGameTimesMethod(baseRequest,gameTimesResultBean -> {
            if (gameTimesResultBean==null)return;
            String code = LocalCache.getGameTimesCode("errorCode");
//            String url = gameTimesResultBean.getH5Url();
            String url = gameTimesResultBean.getGameUrl();
            int count = gameTimesResultBean.getNum();
            if (TextUtils.equals(code,"0")){
                setDialog(url,count+"");
            }else if (TextUtils.equals(code,"4")){
                ToastUtils.INSTANCE.showToast("次数增加已到上限");
            }
        });
    }

    private void setDialog(String url,String count) {
        //1、使用Dialog、设置style
        Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.baobing_dialog, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        TextView tv_2_act = dialog.findViewById(R.id.tv_2_act);
        TextView tv_stay = dialog.findViewById(R.id.tv_stay);
        TextView title = dialog.findViewById(R.id.title);
        title.setText("已获得"+count+"次博饼机会");
        tv_2_act.setOnClickListener(view1 -> {
            //TODO
            startWebViewActivity(this,url,"");
            dialog.dismiss();
            finish();
        });

        tv_stay.setOnClickListener(view13 -> dialog.dismiss());
    }


    /**
     *
     * @param context
     * @param url
     * @throws JSONException
     */
    private void startWebViewActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, url);
        intent.putExtra(Constant.WEB_BACK, true);
        intent.putExtra(Constant.WEB_TITLE, title);
        intent.putExtra(Constant.WEB_BAOBING_BACK, 1);
        intent.putExtra(Constant.WEB_TITLE_RIGHT, 1);
        context.startActivity(intent);
    }

    private void showReasonDialog(TreeMap<String, Object> baseRequest) {
        final TwoButtonEditDialog dialog = new TwoButtonEditDialog(MemberManageActivity.this);
        dialog.setDialogMsg(getResources().getString(R.string.add_member_limit_tip));
        dialog.setOkListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(dialog.getTextMsg())){
                    ToastUtils.INSTANCE.showToast("请输入申请理由");
                    return;
                }
                baseRequest.put("applyReason", dialog.getTextMsg());
                loading.show();
                memberPresenterImp.updataDialogInfo(baseRequest,  string-> {
                    loading.dismiss();
                    if (string == null) return;
                    ToastUtils.INSTANCE.showLongToast(MemberManageActivity.this,"提交成功");
                    dialog.dismiss();
                    finish();
                });
            }
        });
        dialog.show();
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
            Intent intent = new Intent(MemberManageActivity.this, PhotoActivity.class);
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
            if (data != null) {
                byte buf[] = data.getByteArrayExtra("data");
                Bitmap bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                base = ImgUtils.smallImage(bitmap);
                memberFace.setText("已登记");
                dialog.dismiss();
            }
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
                .setViewOnClickListener(MemberManageActivity.this)
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
                .setViewOnClickListener(MemberManageActivity.this)
                .setBackgroundDarkEnable(true)
                .setBackgroundAlpha(0.7f)
                .setBackgroundDrawable(new ColorDrawable(999999))
                .build(MemberManageActivity.this)
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
                        if (info.equals("身份证")) {
                            certificateType = "ID_CARD";
                        } else if (info.equals("护照")) {
                            certificateType = "PASSPORT";
                        } else if (info.equals("其他证件")) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}