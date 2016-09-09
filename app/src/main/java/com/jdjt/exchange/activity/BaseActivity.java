package com.jdjt.exchange.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.app.Ioc;
import com.android.pc.ioc.db.sqlite.DbUtils;
import com.android.pc.ioc.db.table.TableUtils;
import com.android.pc.util.Handler_System;
import com.jdjt.exchange.R;
import com.jdjt.exchange.adapter.TagsAdapter;
import com.jdjt.exchange.model.TagDao;
import com.jdjt.exchange.util.AnimationUtils;
import com.jdjt.exchange.view.FlowLayout;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
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
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    protected TableLayout tbl;
    RelativeLayout rl_top_view;
    RelativeLayout rl_bottom_view;
    protected TextView tag0, tag1, tag2, tag3;
    protected TableLayout important_tag; //重要标签组 布局
    DbUtils db;
    TagDao tagDao;
    /**
     * 初始化页面控件
     */
    protected void initPageView() {
        // 获取当前屏幕的宽高
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;
        screenWidth = dm.widthPixels;
        initBottomToolBar();
        iniTopToolBar();
        ViewTreeObserver();

    }

    /**
     * 底部菜单布局声明
     */
    private void initBottomToolBar() {
        //底部菜单整体布局view
        rl_bottom_view = (RelativeLayout) findViewById(R.id.rl_bottom_view);
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
    }

    RelativeLayout find_view;
    ImageView imageView2;//返回按钮
    TextView text_find_btn;//搜索按钮
    EditText editText;//搜索内容

    LinearLayout ll_search;//搜索view容器
    LinearLayout ll_content;
    /**
     * 顶部菜单布局声明
     **/
    private void iniTopToolBar() {

        //一直在顶部展示的筛选标签
        tag0 = (TextView) findViewById(R.id.tag0);
        tag1 = (TextView) findViewById(R.id.tag1);
        tag2 = (TextView) findViewById(R.id.tag2);
        tag3 = (TextView) findViewById(R.id.tag3);
        important_tag= (TableLayout) findViewById(R.id.important_tag);
        rl_top_view = (RelativeLayout) findViewById(R.id.rl_top_view);
        rl_top_bar = (RelativeLayout) findViewById(R.id.rl_top_bar);
        ll_search_view = (LinearLayout) findViewById(R.id.ll_search_view);
        find_view = (RelativeLayout) findViewById(R.id.find_view);
        ll_top_content = (LinearLayout) findViewById(R.id.ll_top_content);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        text_find_btn = (TextView) findViewById(R.id.text_find_btn);
        editText= (EditText) findViewById(R.id.editText);

        ll_search= (LinearLayout) findViewById(R.id.ll_search);

        ll_content= (LinearLayout) findViewById(R.id.ll_content);

        if (rl_top_bar != null) {
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            editText.setCursorVisible(false);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        editText.setCursorVisible(true);
                    }else{
                        editText.setCursorVisible(false);
                    }

                }
            });
            editText.clearFocus();
            toplayoutParams = (ViewGroup.LayoutParams) ll_top_content.getLayoutParams();
            ll_top_content.setLayoutParams(toplayoutParams);
            ll_search_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isShowFindBtn) { //判断当前顶部菜单是否拉伸
                        if (userInfoShow) { //判断底部菜单是否显示，显示的话进行隐藏操作
                            bottomHidAnimation();
                        }
                        ll_content.setVisibility(View.VISIBLE);
                        topShowAnimation();
                        editText.requestFocus();
                    } else {
                        ll_content.setVisibility(View.GONE);


                        topHidAnimation();
                        editText.clearFocus();
                    }
                }
            });
            initTableLayout();
        }
    }

    /**
     * 重新初始化 控件高度
     */
    private void ViewTreeObserver() {

        ll_btn_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                userInfoShow = false;

                bottomlayoutParams.height = screenHeight / 5 * 3;
                //初始化 标签位置
                ViewHelper.setTranslationY(rl_bottom_view, ll_bottom_content.getHeight() - rl_bottom_bar.getHeight());
            }
        });
        // 如果当前 的搜索调前为null 则不做如下处理
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
                    LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams((screenWidth - Handler_System.dip2px(80)) / 4, LinearLayout.LayoutParams.WRAP_CONTENT);
                    textParams.setMargins(2, 2, 2, 2);
