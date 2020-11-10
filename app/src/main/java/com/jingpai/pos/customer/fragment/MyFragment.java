package com.jingpai.pos.customer.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.authentication.activity.MyHouseDefaultActivity;
import com.jingpai.pos.customer.activity.housemember.BuildingActivity;
import com.jingpai.pos.activity.payment.PaymentActivity;
import com.jingpai.pos.customer.activity.repairs.MatterHistoryActivity;
import com.jingpai.pos.customer.activity.show.Home.CarManageActivity;
import com.jingpai.pos.customer.activity.show.Home.FeedbackActivity;
import com.jingpai.pos.customer.activity.show.My.AboutUsActivity;
import com.jingpai.pos.activity.login.PersonalCenterActivity;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.mvp.presenter.shop.ShopPresenter;
import com.jingpai.pos.customer.utils.Intents;
import com.jingpai.pos.customer.utils.LocalCache;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jingpai.pos.BuildConfig.ACIVITY_URL;
import static com.jingpai.pos.BuildConfig.SHOP_URL;

/*
 * function:我的页面
 */
public class MyFragment extends Fragment {

    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_my_name)
    TextView tvMyName;
    Unbinder unbinder;
    @BindView(R.id.tv_default_plot)
    TextView tvDefaultPlot;

    @BindView(R.id.tv_point)
    TextView tvPoint;

    @BindView(R.id.tv_coupon)
    TextView tvCoupon;

    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.tv_distribution)
    TextView tvDistribution;

    private ShopPresenter shopPresenter;
    String communityName;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);
        shopPresenter = new ShopPresenter();
        communityName = LocalCache.getCurrentCommunityName();
        return view;
    }

    private void initMemberPoint() {
        shopPresenter.getMemberPoint(memberPointBean -> {
            if (memberPointBean != null) {
                tvPoint.setText(String.valueOf(memberPointBean.getIntegral()));
                tvCoupon.setText(String.valueOf(memberPointBean.getCouponNum()));
                tvCollect.setText(String.valueOf(memberPointBean.getCollection()));
            }
        });

        shopPresenter.getMyDistribution(desc -> {
            if (desc != null) {
                tvDistribution.setText(desc);
            }
        });
    }

    @OnClick(R.id.ll_my_distribution)
    public void onClickedMyDistribution(View view) {
        openShopPage("distribution/turningPage");
    }

    @OnClick(R.id.ll_my_involved)
    public void onClickedMyInvolved(View view) {
        openActivityPage("myActivity?isSourceApp=app&token=" + LocalCache.getToken());
    }

    @OnClick({R.id.my_car, R.id.my_house, R.id.ll_my_matter, R.id.tv_about_us, R.id.tv_service, R.id.iv_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_car:
                if (!StringUtils.isEmpty(communityName)) {
                    Intents.getInstence().intent(getActivity(), CarManageActivity.class);
                } else {
//                    isNeedRegster();
                    startActivity(new Intent(getContext(), MyHouseDefaultActivity.class));
//                    ToastUtils.INSTANCE.showToast("您暂无权限");
                }
                break;
            case R.id.my_house:
                Intents.getInstence().intent(getActivity(), BuildingActivity.class);

//                else{
//                    isNeedRegster();
//                    ToastUtils.INSTANCE.showToast("您暂无权限");
//                }
                break;
            case R.id.ll_my_matter:
                if (!StringUtils.isEmpty(communityName)) {
                    Intents.getInstence().intent(getActivity(), MatterHistoryActivity.class);

                } else {
//                    ToastUtils.INSTANCE.showToast("您暂无权限");
//                    isNeedRegster();
                    startActivity(new Intent(getContext(), MyHouseDefaultActivity.class));
                }

                break;
            case R.id.tv_about_us:
                Intents.getInstence().intent(getActivity(), AboutUsActivity.class);
                break;
            case R.id.tv_service:
            case R.id.iv_service:
                //1、使用Dialog、设置style
                Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
                //2、设置布局
                View view1 = View.inflate(getActivity(), R.layout.dialog_show_phone, null);
                dialog.setContentView(view1);
                Window window = dialog.getWindow();
                //设置弹出位置
                window.setGravity(Gravity.BOTTOM);
                //设置弹出动画
                window.setWindowAnimations(R.style.main_menu_animStyle);
                //设置对话框大小
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                String newPhone = tvService.getText().toString();
                TextView tvPhone = dialog.findViewById(R.id.tv_show_phone);
                tvPhone.setText(newPhone);
                tvPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            //没有申请权限,做权限的申请处理 参数:1.上下文 2.字符串数组,可以一次申请多个权限 3.int型,请求码方便我们后面权限区分
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 0);
                        } else {
                            //3.申请权限了,直接做打电话的业务逻辑
                            doCallPhone();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    @OnClick(R.id.rl_personal_center)
    public void toPersonalCenter() {
        Intents.getInstence().intent(getActivity(), PersonalCenterActivity.class);
    }

    @OnClick({R.id.iv_check_in, R.id.ll_point})
    public void toCheckIn() {
        openShopPage("integrationCenter");
    }

    @OnClick(R.id.ll_coupon)
    public void toCoupon() {
        openShopPage("member/coupon/membercoupon");
    }

    @OnClick(R.id.ll_collect)
    public void toCollect() {
        openShopPage("member/collect/collect");
    }

    public void openShopPage(String path) {
        Intent intent = new Intent(getContext(), CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, SHOP_URL + path);
        startActivity(intent);
    }

    public void openActivityPage(String path) {
        Intent intent = new Intent(getContext(), CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, ACIVITY_URL + path);
        startActivity(intent);
    }

    private void doCallPhone() {
        //执行打电话到4001616116的操作
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + tvService.getText().toString());
        intent.setData(uri);
        //使用AS,这里会报红,编译能通过,只是提醒你要做Android6.0权限的适配
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //因为这是处理所有权限申请的回调,为方便对应申请权限做对应逻辑操作,使用switch判断之前请求码的设置,区分权限
        switch (requestCode) {
            case 0://打电话权限的回调处理
                //判断打电话申请权限是否成功,成功就执行打电话的逻辑
                //因为集合里只有一个权限申请,所以参数为0代表打电话权限
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doCallPhone();//打电话
                } else {
                    //用户拒绝了权限请求,给用户提示权限的功能
                    Toast.makeText(getActivity(), "权限没有授予", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.ll_my_bill)
    public void toMyBill() {
        if (!StringUtils.isEmpty(communityName)) {
            Intents.getInstence().intent(getActivity(), PaymentActivity.class);

        } else {
//            ToastUtils.INSTANCE.showToast("您暂无权限");
//            isNeedRegster();
            startActivity(new Intent(getContext(), MyHouseDefaultActivity.class));
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        initMemberPoint();
        tvMyName.setText(LocalCache.getUserName());
        String avatar = LocalCache.getUserAvatar();
        if (StringUtils.isEmpty(avatar)) {
            Glide.with(getActivity()).load(R.drawable.ic_headportraitdefault_my).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivImage);
        } else {
            Glide.with(getActivity()).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivImage);
        }

        tvDefaultPlot.setText(LocalCache.getCurrentCommunityName());
    }

    @OnClick(R.id.ll_feedback)
    public void toFeedback() {
        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
        intent.putExtra("title", "意见反馈");
        startActivity(intent);
    }

    @OnClick(R.id.ll_order)
    public void toMyOrder() {
        Intent intent = new Intent(getActivity(), CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, SHOP_URL + "member/order/orderlist");
        startActivity(intent);
    }

    //跳转公司简介
    @OnClick(R.id.ll_synopsis)
    public void toCompanyProfile() {
//        Intent intent = new Intent(getActivity(), CompanyActivity.class);
//        startActivity(intent);
        startWebViewActivity(getContext(), Constant.COMPANY_PROFILE, getResources().getString(R.string.Company_profile));
    }

    /**
     * WebView打开公司简介url
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
        context.startActivity(intent);
    }

}