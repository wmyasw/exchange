package com.jdjt.exchange.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jdjt.exchange.R;
import com.jdjt.exchange.util.BitmapUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

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
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initPageLayoutID() != 0)
            setContentView(initPageLayoutID());//初始化页面
        context=this;
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

    private LinearLayout ll_bottom_content;// 底部菜单 内容容器
    public RelativeLayout rl_bottom_bar;  //底部标签点击部分
    public  int screenHeight = 0;        // 屏幕高度
    private LinearLayout ll_btn_view;   //底部标签点击部分
    LinearLayout.LayoutParams layoutParams;

    /**
     * 初始化页面控件
     */
    protected void initPageView() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        screenHeight = dm.heightPixels;

        rl_bottom_bar = (RelativeLayout) findViewById(R.id.rl_bottom_bar);
        ll_btn_view = (LinearLayout) findViewById(R.id.ll_btn_view);
        //底部标签点击部分
        ll_bottom_content = (LinearLayout) findViewById(R.id.ll_bottom_content);
        layoutParams = (LinearLayout.LayoutParams) ll_bottom_content.getLayoutParams();

        ll_bottom_content.setLayoutParams(layoutParams);
        ll_btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userInfoShow) {
                    showAnimation();
                } else {
                    hidAnimation();
                }

            }
        });
        ViewTreeObserver();
    }

    /**
     * 重新初始化 控件高度
     */
    private void ViewTreeObserver() {
        ll_btn_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                Log.i("TAG",+"*******************");
                layoutParams.height = screenHeight / 5 * 3-rl_bottom_bar.getHeight();
                //初始化页移动 底部标签 点击部分
                ViewHelper.setTranslationY(rl_bottom_bar, ll_bottom_content.getHeight());
                //初始化页移动 底部标签 内容部分
                ViewHelper.setTranslationY(ll_bottom_content, ll_bottom_content.getHeight());
            }
        });

        ll_bottom_content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                ll_bottom_content.getViewTreeObserver().removeOnPreDrawListener(this);
                ll_bottom_content.buildDrawingCache();

                Bitmap bmp = ll_bottom_content.getDrawingCache();
                blur(bmp, ll_bottom_content);
                return true;
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();

        float radius = 10;

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);

        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);

        RenderScript rs = RenderScript.create(this);

        Allocation overlayAlloc = Allocation.createFromBitmap(
                rs, overlay);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);

        blur.setRadius(radius);

        blur.forEach(overlayAlloc);

        overlayAlloc.copyTo(overlay);

        view.setBackground(new BitmapDrawable(
                getResources(), overlay));

        rs.destroy();
    }

    boolean userInfoShow = false;

    private void hidAnimation() {
        userInfoShow = false;

        ViewPropertyAnimator.animate(ll_bottom_content).translationYBy(ll_bottom_content.getHeight()).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                ViewPropertyAnimator.animate(rl_bottom_bar).translationYBy(ll_bottom_content.getHeight()).setDuration(500)
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

    private void showAnimation() {
        userInfoShow = true;
        ViewPropertyAnimator.animate(ll_bottom_content).translationYBy(-ll_bottom_content.getHeight()).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                ViewPropertyAnimator.animate(rl_bottom_bar).translationYBy(-ll_bottom_content.getHeight()).setDuration(500)
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

}
