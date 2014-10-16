package com.hbn.android.features.appinfo;

import java.util.List;
import java.util.Map;

import com.hbn.android.R;

import android.content.Context;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class AppInfoExpListAdapter extends SimpleExpandableListAdapter {
	
	@SuppressWarnings("unused")
	private final String TAG = AppInfoExpListAdapter.class.getName();
	
    private List<? extends Map<String, ?>> mGroupData;
    List<List<Map<String, String>>> mChildData;
    private String[] mGroupFrom;
    private int[] mGroupTo;
	
	public AppInfoExpListAdapter(Context context,
			List<Map<String, String>> groupData,
			int simpleExpandableListItem2, String[] groupFrom, int[] groupTo,
			List<List<Map<String, String>>> childData,
			int simpleExpandableListItem22, String[] strings2, int[] is2) {
		
		super(context, groupData, simpleExpandableListItem2, groupFrom, 
				groupTo, childData, simpleExpandableListItem22, strings2, is2);
		
        mGroupData = groupData;
        mChildData = childData;
        mGroupFrom = groupFrom;
        mGroupTo = groupTo;
	}
	
	@Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
		
        
		View v;
        if (convertView == null) {
        	v = newGroupView(isExpanded, parent);
        } else {
        	v = convertView;
        }
        bindView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo);
        
        if (mChildData.get(groupPosition).size() == 0){
        	
        	textPaddingHack(parent, v, groupPosition);

        }
        
        return v;
   
	}
	
	
	
    private void textPaddingHack(ViewGroup parent, View v, int groupPosition) {
		
    	ExpandableListView elv = (ExpandableListView)parent;
    	elv.expandGroup(groupPosition);
    	
    	TextView tv = (TextView)v.findViewById(R.id.tv_text_1);
    	
    	float px = 
    			TypedValue.applyDimension(
    					TypedValue.COMPLEX_UNIT_DIP, 
    					3, 
    					parent.getResources().getDisplayMetrics()
    			);
    	tv.setPadding(Math.round(px), tv.getPaddingTop(), tv.getPaddingRight(), tv.getPaddingBottom());
		
	}

	private void bindView(View view, Map<String, ?> data, String[] from, int[] to) {
        int len = to.length;

        for (int i = 0; i < len; i++) {
            TextView v = (TextView)view.findViewById(to[i]);
            if (v != null) {
                v.setText((String)data.get(from[i]));
            }
            
        }
    }
	
    
    
}//Adapter class

