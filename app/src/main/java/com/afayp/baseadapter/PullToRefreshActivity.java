package com.afayp.baseadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.afayp.baseadapter.sample1.MultiItemSampleAdapter;
import com.afayp.baseadapter.sample1.Person;
import com.afayp.baseadapter.sample1.wrapper.HeaderAndFooterWrapper;
import com.afayp.baseadapter.sample1.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * SwipeRefreshLayout结合RecyclerView实现
 */
public class PullToRefreshActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Person> data;
    private LoadMoreWrapper loadMoreWrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i%3 == 0){
                data.add(new Person("ABC",2));
            }else {
                data.add(new Person("ABC",1));
            }
        }

        MultiItemSampleAdapter multiItemSampleAdapter =  new MultiItemSampleAdapter(this, data);
        HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(multiItemSampleAdapter);
        View header = LayoutInflater.from(this).inflate(R.layout.item_header,null);
        View footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        headerAndFooterWrapper.addHeaderView(header);
        headerAndFooterWrapper.addFootView(footer);
//        loadMoreWrapper = new LoadMoreWrapper(headerAndFooterWrapper);
        loadMoreWrapper.setLoadMoreView(R.layout.item_load_more);
        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.add(new Person("New",1));
                        data.add(new Person("New",1));
                        loadMoreWrapper.notifyDataSetChanged();
                    }
                },2000);
            }
        });
        recyclerView.setAdapter(loadMoreWrapper);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        for (int i = 0; i < 10; i++) {
                            data.add(new Person("NEW",1));
                        }
                        loadMoreWrapper.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);

            }
        });

    }
}
