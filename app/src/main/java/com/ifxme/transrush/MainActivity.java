package com.ifxme.transrush;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private String url = "http://www.transrush.com/Transport/LogisticsTransferTrace.aspx?code=DD160318200157";
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ExpressAdapter adapter;
    private List<ExpressModel> expressModelList;
    private static final int PROCESS_SUCCEED_ONE = 1001;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.nav_bg, R.color.green, R.color.yellow, R.color.red);
        listView = (ListView) findViewById(R.id.listView);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initData() {
        expressModelList = new ArrayList<ExpressModel>();
        adapter = new ExpressAdapter(this, expressModelList);
        listView.setAdapter(adapter);
        getExpressList(PROCESS_SUCCEED_ONE);
    }


    /**
     * 获取快递列表
     *
     * @param tag
     */
    private void getExpressList(final int tag) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                List<ExpressModel> list = ExpressPraser.getInstance().prase(url);
                Message message = new Message();
                message.what = tag;
                message.obj = list;
                handler.sendMessage(message);
            }
        }).start();
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getExpressList(PROCESS_SUCCEED_ONE);
            }
        });
    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            swipeRefreshLayout.setRefreshing(false);
            if (msg.what == PROCESS_SUCCEED_ONE) {
                final List<ExpressModel> list = (List<ExpressModel>) msg.obj;
                expressModelList.clear();
                expressModelList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        }
    };



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_express, menu);
//        return true;
//
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_item_search) {
//
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


}



