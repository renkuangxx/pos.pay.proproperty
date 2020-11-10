package com.jingpai.pos.customer.base

import com.jingpai.pos.BuildConfig.ACIVITY_URL
import com.jingpai.pos.BuildConfig.SHOP_URL

object Constant {
    const val TAG = "CityLife"
    const val APP_ID = "customer-android";
    const val PROPERTY_API = "property-management-business/"
    const val PROPERTY_API_BILL = "property-billing/"
    const val PROPERTY_SHOP_API = "channelIndex/"
    const val PROPERTY_API_G = "property-management-government/"
    const val VS_API = "vsapi/"
    const val SHOPMALL_API = "shopmall/"
    const val SERVICE_MOBILE = "15306009841"

    //小程序id
    const val WX_USER_NAME = "gh_415fef815ba9"
    const val WEIXIN_BUSINESS_TYEP = "CITYLIFE"
    const val WEIXIN_APP_ID = "wxde6b04bb171f3190";


    //商城错误页
    const val SHOP_URL_ERROR = SHOP_URL + "error"

    //斑马
    const val HOUSEKEEPING_URL = " http://intermediate.jphl.com:8188/housekeeping";
    const val AGREEMENT_URL = ACIVITY_URL + "agreement";
    const val CONTENT_TYPE = "application/json;charset=UTF-8"
    const val WEB_URL = "WEB_URL"
    const val WEB_HTML = "WEB_HTML"
    const val WEB_BACK = "WEB_BACK"
    const val WEB_BACK_URL = "WEB_BACK_URL"
    const val WEB_TITLE = "WEB_TITLE"
    const val WEB_BAOBING_BACK = "WEB_BAOBING_BACK"
    const val WEB_TITLE_RIGHT = "WEB_TITLE_RIGHT"
    const val WEB_PADDING = "WEB_PADDING"

    //前往商城首页
    const val GO_SHOP_HOME = "goShopHome"

    //公司简介url
    const val COMPANY_PROFILE = "https://mp.weixin.qq.com/s/OWmHl2A-k7xcNYlIkGBlkQ"
    const val JUMP_STR = "com.jingling.citylife.customer.activity"
    //var CAMERA_SAVE_PATH: String = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).path
    //val PHOTO_PATH: String = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).path

    //EventBus事件
    const val EVENT_BUS_LIFE_SERVER = "EVENT_BUS_LIFE_SERVER"//生活服务tab
    const val EVENT_BUS_ELECTRONIC_APPLIANCES = "EVENT_BUS_ELECTRONIC_APPLIANCES"//电子电器tab
    const val EVENT_BUS_CAR_LIFE = "EVENT_BUS_CAR_LIFE"//汽车生活tab
    const val EVENT_BUS_PET_PARADISE  = "EVENT_BUS_PET_PARADISE"//宠物乐园tab
    const val EVENT_BUS_COMMUNITY_GROUP_BUY = "EVENT_BUS_COMMUNITY_GROUP_BUY"//社区团购
    const val EVENT_BUS_SKIP_TO_ME = "EVENT_BUS_SKIP_TO_ME"//我的页面
    const val EVENT_BUS_SKIP_TO_WEBVIEW = "EVENT_BUS_SKIP_TO_WEBVIEW"//打开webview
    const val EVENT_BUS_SKIP_TO_SHOPPING = "EVENT_BUS_SKIP_TO_SHOPPING"//商城tab
    const val EVENT_BUS_SHOW_HOME_PAGE = "EVENT_BUS_SHOW_HOME_PAGE"//首页
    const val EVENT_BUS_BABX_TO_AIUI = "EVENT_BUS_BABX_TO_AIUI"//语音助手
}