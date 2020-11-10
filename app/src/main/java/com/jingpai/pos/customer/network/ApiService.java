package com.jingpai.pos.customer.network;

import com.jingpai.pos.customer.base.Constant;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

import static com.jingpai.pos.BuildConfig.CMS_URL;
import static com.jingpai.pos.BuildConfig.COMPREHENSIVE_CONTROL_URL;
import static com.jingpai.pos.BuildConfig.SERVE_URL;

public interface ApiService {
    //登录
    @POST(Constant.PROPERTY_API+"app/user/login/property")
    Observable<ResponseBody> Login(@Body Map<String, Object> map);
    //获取用户所有小区
    @GET(Constant.PROPERTY_API+"app/community/list")
    Observable<ResponseBody> communityList();
    //验证码
    @POST(Constant.PROPERTY_API+"app/captcha/send")
    Observable<ResponseBody> Verification(@Body Map<String, Object> map);
    //忘记密码
    @POST(Constant.PROPERTY_API+"app/user/getBackPassword")
    Observable<ResponseBody> GetBackPassword(@Body Map<String, Object> map);


    //获取楼栋
    @POST(Constant.PROPERTY_API_BILL+"app/yinShangPos/buildingList")
    Observable<ResponseBody> getCommunityBuilding(@Body Map<String, Object> map);
    //获取单元
    @POST(Constant.PROPERTY_API_BILL+"app/yinShangPos/unitList")
    Observable<ResponseBody> getCommunityUnit(@Body Map<String, Object> map);
    //获取房间
    @POST(Constant.PROPERTY_API_BILL+"app/yinShangPos/houseList")
    Observable<ResponseBody> getCommunityRoom(@Body Map<String, Object> map);
    //根据手机号获取房间
    @GET(Constant.PROPERTY_API_BILL+"app/yinShangPos/getHouseByPhone")
    Observable<ResponseBody> getRoomByPhone(@Query("areaType") int areaType,@Query("phone") String phone);
    //获取车位
    @GET(Constant.PROPERTY_API_BILL+"app/yinShangPos/parkingList")
    Observable<ResponseBody> getCommunityPark(@Query("communityId") String communityId);
    //获取商铺
    @GET(Constant.PROPERTY_API_BILL+"app/yinShangPos/shopList")
    Observable<ResponseBody> getCommunityShop(@Query("communityId") String communityId);
    //获取账单房屋信息
    @GET(Constant.PROPERTY_API_BILL+"app/yinShangPos/house")
    Observable<ResponseBody> getNoPayBillInfo(@Query("areaId") String areaId,@Query("areaType") int areaType);
    /**
     * APP最新版本
     */
    @GET(Constant.PROPERTY_API_BILL + "app/yinShangPos/version/latest")
    Observable<ResponseBody> getLatestVersion(@Query("appId") String appId);





    //注册
    @POST(Constant.PROPERTY_API+"app/user/register")
    Observable<ResponseBody> Register(@Body Map<String, Object> map);

    //车辆绑定
    @POST(Constant.PROPERTY_API+"app/car/add")
    Observable<ResponseBody> carAdd(@Body Map<String, Object> map);
    //车辆绑定
    @GET(Constant.PROPERTY_API+"app/car/candidateParking/query")
    Observable<ResponseBody> carAddChoseStall();

    //车辆接触绑定
    @POST(Constant.PROPERTY_API+"app/car/delete")
    Observable<ResponseBody> carDelete(@Body Map<String, Object> map);
    //查询绑定车辆列表
    @GET(Constant.PROPERTY_API+"app/car/query")
    Observable<ResponseBody> carQuery();
    //查询房屋
    @POST(Constant.PROPERTY_API+"app/house/query")
    Observable<ResponseBody> houseQuery(@Body Map<String, Object> map);
    //添加成员
    @POST(Constant.PROPERTY_API+"app/member/add")
    Observable<ResponseBody> memberAdd(@Body Map<String, Object> map);
    //更新成员
    @POST(Constant.PROPERTY_API+"app/member/update")
    Observable<ResponseBody> memberUpdate(@Body Map<String, Object> map);
    //删除成员
    @POST(Constant.PROPERTY_API+"app/member/delete")
    Observable<ResponseBody> memberDelete(@Body Map<String, Object> map);

