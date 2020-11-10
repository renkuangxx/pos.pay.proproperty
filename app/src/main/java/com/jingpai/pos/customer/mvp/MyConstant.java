package com.jingpai.pos.customer.mvp;

import android.os.Environment;

import com.jingpai.pos.customer.base.MyApplication;

/*
 * function:
 */
public class MyConstant {
    public final static String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + MyApplication.getContext().getPackageName();
    public final static String DOWNLOAD_DIR = "/downlaod/";
}
