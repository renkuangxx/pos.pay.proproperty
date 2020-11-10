package com.jingpai.pos.customer.component.keyboard;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.utils.PixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义车牌
 */
public class PlateNumber implements View.OnFocusChangeListener, Keyboard.OnClickListener, View.OnClickListener{

    /**
     * 车牌最小位数
     */
    public static final int MIN_NUMBER = 7;

    /**
     * 车牌最大位数
     */
    public static final int MAX_NUMBER = 8;

    private List<EditText> plateNumberList = new ArrayList<>(MAX_NUMBER);

    private EditText currentEditText;

    private Keyboard keyboard;

    private PixelUtil pixelUtil;

    public PlateNumber(Activity context, Keyboard keyboard, LinearLayout linearLayout) {
        pixelUtil = new PixelUtil(context);

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        for(int i = 0; i < MAX_NUMBER; i++) {
            EditText editText = new EditText(context);
            editText.setTag(i);
            editText.setShowSoftInputOnFocus(false);
            editText.setOnFocusChangeListener(this);
            editText.setOnClickListener(this);
            editText.setGravity(Gravity.CENTER);
            editText.setPadding(0, pixelUtil.dp2px(8), 0, pixelUtil.dp2px(12));
            editText.setLayoutParams(layoutParams);
            editText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            editText.setBackgroundResource(R.drawable.plate_number_selector);

            linearLayout.addView(editText);
            plateNumberList.add(editText);
        }

        this.keyboard = keyboard;
        this.keyboard.setOnClickListener(this);


    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus) {
            currentEditText = (EditText) view;
            currentEditText.setSelection(currentEditText.getText().toString().length());
            if ("0".equals(view.getTag().toString())) {
                keyboard.showProvince();
            } else {
                keyboard.showKeyword();
            }
        }
    }

    @Override
    public void onKeyboard(String value) {
        Integer index = Integer.valueOf(currentEditText.getTag().toString());
        String oldValue = currentEditText.getText().toString();
        currentEditText.setText(value);

        if("".equals(value)) {
            if("".equals(oldValue)) {
                index--;
                if(index < 0) {
                    index = 0;
                }

                plateNumberList.get(index).setText("");
            }
        } else {
            index++;
        }

        if(index < plateNumberList.size()) {
            plateNumberList.get(index).requestFocus();
        } else {
            keyboard.hide();
        }
    }

    @Override
    public void onClick(View v) {
        Integer index = Integer.valueOf(currentEditText.getTag().toString());
        if (index == 0) {
            keyboard.showProvince();
        } else {
            keyboard.showKeyword();
        }

        currentEditText.setSelection(currentEditText.getText().length());
    }

    /**
     * 获取车牌号码
     * @return
     */
    public String getPlateNumber() {
        StringBuilder plateNumber = new StringBuilder(MAX_NUMBER);

        for (EditText editText : plateNumberList) {
            plateNumber.append(editText.getText().toString());
        }

        return plateNumber.toString();
    }
}
