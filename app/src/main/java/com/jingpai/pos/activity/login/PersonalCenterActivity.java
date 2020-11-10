package com.jingpai.pos.activity.login;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.bean.User;
import com.jingpai.pos.views.TipsDialog;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.stx.xhb.androidx.OnDoubleClickListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 时间: 2020/2/5
 * 功能:个人中心页面
 */
public class PersonalCenterActivity extends BaseActivity {
    @BindView(R.id.iv_personal_image)
    ImageView ivPersonalImage;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_my_phone)
    TextView tvMyPhone;


    @Override
    protected int getLayout() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initData() {
        User user = LocalCache.getUser();
        try {
            if (user != null) {
                if(user.getSex()!=null){
                    tvSex.setText(user.getSex().intValue() == 1 ? "男" : user.getSex().intValue() == 2 ? "女" : "未知");
                }else{
                    tvSex.setText("未知");
                }
                tvUserName.setText(user.getName());
                tvMyPhone.setText(CommonUtil.strEncry(user.getPhone(), 3, 6));
                String avatar = user.getAvatar();
                if (!StringUtils.isEmpty(avatar)) {
                    Glide.with(PersonalCenterActivity.this).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivPersonalImage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.tv_log_out)
    public void onTvLogOutClicked() {
        TipsDialog tipsDialog = new TipsDialog(this, "确定退出当前账号?");
        tipsDialog.show();
        tipsDialog.setOnOkClick(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                tipsDialog.dismiss();
                gotoLogin();
            }
        });
    }
}