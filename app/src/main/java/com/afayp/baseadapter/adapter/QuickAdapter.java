package com.afayp.baseadapter.adapter;

import com.afayp.baseadapter.R;

import java.util.List;

/**
 * Created by afayp on 2016/8/26.
 */
public class QuickAdapter extends BaseQuickAdapter<String> {


    public QuickAdapter(List<String> data, int layoutResId) {
        super(data, layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, final String item,int position) {
        holder.setText(R.id.tv1,item);
//        TextView tv = holder.getView(R.id.tv);
//        tv.setText("修改后");



    }
}