    //查询成员
    @POST(Constant.PROPERTY_API+"app/house/activity/query")
    Observable<ResponseBody> memberQueryAll(@Body Map<String, Object> map);

    //根据房屋查询成员
    @POST(Constant.PROPERTY_API+"app/member/queryHouse")
    Observable<ResponseBody> queryHouseMember(@Body Map<String, Object> map);


    //上传头像
    @POST(Constant.PROPERTY_API+"app/user/avatar")
    Observable<ResponseBody> avatar(@Body Map<String, Object> map);
    //上传文件
    @Multipart
    @POST(Constant.PROPERTY_API+"app/file")
    Observable<ResponseBody> file(@Part MultipartBody.Part file,@Part("fileType") RequestBody body);
    /**
     * 批量上传文件
     */
    @Multipart
    @POST(Constant.PROPERTY_API+"app/file/batch")
    Observable<ResponseBody> fileBatch(@Part() List<MultipartBody.Part> file, @Query("fileType") String fileType);
    //修改用户名
    @POST(Constant.PROPERTY_API+"app/user/modifyNickname")
    Observable<ResponseBody> modifyNickname(@Body Map<String, Object> map);
    //修改性别
    @POST(Constant.PROPERTY_API+"app/user/modifySex")
    Observable<ResponseBody> modifySex(@Body Map<String, Object> map);
    //更新人脸数据
    @POST(Constant.PROPERTY_API+"app/user/face")
    Observable<ResponseBody> updateFace(@Body Map<String, Object> map);

    //更新人脸数据
    @POST(Constant.PROPERTY_API+"app/user/queryPersonInformation")
    Observable<ResponseBody> getUserInfo(@Body Map<String, Object> map);

    /**
     * 修改用户名
     */
    @GET(Constant.PROPERTY_API+"app/home")
    Observable<ResponseBody> home(@Query("cityCode") String cityCode);

    @GET(Constant.PROPERTY_API+"app/home/new")
    Observable<ResponseBody> newHome(@Query("cityCode") String cityCode,@Query("communityId") String communityId,@Query("menuClientTypeEnum") String menuClientTypeEnum);
    /**
     * 访客
     */
    @POST(Constant.PROPERTY_API+"app/visitor/visitorRegister")
    Observable<ResponseBody> visitorRegister(@Body Map<String, Object> map);
    /**
     * 访客-房屋列表
     */
    @POST(Constant.PROPERTY_API+"app/visitor/queryHouse")
    Observable<ResponseBody> queryHouseList(@Body Map<String, Object> map);
    /**
     * 查询房屋
     * @param map
     * @return 房屋列表
     */
    @POST(Constant.PROPERTY_API+"app/house/query")
    Observable<ResponseBody> queryHouse(@Body Map<String, Object> map);
    /**
     * 访客-房屋列表-车位列表
     */
    @GET(Constant.PROPERTY_API+"app/visitor/queryParking")
    Observable<ResponseBody> queryParking(@QueryMap Map<String, Object> map);

    /**
     * 访客-来访记录
     */
    @POST(Constant.PROPERTY_API+"app/visitor/visitorRecord")
    Observable<ResponseBody> visitorRecord(@Body Map<String, Object> map);

    /**
     * 访客-来访记录-记录详情
     */
    @GET(Constant.PROPERTY_API+"app/visitor/visitorRecordDetail")
    Observable<ResponseBody> visitorRecordDetail(@QueryMap Map<String, Object> map);

    /**
     * 访客-访客车位申请审批-同意
     */
    @POST(Constant.PROPERTY_API+"app/parkingApply/pass")
    Observable<ResponseBody> parkingApplyPass(@Body Map<String, Object> map);
    /**
     * 访客-访客车位申请审批-拒绝
     */
    @POST(Constant.PROPERTY_API+"app/parkingApply/reject")
    Observable<ResponseBody> parkingApplyReject(@Body Map<String, Object> map);
    /**
     * 访客-访客车-取消邀请
     */
    @POST(Constant.PROPERTY_API+"app/visitor/cancel")
    Observable<ResponseBody> parkingApplyCancel(@Body Map<String, Object> map);

