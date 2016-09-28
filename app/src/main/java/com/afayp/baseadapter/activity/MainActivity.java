package com.afayp.baseadapter.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afayp.baseadapter.R;
import com.afayp.baseadapter.sample1.BaseViewHolder;
import com.afayp.baseadapter.sample1.SingleItemBaseAdapter;
import com.afayp.baseadapter.sample1.Model;
import com.afayp.baseadapter.sample1.MultiItemBaseAdapter;
import com.afayp.baseadapter.sample1.MultiItemSampleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SingleItemBaseAdapter<Model> singleItemAdapter;
    private MultiItemSampleAdapter multiItemAdapter;
    private MultiItemBaseAdapter currentAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Model> singleData;
    private List<Model> multiData;
    private List<Model> emptyData;
    private SingleItemBaseAdapter<Model> emptyAdapter;
    private Toolbar toolbar;
    private Button btn_add;
    private Button btn_remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_remove = (Button) findViewById(R.id.btn_remove);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Math.random() > 0.5){
                    currentAdapter.add(0,new Model("Add",1));
                }else {
                    currentAdapter.add(0,new Model("Add",2));
                }
            }
        });
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentAdapter.remove(0);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<Model> tempList = new ArrayList<Model>();
                        tempList.add(0,new Model("Add",1));
                        tempList.add(0,new Model("Add",2));
                        currentAdapter.addData(0,tempList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);

            }
        });
        initData();
        initAdapter();
        singleItem();
    }
    private void initData() {
        singleData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            singleData.add(new Model("Type One",1));
        }

        multiData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i%2 == 0){
                multiData.add(new Model("Type One " + i,1));
            }else {
                multiData.add(new Model("TYPE Two "+i ,2));
            }
        }

        emptyData = new ArrayList<>();
    }

    private void initAdapter() {
        View header = LayoutInflater.from(this).inflate(R.layout.item_header,null);
        View footer = LayoutInflater.from(this).inflate(R.layout.item_footer, null);

        singleItemAdapter = new SingleItemBaseAdapter<Model>(this, singleData, R.layout.item_type1) {
            @Override
            protected void convert(BaseViewHolder holder, Model model, int position) {
                holder.setText(R.id.tv1, model.getName());
            }
        };
        singleItemAdapter.addHeaderView(header);
        singleItemAdapter.addFootView(footer);
        singleItemAdapter.setLoadMoreView(R.layout.item_load_more);
        singleItemAdapter.setloadMoreEnable(true);
        singleItemAdapter.setOnLoadMoreListener(new MultiItemBaseAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        singleData.add(new Model("More",1));
                        singleData.add(new Model("More",1));
                        singleItemAdapter.notifyDataSetChanged();
                    }
                },1000);
            }
        });
        singleItemAdapter.setOnItemClickListener(new MultiItemBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                toast("click "+singleData.get(position).getName() +" position: "+position);

            }

            @Override
            public boolean onItemLongClick(View view, BaseViewHolder holder, int position) {
                return false;
            }
        });

        multiItemAdapter = new MultiItemSampleAdapter(this, multiData);
        multiItemAdapter.addHeaderView(header);
        multiItemAdapter.addFootView(footer);
        multiItemAdapter.setLoadMoreView(R.layout.item_load_more);
        multiItemAdapter.setloadMoreEnable(true);
        multiItemAdapter.setOnLoadMoreListener(new MultiItemBaseAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        multiData.add(new Model("More",1));
                        multiData.add(new Model("More",2));
                        multiItemAdapter.notifyDataSetChanged();
                        multiItemAdapter.setloadMoreEnable(false);
                    }
                },1000);
            }
        });
        multiItemAdapter.setOnItemClickListener(new MultiItemBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseViewHolder holder, int position) {
                toast("click "+multiData.get(position).getName() +" position: "+position);
            }

            @Override
            public boolean onItemLongClick(View view, BaseViewHolder holder, int position) {
                return false;
            }
        });

        emptyAdapter = new SingleItemBaseAdapter<Model>(this, emptyData, R.layout.item_type1) {
            @Override
            protected void convert(BaseViewHolder holder, Model model, int position) {
                holder.setText(R.id.tv1, model.getName());
            }
        };
        emptyAdapter.addHeaderView(header);
        emptyAdapter.addFootView(footer);
        emptyAdapter.setEmptyView(R.layout.item_empty);
    }

    private void singleItem() {
        currentAdapter = singleItemAdapter;
        recyclerView.setAdapter(singleItemAdapter);
    }

    private void multiItem(){
        currentAdapter = multiItemAdapter;
        recyclerView.setAdapter(multiItemAdapter);
    }

    public void emptyView(){
        currentAdapter = emptyAdapter;
        recyclerView.setAdapter(emptyAdapter);
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
                break;
            case R.id.menu_multi_item:
                multiItem();
                break;
            case R.id.menu_empty_item:
                emptyView();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}
