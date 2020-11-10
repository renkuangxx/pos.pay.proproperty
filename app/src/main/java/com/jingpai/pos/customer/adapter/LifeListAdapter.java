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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.web.CityLifeWebViewActivity;
import com.jingpai.pos.customer.base.Constant;
import com.jingpai.pos.customer.bean.MoreListBean;
import com.jingpai.pos.customer.mvp.presenter.LifePresenter;
import com.jingpai.pos.customer.network.NetWorkUtil;
import com.jingpai.pos.customer.utils.LocalCache;
import com.jingpai.pos.utils.ToastUtils;
import com.jingpai.pos.customer.views.UpdataInterface;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.jingpai.pos.BuildConfig.LIFE_CONTENT_URL;


//更多 列表
public class LifeListAdapter extends RecyclerView.Adapter<LifeListAdapter.ItemViewHolder> {
    private Context mContext;
    private List<MoreListBean.PageInfoBean.ListBean> listBeans;
    LifePresenter lifePresenter;
    private UpdataInterface updataInterface;

    public LifeListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public LifeListAdapter(Context mContext, List<MoreListBean.PageInfoBean.ListBean> listBeans1
            , LifePresenter lifePresenter,UpdataInterface updataInterface) {
        this.mContext = mContext;
        listBeans = new ArrayList<>();
        this.listBeans = listBeans1;
        this.lifePresenter = lifePresenter;
        this.updataInterface = updataInterface;
    }

    @NonNull
    @Override
    public LifeListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_life, parent, false);
        LifeListAdapter.ItemViewHolder holder = new LifeListAdapter.ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (listBeans.size() <= 0 || position > listBeans.size() || listBeans.get(position) == null)
            return;

        String touxiang = TextUtils.isEmpty(listBeans.get(position).getAvatar()) ? ""
                : listBeans.get(position).getAvatar();
        Glide.with(mContext).load(touxiang).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.iv_touxiang);

//        nickName	账号昵称
        String nickName = TextUtils.isEmpty(listBeans.get(position).getNickName()) ? ""
                : listBeans.get(position).getNickName();
        holder.tvNickName.setText(TextUtils.isEmpty(nickName) ?
                TextUtils.isEmpty(LocalCache.getUser().getName()) ? ""
                        : LocalCache.getUser().getName() : nickName);

//        cover	封面图片
        String pic = TextUtils.isEmpty(listBeans.get(position).getCover()) ? ""
                : listBeans.get(position).getCover();
        Glide.with(mContext).load(pic).into(holder.ivPic);

//        body	正文
        String body = TextUtils.isEmpty(listBeans.get(position).getSummary()) ? ""
                : listBeans.get(position).getSummary();
        holder.tvContent.setText(body);

//        likec	初始点赞数
        String likeCount = TextUtils.isEmpty(listBeans.get(position).getLikeCount() + "") ? ""
                : listBeans.get(position).getLikeCount() + "";
        holder.tvPicTitle.setText(likeCount+"人喜欢");

//        budget	预算
        String budget = listBeans.get(position).getSquareCost()==0 ? "面议"
                : "￥" + listBeans.get(position).getSquareCost();
        holder.tvPrice.setText(budget);

//        link	链接
        String link = TextUtils.isEmpty(listBeans.get(position).getLink()) ? ""
                : listBeans.get(position).getLink();
        String id = TextUtils.isEmpty(listBeans.get(position).getId()+"") ? ""
                : listBeans.get(position).getId()+"";

//        title	标题
        String title = TextUtils.isEmpty(listBeans.get(position).getTitle()) ? ""
                : listBeans.get(position).getTitle();
        holder.tvTitle.setText(title);

        //跳转h5
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CityLifeWebViewActivity.class);
                intent.putExtra(Constant.WEB_URL, LIFE_CONTENT_URL + "/"+id);
                mContext.startActivity(intent);
            }
        });

        boolean isLike = listBeans.get(position).isLike();
        if (isLike) { //red
            // todo 换图片
            Glide.with(mContext).load(R.mipmap.like).into(holder.iv_like);
        } else {      // white
            // todo 换图片
            Glide.with(mContext).load(R.mipmap.unlike).into(holder.iv_like);
        }


        TreeMap<String, Object> baseRequest = NetWorkUtil.getInstance().getBaseRequest();
        baseRequest.put("contentId", listBeans.get(position).getId());
        baseRequest.put("likePostId", TextUtils.isEmpty(LocalCache.getUserId()) ? ""
                : LocalCache.getUserId());
        baseRequest.put("likedUserId", listBeans.get(position).getOperUser());
        holder.tvPicTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike(isLike, holder, baseRequest);
            }
        });
        holder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLike(isLike, holder, baseRequest);
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

    private void setLike(boolean isLike, @NonNull ItemViewHolder holder, TreeMap<String, Object> baseRequest) {
        if (isLike) { //已经点赞 --》变为不点赞
            // todo 换图片
            lifePresenter.unLikeQuery(baseRequest, s -> {
                if (s == null) return;
                if ("true".equals(s)) {
                    ToastUtils.INSTANCE.showToast("取消点赞");
                    Glide.with(mContext).load(R.mipmap.unlike).into(holder.iv_like);

                }
            });
        } else {      // 不点赞 --》变为点赞
            // todo 换图片
            lifePresenter.likeQuery(baseRequest, s -> {
                if (s == null) return;
                if ("true".equals(s)) {
                    ToastUtils.INSTANCE.showToast("点赞成功");
                    Glide.with(mContext).load(R.mipmap.like).into(holder.iv_like);

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

    public void setData(List<MoreListBean.PageInfoBean.ListBean> list) {
        if (list != null) {
            listBeans.clear();
            listBeans.addAll(list);
            notifyDataSetChanged();
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPic, iv_touxiang, iv_like;
        private TextView tvPicTitle;
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvNickName;
        private TextView tvPrice;
        private RelativeLayout rl;
        private RelativeLayout rl_outside;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_pic);
            tvPicTitle = itemView.findViewById(R.id.tv_pic_title);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvNickName = itemView.findViewById(R.id.tv_nick_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            iv_touxiang = itemView.findViewById(R.id.iv_touxiang);
            rl = itemView.findViewById(R.id.rl);
            iv_like = itemView.findViewById(R.id.iv_like);
            rl_outside = itemView.findViewById(R.id.rl_outside);

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