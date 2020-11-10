package com.jingpai.pos.customer.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jingpai.pos.R;

/**
 * 自定义toolbar
 * 2020/3/13
 */
public class CustomToolBar extends RelativeLayout {
    private TextView mTvTitle;
    private ImageView mImageBack;
    private TextView mRightTvTitle;

    //属性
    private int mIvBackId;
    private String mStrTitle;
    private int mTitleColor;
    private float mTitleTextSize;
    private String mRightStrTitle;
    private int mRightTitleColor;
    private float mRightTitleTextSize;
    /**
     * 默认返回图标
     */
    private int defaultBackId= R.drawable.btn_back_arrow;

    /**
     *默认title颜色
     */
    private int defaultTitleTextColor =getResources().getColor(R.color.title_text_color);
    /**
     *默认rightTitle颜色
     */
    private int defaultRightTitleTextColor =getResources().getColor(R.color.main);
    private int defaultBackgroundColor =getResources().getColor(R.color.white);

    public CustomToolBar(Context context) {
        super(context);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
        initData();
    }

    private void initData() {
        this.setBackgroundColor(defaultBackgroundColor);
        if (!TextUtils.isEmpty(mStrTitle)) {
            setTitle(mStrTitle);
        }
        setTitleColor(mTitleColor);
        setTitleTextSize(mTitleTextSize);
        if (!TextUtils.isEmpty(mRightStrTitle)) {
            mRightTvTitle.setVisibility(VISIBLE);
            setRightTitle(mRightStrTitle);
        }

        setRightTitleColor(mRightTitleColor);
        setRightTitleTextSize(mRightTitleTextSize);


        mImageBack.setImageResource(mIvBackId);
        mImageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }

    private void initAttrs(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolBar);
        mIvBackId = typedArray.getResourceId(R.styleable.CustomToolBar_back_icon, defaultBackId);
        mStrTitle = typedArray.getString(R.styleable.CustomToolBar_title);
        mTitleColor = typedArray.getColor(R.styleable.CustomToolBar_title_color, defaultTitleTextColor);
        mTitleTextSize=typedArray.getDimension(R.styleable.CustomToolBar_title_text_size,18.0f);

        mRightStrTitle = typedArray.getString(R.styleable.CustomToolBar_right_title);
        mRightTitleColor = typedArray.getColor(R.styleable.CustomToolBar_right_title_color, defaultRightTitleTextColor);
        mRightTitleTextSize=typedArray.getDimension(R.styleable.CustomToolBar_right_title_text_size,14.0f);
        defaultBackgroundColor = typedArray.getColor(R.styleable.CustomToolBar_android_background, defaultBackgroundColor);
        typedArray.recycle();
    }

    private void initView(Context context) {
        View rootView = inflate(context, R.layout.action_bar, this);
        mTvTitle = rootView.findViewById(R.id.tv_title);
        mImageBack = rootView.findViewById(R.id.iv_back);
        mRightTvTitle = rootView.findViewById(R.id.tv_right_btn);
        //默认右侧标题不显示
        mRightTvTitle.setVisibility(GONE);

    }

    /**
     * 设置返回键点击事件，如果不设置，默认是点击finish()界面
     *
     * @param clickListener 自己要处理的点击回调（如 像上一个界面返回数据）
     */
    public void setBackClick(OnClickListener clickListener) {
        mImageBack.setOnClickListener(clickListener);
    }

    public void setBackVisitivity(boolean visitivity) {
        mImageBack.setVisibility(visitivity?VISIBLE:GONE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setTitleColor(int color) {
        mTvTitle.setTextColor(color);
    }

    /**
     * 设置标题字体大小  （sp）
     * @param size  字体大小
     */
    public void setTitleTextSize(float size){
        mTvTitle.setTextSize(size);
    }

    /**
     * 设置右侧标题
     *
     * @param title
     */
    public void setRightTitle(String title) {
        if (mRightTvTitle.getVisibility() == GONE)
            mRightTvTitle.setVisibility(VISIBLE);
        mRightTvTitle.setText(title);
    }

    /**
     * 设置右侧标题颜色
     *
     * @param color
     */
    public void setRightTitleColor(int color) {
        if (mRightTvTitle.getVisibility() == GONE)
            mRightTvTitle.setVisibility(VISIBLE);
        mRightTvTitle.setTextColor(color);
    }
    public void setRightTitleVisibility(boolean visibility){
        mRightTvTitle.setVisibility(visibility?VISIBLE:GONE);
    }
    /**
     * 设置右侧标题字体大小  （sp）
     * @param size  字体大小
     */
    public void setRightTitleTextSize(float size){
        mRightTvTitle.setTextSize(size);
    }
    /**
     * 设置右侧标题点击事件
     * @param click     点击事件
     */
    public void setRightTvTitleClick(OnClickListener click){
        mRightTvTitle.setOnClickListener(click);
    }
    public TextView getmTvTitle() {
        return mTvTitle;
    }

    public void setmTvTitle(TextView mTvTitle) {
        this.mTvTitle = mTvTitle;
    }

    public ImageView getmImageBack() {
        return mImageBack;
    }

    public void setmImageBack(ImageView mImageBack) {
        this.mImageBack = mImageBack;
    }

    public TextView getmRightTvTitle() {
        return mRightTvTitle;
    }

    public void setmRightTvTitle(TextView mRightTvTitle) {
        this.mRightTvTitle = mRightTvTitle;
    }
}
