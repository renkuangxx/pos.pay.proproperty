package com.jingpai.pos.customer.views;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.bean.CommunityBean;
import com.jingpai.pos.customer.adapter.CommunityAdapter;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectCommunityDialog implements View.OnClickListener {
	RecyclerView rl_button;
	private OnClickListener okListener;
	private View rl_dialog;
	private PopupWindow popupWindow;
	private View contentView;
	private View mainView;
	private ImageView ivBack;
	private TextView tv_city_name;
	private Activity mContext;
	private List<CommunityBean> mCommunityList =new ArrayList<>();
	private CommunityAdapter communityAdapter;
	public SelectCommunityDialog(Activity context) {
		mContext = context;
		mainView = context.getWindow().getDecorView().findViewById(android.R.id.content);
		contentView = LayoutInflater.from(context).inflate( R.layout.dialog_select_community, null);
		rl_dialog = contentView.findViewById(R.id.rl_dialog);
		ivBack = contentView.findViewById(R.id.iv_back);
		tv_city_name = contentView.findViewById(R.id.tv_city_name);
		popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
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
//				rl_dialog.setBackgroundColor(Color.argb(
//						(int) (200 * value / 50), 0, 0, 0));
			}
		});
		animator.setDuration(500);
		animator.setTarget(rl_dialog);
		animator.start();
	}
	public void setData(List<CommunityBean> communityList,String cityName){
		if (communityList == null || communityList.size() == 0) {
			return;
		}

		mCommunityList.clear();
		mCommunityList.addAll(communityList);
		communityAdapter.notifyDataSetChanged();
		tv_city_name.setText(cityName);
	}
	private void initView() {
		rl_button = (RecyclerView) contentView.findViewById(R.id.rl_community);
		rl_button.setNestedScrollingEnabled(false);
		initFunction();
	}
	private void initFunction() {
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
		rl_button.setLayoutManager(layoutManager);
		communityAdapter = new CommunityAdapter(R.layout.item_city,mCommunityList);
		communityAdapter.setOnItemClick(new CommunityAdapter.OnItemClick() {
			@Override
			public void onItemClick(int position) {
				popupWindow.dismiss();
				okListener.onClick(null,position);
			}
		});
		rl_button.setAdapter(communityAdapter);
	}

	public void setOkListener(OnClickListener okListener) {
		this.okListener = okListener;
	}
	

	
	public void show(View view) {
		CommonUtil.hideKeyboard(MyApplication.getContext(), view);
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}
	
	private void initListener() {
		ivBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		popupWindow.dismiss();
		switch (v.getId()) {
		case R.id.iv_back:
			popupWindow.dismiss();
//			if (okListener != null) {
//				okListener.onClick(null, 1);
//			}
			break;
		default:
			break;
		}
	}
}
