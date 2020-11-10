package com.jingpai.pos.customer.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Create by liujinheng
 * date 2020/7/16
 * function
 */
public class PickViewUtils {
    //获得Calendar这个类的实例：
    private static Calendar calendar = Calendar.getInstance();

    public interface SelectedItem{
        void selectItem(String item, int position);
    }
    public interface TimeSelectedItem{
        void selectItem(Date date);
    }
    /**
     * 展示选择器
     * 核心代码
     * 单条数据
     */
    public static void showOptionsPicker(final Context context, final List<String> listData, final SelectedItem selectedItem) {
//      监听选中
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
//               返回的分别是三个级别的选中位置
                selectedItem.selectItem(listData.get(options1),options1);
            }
        })
        .setSelectOptions(0)//设置选择第一个
        .setOutSideCancelable(false)//点击背的地方不消失
//        .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
        .setTitleColor(0xFF979797)//标题文字颜色
        .setSubmitColor(0xFF979797)//确定按钮文字颜色
        .setCancelColor(0xFF979797)//取消按钮文字颜色
        .build();//创建
//      把数据绑定到控件上面
        pvOptions.setPicker(listData);
        pvOptions.show();
    }

    /**
     * 展示选择器
     * 核心代码
     * 要展示的数据
     */
    public static void showTimePicker(final Context context, final TimeSelectedItem selectedItem) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
//        selectedDate.set(1990, 1, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.set(1990, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2040, 1, 1);

//      监听选中
        TimePickerView timePickerView = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                selectedItem.selectItem(date);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setContentTextSize(16)
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(16)
                .setContentTextSize(18)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
//                .setTitleText("日期选择")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
                .setTitleColor(0xFF979797)//标题文字颜色
                .setSubmitColor(0xFF979797)//确定按钮文字颜色
                .setCancelColor(0xFF979797)//取消按钮文字颜色
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                .setRangDate(startDate, endDate)//默认是1900-2100年
                .setDate(selectedDate)// 默认是系统时间*/
                .setLabel("年","月","日",null,null,null)
//                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式

                .build();
//      把数据绑定到控件上面
        timePickerView.setDate(Calendar.getInstance());
        timePickerView.show();
    }

    public static void showAddressPicker(final Context context, final List<String> listData, final TimeSelectedItem selectedItem) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(option2)
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//                tvOptions.setText(tx);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .build();

//        pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
        pvOptions.show();
    }



    public static String getFormatDate(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        StringBuilder sb = new StringBuilder(calendar.get(Calendar.YEAR) + "-");
        if (month < 10) {
            sb.append("0").append(month).append("-");
        } else {
            sb.append(month).append("-");
        }
        if (day < 10) {
            sb.append("0").append(day).append(" ");
        } else {
            sb.append(day).append(" ");
        }
        if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (minute < 10) {
            sb.append("0").append(minute);
        } else {
            sb.append(minute);
        }

        return sb.toString();
    }
}
