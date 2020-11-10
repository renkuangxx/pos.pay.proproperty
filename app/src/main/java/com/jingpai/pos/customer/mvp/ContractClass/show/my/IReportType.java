package com.jingpai.pos.customer.mvp.ContractClass.show.my;

import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * 时间: 2020/2/5
 * 功能:
 */
public interface IReportType {
    interface ReportTypeModel{
        void reportTypeData(NetWorkUtil.MyCallBack myCallBack);
        void houseQueryData(Map<String,Object> map,NetWorkUtil.MyCallBack myCallBack);
        void reportData(Map<String,Object> map,NetWorkUtil.MyCallBack myCallBack);
    }
    interface PersonalView{
        void reportTypeData(String succeed);
        void houseQueryData(String succeed);
        void reportData(String succeed);
    }
}
