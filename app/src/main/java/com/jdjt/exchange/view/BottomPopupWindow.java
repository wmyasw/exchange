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

	private void init(){
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		menuView = inflater.inflate(R.layout.pop_bottom_user_info, null);// 填充view
//		mMenuView.findViewById(R.id.parents).setPadding(0, 0, 0,0);
//		补间动画
//
//
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

		int screenWidth =dm.widthPixels;
		int screenHeight =  dm.heightPixels;
		LinearLayout ll_bottom_content= (LinearLayout) menuView.findViewById(R.id.ll_bottom_content);

//		ll_bottom_content.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,screenHeight/2));
		// 设置按钮监听
		// 设置SelectPicPopupWindow的View
		this.setContentView(menuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(screenHeight/2);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.bottom_pop);
		// 实例化一个ColorDrawable颜色为半透明
//		ColorDrawable dw = new ColorDrawable(0x90000000);
		ColorDrawable dw = new ColorDrawable(0x00000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		menuView.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

//				int height = menuView.findViewById(R.id.rl_bottom_view).getTop();
//				int y = (int) event.getY();
//				if (event.getAction() == MotionEvent.ACTION_UP) {
//					if (y < height) {
////						dismiss();
//					}
//				}
				return true;
			}
		});
		//取消
		Button cancle_btn = (Button)menuView.findViewById(R.id.btn_user);
//		menuView.setOnClickListener(null);
		cancle_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

	@Override
	public void dismiss() {
//		if(context!=null&&menuView!=null) {
//			RelativeLayout relative_Layout = (RelativeLayout) menuView.findViewById(R.id.rl_bottom_view);
//			Animation translateAnimation = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);
//			translateAnimation.setDuration(300);
//			translateAnimation.setInterpolator(AnimationUtils
//					.loadInterpolator(context,
//							android.R.anim.decelerate_interpolator));
//			relative_Layout.startAnimation(translateAnimation);
//		}
		super.dismiss();
	}
}
