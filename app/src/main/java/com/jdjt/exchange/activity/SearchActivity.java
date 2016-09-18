package com.jdjt.exchange.activity;




import android.widget.ListView;

import com.jdjt.exchange.R;
import com.jdjt.exchange.adapter.FooterListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseActivity {


    ListView listView;
    FooterListAdapter adapter;
    List<Map<String,String>> list;
    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initPageView() {
        super.initPageView();
        listView= (ListView) findViewById(R.id.listView);
        adapter=new FooterListAdapter(this);
        listView.setAdapter(adapter);
        initData();
        adapter.setDataSource(list);
    }
    private void initData(){
        list=new ArrayList<>();
        for(int i=0;i<5;i++){
            Map map=new HashMap();
            list.add(map);
        }

    }
}
