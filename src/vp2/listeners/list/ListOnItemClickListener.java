package vp2.listeners.list;

import vp2.items.SRTItem;
import vp2.main.MainActv;
import vp2.utils.Methods;
import vp2.utils.Tags;
import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListOnItemClickListener implements OnItemClickListener {

	Activity actv;
	
	Vibrator vib;
	
	public ListOnItemClickListener(Activity actv) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		
		Tags.ListTags tag = (Tags.ListTags) parent.getTag();
		
		vib.vibrate(Methods.vibLength_click);
		
		// Log
		Log.d("ListOnItemClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tag=" + tag.name());
		
		switch (tag) {
		
		case actv_main_lv:
			
			case_actv_main_lv(parent, v, position);
			
//			SRTItem item = (SRTItem) parent.getItemAtPosition(position);
//			
//			Long start_t = item.getStart_time();
//			
//			// Log
//			Log.d("ListOnItemClickListener.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "start_t=" + start_t);
			
			
			
			break;
		
		default:
			break;
		}//switch (tag)


	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)

	private void case_actv_main_lv(AdapterView<?> parent, View v, int position) {
		
		SRTItem item = (SRTItem) parent.getItemAtPosition(position);
		
		Long start_t = item.getStart_time();
		
		// Log
		Log.d("ListOnItemClickListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "start_t=" + start_t);
		
		if (MainActv.vvPlayer != null && MainActv.vvPlayer.isPlaying()) {
			
//			MainActv.vvPlayer.pause();
			
			MainActv.vvPlayer.seekTo(start_t.intValue());
			
//			MainActv.vvPlayer.resume();
			
		} else {//if (MainActv.vvPlayer != null &&  == )
			
			// Log
			Log.d("ListOnItemClickListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "else");
			
		}//if (MainActv.vvPlayer != null &&  == )
		
	}//private void case_actv_main_lv(AdapterView<?> parent, View v, int position)

}
