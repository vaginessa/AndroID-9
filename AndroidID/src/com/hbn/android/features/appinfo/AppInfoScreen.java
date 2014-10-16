package com.hbn.android.features.appinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbn.android.R;
import com.hbn.android.utils.Consts;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class AppInfoScreen extends Fragment{

	private final String TAG = AppInfoScreen.class.getName();

	private PackageInfo mPackInfo ;
	private ApplicationInfo mAppInfo ;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.feature_appinfo, container, false);

		PackageManager pm = rootView.getContext().getPackageManager();
		String bpack = getArguments().getString(Consts.APPLICATION_BPACK);
		try {
			mPackInfo = pm.getPackageInfo(bpack, 
					PackageManager.GET_PERMISSIONS);
			mAppInfo = pm.getApplicationInfo(bpack, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}


		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (mPackInfo == null || mAppInfo == null){
			Log.e(TAG,"Error initializing app/package.");
			return ;
		}

		initListData();


	}

	private void initListData(){

		setListHeader();

		ExpandableListView elv = (ExpandableListView) getView().findViewById(R.id.list);
		elv.setGroupIndicator(getResources().getDrawable(R.drawable.expandable_toggler));

		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();

		addBpack(groupData, childData);
		addInstallSource(groupData, childData);
		addSourceDir(groupData, childData);
		addPermissions(groupData, childData);

		ExpandableListAdapter adapter = new AppInfoExpListAdapter(
				getActivity().getApplicationContext(),
				groupData,
				R.layout.expandable_list_item_1,
				new String[] { "key", },
				new int[] { R.id.tv_text_1},
				childData,
				R.layout.expandable_list_item_2,
				new String[] { "key"},
				new int[] { R.id.tv_text_1}
				);

		elv.setAdapter(adapter);

	}

	private void setListHeader(){

		ImageView icon = (ImageView)getView().findViewById(R.id.iv_app_icon);
		icon.setImageDrawable(
				mAppInfo.loadIcon(getActivity().getPackageManager()));

		TextView appLabel = (TextView)getView().findViewById(R.id.tv_app_label);
		appLabel.setText(
				getString(R.string.app_label, mAppInfo.loadLabel(getActivity().getPackageManager())));

	}
	
	private void addInstallSource(List<Map<String, String>> groupData,
			List<List<Map<String, String>>> childData) {

		Map<String, String> curGroupMap = new HashMap<String, String>();
		groupData.add(curGroupMap);
		
		String installer = getActivity().getPackageManager().getInstallerPackageName(mAppInfo.packageName);
		if (installer == null || installer.isEmpty()){
			installer = getString(R.string.na);
		}
		
		curGroupMap.put("key", getString(R.string.installer, installer));

		List<Map<String, String>> children = new ArrayList<Map<String, String>>();
		childData.add(children);

	}
	
	private void addBpack(List<Map<String, String>> groupData,
			List<List<Map<String, String>>> childData) {

		Map<String, String> curGroupMap = new HashMap<String, String>();
		groupData.add(curGroupMap);
		curGroupMap.put("key", getString(R.string.bpack, mAppInfo.packageName));

		List<Map<String, String>> children = new ArrayList<Map<String, String>>();
		childData.add(children);

	}

	private void addSourceDir(List<Map<String, String>> groupData,
			List<List<Map<String, String>>> childData) {

		Map<String, String> curGroupMap = new HashMap<String, String>();
		groupData.add(curGroupMap);
		curGroupMap.put("key", getString(R.string.source_dir, mAppInfo.sourceDir));

		List<Map<String, String>> children = new ArrayList<Map<String, String>>();
		childData.add(children);

	}

	private void addPermissions(List<Map<String, String>> groupData,
			List<List<Map<String, String>>> childData) {

		Map<String, String> curGroupMap = new HashMap<String, String>();
		groupData.add(curGroupMap);
		curGroupMap.put("key", "Permissions");

		List<Map<String, String>> children = new ArrayList<Map<String, String>>();

		if (mPackInfo.requestedPermissions != null){
			for (String perm : mPackInfo.requestedPermissions){
				Map<String, String> curChildMap = new HashMap<String, String>();
				curChildMap.put("key", perm);
				children.add(curChildMap);
			}
		}
		childData.add(children);


	}






}
