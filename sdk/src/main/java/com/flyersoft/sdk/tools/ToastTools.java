package com.flyersoft.sdk.tools;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyersoft.sdk.R;

public class ToastTools {

	/** toast显示时距离底部的距离，可定制 */
//	private static int INSTANCE_FROM_BOTTOM =280;

	private static Toast toast;
	public static RelativeLayout rl;

	public static void showToast(Context context, String word) {
		initToastView(context, word);
	}

	public static void showToast(Context context, int word) {
		initToast(context, word);
	}

	public static void showToast(Context context, String word, int fromBottom) {
//		INSTANCE_FROM_BOTTOM = fromBottom;
		initToastView(context, word);
	}

	public static void cancel(){
		toast.cancel();
	}


	/**
	 * 初始化toast风格，具体可定制
	 */
	private static void initToastView(Context context, String word) {
		View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,
				null);
		TextView toastTV = (TextView) view.findViewById(R.id.toast_tv);
		rl= (RelativeLayout) view.findViewById(R.id.rl_toast_layout);
		toastTV.setText(word);
		initToast(context, view);
	}

	/**
	 * 文字为res中的资源
	 * 
	 * @param context
	 * @param word
	 */
	private static void initToast(Context context, int word) {
		View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,
				null);
		TextView toastTV = (TextView) view.findViewById(R.id.toast_tv);
		toastTV.setText(word);
		initToast(context, view);
	}

	private static void initToast(Context context, View view) {
		
		if (toast == null) {
			//某些手机系统初始化的context必须是自己的包名的context才能toast！
			if (!(context instanceof Activity)) {
				context=Tools.getPackageContext(context);
			}
			toast = new Toast(context);
		}
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0,
				(DeviceConfig.getDeviceHeight()/6));
		toast.show();
	}

}
