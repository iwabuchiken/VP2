package vp2.adapters;

import java.util.List;

import vp2.main.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainListAdapter extends ArrayAdapter<String> {

	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	public MainListAdapter(Context con, int resourceId,
			List<String> list) {
		
		super(con, resourceId, list);
		
		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}//public MainListAdapter

	public MainListAdapter(Context con, int resourceId, String[] fileList) {
		// TODO Auto-generated constructor stub
		super(con, resourceId, fileList);
		
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*********************************
		 * 1. Setup view
		 * 2. Get item
		 * 
		 *********************************/
//		// Log
//		Log.d("MainListAdapter.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "getView()");
		
    	View v = null;
    	
    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.listrow_actv_main, null);

		}//if (convertView != null)

    	/*********************************
		 * 2. Get item
		 *********************************/
    	String name = getItem(position);
    	
    	// Log
		Log.d("MainListAdapter.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ ":"
				+ Thread.currentThread().getStackTrace()[2].getMethodName()
				+ "]", "name=" + name);
    	
    	/*********************************
		 * 3. Get view => TextView
		 *********************************/
    	TextView tv = (TextView) v.findViewById(R.id.listrow_actv_main_tv);
    	
    	tv.setText(name);
    	
    	return v;
//		return super.getView(position, convertView, parent);
	}//public View getView

	
}//public class MainListAdapter<T>
