package com.jdjt.exchange.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


import com.jdjt.exchange.R;

/**
 * @author wmy
 * @Description: 底部弹出菜单
 * @FileName:BottomPopupWindow
 * @Package
 * @Date 2016/9/2 16:30
 */
public class BottomPopupWindow extends PopupWindow {

	// /private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private View menuView;
	Context context;
	private OnItemClickListener onItemClickListener=null;

	public BottomPopupWindow(final Activity context) {
		super(context);
		this.context=context;
		init();
	}
//	public void isShow(){
//		this.isShowing();
//	}

	private void init(){
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		menuView = inflater.inflate(R.layout.pop_bottom_user_info, null);// 填充view
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

		int screenHeight =  dm.heightPixels;
//		LinearLayout ll_bottom_content= (LinearLayout) menuView.findViewById(R.id.ll_bottom_content);
//		ll_bottom_content.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,screenHeight/2));
		// 设置按钮监听
		// 设置SelectPicPopupWindow的View
		this.setContentView(menuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(screenHeight/5*3);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.bottom_pop);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		this.setFocusable(false);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		//取消
		LinearLayout cancle_btn = (LinearLayout)menuView.findViewById(R.id.ll_btn_view);
//		menuView.setOnClickListener(null);
		cancle_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}
}
