package com.jingpai.pos.customer.activity.invite;

import com.jingpai.pos.customer.bean.ExamineBean;
import com.jingpai.pos.customer.mvp.presenter.BasePresenter_Old;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/**
 * @author 86173
 */
public class Invite2RegisterPresenter extends BasePresenter_Old {
    //上传数据
    public void updata(Map<String, Object> map, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.upData(map),String.class, callback);
    }

    //审核详情
    public void examineInfo(String auditId, Callback<ExamineBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.examineInfo(auditId),ExamineBean.class, callback);
    }
}
