package com.jdjt.exchange.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.app.Ioc;
import com.android.pc.util.Handler_System;
import com.android.pc.util.Handler_Ui;
import com.jdjt.exchange.R;
import com.jdjt.exchange.adapter.TagsAdapter;
import com.jdjt.exchange.util.BitmapUtils;
import com.jdjt.exchange.view.FlowLayout;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wmy
 * @Description: 基础activity，控制派生类状态
 * @version:1.0
 * @FileName:BaseActivity
 * @Package com.jdjt.exchange.activity
 * @Date 2016/8/31 16:44
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_TITLE = "title";
    private Toolbar mActionBarToolbar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (initPageLayoutID() != 0)
            setContentView(initPageLayoutID());//初始化页面
        context = this;
        init(savedInstanceState);
    }

    /**
     * 初始化
     *
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

    protected LinearLayout ll_bottom_content;// 底部菜单 内容容器
    protected RelativeLayout rl_bottom_bar;  //底部标签点击部分
    protected int screenHeight = 0;        // 屏幕高度
    protected int screenWidth = 0;        // 屏幕宽度
    protected LinearLayout ll_btn_view;   //底部标签点击部分
    protected ViewGroup.LayoutParams bottomlayoutParams;
    protected ViewGroup.LayoutParams toplayoutParams;
    protected LinearLayout ll_top_content;// 顶菜单 内容容器
    protected RelativeLayout rl_top_bar;  //顶部标签点击部分
    protected LinearLayout ll_search_view;   //顶部标签点击部分
    protected boolean userInfoShow = false;
    protected boolean isShowFindBtn = false;
    protected  TableLayout tbl;

    /**
     * 初始化页面控件
     */
    protected void initPageView() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        /**底部菜单布局声明**/
        rl_bottom_bar = (RelativeLayout) findViewById(R.id.rl_bottom_bar);
        ll_btn_view = (LinearLayout) findViewById(R.id.ll_btn_view);
        //底部标签点击部分
        ll_bottom_content = (LinearLayout) findViewById(R.id.ll_bottom_content);
        bottomlayoutParams = (ViewGroup.LayoutParams) ll_bottom_content.getLayoutParams();
        ll_bottom_content.setLayoutParams(bottomlayoutParams);
        ll_btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userInfoShow) { //判断当前底部惨淡是否显示，
                    bottomShowAnimation();
                } else {
                    bottomHidAnimation();
                }
            }
        });
        /**顶部菜单布局声明**/
        rl_top_bar = (RelativeLayout) findViewById(R.id.rl_top_bar);
        ll_search_view = (LinearLayout) findViewById(R.id.ll_search_view);
        ll_top_content = (LinearLayout) findViewById(R.id.ll_top_content);
        if (rl_top_bar != null) {
            toplayoutParams = (ViewGroup.LayoutParams) ll_top_content.getLayoutParams();
            ll_top_content.setLayoutParams(toplayoutParams);
            ll_search_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isShowFindBtn) { //判断当前顶部菜单是否拉伸
                        if (userInfoShow) { //判断底部菜单是否显示，显示的话进行隐藏操作
                            bottomHidAnimation();
                        }
                        topShowAnimation();
                    } else {
                        topHidAnimation();
                    }
                }
            });
            initTableLayout();
        }
        ViewTreeObserver();

    }


    Bitmap bitmap;//毛玻璃背景
    /**
     * 重新初始化 控件高度
     */
    private void ViewTreeObserver() {

        ll_btn_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                 userInfoShow = false;

                bottomlayoutParams.height = screenHeight / 5 * 3 ;
                //初始化页移动 底部标签 点击部分
                ViewHelper.setTranslationY(rl_bottom_bar, ll_bottom_content.getHeight()- rl_bottom_bar.getHeight());
                //初始化页移动 底部标签 内容部分
                ViewHelper.setTranslationY(ll_bottom_content, ll_bottom_content.getHeight()- rl_bottom_bar.getHeight());
            }
        });
        if (ll_search_view != null) {
            ll_top_content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //判断隐藏软键盘是否弹出
                    if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
                    // 因为 在软键盘弹出后 会重新处理布局文件后会影响当前设置，所以在这里就直接 返回，不做布局调整
                        return;
                    }
                    isShowFindBtn = false;
                    toplayoutParams.height = screenHeight;
                    //初始化页移动 顶部标签 点击部分
                    ViewHelper.setTranslationY(rl_top_bar, -ll_top_content.getHeight()+rl_top_bar.getHeight());
                    //初始化页移动 顶部标签 内容部分
                    ViewHelper.setTranslationY(ll_top_content, -ll_top_content.getHeight()+rl_top_bar.getHeight());

                }
            });


        }
    }

    /**
     * 初始化标签组
     */
    private void initTableLayout() {
         tbl = (TableLayout) findViewById(R.id.tbl_tag);

        TableRow.LayoutParams talbeRowLayoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < 8; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(talbeRowLayoutParams);
            tableRow.setGravity(Gravity.CENTER);
            //getResources().getColor(Co)
            tableRow.setBackgroundColor(getResources().getColor(R.color.half_white_25));//背景设置色为黑色
            for (int j = 0; j < 13; j++) {
                String content;
                if (i == 0) {
                    content = i + "标题" + j;
                } else {
                    content = i + "内容" + j;
                }
                tableRow.addView(getTextView(content, i, j));
            }
            tbl.addView(tableRow);
        }
    }


    /**
     * 标签组 展示view
     * @param content
     * @param row
     * @param col
     * @return
     */
    private TextView getTextView(String content, int row, int col) {
        TextView text = new TextView(this);
        text.setText(content);
        text.setTextSize(14);
        text.setPadding(20,20, 20, 20);
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(getContentLayoutParams(row, col));
        text.setTextColor(getResources().getColor(R.color.white));
        //设置背景色为白色，通过Margin来凸显黑色背景来实现边框
        if (row == 0) {
            text.setBackgroundColor(getResources().getColor(R.color.btn_shape1));
        } else {
            text.setBackgroundColor(getResources().getColor(R.color.half_brown));
            text.setOnClickListener(this);
//            text.setTag(0,col);
        }
        return text;
    }

    /**
     * 处理tab 行列显示
     * @param row
     * @param col
     * @return
     */
    private TableRow.LayoutParams getContentLayoutParams(int row, int col) {
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        //为保证边框宽度均匀，唯有第一行需要topMargin，第一列需要leftMargin
        params.setMargins(col == 0 ? 1 : 0, row == 0 ? 1 : 0, 1, 1);

        return params;
    }

    /**
     * 顶部动画
     */
    public void topShowAnimation() {
        isShowFindBtn = true;

        showHideAnimation(rl_top_bar, ll_top_content, false,ll_top_content.getHeight()-rl_top_bar.getHeight());

    }

    /**
     * 顶部动画
     */
    private void topHidAnimation() {
        isShowFindBtn = false;
        Ioc.getIoc().getLogger().i("asdadddddddd"+(rl_top_bar.getHeight()-ll_top_content.getHeight()));
        showHideAnimation(rl_top_bar, ll_top_content, true,rl_top_bar.getHeight()-ll_top_content.getHeight());

    }

    /**
     * 底部隐藏动画
     */
    private void bottomHidAnimation() {
        userInfoShow = false;
        showHideAnimation(rl_bottom_bar, ll_bottom_content, userInfoShow,ll_bottom_content.getHeight()- rl_bottom_bar.getHeight());

    }
    /**
     * 底部显示动画
     */
    private void bottomShowAnimation() {
        userInfoShow = true;
        showHideAnimation(rl_bottom_bar, ll_bottom_content, userInfoShow,rl_bottom_bar.getHeight()-ll_bottom_content.getHeight());

    }

    /**
     * 位移动画
     *
     * @param title   需要跟随的标题view
     * @param content 执行位移 计算高度的 内容控件
     * @param isShow  判断向上位移 和向下位移， 为false 向上位移，为true 向下位移
     */
    private void showHideAnimation(final View title, final View content, boolean isShow,int translation) {
        if(translation==0){
            translation = isShow ? -content.getHeight() : content.getHeight();
        }
        final int finalTranslation = translation;
        ViewPropertyAnimator.animate(content).translationYBy(finalTranslation).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                ViewPropertyAnimator.animate(title).translationYBy(finalTranslation).setDuration(500)
                        .start();
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).setDuration(500).start();
    }


    /**
     * 页面控件点击事件处理
     */
    protected void initPageViewListener() {

    }

   List<String> tgs;
    public void onClick(View v) {

        if(!"isCheck".equals(v.getTag())) {
            v.setTag("isCheck");
            v.setBackgroundColor(getResources().getColor(R.color.btn_shape1));
            Map<String, String> tag=new HashMap<>();
            tag.put("title",((TextView)v).getText()+"");
            tag.put("code","123123");
            initTags(setTageData(tag));

        }else {
            Map<String, Object> tag=new HashMap<>();
            tag.put("title",((TextView)v).getText());
            tag.put("code","123123");
            removeTag(tag);
            v.setTag("");
            v.setBackgroundColor(getResources().getColor(R.color.half_brown));
        }

    }
    List<Map<String, String>> tagData;
    private  List<Map<String, String >> setTageData( Map<String, String> tag){
        if(tagData==null)
            tagData=new ArrayList<>();
//        Map<String, Object> tag=new HashMap<>();
//        tag.put("title","标签一");
//        tag.put("code","123123");
//        tagData.add(tag);
//        tag=new HashMap<>();
//        tag.put("title","标签er");
//        tag.put("code","123123");
//        tagData.add(tag);
//        tag=new HashMap<>();
//        tag.put("title","标签sa");
//        tag.put("code","123123");
//        tagData.add(tag);
//        tag=new HashMap<>();
//        tag.put("title","标签si");
//        tag.put("code","123123");
        tagData.add(tag);
        return tagData;
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
     *
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


    /**
     * 设置标签组
     *
     * @param getData
     */
    private void initTags(List<Map<String, String>> getData) {
        //新建适配器
        String[] from = {"title"};
        int[] to = {R.id.tags_name};
        FlowLayout gl_tags = (FlowLayout) findViewById(R.id.gl_tags);
        gl_tags.setHorizontalSpacing(1);
        gl_tags.setVerticalSpacing(1);
        TagsAdapter tagsAdapter=  new TagsAdapter(this);
        tagsAdapter.setDataSource(tagData);
        gl_tags.setAdapter(tagsAdapter);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(4, 4, 4, 4);
//        for (int i = 0; i < tagData.size(); i++) {
//            LinearLayout linearLayout = new LinearLayout(this);
//            TextView textView = new TextView(this);
//            textView.setText(getData.get(i).get("title") + "");
//
//            textView.setWidth((screenWidth/5)-20);
//            textView.setHeight(100);
//            textView.setPadding(4, 4, 4, 4);
//            textView.setGravity(Gravity.CENTER);
//            textView.setLayoutParams(lp);
//            textView.setTextColor(Color.WHITE);
//            textView.setBackgroundResource(R.drawable.main_bg_shape);
//
//            linearLayout.addView(textView);
//            gl_tags.addView(linearLayout);
//        }
    }

    /**
     * 删除标签
     * @param getData
     */
    private void removeTag(Map<String, Object> getData) {
        //新建适配器
        String[] from = {"title"};
        int[] to = {R.id.tags_name};
        FlowLayout gl_tags = (FlowLayout) findViewById(R.id.gl_tags);

//        LayoutInflater inflater = LayoutInflater.from(this);

        for (int i = 0; i < tagData.size(); i++) {
            if(getData.get("title").equals(tagData.get(i).get("title")+"")){
                gl_tags.removeViewAt(i);
                tagData.remove(i);
            }
        }
    }
}
