package com.jingpai.pos.customer.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.show.PromotionBean;
import com.jingpai.pos.customer.utils.GlideUtils;

import java.util.List;

/*
 * function:
 */
public class CommunityLifeAdapter extends BaseQuickAdapter<PromotionBean, BaseViewHolder> {

    public CommunityLifeAdapter(int layoutResId, @Nullable List<PromotionBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, PromotionBean item) {
//        ImageView imageView = helper.getView(R.id.iv_community_home_product);
//        TextView labell = helper.getView(R.id.iv_community_home_product_label1);
//        TextView label2 = helper.getView(R.id.iv_community_home_product_label2);
//        TextView label3 = helper.getView(R.id.iv_community_home_product_label3);
//        ImageView iv_end = helper.getView(R.id.iv_end);
//        ImageView iv_meng = helper.getView(R.id.iv_meng);
//        GlideUtils.LoadingRoundedCornersImg(mContext,item.getPromotionPic(),imageView,10);
//        helper.setText(R.id.iv_community_home_product_name, item.getPromotionName());
//        helper.setText(R.id.iv_community_home_product_date, "活动截止："+DateUtil.formatStrYYMMDD(item.getPromotionEndTime()));
//        //        新增图层展示
//        iv_end.setVisibility(item.getPromotionStatus() ==2 ? View.VISIBLE : View.GONE);
//        iv_meng .setVisibility(item.getPromotionStatus() ==2 ? View.VISIBLE : View.GONE);
//        Glide.with(MyApplication.getContext()).asBitmap().load(item.getPromotionEndIconUrl())
//                .into(iv_end);
//        Glide.with(MyApplication.getContext()).asBitmap().load(item.getPromotionPic())
//                .into(imageView);
//        int size = item.getLabelList().size();
//        if (null!=item.getLabelList()){
//            if (size>0){
//                labell.setText(item.getLabelList().get(0));
//                labell.setVisibility(View.VISIBLE);
//            }
//            if (size>1){
//                label2.setText(item.getLabelList().get(1));
//                label2.setVisibility(View.VISIBLE);
//            }
//            if (size>2){
//                label3.setText(item.getLabelList().get(2));
//                label3.setVisibility(View.VISIBLE);
//            }
//        }
//
//        View takeView = helper.getView(R.id.takeview);
//        ViewGroup.LayoutParams layoutParams1 = takeView.getLayoutParams();
//        if (helper.getAdapterPosition()==0){
//            layoutParams1.width =  SystemUtils.Dp2Px(mContext,16);
//        }else{
//            layoutParams1.width =  SystemUtils.Dp2Px(mContext,10);
//        }
//        takeView.setLayoutParams(layoutParams1);
//
//        LinearLayout view = helper.getView(R.id.ll_promotion);
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.width =  SystemUtils.getScreenWidth(mContext)- SystemUtils.Dp2Px(mContext,50);
//        view.setLayoutParams(layoutParams);

        ImageView iv_end = helper.getView(R.id.iv_end);
        ImageView iv_meng = helper.getView(R.id.iv_meng);
        //        新增图层展示
        iv_end.setVisibility(item.getPromotionStatus() ==2 ? View.VISIBLE : View.GONE);
        Glide.with(MyApplication.getContext()).asBitmap().load(item.getPromotionEndIconUrl())
                .into(iv_end);
        iv_meng .setVisibility(item.getPromotionStatus() ==2 ? View.VISIBLE : View.GONE);
        ImageView activity = helper.getView(R.id.iv_community_activity);
//        View viewSpace = helper.getView(R.id.takeview);
//        int screenWidth = SystemUtils.getScreenWidth(mContext);
//        int height = (screenWidth-SystemUtils.Dp2Px(mContext,56))*180/310;
//        int width = (screenWidth-SystemUtils.Dp2Px(mContext,66))/2;
//        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams)activity.getLayoutParams();
//        params.height = height;
//        params.width = width;
////        activity.setLayoutParams(params);
////        iv_meng.setLayoutParams(params);
        GlideUtils.LoadingRoundedCornersImg(mContext,item.getPromotionCommuPic(),activity,5);
        LinearLayout view = helper.getView(R.id.ll_promotion);
        view.setOnClickListener(v -> onItemClick.onItemClick(item.getPromotionId()));
//        if (helper.getAdapterPosition()==1){
//            viewSpace.setVisibility(View.GONE);
//        }
    }

    public interface OnItemClick {
        void onItemClick(String promotionId);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick OnItemClick) {
        this.onItemClick = OnItemClick;
    }

}