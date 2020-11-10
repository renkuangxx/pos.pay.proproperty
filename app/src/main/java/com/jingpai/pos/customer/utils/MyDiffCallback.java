package com.jingpai.pos.customer.utils;

/**
 * 时间: 2020/3/7
 * 功能: 局部刷新数据
 */
public class MyDiffCallback {
    /*//Thing 是adapter 的数据类，要换成自己的adapter 数据类
    private List<CarQueryBean.DataBean> current;
    private List<CarQueryBean.DataBean> next;

    public MyDiffCallback(List<CarQueryBean.DataBean> current, List<CarQueryBean.DataBean> next) {
        this.current = current;
        this.next = next;
        Log.d("数据c", current.toString());
        Log.d("数据n", next.toString());
    }

    *//**
     * 旧数据的size
     *//*
    @Override
    public int getOldListSize() {
        return current.size();
    }

    *//**
     * 新数据的size
     *//*
    @Override
    public int getNewListSize() {
        return next.size();
    }

    *//**
     * 这个方法自由定制 ，
     * 在对比数据的时候会被调用
     * 返回 true 被判断为同一个item
     *//*
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        CarQueryBean.DataBean currentItem = current.get(oldItemPosition);
        CarQueryBean.DataBean nextItem = next.get(newItemPosition);

        return currentItem.getId() == nextItem.getId();
    }

    *//**
     *在上面的方法返回true 时，
     * 这个方法才会被diff 调用
     * 返回true 就证明内容相同
     *//*
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CarQueryBean.DataBean currentItem = current.get(oldItemPosition);
        CarQueryBean.DataBean nextItem = next.get(newItemPosition);
        return currentItem.equals(nextItem);
    }*/
}
