package com.jingpai.pos.customer.activity.show.Home;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.adapter.NoticeAdapter;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.bean.common.PageBefore;
import com.jingpai.pos.customer.bean.show.NoticeBean;
import com.jingpai.pos.customer.mvp.presenter.show.home.NoticePresenter;
import com.jingpai.pos.customer.utils.RecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NoticeActivity extends BaseActivity {

    @BindView(R.id.rv_notice)
    RecyclerView rvNotion;

    private NoticePresenter noticePresenter;
    private NoticeAdapter noticeAdapter;

    private String before = "0";
    private boolean hasMore = true;
    private List<NoticeBean> noticeBeanList = new ArrayList<>(0);

    @Override
    protected int getLayout() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initData() {
        rvNotion.setLayoutManager(new LinearLayoutManager(this));
        noticeAdapter = new NoticeAdapter(R.layout.notice_item, noticeBeanList);
        rvNotion.setAdapter(noticeAdapter);

        rvNotion.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (hasMore && RecyclerViewUtil.isSlideToBottom(recyclerView)) {
                    loadNotice();
                }
            }
        });

        noticeAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            toDetail(position);
        });

        noticePresenter = new NoticePresenter();
        loadNotice();
    }

    private void toDetail(int position) {
        NoticeBean noticeBean = noticeBeanList.get(position);
        Intent intent = new Intent(NoticeActivity.this, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_HTML, noticeBean.getContent());
        intent.putExtra(Constant.WEB_BACK, true);
        intent.putExtra(Constant.WEB_TITLE, getString(R.string.notice));
        startActivity(intent);
    }

    private void loadNotice() {
        noticePresenter.queryNotice(before, pageBefore -> {
            initNoticeList(pageBefore);
        });
    }

    private void initNoticeList(PageBefore<JSONArray> pageBefore) {
        if (pageBefore != null) {
            JSONArray jsonArray = pageBefore.getData();
            List<NoticeBean> list = jsonArray.toJavaList(NoticeBean.class);
            noticeBeanList.addAll(list);
            noticeAdapter.notifyDataSetChanged();
            hasMore = pageBefore.isHasMore();
            before = pageBefore.getBefore();
        }
    }

}
