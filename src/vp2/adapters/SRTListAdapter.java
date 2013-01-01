package vp2.adapters;

import java.util.List;

import vp2.items.SRTItem;
import vp2.main.R;
import vp2.utils.Methods;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SRTListAdapter extends ArrayAdapter<SRTItem> {

	// Context
	Context con;

	// Inflater
	LayoutInflater inflater;

	
	public SRTListAdapter(Context con,
				int textViewResourceId, List<SRTItem> objects) {
		super(con, textViewResourceId, objects);
		
		// Context
		this.con = con;

		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}//public SRTListAdapter


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = getView_B4_v1_0(position, convertView, parent);
		
		
		return v;
		
	}//public View getView(int position, View convertView, ViewGroup parent)


	private View getView_B4_v1_0(
			int position, View convertView, ViewGroup parent) {

		View v;
		
    	if (convertView != null) {

    		v = convertView;
    		
		} else {//if (convertView != null)

			v = inflater.inflate(R.layout.listrow_srt_item, null);

		}//if (convertView != null)

		
		SRTItem item = getItem(position);
		
		Long start_time = item.getStart_time();
		
		//
		TextView tv_start = (TextView) v.findViewById(R.id.listrow_srt_item_tv_start);
		
//		tv_start.setText(String.valueOf(start_time));
		tv_start.setText(Methods.convert_milsec_to_digits(start_time));
		
		return v;
		
	}//private View getView_B4_v1_0

	
}//public class SRTListAdapter extends ArrayAdapter<SRTItem>
