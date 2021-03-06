package com.jingpai.pos.customer.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.show.PromotionBean;

import java.util.List;

/*
 * function:
 */
public class RvActivityAdapter extends BaseQuickAdapter<PromotionBean, BaseViewHolder> {

    public RvActivityAdapter(int layoutResId, @Nullable List<PromotionBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, PromotionBean item) {
        helper.setText(R.id.tv_activity_title, item.getPromotionName());
        ImageView imageView = helper.getView(R.id.iv_activity_image);
        ImageView iv_end = helper.getView(R.id.iv_end);
        ImageView iv_meng = helper.getView(R.id.iv_meng);

        LinearLayout tvLabel = helper.getView(R.id.ll_label);
        tvLabel.removeAllViews();
        //新增图层展示

        iv_end.setVisibility(item.getPromotionStatus() ==2 ? View.VISIBLE : View.GONE);
        iv_meng .setVisibility(item.getPromotionStatus() ==2 ? View.VISIBLE : View.GONE);
        Glide.with(MyApplication.getContext()).asBitmap().load(item.getPromotionEndIconUrl())
                .into(iv_end);
        Glide.with(MyApplication.getContext()).asBitmap().load(item.getPromotionPic())
                .into(imageView);
        helper.setText(R.id.tv_enrollCount, item.getEnrollCount());

        if (item.getPrice().equals("0.00") || item.getPrice().equals("0.0") || item.getPrice().equals("0")) {
            helper.setText(R.id.tv_price, "免费");
        } else {
            helper.setText(R.id.tv_price, "￥" + item.getPrice());
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < item.getLabelList().size(); i++) {
            TextView textView = new TextView(MyApplication.getContext());
            layoutParams.setMargins(0, 0, 10, 0);

            textView.setPadding(2, 5, 2, 5);
            textView.setTextSize(8);
            textView.setBackgroundResource(R.drawable.label_style);
            //设置背景
            textView.setCompoundDrawables(null, null, null, null);
            textView.setCompoundDrawablePadding(10);
            textView.setTextColor(Color.parseColor("#FF8A49"));
            textView.setText(" " + item.getLabelList().get(i) + " ");
            textView.setLayoutParams(layoutParams);
            tvLabel.addView(textView);
        }

        View view = helper.getView(R.id.ll_promotion);
        view.setOnClickListener(v -> onItemClick.onItemClick(item.getPromotionId()));

    }

    public interface OnItemClick {
        void onItemClick(String promotionId);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick OnItemClick) {
        this.onItemClick = OnItemClick;
    }

}