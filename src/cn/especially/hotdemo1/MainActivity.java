package cn.especially.hotdemo1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.banlei.hotdemo1.R;
import cn.especially.hotdemo2.PullDownActivity;
import cn.especially.hotdemo3.PullUpActivity;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn_hot;
	private Button btn_pull_down;
	private Button btn_up_down;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_hot = (Button) findViewById(R.id.btn_hot);
		btn_pull_down = (Button) findViewById(R.id.btn_pull_down);
		btn_up_down = (Button) findViewById(R.id.btn_up_down);

		btn_hot.setOnClickListener(this);
		btn_pull_down.setOnClickListener(this);
		btn_up_down.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_hot:
			Intent intent = new Intent(this, HotActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_pull_down:
			Intent intent2 = new Intent(this, PullDownActivity.class);
			startActivity(intent2);
			break;
		case R.id.btn_up_down:
			Intent intent3 = new Intent(this, PullUpActivity.class);
			startActivity(intent3);
			break;
		}
		

	}

}
