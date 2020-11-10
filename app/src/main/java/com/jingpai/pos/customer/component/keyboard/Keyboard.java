package com.jingpai.pos.customer.component.keyboard;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jingpai.pos.R;

/**
 * 自定义车牌输入法
 */
public class Keyboard implements View.OnTouchListener, View.OnClickListener{

    private String[] provinceArray =  {"京", "沪", "津", "渝", "鲁", "冀", "晋", "蒙", "辽", "吉", "黑", "苏", "浙", "皖", "闽", "赣", "豫", "湘", "鄂", "粤", "桂", "琼", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "港", "澳", "台"};

    private String[] keywordArray =  {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "Q", "W", "E", "R", "T", "Y", "U", "P", "警","挂",
            "A", "S", "D", "F", "G", "H", "J", "K", "L", "学",
            "Z", "X", "C", "V", "B", "N", "M", "领", "使"};


    private LinearLayout provinceLayout;

    private LinearLayout keywordLayout;

    private OnClickListener onClickListener;


    public Keyboard(@NonNull Activity context, LinearLayout keyboardLayout) {
        keyboardLayout.setLongClickable(true);
        provinceLayout = new LinearLayout(context);
        provinceLayout.setBackgroundResource(R.color.home_line_1);
        keywordLayout = new LinearLayout(context);
        keywordLayout.setBackgroundResource(R.color.home_line_1);
        initLayout(context, keyboardLayout, provinceLayout, provinceArray);
        initLayout(context, keyboardLayout, keywordLayout, keywordArray);

        View rootView = context.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setOnClickListener(this);
    }

    public void initLayout(Activity context, LinearLayout keyboardLayout, LinearLayout layout, String[] wordArray) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int margin = 8;
        int marginHeight = 8;
        int width = (size.x - margin) / 10 - margin;

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setVisibility(View.GONE);

        LinearLayout rowLayout = null;
        LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, width + 2 * marginHeight);
        layoutParams.setMargins(0, 10, 0, 10);

        LinearLayout.LayoutParams wordLayoutParam = new LinearLayout.LayoutParams(width, width);
        wordLayoutParam.setMargins(margin, margin,0, margin);
        for(int i = 0; i < wordArray.length; i++) {
            if(i % 10 == 0) {
                rowLayout = new LinearLayout(context);
                rowLayout.setLayoutParams(layoutParams);
                rowLayout.setGravity(Gravity.CENTER_VERTICAL);

                layout.addView(rowLayout);
            }

            TextView textView = new TextView(context);
            textView.setTag(wordArray[i]);
            textView.setText(wordArray[i]);
            textView.setLayoutParams(wordLayoutParam);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.keyboard);
            textView.setLongClickable(true);
            textView.setOnTouchListener(this);
            textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            rowLayout.addView(textView);
        }

        int padingWidth = width / 5;
        ImageView imageView = new ImageView(context);
        imageView.setTag("");
        imageView.setLayoutParams(wordLayoutParam);
        imageView.setImageResource(R.drawable.ic_del);
        imageView.setBackgroundResource(R.drawable.keyboard);
        imageView.setPadding(padingWidth, 0, padingWidth, 0);
        imageView.setLongClickable(true);
        imageView.setOnTouchListener(this);
        rowLayout.addView(imageView);


        keyboardLayout.addView(layout);
    }

    public void showKeyword() {
        provinceLayout.setVisibility(View.GONE);
        keywordLayout.setVisibility(View.VISIBLE);
    }

    public void showProvince() {
        provinceLayout.setVisibility(View.VISIBLE);
        keywordLayout.setVisibility(View.GONE);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void hide() {
        provinceLayout.setVisibility(View.GONE);
        keywordLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            Object tag = v.getTag();
            if (onClickListener != null) {
                String value = v.getTag().toString();
                onClickListener.onKeyboard(value);
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        hide();
    }

    public static interface OnClickListener {
        void onKeyboard(String value);
    }
}
