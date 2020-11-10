package com.jingpai.pos.customer.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jingpai.pos.R

class PayPhoneFeeKeyBoardFragment : Fragment() {


    companion object{
        var instance: PayPhoneFeeKeyBoardFragment? = null;
        fun newInstance(): PayPhoneFeeKeyBoardFragment {
            if (instance == null) {
                instance = PayPhoneFeeKeyBoardFragment();
            }
            return instance as PayPhoneFeeKeyBoardFragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_pay_phone_fee_kb,container,false)
    }

}