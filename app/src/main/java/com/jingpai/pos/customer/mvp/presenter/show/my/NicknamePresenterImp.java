package com.jingpai.pos.customer.mvp.presenter.show.my;

import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

public class NicknamePresenterImp extends BasePresenter_Old {

    public void nickNameData(Map<String, Object> map, Callback callback) {

        exeCallback(NetWorkUtil.getInstance().apiService.modifyNickname(map), callback);
    }
}