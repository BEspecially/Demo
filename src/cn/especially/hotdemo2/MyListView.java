package cn.especially.hotdemo2;

 
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class MyListView extends ListView {

	public MyListView(Context context) {
		this(context, null);
	}

	public MyListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	private ImageView imageView;
	private int intrinsicHeight;
	private int height;
	/**
	 * 提供一个设置宽高的方法
	 * @param attrs
	 */
	public void setHeaderImageView(ImageView attrs){
		imageView=attrs;
		//得到图片的真实高度
		intrinsicHeight = imageView.getDrawable().getIntrinsicHeight();
		
		height = imageView.getHeight();
	}
	
	/**
	 * 当滑动到头 继续触摸滑动 时候  如果向下 deltaY<0  向上deltaY>0
	 */
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		
		if (isTouchEvent && deltaY<0) {
			LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)imageView.getLayoutParams();
			int newHeigt=layoutParams.height+Math.abs(deltaY)/2;
			//方式图片无线大 一直被拉伸
			if (newHeigt<intrinsicHeight) {
				layoutParams.height=newHeigt;
				imageView.setLayoutParams(layoutParams);
				//imageView.layout(0, 0, width, newHeigt);
			}
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY,
				scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY,
				isTouchEvent);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		//处理 抬手
		case MotionEvent.ACTION_UP:
			//图片开始的高度
			final int startHeight=imageView.getHeight();
			//值动画  0.0   到1.0  
			ValueAnimator animator = ValueAnimator.ofFloat(1.0f);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float percent = animation.getAnimatedFraction();
					
					Integer newHeigt = EvaluateUtil.evaluateInt(percent,
							startHeight, height);
							
					LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)imageView.getLayoutParams();
						
						layoutParams.height=newHeigt;
						imageView.setLayoutParams(layoutParams);
							
				}
			});
			
			
			animator.setDuration(500);
			animator.setInterpolator(new OvershootInterpolator(5.0f));
			animator.start();
			
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	
	
	
	
	
	
	
}
