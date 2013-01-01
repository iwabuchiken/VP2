package vp2.listeners;

import vp2.services.ProgressService;
import vp2.utils.Tags;
import vp2.utils.Tags.ViewTags;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;

public class CustomOnPreparedListener implements OnPreparedListener {

	Activity actv;
	
	Tags.ViewTags tag;
	
	public CustomOnPreparedListener(Activity actv) {
		// TODO Auto-generated constructor stub
		
		this.actv = actv;
	}

	public CustomOnPreparedListener(Activity actv, ViewTags tag) {
		// TODO Auto-generated constructor stub
		this.actv = actv;
		
		this.tag = tag;
	}

	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub

		// Log
		Log.d("CustomOnPreparedListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Prepared");
		// Log
		Log.d("CustomOnPreparedListener.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tag.name()=" + tag.name());
		
		switch (tag) {
		
		case actv_main_vv:
			
			Intent i = new Intent(actv, ProgressService.class);
			
			// Log
			Log.d("CustomOnPreparedListener.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Starting service...");
			
			actv.startService(i);
			
			break;
			
			default:
				break;
		
		}//switch (tag)
		
	}//public void onPrepared(MediaPlayer mp)

}//public class CustomOnPreparedListener implements OnPreparedListener
