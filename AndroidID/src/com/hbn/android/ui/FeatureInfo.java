package com.hbn.android.ui;

import com.hbn.androididfeature.IAndroidIDFeature;

import android.support.v4.app.FragmentActivity;

public class FeatureInfo  implements IAndroidIDFeature{

	public final String FEATURE_NAME = "Paths";
	public final int FEATURE_PRIORITY = 1;
	
	@Override
	public String getFeatureName() {
		return FEATURE_NAME;
	}

	@Override
	public int getFeaturePriority() {
		return FEATURE_PRIORITY;
	}

	@Override
	public String getMainClassName() {
		return "com.hbn.android.features.ui.MainScreenUI";
	}

}
