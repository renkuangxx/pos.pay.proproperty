package com.jingpai.pos.customer.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.AccessBean;

/**
 * 时间: 2020/2/24
 * 功能:
 */
public class GuardListAdapter extends BaseQuickAdapter<AccessBean, BaseViewHolder> {

    public GuardListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void setEmptyView(int layoutResId) {
        super.setEmptyView(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccessBean item) {
        helper.setText(R.id.tv_door_bear_name, item.getName());
        ImageView imageView = helper.getView(R.id.iv_open_door);
        imageView.setOnClickListener(v -> {
            openDoorBack.OpenDoorBack(item.getId());
        });
    }

    public interface OpenDoorBack{
        void OpenDoorBack(String id);
    }

    private OpenDoorBack openDoorBack;

    public void setOpenDoorBack(OpenDoorBack openDoorBack){

        this.openDoorBack = openDoorBack;

    }

}