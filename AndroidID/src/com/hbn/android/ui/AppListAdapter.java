package com.hbn.android.ui;

import java.util.List;

import com.hbn.android.R;

import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter{

	private List<ApplicationInfo> mData;
	
	public AppListAdapter(List<ApplicationInfo> adapterData) {
		mData = adapterData;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position > mData.size()-1 ? null : mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ApplicationInfo appInfo = mData.get(position);
		
		if (convertView == null){
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.list_item_app, parent, false);
		}
		
		ImageView icon = (ImageView) convertView.findViewById(R.id.iv_icon);
		icon.setImageDrawable(appInfo.loadIcon(parent.getContext().getPackageManager()));
		
		TextView name = (TextView) convertView.findViewById(R.id.tv_appname);
		name.setText(appInfo.loadLabel(parent.getContext().getPackageManager()));
		TextView bpack = (TextView) convertView.findViewById(R.id.tv_bpack);
		bpack.setText(appInfo.packageName);

        return convertView;
	
	}
	

}
