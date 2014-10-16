package com.hbn.android.ui;

import java.util.ArrayList;
import java.util.List;

import com.hbn.android.R;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

public class MainScreenUI extends Fragment implements AppList.DataCallback{
    
	private final String TAG = "MainScreenUI";
	
	private List<ApplicationInfo> mInstalledApps;
	
    public MainScreenUI() {
    	mInstalledApps = new ArrayList<ApplicationInfo>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
 
        final CheckBox cb_apps = (CheckBox)rootView.findViewById(R.id.cb_apps);
        final CheckBox cb_sys_apps = (CheckBox)rootView.findViewById(R.id.cb_sys_apps);
        Button scan = (Button)rootView.findViewById(R.id.btn_scan);
        scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mInstalledApps.clear();
				List<ApplicationInfo> apps = getActivity().getPackageManager().getInstalledApplications(0);
				for (ApplicationInfo app : apps){
					if ( (app.flags & ApplicationInfo.FLAG_SYSTEM) > 0){
						if (cb_sys_apps.isChecked()){
							Log.i(TAG,"Adding system app");
							mInstalledApps.add(app);
						}
					}else{
						if (cb_apps.isChecked()){
							Log.i(TAG,"Adding data app");
							mInstalledApps.add(app);
						}
					}
				}
				
				AppList frag = new AppList();
				frag.setDataCallback(MainScreenUI.this);
				getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, frag)
					.addToBackStack("AppList")
					.commit();
			}
		});
        
        return rootView;
    }

	@Override
	public List<ApplicationInfo> getAdapterData() {
		return mInstalledApps;
	}
}
