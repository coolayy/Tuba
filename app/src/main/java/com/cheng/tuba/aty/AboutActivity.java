package com.cheng.tuba.aty;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.cheng.tuba.R;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_activity);
	}
	
}
