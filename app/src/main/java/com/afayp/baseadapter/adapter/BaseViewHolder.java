package com.afayp.baseadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.afayp.baseadapter.R;
import com.bumptech.glide.Glide;

/**
 * 所有ViewHolder的基类
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;
    private final Context context;
    private View convertView;//整个itemview

    protected BaseViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        this.convertView = view;
        this.views = new SparseArray<>();
    }

    public static BaseViewHolder createViewHolder(Context context, View itemView) {
        BaseViewHolder holder = new BaseViewHolder(context, itemView);
        return holder;
    }

    public static BaseViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        BaseViewHolder holder = new BaseViewHolder(context, itemView);
        return holder;
    }

    /**
     * 根据viewId 返回item中对应的view
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     *返回整个item view
     */
    public View getView(){
        return convertView;
    }

    //******以下为辅助方法******//

    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setImageUrl(int viewId, String imageUrl) {
        ImageView view = getView(viewId);
        Glide.with(context).load(imageUrl).placeholder(R.mipmap.ic_launcher).into(view);//加载图片的方式可以自己定义
        return this;
    }

    /**
     *
     * @param viewId
     * @param visible True for VISIBLE, false for GONE.
     * @return
     */
    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);//超链接
        return this;
    }

    public BaseViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    //******此处省略若干常用赋值常用方法,可以根据直接需要添加******//

    public BaseViewHolder setTag(int viewId, Object tag)
    {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public BaseViewHolder setTag(int viewId, int key, Object tag)
    {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public BaseViewHolder setChecked(int viewId, boolean checked)
    {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }


    //******以下为点击事件的添加******//
    //这里没有设置onItemClickListener,
    //因为convert(BaseViewHolder holder, T item)方法里已经可以拿到对应position的数据，感觉也没什么必要
    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }


}
