package com.jingpai.pos.customer.mvp.ContractClass.show.my;

import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * 时间: 2020/2/5
 * 功能:
 */
public interface INickName {
    interface NickNameModel{
        void nickNameData(Map<String,Object> map, NetWorkUtil.MyCallBack myCallBack);
    }
    interface NickNameView{
        void nickNameData(String succeed);
    }
}
