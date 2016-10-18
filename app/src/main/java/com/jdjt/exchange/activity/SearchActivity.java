package com.jdjt.exchange.activity;




import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.android.pc.util.Handler_System;
import com.jdjt.exchange.R;
import com.jdjt.exchange.adapter.FooterListAdapter;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends BaseActivity {


    ListView listView;
    FooterListAdapter adapter;
    List<Map<String,String>> list;
    CoordinatorLayout cl_search;
    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initPageView() {
        super.initPageView();

        cl_search= (CoordinatorLayout) findViewById(R.id.cl_search);


        cl_search.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                cl_search.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,screenHeight-getStatusBarHeight()));
            }
        });
        listView= (ListView) findViewById(R.id.listView);
        adapter=new FooterListAdapter(this);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_DISABLED);
        listView.setAdapter(adapter);
        initData();
        adapter.setDataSource(list);

    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    private void initData(){
        list=new ArrayList<>();
        for(int i=0;i<5;i++){
            Map map=new HashMap();
            list.add(map);
        }

    }
}
