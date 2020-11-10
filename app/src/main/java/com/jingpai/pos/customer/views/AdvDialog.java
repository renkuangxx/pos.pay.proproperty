package com.jingpai.pos.customer.views;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.SplashBean;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.utils.GlideUtils;

public class AdvDialog implements View.OnClickListener {
	private OnClickListener okListener;
	private View rl_dialog;
	private PopupWindow popupWindow;
	private View contentView;
	private ImageView ivClose,ivAdv;
	private View mainView;
	private SplashBean mSplashBean;
	private Activity mContext;
	public AdvDialog(Activity context) {
		mContext = context;
		mainView = context.getWindow().getDecorView().findViewById(android.R.id.content);
		contentView = LayoutInflater.from(context).inflate( R.layout.dialog_adv, null);
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

	public void setData(SplashBean splashBean){
		mSplashBean = splashBean;
		GlideUtils.LoadingImgOrg(mContext,splashBean.getImageUrl(),ivAdv);
	}

	public void setOkListener(OnClickListener okListener) {
		this.okListener = okListener;
	}
	
	private void initView() {
		ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
		ivAdv = (ImageView) contentView.findViewById(R.id.iv_adv);
	}
	
	public void show(View view) {
		CommonUtil.hideKeyboard(MyApplication.getContext(), view);
		popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
	}
	
	private void initListener() {
		ivClose.setOnClickListener(this);
		ivAdv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_close:
			popupWindow.dismiss();
			break;
		case R.id.iv_adv:
			Intent intent=null;
			if (mSplashBean.getLink().contains("jphl.com")){
				intent = new Intent(mContext, CityLifeWebViewActivity.class);
				intent.putExtra(Constant.WEB_URL,mSplashBean.getLink());
//			intent.putExtra(Constant.WEB_BACK,true);
//			intent.putExtra(Constant.WEB_TITLE,mSplashBean.getName());
				mContext.startActivity(intent);
			}else{
				intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(mSplashBean.getLink());
				intent.setData(content_url);
				mContext.startActivity(intent);
			}

			break;
		default:
			break;
		}
	}
}
