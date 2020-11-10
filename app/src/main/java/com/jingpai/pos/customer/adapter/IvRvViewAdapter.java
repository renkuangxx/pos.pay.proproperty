package com.jingpai.pos.customer.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.base.MyApplication;
import com.jingpai.pos.customer.bean.show.FileBean;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * 时间: 2020/2/20
 * 功能:
 */
public class IvRvViewAdapter extends BaseQuickAdapter<FileBean, BaseViewHolder> {
    public IvRvViewAdapter(int layoutResId, @Nullable List<FileBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileBean item) {

        ImageView imageView = helper.getView(R.id.iv_matter_image);
        Glide.with(MyApplication.getContext())
                .load(item.getFilePath())
                .apply(bitmapTransform(new RoundedCornersTransformation(5, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(imageView);

    }
}
