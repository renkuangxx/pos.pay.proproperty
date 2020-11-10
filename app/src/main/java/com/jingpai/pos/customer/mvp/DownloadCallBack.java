package com.jingpai.pos.customer.mvp;

/*
 * function:
 */
public interface DownloadCallBack {
    void onProgress(int progress);

    void onCompleted();

    void onError(String msg);
}
