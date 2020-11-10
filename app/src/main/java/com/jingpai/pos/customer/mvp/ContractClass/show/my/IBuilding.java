package com.jingpai.pos.customer.mvp.ContractClass.show.my;

import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * 时间: 2020/2/6
 * 功能:
 */
public interface IBuilding {
    interface BuildingModel{
        void buildingData(Map<String,Object> map, NetWorkUtil.MyCallBack myCallBack);
    }
    interface BuildingView{
        void buildingData(String succeed);
    }
}
