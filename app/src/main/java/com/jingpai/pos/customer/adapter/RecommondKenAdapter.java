package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.ContentLikeCollectPageInfo;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.views.UpdataInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.jingpai.pos.BuildConfig.LIFE_CONTENT_URL;


//空间楼层适配器
public class RecommondKenAdapter extends RecyclerView.Adapter<RecommondKenAdapter.RecommondKenViewHolder> {
    private Context mContext;
    private List<ContentLikeCollectPageInfo.ListBean> listBeans;
    LifePresenter lifePresenter;

    public RecommondKenAdapter(Context mContext) {
        this.mContext = mContext;
    }
    private UpdataInterface updataInterface;
    public RecommondKenAdapter(Context mContext, List<ContentLikeCollectPageInfo.ListBean>
            listBeans1, LifePresenter lifePresenter,UpdataInterface updataInterface) {
        this.mContext = mContext;
        listBeans = new ArrayList<>();
        listBeans = listBeans1;
        this.lifePresenter = lifePresenter;
        this.updataInterface=updataInterface;
    }

    @NonNull
    @Override
    public RecommondKenAdapter.RecommondKenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ken_recommond, parent, false);
        RecommondKenAdapter.RecommondKenViewHolder holder = new RecommondKenAdapter.RecommondKenViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommondKenViewHolder holder, int position) {
        if (listBeans.size() <= 0 || position > listBeans.size() || listBeans.get(position) == null)
            return;
        String title = TextUtils.isEmpty(listBeans.get(position).getTitle()) ? "" : listBeans.get(position).getTitle();
        String summary = TextUtils.isEmpty(listBeans.get(position).getSummary()) ? "" : listBeans.get(position).getSummary();
        String cover = TextUtils.isEmpty(listBeans.get(position).getCover()) ? "" : listBeans.get(position).getCover();
        String avatar = TextUtils.isEmpty(listBeans.get(position).getAvatar()) ? "" : listBeans.get(position).getAvatar();
        String id = TextUtils.isEmpty(listBeans.get(position).getId()+"") ? "" : listBeans.get(position).getId()+"";
        String nickName = TextUtils.isEmpty(listBeans.get(position).getNickName()) ? "" : listBeans.get(position).getNickName();
        boolean like = listBeans.get(position).isLike();
        String likecount = listBeans.get(position).getLikeCount() + "";

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
//                .apply(RequestOptions.bitmapTransform(
//                        new RoundedCornersTransformation(6, 0,
//                                RoundedCornersTransformation.CornerType.TOP)))
                .into(holder.ivPic);
        Glide.with(mContext).load(avatar).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.iv_tou);
        holder.tvTitle.setText(title);
        holder.tvTitle2.setText(summary);
        holder.tv_nick_name.setText(nickName);
        holder.tv_num.setText(likecount);


        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("contentId", listBeans.get(position).getId());
        baseRequest.put("likePostId",TextUtils.isEmpty(LocalCache.getUserId()) ? ""
                : LocalCache.getUserId());
        baseRequest.put("likedUserId",  listBeans.get(position).getOperUser());
        holder.tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike(like, holder, baseRequest);
            }
        });
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike(like, holder, baseRequest);
            }
        });

        holder.ll_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CityLifeWebViewActivity.class);
                intent.putExtra(Constant.WEB_URL, LIFE_CONTENT_URL + "/"+id);
                mContext.startActivity(intent);
            }
        });

    }

    private void setLike(boolean like, @NonNull RecommondKenViewHolder holder, TreeMap<String, Object> baseRequest) {
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
        return listBeans == null ? 0 : listBeans.size();
    }

    public void setData(List<ContentLikeCollectPageInfo.ListBean> list) {
        if (list == null) return;
        listBeans.clear();
        listBeans.addAll(list);
        notifyDataSetChanged();
    }

    public class RecommondKenViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPic, iv_tou, iv_like;
        private TextView mengceng;
        private RelativeLayout rlTitle;
        private TextView tvTitle;
        private TextView tvTitle2;
        private TextView tv_nick_name;
        private TextView tv_num;
        private LinearLayout ll_outside;


        public RecommondKenViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            mengceng = itemView.findViewById(R.id.mengceng);
            rlTitle = itemView.findViewById(R.id.rl_title);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTitle2 = itemView.findViewById(R.id.tv_title2);
            iv_like = itemView.findViewById(R.id.iv_like);
            iv_tou = itemView.findViewById(R.id.iv_tou);
            tv_nick_name = itemView.findViewById(R.id.tv_nick_name);
            tv_num = itemView.findViewById(R.id.tv_num);
            ll_outside = itemView.findViewById(R.id.ll_outside);
        }
    }
}