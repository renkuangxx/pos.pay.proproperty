package com.jingpai.pos.customer.activity.authentication.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.authentication.activity.YezhuRegisterSearchDanyuanActivity;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.YezhuXiaoquLoudongInfoBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.views.UpdataInterface;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间: 2020/2/23
 * 功能: 业主认证 选择小区list
 */
public class YezhuSelectLoudongAdapter extends RecyclerView.Adapter<YezhuSelectLoudongAdapter.ItemViewHolder> {
    private Context mContext;
    private List<YezhuXiaoquLoudongInfoBean.data> listBeans =new ArrayList<>();
    LifePresenter lifePresenter;
    private UpdataInterface updataInterface;
    String loudongInfo;

    public YezhuSelectLoudongAdapter(Context mContext) {
        this.mContext = mContext;
    }  public YezhuSelectLoudongAdapter(Context mContext,String loudongInfo) {
        this.mContext = mContext;
        this.loudongInfo = loudongInfo;
    }

    public YezhuSelectLoudongAdapter(Context mContext, List<YezhuXiaoquLoudongInfoBean.data> listBeans1) {
        this.mContext = mContext;
        listBeans = new ArrayList<>();
        this.listBeans = listBeans1;
    }

    @NonNull
    @Override
    public YezhuSelectLoudongAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.village_item_yezhu, parent, false);
        YezhuSelectLoudongAdapter.ItemViewHolder holder = new YezhuSelectLoudongAdapter.ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull YezhuSelectLoudongAdapter.ItemViewHolder holder, int position) {
        if (listBeans.size() <= 0 || position > listBeans.size() || listBeans.get(position) == null)
            return;
        int id =listBeans.get(position).getBuildingId();
        String num = TextUtils.isEmpty(listBeans.get(position).getNumber())?"":listBeans.get(position).getNumber();
        holder.tv_village_item.setText(num);
        holder.iv_selected.setVisibility(View.GONE);
        holder.rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.iv_selected.setVisibility(View.VISIBLE);
                Intent intent = new Intent(mContext, YezhuRegisterSearchDanyuanActivity.class);
                intent.putExtra("buildingId",id);
                intent.putExtra("loudongInfo",loudongInfo+num);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listBeans == null ? 0 : listBeans.size();
    }

    public void setData(List<YezhuXiaoquLoudongInfoBean.data> list) {
        if (list != null) {
            listBeans.clear();
            listBeans.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_selected;
        private TextView tv_village_item,tv_distance;
        private RelativeLayout rl_parent;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_selected = itemView.findViewById(R.id.iv_selected);
            tv_village_item = itemView.findViewById(R.id.tv_village_item);
            tv_distance = itemView.findViewById(R.id.tv_distance);
            rl_parent = itemView.findViewById(R.id.rl_parent);

        }
    }

    /**
     * WebView打开公司简介url
     *
     * @param context
     * @param url
     * @throws JSONException
     */
    private void startWebViewActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, CityLifeWebViewActivity.class);
        intent.putExtra(Constant.WEB_URL, url);
        intent.putExtra(Constant.WEB_BACK, true);
        intent.putExtra(Constant.WEB_TITLE, title);
        context.startActivity(intent);
    }
}