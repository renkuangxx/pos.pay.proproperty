package com.jingpai.pos.customer.activity.housemember;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.HouseHolderBean;
import com.jingpai.pos.customer.bean.show.AnnouncementBean;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.views.TwoButtonEditDialog;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 86173
 */
public class HouseHolderActivity extends BaseActivity {
    @BindView(R.id.tv_room)
    TextView tvRoom;
    @BindView(R.id.tv_shenfen)
    TextView tvShenfen;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.rl_tel)
    RelativeLayout rlTel;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.tv_customer)
    TextView tvCustomer;
    @BindView(R.id.rl_customer)
    RelativeLayout rlCustomer;
    @BindView(R.id.tv_customer_result)
    TextView tvCustomerResult;
    @BindView(R.id.rl_customer_result)
    RelativeLayout rlCustomerResult;
    @BindView(R.id.tv_customer_time)
    TextView tvCustomerTime;
    @BindView(R.id.rl_customer_time)
    RelativeLayout rlCustomerTime;
    @BindView(R.id.tv_owner_result)
    TextView tvOwnerResult;
    @BindView(R.id.rl_owner_result)
    RelativeLayout rlOwnerResult;
    @BindView(R.id.tv_owner_time)
    TextView tvOwnerTime;
    @BindView(R.id.rl_owner_time)
    RelativeLayout rlOwnerTime;
    @BindView(R.id.tv_refuse_reson)
    TextView tvRefuseReson;
    @BindView(R.id.ll_refuse_reson)
    LinearLayout llRefuseReson;
    @BindView(R.id.sc_bg)
    ScrollView scBg;
    @BindView(R.id.tv_reapply)
    TextView tvReapply;
    @BindView(R.id.btn_cancel)
    TextView btnCancel;
    @BindView(R.id.btn_ok)
    TextView btnOk;
    @BindView(R.id.ll_apply)
    LinearLayout llApply;
    @BindView(R.id.rl_btn)
    RelativeLayout rlBtn;
    @BindView(R.id.tv_card_type)
    TextView tvCardType;
    @BindView(R.id.rl_card_type)
    RelativeLayout rlCardType;
    @BindView(R.id.tv_card_no)
    TextView tvCardNo;
    @BindView(R.id.rl_card_no)
    RelativeLayout rlCardNo;

    private HouseHolderPresenterOld houseHolderPresenter;
    private HouseHolderBean houseMemberBean;

    String idCard;
    String auditId;

    @Override
    protected int getLayout() {
        return R.layout.activity_householder;
    }

    @Override
    protected void initData() {
        houseHolderPresenter = new HouseHolderPresenterOld();

        String message = getIntent().getStringExtra("message");
        if (!TextUtils.isEmpty(message)) {
            JSONObject jsonObject = JSONObject.parseObject(message);
            AnnouncementBean.DataBeanX.DataBean bean = JSONObject.toJavaObject(jsonObject, AnnouncementBean.DataBeanX.DataBean.class);
            auditId = bean.getId() + "";
        } else {
            auditId = getIntent().getStringExtra("auditId");
        }


        houseHolderPresenter.getHouseHolderInfo(TextUtils.isEmpty(auditId) ? "" : auditId, houseHolderBean -> {
            if (houseHolderBean == null) return;
            houseMemberBean = houseHolderBean;
            tvRoom.setText(TextUtils.isEmpty(houseHolderBean.getRoomNo()) ? "" : houseHolderBean.getRoomNo());
            tvShenfen.setText(TextUtils.isEmpty(houseHolderBean.getIdentity()) ? "" : houseHolderBean.getIdentity());
            tvName.setText(TextUtils.isEmpty(houseHolderBean.getMemberName()) ? "" : houseHolderBean.getMemberName());
            if (TextUtils.isEmpty(houseHolderBean.getMemberPhone())) {
                rlTel.setVisibility(View.GONE);
            } else {
                tvTel.setText(CommonUtil.strEncry(houseHolderBean.getMemberPhone(), 3, 6));
            }

            idCard = TextUtils.isEmpty(houseHolderBean.getIdCard()) ? "" : houseHolderBean.getIdCard();
            if (TextUtils.isEmpty(houseHolderBean.getIdCard())) {
                rlCardType.setVisibility(View.GONE);
                rlCardNo.setVisibility(View.GONE);
            } else {
                if ("OTHER".equals(houseHolderBean.getCertificateType())) {
                    tvCardType.setText("其他类型");
                    tvCardNo.setText(CommonUtil.strEncry(idCard, 1, idCard.length() - 1));
                } else if ("PASSPORT".equals(houseHolderBean.getCertificateType())) {
                    tvCardType.setText("护照");
                    tvCardNo.setText(CommonUtil.strEncry(idCard, 1, idCard.length() - 1));
                } else if ("ID_CARD".equals(houseHolderBean.getCertificateType())) {
                    tvCardType.setText("身份证");
                    tvCardNo.setText(CommonUtil.strEncry(idCard, 6, 13));
                }
            }
            tvTime.setText(TextUtils.isEmpty(houseHolderBean.getApplyTime()) ? "" : houseHolderBean.getApplyTime());
            tvReason.setText(TextUtils.isEmpty(houseHolderBean.getApplyReason()) ? "" : houseHolderBean.getApplyReason());

            if (TextUtils.isEmpty(houseHolderBean.getManagerName())) {
                rlCustomer.setVisibility(View.GONE);
            } else {
                tvCustomer.setText(houseHolderBean.getManagerName());
            }
            if (TextUtils.isEmpty(houseHolderBean.getManagerAuditResult())) {
                rlCustomerResult.setVisibility(View.GONE);
            } else {
                tvCustomerResult.setText(houseHolderBean.getManagerAuditResult());
                if ("已同意".equals(houseHolderBean.getManagerAuditResult())) {
                    tvCustomerResult.setTextColor(getResources().getColor(R.color.bg_front_default));
                } else if ("审核中".equals(houseHolderBean.getManagerAuditResult())) {
                    tvCustomerResult.setTextColor(getResources().getColor(R.color.main));
                }
            }
            if (TextUtils.isEmpty(houseHolderBean.getManagerAuditTime())) {
                rlCustomerTime.setVisibility(View.GONE);
            } else {
                tvCustomerTime.setText(houseHolderBean.getManagerAuditTime());
            }

            if (TextUtils.isEmpty(houseHolderBean.getOwnerAuditResult())) {
                rlOwnerResult.setVisibility(View.GONE);
            } else {
                tvOwnerResult.setText(houseHolderBean.getOwnerAuditResult());
                if ("已同意".equals(houseHolderBean.getOwnerAuditResult())) {
                    tvOwnerResult.setTextColor(getResources().getColor(R.color.bg_front_default));
                } else if ("审核中".equals(houseHolderBean.getOwnerAuditResult())) {
                    tvOwnerResult.setTextColor(getResources().getColor(R.color.main));
                }
            }
            if (TextUtils.isEmpty(houseHolderBean.getOwnerAuditTime())) {
                rlOwnerTime.setVisibility(View.GONE);
            } else {
                tvOwnerTime.setText(houseHolderBean.getOwnerAuditTime());
            }

            switch (houseHolderBean.getAuditResult()) {//0-待审核、1-审核通过、2-审核不通过
                case 0:
                    if (houseHolderBean.isHasAuth()) {
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
                    tvRefuseReson.setText(houseHolderBean.getRejectReason());
                    if (houseHolderBean.isApplyUser()) {
                        tvReapply.setVisibility(View.VISIBLE);
                        rlBtn.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        });
    }

    //重新申请
    @OnClick(R.id.tv_reapply)
    public void reApply() {
        if (houseMemberBean != null) {
            Intent intent = new Intent(HouseHolderActivity.this, MemberManageActivity.class);
            intent.putExtra("houseMemberBean",houseMemberBean);
            startActivity(intent);
            finish();
        }
    }

    //拒绝
    @OnClick(R.id.btn_cancel)
    public void refuse() {
        showReasonDialog();
    }

    //同意
    @OnClick(R.id.btn_ok)
    public void btnOk() {
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("auditId", auditId);
        baseRequest.put("auditResult", 1);
//                baseRequest.put("remark", dialog.getTextMsg());
        houseHolderPresenter.applyMember(baseRequest, string -> {
            if (string == null) return;
            ToastUtils.INSTANCE.showLongToast(HouseHolderActivity.this, "提交成功");
            finish();
        });
    }

    private void showReasonDialog() {
        final TwoButtonEditDialog dialog = new TwoButtonEditDialog(HouseHolderActivity.this);
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
                houseHolderPresenter.applyMember(baseRequest, string -> {
                    if (string == null) return;
                    ToastUtils.INSTANCE.showLongToast(HouseHolderActivity.this, "提交成功");
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
}