    /**
     * 访客-访客车-撤销车位申请
     * @param map
     * @return
     */
    @POST(Constant.PROPERTY_API+"app/visitor/cancelParking")
    Observable<ResponseBody> parkingApplyCancelParking(@Body Map<String, Object> map);

    /**
     * 访客-访客车-撤销车位申请授权
     * @param map
     * @return
     */
    @POST(Constant.PROPERTY_API+"app/visitor/cancelParkingAuth")
    Observable<ResponseBody> parkingApplyCancelParkingAuth(@Body Map<String, Object> map);

    /**
     * 缴费账单
     */
    @POST(Constant.PROPERTY_API_BILL+"app/bill/unpaid")
    Observable<ResponseBody> queryBill(@Body Map<String, Object> map);
    /**
     * 已缴费账单
     */
    @GET(Constant.PROPERTY_API_BILL+"app/bill/paid")
    Observable<ResponseBody> queryBillHistory(@QueryMap Map<String, Object> map);

    /**
     * 已缴费账单详情
     */
    @GET(Constant.PROPERTY_API_BILL+"app/bill/{orderId}")
    Observable<ResponseBody> queryBillDetail(@Path("orderId") String orderId);
    /**
     * 报事类型
     */
    @GET(Constant.PROPERTY_API+"app/report/type")
    Observable<ResponseBody> reportType();
    /**
     * 上报
     */
    @POST(Constant.PROPERTY_API+"app/report")
    Observable<ResponseBody> report(@Body Map<String, Object> map);
    /**
     * 上报历史
     */
    @GET(Constant.PROPERTY_API+"app/report")
    Observable<ResponseBody> reportHistory(@QueryMap Map<String, Object> map);

    /**
     * 催办报事
     */
    @PUT(Constant.PROPERTY_API+"app/report/{id}")
    Observable<ResponseBody> reportUrging(@Path("id") String id);
    /**
     * 取消报事
     */
    @POST(Constant.PROPERTY_API+"app/report/{id}")
    Observable<ResponseBody> cancelMatter(@Path("id") String id);
    /**
     * 报事详情
     */
    @GET(Constant.PROPERTY_API+"app/report/{id}")
    Observable<ResponseBody> matterDetail(@Path("id") String id);
    /**
     * 评价报事
     */
    @POST(Constant.PROPERTY_API+"app/report/evaluate")
    Observable<ResponseBody> evaluateMatter(@Body Map<String, Object> map);


    /**
     * 门禁设备列表
     */
    @GET(Constant.PROPERTY_API+"app/access")
    Observable<ResponseBody> accessList(@Query("communityId") String id);
    /**
     * 远程开门
     */
    @POST(Constant.PROPERTY_API+"app/access")
    Observable<ResponseBody> accessOpenDoor(@Body Map<String, Object> map);


    /**
     * 游客选择小区
     */
    @GET(Constant.PROPERTY_API+"app/community/getNearestCommunity")
    Observable<ResponseBody> communityNearest(@Query("lat") String lat,@Query("lon") String lon);
    /**
     * 游客选择小区
     */
    @GET(Constant.PROPERTY_API+"app/community/getCommunityGroupByCity")
    Observable<ResponseBody> communityListByCity();
    /**
     * 获取消息
     */
    @GET(Constant.PROPERTY_API+"app/message")
    Observable<ResponseBody> message(@Query("before") int before,@Query("pageSize") int pageSize);
    /**
     * 获取消息详情
     */
    @POST(Constant.PROPERTY_API+"app/message")
    Observable<ResponseBody> messageDetail(@Body Map<String, Integer> map);
    /**
     * 获取查看账单房屋
     */
    @GET(Constant.PROPERTY_API_BILL+"app/bill/house")
    Observable<ResponseBody> queryRoom(@Query("communityId") String communityId);

