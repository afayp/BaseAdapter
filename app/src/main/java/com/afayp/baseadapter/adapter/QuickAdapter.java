package com.afayp.baseadapter.adapter;

import android.view.View;
import android.widget.Toast;

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
        holder.setText(R.id.tv,item);
//        TextView tv = holder.getView(R.id.tv);
//        tv.setText("修改后");

        holder.setOnClickListener(R.id.ll, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click "+item,Toast.LENGTH_SHORT).show();
            }
        });


    }
}
