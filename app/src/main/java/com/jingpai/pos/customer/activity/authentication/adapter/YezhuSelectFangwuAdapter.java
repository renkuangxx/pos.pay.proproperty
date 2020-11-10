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
import com.jingpai.pos.customer.activity.authentication.activity.SelectIdActivity;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.XiaoquFangwuInfoBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.views.UpdataInterface;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间: 2020/2/23
 * 功能: 业主认证 选择小区list
 */
public class YezhuSelectFangwuAdapter extends RecyclerView.Adapter<YezhuSelectFangwuAdapter.ItemViewHolder> {
    private Context mContext;
    private List<XiaoquFangwuInfoBean> listBeans =new ArrayList<>();
    LifePresenter lifePresenter;
    private UpdataInterface updataInterface;
    String finalInfo;

    public YezhuSelectFangwuAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public YezhuSelectFangwuAdapter(Context mContext,String finalInfo) {
        this.mContext = mContext;
        this.finalInfo = finalInfo;
    }

    public YezhuSelectFangwuAdapter(Context mContext, List<XiaoquFangwuInfoBean> listBeans1) {
        this.mContext = mContext;
        listBeans = new ArrayList<>();
        this.listBeans = listBeans1;
    }

    @NonNull
    @Override
    public YezhuSelectFangwuAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.village_item_yezhu, parent, false);
        YezhuSelectFangwuAdapter.ItemViewHolder holder = new YezhuSelectFangwuAdapter.ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull YezhuSelectFangwuAdapter.ItemViewHolder holder, int position) {
        if (listBeans.size() <= 0 || position > listBeans.size() || listBeans.get(position) == null)
            return;
        String roomNo =TextUtils.isEmpty(listBeans.get(position).getRoomNo())?"":listBeans.get(position).getRoomNo();
        int houseId =listBeans.get(position).getHouseId();
        holder.tv_village_item.setText(roomNo);
        holder.iv_selected.setVisibility(View.GONE);
        holder.rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.iv_selected.setVisibility(View.VISIBLE);
                Intent intent = new Intent(mContext, SelectIdActivity.class);
                intent.putExtra("finalInfo",finalInfo+roomNo);
                intent.putExtra("houseId",houseId);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listBeans == null ? 0 : listBeans.size();
    }

    public void setData(List<XiaoquFangwuInfoBean> list) {
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