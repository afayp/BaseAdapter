package com.afayp.baseadapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public abstract class SingleItemBaseAdapter<T> extends MultiItemBaseAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;


    public SingleItemBaseAdapter(Context context, List<T> datas, final int layoutId) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        //默认添加一种 ItemViewDelegate
        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(BaseViewHolder holder, T t, int position)
            {
                SingleItemBaseAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(BaseViewHolder holder, T t, int position);
}
