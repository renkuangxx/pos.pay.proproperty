package com.jingpai.pos.customer.utils;

import com.jingpai.pos.customer.activity.TemperatureAlarmActivity;
import com.jingpai.pos.customer.activity.authentication.activity.YezhuDetailActivity;
import com.jingpai.pos.customer.activity.housemember.BuildingActivity;
import com.jingpai.pos.customer.activity.housemember.HouseHolderActivity;
import com.jingpai.pos.customer.activity.show.Home.ShowActivity;
import com.jingpai.pos.activity.login.PersonalCenterActivity;
import com.jingpai.pos.customer.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 推送消息类型和界面直接的映射关系，便于打开对应界面
 */
public class   ActivityChannelUtil {
    private static Map<String, Class<? extends BaseActivity>> map = new HashMap<>(3);

    public static void initMap() {
        map.put("TemperatureAlarm", TemperatureAlarmActivity.class);
        map.put("personalCenter", PersonalCenterActivity.class);
        map.put("myHouse", BuildingActivity.class);
        map.put("memberAuditDetail", HouseHolderActivity.class);
        map.put("ownerAuditDetail", YezhuDetailActivity.class);
        map.put("homePage", ShowActivity.class);
    }

    public static Map<String, Class<? extends BaseActivity>> getMap() {

        return map;
    }


}
