package com.jdjt.exchange.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;

import com.jdjt.exchange.R;
import com.jdjt.exchange.util.BitmapUtils;

import java.lang.reflect.Field;

/**
 * @author wmy
 * @Description: 底部弹出菜单
 * @FileName:BottomPopupWindow
 * @Package
 * @Date 2016/9/2 16:30
 */
public class TopPopupWindow extends PopupWindow {

	// /private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private View menuView;
	Context context;

	public TopPopupWindow(final Activity context) {
		super(context);
		this.context=context;
		init();
	}

	private void init(){
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		menuView = inflater.inflate(R.layout.pop_top_layout, null);// 填充view
		DisplayMetrics dm = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);

		SearchView calSearchView = (SearchView)menuView.findViewById(R.id.searchView);
		if (calSearchView != null) {
			try {        //--拿到字节码
				Class<?> argClass = calSearchView.getClass();        //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
				Field ownField = argClass.getDeclaredField("mSearchPlate");        //--暴力反射,只有暴力反射才能拿到私有属性
				ownField.setAccessible(true);
				View mView = (View) ownField.get(calSearchView);
			}catch (Exception e){
			}
		}



		int screenHeight =  dm.heightPixels;
		int screenWight =  dm.widthPixels;
		LinearLayout ll_bottom_content= (LinearLayout) menuView.findViewById(R.id.ll_bottom_content);
//		ll_bottom_content.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,screenHeight/2));
		// 设置按钮监听
		// 设置SelectPicPopupWindow的View
		this.setContentView(menuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.top_pop);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
//		BitmapUtils.doBlur(BitmapUtils.drawableToBitamp(ll_bottom_content.getBackground()),1,true);
//		Bitmap bitmap=Bitmap.createBitmap(screenWight, screenHeight, Bitmap.Config.ARGB_4444);
//		BitmapUtils.doBlur(context,bitmap,10,true);
//		Drawable drawable =new BitmapDrawable(bitmap);
//		menuView.setBackground(drawable);
//		menuView.buildDrawingCache();
//		Bitmap bitmap = menuView.getDrawingCache();
//		BitmapUtils.doBlur(context,bitmap,10,true);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		this.setOutsideTouchable(true);
		//防止虚拟软键盘被弹出菜单遮住
//		this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