//                    textParams.weight=screenWidth/5;
//
                    tag0.setLayoutParams(textParams);
                    tag1.setLayoutParams(textParams);
                    tag2.setLayoutParams(textParams);
                    tag3.setLayoutParams(textParams);
                    //初始化 标签位置
                    ViewHelper.setTranslationY(rl_top_view, -ll_top_content.getHeight() + rl_top_bar.getHeight()-Handler_System.dip2px(10) );
                    initSearCh(isShowFindBtn);
                }
            });


        }
    }

    Map<String, List> map;
    List<TextView> list = null;

    /**
     * 初始化标签组
     */
    private void initTableLayout() {
        initDB();
        tbl = (TableLayout) findViewById(R.id.tbl_tag);
        map = new HashMap<>();
        list = new ArrayList<>();
        TableRow.LayoutParams talbeRowLayoutParams = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 8; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(talbeRowLayoutParams);
            tableRow.setGravity(Gravity.CENTER);
            //getResources().getColor(Co)
            tableRow.setBackgroundColor(getResources().getColor(R.color.half_white_25));//背景设置色为黑色
            for (int j = 4; j <  12; j++) {
                String content;
                if (i == 0) {
                    content = j + "标题" + i;
                } else {
                    content = j + "内容" + i;
                }
                    tableRow.setLayoutParams(talbeRowLayoutParams);
                    tableRow.addView(getTextView(content, i, j));

            }
            tbl.addView(tableRow);

        }

        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(talbeRowLayoutParams);
        tableRow.setGravity(Gravity.CENTER);
        //getResources().getColor(Co)
        tableRow.setBackgroundColor(getResources().getColor(R.color.half_white_25));//背景设置色为黑色
        for (int i = 0; i < 4; i++) {
            RadioGroup group=new RadioGroup(this);
//            group.setOnCheckedChangeListener(this);
            for (int j=0;j<8;j++){
                String content="";
                if (j == 0) {
                    content = i + "Radio" + j ;
                    group.addView(getTitleTextView(content,j,i));
                } else {
                    content =  i+ "Radio" +j;
                    RadioButton radioButton=getRadioButton(content,j,i);
                    group.addView(radioButton);
                    if (j == 1) {
                        group.check(radioButton.getId());
                    }
                }

            }

            tableRow.addView(group);
        }
        important_tag.addView(tableRow);
    }
    /**
     * 标签组 展示view
     * @param content
     * @param row
     * @param col
     * @return
     */
    private RadioButton getRadioButton(String content, int row, int col) {
        RadioButton text = new RadioButton(this);
        text.setText(content);
        Bitmap a=null;
        text.setButtonDrawable(new BitmapDrawable(a));
        text.setTextSize(14);
        text.setPadding(20, 20, 20, 20);
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(getContentRadioGroupLayoutParams(col, row));
        text.setTextColor(getResources().getColor(R.color.white));
        text.setBackgroundResource(R.drawable.selector_radio_gray);
        text.setOnClickListener(this);
        //设置背景色为白色，通过Margin来凸显黑色背景来实现边框
        text.setTag(R.id.colCode, col + "");
        return text;
    }
    /**
     * 标签组 展示view
     *
     * @param content
     * @param row
     * @param col
     * @return
     */
    private TextView getTitleTextView(String content, int row, int col) {
        TextView text = new TextView(this);
        text.setText(content);
        text.setTextSize(14);
        text.setPadding(20, 20, 20, 20);
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(getContentRadioGroupLayoutParams(row, col));
        text.setTextColor(getResources().getColor(R.color.white));
        //设置背景色为白色，通过Margin来凸显黑色背景来实现边框
        if (row == 0) {
            text.setBackgroundColor(getResources().getColor(R.color.btn_shape1));
        } else {
            text.setBackgroundColor(getResources().getColor(R.color.half_brown));
            text.setOnClickListener(this);
            text.setTag(R.id.colCode, col + "");
        }
        return text;
    }
    /**
     * 标签组 展示view
     *
     * @param content
     * @param row
     * @param col
     * @return
     */
    private TextView getTextView(String content, int row, int col) {
        TextView text = new TextView(this);
        text.setText(content);
        text.setTextSize(14);
        text.setPadding(20, 20, 20, 20);
        text.setGravity(Gravity.CENTER);
        text.setLayoutParams(getContentLayoutParams(row, col));
        text.setTextColor(getResources().getColor(R.color.white));
        //设置背景色为白色，通过Margin来凸显黑色背景来实现边框
        if (row == 0) {
            text.setBackgroundColor(getResources().getColor(R.color.btn_shape1));
        } else {
            text.setBackgroundColor(getResources().getColor(R.color.half_brown));
            text.setOnClickListener(this);
            text.setTag(R.id.colCode, col + "");
        }
        return text;
    }

    /**
     * 处理tab 行列显示
     *
     * @param row
     * @param col
     * @return
     */
    private RadioGroup.LayoutParams getContentRadioGroupLayoutParams(int row, int col) {
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        //为保证边框宽度均匀，唯有第一行需要topMargin，第一列需要leftMargin
        params.setMargins(col == 0 ? 1 : 0, row == 0 ? 1 : 0, 1, 1);
//        params.setMargins(1,  1, 1, 1);
        return params;
    }
    /**
     * 处理tab 行列显示
     *
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
        AnimationUtils.showHideAnimation(rl_top_view, false, ll_top_content.getHeight() - rl_top_bar.getHeight()+Handler_System.dip2px(10));
        initSearCh(isShowFindBtn);
    }

    /**
     * 空值搜索栏
     * @param isShowFindBtn
     */
    private void initSearCh(Boolean isShowFindBtn){
        if(!isShowFindBtn){
            Bitmap a=null;
            imageView2.setBackground(new BitmapDrawable(a));
            text_find_btn.setVisibility(View.INVISIBLE);
            editText.setBackgroundResource(R.drawable.main_ll_fillet_half);
            ll_search.setBackgroundResource(R.drawable.main_ll_fillet_half);
        }else{
            imageView2.setBackgroundResource(R.drawable.selector_btn_shape2);
            text_find_btn.setVisibility(View.VISIBLE);
            editText.setBackgroundResource(R.drawable.main_ll_fillet_white);
            ll_search.setBackgroundResource(R.drawable.main_ll_fillet_white);
        }

    }
    /**
     * 顶部动画
     */
    private void topHidAnimation() {
        isShowFindBtn = false;
        AnimationUtils.showHideAnimation(rl_top_view, true, rl_top_bar.getHeight()-Handler_System.dip2px(10)- ll_top_content.getHeight());
        initSearCh(isShowFindBtn);
    }

    /**
     * 底部隐藏动画
     */
    private void bottomHidAnimation() {
        userInfoShow = false;
        AnimationUtils.showHideAnimation(rl_bottom_view, userInfoShow, ll_bottom_content.getHeight() - rl_bottom_bar.getHeight());

    }

    /**
     * 底部显示动画
     */
    private void bottomShowAnimation() {
        userInfoShow = true;
        AnimationUtils.showHideAnimation(rl_bottom_view, userInfoShow, rl_bottom_bar.getHeight() - ll_bottom_content.getHeight());

    }



    /**
     * 页面控件点击事件处理
     */
    protected void initPageViewListener() {

    }

    private void initDB() {
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
        config.setDbName("exchange-demo"); //db名
        config.setDbVersion(1);  //db版本
        db = DbUtils.create(config);//db还有其他的一些构造方法，比如含有更新表版本的监听器的
        tagDao = new TagDao();
        tagDao.setId(0);
//        tagDao = db.findById(TagDao.class, tagDao.getId());
    }


    public void onClick(View v) {

        switch (Integer.valueOf(v.getTag(R.id.colCode) + "")) {
            case 0:
                tag0.setText(((TextView) v).getText() + "");
                tagDao.setTag0(((TextView) v).getText() + "");
                break;
            case 1:
                tag1.setText(((TextView) v).getText() + "");
                tagDao.setTag1(((TextView) v).getText() + "");
                break;
            case 2:
                tag2.setText(((TextView) v).getText() + "");
                tagDao.setTag2(((TextView) v).getText() + "");
                break;
            case 3:
                tag3.setText(((TextView) v).getText() + "");
                tagDao.setTag3(((TextView) v).getText() + "");
                break;
            default:

                if (!"isCheck".equals(v.getTag())) {
                    if(tagData!=null&&tagData.size()==14){
                        Toast.makeText(this,"11111111",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    v.setTag("isCheck");
                    v.setBackgroundColor(getResources().getColor(R.color.btn_shape1));
//                    v.setBackgroundColor(getResources().getColor(R.color.btn_shape1));
                    Map<String, String> tag = new HashMap<>();
                    tag.put("title", ((TextView) v).getText() + "");
                    tag.put("code", "");

                    initTags(setTageData(tag));
                } else {
                    v.setTag("");

                    v.setBackgroundColor(getResources().getColor(R.color.half_brown));
                    if (!"0".equals(v.getTag(R.id.colCode)) && !"1".equals(v.getTag(R.id.colCode)) && !"2".equals(v.getTag(R.id.colCode)) && !"3".equals(v.getTag(R.id.colCode))) {
                        Map<String, Object> tag = new HashMap<>();
                        tag.put("title", ((TextView) v).getText());
                        tag.put("code", "123123");
                        removeTag(tag);
                    }
                }
                break;
        }
        tagDao.setTags(tagData);
//        db.saveOrUpdate(tagDao);

    }

    List<Map<String, String>> tagData;

    private List<Map<String, String>> setTageData(Map<String, String> tag) {
        if (tagData == null)
            tagData = new ArrayList<>();
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
        FlowLayout gl_tags = (FlowLayout) findViewById(R.id.gl_tags);
        gl_tags.setHorizontalSpacing(1);
        gl_tags.setVerticalSpacing(1);
        TagsAdapter tagsAdapter = new TagsAdapter(this);
        tagsAdapter.setDataSource(tagData);
        gl_tags.setAdapter(tagsAdapter);
    }

    /**
     * 删除标签
     *
     * @param getData
     */
    private void removeTag(Map<String, Object> getData) {
        FlowLayout gl_tags = (FlowLayout) findViewById(R.id.gl_tags);
        for (int i = 0; i < tagData.size(); i++) {
            if (getData.get("title").equals(tagData.get(i).get("title") + "")) {
                gl_tags.removeViewAt(i);
                tagData.remove(i);
            }
        }
    }
}
