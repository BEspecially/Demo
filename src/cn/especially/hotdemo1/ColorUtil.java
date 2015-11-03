package cn.especially.hotdemo1;

import java.util.Random;

import android.graphics.Color;

public class ColorUtil {
	public static int randomColor(){
		Random random = new Random();
		int red=random.nextInt(100)+50;  //0-255
		int green=random.nextInt(100)+50; //50-150
		int blue=random.nextInt(100)+50;
		return Color.rgb(red, green, blue);
	}
}
