package com.jingpai.pos.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingpai.pos.R;

import java.util.List;

/**
 * 时间: 2020/2/24
 * 功能:
 */
public class AiuiListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MOBILE = 0;//拨打电话
    private static final int TYPE_OPENDOOR = 1;//开门
    private List<Object> itemList;
    private Context mContext;
    public AiuiListAdapter(Context context, AiuiListener aiuiListener) {
        this.mContext = context;
        this.aiuiListener = aiuiListener;
    }
    public void setData(List<Object> items){
        itemList = items;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_MOBILE) {
            View mView1 = LayoutInflater.from(mContext).inflate(R.layout.item_aiui_mobile, null);
            ViewHolderMobile viewHolderMobile = new ViewHolderMobile(mView1);
            return viewHolderMobile;
        } else if (viewType == TYPE_OPENDOOR){
            View mView2 = LayoutInflater.from(mContext).inflate(R.layout.item_aiui_opendoor, null);
            ViewHolderOpenDoor viewHolderOpenDoor = new ViewHolderOpenDoor(mView2);
            return viewHolderOpenDoor;
        }else {
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder==null)return;
        if (holder instanceof ViewHolderMobile) {
//            ((ViewHolderMobile) holder).tv_name.setText(personList.get(position).getName());
//            //头像删除按钮点击
//            ((ViewHolderMobile) holder).img_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemAdapterClickListener.onItemClickEvent(view, position);
//                }
//            });
        } else if (holder instanceof ViewHolderOpenDoor) {
            //添加按钮点击
//            ((ViewHolderOpenDoor) holder).ly_add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemAdapterClickListener.onItemClickEvent(v, position);
//                }
//            });
        }
    }
    class ViewHolderMobile extends RecyclerView.ViewHolder {
        private ImageView img_delete;
        private TextView tv_name;

        public ViewHolderMobile(@NonNull View itemView) {
            super(itemView);
//            img_delete = itemView.findViewById(R.id.img_delete);
//            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    class ViewHolderOpenDoor extends RecyclerView.ViewHolder {
        private ImageView img_delete;
        private TextView tv_name;

        public ViewHolderOpenDoor(@NonNull View itemView) {
            super(itemView);
//            img_delete = itemView.findViewById(R.id.img_delete);
//            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
    @Override
    public int getItemCount() {
        return 0;
    }

    //openDoorBack.OpenDoorBack(item.getId());
    public interface AiuiListener{
        void aiuiListener(String id);
    }
    private AiuiListener aiuiListener;
    public void setAiuiListener(AiuiListener aiuiListener){
        this.aiuiListener = aiuiListener;
    }

}