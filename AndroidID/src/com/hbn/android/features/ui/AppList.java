package com.hbn.android.features.ui;

import java.util.ArrayList;
import java.util.List;

import com.hbn.android.R;
import com.hbn.android.features.FeatureContent;
import com.hbn.androididfeature.IAndroidIDFeature;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;

public class AppList extends ListFragment{

	public static interface DataCallback {
		
		public List<ApplicationInfo> getAdapterData(); 
		
	}
	
	private final String TAG = "AppList";
	
	private DataCallback mDummyData = new DataCallback() {
		
		@Override
		public List<ApplicationInfo> getAdapterData() {
			return new ArrayList<ApplicationInfo>();
		}
	};
	
	private DataCallback mDataCallback = null;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
        Log.i(TAG,"OnViewCreated");
		setListAdapter(new AppInfoAdapter(mDataCallback.getAdapterData()));
//        setListAdapter(new ArrayAdapter<ApplicationInfo>(
//                getActivity(),
//                android.R.layout.simple_list_item_activated_1,
//                android.R.id.text1,
//                mDataCallback.getAdapterData()));
		registerForContextMenu(getListView());
		getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {
				
				return false;
			}
		});
		
	}
    
    public void setDataCallback(DataCallback callback){
    	Log.i(TAG,"Setting adapter data, # of items: " + callback.getAdapterData().size());
    	mDataCallback = callback;
    }
    
    @Override
    public void onDetach() {
    	mDataCallback = mDummyData;
    	super.onDetach();
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	for (IAndroidIDFeature feature : FeatureContent.getInstance().ITEMS){
    		menu.add(feature.getFeatureName());
    	}
    	
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	Fragment frag = null;
    	try {
			frag = (Fragment) Class.forName(
					FeatureContent.getInstance().ITEM_MAP.get(item.getTitle()).getMainClassName())
					.newInstance();
		} catch (java.lang.InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    	if (frag != null){
    		getActivity().getSupportFragmentManager()
    		.beginTransaction()
    		.replace(R.id.container, frag)
    		.addToBackStack(null)
    		.commit();
    	}
    	
    	return super.onContextItemSelected(item);
    }
    
}
