package cn.especially.hotdemo1;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class Flowlayout extends ViewGroup {
	private final int SPACINT = 10;
	private int horizontalSpacing = SPACINT;// 水平之间间距
	private int verionSpacing = SPACINT;

	// 用来保存所有的行对象
	private ArrayList<Line> lienList = new ArrayList<Line>(); // 存取所有的line

	public Flowlayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Flowlayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Flowlayout(Context context) {
		super(context);
	}

	/**
	 * 设置水平间距
	 * 
	 * @param horizontalSpacing
	 */
	public void setHorizontaSpace(int horizontalSpacing) {
		if (horizontalSpacing > 0) {
			this.horizontalSpacing = horizontalSpacing;
		}
	}

	/**
	 * 设置垂直间距
	 * 
	 * @param verionSpacing
	 */
	public void setVerionSpacing(int verionSpacing) {
		if (verionSpacing > 0) {
			this.verionSpacing = verionSpacing;
		}
	}

	/**
	 * 在onMeasure中完成分行的操作
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 获取FlowLayout的宽度
		int width = MeasureSpec.getSize(widthMeasureSpec);
		// 计算出没有pading的宽
		int noPaddingWidth = width - getPaddingLeft() - getPaddingRight();
		// 遍历所有的子TextView，进行分行操作
		Line line = new Line();
		lienList.clear(); // 用之前清理 buff
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			childView.measure(0, 0); // 引起viweonmeasure回调

			if (line.getViewList().size() == 0) {
				line.addLineView(childView);
			} else if (line.getWidth() + horizontalSpacing
					+ childView.getMeasuredWidth() > noPaddingWidth) {
				// .如果当前line的宽+水平间距+childView的宽大于noPaddingWidth，则换行
				lienList.add(line);

				line = new Line();
				line.addLineView(childView);
			} else {
				// 如果小于noPaddingWidth，则将childView放入当前Line中
				line.addLineView(childView);
			}
			if (i == (getChildCount() - 1)) {
				lienList.add(line);
			}
		}
		int hegiht = getPaddingTop() + getPaddingBottom();
		for (int j = 0; j < lienList.size(); j++) {
			hegiht += lienList.get(j).getHeight();
		}
		hegiht += (lienList.size() - 1) * verionSpacing;
		setMeasuredDimension(width, hegiht);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();
		for (int i = 0; i < lienList.size(); i++) {
			Line line = lienList.get(i);

			// 从第二行开始 他们的top总是比上一行高一个行高
			if (i > 0) {
				paddingTop += lienList.get(i - 1).getHeight() + verionSpacing;
			}
			ArrayList<View> viewList = line.getViewList();
			// 计算留白
			int remainSpacing = getLineReamianSpacing(line);

			float perSpacing = remainSpacing / viewList.size();

			for (int j = 0; j < viewList.size(); j++) {
				View childView = viewList.get(j);
				int widthMeasureSpec = MeasureSpec.makeMeasureSpec(
						(int) (childView.getMeasuredWidth() + perSpacing),
						MeasureSpec.EXACTLY);
				childView.measure(widthMeasureSpec, 0);
				if (j == 0) {

					childView.layout(paddingLeft, paddingTop, paddingLeft
							+ childView.getMeasuredWidth(), paddingTop
							+ childView.getMeasuredHeight());
				} else {
					View preView = viewList.get(j - 1);
					int left = preView.getRight() + horizontalSpacing;
					childView.layout(left, preView.getTop(),
							childView.getMeasuredWidth() + left,
							preView.getBottom());
				}
			}
		}
	}

	/**
	 * 获取留白
	 * 
	 * @param line
	 * @return
	 */
	private int getLineReamianSpacing(Line line) {
		return getMeasuredWidth() - getPaddingLeft() - getPaddingRight()
				- line.getWidth();
	}

	class Line {
		private ArrayList<View> viewList = new ArrayList<View>();
		private int width; // 当前行所有的Textview 宽和高 还有间距
		private int height;

		public void addLineView(View lineView) {
			if (!viewList.contains(lineView)) {
				viewList.add(lineView);
				if (viewList.size() == 1) {
					// 如果是第一个textview
					width = lineView.getMeasuredWidth();
				} else {
					// 如果不是第一个textview
					width += horizontalSpacing + lineView.getMeasuredWidth();
				}
				// 更新高度
				height = Math.max(height, lineView.getMeasuredHeight());
			}
		}

		public ArrayList<View> getViewList() {
			return viewList;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
	}
}