    /**
     * 下单
     */
    @GET(Constant.PROPERTY_API_BILL+"app/bill/order")
    Observable<ResponseBody> billOrder(@Query("roomNo") String roomNo);

    /**
     * 下单
     */
    @POST(Constant.PROPERTY_API_BILL+"app/yinShangPos/order/v2")
    Observable<ResponseBody> billOrder(@Body Map<String, Object> map);

    /**
     * 支付
     */
    @POST(Constant.PROPERTY_API_BILL+"app/bill/pay")
    Observable<ResponseBody> pay(@Body Map<String, Object> map);

    /**
     * 支付
     */
    @POST(Constant.PROPERTY_API+"pay/queryAliPayState")
    Observable<ResponseBody> queryAliPayState(@Body Map<String, Object> map);

    /**
     * 个人基础信息查询
     */
    @POST(Constant.PROPERTY_API+"app/user/queryPersonInformation")
    Observable<ResponseBody> queryPersonInformation();

    /**
     * 查询活动
     */
    @POST(Constant.PROPERTY_API+"app/promotion/allList")
    Observable<ResponseBody> promotionList(@Body Map<String, Object> map);


    /**
     * 活动详情
     */
    @POST(Constant.PROPERTY_API+"app/feedback")
    Observable<ResponseBody> feedback(@Body Map<String, Object> map);

    /**
     *
     */
    @POST(Constant.PROPERTY_API+"app/member/queryAll")
    Observable<ResponseBody> queryAll(@Body Map<String, Object> map);


    /**
     * 公告列表
     */
    @GET(Constant.PROPERTY_API+"app/notice")
    Observable<ResponseBody> queryNotice(@QueryMap Map<String, Object> map);

    @Streaming
    @GET
    Observable<ResponseBody> executeDownload(@Url() String url);

    /**
     * 修改密码
     */
    @POST(Constant.PROPERTY_API+"app/user/updatePassword")
    Observable<ResponseBody> updatePassword(@Body Map<String, Object> map);
    /**
     * 查询车位
     */
    @GET(Constant.PROPERTY_API+"app/car/parking/query")
    Observable<ResponseBody> parkingQuery(@Query("communityId") String communityId);
    /**
     * 车辆出入记录查询
     */
    @GET(Constant.PROPERTY_API+"app/car/accessRecords")
    Observable<ResponseBody> accessRecords(@Query("plateNumber") String communityId);
    /**
     * 防疫专区
     */
    @POST(Constant.PROPERTY_API+"app/health")
    Observable<ResponseBody> health(@Body Map<String, Object> map);
    /**
     * 防疫上报 最后上传的数据
     */
    @GET(Constant.PROPERTY_API+"app/health/last")
    Observable<ResponseBody> last();
    /**
     * 删除车位
     */
    @POST(Constant.PROPERTY_API+"app/car/parking/delete")
    Observable<ResponseBody> delete(@Body Map<String, Object> map);
    /**
     * 每日防疫登记列表
     */
    @GET(Constant.PROPERTY_API+"app/health")
    Observable<ResponseBody> healthQuery(@Query("before") String before,@Query("pageSize") String pageSize);
    /**
     * 疫情上报历史详情
     */
    @GET(Constant.PROPERTY_API+"app/health/{id}")
    Observable<ResponseBody> healthParticulars(@Path("id") int id);
    /**
     * 疫情上报修改
     */
    @POST(Constant.PROPERTY_API+"app/health/update")
    Observable<ResponseBody> update(@Body Map<String, Object> map);

    /**
     *创建订单
     */
    @POST(Constant.VS_API+"front/hongYi/createHongYiOrder")
    Observable<ResponseBody> createHongYiOrder(@Body Map<String, Object> map);
    /**
     *根据订单号查询
     */
    @POST(Constant.VS_API+"front/hongYi/getHongYiPayStateByOrderNo")
    Observable<ResponseBody> getHongYiPayStateByOrderNo(@Body Map<String, String> map);
    /**
     *根据用户手机号查询充值记录
     */
    @POST(Constant.VS_API+"front/hongYi/getOrdersByMobile")
    Observable<ResponseBody> getOrdersByMobile(@Body Map<String, String> map);


