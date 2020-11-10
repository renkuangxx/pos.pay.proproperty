package com.jingpai.pos.customer.base;
/**
 * Create by liujinheng
 * date 2020/5/18
 * function
 */
import com.trello.rxlifecycle2.LifecycleTransformer;

public interface MVPBaseContract {

    interface BasePresenter<T extends MVPBaseContract.BaseView> {

        void attachView(T view);

        void detachView();
    }


    interface BaseView {

        //显示进度中
        void showLoading();

        //隐藏进度
        void hideLoading();

        //显示请求成功
        void showSuccess(String message);

        //失败重试
        void showFailed(String message);

        //显示当前网络不可用
        void showNoNet();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();

        /**
         * 跳转到登录页面
         */
        void jumpToLogin();
    }
}
