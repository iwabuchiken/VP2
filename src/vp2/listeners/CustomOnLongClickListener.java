package vp2.listeners;

import vp2.utils.Methods.ItemTags;
import android.app.Activity;
import android.view.View;
import android.view.View.OnLongClickListener;

public class CustomOnLongClickListener implements OnLongClickListener {

	//
	Activity actv;
	
	//
	int position;
	
	//
	ItemTags itemTag;
	
	public CustomOnLongClickListener(Activity actv, int position) {
		
		this.actv = actv;
		this.position = position;
		
	}
	
	public CustomOnLongClickListener(Activity actv, int position,
											ItemTags tag) {
		
		this.actv = actv;
		this.position = position;
		this.itemTag = tag;
		
	}//public CustomOnLongClickListener

//	@Override
	public boolean onLongClick(View v) {
		
		if (itemTag != null && itemTag instanceof ItemTags) {
			
			switch (itemTag) {
			
			}//switch (tag)
			
		}//if (tag != null)
		
		return true;
	}//public boolean onLongClick(View arg0)

}//public class CustomOnLongClickListener implements OnLongClickListener
