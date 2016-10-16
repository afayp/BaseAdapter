package com.afayp.baseadapter.adapter;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface ItemViewDelegate<T> {

    boolean isForViewType(T item, int position);//当前数据是否属于这个type,每个ItemViewDelegate的实现类都有自己的标准

    int getItemViewLayoutId();//用什么布局

    void convert(BaseViewHolder holder, T t, int position);//每种布局数据的绑定方式可能不一样
}
