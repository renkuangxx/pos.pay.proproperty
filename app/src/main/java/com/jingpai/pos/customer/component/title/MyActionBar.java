package com.jingpai.pos.customer.component.title;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jingpai.pos.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyActionBar {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_back)
    View backView;

    WeakReference<AppCompatActivity> activityWeakReference;

    public MyActionBar(final AppCompatActivity activity, int titleRes) {
        View actionBarView = LayoutInflater.from(activity).inflate(R.layout.action_bar, null);
        actionBarView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ButterKnife.bind(this, actionBarView);
        tvTitle.setText(titleRes);
    }

    @OnClick(R.id.iv_back)
    public void back(View view) {
        AppCompatActivity activity = activityWeakReference.get();
        if (activity != null) {
            activity.finish();
        }
    }




}
