package com.jingpai.pos.customer.views;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.utils.ImgUtils;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.utils.WxShareUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

public class ShareDialog implements View.OnClickListener {
	private OnClickListener okListener;
	private View rl_dialog;
	private PopupWindow popupWindow;
	private View contentView;
	private LinearLayout ll_share_friend,ll_share_friend_circle,ll_save_to_album;
	private View mainView;
	private Activity mContext;
	public ShareDialog(Activity context) {
		mContext = context;
		mainView = context.getWindow().getDecorView().findViewById(android.R.id.content);
		contentView = LayoutInflater.from(context).inflate( R.layout.dialog_share, null);
		rl_dialog = contentView.findViewById(R.id.rl_dialog);
		popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
//				rl_dialog.setBackgroundColor(Color.argb(
//						(int) (200 * value / 50), 0, 0, 0));
			}
		});
		animator.setDuration(500);
		animator.setTarget(rl_dialog);
		animator.start();
	}


	public void setOkListener(OnClickListener okListener) {
		this.okListener = okListener;
	}

	private void initView() {
		ll_share_friend = (LinearLayout) contentView.findViewById(R.id.ll_share_friend);
		ll_share_friend_circle = (LinearLayout) contentView.findViewById(R.id.ll_share_friend_circle);
		ll_save_to_album = (LinearLayout) contentView.findViewById(R.id.ll_save_to_album);
	}

	public void show(View view) {
		CommonUtil.hideKeyboard(MyApplication.getContext(), view);
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}

	private void initListener() {
		ll_share_friend.setOnClickListener(this);
		ll_share_friend_circle.setOnClickListener(this);
		ll_save_to_album.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ll_share_friend:
			Bitmap shareFriendBmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.download_qrcode);
			WxShareUtils.shareToWXSceneTimeline(shareFriendBmp, mContext, SendMessageToWX.Req.WXSceneSession);
			break;
		case R.id.ll_share_friend_circle:
			Bitmap shareCircleBmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.download_qrcode);
			WxShareUtils.shareToWXSceneTimeline(shareCircleBmp, mContext,SendMessageToWX.Req.WXSceneTimeline);
			break;
		case R.id.ll_save_to_album:
			Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.download_qrcode);
			if (ImgUtils.saveImageToGallery(mContext, bmp,"门口驿站下载码")){
				ToastUtils.INSTANCE.showToast("保存成功");
			}
			break;
		default:
			break;
		}
	}
}
