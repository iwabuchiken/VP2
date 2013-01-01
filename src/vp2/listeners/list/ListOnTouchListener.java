package vp2.listeners.list;

import vp2.utils.Methods;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ListOnTouchListener implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	public ListOnTouchListener(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		Methods.ListTags tag = (Methods.ListTags) v.getTag();
		
		// Log
		Log.d("ListOnTouchListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tag=" + tag.name());
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			// Log
			Log.d("ListOnTouchListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "DOWN");
			
			switch (tag) {
			
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:
			
			
		case MotionEvent.ACTION_UP:
			
			// Log
			Log.d("ListOnTouchListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "UP");
			
			switch (tag) {
			
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
		}//switch (event.getActionMasked())
		return false;
	}

}
