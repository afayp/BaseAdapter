package com.afayp.baseadapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 一种布局
 */
public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected Context mContext;
    protected List<T> mData;//数据源
    protected LayoutInflater mLayoutInflater;
    private int layoutResId;//item布局resId
    private int mDuration = 300;//默认动画时间
    private boolean isOpenAnimation = true;//是否开启动画
    private LinearLayout mHeaderLayout;//所有的headerview放在这里
    private LinearLayout mFooterLayout;//所有的footerview放在这里
    private LinearLayout mEmptyView;//空view
    private int mLastPosition = -1;
    private boolean mIsLoading = false;//是否正在加载 mLoadingMoreEnable

    public static final int HEADER_VIEW = 0x00000111;
    public static final int LOADING_VIEW = 0x00000222;
    public static final int FOOTER_VIEW = 0x00000333;
    public static final int EMPTY_VIEW = 0x00000555;

    private LoadMoreListener mLoadMoreListener;
    private View mLoadingView;//正在加载的view

    private void setLoadMorelistener(LoadMoreListener loadMorelistener){
        this.mLoadMoreListener  = loadMorelistener;
    }

    public interface LoadMoreListener{
        void onLoadMore();
    }

    public BaseQuickAdapter(List<T> data, int layoutResId) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0){
            this.layoutResId = layoutResId;
        }
    }

    public BaseQuickAdapter(List<T> data) {
        this(data,0);
    }

    /** 傻傻分不清。。
     *  notifyItemInserted();
        notifyItemRangeInserted();

        notifyItemRemoved();
        notifyItemRangeRemoved();

        notifyItemRangeChanged();
        notifyDataSetChanged();
        notifyAll();

        或者直接 notifyItemRangeChanged(0, mData.size());//全部重新bind一遍
     * @param position
     */
    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position +getHeaderLayoutCount());
        notifyItemRangeChanged(position + getHeaderLayoutCount(),mData.size() +getHeaderLayoutCount() - position);//？
    }
    public void add(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
        notifyItemRangeChanged(position +1 +getHeaderLayoutCount(),mData.size() +getHeaderLayoutCount() - position);
    }

    //加到指定位置
    public void addData(int position , List<T> data){
        this.mData.addAll(data);
        notifyItemRangeInserted(position + getHeaderLayoutCount(),mData.size() +getHeaderLayoutCount() - position);
        notifyItemRangeChanged(position +getHeaderLayoutCount(),mData.size() +getHeaderLayoutCount() - position);
    }

    //加到最后面
    public void addData(List<T> newData){
        this.mData.addAll(newData);
        notifyItemRangeChanged(mData.size() - newData.size() + getHeaderLayoutCount(),newData.size());
    }

    //重新设置数据
    public void setNewData(List<T> data){
        this.mData = data;
        mLastPosition = -1;
        notifyDataSetChanged();
    }

    public boolean isLoading(){
        return mIsLoading;
    }

    public void setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
    }

    public List<T> getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public int getmEmptyViewCount() {
        return mEmptyView == null ? 0 : 1;
    }

    public int getHeaderLayoutCount() {
        return mHeaderLayout == null ? 0 : 1;
    }

    public int getFooterLayoutCount() {
        return mFooterLayout == null ? 0 : 1;
    }

    public int getEmptyViewCount() {
        return mEmptyView == null ? 0 : 1;
    }

    private boolean isLoadMore() {
//        return mNextLoadEnable && pageSize != -1 && mRequestLoadMoreListener != null && mData.size() >= pageSize;
        return true;
    }



    @Override
    public int getItemCount() {
        int i = isLoadMore() ? 1: 0;
        int count = mData.size() +i + getHeaderLayoutCount() + getFooterLayoutCount();//加上header和footer的数量
        // TODO: 2016/9/20
        return count;
    }



    @Override
    public int getItemViewType(int position) {
        if (mHeaderLayout != null && position == 0){
            return HEADER_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        BaseViewHolder holder = null;
        switch (viewType){
            case HEADER_VIEW:
                holder = BaseViewHolder.createViewHolder(mContext,mHeaderLayout);
                break;
            case FOOTER_VIEW:
                holder = BaseViewHolder.createViewHolder(mContext,mFooterLayout);
                break;
            default:
                holder = BaseViewHolder.createViewHolder(mContext,parent,layoutResId);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int type = holder.getItemViewType();
        int realPos = holder.getLayoutPosition();
        switch (type){
            case HEADER_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            default:
                convert(holder, mData.get(realPos - getHeaderLayoutCount()),realPos);
                break;
        }
    }

    protected abstract void convert(BaseViewHolder holder, T item,int position);






    //**********头部、尾部、空view***********//

    public void addHeaderView(View header) {
        addHeaderView(header, -1);
    }

    /**
     * hearView整个是个垂直方向的LinearLayout,你可以往里面加多个子view，并且指定位置
     * 所以添加前要判断指定的index是否超出现有的childCount，做一些处理
     * @param header
     * @param index
     */
    private void addHeaderView(View header, int index) {


        if (mHeaderLayout == null) {
//            if (mCopyHeaderLayout == null) {
                mHeaderLayout = new LinearLayout(header.getContext());
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                mCopyHeaderLayout = mHeaderLayout;
//            } else {
//                mHeaderLayout = mCopyHeaderLayout;
//            }
        }

        index = index >= mHeaderLayout.getChildCount() ? -1 : index;
        mHeaderLayout.addView(header, index);
        this.notifyDataSetChanged();
    }

    public void addFooterView(View footer) {
        addFooterView(footer, -1);
    }

    private void addFooterView(View footer, int index) {
        if (mFooterLayout == null){
            mFooterLayout = new LinearLayout(footer.getContext());
            mFooterLayout.setOrientation(LinearLayout.VERTICAL);
            mFooterLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        index = index >= mFooterLayout.getChildCount() ? -1 : index;
        mFooterLayout.addView(footer, index);
        this.notifyDataSetChanged();
    }

    /**
     * 删除指定的headerview
     * @param header
     */
    public void removeHeaderView(View header) {
        if (mHeaderLayout == null) return;

        mHeaderLayout.removeView(header);
        if (mHeaderLayout.getChildCount() == 0) {
            mHeaderLayout = null;
        }
        this.notifyDataSetChanged();
    }

    /**
     * 删除指定的footerview
     * @param footer
     */
    public void removeFooterView(View footer) {
        if (mFooterLayout == null) return;

        mFooterLayout.removeView(footer);
        if (mFooterLayout.getChildCount() == 0) {
            mFooterLayout = null;
        }
        this.notifyDataSetChanged();
    }

    /**
     * 删除所有headerview
     */
    public void removeAllHeaderView() {
        if (mHeaderLayout == null) return;
        mHeaderLayout.removeAllViews();
        mHeaderLayout = null;
    }

    /**
     * 删除所有footerview
     */
    public void removeAllFooterView() {
        if (mFooterLayout == null) return;
        mFooterLayout.removeAllViews();
        mFooterLayout = null;
    }

    public View getHeaderView(){
        return mHeaderLayout;
    }
    public View getFooterView(){
        return mFooterLayout;
    }
    public View getEmptyView() {
        return mEmptyView;
    }





}
