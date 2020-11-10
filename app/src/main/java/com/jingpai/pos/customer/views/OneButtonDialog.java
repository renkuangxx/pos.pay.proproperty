package com.jingpai.pos.customer.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.utils.CommonUtil;

public class OneButtonDialog implements android.view.View.OnClickListener {
	private TextView tv_title, tv_msg;
	private TextView btn_ok;
	private OnClickListener okListener;
	
	private View rl_dialog;
	private PopupWindow popupWindow;
	private View contentView;
	private View mainView;

	public OneButtonDialog(Activity context) {
		mainView = context.getWindow().getDecorView().findViewById(android.R.id.content);
		contentView = LayoutInflater.from(context).inflate( R.layout.dialog_one_button, null);
		rl_dialog = contentView.findViewById(R.id.rl_dialog);
		popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.setAnimationStyle(R.style.dialogWindowAnim);  
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha(context,1f);
			}
		});
		initView();
		initListener();
		backgroundAlpha(context,0.4f);
		ValueAnimator animator = ValueAnimator.ofFloat(0, 50);
		animator.setDuration(500);
		animator.setTarget(rl_dialog);
		animator.start();
	}
	private void backgroundAlpha(Activity activity,float f) {
		WindowManager.LayoutParams lp =activity.getWindow().getAttributes();
		lp.alpha = f;
		activity.getWindow().setAttributes(lp);
	}
	public void setOkListener(OnClickListener okListener) {
		this.okListener = okListener;
	}
	
	private void initView() {
		tv_title = (TextView) contentView.findViewById(R.id.tv_title);
		tv_msg = (TextView) contentView.findViewById(R.id.tv_msg);
		btn_ok = (TextView) contentView.findViewById(R.id.btn_ok);
	}
	
	public void show() {
		CommonUtil.hideKeyboard(MyApplication.getContext(), mainView);
		popupWindow.showAtLocation(mainView, Gravity.BOTTOM, 0, 0);
	}
	
	private void initListener() {
		btn_ok.setOnClickListener(this);
	}
	
	
	public void setDialogTitle(String string) {
		tv_title.setText(string);
	}
	
	public void setDialogTitle(int string) {
		tv_title.setText(string);
	}
	
	public void setDialogMsg(String string) {
		tv_msg.setText(string);
	}
	
	public void setDialogMsg(int string) {
		tv_msg.setText(string);
	}

	public void setDialogOk(int string) {
		btn_ok.setText(string);
	}
	
	public void setDialogOk(String string) {
        btn_ok.setText(string);
    }

	@Override
	public void onClick(View v) {
		popupWindow.dismiss();
		switch (v.getId()) {
		case R.id.btn_ok:
			if (okListener != null) {
				okListener.onClick(null, 1);
			}
			break;
		default:
			break;
		}
	}
}
