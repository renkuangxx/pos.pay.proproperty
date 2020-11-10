package com.jingpai.pos.customer.bean.payment;

import com.jingpai.pos.bean.common.PageBean;
import com.jingpai.pos.customer.utils.MoneyUtil;

import java.util.List;

public class BillBean {

    private String total = "";

    private PageBean<BillItemBean> page;

    public String getTotal() {
        if (page != null && page.getData() != null) {
            int totalFen = 0;
            List<BillItemBean> list = page.getData();
            for (BillItemBean billItemBean : list) {
                totalFen += MoneyUtil.yuanToFen(billItemBean.getMoney());
            }

            total = MoneyUtil.fenToYuan(totalFen);
        }

        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public PageBean<BillItemBean> getPage() {
        return page;
    }

    public void setPage(PageBean<BillItemBean> page) {
        this.page = page;
    }
}
