package com.jingpai.pos.customer.activity.census.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONArray;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.GatherInfoPresenterOld;
import com.jingpai.pos.customer.activity.census.OnDeleteClickLister;
import com.jingpai.pos.customer.activity.census.adapter.UserInfoAdapter;
import com.jingpai.pos.customer.activity.census.bean.DataBean;
import com.jingpai.pos.customer.base.BaseActivity;
import com.jingpai.pos.customer.bean.show.BuildingBean;
import com.jingpai.pos.customer.mvp.presenter.show.home.MemberPresenterImp;
import com.jingpai.pos.customer.utils.Intents;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.customer.network.NetWorkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 86173
 */
public class FirstCensusActivity extends BaseActivity {
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.btn_more)
    RelativeLayout btnMore;
    @BindView(R.id.rv_history)
    SlideRecyclerView rvHistory;
    @BindView(R.id.btn_add_info)
    Button btnAddInfo;
    private UserInfoAdapter userInfoAdapter;
    private GatherInfoPresenterOld gatherInfoPresenter;
    private MemberPresenterImp memberPresenterImp;

    private Dialog dialog;
    private List<DataBean> list;
    boolean isYeZhu;
    boolean hasYeZhu;
    @Override
    protected int getLayout() {
        return R.layout.activity_first_census;
    }

    @Override
    protected void initData() {
        initView();
        gatherInfoPresenter = new GatherInfoPresenterOld();
        refreshData();
        deleteInfo();
        getIsYeZhu();
    }

    private void getIsYeZhu() {
        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("communityId", LocalCache.getCurrentCommunityId());
        memberPresenterImp.HouseQueryData(baseRequest, this::memberData);
    }

    //业主查询
    public void memberData(JSONArray jsonArray) {
        List<BuildingBean.DataBean> data = jsonArray.toJavaList(BuildingBean.DataBean.class);
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getType().equals("OWNER")) {
                isYeZhu = true;
            }
        }
    }

    //shanchu
    private void deleteInfo() {
        userInfoAdapter.setOnDeleteClickListener(new OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                int id = list.get(position).getPopulation() == null ? 0 : list.get(position).getPopulation().id;
                if (id != 0) {
                    TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
                    baseRequest.put("id", Integer.valueOf(id));
                    gatherInfoPresenter.deleteInfoQuery(baseRequest, this::callback);
                    list.remove(position);
                    userInfoAdapter.notifyDataSetChanged();
                    rvHistory.closeMenu();
                }
            }

            private void callback(Object o) {
                if (o != null) {
                    ToastUtils.showShort("删除成功");
                    refreshData();
                }
            }
        });
    }

    @Override
    protected void refreshData() {
        super.refreshData();

        gatherInfoPresenter.userInfoAllQuery(UserInfoAllBean -> {
            smartRefreshLayout.finishRefresh();
            if (UserInfoAllBean != null) {
                String image = UserInfoAllBean.getImage() == null ? "" : UserInfoAllBean.getImage();
                String textInfo = UserInfoAllBean.getText() == null ? "" : UserInfoAllBean.getText();
                Glide.with(this).load(image).into(ivTop);
                Glide.with(this).load(image).into(ivTop);
                tvInfo.setText(textInfo);
                //rv填充 传值
                list = UserInfoAllBean.data;
                userInfoAdapter.setData(list);
                if (list != null && list.size() > 0) {
                    btnMore.setVisibility(View.VISIBLE);
                } else {
                    btnMore.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initView() {
        btnMore.setVisibility(View.GONE);
        list = new ArrayList<>();
        userInfoAdapter = new UserInfoAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setAdapter(userInfoAdapter);
        memberPresenterImp = new MemberPresenterImp();
    }

    @OnClick({R.id.btn_add_info,R.id.tv_right_btn,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //添加人员按钮点击事件
            case R.id.btn_add_info:
                setDialog();
                break;
            case R.id.tv_right_btn:
                Intents.getInstence().intent(this, HistoryInfoActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void setDialog() {
        //1、使用Dialog、设置style
        dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.takephoto_dialog, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        TextView yeZhu = dialog.findViewById(R.id.tv_take_photo);
        yeZhu.setText("添加业主信息");
        yeZhu.setOnClickListener(view1 -> {
            int yezhuId = 0;
            int baifenbi = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null) {
                    return;
                }
                if (list.get(i).getIdentity().equals("业主")) {
                    hasYeZhu = true;
                    yezhuId = list.get(i).getPopulation() == null ? 0 : list.get(i).getPopulation().getId();
                    baifenbi = list.get(i).getCompleteness();
                    if (baifenbi < 100) {
                        LocalCache.saveInfo(yezhuId + "", list.get(i).getPopulation());
                    }
                    break;
                }
            }
            if (hasYeZhu && baifenbi < 100) { //有记录 是业主 未完成
                if (yezhuId == 0) return;
                Intent intent = new Intent(this, OwnerInfoActivity.class);
                intent.putExtra("idNum", yezhuId + "");
                intent.putExtra("position", "0");
                startActivity(intent);
                dialog.dismiss();
            } else if (hasYeZhu && (baifenbi == 100)) {
                ToastUtils.showShort("您已提交完成");
                dialog.dismiss();
            } else if (!hasYeZhu && isYeZhu) {
                Intent intent = new Intent();
                intent.putExtra("idNum",  "");
                intent.putExtra("position", "0");
                intent.setClass(this, OwnerInfoActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        TextView otherInfo = dialog.findViewById(R.id.tv_take_pic);
        otherInfo.setText("添加家属/租户信息");
        otherInfo.setOnClickListener(view12 -> {
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, "1");
            Intent intent = new Intent();
            intent.putExtra("idNum",  "");
            intent.putExtra("position", "1");
            intent.setClass(this, OwnerInfoActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(view13 -> dialog.dismiss());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
