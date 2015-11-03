package cn.especially.hotdemo1;


import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.banlei.hotdemo1.R;

public class HotActivity extends Activity {
	private FrameLayout fl_content;
	private ScrollView scroller;
	private int textHpadding,textVpadding;
	private float hot_radius;
	private Flowlayout flowlayout;
	private ArrayList<String> list=new ArrayList<String>();
	private String[] it = {"猎豹清理大师","QQ","爱奇艺视频","腾讯手机管家","谷歌地图","百度地图","电影电视","免费游戏","hao123上网导航","机票","视频","电子书",
			"酒店","单机","小说","斗地主","优酷","网游","WIFI万能钥匙","熊出没之熊大快跑","美图秀秀","youni有你","万年历-农历黄历","海淘网","QQ空间",
			"播放器","刀塔传奇","壁纸","旅游","大众点评","2048","节奏大师","锁屏","装机必备","京东","单机游戏","天天动听","支付宝钱包","浏览器","网盘",
			"捕鱼达人2","游戏","我的世界","备份","其他","QQ","爱奇艺视频","腾讯手机管家","谷歌地图","百度地图","电影电视","免费游戏","hao123上网导航","机票","视频","电子书",
			"酒店","单机","小说","斗地主","优酷","网游","WIFI万能钥匙","熊出没之熊大快跑","美图秀秀","youni有你","万年历-农历黄历","海淘网","QQ空间",
			"播放器","刀塔传奇","壁纸","旅游","大众点评"};
	private int height;
	private int width;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hot);
		fl_content = (FrameLayout) findViewById(R.id.fl_content);
		initView();
		initDate();
	}
	/**
	 * 展示数据
	 */
	private void initDate() {
		for (String str : it) {
			list.add(str);
			
			final TextView  textView = new TextView(this);
			textView.setText(str);
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(Color.WHITE);
			textView.setTextSize(16);
			textView.setPadding(textHpadding, textVpadding, textHpadding, textVpadding);
			//textView.setBackgroundColor(ColorUtil.randomColor());
			GradientDrawable press = DrawableUtil.getDrawable(ColorUtil.randomColor(),hot_radius);
			GradientDrawable noraml = DrawableUtil.getDrawable(ColorUtil.randomColor(),hot_radius);
			textView.setBackgroundDrawable(DrawableUtil.getState(press, noraml));
			flowlayout.addView(textView);
			
			textView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					runOnUiThread(new Runnable() {
						public void run() {
							Toast.makeText(getApplicationContext(), textView.getText().toString(), Toast.LENGTH_SHORT).show();
							
						}
					});
				}
			});
			
			/**
			 * 长按删除
			 */
			textView.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					setDialog(textView);
					return true;
				}

				private void setDialog(final TextView textView) {
					AlertDialog.Builder builder=new Builder(HotActivity.this);
					builder.setTitle("是否确定删除?");
					builder.setNegativeButton("取消", null);
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							flowlayout.removeView(textView);
						}
					});
					builder.show();
				}
			});
			
		}
		
	}
	/**
	 * 初始化界面
	 */
	private void initView() {
		
		scroller = new ScrollView(this);
		scroller.setVerticalScrollBarEnabled(false);
		flowlayout = new Flowlayout(this);
		
		textHpadding = (int) getResources().getDimension(R.dimen.hot_text_hpading);
		textVpadding = (int) getResources().getDimension(R.dimen.hot_text_vpading);
		hot_radius = getResources().getDimension(R.dimen.hot_radius);
		int padding = (int) getResources().getDimension(R.dimen.flowlayout_padding);
		flowlayout.setPadding(padding, padding, padding, padding);
		//设置水平垂直距离
		int spacing = (int) getResources().getDimension(R.dimen.flowlayout_spacing);
		
		flowlayout.setHorizontaSpace(spacing);
		flowlayout.setVerionSpacing(spacing);
		scroller.addView(flowlayout);
		
		fl_content.addView(scroller);
	}
	
}
