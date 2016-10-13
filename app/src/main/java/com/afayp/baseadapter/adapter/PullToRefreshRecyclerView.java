package com.afayp.baseadapter.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/9/24.
 */
public class PullToRefreshRecyclerView extends RecyclerView {
    private int lastX;
    private int lastY;
    private boolean isOnTouching;
    private boolean isRefresh;
    private View refreshView;

    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isOnTouching = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是否滑动到了头部
                if (!canScrollVertically(-1)) {
                    int dy = lastY - y;
                    int dx = lastX - x;

                    if (Math.abs(dy) > Math.abs(dx)) {
                        isRefresh = true;
                        changeHeight(dy);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isRefresh = false;
                isOnTouching = false;
//                if (mState == STATE_READY) {
//                    onStatusChange(STATE_REFRESHING);
//                }
//                autoSize();
                break;
        }

        lastX = x;
        lastY = y;

        return super.onTouchEvent(e);
    }

    private void changeHeight(int dy) {

    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }
}
