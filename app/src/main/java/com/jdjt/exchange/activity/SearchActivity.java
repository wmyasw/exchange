package com.jdjt.exchange.activity;


import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jdjt.exchange.R;
import com.jdjt.exchange.view.BottomPopupWindow;
import com.jdjt.exchange.view.TopPopupWindow;

public class SearchActivity extends BaseActivity {


    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initPageView() {

        LinearLayout fab = (LinearLayout) findViewById(R.id.ll_btn_view);
        LinearLayout search = (LinearLayout) findViewById(R.id.ll_search_view);
        final LinearLayout layout_top= (LinearLayout) findViewById(R.id.layout_top);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                layout_top.setVisibility(View.GONE);
                TopPopupWindow
                        resPop = new TopPopupWindow(SearchActivity.this);
                resPop.showAtLocation(view, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的
                resPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                        layout_top.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                BottomPopupWindow
                        resPop = new BottomPopupWindow(SearchActivity.this);
                resPop.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的

            }
        });
    }
}
