package com.jdjt.exchange.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.jdjt.exchange.R;

/**
 * @author wmy
 * @Description: 基础activity，控制派生类状态
 * @version:1.0
 * @FileName:BaseActivity
 * @Package com.jdjt.exchange.activity
 * @Date 2016/8/31 16:44
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "title";
    private Toolbar mActionBarToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initPageLayoutID() != 0)
            setContentView(initPageLayoutID());//初始化页面

        init(savedInstanceState);
    }

    /**
     * 初始化
     * @param savedInstanceState
     */
    private void init(Bundle savedInstanceState) {
        initActionBar();//初始化标题栏
        initTitle();
        initPageView();//初始化页面view
        initPageViewListener();
        process(savedInstanceState);
    }

    /**
     * 返回主布局id
     */
    protected abstract int initPageLayoutID();

    /**
     * 设置标题
     */
    protected void initTitle() {

    }

    /**
     * 初始化页面控件
     */
    protected void initPageView() {
    }

    /**
     * 页面控件点击事件处理
     */
    protected void initPageViewListener() {
    }

    /**
     * 逻辑处理
     */
    protected void process(Bundle savedInstanceState) {
    }

    /**
     * 初始化标题栏
     */
    protected void initActionBar() {
        if (getActionBarToolbar() == null) {
            return;
        }
        mActionBarToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (mActionBarToolbar != null && !TextUtils.isEmpty(title) && getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * 获取Toolbar
     * @return
     */
    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

}
