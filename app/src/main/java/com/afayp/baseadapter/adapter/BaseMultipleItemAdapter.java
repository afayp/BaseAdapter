package com.afayp.baseadapter.adapter;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by afayp on 2016/8/26.
 */
public abstract class BaseMultipleItemAdapter<T> extends BaseQuickAdapter {

    private SparseArray<Integer> layouts;

    private static final int DEFAULT_VIEW_TYPE = -0xff;


    public BaseMultipleItemAdapter(List data) {
        super(data);
    }

    @Override
    public int getItemViewType(int position) {
        Object o = mData.get(position);

        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, getLayoutId(viewType));
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType);
    }


}
