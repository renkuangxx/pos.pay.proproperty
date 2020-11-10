package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
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

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.jingpai.pos.BuildConfig.LIFE_CONTENT_URL;


//空间楼层适配器
public class RecommondSpaceAdapter extends RecyclerView.Adapter<RecommondSpaceAdapter.RecommondSpaceViewHolder> {
    private Context mContext;
    private List<ContentLikeCollectPageInfo.ListBean> contentCateVos;
    LifePresenter lifePresenter;
    private UpdataInterface updataInterface;

    public RecommondSpaceAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public RecommondSpaceAdapter(Context mContext, List<ContentLikeCollectPageInfo.ListBean> contentCateVos1
            , LifePresenter lifePresenter, UpdataInterface updataInterface) {
        this.mContext = mContext;
        this.lifePresenter = lifePresenter;
        contentCateVos = contentCateVos1;
        this.updataInterface = updataInterface;
        contentCateVos = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecommondSpaceAdapter.RecommondSpaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_space_recommond, parent, false);
        RecommondSpaceAdapter.RecommondSpaceViewHolder holder = new RecommondSpaceAdapter.RecommondSpaceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommondSpaceViewHolder holder, int position) {
        if (contentCateVos.size() <= 0 || position > contentCateVos.size() || contentCateVos.get(position) == null)
            return;
        String title = TextUtils.isEmpty(contentCateVos.get(position).getTitle()) ? "" : contentCateVos.get(position).getTitle();
        String summary = TextUtils.isEmpty(contentCateVos.get(position).getSummary()) ? "" : contentCateVos.get(position).getSummary();
        String cover = TextUtils.isEmpty(contentCateVos.get(position).getCover()) ? "" : contentCateVos.get(position).getCover();
        String avatar = TextUtils.isEmpty(contentCateVos.get(position).getAvatar()) ? "" : contentCateVos.get(position).getAvatar();
        String nickName = TextUtils.isEmpty(contentCateVos.get(position).getNickName()) ? "" : contentCateVos.get(position).getNickName();
        boolean like = contentCateVos.get(position).isLike();
        String likecount = contentCateVos.get(position).getLikeCount() + "";
        String id = TextUtils.isEmpty(contentCateVos.get(position).getId()+"") ? "" : contentCateVos.get(position).getId()+"";

//顶部左边圆角
        RoundedCornersTransformation transformation = new RoundedCornersTransformation
                (20, 0, RoundedCornersTransformation.CornerType.TOP);

        //组合各种Transformation,
        MultiTransformation<Bitmap> mation = new MultiTransformation<>
                //Glide设置圆角图片后设置ImageVIew的scanType="centerCrop"无效解决办法,将new CenterCrop()添加至此
                (new CenterCrop(), transformation);
        Glide.with(mContext)
                .load(cover)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.touxiang)
                        .fallback(R.mipmap.touxiang)
                        .error(R.mipmap.touxiang))
                .into(holder.ivPic);
        if (like) { //红心
            // todo 换图片
            //设置图片圆角角度
            Glide.with(mContext)
                    .load(R.mipmap.like)
                    .into(holder.iv_like);
        } else {      //白心
            // todo 换图片
            Glide.with(mContext)
                    .load(R.mipmap.unlike)
                    .apply(new RequestOptions()
                            .transforms(new CenterCrop(), new RoundedCorners(4)))
                    .into(holder.iv_like);
        }


        Glide.with(mContext)
                .load(avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.iv_tou);
        holder.tvTitle.setText(title);
        holder.tvTitle2.setText(summary);
        holder.tv_nick_name.setText(nickName);
        holder.tv_num.setText(likecount);


        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("contentId", contentCateVos.get(position).getId());
        baseRequest.put("likePostId", TextUtils.isEmpty(LocalCache.getUserId()) ? ""
                : LocalCache.getUserId());
        baseRequest.put("likedUserId", contentCateVos.get(position).getOperUser());
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

    private void setLike(boolean like, @NonNull RecommondSpaceViewHolder holder, TreeMap<String, Object> baseRequest) {
        if (like) { //已经点赞 --》变为不点赞
            // todo 换图片
            lifePresenter.unLikeQuery(baseRequest, s -> {
                if (s == null) return;
                if ("success".equals(s)) {
                    ToastUtils.INSTANCE.showToast("取消点赞");
                    Glide.with(mContext)
                            .load(R.mipmap.unlike)
                            .into(holder.iv_like);
                }
            });
        } else {      // 不点赞 --》变为点赞
            lifePresenter.likeQuery(baseRequest, s -> {
                if (s == null) return;
                if ("success".equals(s)) {
                    ToastUtils.INSTANCE.showToast("点赞成功");
                    // todo 换图片
                    Glide.with(mContext)
                            .load(R.mipmap.like)
                            .into(holder.iv_like);
                }
            });
        }
        if (updataInterface != null) {
            updataInterface.updataInterface();
        }
    }

    @Override
    public int getItemCount() {
        return contentCateVos == null ? 0 : contentCateVos.size() > 4 ? 3 : contentCateVos.size();
    }

    public void setData(List<ContentLikeCollectPageInfo.ListBean> list) {
        if (list == null) return;
        contentCateVos.clear();
        contentCateVos.addAll(list);
        notifyDataSetChanged();
    }

    public class RecommondSpaceViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPic, iv_tou, iv_like;
        private TextView mengceng;
        private RelativeLayout rlTitle;
        private TextView tvTitle;
        private TextView tvTitle2;
        private TextView tv_nick_name;
        private TextView tv_num;
        private LinearLayout ll_outside;


        public RecommondSpaceViewHolder(@NonNull View itemView) {
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