package vp2.listeners.button;

import vp2.main.R;
import vp2.utils.Methods;
import vp2.utils.Tags;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;

public class ButtonOnTouchListener implements OnTouchListener {

	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	public ButtonOnTouchListener(Activity actv) {
		//
		this.actv = actv;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
	}

//	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO 自動生成されたメソッド・スタブ
		Tags.ButtonTags tag = (Tags.ButtonTags) v.getTag();
		
		switch (event.getActionMasked()) {
		case MotionEvent.ACTION_DOWN:
			
			switch (tag) {
			
			case actv_main_bt_export:

				v.setBackgroundColor(Color.GRAY);
				
				break;

			case actv_main_bt_start:

				v.setBackgroundColor(Color.GRAY);
				
				break;

			case actv_main_bt_end:

				v.setBackgroundColor(Color.GRAY);
				
				break;

			default:
				break;
				
			}//switch (tag)
			
			break;//case MotionEvent.ACTION_DOWN:
			
			
		case MotionEvent.ACTION_UP:
			switch (tag) {

			case actv_main_bt_export:

				v.setBackgroundColor(Color.WHITE);
				
				break;
				
			case actv_main_bt_start:

				v.setBackgroundColor(Color.WHITE);
				
				break;

			case actv_main_bt_end:

				v.setBackgroundColor(Color.WHITE);
				
				break;

			default:
				break;

			}//switch (tag)
			
			break;//case MotionEvent.ACTION_UP:
		}//switch (event.getActionMasked())
		return false;
	}

}
