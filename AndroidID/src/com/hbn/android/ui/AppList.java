package com.hbn.android.ui;

import java.util.ArrayList;
import java.util.List;

import com.hbn.android.R;
import com.hbn.android.features.FeatureContent;
import com.hbn.android.utils.Consts;
import com.hbn.androididfeature.IAndroidIDFeature;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
		setListAdapter(new AppListAdapter(mDataCallback.getAdapterData()));
//        setListAdapter(new ArrayAdapter<ApplicationInfo>(
//                getActivity(),
//                android.R.layout.simple_list_item_activated_1,
//                android.R.id.text1,
//                mDataCallback.getAdapterData()));
		registerForContextMenu(getListView());
		
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

    	// Get the list
        ListView list = (ListView)v;
        // Get the list item position    
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuInfo;
        int position = info.position;

        // Now you can do whatever.. (Example, load different menus for different items)
    	for (IAndroidIDFeature feature : FeatureContent.getInstance().ITEMS){
    		menu.add(0, position, feature.getFeaturePriority(), feature.getFeatureName());
    	}
    	
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	Fragment frag = null;
    	FeatureContent fc = FeatureContent.getInstance();
    	try {
			frag = (Fragment) Class.forName(
					fc.ITEM_MAP.get(item.getTitle()).getMainClassName())
					.newInstance();
			Bundle args = new Bundle();
			args.putString(Consts.APPLICATION_BPACK, parseBpackFromListPos(item.getItemId()));
			frag.setArguments(args);
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
    
    private String parseBpackFromListPos(int position){
    	
    	String res = null;
    	
    	ApplicationInfo appInfo= (ApplicationInfo)getListView().getItemAtPosition(position);
    	res = appInfo.packageName;
    	return res;
    }
    
}
