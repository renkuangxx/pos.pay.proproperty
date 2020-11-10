package com.jingpai.pos.customer.component.dialog;

import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.component.keyboard.Keyboard;

public class KeyBoardDialog extends BaseDialog {
    private Keyboard keyboard;
    private Keyboard.OnClickListener onClickListener;
    private Activity activity;
    public KeyBoardDialog(@NonNull Context context,Activity activity) {
        super(context);
        this.activity=activity;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_keyboard;
    }

    @Override
    public void init() {
        initLayoutParamsForBottom();
        LinearLayout linearLayout = findViewById(R.id.ll_content);
        if (activity!=null) {
            keyboard = new Keyboard(activity, linearLayout);
            keyboard.setOnClickListener(getOnClickListener());
        }

    }

    public Keyboard.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(Keyboard.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void showProvince() {
        keyboard.showProvince();

    }

    public void showKeyword() {
        keyboard.showKeyword();
    }
}
