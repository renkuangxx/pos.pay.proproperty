package com.jingpai.pos.customer.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created By：jinheng.liu
 * on 2020/10/12
 */

public class AiuiMultBean implements MultiItemEntity {
    //拨打电话
    public static final int MOBLIE = 0;
    //开门
    public static final int OPENDOOR = 1;
    //item类型
    private int fieldType;
    @Override
    public int getItemType() {
        return fieldType;
    }

    public AiuiMultBean(int fieldType) {
        //将传入的type赋值
        this.fieldType = fieldType;
    }
}
