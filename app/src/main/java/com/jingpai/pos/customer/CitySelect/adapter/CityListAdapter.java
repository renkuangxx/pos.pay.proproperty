package com.jingpai.pos.customer.CitySelect.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingpai.pos.customer.CitySelect.util.PinyinUtils;
import com.jingpai.pos.R;
import com.jingpai.pos.customer.activity.census.FilterListener;
import com.jingpai.pos.customer.bean.City;
import com.jingpai.pos.customer.bean.LocateState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 城市列表适配器
 */
public class CityListAdapter extends BaseAdapter implements Filterable {
    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<City> mCities;
    private HashMap<String, Integer> letterIndexes = new HashMap<>();;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;
    private String code;
    private List<City> mHotData = new ArrayList<>();
    List<String> list = new ArrayList<>();
    private MyFilter filter = null;// 创建MyFilter对象
    private FilterListener listener = null;// 接口对象

    public CityListAdapter(List<String> list, Context context, FilterListener filterListener) {
        this.list = list;
        this.mContext = context;
        this.listener = filterListener;
    }

    public void setData(List<City> mCities) {
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null) {
            mCities = new ArrayList<>();
        }
        mCities.add(0, new City(-1, "定位", "0"));
//        mCities.add(1, new City(-1, "热门", "1"));
        int size = mCities.size();
        sections = new String[size];

        for (int index = 0; index < size; index++) {
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getPinyin());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getPinyin()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
            //筛选出热门
//            if (mCities.get(index).isHot())
//                mHotData.add(mCities.get(index));
        }
        notifyDataSetChanged();
    }

    public CityListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 更新定位状态
     *
     * @param state
     */
    public void updateLocateState(int state, String city,String code) {
        this.locateState = state;
        this.locatedCity = city;
        this.code = code;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public City getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:     //定位
                view = inflater.inflate(R.layout.cp_view_locate_city, parent, false);
                ViewGroup container = view.findViewById(R.id.layout_locate);
                TextView state = view.findViewById(R.id.tv_located_city);
                ImageView iv_refresh = view.findViewById(R.id.iv_refresh);
                switch (locateState) {
                    case LocateState.LOCATING:
                        state.setText("正在定位…");
                        break;
                    case LocateState.FAILED:
                        state.setText("定位失败");
                        break;
                    case LocateState.SUCCESS:
                        state.setText(locatedCity);
                        break;
                    case LocateState.INIT:
                        state.setText("定位");
                        break;
                }
                iv_refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (locateState == LocateState.FAILED) {
                        //重新定位

                        if (onCityClickListener != null) {
                            state.setText("正在定位…");
                            onCityClickListener.onLocateClick();
                        } else if (locateState == LocateState.SUCCESS) {
                            //返回定位城市
                            if (onCityClickListener != null) {
                                onCityClickListener.onCityClick(locatedCity,code);
                            }
                        }
                    }
                });
                state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (state.getText()=="定位失败")return;
                        onCityClickListener.onCityClick(locatedCity,code);
                    }
                });

                break;
            case 1:     //热门
//                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
//
//                WrapHeightGridView gridView = view.findViewById(R.id.gridview_hot_city);
//                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext);
//                gridView.setAdapter(hotCityGridAdapter);
//                hotCityGridAdapter.setData(mHotData);
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        if (onCityClickListener != null) {
//                            onCityClickListener.onCityClick(hotCityGridAdapter.getItem(position).getName());
//                        }
//                    }
//                });
//                break;
            case 2:     //所有
                if (view == null) {
                    view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    holder.v_line = view.findViewById(R.id.v_line);
                    view.setTag(holder);
                } else {
                    holder = (CityViewHolder) view.getTag();
                }
                if (position >= 1) {
                    final String city = mCities.get(position).getName();
                    final String code = mCities.get(position).getId()+"";
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPinyin());
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getPinyin()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)) {
                        holder.letter.setVisibility(View.GONE);
                        holder.v_line.setVisibility(View.GONE);
                        holder.letter.setText(currentLetter);
                    } else if (TextUtils.equals(mCities.get(position).getName(),"阿克苏") && currentLetter.equals("A")) {
                        holder.letter.setVisibility(View.GONE);
                        holder.letter.setText(currentLetter);
                        holder.v_line.setVisibility(View.GONE);
                    } else {
                        holder.letter.setVisibility(View.GONE);
                        holder.v_line.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null ) {
                                onCityClickListener.onCityClick(city,code);
                            }
                        }
                    });
                }
                break;
        }
        return view;
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
     *
     * @author 邹奇
     */
    class MyFilter extends Filter {

        // 创建集合保存原始数据
        private List<String> original = new ArrayList<String>();

        public MyFilter(List<String> list) {
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
            if (TextUtils.isEmpty(constraint)) {
                results.values = original;
                results.count = original.size();
            } else {
                // 创建集合保存过滤后的数据
                List<String> mList = new ArrayList<String>();
                // 遍历原始数据集合，根据搜索的规则过滤数据
                for (String s : original) {
                    // 这里就是过滤规则的具体实现【规则有很多，大家可以自己决定怎么实现】
                    if (s.trim().toLowerCase().contains(constraint.toString().trim().toLowerCase())) {
                        // 规则匹配的话就往集合中添加该数据
                        mList.add(s);
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
            list = (List<String>) results.values;
            // 如果接口对象不为空，那么调用接口中的方法获取过滤后的数据，具体的实现在new这个接口的时候重写的方法里执行
            if (listener != null) {
                listener.getFilterData(list);
            }
            // 刷新数据源显示
            notifyDataSetChanged();
        }

    }

    public static class CityViewHolder {
        TextView letter;
        TextView name;
        View v_line;
    }

    public void setOnCityClickListener(OnCityClickListener listener) {
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener {
        void onCityClick(String name,String code);

        void onLocateClick();
    }
}
