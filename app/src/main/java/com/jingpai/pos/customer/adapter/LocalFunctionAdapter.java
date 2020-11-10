package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.ButtonListBean;
import com.jingpai.pos.customer.utils.JumpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 * jie ou
 */
public class LocalFunctionAdapter extends RecyclerView.Adapter<LocalFunctionAdapter.ButtonListViewHolder> {
    private List<ButtonListBean> listBeanList = new ArrayList<>();
    private Context mContext;
    private OnClick myOnClick;
    //接口回调
    public interface OnClick{
        public void onMoreFunclick();
        public void onItemclick(String finalLinkUrl);
    }
    public LocalFunctionAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setClickListener(OnClick okListener) {
        this.myOnClick = okListener;
    }
    public LocalFunctionAdapter(List<ButtonListBean> listBeanList, Context mContext) {
        this.listBeanList = listBeanList;
        this.mContext = mContext;
    }

    public void setDataChange(List<ButtonListBean> buttonListBeans) {
        listBeanList.clear();
        listBeanList.addAll(buttonListBeans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocalFunctionAdapter.ButtonListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_function, parent, false);
        ButtonListViewHolder holder = new ButtonListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocalFunctionAdapter.ButtonListViewHolder holder, int position) {
        if (listBeanList.size() <= 0 || position > listBeanList.size() || listBeanList.get(position) ==null) return;
        String title = listBeanList.get(position).getTitle()==null?"":listBeanList.get(position).getTitle();
        String icon = listBeanList.get(position).getIcon();
        String linkUrl = listBeanList.get(position).getUrl()==null?"":listBeanList.get(position).getUrl();
        String finalLinkUrl = JumpUtil.containString(linkUrl);
        Glide.with(mContext).load(icon).into(holder.iv_icon);
        holder.tv_title_icon.setText(title);

        //tiaozhuan
        holder.ll_father.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myOnClick != null) {
                    if (listBeanList.get(position).isUsable()){
                        myOnClick.onItemclick(finalLinkUrl);
                    }else{
                        myOnClick.onItemclick("");
                    }
                }
//                if (StringUtils.isEmpty(LocalCache.getCurrentCommunityName())){
//                    //                    isNeedRegster();
////                    ToastUtils.INSTANCE.showToast("您暂无权限");
//                    mContext.startActivity(new Intent(mContext, MyHouseDefaultActivity.class));
//                }else {
//                    switch (position){
//                        case 0:
//                            mContext.startActivity(new Intent(mContext, AddPhoneFeeActivity.class));
//                            break;
//                        case 1:
//                            EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_LIFE_SERVER,null));
//                            break;
//                        case 2:
//                            EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_ELECTRONIC_APPLIANCES,null));
//                            break;
//                        case 3:
//                            EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_CAR_LIFE,null));
//                            break;
//                        case 4:
//                            EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_PET_PARADISE,null));
//                            break;
//                        case 5:
//                            ToastUtils.INSTANCE.showToast("功能暂未开放，敬请期待");
//                            break;
//                        case 6:
//                            ToastUtils.INSTANCE.showToast("功能暂未开放，敬请期待");
//                            break;
//                        case 7:
//                            ToastUtils.INSTANCE.showToast("功能暂未开放，敬请期待");
//                            break;
//                        case 8:
//                            EventBus.getDefault().postSticky(new EventBusMessage(Constant.EVENT_BUS_COMMUNITY_GROUP_BUY,null));
//                            break;
//                        case 9:
//                            ToastUtils.INSTANCE.showToast("功能暂未开放，敬请期待");
//                            break;
//
//                    }
                }
//            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeanList == null ? 0 : listBeanList.size();
    }

    public class ButtonListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title_icon;
        private LinearLayout ll_father;
        private ImageView iv_icon;
        public ButtonListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title_icon = itemView.findViewById(R.id.tv_title_icon);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            ll_father = itemView.findViewById(R.id.ll_father);
        }
    }
}
