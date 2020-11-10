package com.jingpai.pos.customer.bean.house;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create by liujinheng
 * date 2020/5/27
 * function物业缴费
 */
public class PayItemBean implements Serializable {
    private String title;
    private float money;
    private ArrayList<DetailItem> detail;
    private boolean checked = true;
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public ArrayList<DetailItem> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<DetailItem> detail) {
        this.detail = detail;
    }

    public static class DetailItem implements Serializable{
        private String item;
        private String money;

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
