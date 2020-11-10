package com.jingpai.pos.customer.views;

/**
 * Create by liujinheng
 * date 2020/6/1
 * function
 */

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;

public class ToastPopuWindow {
    private PopupWindow popupWindow;
    private Context mContext;
    private static ToastPopuWindow myPopuWindow;

    public static ToastPopuWindow getInstance(){
        if(myPopuWindow==null){
            myPopuWindow=new ToastPopuWindow();
        }
        return myPopuWindow;
    }

    /**
     *
     * @param mContext
     * @param mainView
     */
    public void setPopupWindow(Context mContext, View mainView,String tipString) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popu_toast, null);
        TextView tip = (TextView) contentView.findViewById(R.id.tv_tip);
        tip.setText(tipString);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.popWindowAnim);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        ColorDrawable dw = new ColorDrawable(MyApplication.getContext().getResources().getColor(R.color.black));
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(mainView, Gravity.BOTTOM, 0, 0);

    }
    //关闭 popupWindow
    private void hide() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public interface PopuClickListener{
        void onScan();
        void onGame();
    }
}
