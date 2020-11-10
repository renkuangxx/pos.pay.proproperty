package com.jingpai.pos.presenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.utils.LogUtil;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BasePresenter {

    public static final String PAGE_SIZE_PARAM = "pageSize";
    public static final int PAGE_SIZE_VALUE = 50;

    public static interface Callback<T> {
        void success(T t);
        void failure(T t);
    }

    protected void exeCallback(Observable observable, Callback<JSONArray> callback) {
        exeCallback(observable, JSONArray.class, callback);
    }
    protected void exeCallbackPOS(Observable observable, Callback<JSONArray> callback) {
        exeCallbackPOS(observable, JSONArray.class, callback);
    }

    protected <T> void exeCallback(Observable observable, Class<T> cls, Callback<T> callback) {

        if (observable == null) {
            return;
        }

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() { }
                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(R.string.system_error);
                        try {
                            callback.failure(null);
                        }catch (Exception ex){
                        }

                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            if (callback != null) {
                                JSONObject response = JSONObject.parseObject(responseBody.string());
                                if (response.getBoolean("success")) {
                                    T dataInstance = response.getObject("data", cls);
                                    if (dataInstance == null){
                                        dataInstance = cls.newInstance();
                                    }
                                    callback.success(dataInstance);
                                } else {
                                    String message = response.getString("returnMsg");
                                    if (message != null && !message.isEmpty()) {
                                        if ("5".equals(response.getString("returnCode"))) {
                                            ToastUtils.showShort("登录已过期，请重新登录");
                                            MyApplication.gotoLogin();
                                        } else {
                                            ToastUtils.showShort(message);
                                            callback.failure(null);
                                        }
                                    }else{
                                        message = response.getString("message");
                                        if (message != null && !message.isEmpty()) {
                                            if ("5".equals(response.getString("code"))) {
                                                ToastUtils.showShort("登录已过期，请重新登录");
                                                MyApplication.gotoLogin();
                                            } else {
                                                ToastUtils.showShort(message);
                                                callback.failure(null);
                                            }
                                        }

                                    }
                                }
                            }

                        } catch (Exception e) {
                            LogUtil.e(LogUtil.URL_TAG,e.toString());
                        }
                    }
                });
    }
    protected <T> void exeCallbackPOS(Observable observable, Class<T> cls, Callback<T> callback) {

        if (observable == null) {
            return;
        }

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() { }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(LogUtil.URL_TAG+"++++++++", e.toString());
                        ToastUtils.showShort(R.string.system_error);
                        try {
                            callback.success(null);
                        }catch (Exception ex){
                            LogUtil.e(LogUtil.URL_TAG,ex.toString());
                        }

                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            if (callback != null) {
                                JSONObject response = JSONObject.parseObject(responseBody.string());
                                if ("OK".equals(response.getString("status"))) {
                                    T dataInstance = response.getObject("data", cls);
                                    if (dataInstance == null){
                                        dataInstance = cls.newInstance();
                                    }
                                    callback.success(dataInstance);
                                } else {
                                    String message = response.getString("returnMsg");
                                    if (message != null && !message.isEmpty()) {
                                        if ("5".equals(response.getString("returnCode"))) {
                                            ToastUtils.showShort("登录已过期，请重新登录");
                                            MyApplication.gotoLogin();
                                        } else {
                                            ToastUtils.showShort(message);
                                            callback.success(null);
                                        }
                                    }else{
                                        message = response.getString("message");
                                        if (message != null && !message.isEmpty()) {
                                            if ("5".equals(response.getString("code"))) {
                                                ToastUtils.showShort("登录已过期，请重新登录");
                                                MyApplication.gotoLogin();
                                            } else {
                                                ToastUtils.showShort(message);
                                                callback.success(null);
                                            }
                                        }

                                    }
                                }
                            }

                        } catch (Exception e) {
                            callback.success(null);
                            LogUtil.e(LogUtil.URL_TAG,e.toString());
                        }
                    }
                });
    }

}
