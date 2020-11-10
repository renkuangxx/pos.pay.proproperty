package com.jingpai.pos.customer.adapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.bean.show.MatterDetailBean;
import com.jingpai.pos.customer.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

public class MatterDealwithAdapter extends BaseQuickAdapter<MatterDetailBean.HandlersBean, BaseViewHolder> {
    private List<MatterDetailBean.HandlersBean> handlerBeanList;
    OnPicClick onPicClick;
    //接口回调
    public interface OnPicClick{
        void onPicClick(int position, ArrayList<String> list);
    }
    public MatterDealwithAdapter(int layoutResId, @Nullable List<MatterDetailBean.HandlersBean> data,OnPicClick picClick) {
        super(layoutResId, data);
        handlerBeanList = data;
        onPicClick = picClick;
    }

    @Override
    protected void convert(BaseViewHolder helper, MatterDetailBean.HandlersBean item) {
        helper.setText(R.id.tv_manage_name,item.getUsername()==null?"":item.getUsername());
        helper.setText(R.id.tv_manage_phone,item.getUserphone()==null?"":item.getUserphone());
        helper.setText(R.id.tv_manage_time,item.getModifyTime()==null?"":item.getHandleTime());

        MyGridView rvPicItem = helper.getView(R.id.rv_processing);
        ImageView ivConfirm = helper.getView(R.id.iv_seal);
        if (item.getState()==4||item.getState()==5){
            ivConfirm.setVisibility(View.VISIBLE);
        }else{
            ivConfirm.setVisibility(View.GONE);
        }
        ArrayList<String> list = item.getImage();
        if (list!=null){
            //获取解决后上传的图片
            GridImageViewAdapter gridViewAdapter1 = new GridImageViewAdapter(mContext, list);
            rvPicItem.setAdapter(gridViewAdapter1);
            rvPicItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onPicClick.onPicClick(position,list);
                }
            });
        }


    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
