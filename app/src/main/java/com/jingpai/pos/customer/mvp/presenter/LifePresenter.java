package com.jingpai.pos.customer.mvp.presenter;

import com.alibaba.fastjson.JSONArray;
import com.jingpai.pos.customer.bean.HomepageQueryBean;
import com.jingpai.pos.customer.bean.HuxingRecommondBean;
import com.jingpai.pos.customer.bean.MoreListBean;
import com.jingpai.pos.customer.bean.SearchBean;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.Map;

/*
 * function:
 */
public class LifePresenter extends BasePresenter_Old {


    //户型推荐
    public void huxingRecommond(Map<String, Object> map, Callback<HuxingRecommondBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.huxingRecommond(map), HuxingRecommondBean.class, callback);
    }

    //更多列表
    public void moreRecommond(Map<String, Object> map, Callback<MoreListBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.moreList(map), MoreListBean.class, callback);
    }

    //首页接口
    public void homepageQuery(String currentUserId, Callback<HomepageQueryBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.treeCate(currentUserId), HomepageQueryBean.class, callback);
    }

    //点赞
    public void likeQuery(Map<String, Object> map, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.like(map), String.class, callback);
    }
    //取消点赞
    public void unLikeQuery(Map<String, Object> map, Callback<String> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.unLike(map), String.class, callback);
    }
    //搜索文本
    public void searchQuery(Map<String, Object> map, Callback<SearchBean> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.search(map), SearchBean.class, callback);
    }
    //chafang
    public void houseQuery(Map<String, Object> map,Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.FindouseLayout(map), callback);
    }

    //标题tab列表
    public void getTabList(Callback<JSONArray> callback) {
        exeCallback(NetWorkUtil.getInstance().apiService.serverTab(), callback);
    }
}
