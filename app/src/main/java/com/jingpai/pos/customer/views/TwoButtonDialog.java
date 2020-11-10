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
import com.jingpai.pos.customer.utils.CommonUtil;

public class TwoButtonDialog implements android.view.View.OnClickListener {
	private TextView tvTitle, tv_msg,tv_channel_address;
	private TextView btnCancel, btnOk;
	private OnClickListener cancelListener;
	private OnClickListener okListener;
	
	private View rl_dialog;
	private PopupWindow popupWindow;
	private View contentView;
	private View mainView;

	public TwoButtonDialog(final Activity context) {
		mainView = context.getWindow().getDecorView().findViewById(android.R.id.content);
		contentView = LayoutInflater.from(context).inflate( R.layout.dialog_two_button, null);
		rl_dialog = contentView.findViewById(R.id.rl_dialog);
		popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		popupWindow.setAnimationStyle(R.style.dialogWindowAnim);  
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
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
	}
	
	public void show() {
		CommonUtil.hideKeyboard(MyApplication.getContext(), mainView);
		popupWindow.showAtLocation(mainView, Gravity.BOTTOM, 0, 0);
	}
	public void dismiss(){
		popupWindow.dismiss();
	}
	
	public void setCancelListener(OnClickListener cancelListener) {
		this.cancelListener = cancelListener;
	}
	
	public void setOkListener(OnClickListener okListener) {
		this.okListener = okListener;
	}
	
	private void initView() {
		tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
		tv_msg = (TextView) contentView.findViewById(R.id.tv_msg);
		btnCancel = (TextView) contentView.findViewById(R.id.btn_cancel);
		btnOk = (TextView) contentView.findViewById(R.id.btn_ok);
		tv_channel_address=(TextView)contentView.findViewById(R.id.tv_channel_address);
	}
	
	private void initListener() {
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}
	
	public void setDialogMsg(String string) {
		tv_msg.setText(string);
		tv_msg.setVisibility(View.VISIBLE);
	}
	
	public void setDialogMsg(int string) {
		tv_msg.setText(string);
		tv_msg.setVisibility(View.VISIBLE);
	}
	
	public void setDialogTitle(String string) {
		tvTitle.setText(string);
		tvTitle.setVisibility(View.VISIBLE);
	}
	
	public void setDialogTitle(int string) {
		tvTitle.setText(string);
		tvTitle.setVisibility(View.VISIBLE);
	}

	public void setDialogCancel(int string) {
		btnCancel.setText(string);
	}
	
	public void setDialogCancel(String string) {
        btnCancel.setText(string);
    }
	
	public void setDialogOk(int string) {
		btnOk.setText(string);
	}
	
	public void setDialogOk(String string) {
        btnOk.setText(string);
    }
	/**
	 * 添加频道地址
	 * @param string
	 */
	public void setAddress(String string) {
		tv_channel_address.setText(string);
		tv_channel_address.setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		popupWindow.dismiss();
		switch (v.getId()) {
		case R.id.btn_cancel:
			if (cancelListener != null) {
				cancelListener.onClick(null, 0);
			}
			break;
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
