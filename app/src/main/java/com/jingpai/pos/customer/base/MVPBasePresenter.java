package com.jingpai.pos.customer.base;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */

public class MVPBasePresenter<T extends MVPBaseContract.BaseView> implements MVPBaseContract.BasePresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}