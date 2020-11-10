package com.jingpai.pos.customer.component.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by HCM on 2017/11/15.
 */

public class MessageBox {
    Activity context;
    AlertDialog.Builder builder;
    public AlertDialog dialog;

    public MessageBox(Activity _context) {
        this.context = _context;
        builder = new AlertDialog.Builder(context);
    }

    public void ShowDlg(String tile, String txt, DialogInterface.OnClickListener ok_click, DialogInterface.OnClickListener can_click) {

        builder.setMessage(txt)
                .setTitle(tile)
                .setCancelable(false)
                .setPositiveButton("确定", ok_click);

        if (can_click != null) {
            builder.setNegativeButton("取消", can_click);
        }
        dialog = builder.create();
        if (this.context!=null && this.context.isFinishing())return;
        dialog.show();
    }

    public void ShowDlg(int tile, String txt, DialogInterface.OnClickListener ok_click, DialogInterface.OnClickListener can_click) {
        builder.setMessage(txt)
                .setTitle(tile)
                .setCancelable(false)
                .setPositiveButton("确定", ok_click);

        if (can_click != null) {
            builder.setNegativeButton("取消", can_click);
        }
        dialog = builder.create();
        dialog.show();
    }

    public boolean isShow() {
        if (dialog!=null)
            return dialog.isShowing();
        return false;
    }
}
