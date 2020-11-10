package com.jingpai.pos.customer.activity.authentication;

import com.jingpai.pos.customer.bean.XiaoquDanyuanInfoBean;
import com.jingpai.pos.customer.bean.XiaoquFangwuInfoBean;
import com.jingpai.pos.customer.bean.YezhuXiaoquLoudongInfoBean;

import java.util.List;

/**
 * @author 86173
 */
public interface FilterYezhuListener {
    void getFilterDataloudong(List<String> list, List<YezhuXiaoquLoudongInfoBean.data> dataList);// 获取过滤后的数据
    void getFilterDatadanyuan(List<String> list, List<XiaoquDanyuanInfoBean> dataList);// 获取过滤后的数据
    void getFilterDatafangwu(List<String> list, List<XiaoquFangwuInfoBean> dataList);// 获取过滤后的数据
}
