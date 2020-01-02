package com.ecareyun.cloudpos.framwork;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;


/**
 * 
 * 类名称 ：RepeatedClickHandler
 * 类描述 ：处理按钮的重复点击
 * 创建人 ：李章丰
 * 创建时间：下午3:01:28
 */
@SuppressLint("HandlerLeak")
public class RepeatedClickHandler {
	private static final long COOL_DOWN_TIME = 200;
	private static final int MSG_WHAT_COOL_DOWN_COMPLETED = 10043;
	private static final int MSG_WHAT_COOL_DOWN_COMPLETED_TOUCH = 10044;
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_WHAT_COOL_DOWN_COMPLETED:
				((View)msg.obj).setClickable(true);
				break;
			case MSG_WHAT_COOL_DOWN_COMPLETED_TOUCH:
				((View)msg.obj).setEnabled(true);
				break;
			}
		};
	};
	
	/**
	 * 处理重复点击
	 * @param view
	 */
	public void handleRepeatedClick(final View view) {
		view.setClickable(false);
		new Thread() {
			public void run() {
				try {
					sleep(COOL_DOWN_TIME);
					Message msg = new Message();
					msg.what = MSG_WHAT_COOL_DOWN_COMPLETED;
					msg.obj = view;
					handler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	/**
	 * 处理吹气按钮多次快速重复点击
	 * @param view
	 */
	public void handleRepeatedTouch(final View view){
		view.setEnabled(false);
		new Thread() {
			public void run() {
				try {
					sleep(COOL_DOWN_TIME);
					Message msg = handler.obtainMessage();
					msg.what = MSG_WHAT_COOL_DOWN_COMPLETED_TOUCH;
					msg.obj = view;
					handler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
}
