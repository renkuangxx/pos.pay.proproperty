package com.jingpai.pos.customer.mvp.ContractClass.show.my;

import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public interface IMatterHistory {
    interface MatterHistoryModel{
        void matterHistoryData(Map<String,Object> map, NetWorkUtil.MyCallBack myCallBack);
    }
    interface MatterHistoryView{
        void matterHistoryData(String succeed);
    }
}
