package com.hbn.android.features;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.hbn.android.features.ui.FeatureMain;
import com.hbn.androididfeature.FeatureComperator;
import com.hbn.androididfeature.IAndroidIDFeature;

import dalvik.system.DexFile;

/**
 * Helper class for providing features for AndroidID
 */
public class FeatureContent {

	private final String TAG = FeatureContent.class.getName();
	
	private static FeatureContent mInstance = null;
	
	public static FeatureContent getInstance(){
		if (mInstance == null){
			mInstance = new FeatureContent();
		}
		return mInstance;
	}
	
	
    /**
     * An array of sample (dummy) items.
     */
    public List<IAndroidIDFeature> ITEMS = new ArrayList<IAndroidIDFeature>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public Map<String, IAndroidIDFeature> ITEM_MAP = new HashMap<String, IAndroidIDFeature>();

    private void addItem(IAndroidIDFeature item) {
        ITEMS.add(item);
        Collections.sort(ITEMS, new FeatureComperator());
        ITEM_MAP.put(item.getFeatureName(), item);
    }

    public void init(Context context){
    	//Manually loading the UI feature (temporary)
    	
    	Log.i(TAG,"Listing packages.");
        try {
        	DexFile df = new DexFile(context.getPackageCodePath());
            for (Enumeration<String> iter = df.entries(); iter.hasMoreElements();) {
            	String className = iter.nextElement();
            	if (className.startsWith("com.hbn.android.features.")){
            		Class<?> clazz = Class.forName(className);
            		for (Class<?> inter : clazz.getInterfaces()){
            			if (inter.getName().equals("com.hbn.androididfeature.IAndroidIDFeature")){
            				IAndroidIDFeature feature = (IAndroidIDFeature) clazz.newInstance(); 
            				Log.i(TAG,"Got a new feature: " + feature.getFeatureName());
            				addItem(feature);
            			}
            		}
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
    	
//    	
//    	for (Package pack : Package.getPackages()){
//    		
//    		Log.i(TAG,pack.getName());
//    	}
    }
    
    public List<IAndroidIDFeature> getItems(){
    	return ITEMS;
    }
    
}
