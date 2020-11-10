package com.jingpai.pos.customer.activity.show.Home;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.adapter.AnnouncementAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.show.AnnouncementBean;
import com.jingpai.pos.customer.mvp.presenter.show.home.AnnouncementPresenter;
import com.jingpai.pos.customer.utils.ActivityChannelUtil;
import com.jingpai.pos.customer.utils.ActivityCollectorUtil;
import com.jingpai.pos.customer.utils.LogUtil;
import com.jingpai.pos.utils.ToastUtils;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;

/*
 * 小区公告
 * */
public class AnnouncementActivity extends BaseActivity {

    @BindView(R.id.rv_announcement)
    RecyclerView rvAnnouncement;

    @BindView(R.id.ll_no_record)
    View noRecord;

    private AnnouncementPresenter announcementPresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_announcement;
    }


    @Override
    protected void initData() {
        announcementPresenter = new AnnouncementPresenter();
        ActivityCollectorUtil.addActivity(this);
        announcementPresenter.message(0, 30, this::announcementData);
    }

    public void announcementData(AnnouncementBean.DataBeanX dataBeanX) {
        if (dataBeanX == null) {
            return;
        }
        List<AnnouncementBean.DataBeanX.DataBean> data = dataBeanX.getData();
        rvAnnouncement.setLayoutManager(new LinearLayoutManager(this));
        AnnouncementAdapter adapter = new AnnouncementAdapter(R.layout.announcement_item, data);
        rvAnnouncement.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                AnnouncementBean.DataBeanX.DataBean item = (AnnouncementBean.DataBeanX.DataBean) adapter.getItem(position);
                if (item == null) {
                    return;
                }
                if ("WEBVIEW".equals(item.getType())) {
                    startWebViewActivity(AnnouncementActivity.this, item.getUrl(), item.getTitle());
                }

                if ("APP".equals(item.getType())) {
                    if (TextUtils.isEmpty(item.getUrl()) || item.getUrl().contains("?"))return;
                    startAppActivity(AnnouncementActivity.this, item.getUrl(), item.getId());
                }

                if ("CONFIRM".equals(item.getType())) {
                    LogUtil.e("messagebean",item.toString());
                    try {
                        AnnouncementBean.DataBeanX.DataBean dataBean=new AnnouncementBean.DataBeanX.DataBean();
                        dataBean.setUrl(item.getUrl());
                        String url = dataBean.getUrl();
                        String[] split = url.split("\\?");
                        dataBean.setUrl(split[0]);
                        String[] split2 = url.split("=");
                        dataBean.setId(split2[1]);
                        String message = JSONObject.toJSON(dataBean).toString();

                        Intent intent = new Intent(AnnouncementActivity.this, ActivityChannelUtil.getMap().get(dataBean.getUrl()));
                        intent.putExtra("message", message);
                        startActivity(intent);
                    }catch (Exception e){
                        LogUtil.e(LogUtil.JPUSH_TAG+"CONFIRM",e.getMessage());
                    }

                }
            }
        });

        if (data == null || data.isEmpty()) {
            noRecord.setVisibility(View.VISIBLE);
        }
    }

    /**
     * WebView打开url
     *
     * @param context
     * @param url
     * @throws JSONException
     */
    private void startWebViewActivity(Context context, String url, String title) {
        if (TextUtils.isEmpty(url)){
            ToastUtils.INSTANCE.showToast("数据缺失");
            return;
        }
        Intent intent = new Intent(context, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, url);
        intent.putExtra(Constant.WEB_BACK, true);
        intent.putExtra(Constant.WEB_TITLE, title);
        context.startActivity(intent);
    }

    /**
     * 打开app指定界面
     *
     * @param context
     * @param messageId
     * @throws JSONException
     */
    private void startAppActivity(Context context, String url, String messageId) {
        if (TextUtils.isEmpty(url)||TextUtils.isEmpty(messageId)){
            ToastUtils.INSTANCE.showToast("数据缺失");
            return;
        }
        Intent intent = new Intent(context, ActivityChannelUtil.getMap().get(url));
        intent.putExtra("messageId", messageId);
        context.startActivity(intent);
    }
}