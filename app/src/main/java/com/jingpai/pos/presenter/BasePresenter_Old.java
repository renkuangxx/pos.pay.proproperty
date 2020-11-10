package com.jingpai.pos.presenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.utils.LogUtil;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BasePresenter_Old {

    public static final String PAGE_SIZE_PARAM = "pageSize";
    public static final int PAGE_SIZE_VALUE = 50;

    public static interface Callback<T> {
        void success(T t);
    }

    protected void exeCallback(Observable observable, Callback<JSONArray> callback) {
        exeCallback(observable, JSONArray.class, callback);
    }
    protected void exeCallback4Game(Observable observable, Callback<JSONArray> callback) {
        exeCallback4Game(observable, JSONArray.class, callback);
    }

    protected <T> void exeCallback(Observable observable, Class<T> cls, Callback<T> callback) {

        if (observable == null) {
            return;
        }

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(LogUtil.URL_TAG + "++++++++", e.toString());
                        ToastUtils.showShort(R.string.system_error);
                        try {
                            callback.success(null);
                        } catch (Exception ex) {
                            LogUtil.e(LogUtil.URL_TAG, ex.toString());
                        }

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            if (callback != null) {
                                JSONObject response = JSONObject.parseObject(responseBody.string());
                                if (TextUtils.isEmpty(response.getString("status") )){
                                    if (response.getBoolean("success")) {
                                        T dataInstance = response.getObject("data", cls);
                                        if (dataInstance == null) {
                                            dataInstance = cls.newInstance();
                                        }
                                        callback.success(dataInstance);
                                    } else {
                                        String message = response.getString("returnMsg");
                                        if (message != null && !message.isEmpty()) {
                                            if ("5".equals(response.getString("returnCode"))||"25".equals(response.getString("returnCode"))) {
                                                ToastUtils.showShort(message);
                                                MyApplication.gotoLogin();
                                            }else {
                                                ToastUtils.showShort(message);
                                                callback.success(null);
                                            }
                                        } else {
                                            message = response.getString("message");
                                            if (message != null && !message.isEmpty()) {
                                                if ("5".equals(response.getString("code"))) {
                                                    ToastUtils.showShort("登录已过期，请重新登录");
                                                    MyApplication.gotoLogin();
                                                } else {
                                                    ToastUtils.showShort(message);
                                                    callback.success(null);
                                                }
                                            }else {
                                                callback.success(null);
                                            }

                                        }
                                    }
                                }else if (response.size()==5 && !TextUtils.isEmpty(response.getString("status"))){
                                    String status = response.getString("status");
                                    if (TextUtils.equals("OK", status)) {
                                        T dataInstance = response.getObject("data", cls);
                                        if (dataInstance == null) {
                                            dataInstance = cls.newInstance();
                                        }
                                        callback.success(dataInstance);
                                    } else {
                                        ob4bill(response, callback);
                                    }
                                }
                            }

                        } catch (Exception e) {
                            callback.success(null);
                            LogUtil.e(LogUtil.URL_TAG, e.toString());
                        }
                    }
                });
    }

    private <T> void ob4bill(JSONObject response, Callback<T> callback) {
        String message = response.getString("message");
        if (message != null && !message.isEmpty()) {
            if ("5".equals(response.getString("returnCode"))) {
                ToastUtils.showShort("登录已过期，请重新登录");
                MyApplication.gotoLogin();
            } else {
                ToastUtils.showShort(message);
                callback.success(null);
            }
        } else {
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

    protected <T> void exeCallback4Game(Observable observable, Class<T> cls, Callback<T> callback) {
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
                                LocalCache.cleanGameTimesCode("errorCode");
                                String code = response.getString("errcode");
                                if (!TextUtils.isEmpty(code)){
                                    LocalCache.saveGameTimesCode("errorCode",code);
                                }
                                if (response.getBoolean("success")) {
                                    T dataInstance = response.getObject("data", cls);
                                    if (dataInstance == null){
                                        dataInstance = cls.newInstance();
                                    }
                                    callback.success(dataInstance);
                                }
                            }

                        } catch (Exception e) {
                            callback.success(null);
                            LogUtil.e(LogUtil.URL_TAG,e.toString());
                        }
                    }
                });
    }

    protected <T> void exeCallbackZongzhi(Observable observable, Class<T> cls, Callback<T> callback) {

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
