package com.jingpai.pos.customer.views;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.bean.VersionBean;
import com.jingpai.pos.customer.utils.CommonUtil;

/**
 * Created By：jinheng.liu
 * on 2020/9/12
 */
public class DownLoadDialog implements View.OnClickListener {
    private OnClickListener okListener;
    private View rl_dialog;
    private PopupWindow popupWindow;
    private View contentView;
    private View mainView;
    private TextView tv_new_version_name,tv_desc,tv_cancel,tv_confirm;
    private Activity mContext;
    public DownLoadDialog(Activity context,VersionBean versionBean) {
        mContext = context;
        mainView = context.getWindow().getDecorView().findViewById(android.R.id.content);
        contentView = LayoutInflater.from(context).inflate( R.layout.dialog_download, null);
        rl_dialog = contentView.findViewById(R.id.rl_dialog);
        popupWindow = new PopupWindow(contentView,
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.dialogWindowAnim);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        initView();
        initListener();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 50);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
				rl_dialog.setBackgroundColor(Color.argb(
						(int) (200 * value / 50), 0, 0, 0));
            }
        });
        animator.setDuration(500);
        animator.setTarget(rl_dialog);
        animator.start();
        tv_new_version_name.setText(versionBean.getVersionName()+"");
        tv_desc.setText(versionBean.getVersionDesc());
        if (versionBean.isForcedUpdate()){
            tv_cancel.setVisibility(View.GONE);
        }
    }

    public void setData(VersionBean versionBean){
        tv_new_version_name.setText(versionBean.getVersionCode());
        tv_desc.setText(versionBean.getVersionDesc());
    }

    public void setOkListener(OnClickListener okListener) {
        this.okListener = okListener;
    }

    private void initView() {
        tv_new_version_name = (TextView) contentView.findViewById(R.id.tv_new_version_name);
        tv_desc = (TextView) contentView.findViewById(R.id.tv_desc);
        tv_cancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        tv_confirm = (TextView) contentView.findViewById(R.id.tv_confirm);
    }

    public void show(View view) {
        CommonUtil.hideKeyboard(MyApplication.getContext(), view);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void initListener() {
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;
            case R.id.tv_confirm:
                popupWindow.dismiss();
                okListener.onClick(null,1);
                break;
            default:
                break;
        }
    }
}