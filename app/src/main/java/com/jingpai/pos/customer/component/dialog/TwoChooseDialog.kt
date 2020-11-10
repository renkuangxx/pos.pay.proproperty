package com.jingpai.pos.customer.component.dialog

import android.content.Context
import android.view.View
import android.widget.TextView
import com.jingpai.pos.R
import kotlinx.android.synthetic.main.two_choose_dialog.*

class TwoChooseDialog(context: Context) : BaseDialog(context) {
    override fun getLayoutId(): Int {
        return R.layout.two_choose_dialog
    }

    override fun init() {
        setCanceledOnTouchOutside(false)
        initLayoutParamsForBottom()

        tv_cancel.setOnClickListener { dismiss() }

    }

    fun setTextOne(text:String){
        tv_one.text = text
    }

    fun setTextTwo(text:String){
        tv_two.text = text
    }

    fun setTextOneClick(onClickListener: View.OnClickListener){
        tv_one.setOnClickListener(onClickListener)
    }
    fun setTextTwoClick(onClickListener: View.OnClickListener){
        tv_two.setOnClickListener(onClickListener)
    }
    fun getTvAddCar(): TextView {
        return tv_one
    }

    fun getTvAddCarSet(): TextView {
        return tv_two
    }



}