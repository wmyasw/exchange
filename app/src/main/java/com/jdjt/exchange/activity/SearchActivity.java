package com.jdjt.exchange.activity;


import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jdjt.exchange.R;
import com.jdjt.exchange.util.SoftKeyboardUtil;
import com.jdjt.exchange.view.BottomPopupWindow;
import com.jdjt.exchange.view.TopPopupWindow;

public class SearchActivity extends BaseActivity {
//    private LinearLayout ll_bottom_content;// 底部菜单 内容容器
//    private RelativeLayout rl_bottom_view;// 底部菜单 内容容器

    @Override
    protected int initPageLayoutID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initPageView() {
        super.initPageView();
        initInputMethodManager();
    }

    private void initInputMethodManager(){
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//
//        SoftKeyboardUtil.observeSoftKeyboard(this, new SoftKeyboardUtil.OnSoftKeyboardChangeListener() {
//            @Override
//            public void onSoftKeyBoardChange(int softKeybardHeight, boolean visible) {
//                if(visible){
//                  if(isShowFindBtn){
//                      topShowAnimation();
//                  }
//
//                }else {
//                    Toast.makeText(SearchActivity.this ,"2222",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }
}
