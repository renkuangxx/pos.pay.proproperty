package com.jingpai.pos.utils

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import com.jingpai.pos.customer.base.MyApplication

/**
 * toast工具类
 *
 */
object ToastUtils {
    private var toast: Toast? = null
    /**
     * 显示提示信息
     */
    fun showToast(context: Context?, text: String?) {
        if (TextUtils.isEmpty(text)) return
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(text)
        }
        toast!!.setGravity(Gravity.CENTER,0,0)
        toast!!.duration
        toast!!.view
        toast!!.show()
    }

    /**
     * 显示提示信息
     */
    fun showToast(text: String?) {
        if (TextUtils.isEmpty(text)) return
//        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT)
//        } else {
//            toast!!.setText(text)
//        }
        toast!!.setGravity(Gravity.CENTER,0,0)
        toast!!.duration
        toast!!.view
        toast!!.show()
    }

    /**
     * 显示提示信息
     */
    fun showToast(context: Context?, rId: Int) {
        if (toast == null) {
            toast = Toast.makeText(context, rId, Toast.LENGTH_SHORT)
        } else {
            toast!!.setText(rId)
        }
        toast!!.show()
    }

    /**
     * 显示提示信息(时间较长)
     */
    fun showLongToast(context: Context?, text: String?) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        } else {
            toast!!.setText(text)
        }
        toast!!.show()
    }

    /**
     * 显示提示信息(时间较长)
     */
    fun showLongToast(context: Context?, rId: Int) {
        if (toast == null) {
            toast = Toast.makeText(context, rId, Toast.LENGTH_LONG)
        } else {
            toast!!.setText(rId)
        }
        toast!!.show()
    }
}