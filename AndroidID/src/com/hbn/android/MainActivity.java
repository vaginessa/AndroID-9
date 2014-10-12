package com.hbn.android;

import com.hbn.android.features.FeatureContent;
import com.hbn.android.features.ui.MainScreenUI;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class MainActivity extends FragmentActivity{

	private final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		FeatureContent.getInstance().init(this);
		
		if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT){
			Log.d(TAG,"orientation portrait");
			setContentView(R.layout.main);
		}else{
			Log.d(TAG,"orientation NOT portrait");
			setContentView(R.layout.main);
		}
		
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainScreenUI()).commit();
		
	}
	
}
