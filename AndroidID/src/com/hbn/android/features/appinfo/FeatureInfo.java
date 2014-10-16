package com.hbn.android.features.appinfo;

import java.util.Locale;

import com.hbn.androididfeature.IAndroidIDFeature;

import android.support.v4.app.FragmentActivity;

public class FeatureInfo  implements IAndroidIDFeature{

	public final String FEATURE_NAME = "Application info";
	public final int FEATURE_PRIORITY = 10;
	
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
		return "com.hbn.android.features.appinfo.AppInfoScreen";
	}

	public String toString(){
		return String.format(
				Locale.getDefault(),
				"{Name: %s , Priority: %d}", 
				getFeatureName(), getFeaturePriority());
		
	}
	
}
