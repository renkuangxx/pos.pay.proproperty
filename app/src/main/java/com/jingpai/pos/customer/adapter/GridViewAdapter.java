package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.utils.MainConstant;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/*
 * function:
 */
public class GridViewAdapter extends android.widget.BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    public GridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent, false);
        if (position < mList.size()) {
            convertView = inflater.inflate(R.layout.grid_item_iv, parent, false);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_item_image);
            //ImageView ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            //代表+号之前的需要正常显示图片
            String picUrl = mList.get(position); //图片路径
            Glide.with(mContext).load(picUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(7, 0, RoundedCornersTransformation.CornerType.ALL)))
                    .into(iv);
        }
        return convertView;
    }
}