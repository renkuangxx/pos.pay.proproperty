package com.gointern.camerademo.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.cameralib.base.activity.BaseFileActivity
import com.gointern.camerademo.util.LogUtil

/**
 * Copyright , 2015-2019 <br></br>
 * Author: 陈刘磊 1070379530@qq.com <br></br>
 * Date: 2019/6/18 14:35    <br></br>
 * Description: BaseActivity  <br></br>
 */
open class BaseActivity : BaseFileActivity() {
    protected var TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtil.i("life", "$TAG-----onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        LogUtil.i("life", "$TAG-----onNewIntent")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.i("life", "$TAG-----onRestart")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.i("life", "$TAG-----onStart")

    }

    override fun onResume() {
        super.onResume()
        LogUtil.i("life", "$TAG-----onResume")

    }

    override fun onPause() {
        super.onPause()
        LogUtil.i("life", "$TAG-----onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.i("life", "$TAG-----onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i("life", "$TAG-----onDestroy")

    }



}
