package com.jingpai.pos.customer.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.component.dialog.TwoChooseDialog;
import com.stx.xhb.androidx.OnDoubleClickListener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * function:
 */
public class CommonUtil {
    public final static String PHONE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    /**
     * 正则表达式匹配判断
     * @param input 需要做匹配操作的字符串
     * @return true if matched, else false
     */
    public static boolean isMatchered( CharSequence input,String patternRule) {
        Pattern pattern = Pattern.compile(patternRule);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 拨打电话
     * @param phone
     * @param activity
     */
    public static void showCallDialog(String phone, Activity activity) {
        TwoChooseDialog callDialog = new TwoChooseDialog(activity);
        callDialog.show();
        callDialog.getTvAddCar().setVisibility(View.GONE);
        callDialog.setTextTwo(phone);
        callDialog.setTextTwoClick(new OnDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                PermissionHelper permissionHelper = new PermissionHelper(activity, new PermissionInterface() {
                    @Override
                    public int getPermissionsRequestCode() {
                        return 10;
                    }

                    @Override
                    public String[] getPermissions() {
                        return new String[]{Manifest.permission.CALL_PHONE};
                    }

                    @SuppressLint("MissingPermission")
                    @Override
                    public void requestPermissionsSuccess() {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri uri = Uri.parse("tel:"+phone);
                        intent.setData(uri);
                        //使用AS,这里会报红,编译能通过,只是提醒你要做Android6.0权限的适配
                        //使用AS,这里会报红,编译能通过,只是提醒你要做Android6.0权限的适配
                        activity.startActivity(intent);
                        callDialog.dismiss();
                    }

                    @Override
                    public void requestPermissionsFail() {

                    }
                });
                permissionHelper.requestPermissions();
            }
        });
    }

    /**
     * 功能描述：字符串隐藏加*
     */
    public static String strEncry(String str,int satrtIndex,int endIndex){
        StringBuilder sb  =new StringBuilder();
        if(!TextUtils.isEmpty(str) && str.length() > satrtIndex ){
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (i >= satrtIndex && i <= endIndex) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }else{
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 功能描述：隐藏键盘
     */
    public static final void hideKeyboard(Context context, View v) {
        if(context!=null&&v!=null){
            InputMethodManager imm = ((InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE));
            imm.hideSoftInputFromWindow(v.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    private static boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) MyApplication.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
