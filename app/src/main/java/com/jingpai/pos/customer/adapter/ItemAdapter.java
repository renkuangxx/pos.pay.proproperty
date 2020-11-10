package com.jingpai.pos.customer.adapter;

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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.HuxingListBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.views.UpdataInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.jingpai.pos.BuildConfig.LIFE_CONTENT_URL;


//户型推荐
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mContext;
    private List<HuxingListBean> huxingListBeans;
private LifePresenter lifePresenter;
    public ItemAdapter(UpdataInterface updataInterface){
        this.updataInterface=updataInterface;
    }
    public ItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    private UpdataInterface updataInterface;

    public ItemAdapter(Context mContext, List<HuxingListBean> historyInfoBeans
            ,LifePresenter lifePresenter,UpdataInterface updataInterface) {
        this.mContext = mContext;
        historyInfoBeans=new ArrayList<>();
        this.huxingListBeans = historyInfoBeans;
        this.lifePresenter = lifePresenter;
        this.updataInterface=updataInterface;
    }

    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_standard, parent, false);
        ItemAdapter.ItemViewHolder holder = new ItemAdapter.ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (huxingListBeans.size() <= 0 || position > huxingListBeans.size() || huxingListBeans.get(position) ==null) return;
        String title = TextUtils.isEmpty(huxingListBeans.get(position).getTitle()) ? "" : huxingListBeans.get(position).getTitle();
        String summary = TextUtils.isEmpty(huxingListBeans.get(position).getSummary()) ? "" : huxingListBeans.get(position).getSummary();
        String cover = TextUtils.isEmpty(huxingListBeans.get(position).getCover()) ? "" : huxingListBeans.get(position).getCover();
        String avatar = TextUtils.isEmpty(huxingListBeans.get(position).getAvatar()) ? "" : huxingListBeans.get(position).getAvatar();
        String nickName = TextUtils.isEmpty(huxingListBeans.get(position).getNickName()) ? "" : huxingListBeans.get(position).getNickName();
        String id = TextUtils.isEmpty(huxingListBeans.get(position).getId()+"") ? "" : huxingListBeans.get(position).getId()+"";
        boolean like = huxingListBeans.get(position).isLike();
        String likecount = huxingListBeans.get(position).getLikeCount() + "";

        if (like) { //红心
            // todo 换图片
            Glide.with(mContext)
                    .load(R.mipmap.like)
                    .into(holder.iv_like);
        } else {      //白心
            // todo 换图片
            Glide.with(mContext)
                    .load(R.mipmap.unlike)
                    .into(holder.iv_like);
        }
        Glide.with(mContext)
                .load(cover)
                .apply(new RequestOptions()
                .placeholder(R.mipmap.touxiang)
                .fallback(R.mipmap.touxiang)
                .error(R.mipmap.touxiang))
                .into(holder.ivPic);

        Glide.with(mContext)
                .load(avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.iv_tou);
        holder.tvTitle.setText(title);
        holder.tvTitle2.setText(summary);
        holder.tv_nick_name.setText(nickName);
        holder.tv_num.setText(likecount);


        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("contentId", huxingListBeans.get(position).getId());
        baseRequest.put("likePostId", TextUtils.isEmpty(LocalCache.getUserId()) ? ""
                : LocalCache.getUserId());
        baseRequest.put("likedUserId",huxingListBeans.get(position).getOperUser() );
        holder.tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeClick(like, holder, baseRequest);
            }
        });
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeClick(like, holder, baseRequest);
            }
        });

        holder.rl_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CityLifeWebViewActivity.class);
                intent.putExtra(Constant.WEB_URL, LIFE_CONTENT_URL + "/"+id);
                mContext.startActivity(intent);
            }
        });
    }

    private void likeClick(boolean like, @NonNull ItemViewHolder holder, TreeMap<String, Object> baseRequest) {
        if (like) { //已经点赞 --》变为不点赞
            lifePresenter.unLikeQuery(baseRequest, s -> {
                if (s == null) return;
                if ("true".equals(s)) {
                    ToastUtils.INSTANCE.showToast("取消点赞");
                    // todo 换图片
                    Glide.with(mContext)
                            .load(R.mipmap.unlike)
                            .into(holder.iv_like);
                }
            });
        } else {      // 不点赞 --》变为点赞

            lifePresenter.likeQuery(baseRequest, s -> {
                if (s == null) return;
                if ("true".equals(s)) {
                    ToastUtils.INSTANCE.showToast("点赞成功");
                    // todo 换图片
                    Glide.with(mContext)
                            .load(R.mipmap.like)
                            .into(holder.iv_like);
                }
            });
        }
        if (updataInterface!=null){
            updataInterface.updataInterface();
        }
    }

    @Override
    public int getItemCount() {
        return huxingListBeans == null ? 0 : huxingListBeans.size()>=0 && huxingListBeans.size() < 3 ? huxingListBeans.size() : 3 ;
    }

    public void setData(List<HuxingListBean> list){
        if (list == null || list.size()==0)return;
        huxingListBeans.clear();
        huxingListBeans.addAll(list);
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private CardView cdPic;
        private ImageView ivPic, iv_tou, iv_like;
        private RelativeLayout rlTitle,rl_outside;
        private TextView tvTitle;
        private TextView tvTitle2;
        private TextView tv_nick_name;
        private TextView tv_num;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cdPic = itemView.findViewById(R.id.cd_pic);
            ivPic = itemView.findViewById(R.id.iv_pic);
            rlTitle = itemView.findViewById(R.id.rl_title);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTitle2 = itemView.findViewById(R.id.tv_title2);
            iv_like = itemView.findViewById(R.id.iv_like);
            iv_tou = itemView.findViewById(R.id.iv_tou);
            tv_nick_name = itemView.findViewById(R.id.tv_nick_name);
            tv_num = itemView.findViewById(R.id.tv_num);
            rl_outside = itemView.findViewById(R.id.rl_outside);
        }
    }
}