package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonalPresenterImp extends BasePresenter_Old {

    public void avatarData(Map<String, Object> map, Callback<String> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.avatar(map), String.class, callback);
    }

    public void fileData(MultipartBody.Part file, RequestBody body, Callback<String> callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.file(file, body), String.class, callback);
    }
}