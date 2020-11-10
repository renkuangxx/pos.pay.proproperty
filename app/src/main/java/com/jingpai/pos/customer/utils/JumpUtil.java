package com.jingpai.pos.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.EventBusMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JumpUtil {

    public static final String openDoor =Constant.JUMP_STR+".opendoor.OpenDoorActivity";//手机开门
    public static final String openDoorWithBlueTooth =Constant.JUMP_STR+".opendoor.OpenDoorWithBlueToothActivity";//蓝牙开门
    public static final String myHouse = Constant.JUMP_STR+".housemember.BuildingActivity";//我的房屋
    public static final String member = Constant.JUMP_STR+".housemember.MemberActivity";//成员管理
    public static final String census = Constant.JUMP_STR+".census.activity.FirstCensusActivity";//人口普查
    public static final String propertyFee = Constant.JUMP_STR+".payment.PaymentActivity";//物业缴费
    public static final String report = Constant.JUMP_STR+".repairs.TheMatterActivity";//报事报修
    public static final String visitorInvitation = Constant.JUMP_STR+".show.My.VisitorActivity";
    public static final String addCredit = Constant.JUMP_STR+".payphone.AddPhoneFeeActivity";//话费充值
    public static final String feedback = Constant.JUMP_STR+".show.Home.FeedbackActivity";//服务建议
    public static final String antiepidemic = Constant.JUMP_STR+".show.Home.DailyRegistrationActivity";//健康登记
    public static final String myReport = Constant.JUMP_STR+".repairs.MatterHistoryActivity";//保修历史
    public static final String myCar = Constant.JUMP_STR+".show.Home.CarManageActivity";//车辆管理
    public static final String entranceTalkback = "";//门禁对讲
    public static final String warrantyFamily = "";//家庭报修
    public static final String mistakePark = "";//错时停车
    public static final String expressageService = "";//快递服务
    public static final String lifePay = "";//生活缴费
    public static final String errandAssistant = "";//跑腿帮办
    public static final String phoneRecharge = Constant.JUMP_STR+".payphone.AddPhoneFeeActivity";//手机充值
    public static final String contactCommunity = "";//联系社区
    public static final String moveRegister = "";//搬家登记
    public static final String fitmentApply = "";//装修申请
    public static final String transactRoom = "";//交房办理

    public static final String AppreciationLifePay = Constant.JUMP_STR+".payphone.AddPhoneFeeActivity";//话费充值
    public static final String AppreciationLifeService = "Appreciation-lifeService";//生活服务
    public static final String AppreciationElectricalElectronic = "Appreciation-electricalElectronic";//电子电器
    public static final String AppreciationCarService = "Appreciation-carService";//汽车生活
    public static final String AppreciationPetParadise = "Appreciation-petParadise";//宠物乐园
    public static final String AppreciationHouseWork = "Appreciation-communityGroupPurchase";//门口房务
    public static final String AppreciationLightLife = "Appreciation-communityGroupPurchase";//轻奢生活
    public static final String AppreciationFinancialInsurance = "Appreciation-communityGroupPurchase";//理财保险
    public static final String AppreciationCommunityGroupPurchase = "Appreciation-communityGroupPurchase";//社区团购
    public static final String AppreciationCommunityEntertainment = "Appreciation-communityGroupPurchase";//社交娱乐


    //等待补充
    /**
     * 路由跳转
     *
     * @param context      上下文
     * @param activityPath 要跳转的类的全包名.类名
     * @param bundle       要传递的参数
     */
    public static void jump(Context context, String activityPath, Bundle bundle) {
        if (TextUtils.isEmpty(activityPath))
            return;
        try {

                if (activityPath.contains("Appreciation-lifeService")){
                    EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_LIFE_SERVER,null));
                }else if (activityPath.contains("Appreciation-electricalElectronic")){
                    EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_ELECTRONIC_APPLIANCES,null));
                }else if (activityPath.contains("Appreciation-carService")){
                    EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_CAR_LIFE,null));
                }else if (activityPath.contains("Appreciation-petParadise")){
                    EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_PET_PARADISE,null));
                }else if (activityPath.contains("Appreciation-communityGroupPurchase")){
                    EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_COMMUNITY_GROUP_BUY,null));
                }else{
                    Intent intent = new Intent(context, Class.forName(activityPath));
                    if (bundle != null)
                        intent.putExtras(bundle);
                    context.startActivity(intent);
                }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String containString(String s){
        if (TextUtils.isEmpty(s)){return "";}
        if (s.equals("Appreciation-lifePay")){
            return AppreciationLifePay;
        }else if (s.equals("Appreciation-lifeService")){
            return AppreciationLifeService;
        }else if (s.equals("Appreciation-electricalElectronic")){
            return AppreciationElectricalElectronic;
        }else if (s.equals("Appreciation-carService")){
            return AppreciationCarService;
        }else if (s.equals("Appreciation-petParadise")){
            return AppreciationPetParadise;
        }else if (s.equals("Appreciation-houseWork")){
            return AppreciationHouseWork;
        }else if (s.equals("Appreciation-lightLife")){

            return AppreciationLightLife;
        }else if (s.equals("Appreciation-financialInsurance")){
            return AppreciationFinancialInsurance;
        }else if (s.equals("Appreciation-communityGroupPurchase")){
            return AppreciationCommunityGroupPurchase;
        }else if (s.equals("Appreciation-communityEntertainment")){
            return AppreciationCommunityEntertainment;
        }else if (s.contains("openDoor")){
            return openDoor;
        }else if (s.contains("propertyFee")){
            return propertyFee;
        }else if (s.contains("visitorInvitation")){
            return visitorInvitation;
        }else if (s.contains("addCredit")){
            return addCredit;
        }else if (s.contains("member")){
            return member;
        }else if (s.contains("feedback")){
            return feedback;
        }else if (s.contains("antiepidemic")){
            return antiepidemic;
        }else if (s.contains("myHouse")){
            return myHouse;
        }else if (s.contains("report")){
            return report;
        }else if (s.contains("myReport")){
            return myReport;
        }else if (s.contains("myCar")){
            return myCar;
        }else if (s.contains("census")){
            return census;
        }else if (s.contains("openWithBlueTooth")){
            return openDoorWithBlueTooth;
        }else if (s.contains("entranceTalkback")){
            return entranceTalkback;
        }else if (s.contains("warrantyFamily")){
            return warrantyFamily;
        }else if (s.contains("mistakePark")){
            return mistakePark;
        }else if (s.contains("expressageService")){
            return expressageService;
        }else if (s.contains("lifePay")){
            return lifePay;
        }else if (s.contains("errandAssistant")){
            return errandAssistant;
        }else if (s.contains("phoneRecharge")){
            return phoneRecharge;
        }else if (s.contains("contactCommunity")){
            return contactCommunity;
        }else if (s.contains("moveRegister")){
            return moveRegister;
        }else if (s.contains("fitmentApply")){
            return fitmentApply;
        }else if (s.contains("transactRoom")){
            return transactRoom;
        }else  {
            return "";
        }

    }

    /***
     * 是否包含指定字符串,不区分大小写
     * @param input : 原字符串
     * @param regex
     * @return
     */
    public static boolean contain2(String input, String regex) {
//        if(ValueWidget.isNullOrEmpty(input)){
//            return false;
//        }
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(input);
        boolean result = m.find();
        return result;
    }
}
