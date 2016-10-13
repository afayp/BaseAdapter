package com.afayp.baseadapter.adapter.sample;

import android.content.Context;

import com.afayp.baseadapter.R;
import com.afayp.baseadapter.adapter.BaseViewHolder;
import com.afayp.baseadapter.adapter.ItemViewDelegate;
import com.afayp.baseadapter.adapter.MultiItemBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class MultiItemSampleAdapter extends MultiItemBaseAdapter<Model> {


    public MultiItemSampleAdapter(Context context, List<Model> datas) {
        super(context, datas);
        addItemViewDelegate(new Type1Delegate());
        addItemViewDelegate(new Type2Delegate());
    }

    class Type1Delegate implements ItemViewDelegate<Model> {

        @Override
        public boolean isForViewType(Model item, int position) {
            return item.getType() == 1 ;
        }

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_type1;
        }

        @Override
        public void convert(BaseViewHolder holder, Model item, int position) {
            holder.setText(R.id.tv1,item.getName());

        }
    }

    class Type2Delegate implements ItemViewDelegate<Model>{

        @Override
        public boolean isForViewType(Model item, int position) {
            return item.getType() == 2;
        }

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_type2;
        }

        @Override
        public void convert(BaseViewHolder holder, Model item, int position) {
            holder.setText(R.id.tv2,item.getName() );

        }
    }
}
