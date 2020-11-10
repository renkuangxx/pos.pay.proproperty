package com.jingpai.pos.customer.activity.census.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.authentication.FilterYezhuListener;
import com.jingpai.pos.customer.bean.XiaoquFangwuInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86173
 */
public class ChoseYezhuFangwuAdapter extends BaseAdapter implements Filterable {

    private List<XiaoquFangwuInfoBean> list = new ArrayList<>();
    private Context context;
    private MyFilter filter = null;// 创建MyFilter对象
    private FilterYezhuListener listener = null;// 接口对象

    public ChoseYezhuFangwuAdapter(List<XiaoquFangwuInfoBean> list, Context context, FilterYezhuListener filterListener) {
        this.list = list;
        this.context = context;
        this.listener = filterListener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_minzu_list, null);
            holder = new ViewHolder();
            holder.tv_ss = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.tv_ss.setText(list.get(position).getRoomNo());
        return convertView;
    }

    /**
     * 自定义MyAdapter类实现了Filterable接口，重写了该方法
     */
    @Override
    public Filter getFilter() {
        // 如果MyFilter对象为空，那么重写创建一个
        if (filter == null) {
            filter = new MyFilter(list);
        }
        return filter;
    }

    /**
     * 创建内部类MyFilter继承Filter类，并重写相关方法，实现数据的过滤
     * @author 邹奇
     *
     */
    class MyFilter extends Filter {

        // 创建集合保存原始数据
        private List<XiaoquFangwuInfoBean> original = new ArrayList<>();

        public MyFilter(List<XiaoquFangwuInfoBean> list) {
            this.original = list;
        }

        /**
         * 该方法返回搜索过滤后的数据
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // 创建FilterResults对象
            FilterResults results = new FilterResults();

            /**
             * 没有搜索内容的话就还是给results赋值原始数据的值和大小
             * 执行了搜索的话，根据搜索的规则过滤即可，最后把过滤后的数据的值和大小赋值给results
             *
             */
            if(TextUtils.isEmpty(constraint)){
                results.values = original;
                results.count = original.size();
            }else {
                // 创建集合保存过滤后的数据
                List<XiaoquFangwuInfoBean> mList = new ArrayList<>();
                // 遍历原始数据集合，根据搜索的规则过滤数据
                    for (int i = 0;i<original.size();i++){
                    // 这里就是过滤规则的具体实现【规则有很多，大家可以自己决定怎么实现】
                    if(original.get(i).getRoomNo().trim().toLowerCase().contains(constraint.toString().trim().toLowerCase())){
                        // 规则匹配的话就往集合中添加该数据
                        mList.add(new XiaoquFangwuInfoBean(original.get(i).getBuildingId()
                                ,original.get(i).getCommunityId()
                                ,original.get(i).getHouseId()
                                ,original.get(i).getRoomNo()
                        ,original.get(i).getUnitId()));
                    }
                }
                results.values = mList;
                results.count = mList.size();
            }

            // 返回FilterResults对象
            return results;
        }

        /**
         * 该方法用来刷新用户界面，根据过滤后的数据重新展示列表
         */
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            // 获取过滤后的数据
            list = (List<XiaoquFangwuInfoBean>) results.values;
            // 如果接口对象不为空，那么调用接口中的方法获取过滤后的数据，具体的实现在new这个接口的时候重写的方法里执行
            List<String> list1 = new ArrayList<>();
            if(listener != null){
                for (int i=0;i<list.size();i++){
                    list1.add(list.get(i).getRoomNo());
                }
                listener.getFilterDatafangwu(list1,list);
            }
            // 刷新数据源显示
            notifyDataSetChanged();
        }

    }

    /**
     * 控件缓存类
     *
     * @author 邹奇
     *
     */
    class ViewHolder {
        TextView tv_ss;
    }
}