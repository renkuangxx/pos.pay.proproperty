package com.jingpai.pos.customer.activity.authentication.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.housemember.HouseHolderPresenterOld;
import com.jingpai.pos.customer.adapter.YezhuRegisterDetailAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.HouseHolderBean;
import com.jingpai.pos.customer.bean.show.AnnouncementBean;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.views.TwoButtonEditDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YezhuDetailActivity extends BaseActivity {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_shenfen)
    TextView tvShenfen;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_register_type)
    TextView tvRegisterType;
    @BindView(R.id.tv_room_number)
    TextView tvRoomNumber;
    @BindView(R.id.tv_customer_result)
    TextView tv_customer_result;
    @BindView(R.id.tv_card_no)
    TextView tvCardNo;
    @BindView(R.id.tv_customer)
    TextView tv_customer;
    @BindView(R.id.iv_above)
    ImageView ivAbove;
    @BindView(R.id.iv_below)
    ImageView ivBelow;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rl_hetong)
    RecyclerView rlHetong;
    @BindView(R.id.tv_owner_result)
    TextView tvOwnerResult;
    @BindView(R.id.rl_owner_result)
    RelativeLayout rlOwnerResult;
    @BindView(R.id.rl_photo)
    RelativeLayout rl_photo;
    @BindView(R.id.rl_start_time)
    RelativeLayout rl_start_time;
    @BindView(R.id.rl_end_time)
    RelativeLayout rl_end_time;
    @BindView(R.id.rl_chanquan_type)
    RelativeLayout rl_chanquan_type;
    @BindView(R.id.rl_customer)
    RelativeLayout rl_customer;
    @BindView(R.id.rl_customer_time)
    RelativeLayout rl_customer_time;
    @BindView(R.id.rl_customer_result)
    RelativeLayout rl_customer_result;
    @BindView(R.id.tv_owner_time)
    TextView tvOwnerTime;
    @BindView(R.id.tv_chanquan_type)
    TextView tv_chanquan_type;
    @BindView(R.id.tv_customer_time)
    TextView tv_customer_time;
    @BindView(R.id.rl_owner_time)
    RelativeLayout rlOwnerTime;
    @BindView(R.id.tv_refuse_reson)
    TextView tvRefuseReson;
    @BindView(R.id.ll_refuse_reson)
    LinearLayout llRefuseReson;
    @BindView(R.id.sc_bg)
    ScrollView scBg;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.ll_apply)
    LinearLayout llApply;
    @BindView(R.id.rl_btn)
    RelativeLayout rlBtn;
    private HouseHolderBean houseMemberBean;
    private YezhuRegisterDetailAdapter yezhuRegisterDetailAdapter;
    private HouseHolderPresenterOld houseHolderPresenter;
    String auditId;
    String opretorTYype;
    String idCard;
    List<String> list = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_yezhu_register;
    }

    @Override
    protected void initData() {
        String message = getIntent().getStringExtra("message");
        if (!TextUtils.isEmpty(message)) {
            JSONObject jsonObject = JSONObject.parseObject(message);
            AnnouncementBean.DataBeanX.DataBean bean = JSONObject.toJavaObject(jsonObject, AnnouncementBean.DataBeanX.DataBean.class);
            auditId = bean.getId() + "";
        } else {
            auditId = getIntent().getStringExtra("auditId");
        }

        yezhuRegisterDetailAdapter = new YezhuRegisterDetailAdapter(this, list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlHetong.setLayoutManager(layoutManager);
        rlHetong.setAdapter(yezhuRegisterDetailAdapter);

        houseHolderPresenter = new HouseHolderPresenterOld();
        houseHolderPresenter.getHouseHolderInfo(TextUtils.isEmpty(auditId) ? "" : auditId, houseHolderBean -> {
            if (houseHolderBean == null) return;
            houseMemberBean = houseHolderBean;
            //申请时间
            String time = TextUtils.isEmpty(houseHolderBean.getApplyTime()) ? "" : houseHolderBean.getApplyTime();
            tvTime.setText(time);
            //申请人
            String name = TextUtils.isEmpty(houseHolderBean.getMemberName()) ? "" : houseHolderBean.getMemberName();
            tvShenfen.setText(name);
            //性别
             String sex = TextUtils.isEmpty(houseHolderBean.getGender()) ? "" : houseHolderBean.getGender();
            tvSex.setText(sex);
            //联系方式
            String phone =TextUtils.isEmpty(houseHolderBean.getMemberPhone())?"":houseHolderBean.getMemberPhone();
            String tel = CommonUtil.strEncry(phone, 3, 6);
            tvTel.setText(phone);
            //认证类型
             String renzhengType = TextUtils.isEmpty(houseHolderBean.getMemberType()) ? "" : houseHolderBean.getMemberType();
             if (TextUtils.equals(renzhengType,"FAMILY")){
                 tvRegisterType.setText("产权家属");
             }else if (TextUtils.equals(renzhengType,"OWNER")){
                 tvRegisterType.setText("产权人");
             }else {
                 tvRegisterType.setText("租户");
             }
            //产权登记类型   todo
           opretorTYype = TextUtils.isEmpty(houseHolderBean.getChangeType()) ? "" : houseHolderBean.getChangeType();
            rl_chanquan_type.setVisibility(View.VISIBLE);
            if (TextUtils.equals(opretorTYype,"ERROR_CHANGE")){
                tv_chanquan_type.setText("登记信息替换");

            }else if (TextUtils.equals(opretorTYype,"CHANGE")){
                tv_chanquan_type.setText("房屋交易变更");
            }else {
                rl_chanquan_type.setVisibility(View.GONE);
            }
            //房间号
            String room = TextUtils.isEmpty(houseHolderBean.getRoomNo()) ? "" : houseHolderBean.getRoomNo();
            tvRoomNumber.setText(room);
            //证件号码
            idCard = TextUtils.isEmpty(houseHolderBean.getIdCard()) ? "" : houseHolderBean.getIdCard();
            String idNum = CommonUtil.strEncry(idCard, 6, idCard.length() - 5);
            tvCardNo.setText(idCard);
            //身份证人像面
             String aboveUrl =TextUtils.isEmpty(houseHolderBean.getIdCardPositive()) ? "" : houseHolderBean.getIdCardPositive();

            Glide.with(this)
                    .load(aboveUrl)
                    .into(ivAbove);
//            ivAbove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (returnBitMap(aboveUrl)!=null){
//                        MyImageDialog myImageDialog = new MyImageDialog(YezhuDetailActivity.this,R.style.dialogWindowAnim,0,-300,returnBitMap(aboveUrl));
//                        myImageDialog.setCanceledOnTouchOutside(true);
//                        myImageDialog.show();
//
//                    }
//                }
//            });
            //身份证国徽面
             String belowUrl =TextUtils.isEmpty(houseHolderBean.getIdCardNegative()) ? "" : houseHolderBean.getIdCardNegative();
            Glide.with(this)
                    .load(belowUrl)
                    .into(ivBelow);
            //租赁开始时间
             String startTime =TextUtils.isEmpty(houseHolderBean.getLeaseStartTime()) ? "" : houseHolderBean.getLeaseStartTime();
            tvStartTime.setText(startTime);
            rl_start_time.setVisibility(TextUtils.isEmpty(startTime)?View.GONE:View.VISIBLE);
            //租赁结束时间
             String endTime =TextUtils.isEmpty(houseHolderBean.getLeaseEndTime()) ? "" : houseHolderBean.getLeaseEndTime();
            tvEndTime.setText(endTime);
            rl_end_time.setVisibility(TextUtils.isEmpty(endTime)?View.GONE:View.VISIBLE);
            //租赁合同  todo
            String hetong = TextUtils.isEmpty(houseHolderBean.getLeaseContract()) ? "" : houseHolderBean.getLeaseContract();
            if ( !TextUtils.isEmpty(hetong)){
                if (hetong.contains(",")){
                    String[] hetongArray = hetong.split(",");
                    if (hetongArray.length>0){
                        list.clear();
                        for (int i =0; i<hetongArray.length;i++){
                            list.add(hetongArray[i]);
                        }
                        yezhuRegisterDetailAdapter.setData(list);
                    }

                }else {
                    list.add(hetong);
                    yezhuRegisterDetailAdapter.setData(list);
                }
                rl_photo.setVisibility(View.VISIBLE);
            }else {
                rl_photo.setVisibility(View.GONE);
            }

            //客服管家
            String manageName = TextUtils.isEmpty(houseHolderBean.getManagerName()) ? "" : houseHolderBean.getManagerName();
            tv_customer.setText(manageName);
            rl_customer.setVisibility(TextUtils.isEmpty(manageName)?View.GONE:View.VISIBLE);
            //客服审核结果
            String managerAuditResult = TextUtils.isEmpty(houseHolderBean.getManagerAuditResult()) ? "" : houseHolderBean.getManagerAuditResult();
            tv_customer_result.setText(managerAuditResult);
            if ("已同意".equals(managerAuditResult)) {
                tv_customer_result.setTextColor(getResources().getColor(R.color.bg_front_default));
            } else if (managerAuditResult.contains("审核")) {
                tv_customer_result.setTextColor(getResources().getColor(R.color.main));
            }
            rl_customer_result.setVisibility(TextUtils.isEmpty(managerAuditResult)?View.GONE:View.VISIBLE);
            //拒绝原因
            String rejectReason = TextUtils.isEmpty(houseHolderBean.getRejectReason()) ? "" : houseHolderBean.getRejectReason();
            String managerRejectReason = TextUtils.isEmpty(houseHolderBean.getManagerRejectReason()) ? "" : houseHolderBean.getManagerRejectReason();
            //产权人审核结果
            String ownerAuditResult = TextUtils.isEmpty(houseHolderBean.getOwnerAuditResult()) ? "" : houseHolderBean.getOwnerAuditResult();
            tvOwnerResult.setText(ownerAuditResult);
            if ("已同意".equals(ownerAuditResult)) {
                tvOwnerResult.setTextColor(getResources().getColor(R.color.bg_front_default));
            } else if (ownerAuditResult.contains("审核")) {
                tvOwnerResult.setTextColor(getResources().getColor(R.color.main));
            }
            rlOwnerResult.setVisibility(TextUtils.isEmpty(ownerAuditResult)?View.GONE:View.VISIBLE);
            //客服审核时间
            String managerAuditTime = TextUtils.isEmpty(houseHolderBean.getManagerAuditTime()) ? "" : houseHolderBean.getManagerAuditTime();
            tv_customer_time.setText(managerAuditTime);
            rl_customer_time.setVisibility(TextUtils.isEmpty(managerAuditTime)?View.GONE:View.VISIBLE);
            //产权人审核时间
            String ownerAuditTime = TextUtils.isEmpty(houseHolderBean.getOwnerAuditTime()) ? "" : houseHolderBean.getOwnerAuditTime();
            tvOwnerTime.setText(ownerAuditTime);
            rlOwnerTime.setVisibility(TextUtils.isEmpty(ownerAuditTime)?View.GONE:View.VISIBLE);

            switch (houseHolderBean.getAuditResult()) {//0-待审核、1-审核通过、2-审核不通过
                case 0:
                    if (houseHolderBean.isHasAuth() && "审核中".equals(houseHolderBean.getAuditResultName())) {
                        llApply.setVisibility(View.VISIBLE);
                        rlBtn.setVisibility(View.VISIBLE);
                    }
                    llRefuseReson.setVisibility(View.GONE);
                    break;
                case 1:
                    llRefuseReson.setVisibility(View.GONE);
                    break;
                case 2:
                    llRefuseReson.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(rejectReason)){
                        tvRefuseReson.setText(rejectReason);
                    }else if (!TextUtils.isEmpty(managerRejectReason)){
                        tvRefuseReson.setText(managerRejectReason);
                    }
                    break;
            }
        });


    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                showReasonDialog();
                break;
            case R.id.btn_ok:
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("auditId", auditId);
                baseRequest.put("auditResult", 1);
                baseRequest.put("type", opretorTYype);
//                baseRequest.put("remark", dialog.getTextMsg());
                houseHolderPresenter.applyMember(baseRequest, string -> {
                    if (string == null) return;
                    ToastUtils.INSTANCE.showLongToast(this, "提交成功");
                    finish();
                });
                break;
        }
    }

    private void showReasonDialog() {
        final TwoButtonEditDialog dialog = new TwoButtonEditDialog(this);
        dialog.setDialogTitle(getResources().getString(R.string.refuce_reson));
        dialog.setOkListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (TextUtils.isEmpty(dialog.getTextMsg())) {
                    ToastUtils.INSTANCE.showToast("请输入拒绝理由");
                    return;
                }
                TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                baseRequest.put("auditId", auditId);
                baseRequest.put("auditResult", 2);
                baseRequest.put("remark", dialog.getTextMsg());
                baseRequest.put("type", opretorTYype);
                houseHolderPresenter.applyMember(baseRequest, string -> {
                    if (string == null) return;
                    ToastUtils.INSTANCE.showLongToast(YezhuDetailActivity.this, "提交成功");
                    dialog.dismiss();
                    finish();
                });
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    Bitmap bitmap;
    public Bitmap returnBitMap(final String url){

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }
}
