package com.jingpai.pos.customer.views;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.adapter.ButtonListAdapter;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.ButtonListBean;
import com.jingpai.pos.customer.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class MoreFunctionDialog implements View.OnClickListener {
	RecyclerView rl_button;
	private View rl_dialog;
	private PopupWindow popupWindow;
	private View contentView;
	private View mainView;
	private Activity mContext;
	private List<ButtonListBean> buttonListBeans = new ArrayList<>();
	private ButtonListAdapter buttonListAdapter;
	private OnClick myOnClick;
	public interface OnClick{
		public void onItemclick(String finalLinkUrl);
	}
	public MoreFunctionDialog(Activity context) {
		mContext = context;
		mainView = context.getWindow().getDecorView().findViewById(android.R.id.content);
		contentView = LayoutInflater.from(context).inflate( R.layout.dialog_more_function, null);
		rl_dialog = contentView.findViewById(R.id.rl_dialog);
		popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
		backgroundAlpha(context,0.5f);
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
	public void setData(List<ButtonListBean> buttonListBeans){
		if (buttonListBeans == null || buttonListBeans.size() == 0) {
			return;
		}
		buttonListAdapter.setDataChange(buttonListBeans);
	}
	private void initFunction() {
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 5);
		rl_button.setLayoutManager(layoutManager);
		buttonListAdapter = new ButtonListAdapter(buttonListBeans, mContext);
		buttonListAdapter.setClickListener(new ButtonListAdapter.OnClick() {
			@Override
			public void onMoreFunclick() {
			}

			@Override
			public void onItemclick(String finalLinkUrl) {
				if (myOnClick != null) {
					myOnClick.onItemclick(finalLinkUrl);
				}
			}
		});
		rl_button.setAdapter(buttonListAdapter);
	}

	public void setClickListener(OnClick okListener) {
		this.myOnClick = okListener;
	}
	private void initView() {
		rl_button = (RecyclerView) contentView.findViewById(R.id.rl_button);rl_button.setNestedScrollingEnabled(false);
		initFunction();
	}
	
	public void show(View view) {
		CommonUtil.hideKeyboard(MyApplication.getContext(), view);
		popupWindow.showAsDropDown(view, Gravity.TOP, 0, 0);
	}
	
	private void initListener() {
	}

	@Override
	public void onClick(View v) {
		popupWindow.dismiss();
		switch (v.getId()) {
		case R.id.tv_cancel:
			break;
		default:
			break;
		}
	}
}
