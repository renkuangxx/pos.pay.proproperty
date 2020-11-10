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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.PhoneAdapter;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.ManagerBean;
import com.jingpai.pos.customer.utils.CommonUtil;
import com.jingpai.pos.customer.utils.SystemUtils;

import java.util.List;

public class CallPhoneDialog implements View.OnClickListener {
	private TextView tv_cancel;
	private OnClickListener okListener;
	private String mPhone;
	private RecyclerView list_phone;
	private View rl_dialog;
	private PopupWindow popupWindow;
	private View contentView;
	private View mainView;
	private Activity mContext;
	private PhoneAdapter phoneAdapter;
	public CallPhoneDialog(Activity context, List<ManagerBean> managerList) {
		mContext = context;
		mainView = context.getWindow().getDecorView().findViewById(android.R.id.content);
		contentView = LayoutInflater.from(context).inflate( R.layout.dialog_show_phone, null);
		rl_dialog = contentView.findViewById(R.id.rl_dialog);
		list_phone = contentView.findViewById(R.id.list_phone);
		tv_cancel = contentView.findViewById(R.id.tv_cancel);
		popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setAnimationStyle(R.style.dialogWindowAnim);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		initListener();
		backgroundAlpha(context,0.4f);
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha(context,1f);
			}
		});
		ValueAnimator animator = ValueAnimator.ofFloat(0, 50);
		animator.setDuration(500);
		animator.setTarget(rl_dialog);
		animator.start();
		phoneAdapter = new PhoneAdapter(R.layout.item_phone,managerList);
		list_phone.setLayoutManager(new LinearLayoutManager(mContext));
		list_phone.setAdapter(phoneAdapter);
		phoneAdapter.notifyDataSetChanged();
		phoneAdapter.setOnItemClick(phone -> SystemUtils.callPhone1(phone));
	}
	private void backgroundAlpha(Activity activity,float f) {
		WindowManager.LayoutParams lp =activity.getWindow().getAttributes();
		lp.alpha = f;
		activity.getWindow().setAttributes(lp);
	}
//	public void setOkListener(OnClickListener okListener) {
//		this.okListener = okListener;
//	}
	

	
	public void show(View view) {
		CommonUtil.hideKeyboard(MyApplication.getContext(), mainView);
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}
	
	private void initListener() {
		tv_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		popupWindow.dismiss();
		switch (v.getId()) {
		case R.id.tv_cancel:
//			if (okListener != null) {
//				okListener.onClick(null, 1);
//			}
			break;
//		case R.id.tv_show_phone:
//			if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//				//没有申请权限,做权限的申请处理 参数:1.上下文 2.字符串数组,可以一次申请多个权限 3.int型,请求码方便我们后面权限区分
//				ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CALL_PHONE}, 0);
//			} else {
//				//3.申请权限了,直接做打电话的业务逻辑
////                        doCallPhone();
//				SystemUtils.callPhone(mPhone);
//			}
//			break;
		default:
			break;
		}
	}
}
