package com.afayp.baseadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afayp.baseadapter.R;

import java.util.List;

/**
 * Created by afayp on 2016/8/26.
 */
public class DefAdpater extends RecyclerView.Adapter<DefAdpater.MyViewHolder>{

    private Context mContext;
    private List<String> mData;

    public DefAdpater(Context context, List<String> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
