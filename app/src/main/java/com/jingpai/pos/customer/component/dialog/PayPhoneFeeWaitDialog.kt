package com.jingpai.pos.customer.component.dialog

import android.content.Context
import android.view.View
import com.jingpai.pos.R
import kotlinx.android.synthetic.main.pay_phone_fee_wait_dialog.*

class PayPhoneFeeWaitDialog(context: Context) : BaseDialog(context) {
    override fun getLayoutId(): Int {
        return R.layout.pay_phone_fee_wait_dialog
    }

    override fun init() {
        setCanceledOnTouchOutside(false)
        initLayoutParamsForBottom()
    }

    public fun setPaySuccessClick(click: View.OnClickListener){
        tv_pay_success.setOnClickListener(click)
        tv_not_pay.setOnClickListener (click)
    }

}