package com.jingpai.pos.customer.utils;

import android.app.Activity;

import java.util.ArrayList;

/*
 * function:
 */
public class ActivityCollectorUtil {
    public static ArrayList<Activity> mActivityList = new ArrayList<Activity>();

    public static int getSize(){
        return mActivityList.size();
    }
    /**
     * onCreate()时添加
     * @param activity
     */
    public static void addActivity(Activity activity){
        LogUtil.e(LogUtil.LIFE_TAG,"onCreate   "+activity.getClass().getSimpleName());
        //判断集合中是否已经添加，添加过的则不再添加
        if (!mActivityList.contains(activity)){
            mActivityList.add(activity);
        }
    }
    /**
     * onDestroy()时删除
     * @param activity
     */
    public static void removeActivity(Activity activity){
        mActivityList.remove(activity);
    }
    /**
     * 关闭所有Activity
     */
    public static void finishAllActivity(){
        for (Activity activity : mActivityList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
//        mActivityList.removeAll(mActivityList);
    }

}