package cn.especially.hotdemo3;

import cn.especially.hotdemo2.EvaluateUtil;

import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MyView extends FrameLayout {
	
	private Callback callback=new Callback() {
		
		@Override
		public boolean tryCaptureView(View arg0, int arg1) {
			
			return ll_boom==arg0||ll_top==arg0;
		}
		/**
		 * 垂直方向
		 */
		@Override
		public int clampViewPositionVertical(View child, int top, int dy) {
			if (child==ll_boom) {
				if (top<0) {
					top=0;
				}else if (top>ll_topHeight) {
					top=ll_topHeight;
				}
			}else if (child==ll_top){
				if (top>0) {
					top=0;   
				}
			}
			return top;
		}
		public int getViewVerticalDragRange(View child) {
			return ll_topHeight;  //大于0
			
		};
		/**
		 * 位置发生改变dy 向上为正 向下为负
		 */
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			if (changedView==ll_top) {
				
				ll_top.layout(0, 0, ll_topWidth, ll_topHeight);
				
				int newHeight=ll_boom.getTop()+dy;
				if (newHeight>ll_topHeight) {
					newHeight=ll_topHeight;
					ll_boom.layout(0, newHeight, ll_topWidth, newHeight+ll_boomheight);
				}
				ll_boom.layout(0, newHeight, ll_topWidth, newHeight+ll_boomheight);
			}
			
			upPullAnimation(ll_boom.getTop());
			
			invalidate();//解决2.3不能动的bug	
		};
		/**
		 * 手释放的时候
		 */
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			if (yvel==0&&ll_boom.getTop()<ll_topHeight*0.5f) {
				close();
			}else if (yvel<0) {
				close();
			}else {
				open();
			}
			
		};
		
	};
	private ViewDragHelper dragHelper;
	private ViewGroup ll_top;
	private ViewGroup ll_boom;
	private int ll_topHeight;
	private int ll_topWidth;
	private int ll_boomheight;

	public MyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	/**
	 * 上拉
	 * @param top
	 */
	protected void upPullAnimation(int top) {
		float fraction = top*1f/ll_topHeight;
		ViewHelper.setTranslationY(ll_top, -EvaluateUtil.evaluateInt(fraction, ll_topHeight/3,0 ));
		
	}
	/**
	 * 打开
	 */
	protected void open() {
		open(true);
	}
	public void open (boolean isSmooth){
		if (isSmooth) {
			dragHelper.smoothSlideViewTo(ll_boom, 0, ll_topHeight);
			invalidate();
		}else {
			ll_boom.layout(0, ll_topHeight, ll_topWidth, ll_topHeight+ll_boomheight);
		}
	}
	/**
	 * 关闭
	 */
	protected void close() {
		close(true);
	}
	public void close (boolean isSmooth){
		if (isSmooth) {
			dragHelper.smoothSlideViewTo(ll_boom, 0, 0);
			invalidate();
		}else {
			ll_boom.layout(0,  0, ll_topWidth, ll_boomheight);
		}
	}

	public MyView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public MyView(Context context) {
		this(context,null);
	}
	private void init() {
		dragHelper = ViewDragHelper.create(this, callback);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dragHelper.processTouchEvent(event);
		return true;
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		
		return dragHelper.shouldInterceptTouchEvent(ev);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount()<2) {
			throw new RuntimeException("Containing at least two view");
		}
		if (!(getChildAt(0) instanceof ViewGroup)||!(getChildAt(1) instanceof ViewGroup)) {
			throw new IllegalArgumentException("illeagal type");
		}
		ll_top = (ViewGroup) getChildAt(0);
		ll_boom = (ViewGroup) getChildAt(1);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		ll_topHeight = ll_top.getMeasuredHeight();
		ll_topWidth = ll_top.getMeasuredWidth();
		ll_boomheight = ll_boom.getMeasuredHeight();
	 
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		ll_boom.layout(0, ll_topHeight, ll_topWidth, ll_topHeight+ll_boomheight);
	}
	 
		@Override
		public void computeScroll() {
			super.computeScroll();
			if(dragHelper.continueSettling(true)){
				 ViewCompat.postInvalidateOnAnimation(this);
			}
		}
	
	
	
	
	
	
	
	
}
