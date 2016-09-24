package com.afayp.baseadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afayp.baseadapter.sample1.BaseViewHolder;
import com.afayp.baseadapter.sample1.CommonAdapter;
import com.afayp.baseadapter.sample1.MultiItemCommonAdapter;
import com.afayp.baseadapter.sample1.MultiItemSampleAdapter;
import com.afayp.baseadapter.sample1.Person;
import com.afayp.baseadapter.sample1.wrapper.EmptyWrapper;
import com.afayp.baseadapter.sample1.wrapper.HeaderAndFooterWrapper;
import com.afayp.baseadapter.sample1.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Person> data;
    private LinearLayoutManager linearLayoutManager;

    private CommonAdapter<Person> singleItemAdapter;
    private MultiItemSampleAdapter multiItemAdapter;
    private LoadMoreWrapper loadMoreAdapter;
    private HeaderAndFooterWrapper headerAndFooterAdapter;
    private EmptyWrapper emptyAdapter;

    private RecyclerView.Adapter currentAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        for (int i = 0; i < 20; i++) {
                            data.add(new Person("NEW" +Math.random() *10,1));
                        }
                        currentAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);

            }
        });
        singleItem();
    }

    private void singleItem() {
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(new Person("SingleType",1));
        }
        singleItemAdapter = new CommonAdapter<Person>(this, data, R.layout.item_type1) {
            @Override
            protected void convert(BaseViewHolder holder, Person person, int position) {
                holder.setText(R.id.tv1, person.getName());
            }
        };
        singleItemAdapter.setOnItemClickListener(new MultiItemCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                toast("click "+position);
            }

            @Override
            public boolean onItemLongClick(View view, BaseViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(singleItemAdapter);

    }

    private void multiItem(){
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i%3 == 0){
                data.add(new Person("TYPE2",2));
            }else {
                data.add(new Person("TYPE1",1));
            }
        }
        multiItemAdapter = new MultiItemSampleAdapter(this, data);
        View header = LayoutInflater.from(this).inflate(R.layout.item_header,null);
        View footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        multiItemAdapter.addHeaderView(header);
        multiItemAdapter.addFootView(footer);
        multiItemAdapter.setOnItemClickListener(new MultiItemCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                toast("click "+data.get(position).getName() +" "+position);
            }

            @Override
            public boolean onItemLongClick(View view, BaseViewHolder holder, int position) {
                return false;
            }
        });

        recyclerView.setAdapter(multiItemAdapter);
    }

    public void headerAndFooter(){
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(new Person("SingleType",1));
        }
        headerAndFooterAdapter = new HeaderAndFooterWrapper(singleItemAdapter);
        View header = LayoutInflater.from(this).inflate(R.layout.item_header,null);
        View footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        headerAndFooterAdapter.addHeaderView(header);
        headerAndFooterAdapter.addFootView(footer);
        recyclerView.setAdapter(headerAndFooterAdapter);
        headerAndFooterAdapter.notifyDataSetChanged();
    }

    public void emptyView(){
        data = new ArrayList<>();
        MultiItemSampleAdapter adapter = new MultiItemSampleAdapter(this, data);
        View header = LayoutInflater.from(this).inflate(R.layout.item_header,null);
        View footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);
        adapter.setEmptyView(R.layout.item_empty);
        adapter.addHeaderView(header);
        adapter.addFootView(footer);

        recyclerView.setAdapter(adapter);
    }

    private void loadMore() {
        loadMoreAdapter = new LoadMoreWrapper(singleItemAdapter);
        loadMoreAdapter.setLoadMoreView(R.layout.item_load_more);
        loadMoreAdapter.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.add(new Person("New",1));
                        data.add(new Person("New",1));
                        loadMoreAdapter.notifyDataSetChanged();
                    }
                },2000);

            }
        });
        recyclerView.setAdapter(loadMoreAdapter);
    }

    private void mix() {
        data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i%3 == 0){
                data.add(new Person("TYPE2",2));
            }else {
                data.add(new Person("TYPE1",1));
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_single_item:
                singleItem();
                currentAdapter = singleItemAdapter;
                break;
            case R.id.menu_multi_item:
                multiItem();
                currentAdapter = multiItemAdapter;
                break;
            case R.id.menu_haf_item:
                headerAndFooter();
                currentAdapter = headerAndFooterAdapter;
                break;
            case R.id.menu_more_item:
                currentAdapter = loadMoreAdapter;
                loadMore();
                break;
            case R.id.menu_empty_item:
                currentAdapter = emptyAdapter;
                emptyView();
                break;
            case R.id.menu_mix_item:
                mix();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
