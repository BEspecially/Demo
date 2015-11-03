package cn.especially.hotdemo1;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtil {
	public static GradientDrawable getDrawable(int argb,float radius){
		GradientDrawable drawable=new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);
		drawable.setCornerRadius(radius); 
		drawable.setColor(argb);
		return drawable;
	}
	
	public static StateListDrawable getState(Drawable press,Drawable normal){
		StateListDrawable state = new StateListDrawable();
		state.addState(new int[]{android.R.attr.state_pressed}, press);
		state.addState(new int[] {}, normal);
		return state;
	}
}
