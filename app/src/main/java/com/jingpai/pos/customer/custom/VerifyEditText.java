package com.jingpai.pos.customer.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.InputFilter.LengthFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.github.cirno_poi.verificationedittextlibrary.R.color;
import com.github.cirno_poi.verificationedittextlibrary.R.drawable;
import com.github.cirno_poi.verificationedittextlibrary.R.styleable;
import com.github.cirno_poi.verifyedittextlibrary.HelperEditText;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VerifyEditText extends LinearLayout {
    private final int DEFAULT_INPUT_COUNT;
    private final int DEFAULT_LINE_HEIGHT;
    private final int DEFAULT_INPUT_SPACE;
    private final int DEFAULT_LINE_SPACE;
    private final int DEFAULT_TEXT_SIZE;
    private Context context;
    private List<HelperEditText> editTextList;
    private List<View> underlineList;
    private int currentPosition;
    private VerifyEditText.inputCompleteListener inputCompleteListener;
    @ColorInt
    private int lineFocusColor;
    @ColorInt
    private int lineDefaultColor;
    private boolean isAllLineLight;
    private int inputCount;
    private int lineHeight;
    private int inputSpace;
    private int lineSpace;
    private float textSize;
    @DrawableRes
    private int mCursorDrawable;

    public VerifyEditText(Context context) {
        this(context, (AttributeSet)null);
    }

    public VerifyEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.DEFAULT_INPUT_COUNT = 4;
        this.DEFAULT_LINE_HEIGHT = 1;
        this.DEFAULT_INPUT_SPACE = 15;
        this.DEFAULT_LINE_SPACE = 8;
        this.DEFAULT_TEXT_SIZE = 20;
        this.currentPosition = 0;
        this.lineFocusColor = ContextCompat.getColor(this.getContext(), 17170450);
        this.lineDefaultColor = ContextCompat.getColor(this.getContext(), color.colorDefault);
        this.isAllLineLight = false;
        this.inputCount = 4;
        this.textSize = 20.0F;
        this.mCursorDrawable = drawable.edit_cursor_shape;
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, styleable.VerifyEditText);
        if (typedArray != null) {
            this.inputCount = typedArray.getInteger(styleable.VerifyEditText_inputCount, 4);
            this.lineHeight = (int)typedArray.getDimension(styleable.VerifyEditText_underlineHeight, (float)this.dp2px(1));
            this.inputSpace = (int)typedArray.getDimension(styleable.VerifyEditText_inputSpace, (float)this.dp2px(15));
            this.lineSpace = (int)typedArray.getDimension(styleable.VerifyEditText_underlineSpace, (float)this.dp2px(8));
            this.textSize = typedArray.getDimension(styleable.VerifyEditText_mTextSize, 20.0F);
            this.lineFocusColor = typedArray.getColor(styleable.VerifyEditText_focusColor, ContextCompat.getColor(this.getContext(), 17170450));
            this.lineDefaultColor = typedArray.getColor(styleable.VerifyEditText_defaultColor, ContextCompat.getColor(this.getContext(), color.colorDefault));
            this.mCursorDrawable = typedArray.getResourceId(styleable.VerifyEditText_cursorDrawable, drawable.edit_cursor_shape);
            typedArray.recycle();
        }

        this.initView();
    }

    private void initView() {
        if (this.inputCount > 0) {
            this.editTextList = new ArrayList();
            this.underlineList = new ArrayList();
            this.setOrientation(0);
            this.setGravity(17);

            HelperEditText editText;
            for(int i = 0; i < this.inputCount; ++i) {
                LayoutParams flParams = new LayoutParams(0, -2, 1.0F);
                flParams.setMargins(i == 0 ? 0 : this.inputSpace, 0, 0, 0);
                FrameLayout frameLayout = new FrameLayout(this.context);
                frameLayout.setLayoutParams(flParams);
                FrameLayout.LayoutParams etParams = new FrameLayout.LayoutParams(-1, -2);
                editText = new HelperEditText(this.context);
                editText.setBackground((Drawable)null);
                editText.setPadding(0, 0, 0, this.lineSpace);
                editText.setMaxLines(1);
                editText.setTextSize(this.textSize);
                InputFilter[] filters = new InputFilter[]{new LengthFilter(1)};
                editText.setFilters(filters);
                editText.setInputType(2);
                editText.setGravity(17);

                try {
                    Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                    f.setAccessible(true);
                    f.set(editText, this.mCursorDrawable);
                } catch (Exception var9) {
                    var9.printStackTrace();
                }

                editText.setLayoutParams(etParams);
                frameLayout.addView(editText);
                FrameLayout.LayoutParams lineParams = new FrameLayout.LayoutParams(-1, this.lineHeight);
                lineParams.gravity = 80;
                View underline = new View(this.context);
                underline.setBackgroundColor(ContextCompat.getColor(this.context, color.colorDefault));
                underline.setLayoutParams(lineParams);
                frameLayout.addView(underline);
                this.addView(frameLayout);
                this.editTextList.add(editText);
                this.underlineList.add(underline);
            }

            TextWatcher textWatcher = new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                public void afterTextChanged(Editable s) {
                    if (!s.toString().isEmpty() && VerifyEditText.this.currentPosition < VerifyEditText.this.editTextList.size() - 1) {
                        VerifyEditText.this.currentPosition++;
                        ((HelperEditText)VerifyEditText.this.editTextList.get(VerifyEditText.this.currentPosition)).requestFocus();
                    }

                    if (VerifyEditText.this.isInputComplete() && VerifyEditText.this.inputCompleteListener != null) {
                        VerifyEditText.this.inputCompleteListener.inputComplete(VerifyEditText.this, VerifyEditText.this.getContent());
                    }

                }
            };
            OnFocusChangeListener onFocusChangeListener = (v, hasFocus) -> {
                for(int i = 0; i < this.editTextList.size(); ++i) {
                    if (((HelperEditText)this.editTextList.get(i)).isFocused()) {
                        this.currentPosition = i;
                    }

                    if (!this.isAllLineLight) {
                        ((View)this.underlineList.get(i)).setBackgroundColor(this.lineDefaultColor);
                    }
                }

                if (!this.isAllLineLight) {
                    ((View)this.underlineList.get(this.currentPosition)).setBackgroundColor(this.lineFocusColor);
                }

            };
            OnKeyListener keyListener = (v, keyCode, event) -> {
                if (keyCode == 67) {
                    if (event.getAction() != 0) {
                        return true;
                    } else {
                        if (((HelperEditText)this.editTextList.get(this.currentPosition)).getText().toString().isEmpty()) {
                            if (this.currentPosition <= 0) {
                                return true;
                            }

                            for(int position = this.currentPosition; position >= 0; --position) {
                                this.currentPosition = position;
                                if (!((HelperEditText)this.editTextList.get(position)).getText().toString().isEmpty()) {
                                    break;
                                }
                            }
                        }

                        ((HelperEditText)this.editTextList.get(this.currentPosition)).requestFocus();
                        ((HelperEditText)this.editTextList.get(this.currentPosition)).getText().clear();
                        return true;
                    }
                } else {
                    return false;
                }
            };
            Iterator var13 = this.editTextList.iterator();

            while(var13.hasNext()) {
                editText = (HelperEditText)var13.next();
                editText.addTextChangedListener(textWatcher);
                editText.setOnFocusChangeListener(onFocusChangeListener);
                editText.setOnKeyListener(keyListener);
            }

            ((HelperEditText)this.editTextList.get(0)).requestFocus();
        }
    }

    public String getContent() {
        if (this.editTextList == null) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            Iterator var2 = this.editTextList.iterator();

            while(var2.hasNext()) {
                HelperEditText et = (HelperEditText)var2.next();
                builder.append(et.getText().toString());
            }

            return builder.toString();
        }
    }

    public boolean isInputComplete() {
        if (this.editTextList == null) {
            return false;
        } else {
            Iterator var1 = this.editTextList.iterator();

            EditText et;
            do {
                if (!var1.hasNext()) {
                    return true;
                }

                et = (EditText)var1.next();
            } while(!et.getText().toString().isEmpty());

            return false;
        }
    }

    public void setAllLineLight(boolean flag) {
        this.isAllLineLight = flag;
        if (this.isAllLineLight) {
            Iterator var2 = this.underlineList.iterator();

            while(var2.hasNext()) {
                View v = (View)var2.next();
                v.setBackgroundColor(this.lineFocusColor);
            }
        }

    }

    public void setInputCompleteListener(VerifyEditText.inputCompleteListener inputCompleteListener) {
        this.inputCompleteListener = inputCompleteListener;
    }

    public int dp2px(int dp) {
        return (int)((float)dp * this.context.getResources().getDisplayMetrics().density + 0.5F);
    }

    public int getLineFocusColor() {
        return this.lineFocusColor;
    }

    public void setLineFocusColor(int lineFocusColor) {
        this.lineFocusColor = lineFocusColor;
    }

    public int getLineDefaultColor() {
        return this.lineDefaultColor;
    }

    public void setLineDefaultColor(int lineDefaultColor) {
        this.lineDefaultColor = lineDefaultColor;
    }

    public int getInputCount() {
        return this.inputCount;
    }

    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }

    public int getLineHeight() {
        return this.lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public int getInputSpace() {
        return this.inputSpace;
    }

    public void setInputSpace(int inputSpace) {
        this.inputSpace = inputSpace;
    }

    public int getLineSpace() {
        return this.lineSpace;
    }

    public void setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    public float getTextSize() {
        return this.textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getmCursorDrawable() {
        return this.mCursorDrawable;
    }

    public void setmCursorDrawable(int mCursorDrawable) {
        this.mCursorDrawable = mCursorDrawable;
    }

    public boolean isAllLineLight() {
        return this.isAllLineLight;
    }

    public interface inputCompleteListener {
        void inputComplete(VerifyEditText var1, String var2);
    }
}
