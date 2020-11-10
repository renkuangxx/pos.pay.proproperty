package com.jingpai.pos.customer.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.ManagerBean;
import com.jingpai.pos.customer.utils.PixelUtil;

import java.util.List;

/**
 * @author
 * tips：管家Adapter
 */
public class RvManagerAdapter extends BaseQuickAdapter<ManagerBean, BaseViewHolder> {

    public RvManagerAdapter(int layoutResId, @Nullable List<ManagerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ManagerBean item) {
        ImageView ivHead = helper.getView(R.id.iv_head);
        //不需要判断 直接交给glide处理逻辑
        if ("".equals(item.getAvatar())){
            ivHead.setImageResource(R.mipmap.touxiang);
        }else{
            Glide.with(MyApplication.getContext()).load(item.getAvatar())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(ivHead);
        }
        helper.setText(R.id.tv_manager_name, item.getName());
        ImageView ivPhone = helper.getView(R.id.iv_phone);
        ivPhone.setOnClickListener(v -> {
            callBack.phoneCallBack(item.getPhone());
        });

        if (mData.size() > 1) {
            //item变短
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) helper.itemView.getLayoutParams();
            PixelUtil pixelUtil = new PixelUtil(mContext);
            layoutParams.width = pixelUtil.width() - pixelUtil.dp2px(32) - pixelUtil.dp2px(74);
            layoutParams.setMarginEnd(pixelUtil.dp2px(6));
            helper.itemView.setLayoutParams(layoutParams);
        } else {
            //item变短
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) helper.itemView.getLayoutParams();
            PixelUtil pixelUtil = new PixelUtil(mContext);
            layoutParams.width = pixelUtil.width() - pixelUtil.dp2px(32);
            helper.itemView.setLayoutParams(layoutParams);
        }


    }

    public interface CallBack {
        void phoneCallBack(String phone);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
}