    /**
     *创建订单
     */
    @GET(Constant.SHOPMALL_API + "member/memberByMobile.html")
    Observable<ResponseBody> getMemberPoint(@Query("mobile") String mobile);

    /**
     * 获取用户所有小区
     */
    @GET(Constant.PROPERTY_API+"/app/menu")
    Observable<ResponseBody> buttonList(@Query("clientType") String clientType);

    /**
     * 采集数据
     */
    @POST(Constant.PROPERTY_API_G+"app/census/gather")
    Observable<ResponseBody> gatherInfo(@Body Map<String, Object> map);
    /**
     * 提交记录查询
     */
    @POST(Constant.PROPERTY_API_G+"app/census/queryRecord")
    Observable<ResponseBody> userInfoQuery(@Body Map<String, Object> map);

    /**
     * 提交记录查询 buhan tiao zhuan
     */
    @GET(Constant.PROPERTY_API_G+"app/census/queryFinishRecord")
    Observable<ResponseBody> userInfoQuery1();
    /**
     * 提交记录查询 buhan tiao zhuan
     */
    @GET(Constant.PROPERTY_API_G+"app/census/queryRecord")
    Observable<ResponseBody> userInfoAllQuery();
    /**
     * 审核详情
     */
    @GET(Constant.PROPERTY_API+"app/auth/audit/detail")
    Observable<ResponseBody> examineInfo(@Query("auditId") String auditId);
    /**
     * 删除
     */


    @POST(Constant.PROPERTY_API_G+"app/census/deleteRecord")
    Observable<ResponseBody> deleteInfoAllQuery(@Body Map<String, Object> map);

    /**
     * 邀请注册 上传
     */
    @POST(Constant.PROPERTY_API+"app/house/tenantHouseAuth")
    Observable<ResponseBody> upData(@Body Map<String, Object> map);

    /**
     * 审批详情
     */
    @GET(Constant.PROPERTY_API+"app/memberAdd/audit/detail")
    Observable<ResponseBody> getInfo(@Query("auditId") String auditId);
    /**
     * 审批列表
     */
    @GET(Constant.PROPERTY_API+"app/memberAdd/audit/customerAuditList")
    Observable<ResponseBody> getInfoList(@Query("pageSize") int pageSize);

    /**
     * 审批
     */
    @POST(Constant.PROPERTY_API+"app/memberAdd/audit/customerAudit")
    Observable<ResponseBody> applyMember(@Body Map<String, Object> map);

    /**
     * 是否要弹窗
     */
    @POST(Constant.PROPERTY_API+"app/member/checkMemberOver")
    Observable<ResponseBody> isNeedDialog(@Body Map<String, Object> map);

    /**
     * 弹窗提交
     */
    @POST(Constant.PROPERTY_API+"app/member/memberAddAudit")
    Observable<ResponseBody> upDataDialogInfo(@Body Map<String, Object> map);
    /**
     *
     * 户型推荐
     */
    @POST(CMS_URL+"contentClient/recommend")
    Observable<ResponseBody> huxingRecommond(@Body Map<String, Object> map);
    /**
     *
     * 更多列表
     */
    @POST(CMS_URL +"contentClient/cateContent")
    Observable<ResponseBody> moreList(@Body Map<String, Object> map);
    /**
     * 首页接口
     */
    @GET(CMS_URL+"content/treeCate")
    Observable<ResponseBody> treeCate(@Query("currentUserId") String currentUserId);

    /**
     * 点赞
     */
    @POST(CMS_URL+"contentClient/like")
    Observable<ResponseBody> like(@Body Map<String, Object> map);

