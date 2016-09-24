package com.afayp.baseadapter.sample1;

import android.content.Context;

import com.afayp.baseadapter.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class MultiItemSampleAdapter extends MultiItemCommonAdapter<Person> {


    public MultiItemSampleAdapter(Context context, List<Person> datas) {
        super(context, datas);
        addItemViewDelegate(new Type1Delegate());
        addItemViewDelegate(new Type2Delegate());
    }

    class Type1Delegate implements ItemViewDelegate<Person>{

        @Override
        public boolean isForViewType(Person item, int position) {
            return item.getType() == 1 ;
        }

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_type1;
        }

        @Override
        public void convert(BaseViewHolder holder, Person item, int position) {
            holder.setText(R.id.tv1,item.getName());

        }
    }

    class Type2Delegate implements ItemViewDelegate<Person>{

        @Override
        public boolean isForViewType(Person item, int position) {
            return item.getType() == 2;
        }

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_type2;
        }

        @Override
        public void convert(BaseViewHolder holder, Person item, int position) {
            holder.setText(R.id.tv2,item.getName() );

        }
    }
}
