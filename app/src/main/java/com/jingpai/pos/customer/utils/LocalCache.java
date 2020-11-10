package com.jingpai.pos.customer.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jingpai.pos.customer.activity.census.bean.PopulationBean;
import com.jingpai.pos.customer.bean.ContentCateListBean;
import com.jingpai.pos.bean.Community;
import com.jingpai.pos.bean.User;

import java.util.List;

public class LocalCache {

    private static SharedPreferencesHelper helper = SharedPreferencesHelper.getInstance();
    private static final String USER_KEY = "USER_KEY";
    private static final String CURRENT_COMMUNITY_KEY = "CURRENT_COMMUNITY_KEY";
    private static final String TOKEN_KEY = "TOKEN_KEY";
    private static final String VERSION = "VERSION";
    private static final String SHOP_URL = "shopUrl";


    public static final String JP_MESSAGE_AUDITID = "jp_message_auditid";
    public static final String AUTO_OPEN_DOOR = "auto_open_door";
    public static final String IS_VISITOR = "IS_VISITOR";
    public static final String COMMUNITY_MANAGER = "COMMUNITY_MANAGER";


    public static void putValue(String key, String value) {
        helper.put(key, value);
    }
    public static String getValue(String key) {
        return helper.getString(key, null);
    }
    public static void clearValue(String key){
        helper.removeKey(key);
    }

    public static void putValue(String key, boolean value) {
        helper.put(key, value);
    }
    public static boolean getBooleanValue(String key) {
        return helper.getBoolean(key,false);
    }

    public static void putValue(String key, Object value) {
        helper.put(key, toJson(value));
    }
    public static <T>  T  getObjectValue(String key, Class<T> clazz) {
        return helper.getObject(key,clazz);
    }

    public static void putListValue(String key, Object value) {
        helper.put(key, toJsonArray(value));
    }
    public static <T> List<T> getObjectListValue(String key, Class<T> clazz) {
        return helper.getObjectList(key,clazz);
    }

    public static void saveInfo(String key, Object bean) {
        helper.put(key, toJson(bean));
    }

    public static PopulationBean getInfo(String key) {
        return helper.getObject(key, PopulationBean.class);
    }

    public static User getUser() {
        return helper.getObject(USER_KEY, User.class);
    }

    public static String getUserAvatar() {
        User user = getUser();
        return user == null?"":user.getAvatar();
    }

    public static void saveUserIdNum(String key,String num) {
        helper.put(key, num);
    }
    public static String getUserIdNum(String key){
        return helper.getString(key,"");
    }

    public static String getUserName() {
        User user = getUser();
        return user == null?"":user.getName();
    }

    public static void saveIdPassportNo(String key, String id) {
        helper.put(key, id);
    }
    public static String getIdPassportNo() {
        User user = getUser();
        return user == null?"":user.getCertificateNo();
    }

    public static void saveUserAvatar(String avatar) {
        User user = getUser();
        user.setAvatar(avatar);
        saveUser(user);
    }
    public static void saveUserSex(int avatar) {
        User user = getUser();
        user.setSex(avatar);
        saveUser(user);
    }
    public static int getUserSex() {
        User user = getUser();
        return user == null?0:user.getSex();
    }
    public static String getUserId() {
        User user = getUser();
        return user == null?"":user.getUserId();
    }

    public static void saveUserAndToken(User user) {
        helper.put(TOKEN_KEY, user.getToken());
        helper.put(USER_KEY, toJson(user));
    }

    public static void saveUser(User user) {
        helper.put(USER_KEY, toJson(user));
    }

    public static String getToken() {
        return helper.getString(TOKEN_KEY, "");
    }

    public static String deleteToken() {
        return helper.removeKey(TOKEN_KEY);
    }



    public static String getCurrentCommunityId() {
        Community community = getCurrentCommunity();
        return community == null?"":community.getCommunityId();
    }

    public static String getCurrentCommunityName() {
        Community community = getCurrentCommunity();
        return community == null?"":community.getCommunityName();
    }

    public static Community getCurrentCommunity() {
        return helper.getObject(buildKey(CURRENT_COMMUNITY_KEY), Community.class);
    }
    public static void setCurrentCommunity(Community currentCommunity) {
        helper.put(buildKey(CURRENT_COMMUNITY_KEY), toJson(currentCommunity));
    }

    private static String buildKey(String key) {
        User user = getUser();
        return user == null?key:key + user.getUserId();
    }

    public static void clean() {
        int version = getVersion();
        helper.clear();
        setVersion(version);
    }

    public static int getVersion() {
        return helper.getInt(VERSION, 0);
    }

    public static void setVersion(int version) {
        helper.put(VERSION, version);
    }

    private static String toJson(Object obj) {
        return JSONObject.toJSONString(obj).toString();
    }
    private static String toJsonArray(Object obj) {
        return JSONArray.toJSONString(obj).toString();
    }

    public static void saveShopUrl(String url){
        helper.put(SHOP_URL,url);
    }
    public static String getShopUrl(){
        return helper.getString(SHOP_URL, "");
    }
    public static void clearShopUrl(){
        helper.removeKey(SHOP_URL);
    }



    public static void clearInfo(String key){
        helper.removeKey(key);
    }

    public static void saveTypeInfo(String key, boolean b) {
        helper.put(key,b);
    }

    public static boolean getTypeInfo(String key) {
        return helper.getBoolean(key, false);
    }

    //清除
    public static void cleanLifeContent(String key) {
        helper.removeKey(key);
    }

    //baocun
    public static void saveLifeContent(String key, ContentCateListBean b) {
        helper.put(key,toJson(b));
    }

    public static ContentCateListBean getLifeContent(String key) {
        return helper.getObject(key, ContentCateListBean.class);
    }

    //清除
    public static void clearcity(String key){
        helper.removeKey(key);
    }

    public static void saveCity(String key, String city) {
        helper.put(key,city);
    }
    public static void saveYezhuState(String key, boolean isCommit) {
        helper.put(key,isCommit);
    }
    public static boolean getYezhuState(String key) {
        return helper.getBoolean(key,false);
    }
    public static void cleanYezhuState(String key) {
        helper.removeKey(key);
    }

    public static String getCity(String key) {
        return helper.getString(key, "");
    }
    //清除
    public static void clearcityCode(String key){
        helper.removeKey(key);
    }

    public static void saveCityCode(String key, String city) {
        helper.put(key,city);
    }

    public static String getCityCode(String key) {
        return helper.getString(key, "");
    }
    //清除
    public static void clearYezhuCommunityId(String key){
        helper.removeKey(key);
    }

    public static void saveYezhuCommunityId(String key, int city) {
        helper.put(key,city);
    }

    public static int getYezhuCommunityId(String key) {
        return helper.getInt(key,0);
    }
    //清除
    public static void clearYezhuBuildingId(String key){
        helper.removeKey(key);
    }

    public static void saveYezhuBuildingId(String key, int city) {
        helper.put(key,city);
    }

    public static int getYezhuBuildingId(String key) {
        return helper.getInt(key, 0);
    }
    //清除
    public static void clearYezhuUnitId(String key){
        helper.removeKey(key);
    }

    public static void saveYezhuUnitId(String key, int city) {
        helper.put(key,city);
    }

    public static int getYezhuUnitId(String key) {
        return helper.getInt(key, 0);
    }

    public static void saveGameTimesCode(String key, String isCommit) {
        helper.put(key,isCommit);
    }
    public static String getGameTimesCode(String key) {
        return helper.getString(key,"");
    }
    public static void cleanGameTimesCode(String key) {
        helper.removeKey(key);
    }
}