    /**
     *取消点赞
     */
    @POST(CMS_URL+"contentClient/unlike")
    Observable<ResponseBody> unLike(@Body Map<String, Object> map);
    /**
     *  搜索
     */
    @POST(CMS_URL+"contentClient/search")
    Observable<ResponseBody> search(@Body Map<String, Object> map);
    /**
     * 查询房屋
     */
    @POST(Constant.PROPERTY_API+"app/house/queryHouseLayout")
    Observable<ResponseBody> FindouseLayout(@Body Map<String, Object> map);

    /**
     * 服务tab
     */
    @GET(SERVE_URL+"channelIndex/getChannels.html")
    Observable<ResponseBody> serverTab();

    /**
     *
     */
    @GET(Constant.PROPERTY_API+"app/visitor/getCityInfoByLocation")
    Observable<ResponseBody> getCityInfoByLocation(@QueryMap Map<String, Object> map);


    /**
     * String id = LocalCache.getCurrentCommunity().getId();
     *
     */
    @GET(Constant.PROPERTY_API+"app/community/queryCBuilding")
    Observable<ResponseBody> queryCBuilding(@Query("communityId") int communityId);

    /**
     * String id = LocalCache.getCurrentCommunity().getId();
     */
    @GET(Constant.PROPERTY_API+"app/community/queryCHouse")
    Observable<ResponseBody> queryCHouse(@Query("unitId") int unitId);
    /**
     * String id = LocalCache.getCurrentCommunity().getId();
     */
    @GET(Constant.PROPERTY_API+"app/community/queryCUnit")
    Observable<ResponseBody> queryCUnit(@Query("buildingId") int buildingId);
    /**
     * String id = LocalCache.getCurrentCommunity().getId();
     */
    @GET(Constant.PROPERTY_API+"app/visitor/propertyIsExists")
    Observable<ResponseBody> propertyIsExists(@Query("houseId") int houseId);
    /**
     * ren zhen
     */
    @POST(Constant.PROPERTY_API+"app/visitor/guestAuthenticate")
    Observable<ResponseBody> guestAuthenticate(@Body Map<String, Object> map);
    /**
     * 城市列表
     */
    @GET(Constant.PROPERTY_API+"app/visitor/getCityList")
    Observable<ResponseBody> getCityList();

    /**
     * huoqu xiao qu mingzi
     */
    @GET(Constant.PROPERTY_API+"app/visitor/getCommunityByCityName")
    Observable<ResponseBody> getCommunityByCityName(@QueryMap Map<String, Object> map);

    /**
     *你的喜欢
     */
    @POST(CMS_URL+"recommend/homeRecommendation")
    Observable<ResponseBody> getFavoriteList(@Body Map<String, Object> map);

    /**
     *首页直播
     */
    @POST(CMS_URL+"recommend/homeRecommendationLive")
    Observable<ResponseBody> getLiving(@Body Map<String, Object> map);

    @POST(CMS_URL+"recommend/pageHomeRecommendationContent")
    Observable<ResponseBody> getFavoriteListNew(@Body Map<String, Object> map);

    @POST(COMPREHENSIVE_CONTROL_URL+"support/app/yz/index")
    Observable<ResponseBody> getCommunityServe(@Body Map<String, Object> map);
    /**
     * 发票
     */
    @GET(Constant.PROPERTY_API_BILL+"/api/collectionRecord/getInfo")
    Observable<ResponseBody> queryApply4Invoice(@QueryMap Map<String, Object> map);

    /**
     * huoqu xiao qu mingzi
     */
    @POST(SERVE_URL +"game/addnum.html")
    Observable<ResponseBody> getGameTimes(@QueryMap Map<String, Object> map);

    /**
     * 获取闪屏广告
     */
    @GET(Constant.PROPERTY_API+"app/advertisement/flash")
    Observable<ResponseBody> getFlashAdv();

    /**
     * 获取首页弹出广告
     */
    @GET(Constant.PROPERTY_API+"app/advertisement/window")
    Observable<ResponseBody> getActivityAdv();

    /**
     * 获取我的页面我的分销字段
     */
    @GET(SERVE_URL+"distributor/center/myincome.html")
    Observable<ResponseBody> getMyDistribution(@Query("mobile") String mobile);

}