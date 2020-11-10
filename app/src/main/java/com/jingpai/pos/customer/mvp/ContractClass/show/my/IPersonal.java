package com.jingpai.pos.customer.mvp.ContractClass.show.my;

import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 时间: 2020/2/5
 * 功能:
 */
public interface IPersonal {
    interface PersonalModel{
        void personalData(Map<String,Object> map, NetWorkUtil.MyCallBack myCallBack);
        void file(MultipartBody.Part file, RequestBody body,NetWorkUtil.MyCallBack myCallBack);
    }
    interface PersonalView{
        void personalData(String succeed);
        void file(String succeed);
    }
}
