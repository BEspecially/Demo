package cn.especially.hotdemo2;

import cn.banlei.hotdemo1.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.TextView;

public class PullDownActivity extends Activity {

    private MyListView my;
	private ImageView iv_imager;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulldown);
        
        my = (MyListView) findViewById(R.id.my_main);
        
        final View headerView = View.inflate(getApplicationContext(), R.layout.lv_header, null);
        iv_imager = (ImageView) headerView.findViewById(R.id.iv_imager);
        //布局是在测量时候执行的 view树的观察者
        headerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				my.setHeaderImageView(iv_imager); 
				headerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
        
        
        my.addHeaderView(headerView);
        
        my.setAdapter(new ArrayAdapter<String>(
        		getApplicationContext(), 
        		android.R.layout.simple_list_item_1, 
        		Cheeses.sCheeseStrings){
        	@Override
        	public View getView(int position, View convertView,
        			ViewGroup parent) {
        		TextView tView=(TextView) super.getView(position, convertView, parent);
        		tView.setTextColor(Color.RED);
        		return tView;
        	}
        });
        
	}
    
}
